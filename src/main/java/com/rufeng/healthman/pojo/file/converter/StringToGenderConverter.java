package com.rufeng.healthman.pojo.file.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.rufeng.healthman.enums.GenderEnum;
import com.rufeng.healthman.exceptions.ExcelException;

/**
 * @author rufeng
 * @time 2022-03-20 17:37
 * @package com.rufeng.healthman.pojo.file.converter
 * @description TODO
 */
public class StringToGenderConverter implements Converter<GenderEnum> {
    @Override
    public GenderEnum convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String value = cellData.getStringValue();
        if (value.equals(GenderEnum.F.getGender())) {
            return GenderEnum.F;
        } else if (value.equals(GenderEnum.M.getGender())) {
            return GenderEnum.M;
        }
        throw new ExcelException("未知性别：" + value);
    }

    @Override
    public WriteCellData<?> convertToExcelData(GenderEnum value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return new WriteCellData<>(value.getGender());
    }
}
