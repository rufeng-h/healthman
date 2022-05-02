package com.rufeng.healthman.service;

import com.rufeng.healthman.enums.UserTypeEnum;
import com.rufeng.healthman.exceptions.UnknownException;
import com.rufeng.healthman.pojo.data.PtLoginFormdata;
import com.rufeng.healthman.pojo.data.PtPwdUpdateFormdata;
import com.rufeng.healthman.pojo.data.PtUserFormdata;
import com.rufeng.healthman.pojo.dto.support.LoginResult;
import com.rufeng.healthman.security.authentication.Authentication;
import com.rufeng.healthman.security.context.SecurityContextHolder;
import com.rufeng.healthman.security.support.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author rufeng
 * @time 2022-03-21 15:46
 * @package com.rufeng.healthman.service.impl
 */
@Service
@Slf4j
public class PtCommonService {
    private final RedisService redisService;
    private final PtAdminService ptAdminService;
    private final PtTeacherService ptTeacherService;
    private final PtStudentService ptStudentService;

    public PtCommonService(RedisService redisService,
                           PtAdminService ptAdminService,
                           PtTeacherService ptTeacherService,
                           PtStudentService ptStudentService) {
        this.redisService = redisService;
        this.ptAdminService = ptAdminService;
        this.ptTeacherService = ptTeacherService;
        this.ptStudentService = ptStudentService;
    }

    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            UserInfo userInfo = authentication.getUserInfo();
            String userId = userInfo.getUserId();
            UserTypeEnum userType = userInfo.getUserType();
            String key = UserInfo.userKey(userType, userId);
            redisService.removeObject(key);
            log.debug(userType + userInfo.getUsername() + "登出");
        }
    }

    public boolean isLogin() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    public UserInfo getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assert.notNull(authentication, "未认证！");
        return authentication.getUserInfo();
    }

    public String getCurrentUserId() {
        return getUserInfo().getUserId();
    }

    public LoginResult login(PtLoginFormdata ptLoginFormdata) {
        UserTypeEnum userTypeEnum = ptLoginFormdata.getUserType();
        if (userTypeEnum == UserTypeEnum.ADMIN) {
            return ptAdminService.login(ptLoginFormdata);
        } else if (userTypeEnum == UserTypeEnum.STUDENT) {
            return ptStudentService.login(ptLoginFormdata);
        } else if (userTypeEnum == UserTypeEnum.TEACHER) {
            return ptTeacherService.login(ptLoginFormdata);
        }
        /* 永远不会到这里 */
        throw new UnknownException("未知的用户类型");
    }

    public UserTypeEnum getCurrentUserType() {
        return getUserInfo().getUserType();
    }

    public boolean updatePwd(PtPwdUpdateFormdata formdata) {
        UserInfo userInfo = getUserInfo();
        UserTypeEnum userType = userInfo.getUserType();
        String userId = userInfo.getUserId();
        if (userType == UserTypeEnum.TEACHER) {
            return ptTeacherService.updatePwd(userId, formdata);
        } else if (userType == UserTypeEnum.STUDENT) {
            return ptStudentService.updatePwd(userId, formdata);
        } else if (userType == UserTypeEnum.ADMIN) {
            return ptAdminService.updatePwd(userId, formdata);
        }
        return false;
    }

    public String getCurrentUserName() {
        return getUserInfo().getUsername();
    }

    public boolean updateUser(PtUserFormdata formdata) {
        UserInfo userInfo = getUserInfo();
        UserTypeEnum userType = userInfo.getUserType();
        String userId = userInfo.getUserId();
        if (userType == UserTypeEnum.ADMIN) {
            return ptAdminService.updateAdmin(userId, formdata);
        } else if (userType == UserTypeEnum.TEACHER) {
            return ptTeacherService.updateTeacher(userId, formdata);
        } else if (userType == UserTypeEnum.STUDENT) {
            return ptStudentService.updateStudent(userId, formdata);
        }
        throw new UnknownException("未知异常");
    }

    @Nullable
    public String getCurrentTeacherId() {
        UserInfo info = getUserInfo();
        if (info.getUserType() == UserTypeEnum.TEACHER) {
            return info.getUserId();
        }
        return null;
    }

}
