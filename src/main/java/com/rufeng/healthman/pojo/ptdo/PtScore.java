package com.rufeng.healthman.pojo.ptdo;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * 成绩表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtScore implements Serializable {
    private Long scoId;

    private String stuId;

    /**
     * 科目id
     */
    private Long subId;

    /**
     * 测试数据
     */
    private BigDecimal scoData;

    private LocalDateTime scoCreated;

    private LocalDateTime scoModified;

    /**
     * 测量id
     */
    private Long msId;

    private String scoLevel;

    private Integer score;

    private static final long serialVersionUID = 1L;
}