package com.rufeng.healthman.pojo.Query;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.rufeng.healthman.enums.QueryOrderEnum;
import com.rufeng.healthman.pojo.DO.PtStudent;
import lombok.Setter;

/**
 * @author rufeng
 * @time 2022-03-13 17:24
 * @package com.rufeng.healthman.pojo.Query
 * @description TODO
 */
@Setter
public class PtStudentQuery extends PtStudent implements QueryOrder {
    private final Converter<String, String> converter = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE);
    private QueryOrderEnum order;
    private String field;

    @Override
    public QueryOrderEnum getOrder() {
        return this.order;
    }

    @Override
    public String getField() {
        return converter.convert(this.field);
    }
}
