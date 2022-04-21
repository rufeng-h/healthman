package com.rufeng.healthman.service;

import com.rufeng.healthman.mapper.PtRoleOperMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-04-22 16:32
 * @package com.rufeng.healthman.service
 * @description TODO
 */
@Service
public class PtRoleOperService {
    private final PtRoleOperMapper ptRoleOperMapper;

    public PtRoleOperService(PtRoleOperMapper ptRoleOperMapper) {
        this.ptRoleOperMapper = ptRoleOperMapper;
    }


    List<String> listOperIdByRoleIds(List<Long> roleIds) {
        return ptRoleOperMapper.listOperIdByRoleIds(roleIds);
    }
}
