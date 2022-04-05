package com.rufeng.healthman.pojo.DO;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author rufeng
 * @time 2022-04-04 13:09
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

    private Date subGrpModified;

    private Date subGrpCreated;

    /**
     * 操作人id
     */
    private String subGrpAdmin;

    private static final long serialVersionUID = 1L;
}