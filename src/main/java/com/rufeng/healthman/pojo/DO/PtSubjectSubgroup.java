package com.rufeng.healthman.pojo.DO;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author rufeng
 * @time 2022-03-29 18:02
 * @package com.rufeng.healthman.pojo.DO
 * @description TODO
 */

/**
 * 科目与科目组关联
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PtSubjectSubgroup implements Serializable {
    private Long subGrpId;

    private Long subId;

    private Long grpId;

    private Date sbuGrpModified;

    private Date subGrpCreated;

    /**
     * 操作人id
     */
    private String subGrpAdmin;

    private static final long serialVersionUID = 1L;
}