package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.m2m.PtSubGrpShareTeaId;
import com.rufeng.healthman.pojo.ptdo.PtSubGroupShare;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author rufeng
 * @time 2022-05-02 15:47
 * @package com.rufeng.healthman.mapper
 */

@Mapper
public interface PtSubGroupShareMapper {
    /**
     * delete by primary key
     *
     * @param sid primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long sid);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(PtSubGroupShare record);

    int insertOrUpdate(PtSubGroupShare record);

    int insertOrUpdateSelective(PtSubGroupShare record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtSubGroupShare record);

    /**
     * select by primary key
     *
     * @param sid primary key
     * @return object by primary key
     */
    PtSubGroupShare selectByPrimaryKey(Long sid);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtSubGroupShare record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtSubGroupShare record);

    int updateBatch(List<PtSubGroupShare> list);

    int updateBatchSelective(List<PtSubGroupShare> list);

    int batchInsert(@Param("list") List<PtSubGroupShare> list);

    List<PtSubGroupShare> selectByShareTeaId(String teacherId);

    int deleteByIds(List<Long> sids);

    int batchInsertSelective(List<PtSubGroupShare> ptSubGroupShares);

    List<PtSubGrpShareTeaId> listSharedTeaIds(List<Long> grpIds);
}