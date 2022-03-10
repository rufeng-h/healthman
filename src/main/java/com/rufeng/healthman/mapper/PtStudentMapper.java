package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.DO.PtStudent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

/**
 * @author rufeng
 * @description 针对表【pt_student(学生表)】的数据库操作Mapper
 */
@Mapper
public interface PtStudentMapper {
    /**
     * 主键查
     *
     * @param number 学号
     * @return stu
     */
    PtStudent getStudent(@Param("number") @NonNull Long number);

    /**
     * 按主键更新
     *
     * @param stu student
     * @return 受影响的行数
     */
    Integer updateStudentByKey(@Param("stu") @NonNull PtStudent stu);
}




