package com.rufeng.healthman.mapper;

import com.rufeng.healthman.config.support.ReturnMap;
import com.rufeng.healthman.pojo.DO.PtCollege;
import com.rufeng.healthman.pojo.file.PtCollegeExcel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * @author rufeng
 * @time 2022-03-27 20:23
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtCollegeMapper {
    /**
     * delete by primary key
     *
     * @param clgCode primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(String clgCode);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(PtCollege record);

    int insertOrUpdate(PtCollege record);

    int insertOrUpdateSelective(PtCollege record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtCollege record);

    /**
     * select by primary key
     *
     * @param clgCode primary key
     * @return object by primary key
     */
    PtCollege selectByPrimaryKey(String clgCode);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtCollege record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtCollege record);

    int updateBatch(List<PtCollege> list);

    int updateBatchSelective(List<PtCollege> list);

    int batchInsert(@Param("list") List<PtCollege> list);

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
    int batchInsertSelective(@Param("items") List<PtCollegeExcel> cachedDataList);

    PtCollege getCollegeByName(String clgName);

    @ReturnMap
    Map<String, String> mapClgNameByIds(@Param("items") List<String> clgs);

    @ReturnMap
    Map<String, String> mapAdminIdClgNameByIds(@Param("items") List<String> collect);
}