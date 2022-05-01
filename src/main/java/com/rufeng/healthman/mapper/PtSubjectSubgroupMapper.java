package com.rufeng.healthman.mapper;

import com.rufeng.healthman.config.support.ReturnMap;
import com.rufeng.healthman.pojo.m2m.PtSubGrpSubject;
import com.rufeng.healthman.pojo.ptdo.PtSubjectSubgroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * @author rufeng
 * @time 2022-05-02 1:01
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtSubjectSubgroupMapper {
    /**
     * delete by primary key
     *
     * @param subGrpId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long subGrpId);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(PtSubjectSubgroup record);

    int insertOrUpdate(PtSubjectSubgroup record);

    int insertOrUpdateSelective(PtSubjectSubgroup record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtSubjectSubgroup record);

    /**
     * select by primary key
     *
     * @param subGrpId primary key
     * @return object by primary key
     */
    PtSubjectSubgroup selectByPrimaryKey(Long subGrpId);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtSubjectSubgroup record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtSubjectSubgroup record);

    int updateBatch(List<PtSubjectSubgroup> list);

    int updateBatchSelective(List<PtSubjectSubgroup> list);

    int batchInsert(@Param("list") List<PtSubjectSubgroup> list);

    int batchInsertSelective(@Param("items") List<PtSubjectSubgroup> subjectSubgroups);

    @ReturnMap
    Map<Long, Integer> countSubByGrpIds(List<Long> grpIds);

    List<Long> listSubIdByGrpId(Long grpId);

    List<PtSubGrpSubject> listSubGrpSubject(List<Long> grpIds);

    int deleteByGrpId(Long grpId);

    /**
     * 按ID删
     */
    int deleteByGrpIdAndSubId(@Param("grpId") Long grpId, @Param("subId") Long subId);
}