package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.DO.PtSubject;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author rufeng
 * @time 2022-03-27 20:23
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
}