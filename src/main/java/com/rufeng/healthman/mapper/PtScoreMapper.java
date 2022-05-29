package com.rufeng.healthman.mapper;

import com.github.pagehelper.Page;
import com.rufeng.healthman.pojo.dto.ptmeasurement.MeasurementScoreInfo;
import com.rufeng.healthman.pojo.dto.ptstu.PtStudentBaseInfo;
import com.rufeng.healthman.pojo.ptdo.PtScore;
import com.rufeng.healthman.pojo.query.PtScoreQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

import java.util.List;


/**
 * @author rufeng
 * @time 2022-04-16 14:17
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtScoreMapper {
    /**
     * delete by primary key
     *
     * @param scoId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long scoId);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(PtScore record);

    int insertOrUpdate(PtScore record);

    int insertOrUpdateSelective(PtScore record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtScore record);

    /**
     * select by primary key
     *
     * @param scoId primary key
     * @return object by primary key
     */
    PtScore selectByPrimaryKey(Long scoId);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtScore record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtScore record);

    int updateBatch(List<PtScore> list);

    int updateBatchSelective(List<PtScore> list);

    int batchInsert(@Param("list") List<PtScore> list);

    /**
     * delete by primary key
     *
     * @param stuId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(@Param("stuId") String stuId, @Param("subId") Long subId, @Param("scoYear") Integer scoYear);

    /**
     * select by primary key
     *
     * @param stuId primary key
     * @return object by primary key
     */
    PtScore selectByPrimaryKey(@Param("stuId") String stuId, @Param("subId") Long subId, @Param("scoYear") Integer scoYear);

    /**
     * 批量添加
     *
     * @param dataList score
     * @return count
     */
    int batchInsertSelective(@Param("items") List<PtScore> dataList);

    int deleteByMsId(Long msId);

    List<PtScore> listScoreByStuIds(@Param("list") List<String> stuIds, @Param("query") PtScoreQuery query);

    Page<MeasurementScoreInfo> pageStuScoreInfo(PtScoreQuery query);

    /**
     * 按姓名查，需要连表，所以有此查询
     */
    List<PtStudentBaseInfo> listStuBaseInfo(PtScoreQuery query);

    /**
     * 条件查询成绩
     *
     * @param query 姓名不作为查询条件
     * @return page
     */
    Page<PtScore> pageScore(PtScoreQuery query);

    List<PtScore> listScoreByStuIdAndMsIds(@NonNull @Param("stuId") String stuId, @NonNull @Param("list") List<Long> msIds);

    int deleteByStuIds(List<String> stuIds);

    int deleteByStuId(String stuId);

    long count();
}