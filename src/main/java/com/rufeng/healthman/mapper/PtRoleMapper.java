package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.DO.PtRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rufeng
 * @description 针对表【pt_role(角色表)】的数据库操作Mapper
 */
@Mapper
public interface PtRoleMapper {

    /**
     * 查询用户角色列表
     *
     * @param userId 用户id
     * @return page
     */
    List<PtRole> listRole(String userId);

    /**
     * 插入一条
     *
     * @param ptRole role
     * @return int
     */
    Integer insertRole(@Param("role") PtRole ptRole);
}



