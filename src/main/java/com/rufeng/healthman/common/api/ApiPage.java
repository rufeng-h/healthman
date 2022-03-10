package com.rufeng.healthman.common.api;

import com.github.pagehelper.Page;
import lombok.Data;

import java.util.List;

/**
 * @author 黄纯峰
 * @time 2021-11-30 13:54
 * @package com.rufeng.vuemall.common
 * @description 分页对象
 */
@Data
public class ApiPage<T> {
    private Integer current;
    private Long total;
    private Integer pageSize = 10;
    private Integer pages;
    private List<T> items;

    public static <T> ApiPage<T> convert(Page<T> page) {
        ApiPage<T> apiPage = new ApiPage<>();
        apiPage.setItems(page.getResult());
        apiPage.setTotal(page.getTotal());
        apiPage.setPageSize(page.getPageSize());
        apiPage.setPages(page.getPages());
        apiPage.setCurrent(page.getPageNum());
        return apiPage;
    }
}
