package com.rufeng.healthman.service;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.pojo.DO.PtClass;
import com.rufeng.healthman.pojo.Query.PtClassQuery;

/**
 * @author rufeng
 * @time 2022-03-09 22:09
 * @package com.rufeng.healthman.service
 * @description .
 */
public interface PtClassService {
    /**
     * 分页
     *
     * @param page     当前页
     * @param pageSize 每页条数
     * @param ptClassQuery  查询条件
     * @return page
     */
    ApiPage<PtClass> pageClass(Integer page, Integer pageSize, PtClassQuery ptClassQuery);
}
