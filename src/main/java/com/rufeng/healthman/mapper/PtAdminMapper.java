package com.rufeng.healthman.mapper;

import com.github.pagehelper.Page;
import com.rufeng.healthman.pojo.DO.PtAdmin;
import com.rufeng.healthman.pojo.DTO.ptadmin.AdminInfo;
import com.rufeng.healthman.pojo.Query.PtAdminQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

/**
 * @author rufeng
 * @description 针对表【pt_user(用户表)】的数据库操作Mapper
 */
@Mapper
public interface PtAdminMapper {
    /**
     * 主键查
     *
     * @param id id
     * @return user or null
     */
    PtAdmin getAdmin(@NonNull @Param("adminId") String id);

    /**
     * 按主键更新
     *
     * @param admin admin
     * @return 受影响的行数
     */
    Integer updateAdminByKey(@NonNull @Param("admin") PtAdmin admin);

    /**
     * 分页查询管理员信息
     *
     * @param query 查询参数
     * @return page
     */
    Page<AdminInfo> pageAdminInfo(@NonNull @Param("query") PtAdminQuery query);


    /**
     * 添加user
     *
     * @param user user obj
     * @return cnt
     */
    Integer insertAdmin(@NonNull @Param("admin") PtAdmin user);
}



