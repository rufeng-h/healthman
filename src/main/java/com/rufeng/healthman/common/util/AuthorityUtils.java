package com.rufeng.healthman.common.util;

import com.rufeng.healthman.pojo.ptdo.PtRole;
import com.rufeng.healthman.security.support.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author rufeng
 * @time 2022-03-27 22:49
 * @package com.rufeng.healthman.common
 * @description TODO
 */
public class AuthorityUtils {
    public static final byte ALL_AUTHORITY = 0xf;
    private static final byte INSERT_MASK = 0x8;
    private static final byte DELETE_MASK = 0x4;
    private static final byte UPDATE_MASK = 0x2;
    private static final byte SELECT_MASK = 0x1;

    public static Set<SimpleGrantedAuthority> fromPtRoles(List<PtRole> roles) {
        final Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach((role) -> {
            byte value = role.getRoleValue();
            String target = role.getTarget();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleType()));
            if (target == null) {
                return;
            }
            if ((value & SELECT_MASK) != 0) {
                authorities.add(new SimpleGrantedAuthority(target + ":" + "SELECT"));
            }
            if ((value & UPDATE_MASK) != 0) {
                authorities.add(new SimpleGrantedAuthority(target + ":" + "UPDATE"));
            }
            if ((value & DELETE_MASK) != 0) {
                authorities.add(new SimpleGrantedAuthority(target + ":" + "DELETE"));
            }
            if ((value & INSERT_MASK) != 0) {
                authorities.add(new SimpleGrantedAuthority(target + ":" + "INSERT"));
            }
        });
        return authorities;
    }
}
