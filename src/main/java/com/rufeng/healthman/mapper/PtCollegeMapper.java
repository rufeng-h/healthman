package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.DO.PtCollege;
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
     * @param collegeId 学院id
     * @return college
     */
    PtCollege getCollege(@Param("collegeId") Long collegeId);
}




