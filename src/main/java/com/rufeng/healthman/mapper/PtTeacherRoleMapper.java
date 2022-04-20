package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.ptdo.PtTeacherRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author rufeng
 * @time 2022-04-20 9:31
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtTeacherRoleMapper {
    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(PtTeacherRole record);

    int insertOrUpdate(PtTeacherRole record);

    int insertOrUpdateSelective(PtTeacherRole record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtTeacherRole record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    PtTeacherRole selectByPrimaryKey(Long id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtTeacherRole record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtTeacherRole record);

    int updateBatch(List<PtTeacherRole> list);

    int updateBatchSelective(List<PtTeacherRole> list);

    int batchInsert(@Param("list") List<PtTeacherRole> list);

    int batchInsertSelective(List<PtTeacherRole> list);

    List<PtTeacherRole> listByTeaId(String teaId);
}