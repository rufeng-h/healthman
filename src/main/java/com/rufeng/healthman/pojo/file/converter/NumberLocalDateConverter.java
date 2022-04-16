package com.rufeng.healthman.pojo.file.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.apache.poi.ss.usermodel.DateUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author rufeng
 * @time 2022-04-16 15:45
 * @package com.rufeng.healthman.pojo.file.converter
 * @description 全局注册
 */
public class NumberLocalDateConverter implements Converter<LocalDate> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return LocalDate.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public LocalDate convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        BigDecimal value = cellData.getNumberValue();
        Date date = DateUtil.getJavaDate(value.doubleValue());
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public WriteCellData<?> convertToExcelData(LocalDate value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        Date date = Date.from(value.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        return new WriteCellData<>(BigDecimal.valueOf(DateUtil.getExcelDate(date)));
    }
}
