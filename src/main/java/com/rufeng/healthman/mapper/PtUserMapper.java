package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.DO.PtUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

/**
 * @author rufeng
 * @description 针对表【pt_user(用户表)】的数据库操作Mapper
 */
@Mapper
public interface PtUserMapper {
    /**
     * 主键查
     *
     * @param id id
     * @return user or null
     */
    PtUser getUser(@NonNull @Param("id") Long id);

    /**
     * 按主键更新
     *
     * @param user user
     * @return 受影响的行数
     */
    Integer updateUserByKey(@NonNull @Param("user") PtUser user);
}




