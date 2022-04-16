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
 * 科目
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtSubject implements Serializable {
    private Long subId;

    /**
     * 科目名称
     */
    private String subName;

    /**
     * 备注信息
     */
    private String subDesp;

    private LocalDateTime subCreated;

    private LocalDateTime subModified;

    /**
     * 运动能力id
     */
    private Long compId;

    private static final long serialVersionUID = 1L;
}