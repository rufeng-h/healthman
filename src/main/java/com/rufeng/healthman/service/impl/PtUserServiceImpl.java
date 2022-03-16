package com.rufeng.healthman.service.impl;

import com.rufeng.healthman.common.JwtTokenUtil;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.enums.RoleTypeEnum;
import com.rufeng.healthman.enums.UserTypeEnum;
import com.rufeng.healthman.mapper.PtUserMapper;
import com.rufeng.healthman.pojo.DO.*;
import com.rufeng.healthman.pojo.DTO.ptuser.LoginResult;
import com.rufeng.healthman.pojo.DTO.ptuser.Role;
import com.rufeng.healthman.pojo.DTO.ptuser.UserInfo;
import com.rufeng.healthman.pojo.Query.LoginQuery;
import com.rufeng.healthman.pojo.Query.PtUserQuery;
import com.rufeng.healthman.service.*;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    private final PtRoleService ptRoleService;
    private final PtCollegeService ptCollegeService;
    private final PtClassService ptClassService;

    public PtUserServiceImpl(PtUserMapper ptUserMapper, PtStudentService ptStudentService, PasswordEncoder passwordEncoder, RedisService redisService, PtRoleService ptRoleService, PtCollegeService ptCollegeService, PtClassService ptClassService) {
        this.ptUserMapper = ptUserMapper;
        this.ptStudentService = ptStudentService;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
        this.ptRoleService = ptRoleService;
        this.ptCollegeService = ptCollegeService;
        this.ptClassService = ptClassService;
    }

    @Override
    public PtUser getUser(Long id) {
        Assert.notNull(id, "userId不能为null");
        return ptUserMapper.getUser(id);
    }

    @Override
    public LoginResult login(LoginQuery loginQuery) {
        UserTypeEnum userTypeEnum = loginQuery.getUserType();
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

    @Override
    public ApiPage<PtUser> pageUser(Integer page, Integer pageSize, PtUserQuery query) {
        return ApiPage.convert(ptUserMapper.pageUser(query));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInfo addUser(UserAddData data) {
        RoleTypeEnum roleType = data.getRoleType();
        PtUser user = new PtUser();
        user.setUsername(data.getUsername());
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        user.setDesp(data.getDesp());
        user.setEmail(data.getEmail());
        user.setPhone(data.getPhone());
        ptUserMapper.insertUser(user);

        PtRole ptRole = new PtRole();
        ptRole.setUserId(user.getId());
        PtCollege college;
        PtClass ptClass;
        switch (roleType) {
            case SYSTEM:
                ptRole.setRoleName(roleType.getName());
                break;
            case COLLEGE:
                college = ptCollegeService.getCollege(data.getCollegeId());
                ptRole.setRoleName(college.getName());
                ptRole.setValue(college.getId().toString());
                break;
            case CLASS:
                ptClass = ptClassService.getPtClass(data.getClassCode());
                assert ptClass.getCollegeId().equals(data.getCollegeId());
                ptRole.setRoleName(ptClass.getCollegeName() + " " + ptClass.getName());
                ptRole.setValue(ptClass.getCollegeId() + ":" + ptClass.getCode());
                break;
            default:
                throw new IllegalArgumentException("参数错误");
        }
        ptRoleService.insertRole(ptRole);

        user.setCreatedTime(LocalDateTime.now());
        return new UserInfo(user, Collections.singletonList(new Role(ptRole.getRoleName(), ptRole.getValue())));
    }

    private LoginResult doLogin(LoginQuery loginQuery) {
        PtUser user = this.getUser(loginQuery.getUserId());
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在!");
        }
        if (!passwordEncoder.matches(loginQuery.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("密码错误!");
        }

        /* 查询角色权限 */
        List<PtRole> roles = ptRoleService.listRole(user.getId());
        List<Role> authorities = roles.stream()
                .map(role -> new Role(role.getRoleName(), role.getValue())).collect(Collectors.toList());

        /* 认证信息 */
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword(), authorities);
        redisService.setObject(REDIS_KEY_PREFIX + ":" + user.getId(), authentication);

        /* 更新上次登录时间 */
        PtUser u = new PtUser();
        u.setId(user.getId());
        u.setLastLoginTime(LocalDateTime.now());
        this.updateUserByKey(u);

        /* 返回结果 */
        UserInfo info = new UserInfo(user, authorities);
        String token = JwtTokenUtil.generateToken(user.getUsername(), user.getId());
        return new LoginResult(token, info);
    }
}
