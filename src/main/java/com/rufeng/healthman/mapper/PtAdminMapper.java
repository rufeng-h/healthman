package com.rufeng.healthman.mapper;

import com.github.pagehelper.Page;import com.rufeng.healthman.pojo.ptdo.PtAdmin;
import java.util.List;
import com.rufeng.healthman.pojo.dto.ptadmin.AdminInfo;import com.rufeng.healthman.pojo.query.PtAdminQuery;import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;import org.springframework.lang.NonNull;


/**
 * @author rufeng
 * @time 2022-03-28 15:01
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtAdminMapper {
    /**
     * delete by primary key
     *
     * @param adminId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(String adminId);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(PtAdmin record);

    int insertOrUpdate(PtAdmin record);

    int insertOrUpdateSelective(PtAdmin record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtAdmin record);

    /**
     * select by primary key
     *
     * @param adminId primary key
     * @return object by primary key
     */
    PtAdmin selectByPrimaryKey(String adminId);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PtAdmin record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PtAdmin record);

    int updateBatch(List<PtAdmin> list);

    int updateBatchSelective(List<PtAdmin> list);

    int batchInsert(@Param("list") List<PtAdmin> list);

    /**
     * 分页查询管理员信息
     *
     * @param query 查询参数
     * @return page
     */
    Page<AdminInfo> pageAdminInfo(@NonNull @Param("query") PtAdminQuery query);

    int batchInsertSelective(@Param("list") List<PtAdmin> list);

    Page<PtAdmin> pageByQuery(@Param("query") PtAdminQuery query);

    List<PtAdmin> listAdminByIds(List<String> adminIds);
}