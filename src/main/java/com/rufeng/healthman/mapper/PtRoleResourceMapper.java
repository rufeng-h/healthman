package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.ptdo.PtRoleResource;
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
public interface PtRoleResourceMapper {
    /**
     * delete by primary key
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(PtRoleResource record);

    int insertOrUpdate(PtRoleResource record);

    int insertOrUpdateSelective(PtRoleResource record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtRoleResource record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    PtRoleResource selectByPrimaryKey(Long id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtRoleResource record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtRoleResource record);

    int updateBatch(List<PtRoleResource> list);

    int updateBatchSelective(List<PtRoleResource> list);

    int batchInsert(@Param("list") List<PtRoleResource> list);
}