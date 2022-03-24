package com.rufeng.healthman.pojo.Query;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.rufeng.healthman.enums.GenderEnum;
import com.rufeng.healthman.enums.QueryOrderEnum;
import lombok.Data;

/**
 * @author rufeng
 * @time 2022-03-13 17:24
 * @package com.rufeng.healthman.pojo.Query
 * @description TODO
 */
@Data
public class PtStudentQuery implements QueryOrder {
    private static final Converter<String, String> CONVERTER = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE);
    private QueryOrderEnum order;
    private String field;
    private String stuName;
    private String stuId;
    private String clsCode;
    private GenderEnum stuGender;

    @Override
    public QueryOrderEnum getOrder() {
        return this.order;
    }

    @Override
    public String getField() {
        return CONVERTER.convert(this.field);
    }
}
