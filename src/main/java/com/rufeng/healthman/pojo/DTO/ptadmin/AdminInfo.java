package com.rufeng.healthman.pojo.DTO.ptadmin;

import com.rufeng.healthman.pojo.DO.PtAdmin;
import com.rufeng.healthman.pojo.DTO.support.RoleInfo;
import com.rufeng.healthman.pojo.DTO.support.UserInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-21 16:08
 * @package com.rufeng.healthman.pojo.DTO.ptadmin
 * @description TODO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class AdminInfo extends UserInfo {
    private String email;
    private String phone;
    private String clgName;

    public AdminInfo(PtAdmin admin, String clgName, List<RoleInfo> roles) {
        super(admin, roles);
        this.clgName = clgName;
        this.email = admin.getEmail();
        this.phone = admin.getPhone();
    }
}
