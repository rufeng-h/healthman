package com.rufeng.healthman.pojo.file.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.rufeng.healthman.common.util.TranslationUtils;
import com.rufeng.healthman.enums.GenderEnum;

/**
 * @author rufeng
 * @time 2022-03-20 17:37
 * @package com.rufeng.healthman.pojo.file.converter
 * @description 性别转换，全局注册
 */
public class StringGenderConverter implements Converter<GenderEnum> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return GenderEnum.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public GenderEnum convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return TranslationUtils.translateGender(cellData.getStringValue());
    }

    @Override
    public WriteCellData<?> convertToExcelData(GenderEnum value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return new WriteCellData<>(value.getGender());
    }
}
