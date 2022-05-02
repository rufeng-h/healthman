package com.rufeng.healthman.pojo.m2m;

import lombok.Data;

import java.util.List;

/**
 * @author rufeng
 */
@Data
public class PtSubGrpShareTeaId {
    private Long grpId;
    private List<String> teaIds;
}