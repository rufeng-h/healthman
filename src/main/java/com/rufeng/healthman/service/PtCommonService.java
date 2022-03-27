package com.rufeng.healthman.service;

import com.rufeng.healthman.enums.UserTypeEnum;
import com.rufeng.healthman.pojo.DTO.support.LoginResult;
import com.rufeng.healthman.pojo.DTO.support.UserInfo;
import com.rufeng.healthman.pojo.Query.LoginQuery;
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
public class PtCommonService{
    private final PtAdminService ptAdminService;
    private final PtStudentService ptStudentService;
    private final RedisService redisService;

    public PtCommonService(PtAdminService ptAdminService, PtStudentService ptStudentService, RedisService redisService) {
        this.ptAdminService = ptAdminService;
        this.ptStudentService = ptStudentService;
        this.redisService = redisService;
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
}
