package com.rufeng.healthman.pojo.ptdo;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author rufeng
 * @time 2022-04-16 14:17
 * @package com.rufeng.healthman.pojo.ptdo
 * @description TODO
 */

/**
 * 科目与科目组关联
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtSubjectSubgroup implements Serializable {
    private Long subGrpId;

    private Long subId;

    private Long grpId;

    private LocalDateTime subGrpModified;

    private LocalDateTime subGrpCreated;

    /**
     * 操作人id
     */
    private String subGrpAdmin;

    private static final long serialVersionUID = 1L;
}