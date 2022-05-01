package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.rufeng.healthman.enums.GenderEnum;
import com.rufeng.healthman.pojo.file.converter.StringGenderConverter;
import com.rufeng.healthman.pojo.file.converter.StringLocalDateConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author rufeng
 * @time 2022-03-20 16:51
 * @package com.rufeng.healthman.pojo.file
 * @description 学生excel
 */
@Data
public class PtStudentExcel {
    @ExcelProperty("学号")
    private String stuId;
    @ExcelProperty("姓名")
    private String stuName;
    @ExcelProperty(value = "出生日期")
    private LocalDate stuBirth;
    @ExcelProperty(value = "性别")
    private GenderEnum stuGender;
    @ExcelProperty("班级")
    private String clsName;

    @ExcelIgnore
    private String clsCode;
}
