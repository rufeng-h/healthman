package com.rufeng.healthman.pojo.file.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author rufeng
 * @time 2022-03-20 17:30
 * @package com.rufeng.healthman.pojo.file.converter
 * @description TODO
 */
public class StringToLocalDateConverter implements Converter<LocalDate> {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return LocalDate.parse(cellData.getStringValue(), formatter);
    }

    @Override
    public WriteCellData<?> convertToExcelData(LocalDate value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData<>(value.format(formatter));
    }
}
