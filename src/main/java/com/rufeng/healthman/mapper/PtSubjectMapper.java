package com.rufeng.healthman.mapper;

import com.github.pagehelper.Page;
import com.rufeng.healthman.config.support.ReturnMap;
import com.rufeng.healthman.pojo.ptdo.PtSubject;
import com.rufeng.healthman.pojo.query.PtSubjectQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * @author rufeng
 * @time 2022-04-09 15:13
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtSubjectMapper {
    /**
     * delete by primary key
     *
     * @param subId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long subId);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(PtSubject record);

    int insertOrUpdate(PtSubject record);

    int insertOrUpdateSelective(PtSubject record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtSubject record);

    /**
     * select by primary key
     *
     * @param subId primary key
     * @return object by primary key
     */
    PtSubject selectByPrimaryKey(Long subId);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtSubject record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtSubject record);

    int updateBatch(List<PtSubject> list);

    int updateBatchSelective(List<PtSubject> list);

    int batchInsert(@Param("list") List<PtSubject> list);

    /**
     * 查询所有
     *
     * @return list
     */
    List<PtSubject> listSubject();

    List<PtSubject> listSubjectByIds(List<Long> subIds);

    @ReturnMap
    Map<Long, String> mapSubIdSubNameByIds(List<Long> subIds);

    List<String> listLevels(Long subId);

    Page<PtSubject> pageSubjectByQueryAndSubIds(@Param("query") PtSubjectQuery query, @Param("list") List<Long> gradeSubIds);
}