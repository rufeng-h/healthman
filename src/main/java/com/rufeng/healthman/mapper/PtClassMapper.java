package com.rufeng.healthman.mapper;

import com.github.pagehelper.Page;
import com.rufeng.healthman.domain.PtClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

/**
 * @author rufeng
 * @time 2022-03-06 22:03
 * @package com.rufeng.healthman.mapper
 * @description 班级
 */
@Mapper
public interface PtClassMapper {
    /**
     * .
     *
     * @param ptClass 条件
     * @return list
     */
    @NonNull
    Page<PtClass> pagePtClass(@NonNull @Param("ptClass") PtClass ptClass);
}
