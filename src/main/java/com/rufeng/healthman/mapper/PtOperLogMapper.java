package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.ptdo.PtOperLog;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author rufeng
 * @time 2022-04-19 14:18
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtOperLogMapper {
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
    int insert(PtOperLog record);

    int insertOrUpdate(PtOperLog record);

    int insertOrUpdateSelective(PtOperLog record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtOperLog record);

    /**
     * select by primary key
     *
     * @param operId primary key
     * @return object by primary key
     */
    PtOperLog selectByPrimaryKey(Long operId);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtOperLog record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtOperLog record);

    int updateBatch(List<PtOperLog> list);

    int updateBatchSelective(List<PtOperLog> list);

    int batchInsert(@Param("list") List<PtOperLog> list);
}