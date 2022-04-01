package com.rufeng.healthman.mapper;

import com.rufeng.healthman.config.support.ReturnMap;
import com.rufeng.healthman.pojo.DO.PtClassMesurement;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author rufeng
 * @time 2022-03-30 0:19
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtClassMesurementMapper {
    /**
     * delete by primary key
     * @param cmsId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long cmsId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(PtClassMesurement record);

    int insertOrUpdate(PtClassMesurement record);

    int insertOrUpdateSelective(PtClassMesurement record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtClassMesurement record);

    /**
     * select by primary key
     * @param cmsId primary key
     * @return object by primary key
     */
    PtClassMesurement selectByPrimaryKey(Long cmsId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtClassMesurement record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtClassMesurement record);

    int updateBatch(List<PtClassMesurement> list);

    int updateBatchSelective(List<PtClassMesurement> list);

    int batchInsert(@Param("list") List<PtClassMesurement> list);

    int batchInsertSelective(List<PtClassMesurement> list);

    @ReturnMap
    Map<Long, Integer> countClsByMsIds(List<Long> msIds);

    @ReturnMap
    Map<Long, Integer> countStuByMsIds(List<Long> msIds);

    int deleteByMsId(Long msId);
}