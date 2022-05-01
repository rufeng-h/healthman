package com.rufeng.healthman.pojo.ptdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 科目与科目组关联
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtSubjectSubgroup implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long subGrpId;
    private Long subId;
    private LocalDateTime subGrpCreated;
    private Long grpId;
    private LocalDateTime subGrpModified;
}