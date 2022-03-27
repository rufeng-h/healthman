package com.rufeng.healthman.pojo.DO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author rufeng
 * @time 2022-03-27 20:23
 * @package com.rufeng.healthman.pojo.DO
 * @description TODO
 */

/**
    * 成绩表
    */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PtScore implements Serializable {
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

    private Integer score;

    private Date scoCreated;

    private Date scoModified;

    /**
    * 测量id
    */
    private Long msId;

    private static final long serialVersionUID = 1L;
}