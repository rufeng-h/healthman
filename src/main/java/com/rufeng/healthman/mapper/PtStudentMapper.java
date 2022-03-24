package com.rufeng.healthman.mapper;

import com.github.pagehelper.Page;
import com.rufeng.healthman.pojo.DO.PtStudent;
import com.rufeng.healthman.pojo.DTO.ptstu.StudentInfo;
import com.rufeng.healthman.pojo.Query.PtStudentQuery;
import com.rufeng.healthman.pojo.file.PtStudentExcel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

import java.util.List;

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
    PtStudent getStudent(@Param("stuId") @NonNull String number);

    /**
     * 按主键更新
     *
     * @param stu student
     * @return 受影响的行数
     */
    Integer updateStudentByKey(@Param("stu") @NonNull PtStudent stu);

    /**
     * 条件查询
     *
     * @param query 条件
     * @return page
     */
    Page<StudentInfo> pageStudentInfo(@Param("query") PtStudentQuery query);

    /**
     * excel插入数据
     *
     * @param cachedDataList list
     * @return updated count
     */
    Integer insertBatch(@Param("items") List<PtStudentExcel> cachedDataList);
}




