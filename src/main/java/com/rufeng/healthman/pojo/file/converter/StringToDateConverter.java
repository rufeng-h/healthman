package com.rufeng.healthman.pojo.file.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.util.Date;

/**
 * @author rufeng
 * @time 2022-04-16 13:54
 * @package com.rufeng.healthman.pojo.file.converter
 * @description TODO
 */
public class StringToDateConverter implements Converter<Date> {
    @Override
    public Date convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return null;
    }
}
