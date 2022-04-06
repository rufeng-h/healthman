package com.rufeng.healthman.mapper;

import com.github.pagehelper.Page;
import com.rufeng.healthman.config.support.ReturnMap;
import com.rufeng.healthman.pojo.DO.PtMeasurement;
import com.rufeng.healthman.pojo.DTO.ptmeasurement.MeasurementSubStatus;
import com.rufeng.healthman.pojo.Query.PtMeasurementQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * @author rufeng
 * @time 2022-03-30 0:18
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtMeasurementMapper {
    /**
     * delete by primary key
     *
     * @param msId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long msId);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(PtMeasurement record);

    int insertOrUpdate(PtMeasurement record);

    int insertOrUpdateSelective(PtMeasurement record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtMeasurement record);

    /**
     * select by primary key
     *
     * @param msId primary key
     * @return object by primary key
     */
    PtMeasurement selectByPrimaryKey(Long msId);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtMeasurement record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtMeasurement record);

    int updateBatch(List<PtMeasurement> list);

    int updateBatchSelective(List<PtMeasurement> list);

    int batchInsert(@Param("list") List<PtMeasurement> list);

    Page<PtMeasurement> pageMeasurement(@Param("query") PtMeasurementQuery query);

    @ReturnMap
    Map<Long, Integer> countCompStuByMsIds(List<Long> msIds);

    int countCompStuByMsId(Long msId);

    int countStuByMsId(Long msId);

    List<PtMeasurement> listMeasurement(List<Long> msIds);

    @ReturnMap
    Map<Long, Integer> countStuByMsIds(List<Long> msIds);

    /**
     * 查询每个体测对应科目的完成状态
     */
    List<MeasurementSubStatus> listMsSubStatus(@Param("stuId") String stuId, @Param("list") List<Long> msIds);

    /**
     * 参加的所有体测，包括完成与未完成
     *
     * @return 1完成 0未完成
     */
    @ReturnMap
    Map<Long, Boolean> listStuMsStatus(String stuId);


    Page<PtMeasurement> pageStuMs(String stuId);
}