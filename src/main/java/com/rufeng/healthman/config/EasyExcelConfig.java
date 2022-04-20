package com.rufeng.healthman.config;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKeyBuild;
import com.alibaba.excel.converters.DefaultConverterLoader;
import com.rufeng.healthman.pojo.file.converter.NumberLocalDateConverter;
import com.rufeng.healthman.pojo.file.converter.StringBooleanConverter;
import com.rufeng.healthman.pojo.file.converter.StringGenderConverter;
import com.rufeng.healthman.pojo.file.converter.StringLocalDateConverter;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author rufeng
 * @time 2022-04-16 15:25
 * @package com.rufeng.healthman.config
 * @description TODO
 */
@Configuration
public class EasyExcelConfig {
    static {
        Map<ConverterKeyBuild.ConverterKey, Converter<?>> allConverter = DefaultConverterLoader.loadAllConverter();
        StringLocalDateConverter stringLocalDateConverter = new StringLocalDateConverter();
        allConverter.put(ConverterKeyBuild.buildKey(
                stringLocalDateConverter.supportJavaTypeKey(),
                stringLocalDateConverter.supportExcelTypeKey()), stringLocalDateConverter);

        StringGenderConverter stringGenderConverter = new StringGenderConverter();
        allConverter.put(ConverterKeyBuild.buildKey(
                stringGenderConverter.supportJavaTypeKey(),
                stringGenderConverter.supportExcelTypeKey()), stringGenderConverter);

        NumberLocalDateConverter numberLocalDateConverter = new NumberLocalDateConverter();
        allConverter.put(ConverterKeyBuild.buildKey(
                        numberLocalDateConverter.supportJavaTypeKey(),
                        numberLocalDateConverter.supportExcelTypeKey()),
                numberLocalDateConverter);

        StringBooleanConverter stringBooleanConverter = new StringBooleanConverter();
        allConverter.put(ConverterKeyBuild.buildKey(
                stringBooleanConverter.supportJavaTypeKey(),
                stringBooleanConverter.supportExcelTypeKey()), stringBooleanConverter);
    }
}
