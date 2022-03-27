package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.DO.PtSubjectSubgroup;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author rufeng
 * @time 2022-03-27 20:30
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
}