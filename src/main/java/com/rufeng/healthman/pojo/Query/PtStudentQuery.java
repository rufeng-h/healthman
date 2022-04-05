package com.rufeng.healthman.pojo.Query;

import com.rufeng.healthman.enums.GenderEnum;
import com.rufeng.healthman.enums.QueryOrderEnum;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * @author rufeng
 * @time 2022-03-13 17:24
 * @package com.rufeng.healthman.pojo.Query
 * @description TODO
 */
@Data
public class PtStudentQuery implements QueryOrder {
    private QueryOrderEnum order;
    @Size(min = 1)
    private String field;
    @Size(min = 1)
    private String stuName;
    @Size(min = 1)
    private String stuId;
    @Size(min = 1)
    private String clsCode;
    private GenderEnum stuGender;

    @Override
    public QueryOrderEnum getOrder() {
        return this.order;
    }

    @Override
    public String getField() {
        return this.field;
    }
}
