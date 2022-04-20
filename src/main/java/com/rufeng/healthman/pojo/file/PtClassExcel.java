package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.rufeng.healthman.pojo.file.converter.StringGradeConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rufeng
 * @time 2022-03-20 9:50
 * @package com.rufeng.healthman.pojo.upload
 * @description classExcel
 */
@Data
@NoArgsConstructor
public class PtClassExcel {
    @ExcelProperty("班级代码")
    private String clsCode;
    @ExcelProperty("班级名称")
    private String clsName;
    @ExcelProperty(value = "年级", converter = StringGradeConverter.class)
    private Integer clsEntryGrade;
    @ExcelProperty("学院")
    private String clgName;
    @ExcelProperty("教师工号")
    private String teaId;
    /**
     * 测试时使用
     */
    @ExcelIgnore
    private Integer clsEntryYear;

    /**
     * 可以为null
     */
    @ExcelIgnore
    private String clgCode;
}
