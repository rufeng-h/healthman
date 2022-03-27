package com.rufeng.healthman.service;

import com.rufeng.healthman.mapper.PtRoleMapper;
import com.rufeng.healthman.pojo.DO.PtRole;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-16 9:28
 * @package com.rufeng.healthman.service.impl
 * @description TODO
 */
@Service
public class PtRoleService {
    private final PtRoleMapper ptRoleMapper;

    public PtRoleService(PtRoleMapper ptRoleMapper) {
        this.ptRoleMapper = ptRoleMapper;
    }


    public List<PtRole> listRole(String userId) {
        return ptRoleMapper.listRole(userId);
    }

    public int addRoleSelective(List<PtRole> roles) {
        if (roles.size() == 0) {
            return 0;
        }
        return ptRoleMapper.batchInsertSelective(roles);
    }
}
