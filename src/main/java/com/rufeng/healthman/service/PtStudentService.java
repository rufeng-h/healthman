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
import com.rufeng.healthman.pojo.dto.ptmeasurement.PtStuMeasurementPageInfo;
import com.rufeng.healthman.pojo.dto.ptmeasurement.StuMeasurementStatus;
import com.rufeng.healthman.pojo.dto.ptstu.PtStudentInfo;
import com.rufeng.healthman.pojo.dto.ptstu.PtStudentPageInfo;
import com.rufeng.healthman.pojo.dto.support.LoginResult;
import com.rufeng.healthman.pojo.file.PtStudentExcel;
import com.rufeng.healthman.pojo.file.PtStudentExcelListener;
import com.rufeng.healthman.pojo.ptdo.PtClass;
import com.rufeng.healthman.pojo.ptdo.PtCollege;
import com.rufeng.healthman.pojo.ptdo.PtMeasurement;
import com.rufeng.healthman.pojo.ptdo.PtStudent;
import com.rufeng.healthman.pojo.query.PtStudentQuery;
import com.rufeng.healthman.security.authentication.Authentication;
import com.rufeng.healthman.security.authentication.AuthenticationImpl;
import com.rufeng.healthman.security.support.UserInfo;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.rufeng.healthman.security.authority.Authority.DEFAULT_STUDENT_AUTHORITIES;

/**
 * @author rufeng
 * @time 2022-03-09 22:10
 * @package com.rufeng.healthman.service.impl
 * @description .
 */
@Service
@Slf4j
public class PtStudentService {
    private static final String DEFAULT_PWD = "123456";
    private final PtStudentMapper ptStudentMapper;
    private final RedisService redisService;
    private final FileService fileService;
    private final PtScoreMapper ptScoreMapper;
    private final PtMeasurementMapper ptMeasurementMapper;
    private final PtCollegeMapper ptCollegeMapper;
    private final PtClassMapper ptClassMapper;

    public PtStudentService(PtStudentMapper ptStudentMapper, RedisService redisService, FileService fileService, PtScoreMapper ptScoreMapper, PtMeasurementMapper ptMeasurementMapper, PtCollegeMapper ptCollegeMapper, PtClassMapper ptClassMapper) {
        this.ptStudentMapper = ptStudentMapper;
        this.redisService = redisService;
        this.ptCollegeMapper = ptCollegeMapper;
        this.fileService = fileService;
        this.ptScoreMapper = ptScoreMapper;
        this.ptMeasurementMapper = ptMeasurementMapper;
        this.ptClassMapper = ptClassMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public LoginResult login(PtLoginFormdata ptLoginFormdata) {
        PtStudent student = ptStudentMapper.selectByPrimaryKey(ptLoginFormdata.getUserId());
        if (student == null) {
            throw new AuthenticationException("用户不存在!");
        }
        if (!DigestUtils.md5DigestAsHex(ptLoginFormdata.getPassword().getBytes(StandardCharsets.UTF_8)).equals(student.getPassword())) {
            throw new AuthenticationException("密码错误!");
        }
        /* 查班级 */
        PtClass ptClass = ptClassMapper.selectByPrimaryKey(student.getClsCode());
        /* 查学院 */
        PtCollege college = null;
        if (ptClass.getClgCode() != null) {
            college = ptCollegeMapper.selectByPrimaryKey(ptClass.getClgCode());
        }
        UserInfo info = new PtStudentInfo(student, ptClass, college, new HashSet<>(DEFAULT_STUDENT_AUTHORITIES));
        /* 认证信息 */
        Authentication authentication = new AuthenticationImpl(info);
        redisService.setObject(UserInfo.userKey(UserTypeEnum.STUDENT, student.getStuId()), authentication);

        /* 更新登录时间 */
        PtStudent stu = PtStudent.builder().stuId(student.getStuId()).stuLastLogin(LocalDateTime.now()).build();
        ptStudentMapper.updateByPrimaryKeySelective(stu);

        String token = JwtTokenUtils.generateToken(student.getStuId(), UserTypeEnum.STUDENT);
        return new LoginResult(token, info);
    }

    public ApiPage<PtStudentPageInfo> pageStudentInfo(Integer page, Integer pageSize, PtStudentQuery query) {
        PageHelper.startPage(page, pageSize);
        Page<PtStudent> students = ptStudentMapper.pageStudent(query);
        if (students.isEmpty()) {
            return ApiPage.empty(students);
        }
        /* 查班级名 */
        List<String> clsCodes = students.stream().map(PtStudent::getClsCode).collect(Collectors.toList());
        List<PtClass> classes = ptClassMapper.listClassByClsCodes(clsCodes);
        Map<String, PtClass> clsMap = classes.stream().collect(Collectors.toMap(PtClass::getClsCode, c -> c));
        /* 查学院名和代码 */
        List<String> clgCodes = PtCollegeService.getClgCodeFromClasses(classes);
        Map<String, String> clgNameMap = ptCollegeMapper.mapClgNameByIds(clgCodes);
        /* 组装数据 */
        List<PtStudentPageInfo> infos = students.stream().map(s -> {
            PtClass cls = clsMap.get(s.getClsCode());
            String clgCode = cls.getClgCode();
            if (clgCode != null) {
                return new PtStudentPageInfo(s, cls.getClsName(), clgCode, clgNameMap.get(clgCode));
            }
            return new PtStudentPageInfo(s, cls.getClsName());
        }).collect(Collectors.toList());
        return ApiPage.convert(students, infos);
    }


    public Resource fileTemplate() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream, PtStudentExcel.class).sheet().doWrite(Collections.emptyList());
        return new ByteArrayResource(outputStream.toByteArray());
    }


    public Integer uploadStudent(MultipartFile file, String clsCode) {
        PtStudentExcelListener listener = new PtStudentExcelListener(this, ptClassMapper, clsCode);
        try {
            EasyExcel.read(file.getInputStream(), PtStudentExcel.class, listener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listener.getHandledCount();
    }


    public int addStudentSelective(List<PtStudentExcel> cachedDataList) {
        if (cachedDataList.size() == 0) {
            return 0;
        }
        return ptStudentMapper.batchInsertSelective(cachedDataList);
    }

    public PtStuMeasurementPageInfo getStuMsInfo(String stuId) {
        PtStudent student = ptStudentMapper.selectByPrimaryKey(stuId);
        /* 查班级 */
        PtClass ptClass = ptClassMapper.selectByPrimaryKey(student.getClsCode());
        /* 查学院 */
        String clgCode = ptClass.getClgCode();
        PtCollege college = null;
        if (clgCode != null) {
            college = ptCollegeMapper.selectByPrimaryKey(clgCode);
        }
        /* 查体测完成状态 */
        Map<Long, Boolean> msStatusMap = ptMeasurementMapper.listStuMsStatus(stuId);
        List<StuMeasurementStatus> msStatus = new ArrayList<>();
        if (!msStatusMap.isEmpty()) {
            List<PtMeasurement> measurements = ptMeasurementMapper.listMeasurement(new ArrayList<>(msStatusMap.keySet()));
            msStatus = measurements.stream().map(m -> new StuMeasurementStatus(m, msStatusMap.get(m.getMsId()))).collect(Collectors.toList());
        }
        if (college == null) {
            return new PtStuMeasurementPageInfo(student, ptClass.getClsName(), msStatus);
        }
        return new PtStuMeasurementPageInfo(student, ptClass.getClsName(), college.getClgCode(), college.getClgName(), msStatus);
    }

    public List<String> listStuId() {
        return ptStudentMapper.listStuId();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateStudent(String stuId, PtUserFormdata formdata) {
        PtStudent ptStudent = ptStudentMapper.selectByPrimaryKey(stuId);
        PtStudent student = new PtStudent();
        student.setStuId(stuId);
        student.setStuBirth(formdata.getBirth());
        student.setAvatar(formdata.getAvatar());
        student.setStuDesp(formdata.getDesp());
        student.setStuModified(LocalDateTime.now());
        if (ptStudentMapper.updateByPrimaryKeySelective(student) != 1) {
            return false;
        }
        if (!fileService.remove(ptStudent.getAvatar())) {
            log.warn("删除头像失败！");
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updatePwd(String stuId, PtPwdUpdateFormdata formdata) {
        PtStudent student = ptStudentMapper.selectByPrimaryKey(stuId);
        if (!StringUtils.pwdEquals(formdata.getOldPwd(), student.getPassword())) {
            throw new AuthenticationException("原始密码错误！");
        }
        PtStudent stu = new PtStudent();
        stu.setStuId(stuId);
        stu.setPassword(DigestUtils.md5DigestAsHex(formdata.getNewPwd().getBytes(StandardCharsets.UTF_8)));
        stu.setStuModified(LocalDateTime.now());
        return ptStudentMapper.updateByPrimaryKeySelective(stu) == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteStudent(String stuId) {
        ptScoreMapper.deleteByStuId(stuId);
        return ptStudentMapper.deleteByPrimaryKey(stuId) == 1;
    }

    public boolean resetPwd(String stuId) {
        PtStudent ptStudent = PtStudent.builder().stuId(stuId).password(DigestUtils.md5DigestAsHex(DEFAULT_PWD.getBytes(StandardCharsets.UTF_8))).stuModified(LocalDateTime.now()).build();
        return ptStudentMapper.updateByPrimaryKeySelective(ptStudent) == 1;
    }

    public Resource export(String stuId) {
        PtStudent student = ptStudentMapper.selectByPrimaryKey(stuId);
        PtClass ptClass = ptClassMapper.selectByPrimaryKey(student.getClsCode());
        Map<String, Object> map = new HashMap<>();
        map.put("stuId", student.getStuId());
        map.put("stuName", student.getStuName());
        map.put("stuGender", student.getStuGender().getGender());
        map.put("clsName", ptClass.getClsName());
        map.put("stuBirth", student.getStuBirth());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ClassPathResource resource = new ClassPathResource("pdf/Blank_A4.jasper");
            InputStream inputStream = resource.getInputStream();
            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, map, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (IOException | JRException e) {
            e.printStackTrace();
        }
        return new ByteArrayResource(outputStream.toByteArray());
    }
}
