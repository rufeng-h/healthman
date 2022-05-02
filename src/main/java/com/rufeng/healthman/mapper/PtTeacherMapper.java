package com.rufeng.healthman.mapper;

import com.github.pagehelper.Page;
import com.rufeng.healthman.config.support.ReturnMap;
import com.rufeng.healthman.pojo.dto.ptteacher.PtTeacherClgIdentity;
import com.rufeng.healthman.pojo.file.PtTeacherExcel;
import com.rufeng.healthman.pojo.ptdo.PtTeacher;
import com.rufeng.healthman.pojo.query.PtTeacherQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;


/**
 * @author rufeng
 * @time 2022-04-20 13:54
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtTeacherMapper {
    /**
     * delete by primary key
     *
     * @param teaId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(String teaId);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(PtTeacher record);

    int insertOrUpdate(PtTeacher record);

    int insertOrUpdateSelective(PtTeacher record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtTeacher record);

    /**
     * select by primary key
     *
     * @param teaId primary key
     * @return object by primary key
     */
    PtTeacher selectByPrimaryKey(String teaId);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtTeacher record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtTeacher record);

    int updateBatch(List<PtTeacher> list);

    int updateBatchSelective(List<PtTeacher> list);

    int batchInsert(@Param("list") List<PtTeacher> list);

    List<String> listTeaId();

    int batchInsertSelective(List<PtTeacherExcel> list);

    List<PtTeacher> listByIds(List<String> teacherIds);

    /**
     * 查询负责人
     */
    List<PtTeacher> listPrincipal(List<String> clgCodes);

    @ReturnMap
    Map<String, String> mapTeaNameByIds(List<String> teaIds);

    Page<PtTeacher> page(PtTeacherQuery query);

    /**
     * 返回 id, name, clgCode字段
     */
    List<PtTeacher> listTeacherListInfo();

    /**
     * 学院负责人
     *
     * @param clgCode 学院代码
     * @return teacher
     */
    @Nullable
    PtTeacher selectPrincipal(String clgCode);

    List<PtTeacherClgIdentity> listClgIdentity();
}