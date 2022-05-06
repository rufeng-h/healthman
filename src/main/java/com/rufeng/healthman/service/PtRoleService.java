package com.rufeng.healthman.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.mapper.PtRoleMapper;
import com.rufeng.healthman.mapper.PtRoleOperMapper;
import com.rufeng.healthman.pojo.data.PtRoleFormdata;
import com.rufeng.healthman.security.support.RoleInfo;
import com.rufeng.healthman.pojo.m2m.PtRoleOperation;
import com.rufeng.healthman.pojo.ptdo.PtOperation;
import com.rufeng.healthman.pojo.ptdo.PtRole;
import com.rufeng.healthman.pojo.ptdo.PtRoleOper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
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

    public ApiPage<RoleInfo> page(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        Page<PtRole> roles = ptRoleMapper.page();
        if (roles.isEmpty()) {
            return ApiPage.empty(roles);
        }
        List<Long> roleIds = roles.stream().map(PtRole::getRoleId).collect(Collectors.toList());
        List<PtRoleOperation> roleOperations = ptRoleOperMapper.listRoleOperationByRoleIds(roleIds);
        Map<Long, List<PtOperation>> map = roleOperations.stream().collect(Collectors.toMap(PtRoleOperation::getRoleId, PtRoleOperation::getOperations));
        List<RoleInfo> res = roles.stream().map(r -> new RoleInfo(r, map.get(r.getRoleId()))).collect(Collectors.toList());
        return ApiPage.convert(roles, res);
    }

    /**
     * @param formdata opeIds不为空
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addRole(PtRoleFormdata formdata) {
        PtRole role = PtRole.builder()
                .roleDesp(formdata.getRoleDesp())
                .roleName(formdata.getRoleName()).build();
        ptRoleMapper.insertSelective(role);
        Long roleId = role.getRoleId();
        List<PtRoleOper> roleOpers = formdata.getOperIds().stream().map(operId ->
                PtRoleOper.builder()
                        .roleId(roleId)
                        .created(LocalDateTime.now())
                        .operId(operId).build()).collect(Collectors.toList());
        return ptRoleOperMapper.batchInsert(roleOpers) == roleOpers.size();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(PtRoleFormdata formdata) {
        long roleId = formdata.getRoleId();
        List<PtRoleOper> raw = ptRoleOperMapper.listByRoleId(roleId);
        Set<PtRoleOper> cur = formdata.getOperIds().stream().map(
                operId -> PtRoleOper.builder()
                        .roleId(roleId)
                        .operId(operId)
                        .created(LocalDateTime.now())
                        .build()).collect(Collectors.toSet());
        Set<PtRoleOper> prev = new HashSet<>(raw);
        prev.removeAll(cur);
        if (prev.size() > 0) {
            ptRoleOperMapper.deleteByIds(prev.stream().map(PtRoleOper::getId).collect(Collectors.toList()));
        }
        prev = new HashSet<>(raw);
        cur.removeAll(prev);
        if (cur.size() > 0) {
            ptRoleOperMapper.batchInsert(new ArrayList<>(cur));
        }
        PtRole role = PtRole.builder()
                .roleId(roleId)
                .roleDesp(formdata.getRoleDesp())
                .status(formdata.getStatus())
                .roleName(formdata.getRoleName())
                .build();
        return ptRoleMapper.updateByPrimaryKeySelective(role) == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRole(Long roleId) {
        ptRoleOperMapper.deleteByRoleId(roleId);
        return ptRoleMapper.deleteByPrimaryKey(roleId) == 1;
    }
}
