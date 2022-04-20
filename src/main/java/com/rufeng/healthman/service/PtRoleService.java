package com.rufeng.healthman.service;

import com.rufeng.healthman.mapper.PtRoleMapper;
import com.rufeng.healthman.pojo.ptdo.PtRole;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-28 11:10
 * @package com.rufeng.healthman.service
 * @description TODO
 */
@Service
public class PtRoleService {
    private final PtRoleMapper ptRoleMapper;

    public PtRoleService(PtRoleMapper ptRoleMapper) {
        this.ptRoleMapper = ptRoleMapper;
    }

    public List<PtRole> listByIds(List<Long> roleIds) {
        if (roleIds.size() == 0) {
            return Collections.emptyList();
        }
        return ptRoleMapper.listByIds(roleIds);
    }

    public List<PtRole> listRole(String adminId) {
        return ptRoleMapper.listRole(adminId);
    }

    public int batchInsertSelective(List<PtRole> roles) {
        if (roles.size() == 0) {
            return 0;
        }
        return ptRoleMapper.batchInsertSelective(roles);
    }
}
