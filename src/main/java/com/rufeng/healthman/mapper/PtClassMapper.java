package com.rufeng.healthman.mapper;

import com.github.pagehelper.Page;
import com.rufeng.healthman.pojo.DO.PtClass;
import com.rufeng.healthman.pojo.Query.PtClassQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author rufeng
 * @description 针对表【pt_class(班级表)】的数据库操作Mapper
 */
@Mapper
public interface PtClassMapper {
    /**
     * 分页
     *
     * @param query 查询参数 不为null
     * @return page
     */
    Page<PtClass> pageClass(@Param("query") @NonNull PtClassQuery query);


    /**
     * 同上，不分页
     *
     * @param query 查询参数
     * @return page
     */
    List<PtClass> listClass(@Param("query") @NonNull PtClassQuery query);


    /**
     * 查询所有年级
     *
     * @param query query
     * @return page
     */
    List<Integer> listGrade(@Param("query") @NonNull PtClassQuery query);

    /**
     * 主键查
     * @param classCode code
     * @return class
     */
    PtClass getPtClass(@Param("code") String classCode);
}




