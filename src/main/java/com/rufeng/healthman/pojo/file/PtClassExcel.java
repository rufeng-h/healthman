package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.annotation.ExcelProperty;
import com.rufeng.healthman.pojo.DO.PtClass;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rufeng
 * @time 2022-03-20 9:50
 * @package com.rufeng.healthman.pojo.upload
 * @description TODO
 */
@Data
@NoArgsConstructor
public class PtClassExcel {
    @ExcelProperty("班级代码")
    private String clsCode;
    @ExcelProperty("班级名称")
    private String clsName;
    @ExcelProperty("学院代码")
    private String clgCode;
    @ExcelProperty("年级")
    private Integer clsEntryGrade;
    @ExcelProperty("学院")
    private String clgName;
    /**
     * 测试时使用
     */
    @ExcelProperty("录入年份")
    private Integer clsEntryYear;

    public PtClassExcel(PtClass ptClass) {
        this.clgCode = ptClass.getClgCode();
        this.clsCode = ptClass.getClsCode();
        this.clsEntryGrade = ptClass.getClsEntryGrade();
        this.clsEntryYear = ptClass.getClsEntryYear();
        this.clsName = ptClass.getClsName();
    }
}
