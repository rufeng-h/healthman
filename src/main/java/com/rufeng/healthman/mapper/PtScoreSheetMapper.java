package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.ptdo.PtScoreSheet;
import com.rufeng.healthman.pojo.dto.ptscoresheet.ScoreSheetKey;
import com.rufeng.healthman.pojo.dto.ptscoresheet.SheetInfo;
import com.rufeng.healthman.pojo.query.PtScoreSheetQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author rufeng
 * @time 2022-03-27 20:23
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtScoreSheetMapper {
    /**
     * delete by primary key
     *
     * @param subjectId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(@Param("subjectId") Long subjectId, @Param("gender") String gender, @Param("grade") Integer grade, @Param("upper") BigDecimal upper, @Param("lower") BigDecimal lower);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(PtScoreSheet record);

    int insertOrUpdate(PtScoreSheet record);

    int insertOrUpdateSelective(PtScoreSheet record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtScoreSheet record);

    /**
     * select by primary key
     *
     * @param subjectId primary key
     * @return object by primary key
     */
    PtScoreSheet selectByPrimaryKey(@Param("subjectId") Long subjectId, @Param("gender") String gender, @Param("grade") Integer grade, @Param("upper") BigDecimal upper, @Param("lower") BigDecimal lower);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtScoreSheet record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtScoreSheet record);

    int updateBatch(List<PtScoreSheet> list);

    int updateBatchSelective(List<PtScoreSheet> list);

    int batchInsert(@Param("list") List<PtScoreSheet> list);

    /**
     * 查询量表
     *
     * @param query query
     * @return list
     */
    List<PtScoreSheet> listScoreSheet(@NonNull @Param("query") PtScoreSheetQuery query);

    /**
     * 批量添加
     *
     * @param sheets records
     * @return count
     */
    Integer batchInsertSelective(@Param("items") List<PtScoreSheet> sheets);

    List<SheetInfo> listSheetInfoBySubIds(List<Long> subIds);

    List<PtScoreSheet> listScoreSheetBySheetKey(ScoreSheetKey sheetKey);
}