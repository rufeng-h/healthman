package com.rufeng.healthman.service;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.pojo.DO.PtClass;
import com.rufeng.healthman.pojo.Query.PtClassQuery;
import org.springframework.lang.NonNull;

import java.util.List;

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
     * @param page         当前页
     * @param pageSize     每页条数
     * @param ptClassQuery 查询条件
     * @return page
     */
    ApiPage<PtClass> pageClass(Integer page, Integer pageSize, @NonNull PtClassQuery ptClassQuery);

    /**
     * 查询所有
     *
     * @param query query
     * @return page
     */
    List<PtClass> listClass(@NonNull PtClassQuery query);


    /**
     * 查询指定所有年级
     *
     * @param query 查询条件
     * @return page
     */
    List<Integer> listGrade(@NonNull PtClassQuery query);

    /**
     * 主键查询
     *
     * @param classCode 班级代码
     * @return class
     */
    PtClass getPtClass(String classCode);
}
