package com.rufeng.healthman.service;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.util.JwtTokenUtils;
import com.rufeng.healthman.enums.UserTypeEnum;
import com.rufeng.healthman.exceptions.FileException;
import com.rufeng.healthman.mapper.PtStudentMapper;
import com.rufeng.healthman.pojo.data.StudentFormData;
import com.rufeng.healthman.pojo.data.UpdatePwdFormdata;
import com.rufeng.healthman.pojo.dto.ptadmin.UserIdRoleTypeAuthentication;
import com.rufeng.healthman.pojo.dto.ptmeasurement.StuMeasurementInfo;
import com.rufeng.healthman.pojo.dto.ptmeasurement.StuMeasurementStatus;
import com.rufeng.healthman.pojo.dto.ptstu.StudentBaseInfo;
import com.rufeng.healthman.pojo.dto.ptstu.StudentInfo;
import com.rufeng.healthman.pojo.dto.ptstu.StudentUserInfo;
import com.rufeng.healthman.pojo.dto.support.LoginResult;
import com.rufeng.healthman.pojo.dto.support.UserInfo;
import com.rufeng.healthman.pojo.file.PtStudentExcel;
import com.rufeng.healthman.pojo.file.PtStudentExcelListener;
import com.rufeng.healthman.pojo.ptdo.PtClass;
import com.rufeng.healthman.pojo.ptdo.PtCollege;
import com.rufeng.healthman.pojo.ptdo.PtMeasurement;
import com.rufeng.healthman.pojo.ptdo.PtStudent;
import com.rufeng.healthman.pojo.query.LoginQuery;
import com.rufeng.healthman.pojo.query.PtStudentQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
    private final FileService fileService;
    private PtMesurementService ptMesurementService;

    public PtStudentService(PtStudentMapper ptStudentMapper,
                            PasswordEncoder passwordEncoder,
                            PtClassService ptClassService,
                            RedisService redisService,
                            PtCollegeService ptCollegeService, FileService fileService) {
        this.ptStudentMapper = ptStudentMapper;
        this.passwordEncoder = passwordEncoder;
        this.ptClassService = ptClassService;
        this.redisService = redisService;
        this.ptCollegeService = ptCollegeService;
        this.fileService = fileService;
    }

    /**
     * ???????????? TODO
     */
    @Autowired
    public void setPtMesurementService(PtMesurementService ptMesurementService) {
        this.ptMesurementService = ptMesurementService;
    }

    public LoginResult login(LoginQuery loginQuery) {
        PtStudent student = ptStudentMapper.selectByPrimaryKey(loginQuery.getUserId());
        if (student == null) {
            throw new UsernameNotFoundException("???????????????!");
        }
        if (!passwordEncoder.matches(loginQuery.getPassword(), student.getPassword())) {
            throw new BadCredentialsException("????????????!");
        }
        /* ???????????? */
        Authentication authentication = new UserIdRoleTypeAuthentication(student.getStuId(),
                student.getStuName(), UserTypeEnum.STUDENT, Collections.emptyList());
        redisService.setObject(student.getStuId(), authentication);

        /* ?????????????????? */
        PtStudent stu = PtStudent.builder()
                .stuId(student.getStuId())
                .stuLastLogin(LocalDateTime.now()).build();
        ptStudentMapper.updateByPrimaryKeySelective(stu);

        /* ????????? */
        PtClass ptClass = ptClassService.getPtClass(student.getClsCode());
        /* ????????? */
        PtCollege college = ptCollegeService.getCollege(ptClass.getClgCode());
        UserInfo info = new StudentUserInfo(student, ptClass, college);
        String token = JwtTokenUtils.generateToken(student.getStuId(), student.getStuName());
        return new LoginResult(token, info);
    }

    public List<StudentBaseInfo> listStuBaseInfoByMsId(Long msId) {
        return ptStudentMapper.listStuBaseInfoByMsId(msId);
    }

    public ApiPage<StudentInfo> pageStudentInfo(Integer page, Integer pageSize, PtStudentQuery query) {
        PageHelper.startPage(page, pageSize);
        Page<PtStudent> students = ptStudentMapper.pageStudent(query);
        /* ???????????? */
        List<String> clsCodes = students.stream().map(PtStudent::getClsCode).collect(Collectors.toList());
        List<PtClass> classes = ptClassService.listClass(clsCodes);
        Map<String, PtClass> clsMap = classes.stream().collect(Collectors.toMap(PtClass::getClsCode, c -> c));
        /* ????????????????????? */
        List<String> clgCodes = ptCollegeService.getClsCodeFromClasses(classes);
        Map<String, String> clgNameMap = ptCollegeService.mapClgNameByIds(clgCodes);
        /* ???????????? */
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


    public Integer uploadStudent(MultipartFile file, String clsCode) {
        PtStudentExcelListener listener = new PtStudentExcelListener(this, ptClassService, clsCode);
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
        PtClass ptClass = ptClassService.getPtClass(student.getClsCode());
        PtCollege college = ptCollegeService.getCollege(ptClass.getClgCode());
        return new StudentUserInfo(student, ptClass, college);
    }

    public StuMeasurementInfo getStuMsInfo(String stuId) {
        PtStudent student = ptStudentMapper.selectByPrimaryKey(stuId);
        /* ????????? */
        PtClass ptClass = ptClassService.getPtClass(student.getClsCode());
        /* ????????? */
        String clgCode = ptClass.getClgCode();
        PtCollege college = null;
        if (clgCode != null) {
            college = ptCollegeService.getCollege(clgCode);
        }
        /* ????????????????????? */
        Map<Long, Boolean> msStatusMap = ptMesurementService.listStuMsStatus(stuId);
        List<PtMeasurement> measurements = ptMesurementService.listMeasurement(new ArrayList<>(msStatusMap.keySet()));
        List<StuMeasurementStatus> msStatus = measurements.stream().map(m -> new StuMeasurementStatus(m, msStatusMap.get(m.getMsId()))).collect(Collectors.toList());
        /* ????????? */
        if (college == null) {
            return new StuMeasurementInfo(student, ptClass.getClsName(), msStatus);
        }
        return new StuMeasurementInfo(student, ptClass.getClsName(), college.getClgCode(), college.getClgName(), msStatus);
    }

    public List<String> listStuId() {
        return ptStudentMapper.listStuId();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateStudent(StudentFormData formData) {
        PtStudent ptStudent = ptStudentMapper.selectByPrimaryKey(formData.getStuId());
        PtStudent student = new PtStudent();
        student.setStuBirth(formData.getBirth());
        student.setAvatar(formData.getAvatar());
        student.setStuDesp(formData.getDesp());
        student.setStuId(formData.getStuId());
        student.setStuModified(LocalDateTime.now());

        if (ptStudentMapper.updateByPrimaryKeySelective(student) != 1) {
            return false;
        }
        if (fileService.remove(ptStudent.getAvatar())) {
            return true;
        }
        throw new FileException("?????????????????????");

    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updatePwd(String stuId, UpdatePwdFormdata formdata) {
        PtStudent student = ptStudentMapper.selectByPrimaryKey(stuId);
        if (!passwordEncoder.matches(formdata.getOldPwd(), student.getPassword())) {
            throw new BadCredentialsException("?????????????????????");
        }
        PtStudent stu = new PtStudent();
        stu.setStuId(stuId);
        stu.setPassword(passwordEncoder.encode(formdata.getNewPwd()));
        stu.setStuModified(LocalDateTime.now());
        return ptStudentMapper.updateByPrimaryKeySelective(stu) == 1;
    }
}
