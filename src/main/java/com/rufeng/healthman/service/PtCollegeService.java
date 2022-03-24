package com.rufeng.healthman.service;

import com.rufeng.healthman.pojo.DO.PtCollege;
import com.rufeng.healthman.pojo.file.PtCollegeExcel;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-12 10:17
 * @package com.rufeng.healthman.service
 * @description TODO
 */
public interface PtCollegeService {
    /**
     * 查询所有
     *
     * @return page
     */
    List<PtCollege> listCollege();

    /**
     * 根据条件查询学院信息
     *
     * @param clgCode college_id
     * @return info
     */
//    PtCollegeInfo getCollegeInfo(String clgCode);

    /**
     * 主键查询
     *
     * @param clgCode 学院代码
     * @return 学院
     */
    PtCollege getCollege(String clgCode);

    /**
     * excel添加学院
     * @param file file
     * @return count
     */
    Integer uploadCollege(MultipartFile file);

    Integer addCollege(List<PtCollegeExcel> cachedDataList);

    Resource fileTemplate();
}
