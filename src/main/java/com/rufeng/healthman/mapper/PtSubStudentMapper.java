package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.dto.ptscoresheet.SubStudent;import com.rufeng.healthman.pojo.ptdo.PtSubStudent;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author rufeng
 * @time 2022-04-16 14:17
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtSubStudentMapper {
    /**
     * delete by primary key
     *
     * @param subId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(@Param("subId") Long subId, @Param("grade") Integer grade, @Param("gender") String gender);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(PtSubStudent record);

    int insertOrUpdate(PtSubStudent record);

    int insertOrUpdateSelective(PtSubStudent record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtSubStudent record);

    /**
     * select by primary key
     *
     * @param subId primary key
     * @return object by primary key
     */
    PtSubStudent selectByPrimaryKey(@Param("subId") Long subId, @Param("grade") Integer grade, @Param("gender") String gender);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtSubStudent record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtSubStudent record);

    int updateBatch(List<PtSubStudent> list);

    int updateBatchSelective(List<PtSubStudent> list);

    int batchInsert(@Param("list") List<PtSubStudent> list);

    List<SubStudent> listSubStudentBySubIds(List<Long> subIds);

    int batchInsertSelective(List<SubStudent> subStudents);

    int deleteBySubId(Long subId);

    List<Long> listSubIdsByGrade(Integer grade);

    List<SubStudent> listSubStudentBySubId(long subId);

    int deleteByIds(List<Long> ids);
}