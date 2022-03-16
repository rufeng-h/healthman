package com.rufeng.healthman.service;

import com.rufeng.healthman.pojo.DO.PtCollege;
import com.rufeng.healthman.pojo.DTO.ptcollege.PtCollegeInfo;

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
     * @param collegeId college_id
     * @return info
     */
    PtCollegeInfo getCollegeInfo(Long collegeId);

    /**
     * 主键查询
     *
     * @param collegeId 学院id
     * @return 学院
     */
    PtCollege getCollege(Long collegeId);
}
