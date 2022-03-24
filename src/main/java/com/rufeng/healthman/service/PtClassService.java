package com.rufeng.healthman.service;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.pojo.DO.PtClass;
import com.rufeng.healthman.pojo.DTO.ptclass.ClassInfo;
import com.rufeng.healthman.pojo.Query.PtClassQuery;
import com.rufeng.healthman.pojo.file.PtClassExcel;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-09 22:09
 * @package com.rufeng.healthman.service
 * @description .
 */
public interface PtClassService {
    /**
     * 分页
     *
     * @param page         当前页
     * @param pageSize     每页条数
     * @param ptClassQuery 查询条件
     * @return page
     */
    ApiPage<ClassInfo> pageClassInfo(Integer page, Integer pageSize, @NonNull PtClassQuery ptClassQuery);

    /**
     * 查询所有
     *
     * @param query query
     * @return page
     */
    List<PtClass> listClass(@NonNull PtClassQuery query);


    /**
     * 查询指定所有年级
     *
     * @param query 查询条件
     * @return page
     */
    List<Integer> listGrade(@NonNull PtClassQuery query);

    /**
     * 主键查询
     *
     * @param classCode 班级代码
     * @return class
     */
    PtClass getPtClass(String classCode);

    /**
     * 文件上传班级信息
     *
     * @param file excel
     * @return 成功添加条数
     */
    Integer uploadClass(MultipartFile file);

    /**
     * 从excel添加
     *
     * @param cachedDataList data
     * @return 添加条数
     */
    Integer addClass(List<PtClassExcel> cachedDataList);

    /**
     * 文件模板
     *
     * @return 文件资源
     */
    Resource fileTemplate();
}
