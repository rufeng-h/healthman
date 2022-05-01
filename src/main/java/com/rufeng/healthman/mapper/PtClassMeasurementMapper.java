package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.m2m.PtMeasurementClass;
import com.rufeng.healthman.pojo.ptdo.PtClass;
import com.rufeng.healthman.pojo.ptdo.PtClassMeasurement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author rufeng
 * @time 2022-05-02 1:00
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtClassMeasurementMapper {
    /**
     * delete by primary key
     *
     * @param cmsId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long cmsId);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(PtClassMeasurement record);

    int insertOrUpdate(PtClassMeasurement record);

    int insertOrUpdateSelective(PtClassMeasurement record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtClassMeasurement record);

    /**
     * select by primary key
     *
     * @param cmsId primary key
     * @return object by primary key
     */
    PtClassMeasurement selectByPrimaryKey(Long cmsId);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtClassMeasurement record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtClassMeasurement record);

    int updateBatch(List<PtClassMeasurement> list);

    int updateBatchSelective(List<PtClassMeasurement> list);

    int batchInsert(@Param("list") List<PtClassMeasurement> list);

    int batchInsertSelective(List<PtClassMeasurement> list);

    int deleteByMsId(Long msId);

    List<PtClass> listClsMeasurementByMsId(Long msId);

    List<PtMeasurementClass> listClsMeasurementByMsIds(List<Long> msIds);

    int deleteByClsCode(String clsCode);
}