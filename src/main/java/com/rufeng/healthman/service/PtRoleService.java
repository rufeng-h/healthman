package com.rufeng.healthman.service;

import com.rufeng.healthman.pojo.DO.PtRole;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-16 9:28
 * @package com.rufeng.healthman.service
 * @description TODO
 */
public interface PtRoleService {
    /**
     * 用户id查询所有权限
     *
     * @param userId userid
     * @return page
     */
    List<PtRole> listRole(String userId);


    Integer insertRole(PtRole ptRole);
}
