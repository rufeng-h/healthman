package com.rufeng.healthman.mapper;

import com.github.pagehelper.Page;
import com.rufeng.healthman.pojo.ptdo.PtRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author rufeng
 * @time 2022-05-06 0:11
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtRoleMapper {
    /**
     * delete by primary key
     *
     * @param roleId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long roleId);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(PtRole record);

    int insertOrUpdate(PtRole record);

    int insertOrUpdateSelective(PtRole record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtRole record);

    /**
     * select by primary key
     *
     * @param roleId primary key
     * @return object by primary key
     */
    PtRole selectByPrimaryKey(Long roleId);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtRole record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtRole record);

    int updateBatch(List<PtRole> list);

    int updateBatchSelective(List<PtRole> list);

    int batchInsert(@Param("list") List<PtRole> list);

    List<PtRole> listByIds(List<Long> roleIds);

    Page<PtRole> page();
}