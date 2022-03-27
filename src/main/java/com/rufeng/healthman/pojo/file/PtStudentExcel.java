package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.annotation.ExcelProperty;
import com.rufeng.healthman.enums.GenderEnum;
import com.rufeng.healthman.pojo.DO.PtStudent;
import com.rufeng.healthman.pojo.file.converter.StringToGenderConverter;
import com.rufeng.healthman.pojo.file.converter.StringToLocalDateConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author rufeng
 * @time 2022-03-20 16:51
 * @package com.rufeng.healthman.pojo.file
 * @description TODO
 */
@Data
@NoArgsConstructor
public class PtStudentExcel {
    @ExcelProperty("学号")
    private String stuId;
    @ExcelProperty("姓名")
    private String stuName;
    @ExcelProperty(value = "出生日期")
    private Date stuBirth;
    @ExcelProperty(value = "性别", converter = StringToGenderConverter.class)
    private GenderEnum stuGender;
    @ExcelProperty("班级代码")
    private String clsCode;

    public PtStudentExcel(PtStudent student) {
        this.stuId = student.getStuId();
        this.clsCode = student.getClsCode();
        this.stuName = student.getStuName();
        this.stuGender = student.getStuGender();
        this.stuBirth = student.getStuBirth();
    }
}
