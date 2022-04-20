package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.ptdo.PtAdmin;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author rufeng
 * @time 2022-04-20 13:24
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtAdminMapper {
    /**
     * delete by primary key
     *
     * @param adminId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(String adminId);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(PtAdmin record);

    int insertOrUpdate(PtAdmin record);

    int insertOrUpdateSelective(PtAdmin record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtAdmin record);

    /**
     * select by primary key
     *
     * @param adminId primary key
     * @return object by primary key
     */
    PtAdmin selectByPrimaryKey(String adminId);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtAdmin record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtAdmin record);

    int updateBatch(List<PtAdmin> list);

    int updateBatchSelective(List<PtAdmin> list);

    int batchInsert(@Param("list") List<PtAdmin> list);
}