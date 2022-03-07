package com.rufeng.healthman.mapper;

import com.rufeng.healthman.domain.PtStudent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author rufeng
 * @time 2022-03-07 16:10
 * @package com.rufeng.healthman.mapper
 * @description stu mapper
 */
@Mapper
public interface PtStudentMapper {
    /**
     * 主键查
     *
     * @param number 学号
     * @return stu
     */
    @Nullable
    PtStudent getPtStudentByNo(@NonNull @Param("number") Long number);
}
