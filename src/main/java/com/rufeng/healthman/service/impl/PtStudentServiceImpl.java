package com.rufeng.healthman.service.impl;

import com.rufeng.healthman.common.JwtTokenUtil;
import com.rufeng.healthman.mapper.PtStudentMapper;
import com.rufeng.healthman.pojo.BO.LoginResult;
import com.rufeng.healthman.pojo.BO.support.UserInfo;
import com.rufeng.healthman.pojo.DO.PtStudent;
import com.rufeng.healthman.pojo.Query.LoginQuery;
import com.rufeng.healthman.service.PtStudentService;
import com.rufeng.healthman.service.RedisService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Collections;

import static com.rufeng.healthman.config.RedisConfig.REDIS_KEY_PREFIX;

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
    public PtStudent getStudent(Long number) {
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
        Authentication authentication = new UsernamePasswordAuthenticationToken(student.getNumber(), student.getPassword(), Collections.emptyList());
        redisService.setObject(REDIS_KEY_PREFIX + ":" + student.getNumber(), authentication);

        /* 更新登录时间 */
        PtStudent stu = new PtStudent();
        stu.setLastLoginTime(LocalDateTime.now());
        stu.setNumber(student.getNumber());
        ptStudentMapper.updateStudentByKey(stu);

        UserInfo info = new UserInfo(student);
        String token = JwtTokenUtil.generateToken(student.getName(), student.getNumber());
        return new LoginResult(token, info);
    }
}
