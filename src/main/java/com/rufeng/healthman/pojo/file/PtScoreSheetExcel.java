package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.rufeng.healthman.enums.GenderEnum;
import com.rufeng.healthman.pojo.file.converter.StringGradeConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author rufeng
 * @time 2022-04-10 16:00
 * @package com.rufeng.healthman.pojo.file
 * @description 评分表excel
 */
@Data
public class PtScoreSheetExcel {
    @ExcelProperty(value = "年级", converter = StringGradeConverter.class)
    private Integer grade;
    @ExcelProperty(value = "性别")
    private GenderEnum gender;
    @ExcelProperty("下限")
    private BigDecimal lower;
    @ExcelProperty("上限")
    private BigDecimal upper;
    @ExcelProperty("成绩")
    private Integer score;
    @ExcelProperty("等级")
    private String level;

    @ExcelIgnore
    private Long subId;
}
