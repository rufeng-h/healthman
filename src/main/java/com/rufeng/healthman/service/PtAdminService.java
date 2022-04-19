package com.rufeng.healthman.service;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.aop.OperLogRecord;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.util.JwtTokenUtils;
import com.rufeng.healthman.enums.OperTypeEnum;
import com.rufeng.healthman.enums.RoleTypeEnum;
import com.rufeng.healthman.exceptions.AuthenticationException;
import com.rufeng.healthman.exceptions.FileException;
import com.rufeng.healthman.mapper.PtAdminMapper;
import com.rufeng.healthman.pojo.data.AdminFormdata;
import com.rufeng.healthman.pojo.data.PtAdminFormdata;
import com.rufeng.healthman.pojo.data.UpdatePwdFormdata;
import com.rufeng.healthman.pojo.dto.ptadmin.AdminInfo;
import com.rufeng.healthman.pojo.dto.support.LoginResult;
import com.rufeng.healthman.pojo.dto.support.RoleInfo;
import com.rufeng.healthman.pojo.file.PtAdminExcel;
import com.rufeng.healthman.pojo.file.PtAdminExcelListener;
import com.rufeng.healthman.pojo.ptdo.PtAdmin;
import com.rufeng.healthman.pojo.ptdo.PtRole;
import com.rufeng.healthman.pojo.query.LoginQuery;
import com.rufeng.healthman.pojo.query.PtAdminQuery;
import com.rufeng.healthman.security.authentication.Authentication;
import com.rufeng.healthman.security.authentication.AuthenticationImpl;
import com.rufeng.healthman.security.support.UserInfo;
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

import static com.rufeng.healthman.common.util.AuthorityUtils.ALL_AUTHORITY;

/**
 * @author rufeng
 * @time 2022-03-09 23:03
 * @package com.rufeng.healthman.service.impl
 * @description .
 */
@Service
public class PtAdminService {
    private final PtAdminMapper ptAdminMapper;
    private final RedisService redisService;
    private final PtRoleService ptRoleService;
    private final PtClassService ptClassService;
    private final PtCollegeService ptCollegeService;
    private final FileService fileService;

    public PtAdminService(PtAdminMapper ptAdminMapper,
                          RedisService redisService,
                          PtRoleService ptRoleService,
                          PtClassService ptClassService,
                          PtCollegeService ptCollegeService,
                          FileService fileService) {
        this.ptAdminMapper = ptAdminMapper;
        this.redisService = redisService;
        this.ptRoleService = ptRoleService;
        this.ptClassService = ptClassService;
        this.ptCollegeService = ptCollegeService;
        this.fileService = fileService;
    }


    public ApiPage<AdminInfo> pageAdminInfo(Integer page, Integer pageSize, PtAdminQuery query) {
        PageHelper.startPage(page, pageSize);
        /* 管理员 */
        Page<PtAdmin> admins = ptAdminMapper.pageByQuery(query);
        /* 查学院 */
        List<String> adminClsCodes = ptCollegeService.getClsCodeFromAdmins(admins);
        Map<String, String> adminClgNameMap = ptCollegeService.mapClgNameByIds(adminClsCodes);
        /* 权限 */
        List<PtRole> roles = ptRoleService.listRoleByIds(admins.stream()
                .map(PtAdmin::getAdminId).collect(Collectors.toList()));
        /* 查询权限的学院、班级名 */
        Map<String, PtRole> clgRoles = new HashMap<>(10);
        Map<String, PtRole> clsRoles = new HashMap<>(10);
        Map<String, PtRole> systemRoles = new HashMap<>(10);
        roles.forEach(r -> {
            if (r.getRoleType() == RoleTypeEnum.CLASS) {
                clsRoles.put(r.getAdminId(), r);
            } else if (r.getRoleType() == RoleTypeEnum.COLLEGE) {
                clgRoles.put(r.getAdminId(), r);
            } else if (r.getRoleType() == RoleTypeEnum.SYSTEM) {
                systemRoles.put(r.getAdminId(), r);
            }
        });
        Map<String, String> clgNameMap = ptCollegeService.mapClgNameByIds(
                clgRoles.values().stream().map(PtRole::getTarget).collect(Collectors.toList()));
        Map<String, String> clsNameMap = ptClassService.mapClsNameByIds(
                clsRoles.values().stream().map(PtRole::getTarget).collect(Collectors.toList()));

        Map<Long, RoleInfo> clgRolemap = clgRoles.values().stream().collect(
                Collectors.toMap(PtRole::getRoleId, r -> new RoleInfo(r, clgNameMap.get(r.getTarget()))));
        Map<Long, RoleInfo> clsRoleMap = clsRoles.values().stream().collect(
                Collectors.toMap(PtRole::getRoleId, r -> new RoleInfo(r, clsNameMap.get(r.getTarget()))));

        return ApiPage.convert(admins, a -> {
            String adminId = a.getAdminId();
            String clgName = null;
            List<RoleInfo> roleInfos = new ArrayList<>();
            if (clgRoles.containsKey(adminId)) {
                roleInfos.add(clgRolemap.get(clgRoles.get(adminId).getRoleId()));
            }
            if (clsRoles.containsKey(adminId)) {
                roleInfos.add(clsRoleMap.get(clsRoles.get(adminId).getRoleId()));
            }
            if (systemRoles.containsKey(adminId)) {
                roleInfos.add(new RoleInfo(systemRoles.get(adminId), "系统管理员"));
            }
            if (a.getClgCode() != null) {
                clgName = adminClgNameMap.get(a.getClgCode());
            }
            return new AdminInfo(a, clgName, roleInfos);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public LoginResult login(LoginQuery loginQuery) {
        PtAdmin admin = ptAdminMapper.selectByPrimaryKey(loginQuery.getUserId());
        if (admin == null) {
            throw new AuthenticationException("用户不存在!");
        }
        if (!DigestUtils.md5DigestAsHex(loginQuery.getPassword().getBytes(StandardCharsets.UTF_8)).equals(admin.getPassword())) {
            throw new AuthenticationException("密码错误!");
        }
        /* 查询学院 */
        String clgName = null;
        if (admin.getClgCode() != null) {
            clgName = ptCollegeService.getCollege(admin.getClgCode()).getClgName();
        }

        /* 查询角色权限 */
        List<PtRole> roles = ptRoleService.listRole(admin.getAdminId());
        /* 返回结果 */
        UserInfo info = new AdminInfo(admin, clgName, hadleRoles(roles));
        /* 认证信息 */
        Authentication authentication = new AuthenticationImpl(info);
        redisService.setObject(admin.getAdminId(), authentication);

        /* 更新上次登录时间 */
        ptAdminMapper.updateByPrimaryKeySelective(PtAdmin
                .builder().adminId(admin.getAdminId())
                .adminLastLogin(LocalDateTime.now())
                .build());

        String token = JwtTokenUtils.generateToken(admin.getAdminId(), admin.getAdminName());
        return new LoginResult(token, info);
    }

    public List<RoleInfo> hadleRoles(List<PtRole> roles) {
        List<RoleInfo> roleInfos = new ArrayList<>();
        /* 查询班级或学院名 */
        List<PtRole> clgRoles = new ArrayList<>();
        List<PtRole> clsRoles = new ArrayList<>();
        roles.forEach(r -> {
            RoleTypeEnum roleType = r.getRoleType();
            switch (roleType) {
                case CLASS:
                    clsRoles.add(r);
                    break;
                case COLLEGE:
                    clgRoles.add(r);
                    break;
                case SYSTEM:
                    roleInfos.add(new RoleInfo(r, "系统管理员"));
                default:
            }
        });
        Map<String, String> clsNameMap = ptClassService.mapClsNameByIds(
                clsRoles.stream().map(PtRole::getTarget).collect(Collectors.toList()));
        Map<String, String> clgNameMap = ptCollegeService.mapClgNameByIds(
                clgRoles.stream().map(PtRole::getTarget).collect(Collectors.toList()));
        roleInfos.addAll(clgRoles.stream().map(r -> new RoleInfo(r, clgNameMap.get(r.getTarget()))).collect(Collectors.toList()));
        roleInfos.addAll(clsRoles.stream().map(r -> new RoleInfo(r, clsNameMap.get(r.getTarget()))).collect(Collectors.toList()));
        return roleInfos;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean addAdminSelective(PtAdminFormdata formdata) {
        PtAdmin admin = PtAdmin.builder().adminName(formdata.getAdminName())
                .phone(formdata.getPhone())
                .email(formdata.getEmail())
                .password(formdata.getPassword())
                .adminId(formdata.getAdminId())
                .adminDesp(formdata.getDesp()).build();
        List<RoleTypeEnum> roleTypes = formdata.getRoleTypes();
        ptAdminMapper.insertSelective(admin);
        ArrayList<PtRole> roles = new ArrayList<>();
        for (RoleTypeEnum roleType : roleTypes) {
            switch (roleType) {
                case CLASS:
                    roles.addAll(formdata.getClsCodes().stream().map(code -> PtRole.builder()
                            .adminId(admin.getAdminId())
                            .roleType(RoleTypeEnum.CLASS)
                            .target(code)
                            .roleValue(ALL_AUTHORITY).build()).collect(Collectors.toList()));
                    break;
                case COLLEGE:
                    roles.addAll(formdata.getClgCodes().stream().map(code -> PtRole.builder()
                            .adminId(admin.getAdminId())
                            .roleType(RoleTypeEnum.COLLEGE)
                            .target(code)
                            .roleValue(ALL_AUTHORITY).build()).collect(Collectors.toList()));
                    break;
                case SYSTEM:
                    roles.add(PtRole.builder().roleType(RoleTypeEnum.SYSTEM)
                            .roleValue(ALL_AUTHORITY)
                            .adminId(admin.getAdminId())
                            .build());
                    break;
                default:
                    throw new IllegalArgumentException("参数错误!");
            }
        }
        ptRoleService.batchInsertSelective(roles);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public int addAdminSelective(List<PtAdminExcel> list) {
        List<PtRole> roles = new ArrayList<>();
        for (PtAdminExcel admin : list) {
            String adminId = admin.getAdminId();
            admin.getClgCodes().forEach(code -> roles.add(PtRole.builder()
                    .roleType(RoleTypeEnum.COLLEGE)
                    .roleValue(ALL_AUTHORITY)
                    .adminId(adminId)
                    .target(code).build()));
            admin.getClsCodes().forEach(code -> roles.add(PtRole.builder()
                    .roleValue(ALL_AUTHORITY)
                    .adminId(adminId)
                    .roleType(RoleTypeEnum.CLASS)
                    .target(code).build()));

        }
        int count = ptAdminMapper.batchInsertSelective(list);
        ptRoleService.batchInsertSelective(roles);
        return count;
    }

    @OperLogRecord(description = "导入教师数据", operType = OperTypeEnum.INSERT)
    public Integer uploadAdmin(MultipartFile file) {
        PtAdminExcelListener listener = new PtAdminExcelListener(this, ptClassService, ptCollegeService);
        try {
            EasyExcel.read(file.getInputStream(), PtAdminExcel.class, listener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listener.getHandledCount();
    }

    public Resource excelTemplate() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream, PtAdminExcel.class).sheet().doWrite(Collections.emptyList());
        return new ByteArrayResource(outputStream.toByteArray());
    }

    public List<PtAdmin> listAdminByIds(List<String> adminIds) {
        if (adminIds.size() == 0) {
            return Collections.emptyList();
        }
        return ptAdminMapper.listAdminByIds(adminIds);
    }

    public PtAdmin getAdmin(String adminId) {
        return ptAdminMapper.selectByPrimaryKey(adminId);
    }

    public List<String> listAdminId() {
        return ptAdminMapper.listAdminId();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateAdmin(AdminFormdata formdata) {
        PtAdmin ptAdmin = ptAdminMapper.selectByPrimaryKey(formdata.getAdminId());
        PtAdmin admin = PtAdmin.builder()
                .adminId(formdata.getAdminId())
                .adminDesp(formdata.getDesp())
                .adminModified(LocalDateTime.now())
                .avatar(formdata.getAvatar())
                .phone(formdata.getPhone())
                .email(formdata.getEmail())
                .adminBirth(formdata.getBirth())
                .build();
        if (ptAdminMapper.updateByPrimaryKeySelective(admin) != 1) {
            return false;
        }
        if (!fileService.remove(ptAdmin.getAvatar())) {
            throw new FileException("删除文件失败！");
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updatePwd(String adminId, UpdatePwdFormdata formdata) {
        PtAdmin admin = ptAdminMapper.selectByPrimaryKey(adminId);
        if (!DigestUtils.md5DigestAsHex(formdata.getOldPwd().getBytes(StandardCharsets.UTF_8)).equals(admin.getPassword())) {
            throw new AuthenticationException("原始密码错误！");
        }
        PtAdmin ptAdmin = new PtAdmin();
        ptAdmin.setAdminId(adminId);
        ptAdmin.setPassword(DigestUtils.md5DigestAsHex(formdata.getNewPwd().getBytes(StandardCharsets.UTF_8)));
        ptAdmin.setAdminModified(LocalDateTime.now());
        return ptAdminMapper.updateByPrimaryKeySelective(ptAdmin) == 1;
    }
}
