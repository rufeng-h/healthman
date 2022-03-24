package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.DO.PtCollege;
import com.rufeng.healthman.pojo.file.PtCollegeExcel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rufeng
 * @description 针对表【pt_college(学院表)】的数据库操作Mapper
 */
@Mapper
public interface PtCollegeMapper {
    /**
     * 查询所有
     *
     * @return page
     */
    List<PtCollege> listCollege();

    /**
     * 主键查
     *
     * @param clgCode 学院代码
     * @return college
     */
    PtCollege getCollege(@Param("clgCode") String clgCode);

    /**
     * excel批量添加
     *
     * @param cachedDataList data
     * @return count
     */
    Integer insertBatch(@Param("items") List<PtCollegeExcel> cachedDataList);
}





