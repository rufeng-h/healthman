package com.rufeng.healthman.service;

import com.rufeng.healthman.enums.UserTypeEnum;
import com.rufeng.healthman.pojo.data.UpdatePwdFormdata;
import com.rufeng.healthman.pojo.dto.support.LoginResult;
import com.rufeng.healthman.pojo.query.LoginQuery;
import com.rufeng.healthman.security.authentication.Authentication;
import com.rufeng.healthman.security.context.SecurityContextHolder;
import com.rufeng.healthman.security.support.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author rufeng
 * @time 2022-03-21 15:46
 * @package com.rufeng.healthman.service.impl
 * @description TODO
 */
@Service
public class PtCommonService {
    private final RedisService redisService;
    private PtAdminService ptAdminService;
    private PtStudentService ptStudentService;

    public PtCommonService(RedisService redisService) {
        this.redisService = redisService;
    }

    @Autowired
    public void setPtAdminService(PtAdminService ptAdminService) {
        this.ptAdminService = ptAdminService;
    }

    @Autowired
    public void setPtStudentService(PtStudentService ptStudentService) {
        this.ptStudentService = ptStudentService;
    }

    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String userId = authentication.getUserInfo().getUserId();
            redisService.remove(userId);
        }
    }

    public UserInfo getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assert.notNull(authentication, "未认证！");
        return authentication.getUserInfo();
    }

    public String getCurrentUserId() {
        return getUserInfo().getUserId();
    }

    public LoginResult login(LoginQuery loginQuery) {
        UserTypeEnum userTypeEnum = loginQuery.getUserType();
        if (userTypeEnum == UserTypeEnum.ADMIN) {
            return ptAdminService.login(loginQuery);
        } else if (userTypeEnum == UserTypeEnum.STUDENT) {
            return ptStudentService.login(loginQuery);
        }
        /* 不该到这里 */
        return null;
    }

    public UserTypeEnum getCurrentUserType() {
        return getUserInfo().getUserType();
    }

    public boolean updatePwd(UpdatePwdFormdata formdata) {
        UserTypeEnum userType = this.getCurrentUserType();
        String userId = this.getCurrentUserId();
        if (userType == UserTypeEnum.ADMIN) {
            return ptAdminService.updatePwd(userId, formdata);
        } else if (userType == UserTypeEnum.STUDENT) {
            return ptStudentService.updatePwd(userId, formdata);
        }
        return false;
    }

    public String getCurrentUserName() {
        return getUserInfo().getUsername();
    }
}
