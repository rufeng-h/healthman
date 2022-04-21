package com.rufeng.healthman.service;

import com.rufeng.healthman.common.util.JwtTokenUtils;
import com.rufeng.healthman.common.util.StringUtils;
import com.rufeng.healthman.enums.UserTypeEnum;
import com.rufeng.healthman.exceptions.AuthenticationException;
import com.rufeng.healthman.mapper.PtAdminMapper;
import com.rufeng.healthman.pojo.data.LoginFormdata;
import com.rufeng.healthman.pojo.data.PtUserFormdata;
import com.rufeng.healthman.pojo.data.UpdatePwdFormdata;
import com.rufeng.healthman.pojo.dto.ptadmin.PtAdminInfo;
import com.rufeng.healthman.pojo.dto.support.LoginResult;
import com.rufeng.healthman.pojo.ptdo.PtAdmin;
import com.rufeng.healthman.security.authentication.Authentication;
import com.rufeng.healthman.security.authentication.AuthenticationImpl;
import com.rufeng.healthman.security.support.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashSet;

import static com.rufeng.healthman.security.authority.Authority.DEFAULT_ADMIN_AUTHORITIES;

/**
 * @author rufeng
 * @time 2022-04-19 23:02
 * @package com.rufeng.healthman.service
 * @description TODO
 */
@Service
@Slf4j
public class PtAdminService {
    private final PtAdminMapper ptAdminMapper;
    private final RedisService redisService;
    private final FileService fileService;

    public PtAdminService(PtAdminMapper ptAdminMapper, RedisService redisService, FileService fileService) {
        this.ptAdminMapper = ptAdminMapper;
        this.redisService = redisService;
        this.fileService = fileService;
    }

    @Transactional(rollbackFor = Exception.class)
    public LoginResult login(LoginFormdata loginFormdata) {
        String adminId = loginFormdata.getUserId();
        PtAdmin admin = ptAdminMapper.selectByPrimaryKey(adminId);
        if (admin == null) {
            throw new AuthenticationException("管理员不存在！");
        }
        if (!StringUtils.pwdEquals(loginFormdata.getPassword(), admin.getPassword())) {
            throw new AuthenticationException("密码错误！");
        }
        ptAdminMapper.updateByPrimaryKeySelective(
                PtAdmin.builder()
                        .adminId(adminId)
                        .adminLastLogin(LocalDateTime.now()).build());
        PtAdminInfo info = new PtAdminInfo(admin, new HashSet<>(DEFAULT_ADMIN_AUTHORITIES));
        Authentication authentication = new AuthenticationImpl(info);
        redisService.setObject(UserInfo.userKey(UserTypeEnum.ADMIN, adminId), authentication);
        String token = JwtTokenUtils.generateToken(adminId, UserTypeEnum.ADMIN);
        return new LoginResult(token, info);
    }

    public boolean updateAdmin(String adminId, PtUserFormdata formdata) {
        PtAdmin admin = ptAdminMapper.selectByPrimaryKey(adminId);
        PtAdmin ptAdmin = PtAdmin.builder()
                .adminId(adminId)
                .adminDesp(formdata.getDesp())
                .adminModified(LocalDateTime.now())
                .avatar(formdata.getAvatar())
                .phone(formdata.getPhone())
                .email(formdata.getEmail())
                .build();
        if (ptAdminMapper.updateByPrimaryKeySelective(ptAdmin) != 1) {
            return false;
        }
        if (!fileService.remove(admin.getAvatar())) {
            log.warn("删除头像失败！");
        }
        return true;
    }

    public boolean updatePwd(String adminId, UpdatePwdFormdata formdata) {
        PtAdmin admin = ptAdminMapper.selectByPrimaryKey(adminId);
        if (!StringUtils.pwdEquals(formdata.getOldPwd(), admin.getPassword())) {
            throw new AuthenticationException("原始密码错误！");
        }
        PtAdmin ptAdmin = new PtAdmin();
        ptAdmin.setAdminId(adminId);
        ptAdmin.setPassword(DigestUtils.md5DigestAsHex(formdata.getNewPwd().getBytes(StandardCharsets.UTF_8)));
        ptAdmin.setAdminModified(LocalDateTime.now());
        return ptAdminMapper.updateByPrimaryKeySelective(ptAdmin) == 1;
    }
}
