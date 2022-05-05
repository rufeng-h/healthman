package com.rufeng.healthman.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.mapper.PtRoleMapper;
import com.rufeng.healthman.mapper.PtRoleOperMapper;
import com.rufeng.healthman.pojo.dto.ptteacher.PtRoleInfo;
import com.rufeng.healthman.pojo.m2m.PtRoleOperation;
import com.rufeng.healthman.pojo.ptdo.PtOperation;
import com.rufeng.healthman.pojo.ptdo.PtRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author rufeng
 * @time 2022-05-06 0:21
 * @package com.rufeng.healthman.service
 * @description 角色
 */
@Service
public class PtRoleService {
    private final PtRoleMapper ptRoleMapper;
    private final PtRoleOperMapper ptRoleOperMapper;

    public PtRoleService(PtRoleMapper ptRoleMapper, PtRoleOperMapper ptRoleOperMapper) {
        this.ptRoleMapper = ptRoleMapper;
        this.ptRoleOperMapper = ptRoleOperMapper;
    }

    public ApiPage<PtRoleInfo> page(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        Page<PtRole> roles = ptRoleMapper.page();
        if (roles.isEmpty()) {
            return ApiPage.empty(roles);
        }
        List<Long> roleIds = roles.stream().map(PtRole::getRoleId).collect(Collectors.toList());
        List<PtRoleOperation> roleOperations = ptRoleOperMapper.listRoleOperByRoleIds(roleIds);
        Map<Long, List<PtOperation>> map = roleOperations.stream().collect(Collectors.toMap(PtRoleOperation::getRoleId, PtRoleOperation::getOperations));
        List<PtRoleInfo> res = roles.stream().map(r -> new PtRoleInfo(r, map.get(r.getRoleId()))).collect(Collectors.toList());
        return ApiPage.convert(roles, res);
    }
}
