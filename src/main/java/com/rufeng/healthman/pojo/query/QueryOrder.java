package com.rufeng.healthman.pojo.query;

import com.rufeng.healthman.enums.QueryOrderEnum;

/**
 * @author rufeng
 * @time 2022-03-14 18:25
 * @package com.rufeng.healthman.pojo.Query
 * @description TODO
 */
public interface QueryOrder {

    /**
     * 获取排序方式
     *
     * @return order
     */
    QueryOrderEnum getOrder();

    /**
     * 返回下划线式字段名
     *
     * @return field
     */
    String getField();
}
