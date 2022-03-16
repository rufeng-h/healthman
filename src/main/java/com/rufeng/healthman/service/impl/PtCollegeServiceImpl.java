package com.rufeng.healthman.service.impl;

import com.rufeng.healthman.mapper.PtCollegeMapper;
import com.rufeng.healthman.pojo.DO.PtCollege;
import com.rufeng.healthman.pojo.DO.PtMajor;
import com.rufeng.healthman.pojo.DTO.ptcollege.PtCollegeInfo;
import com.rufeng.healthman.pojo.Query.PtClassQuery;
import com.rufeng.healthman.pojo.Query.PtMajorQuery;
import com.rufeng.healthman.service.PtClassService;
import com.rufeng.healthman.service.PtCollegeService;
import com.rufeng.healthman.service.PtMajorService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-12 10:18
 * @package com.rufeng.healthman.service.impl
 * @description TODO
 */
@Service
public class PtCollegeServiceImpl implements PtCollegeService {
    private final PtCollegeMapper ptCollegeMapper;
    private final PtClassService ptClassService;
    private final PtMajorService ptMajorService;

    public PtCollegeServiceImpl(PtCollegeMapper ptCollegeMapper, PtClassService ptClassService, PtMajorService ptMajorService) {
        this.ptCollegeMapper = ptCollegeMapper;
        this.ptClassService = ptClassService;
        this.ptMajorService = ptMajorService;
    }

    @Override
    public List<PtCollege> listCollege() {
        return ptCollegeMapper.listCollege();
    }

    @Override
    public PtCollegeInfo getCollegeInfo(Long collegeId) {
        /* 查询所有年级 */
        PtClassQuery classQuery = new PtClassQuery();
        classQuery.setCollegeId(collegeId);
        List<Integer> grades = ptClassService.listGrade(classQuery);

        /* 查询所有专业 */
        PtMajorQuery majorQuery = new PtMajorQuery();
        majorQuery.setCollegeId(collegeId);
        List<PtMajor> majors = ptMajorService.listMajor(majorQuery);

        PtCollegeInfo collegeInfo = new PtCollegeInfo();
        collegeInfo.setGrades(grades);
        collegeInfo.setMajors(majors);
        collegeInfo.setId(collegeId);
        return collegeInfo;
    }

    @Override
    public PtCollege getCollege(Long collegeId) {
        return ptCollegeMapper.getCollege(collegeId);
    }
}
