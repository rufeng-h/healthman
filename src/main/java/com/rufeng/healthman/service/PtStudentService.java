package com.rufeng.healthman.service;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.JwtTokenUtil;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.enums.UserTypeEnum;
import com.rufeng.healthman.mapper.PtStudentMapper;
import com.rufeng.healthman.pojo.DO.PtStudent;
import com.rufeng.healthman.pojo.DTO.ptadmin.UserIdRoleTypeAuthentication;
import com.rufeng.healthman.pojo.DTO.ptstu.StuScoreInfo;
import com.rufeng.healthman.pojo.DTO.ptstu.StudentBaseInfo;
import com.rufeng.healthman.pojo.DTO.ptstu.StudentInfo;
import com.rufeng.healthman.pojo.DTO.ptstu.StudentUserInfo;
import com.rufeng.healthman.pojo.DTO.support.LoginResult;
import com.rufeng.healthman.pojo.DTO.support.UserInfo;
import com.rufeng.healthman.pojo.Query.LoginQuery;
import com.rufeng.healthman.pojo.Query.PtStudentQuery;
import com.rufeng.healthman.pojo.file.PtStudentExcel;
import com.rufeng.healthman.pojo.file.PtStudentExcelListener;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
    private final RedisService redisService;

    public PtStudentService(PtStudentMapper ptStudentMapper, PasswordEncoder passwordEncoder, RedisService redisService) {
        this.ptStudentMapper = ptStudentMapper;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
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
        return ApiPage.convert(ptStudentMapper.pageStudentInfo(query));
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

    public PtStudent getStudent(String stuId) {
        return ptStudentMapper.selectByPrimaryKey(stuId);
    }

    public StudentBaseInfo getStuBaseInfo(String stuId){
        return ptStudentMapper.getStuBaseInfo(stuId);
    }

    public List<StuScoreInfo> listStuScoreInfo(List<String> stuIds) {
        if (stuIds .size() == 0) {
            return Collections.emptyList();
        }
        return ptStudentMapper.listStuScoreInfo(stuIds);
    }
}
