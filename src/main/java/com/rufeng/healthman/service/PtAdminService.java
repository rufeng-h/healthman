package com.rufeng.healthman.service;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.AuthorityUtil;
import com.rufeng.healthman.common.JwtTokenUtil;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.enums.RoleTypeEnum;
import com.rufeng.healthman.enums.UserTypeEnum;
import com.rufeng.healthman.mapper.PtAdminMapper;
import com.rufeng.healthman.pojo.DO.PtAdmin;
import com.rufeng.healthman.pojo.DO.PtRole;
import com.rufeng.healthman.pojo.DTO.ptadmin.AdminInfo;
import com.rufeng.healthman.pojo.DTO.ptadmin.UserIdRoleTypeAuthentication;
import com.rufeng.healthman.pojo.DTO.support.LoginResult;
import com.rufeng.healthman.pojo.DTO.support.UserInfo;
import com.rufeng.healthman.pojo.Query.LoginQuery;
import com.rufeng.healthman.pojo.Query.PtAdminQuery;
import com.rufeng.healthman.pojo.file.PtAdminExcel;
import com.rufeng.healthman.pojo.file.PtAdminExcelListener;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

import static com.rufeng.healthman.common.AuthorityUtil.ALL_AUTHORITY;

/**
 * @author rufeng
 * @time 2022-03-09 23:03
 * @package com.rufeng.healthman.service.impl
 * @description .
 */
@Service
public class PtAdminService {
    private final PtAdminMapper ptAdminMapper;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final PtRoleService ptRoleService;
    private final PtCollegeService ptCollegeService;
    private final PtClassService ptClassService;

    public PtAdminService(PtAdminMapper ptAdminMapper,
                          PasswordEncoder passwordEncoder,
                          RedisService redisService,
                          PtRoleService ptRoleService,
                          PtCollegeService ptCollegeService, PtClassService ptClassService) {
        this.ptAdminMapper = ptAdminMapper;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
        this.ptRoleService = ptRoleService;
        this.ptCollegeService = ptCollegeService;
        this.ptClassService = ptClassService;
    }


    public ApiPage<AdminInfo> pageAdminInfo(Integer page, Integer pageSize, PtAdminQuery query) {
        PageHelper.startPage(page, pageSize);
        Page<AdminInfo> infos = ptAdminMapper.pageAdminInfo(query);
        return ApiPage.convert(infos);
    }

    @Transactional(rollbackFor = Exception.class)
    public LoginResult login(LoginQuery loginQuery) {
        PtAdmin admin = ptAdminMapper.selectByPrimaryKey(loginQuery.getUserId());
        if (admin == null) {
            throw new UsernameNotFoundException("用户不存在!");
        }
        if (!passwordEncoder.matches(loginQuery.getPassword(), admin.getPassword())) {
            throw new BadCredentialsException("密码错误!");
        }

        /* 查询角色权限 */
        List<PtRole> roles = ptRoleService.listRole(admin.getAdminId());
        Collection<? extends GrantedAuthority> authorities = AuthorityUtil.fromPtRoles(roles);

        /* 认证信息 */
        Authentication authentication = new UserIdRoleTypeAuthentication(admin.getAdminId(),
                admin.getAdminName(), UserTypeEnum.ADMIN, authorities);
        redisService.setObject(admin.getAdminId(), authentication);

        /* 更新上次登录时间 */
        ptAdminMapper.updateByPrimaryKeySelective(PtAdmin
                .builder().adminId(admin.getAdminId())
                .adminLastLogin(new Date())
                .build());

        /* 返回结果 */
        UserInfo info = new AdminInfo(admin, roles);
        String token = JwtTokenUtil.generateToken(admin.getAdminId(), admin.getAdminName());
        return new LoginResult(token, info);
    }


    public AdminInfo adminInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminId = (String) authentication.getPrincipal();
        PtAdmin user = ptAdminMapper.selectByPrimaryKey(adminId);
        List<PtRole> roles = ptRoleService.listRole(user.getAdminId());
        return new AdminInfo(user, roles);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer addAdmin(List<PtAdminExcel> list) {
        if (list.size() == 0) {
            return 0;
        }
        List<PtRole> roles = new ArrayList<>();
        List<PtAdmin> admins = new ArrayList<>();
        for (PtAdminExcel admin : list) {
            String adminId = admin.getAdminId();
            admins.add(PtAdmin.builder().adminId(adminId)
                    .adminDesp(admin.getAdminDesp())
                    .adminName(admin.getAdminName())
                    .email(admin.getEmail())
                    .phone(admin.getPhone()).build());
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
        int count = ptAdminMapper.batchInsertSelective(admins);
        ptRoleService.addRoleSelective(roles);
        return count;
    }

    public Integer uploadAdmin(MultipartFile file) {
        PtAdminExcelListener listener = new PtAdminExcelListener(this, ptCollegeService, ptClassService);
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
}
