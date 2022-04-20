package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.ptdo.PtUrlResource;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author rufeng
 * @time 2022-04-19 22:58
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtUrlResourceMapper {
    /**
     * delete by primary key
     * @param resId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long resId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(PtUrlResource record);

    int insertOrUpdate(PtUrlResource record);

    int insertOrUpdateSelective(PtUrlResource record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtUrlResource record);

    /**
     * select by primary key
     * @param resId primary key
     * @return object by primary key
     */
    PtUrlResource selectByPrimaryKey(Long resId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtUrlResource record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtUrlResource record);

    int updateBatch(List<PtUrlResource> list);

    int updateBatchSelective(List<PtUrlResource> list);

    int batchInsert(@Param("list") List<PtUrlResource> list);
}