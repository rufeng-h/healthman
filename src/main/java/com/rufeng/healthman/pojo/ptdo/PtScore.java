package com.rufeng.healthman.pojo.ptdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 成绩表
 *
 * @author rufeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtScore implements Serializable {
    private static final long serialVersionUID = 1L;
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
}