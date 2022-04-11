package com.rufeng.healthman.pojo.file.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.rufeng.healthman.enums.GradeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rufeng
 * @time 2022-04-10 16:04
 * @package com.rufeng.healthman.pojo.file.converter
 * @description 年级converter
 */
public class GradeConverter implements Converter<Integer> {
    private static final Map<String, Integer> STR2INT_MAP = new HashMap<>(16);
    private static final Map<Integer, String> INT2STR_MAP = new HashMap<>(16);

    static {
        GradeEnum[] gradeEnums = GradeEnum.class.getEnumConstants();
        for (GradeEnum gradeEnum : gradeEnums) {
            STR2INT_MAP.put(gradeEnum.getGrade(), gradeEnum.getValue());
            INT2STR_MAP.put(gradeEnum.getValue(), gradeEnum.getGrade());
        }
    }

    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        Integer grade = STR2INT_MAP.get(cellData.getStringValue());
        if (grade == null) {
            throw new Exception("未知的年级：" + cellData.getStringValue());
        }
        return grade;
    }

    @Override
    public WriteCellData<?> convertToExcelData(Integer value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String grade = INT2STR_MAP.get(value);
        if (grade == null) {
            throw new Exception("年级错误：" + value);
        }
        return new WriteCellData<>(grade);
    }
}
