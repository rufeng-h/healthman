package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.DO.PtScore;
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
    Integer insertBatch(@Param("items") List<PtScore> dataList);
}