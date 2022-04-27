package com.rufeng.healthman.config;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKeyBuild;
import com.alibaba.excel.converters.DefaultConverterLoader;
import com.rufeng.healthman.pojo.file.converter.NumberLocalDateConverter;
import com.rufeng.healthman.pojo.file.converter.StringBooleanConverter;
import com.rufeng.healthman.pojo.file.converter.StringGenderConverter;
import com.rufeng.healthman.pojo.file.converter.StringLocalDateConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author rufeng
 * @time 2022-04-16 15:25
 * @package com.rufeng.healthman.config
 * @description 添加全局Excel类型转换器
 * Configuration注解仅仅为了虚拟机加载该类
 */
@Configuration
public class EasyExcelConfig {
    public static final Log logger = LogFactory.getLog(EasyExcelConfig.class);

    static {
        logger.info("初始化EasyExcel类型转换器");
        Map<ConverterKeyBuild.ConverterKey, Converter<?>> allConverter = DefaultConverterLoader.loadAllConverter();
        /* yyyy-MM-dd  localdate*/
        StringLocalDateConverter stringLocalDateConverter = new StringLocalDateConverter();
        allConverter.put(ConverterKeyBuild.buildKey(
                stringLocalDateConverter.supportJavaTypeKey(),
                stringLocalDateConverter.supportExcelTypeKey()), stringLocalDateConverter);

        /* 性别 */
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
