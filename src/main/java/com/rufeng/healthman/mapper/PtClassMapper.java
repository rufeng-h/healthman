package com.rufeng.healthman.mapper;

import com.github.pagehelper.Page;
import com.rufeng.healthman.config.support.ReturnMap;
import com.rufeng.healthman.pojo.DO.PtClass;
import com.rufeng.healthman.pojo.DTO.ptclass.ClassInfo;
import com.rufeng.healthman.pojo.Query.PtClassQuery;
import com.rufeng.healthman.pojo.file.PtClassExcel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;


/**
 * @author rufeng
 * @time 2022-03-27 20:23
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtClassMapper {
    /**
     * delete by primary key
     *
     * @param clsCode primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(String clsCode);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(PtClass record);

    int insertOrUpdate(PtClass record);

    int insertOrUpdateSelective(PtClass record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtClass record);

    /**
     * select by primary key
     *
     * @param clsCode primary key
     * @return object by primary key
     */
    PtClass selectByPrimaryKey(String clsCode);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtClass record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtClass record);

    int updateBatch(List<PtClass> list);

    int updateBatchSelective(List<PtClass> list);

    int batchInsert(@Param("list") List<PtClass> list);

    /**
     * 分页
     *
     * @param query 查询参数 不为null
     * @return page
     */
    Page<ClassInfo> pageClassInfo(@Param("query") @NonNull PtClassQuery query);

    /**
     * 同上，不分页
     *
     * @param query 查询参数
     * @return page
     */
    List<PtClass> listClass(@Param("query") @NonNull PtClassQuery query);

    /**
     * 查询所有年级
     *
     * @param query query
     * @return page
     */
    List<Integer> listGrade(@Param("query") @NonNull PtClassQuery query);

    /**
     * 插入excel数据
     *
     * @param cachedDataList excel数据
     * @return count
     */
    int batchInsertSelective(@Param("items") List<PtClassExcel> cachedDataList);

    /**
     * 班级人数，返回map
     *
     * @param clsCodes 班级代码
     * @return map
     */
    @ReturnMap
    Map<String, Integer> countStudent(@Param("clsCodes") List<String> clsCodes);

    @ReturnMap
    Map<String, String> mapClsNameByIds(@Param("items") List<String> clss);

    List<PtClass> listClassByClsCodes(List<String> clsCodes);
}