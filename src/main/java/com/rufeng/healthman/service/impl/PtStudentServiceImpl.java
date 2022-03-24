package com.rufeng.healthman.service.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.JwtTokenUtil;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.enums.UserTypeEnum;
import com.rufeng.healthman.mapper.PtStudentMapper;
import com.rufeng.healthman.pojo.DO.PtStudent;
import com.rufeng.healthman.pojo.DTO.ptadmin.UserIdRoleTypeAuthentication;
import com.rufeng.healthman.pojo.DTO.ptstu.StudentInfo;
import com.rufeng.healthman.pojo.DTO.ptstu.StudentUserInfo;
import com.rufeng.healthman.pojo.DTO.support.LoginResult;
import com.rufeng.healthman.pojo.DTO.support.UserInfo;
import com.rufeng.healthman.pojo.Query.LoginQuery;
import com.rufeng.healthman.pojo.Query.PtStudentQuery;
import com.rufeng.healthman.pojo.file.PtStudentExcel;
import com.rufeng.healthman.pojo.file.PtStudentExcelListener;
import com.rufeng.healthman.service.PtStudentService;
import com.rufeng.healthman.service.RedisService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-09 22:10
 * @package com.rufeng.healthman.service.impl
 * @description .
 */
@Service
public class PtStudentServiceImpl implements PtStudentService {
    private final PtStudentMapper ptStudentMapper;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;

    public PtStudentServiceImpl(PtStudentMapper ptStudentMapper, PasswordEncoder passwordEncoder, RedisService redisService) {
        this.ptStudentMapper = ptStudentMapper;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
    }

    @Override
    public PtStudent getStudent(String number) {
        Assert.notNull(number, "学号不能为null");
        return ptStudentMapper.getStudent(number);
    }

    @Override
    public LoginResult login(LoginQuery loginQuery) {
        PtStudent student = getStudent(loginQuery.getUserId());
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
        PtStudent stu = new PtStudent();
        stu.setStuLastLogin(LocalDateTime.now());
        stu.setStuId(student.getStuId());
        ptStudentMapper.updateStudentByKey(stu);

        UserInfo info = new StudentUserInfo(student);
        String token = JwtTokenUtil.generateToken(student.getStuId(), student.getStuName());
        return new LoginResult(token, info);
    }

    @Override
    public ApiPage<StudentInfo> pageStudentInfo(Integer page, Integer pageSize, PtStudentQuery query) {
        PageHelper.startPage(page, pageSize);
        return ApiPage.convert(ptStudentMapper.pageStudentInfo(query));
    }

    @Override
    public Resource fileTemplate() {
        PageHelper.startPage(1, 10);
//        Page<PtStudent> students = ptStudentMapper.pageStudentInfo(new PtStudentQuery());
//        List<PtStudentExcel> data = students.stream().map(PtStudentExcel::new).collect(Collectors.toList());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream, PtStudentExcel.class).sheet().doWrite(Collections.emptyList());
        return new ByteArrayResource(outputStream.toByteArray());
    }

    @Override
    public Integer uploadStudent(MultipartFile file) {
        PtStudentExcelListener listener = new PtStudentExcelListener(this);
        try {
            EasyExcel.read(file.getInputStream(), PtStudentExcel.class, listener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listener.getHandledCount();
    }

    @Override
    public Integer addStudent(List<PtStudentExcel> cachedDataList) {
        if (cachedDataList.size() == 0) {
            return 0;
        }
        return ptStudentMapper.insertBatch(cachedDataList);
    }

    @Override
    public StudentUserInfo studentInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String stuId = (String) authentication.getPrincipal();
        PtStudent student = getStudent(stuId);
        return new StudentUserInfo(student);
    }
}
