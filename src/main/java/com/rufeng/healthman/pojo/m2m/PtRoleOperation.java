package com.rufeng.healthman.pojo.m2m;

import com.rufeng.healthman.pojo.ptdo.PtOperation;
import lombok.Data;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-05-06 0:25
 * @package com.rufeng.healthman.pojo.m2m
 * @description 角色权限
 */
@Data
public class PtRoleOperation {
    private Long roleId;
    private List<PtOperation> operations;
}
