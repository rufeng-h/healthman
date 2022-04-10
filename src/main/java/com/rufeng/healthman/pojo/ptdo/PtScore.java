package com.rufeng.healthman.pojo.ptdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 成绩表
 *
 * @author rufeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PtScore implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long scoId;
    /**
     * 学生学号
     */
    private String stuId;
    /**
     * 科目id
     */
    private Long subId;
    /**
     * 测试数据
     */
    private BigDecimal scoData;
    private Date scoCreated;
    private Date scoModified;
    /**
     * 测量id
     */
    private Long msId;
    private String scoLevel;
    private Integer score;
}