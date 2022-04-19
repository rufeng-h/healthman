package com.rufeng.healthman.service;

import com.rufeng.healthman.enums.UserTypeEnum;
import com.rufeng.healthman.pojo.data.UpdatePwdFormdata;
import com.rufeng.healthman.pojo.dto.support.LoginResult;
import com.rufeng.healthman.pojo.dto.support.UserInfo;
import com.rufeng.healthman.pojo.query.LoginQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
            String userId = (String) authentication.getPrincipal();
            redisService.remove(userId);
        }
    }

    public UserInfo userInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserTypeEnum userType = (UserTypeEnum) authentication.getDetails();
        return userType == UserTypeEnum.ADMIN ? ptAdminService.adminInfo() : ptStudentService.studentInfo();
    }

    public String getCurrentUserId() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
        return (UserTypeEnum) SecurityContextHolder.getContext().getAuthentication().getDetails();
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
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
