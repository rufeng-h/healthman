package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.ptdo.PtCompetency;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author rufeng
 * @time 2022-04-09 15:52
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtCompetencyMapper {
    /**
     * delete by primary key
     * @param compId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long compId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(PtCompetency record);

    int insertOrUpdate(PtCompetency record);

    int insertOrUpdateSelective(PtCompetency record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtCompetency record);

    /**
     * select by primary key
     * @param compId primary key
     * @return object by primary key
     */
    PtCompetency selectByPrimaryKey(Long compId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtCompetency record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtCompetency record);

    int updateBatch(List<PtCompetency> list);

    int updateBatchSelective(List<PtCompetency> list);

    int batchInsert(@Param("list") List<PtCompetency> list);

    List<PtCompetency> listCompByIds(List<Long> compIds);

    List<PtCompetency> listComp();
}