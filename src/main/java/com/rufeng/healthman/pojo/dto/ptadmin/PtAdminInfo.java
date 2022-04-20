package com.rufeng.healthman.pojo.dto.ptadmin;

import com.rufeng.healthman.pojo.ptdo.PtAdmin;
import com.rufeng.healthman.security.support.UserInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author rufeng
 * @time 2022-04-20 10:59
 * @package com.rufeng.healthman.pojo.dto.ptadmin
 * @description TODO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class PtAdminInfo extends UserInfo {
    private String phone;
    private String email;

    public PtAdminInfo(PtAdmin admin) {
        super(admin);
        this.phone = admin.getPhone();
        this.email = admin.getEmail();
    }
}
