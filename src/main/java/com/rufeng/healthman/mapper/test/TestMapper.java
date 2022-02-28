package com.rufeng.healthman.mapper.test;

import com.github.pagehelper.Page;
import com.rufeng.healthman.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author rufeng
 * @time 2022-01-10 15:30
 * @package com.rufeng.healthman.mapper.test
 * @description 测试
 */

@Mapper
public interface TestMapper {
    /**
     * 接口测试
     * @return .
     */
    @Select("select * from sp_user")
    List<Map<String, Object>> getAll();

    /**
     * 接口测试
     * @return .
     */
    Page<User> selectPage();
}
