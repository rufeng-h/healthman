package com.rufeng.healthman.mapper;

import com.github.pagehelper.Page;import com.rufeng.healthman.pojo.dto.ptscoresheet.SubStudent;import com.rufeng.healthman.pojo.file.PtScoreSheetExcel;import com.rufeng.healthman.pojo.ptdo.PtScoreSheet;
import java.math.BigDecimal;
import java.util.List;
import com.rufeng.healthman.pojo.query.PtScoreSheetQuery;import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author rufeng
 * @time 2022-04-16 14:17
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtScoreSheetMapper {
    /**
     * delete by primary key
     *
     * @param subId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(@Param("subId") Long subId, @Param("gender") String gender, @Param("grade") Integer grade, @Param("upper") BigDecimal upper, @Param("lower") BigDecimal lower);

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
     * @param subId primary key
     * @return object by primary key
     */
    PtScoreSheet selectByPrimaryKey(@Param("subId") Long subId, @Param("gender") String gender, @Param("grade") Integer grade, @Param("upper") BigDecimal upper, @Param("lower") BigDecimal lower);

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
     * 批量添加
     *
     * @param sheets records
     * @return count
     */
    Integer batchInsertSelective(@Param("items") List<PtScoreSheetExcel> sheets);

    List<PtScoreSheet> listScoreSheetBySubStudent(SubStudent sheetKey);

    Page<PtScoreSheet> pageScoreSheet(PtScoreSheetQuery query);

    List<Long> listSubIdBySubIds(List<Long> subIds);

    int deleteBySubId(Long subId);

    int updateByIdSelective(PtScoreSheet scoreSheet);

    int deleteById(Long id);
}