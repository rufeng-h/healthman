package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-27 23:15
 * @package com.rufeng.healthman.pojo.file
 * @description TODO
 */
@Data
@NoArgsConstructor
public class PtAdminExcel {
    @ExcelProperty("姓名")
    private String adminName;
    @ExcelProperty("邮箱")
    private String email;
    @ExcelProperty("备注")
    private String adminDesp;
    @ExcelProperty("手机")
    private String phone;
    @ExcelProperty("工号")
    private String adminId;
    @ExcelProperty("班级权限")
    private String clsRole;
    @ExcelProperty("学院权限")
    private String clgRole;

    @ExcelIgnore
    private List<String> clgCodes = new ArrayList<>();
    @ExcelIgnore
    private List<String> clsCodes = new ArrayList<>();
}
