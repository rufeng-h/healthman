package com.rufeng.healthman.common.api;

import com.github.pagehelper.Page;
import lombok.Data;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public static <U, T> ApiPage<T> convert(Page<U> source, Function<U, T> trans) {
        List<T> items = source.stream().map(trans).collect(Collectors.toList());
        ApiPage<T> page = new ApiPage<>();
        page.setPages(source.getPages());
        page.setPageSize(source.getPageSize());
        page.setTotal(source.getTotal());
        page.setItems(items);
        page.setCurrent(source.getPageNum());
        return page;
    }
}
