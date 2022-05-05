package com.rufeng.healthman.mapper;

import com.github.pagehelper.Page;
import com.rufeng.healthman.pojo.ptdo.PtOperation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author rufeng
 * @time 2022-05-05 20:20
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtOperationMapper {
    /**
     * delete by primary key
     *
     * @param operId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long operId);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(PtOperation record);

    int insertOrUpdate(PtOperation record);

    int insertOrUpdateSelective(PtOperation record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtOperation record);

    /**
     * select by primary key
     *
     * @param operId primary key
     * @return object by primary key
     */
    PtOperation selectByPrimaryKey(Long operId);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtOperation record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtOperation record);

    int updateBatch(List<PtOperation> list);

    int updateBatchSelective(List<PtOperation> list);

    int batchInsert(@Param("list") List<PtOperation> list);


    List<PtOperation> list();

    List<PtOperation> listByIds(List<String> operIds);

    Page<PtOperation> page();
}