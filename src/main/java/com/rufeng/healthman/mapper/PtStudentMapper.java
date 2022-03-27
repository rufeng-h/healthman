package com.rufeng.healthman.mapper;

import com.github.pagehelper.Page;import com.rufeng.healthman.pojo.DO.PtStudent;
import java.util.List;
import com.rufeng.healthman.pojo.DTO.ptstu.StudentInfo;import com.rufeng.healthman.pojo.Query.PtStudentQuery;import com.rufeng.healthman.pojo.file.PtStudentExcel;import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author rufeng
 * @time 2022-03-27 20:23
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtStudentMapper {
    /**
     * delete by primary key
     *
     * @param stuId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(String stuId);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(PtStudent record);

    int insertOrUpdate(PtStudent record);

    int insertOrUpdateSelective(PtStudent record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtStudent record);

    /**
     * select by primary key
     *
     * @param stuId primary key
     * @return object by primary key
     */
    PtStudent selectByPrimaryKey(String stuId);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtStudent record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtStudent record);

    int updateBatch(List<PtStudent> list);

    int updateBatchSelective(List<PtStudent> list);

    int batchInsert(@Param("list") List<PtStudent> list);

    /**
     * 条件查询
     *
     * @param query 条件
     * @return page
     */
    Page<StudentInfo> pageStudentInfo(@Param("query") PtStudentQuery query);

    /**
     * excel插入数据
     *
     * @param cachedDataList list
     * @return updated count
     */
    Integer batchInsertSelective(@Param("items") List<PtStudentExcel> cachedDataList);
}