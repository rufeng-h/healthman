package com.rufeng.healthman.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.JwtTokenUtil;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.enums.RoleTypeEnum;
import com.rufeng.healthman.enums.UserTypeEnum;
import com.rufeng.healthman.mapper.PtAdminMapper;
import com.rufeng.healthman.pojo.DO.*;
import com.rufeng.healthman.pojo.DTO.ptadmin.AdminInfo;
import com.rufeng.healthman.pojo.DTO.ptadmin.UserIdRoleTypeAuthentication;
import com.rufeng.healthman.pojo.DTO.support.LoginResult;
import com.rufeng.healthman.pojo.DTO.support.UserInfo;
import com.rufeng.healthman.pojo.Query.LoginQuery;
import com.rufeng.healthman.pojo.Query.PtAdminQuery;
import com.rufeng.healthman.pojo.data.PtAdminFormdata;
import com.rufeng.healthman.service.*;
import org.springframework.aop.framework.AopContext;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rufeng
 * @time 2022-03-09 23:03
 * @package com.rufeng.healthman.service.impl
 * @description .
 */
@Service
public class PtAdminServiceImpl implements PtAdminService {
    private final PtAdminMapper ptAdminMapper;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final PtRoleService ptRoleService;
    private final PtCollegeService ptCollegeService;
    private final PtClassService ptClassService;

    public PtAdminServiceImpl(PtAdminMapper ptAdminMapper,
                              PasswordEncoder passwordEncoder,
                              RedisService redisService,
                              PtRoleService ptRoleService,
                              PtCollegeService ptCollegeService,
                              PtClassService ptClassService) {
        this.ptAdminMapper = ptAdminMapper;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
        this.ptRoleService = ptRoleService;
        this.ptCollegeService = ptCollegeService;
        this.ptClassService = ptClassService;
    }

    @Override
    public PtAdmin getAdmin(String adminId) {
        Assert.notNull(adminId, "userId不能为null");
        return ptAdminMapper.getAdmin(adminId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAdminByKey(@NonNull PtAdmin user) {
        Assert.notNull(user.getAdminId(), "userId不能为null!");
        ptAdminMapper.updateAdminByKey(user);
    }

    @Override
    public ApiPage<AdminInfo> pageAdminInfo(Integer page, Integer pageSize, PtAdminQuery query) {
        PageHelper.startPage(page, pageSize);
        Page<AdminInfo> infos = ptAdminMapper.pageAdminInfo(query);
        return ApiPage.convert(infos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInfo addAdmin(PtAdminFormdata data) {
        RoleTypeEnum roleType = data.getRoleType();
        PtAdmin user = new PtAdmin();
        user.setAdminName(data.getUsername());
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        user.setAdminDesp(data.getDesp());
        user.setEmail(data.getEmail());
        user.setPhone(data.getPhone());
        ptAdminMapper.insertAdmin(user);

        PtRole ptRole = new PtRole();
        ptRole.setAdminId(user.getAdminId());
        PtCollege college;
        PtClass ptClass;
        switch (roleType) {
            case SYSTEM:
                ptRole.setRoleName(roleType.getValue());
//                ptRole.setValue("0");
                break;
            case COLLEGE:
                college = ptCollegeService.getCollege(data.getClgCode());
                ptRole.setRoleName(college.getClgName());
//                ptRole.setValue(college.getId().toString());
                break;
            case CLASS:
                ptClass = ptClassService.getPtClass(data.getClassCode());
                assert ptClass.getClgCode().equals(data.getClgCode());
//                ptRole.setRoleName(ptClass.getCollegeName() + " " + ptClass.getClsName());
//                ptRole.setValue(ptClass.getClgCode() + ":" + ptClass.getClsCode());
                break;
            default:
                throw new IllegalArgumentException("参数错误");
        }
        ptRoleService.insertRole(ptRole);

        user.setAdminCreated(LocalDateTime.now());
        return new AdminInfo(user, Collections.singletonList(ptRole));
    }

    @Override
    public LoginResult login(LoginQuery loginQuery) {
        PtAdmin admin = this.getAdmin(loginQuery.getUserId());
        if (admin == null) {
            throw new UsernameNotFoundException("用户不存在!");
        }
        if (!passwordEncoder.matches(loginQuery.getPassword(), admin.getPassword())) {
            throw new BadCredentialsException("密码错误!");
        }

        /* 查询角色权限 */
        List<PtRole> roles = ptRoleService.listRole(admin.getAdminId());
        List<GrantedAuthority> authorities = roles.stream().map(PtRole::getRoleValue)
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        /* 认证信息 */
        Authentication authentication = new UserIdRoleTypeAuthentication(admin.getAdminId(),
                admin.getAdminName(), UserTypeEnum.ADMIN, authorities);
        redisService.setObject(admin.getAdminId(), authentication);

        /* 更新上次登录时间 */
        PtAdmin u = new PtAdmin();
        u.setAdminId(admin.getAdminId());
        u.setAdminLastLogin(LocalDateTime.now());
        PtAdminService service = (PtAdminService) AopContext.currentProxy();
        service.updateAdminByKey(u);

        /* 返回结果 */
        UserInfo info = new AdminInfo(admin, roles);
        String token = JwtTokenUtil.generateToken(admin.getAdminId(), admin.getAdminName());
        return new LoginResult(token, info);
    }

    @Override
    public AdminInfo adminInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminId = (String) authentication.getPrincipal();
        PtAdmin user = getAdmin(adminId);
        List<PtRole> roles = ptRoleService.listRole(user.getAdminId());
        return new AdminInfo(user, roles);
    }
}
