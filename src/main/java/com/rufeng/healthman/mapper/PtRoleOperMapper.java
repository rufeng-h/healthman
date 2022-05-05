package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.m2m.PtRoleOperation;
import com.rufeng.healthman.pojo.ptdo.PtRoleOper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author rufeng
 * @time 2022-04-22 0:25
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtRoleOperMapper {
    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(PtRoleOper record);

    int insertOrUpdate(PtRoleOper record);

    int insertOrUpdateSelective(PtRoleOper record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtRoleOper record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    PtRoleOper selectByPrimaryKey(Long id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtRoleOper record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtRoleOper record);

    int updateBatch(List<PtRoleOper> list);

    int updateBatchSelective(List<PtRoleOper> list);

    int batchInsert(@Param("list") List<PtRoleOper> list);

    List<String> listOperIdByRoleIds(List<Long> roleIds);


    List<PtRoleOperation> listRoleOperByRoleIds(List<Long> roleIds);
}