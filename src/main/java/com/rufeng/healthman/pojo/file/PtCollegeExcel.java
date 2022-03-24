package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.annotation.ExcelProperty;
import com.rufeng.healthman.pojo.DO.PtCollege;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rufeng
 * @time 2022-03-21 10:04
 * @package com.rufeng.healthman.pojo.file
 * @description TODO
 */
@Data
@NoArgsConstructor
public class PtCollegeExcel {
    @ExcelProperty("学院代码")
    private String clgCode;
    @ExcelProperty("学院名称")
    private String clgName;
    @ExcelProperty("负责人")
    private String clgPrincipal;
    @ExcelProperty("办公室")
    private String clgOffice;
    @ExcelProperty("电话")
    private String clgTel;
    @ExcelProperty("主页")
    private String clgHome;

    public PtCollegeExcel(PtCollege college) {
        clgCode = college.getClgCode();
        clgHome = college.getClgHome();
        clgName = college.getClgName();
        clgOffice = college.getClgOffice();
        clgPrincipal = college.getClgPrincipal();
        clgTel = college.getClgTel();
    }
}
