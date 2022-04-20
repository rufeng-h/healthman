package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.rufeng.healthman.enums.GenderEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author rufeng
 * @time 2022-03-27 23:15
 * @package com.rufeng.healthman.pojo.file
 * @description adminExcel
 */
@Data
@NoArgsConstructor
public class PtTeacherExcel {
    @ExcelProperty("姓名")
    private String teaName;
    @ExcelProperty("邮箱")
    private String email;
    @ExcelProperty("手机")
    private String phone;
    @ExcelProperty("工号")
    private String teaId;
    @ExcelProperty("学院")
    private String clgName;
    @ExcelProperty("负责人")
    private Boolean principal = false;
    @ExcelProperty("性别")
    private GenderEnum teaGender;
    @ExcelProperty("出生日期")
    private LocalDate teaBirth;
    @ExcelIgnore
    private String clgCode;
}
