package com.rufeng.healthman.service.impl;

import com.rufeng.healthman.mapper.PtRoleMapper;
import com.rufeng.healthman.pojo.DO.PtRole;
import com.rufeng.healthman.service.PtRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-16 9:28
 * @package com.rufeng.healthman.service.impl
 * @description TODO
 */
@Service
public class PtRoleServiceImpl implements PtRoleService {
    private final PtRoleMapper ptRoleMapper;

    public PtRoleServiceImpl(PtRoleMapper ptRoleMapper) {
        this.ptRoleMapper = ptRoleMapper;
    }

    @Override
    public List<PtRole> listRole(Long userId) {
        return ptRoleMapper.listRole(userId);
    }

    @Override
    public Integer insertRole(PtRole ptRole) {
        return ptRoleMapper.insertRole(ptRole);
    }
}
