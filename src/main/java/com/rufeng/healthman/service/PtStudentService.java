package com.rufeng.healthman.service;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.JwtTokenUtil;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.enums.UserTypeEnum;
import com.rufeng.healthman.mapper.PtStudentMapper;
import com.rufeng.healthman.pojo.DO.PtClass;
import com.rufeng.healthman.pojo.DO.PtCollege;
import com.rufeng.healthman.pojo.DO.PtMeasurement;
import com.rufeng.healthman.pojo.DO.PtStudent;
import com.rufeng.healthman.pojo.DTO.ptadmin.UserIdRoleTypeAuthentication;
import com.rufeng.healthman.pojo.DTO.ptmeasurement.MeasurementStatus;
import com.rufeng.healthman.pojo.DTO.ptstu.StuMsInfo;
import com.rufeng.healthman.pojo.DTO.ptstu.StudentBaseInfo;
import com.rufeng.healthman.pojo.DTO.ptstu.StudentInfo;
import com.rufeng.healthman.pojo.DTO.ptstu.StudentUserInfo;
import com.rufeng.healthman.pojo.DTO.support.LoginResult;
import com.rufeng.healthman.pojo.DTO.support.UserInfo;
import com.rufeng.healthman.pojo.Query.LoginQuery;
import com.rufeng.healthman.pojo.Query.PtStudentQuery;
import com.rufeng.healthman.pojo.file.PtStudentExcel;
import com.rufeng.healthman.pojo.file.PtStudentExcelListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author rufeng
 * @time 2022-03-09 22:10
 * @package com.rufeng.healthman.service.impl
 * @description .
 */
@Service
public class PtStudentService {
    private final PtStudentMapper ptStudentMapper;
    private final PasswordEncoder passwordEncoder;
    private final PtClassService ptClassService;
    private final RedisService redisService;
    private final PtCollegeService ptCollegeService;
    private PtMesurementService ptMesurementService;

    public PtStudentService(PtStudentMapper ptStudentMapper,
                            PasswordEncoder passwordEncoder,
                            PtClassService ptClassService,
                            RedisService redisService,
                            PtCollegeService ptCollegeService) {
        this.ptStudentMapper = ptStudentMapper;
        this.passwordEncoder = passwordEncoder;
        this.ptClassService = ptClassService;
        this.redisService = redisService;
        this.ptCollegeService = ptCollegeService;
    }

    /**
     * 循环依赖 TODO
     */
    @Autowired
    public void setPtMesurementService(PtMesurementService ptMesurementService) {
        this.ptMesurementService = ptMesurementService;
    }

    public LoginResult login(LoginQuery loginQuery) {
        PtStudent student = ptStudentMapper.selectByPrimaryKey(loginQuery.getUserId());
        if (student == null) {
            throw new UsernameNotFoundException("用户不存在!");
        }
        if (!passwordEncoder.matches(loginQuery.getPassword(), student.getPassword())) {
            throw new BadCredentialsException("密码错误!");
        }
        /* 认证信息 */
        Authentication authentication = new UserIdRoleTypeAuthentication(student.getStuId(),
                student.getStuName(), UserTypeEnum.STUDENT, Collections.emptyList());
        redisService.setObject(student.getStuId(), authentication);

        /* 更新登录时间 */
        PtStudent stu = PtStudent.builder()
                .stuId(student.getStuId())
                .stuLastLogin(new Date()).build();
        ptStudentMapper.updateByPrimaryKeySelective(stu);

        UserInfo info = new StudentUserInfo(student);
        String token = JwtTokenUtil.generateToken(student.getStuId(), student.getStuName());
        return new LoginResult(token, info);
    }


    public ApiPage<StudentInfo> pageStudentInfo(Integer page, Integer pageSize, PtStudentQuery query) {
        PageHelper.startPage(page, pageSize);
        Page<PtStudent> students = ptStudentMapper.pageStudent(query);
        /* 查班级名 */
        List<String> clsCodes = students.stream().map(PtStudent::getClsCode).collect(Collectors.toList());
        List<PtClass> classes = ptClassService.listClass(clsCodes);
        Map<String, PtClass> clsMap = classes.stream().collect(Collectors.toMap(PtClass::getClsCode, c -> c));
        /* 查学院名和代码 */
        List<String> clgCodes = ptCollegeService.getClsCodeFromClasses(classes);
        Map<String, String> clgNameMap = ptCollegeService.mapClgNameByIds(clgCodes);
        /* 组装数据 */
        List<StudentInfo> infos = students.stream().map(s -> {
            PtClass cls = clsMap.get(s.getClsCode());
            String clgCode = cls.getClgCode();
            if (clgCode != null) {
                return new StudentInfo(s, cls.getClsName(), clgCode, clgNameMap.get(clgCode));
            }
            return new StudentInfo(s, cls.getClsName());
        }).collect(Collectors.toList());
        return ApiPage.convert(students, infos);
    }


    public Resource fileTemplate() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream, PtStudentExcel.class).sheet().doWrite(Collections.emptyList());
        return new ByteArrayResource(outputStream.toByteArray());
    }


    public Integer uploadStudent(MultipartFile file) {
        PtStudentExcelListener listener = new PtStudentExcelListener(this);
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

    public StudentUserInfo studentInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String stuId = (String) authentication.getPrincipal();
        PtStudent student = ptStudentMapper.selectByPrimaryKey(stuId);
        return new StudentUserInfo(student);
    }

    public StuMsInfo getStuMsInfo(String stuId) {
        PtStudent student = ptStudentMapper.selectByPrimaryKey(stuId);
        /* 查班级 */
        PtClass ptClass = ptClassService.getPtClass(student.getClsCode());
        /* 查学院 */
        String clgCode = ptClass.getClgCode();
        PtCollege college = null;
        if (clgCode != null) {
            college = ptCollegeService.getCollege(clgCode);
        }
        /* 查体测完成状态 */
        Map<Long, Boolean> msStatusMap = ptStudentMapper.listStuMsStatus(stuId);
        List<PtMeasurement> measurements = ptMesurementService.listMeasurement(new ArrayList<>(msStatusMap.keySet()));
        List<MeasurementStatus> msStatus = measurements.stream().map(m -> new MeasurementStatus(m, msStatusMap.get(m.getMsId()))).collect(Collectors.toList());
        /* 查分数 */
        if (college == null) {
            return new StuMsInfo(student, ptClass.getClsName(), msStatus);
        }
        return new StuMsInfo(student, ptClass.getClsName(), college.getClgCode(), college.getClgName(), msStatus);
    }

    public StudentBaseInfo getStuBaseInfo(String stuId) {
        return ptStudentMapper.getStuBaseInfo(stuId);
    }
}
