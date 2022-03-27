package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.DO.PtSubgroup;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author rufeng
 * @time 2022-03-27 20:23
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtSubgroupMapper {
    /**
     * delete by primary key
     *
     * @param grpId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long grpId);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(PtSubgroup record);

    int insertOrUpdate(PtSubgroup record);

    int insertOrUpdateSelective(PtSubgroup record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtSubgroup record);

    /**
     * select by primary key
     *
     * @param grpId primary key
     * @return object by primary key
     */
    PtSubgroup selectByPrimaryKey(Long grpId);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtSubgroup record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtSubgroup record);

    int updateBatch(List<PtSubgroup> list);

    int updateBatchSelective(List<PtSubgroup> list);

    int batchInsert(@Param("list") List<PtSubgroup> list);

    /**
     * 查询所有
     *
     * @return list of group
     */
    List<PtSubgroup> listSubGroup();
}