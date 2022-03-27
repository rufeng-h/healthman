package com.rufeng.healthman.pojo.DO;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 成绩表
 * </p>
 *
 * @author rufeng
 * @since 2022-03-19
 */
@Data
@Alias("PtScore")
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

    private Integer scoYear;

    /**
     * 测试数据
     */
    private BigDecimal scoData;

    private LocalDateTime scoCreated;

    private LocalDateTime scoModified;
}
