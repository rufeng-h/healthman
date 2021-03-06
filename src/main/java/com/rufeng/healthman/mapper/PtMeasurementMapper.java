package com.rufeng.healthman.mapper;

import com.github.pagehelper.Page;import com.rufeng.healthman.config.support.ReturnMap;import com.rufeng.healthman.pojo.dto.ptmeasurement.MeasurementSubStatus;import com.rufeng.healthman.pojo.ptdo.PtMeasurement;
import java.util.List;import java.util.Map;
import com.rufeng.healthman.pojo.query.PtMeasurementQuery;import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author rufeng
 * @time 2022-04-16 14:17
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
     * ?????????????????????????????????????????????
     */
    List<MeasurementSubStatus> listMsSubStatus(@Param("stuId") String stuId, @Param("list") List<Long> msIds);

    /**
     * ????????????????????????????????????????????????
     *
     * @return 1?????? 0?????????
     */
    @ReturnMap
    Map<Long, Boolean> listStuMsStatus(String stuId);

    Page<PtMeasurement> pageStuMs(String stuId);
}