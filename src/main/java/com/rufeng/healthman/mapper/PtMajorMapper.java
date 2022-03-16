package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.DO.PtMajor;
import com.rufeng.healthman.pojo.Query.PtMajorQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author rufeng
 * @description 针对表【pt_major(专业表)】的数据库操作Mapper
 */
@Mapper
public interface PtMajorMapper {
    /**
     * 查询列表
     *
     * @param query 查询参数
     * @return page
     */
    List<PtMajor> listMajor(@Param("query") @NonNull PtMajorQuery query);
}




