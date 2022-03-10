package com.rufeng.healthman.service.impl;

import com.rufeng.healthman.common.JwtTokenUtil;
import com.rufeng.healthman.enums.UserTypeEnum;
import com.rufeng.healthman.mapper.PtUserMapper;
import com.rufeng.healthman.pojo.BO.LoginResult;
import com.rufeng.healthman.pojo.BO.support.UserInfo;
import com.rufeng.healthman.pojo.DO.PtUser;
import com.rufeng.healthman.pojo.Query.LoginQuery;
import com.rufeng.healthman.service.PtStudentService;
import com.rufeng.healthman.service.PtUserService;
import com.rufeng.healthman.service.RedisService;
import org.springframework.lang.NonNull;
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
 * @time 2022-03-09 23:03
 * @package com.rufeng.healthman.service.impl
 * @description .
 */
@Service
public class PtUserServiceImpl implements PtUserService {
    private final PtUserMapper ptUserMapper;
    private final PtStudentService ptStudentService;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;

    public PtUserServiceImpl(PtUserMapper ptUserMapper, PtStudentService ptStudentService, PasswordEncoder passwordEncoder, RedisService redisService) {
        this.ptUserMapper = ptUserMapper;
        this.ptStudentService = ptStudentService;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
    }

    @Override
    public PtUser getUser(Long id) {
        Assert.notNull(id, "userId不能为null");
        return ptUserMapper.getUser(id);
    }

    @Override
    public LoginResult login(LoginQuery loginQuery) {
        UserTypeEnum userTypeEnum = loginQuery.getUserTypeEnum();
        if (userTypeEnum == UserTypeEnum.ADMIN) {
            return doLogin(loginQuery);
        } else if (userTypeEnum == UserTypeEnum.STUDENT) {
            return ptStudentService.login(loginQuery);
        }
        /* 不该到这里 */
        return null;
    }

    @Override
    public Integer updateUserByKey(@NonNull PtUser user) {
        Assert.notNull(user.getId(), "userId不能为null!");
        return ptUserMapper.updateUserByKey(user);
    }

    private LoginResult doLogin(LoginQuery loginQuery) {
        PtUser user = this.getUser(loginQuery.getUserId());
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在!");
        }
        if (!passwordEncoder.matches(loginQuery.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("密码错误!");
        }
        /* 认证信息 */
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword(), Collections.emptyList());
        redisService.setObject(REDIS_KEY_PREFIX + ":" + user.getId(), authentication);

        /* 更新上次登录时间 */
        PtUser u = new PtUser();
        u.setId(user.getId());
        u.setLastLoginTime(LocalDateTime.now());
        this.updateUserByKey(u);

        /* 返回结果 */
        UserInfo info = new UserInfo(user);
        String token = JwtTokenUtil.generateToken(user.getUsername(), user.getId());
        return new LoginResult(token, info);
    }
}
