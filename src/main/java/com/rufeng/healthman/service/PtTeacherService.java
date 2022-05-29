package com.rufeng.healthman.service;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.util.JwtTokenUtils;
import com.rufeng.healthman.common.util.StringUtils;
import com.rufeng.healthman.enums.UserTypeEnum;
import com.rufeng.healthman.exceptions.AuthenticationException;
import com.rufeng.healthman.mapper.*;
import com.rufeng.healthman.pojo.data.PtLoginFormdata;
import com.rufeng.healthman.pojo.data.PtPwdUpdateFormdata;
import com.rufeng.healthman.pojo.data.PtUserFormdata;
import com.rufeng.healthman.pojo.dto.ptteacher.PtTeacherClgIdentity;
import com.rufeng.healthman.pojo.dto.ptteacher.PtTeacherInfo;
import com.rufeng.healthman.pojo.dto.ptteacher.PtTeacherListInfo;
import com.rufeng.healthman.pojo.dto.ptteacher.PtTeacherPageInfo;
import com.rufeng.healthman.pojo.dto.support.LoginResult;
import com.rufeng.healthman.pojo.file.PtTeacherExcel;
import com.rufeng.healthman.pojo.file.PtTeacherExcelListener;
import com.rufeng.healthman.pojo.ptdo.*;
import com.rufeng.healthman.pojo.query.PtTeacherQuery;
import com.rufeng.healthman.security.authentication.Authentication;
import com.rufeng.healthman.security.authentication.AuthenticationImpl;
import com.rufeng.healthman.security.support.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.rufeng.healthman.security.authority.Authority.DEFAULT_TEACHER_AUTHORITIES;


/**
 * @author rufeng
 * @time 2022-03-09 23:03
 * @package com.rufeng.healthman.service.impl
 * @description .
 */
@Slf4j
@Service
public class PtTeacherService {
    private static final String DEFAULT_PWD = "123456";
    private final PtTeacherRoleMapper ptTeacherRoleMapper;
    private final PtTeacherMapper ptTeacherMapper;
    private final RedisService redisService;
    private final FileService fileService;
    private final PtClassMapper ptClassMapper;
    private final PtCollegeMapper ptCollegeMapper;
    private final PtOperationMapper ptOperationMapper;
    private final PtRoleOperMapper ptRoleOperMapper;
    private final PtRoleMapper ptRoleMapper;

    public PtTeacherService(PtTeacherRoleMapper ptTeacherRoleMapper, PtTeacherMapper ptTeacherMapper,
                            RedisService redisService,
                            FileService fileService,
                            PtClassMapper ptClassMapper,
                            PtCollegeMapper ptCollegeMapper,
                            PtOperationMapper operationMapper,
                            PtRoleOperMapper ptRoleOperMapper, PtRoleMapper ptRoleMapper) {
        this.ptTeacherRoleMapper = ptTeacherRoleMapper;
        this.ptTeacherMapper = ptTeacherMapper;
        this.redisService = redisService;
        this.fileService = fileService;
        this.ptClassMapper = ptClassMapper;
        this.ptCollegeMapper = ptCollegeMapper;
        this.ptOperationMapper = operationMapper;

        this.ptRoleOperMapper = ptRoleOperMapper;
        this.ptRoleMapper = ptRoleMapper;
    }

    public ApiPage<PtTeacherPageInfo> pageTeacherInfo(Integer page, Integer pageSize, PtTeacherQuery query) {
        PageHelper.startPage(page, pageSize);
        Page<PtTeacher> teachers = ptTeacherMapper.page(query);
        if (teachers.isEmpty()) {
            return ApiPage.empty(teachers);
        }
        /* 查学院 */
        List<String> clgCodes = PtCollegeService.getClgCodeFromTeachers(teachers);
        Map<String, String> clgNameMap = ptCollegeMapper.mapClgNameByIds(clgCodes);
        /* 查班级 */
        List<String> teaIds = teachers.stream().map(PtTeacher::getTeaId).collect(Collectors.toList());
        List<PtClass> classes = ptClassMapper.listByTeaIds(teaIds);
        Map<String, List<PtClass>> map = new HashMap<>(teachers.size());
        classes.forEach(c -> map.computeIfAbsent(c.getTeaId(), k -> new ArrayList<>()).add(c));
        List<PtTeacherPageInfo> infos = teachers.stream().map(
                t -> new PtTeacherPageInfo(t, clgNameMap.get(t.getClgCode()), map.get(t.getTeaId()))).collect(Collectors.toList());
        return ApiPage.convert(teachers, infos);

    }

    @Transactional(rollbackFor = Exception.class)
    public LoginResult login(PtLoginFormdata ptLoginFormdata) {
        PtTeacher teacher = ptTeacherMapper.selectByPrimaryKey(ptLoginFormdata.getUserId());
        if (teacher == null) {
            throw new AuthenticationException("用户不存在!");
        }
        if (!StringUtils.pwdEquals(ptLoginFormdata.getPassword(), teacher.getPassword())) {
            throw new AuthenticationException("密码错误!");
        }
        /* 查询学院 */
        String clgName = null;
        if (teacher.getClgCode() != null) {
            clgName = ptCollegeMapper.selectByPrimaryKey(teacher.getClgCode()).getClgName();
        }
        /* 查班级 */
        List<PtClass> classes = ptClassMapper.listByTeaId(teacher.getTeaId());
        /* 查询教师角色 */
        List<PtTeacherRole> teacherRoles = ptTeacherRoleMapper.listByTeaId(teacher.getTeaId());
        List<Long> roleIds = teacherRoles.stream().map(PtTeacherRole::getRoleId).collect(Collectors.toList());
        List<PtRole> roles = ptRoleMapper.listByIds(roleIds);
        /* 查询权限 */
        List<String> operIds = ptRoleOperMapper.listOperIdByRoleIds(roleIds);
        Set<String> authorities = new TreeSet<>(DEFAULT_TEACHER_AUTHORITIES);
        if (operIds.size() != 0) {
            List<PtOperation> operations = ptOperationMapper.listByIds(operIds);
            /* 组装数据 */
            operations.forEach(o -> authorities.add(o.getOperName()));
        }
        /* 返回结果 */
        PtTeacherInfo info = new PtTeacherInfo(teacher, clgName, classes, roles, authorities);
        /* 认证信息 */
        Authentication authentication = new AuthenticationImpl(info);
        redisService.setObject(UserInfo.userKey(UserTypeEnum.TEACHER, teacher.getTeaId()), authentication);
        /* 更新上次登录时间 */
        ptTeacherMapper.updateByPrimaryKeySelective(PtTeacher
                .builder().teaId(teacher.getTeaId())
                .teaLastLogin(LocalDateTime.now())
                .build());
        String token = JwtTokenUtils.generateToken(teacher.getTeaId(), UserTypeEnum.TEACHER);
        return new LoginResult(token, info);
    }

    @Transactional(rollbackFor = Exception.class)
    public int addTeacherSelective(List<PtTeacherExcel> list) {
        return ptTeacherMapper.batchInsertSelective(list);
    }

    public Integer uploadTeacher(MultipartFile file) {
        PtTeacherExcelListener listener = new PtTeacherExcelListener(this, ptCollegeMapper);
        try {
            EasyExcel.read(file.getInputStream(), PtTeacherExcel.class, listener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listener.getHandledCount();
    }

    public Resource excelTemplate() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream, PtTeacherExcel.class).sheet().doWrite(Collections.emptyList());
        return new ByteArrayResource(outputStream.toByteArray());
    }

    public List<PtTeacherClgIdentity> listClgIdentity() {
        return ptTeacherMapper.listClgIdentity();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateTeacher(String teaId, PtUserFormdata formdata) {
        PtTeacher ptTeacher = ptTeacherMapper.selectByPrimaryKey(teaId);
        PtTeacher teacher = PtTeacher.builder()
                .teaId(teaId)
                .teaDesp(formdata.getDesp())
                .teaModified(LocalDateTime.now())
                .avatar(formdata.getAvatar())
                .phone(formdata.getPhone())
                .email(formdata.getEmail())
                .teaBirth(formdata.getBirth())
                .build();
        if (ptTeacherMapper.updateByPrimaryKeySelective(teacher) != 1) {
            return false;
        }
        if (!fileService.remove(ptTeacher.getAvatar())) {
            log.warn("删除头像失败！");
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updatePwd(String teacherId, PtPwdUpdateFormdata formdata) {
        PtTeacher teacher = ptTeacherMapper.selectByPrimaryKey(teacherId);
        if (!StringUtils.pwdEquals(formdata.getOldPwd(), teacher.getPassword())) {
            throw new AuthenticationException("原始密码错误！");
        }
        PtTeacher ptTeacher = new PtTeacher();
        ptTeacher.setTeaId(teacherId);
        ptTeacher.setPassword(DigestUtils.md5DigestAsHex(formdata.getNewPwd().getBytes(StandardCharsets.UTF_8)));
        ptTeacher.setTeaModified(LocalDateTime.now());
        return ptTeacherMapper.updateByPrimaryKeySelective(ptTeacher) == 1;
    }

    public List<PtTeacherListInfo> listTeacherListInfo() {
        List<PtTeacher> teachers = ptTeacherMapper.listTeacherListInfo();
        if (teachers.isEmpty()) {
            return Collections.emptyList();
        }
        /* 查学院 */
        List<String> clgCodes = PtCollegeService.getClgCodeFromTeachers(teachers);
        if (clgCodes.size() != 0) {
            Map<String, String> clgNameMap = ptCollegeMapper.mapClgNameByIds(clgCodes);
            return teachers.stream().map(t -> new PtTeacherListInfo(
                    t, clgNameMap.get(t.getClgCode()))).collect(Collectors.toList());
        }
        return teachers.stream().map(PtTeacherListInfo::new).collect(Collectors.toList());
    }

    public boolean resetPwd(String teaId) {
        PtTeacher ptTeacher = PtTeacher.builder()
                .teaId(teaId)
                .password(DigestUtils.md5DigestAsHex(DEFAULT_PWD.getBytes(StandardCharsets.UTF_8)))
                .teaModified(LocalDateTime.now())
                .build();
        return ptTeacherMapper.updateByPrimaryKeySelective(ptTeacher) == 1;
    }

    public boolean deleteTeacher(String teaId) {
        return ptTeacherMapper.deleteByPrimaryKey(teaId) == 1;
    }
}
