package com.rufeng.healthman.mapper;

import com.github.pagehelper.Page;
import com.rufeng.healthman.config.support.ReturnMap;
import com.rufeng.healthman.pojo.DO.PtClass;
import com.rufeng.healthman.pojo.DTO.ptclass.ClassInfo;
import com.rufeng.healthman.pojo.Query.PtClassQuery;
import com.rufeng.healthman.pojo.file.PtClassExcel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;

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
    Page<ClassInfo> pageClassInfo(@Param("query") @NonNull PtClassQuery query);


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
     *
     * @param classCode code
     * @return class
     */
    PtClass getPtClass(@Param("code") String classCode);

    /**
     * 插入excel数据
     *
     * @param cachedDataList excel数据
     * @return count
     */
    Integer insertBatch(@Param("items") List<PtClassExcel> cachedDataList);

    /**
     * 班级人数，返回map
     *
     * @param clsCodes 班级代码
     * @return map
     */
    @ReturnMap
    Map<String, Integer> countStudent(@Param("clsCodes") List<String> clsCodes);
}




