package com.rufeng.healthman.service;

import com.alibaba.excel.EasyExcel;
import com.rufeng.healthman.mapper.PtCollegeMapper;
import com.rufeng.healthman.pojo.DO.PtCollege;
import com.rufeng.healthman.pojo.file.PtCollegeExcel;
import com.rufeng.healthman.pojo.file.PtCollegeExcelListener;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-12 10:18
 * @package com.rufeng.healthman.service.impl
 * @description TODO
 */
@Service
public class PtCollegeService {
    private final PtCollegeMapper ptCollegeMapper;
    private final PtClassService ptClassService;

    public PtCollegeService(PtCollegeMapper ptCollegeMapper, PtClassService ptClassService) {
        this.ptCollegeMapper = ptCollegeMapper;
        this.ptClassService = ptClassService;
    }

    
    public List<PtCollege> listCollege() {
        return ptCollegeMapper.listCollege();
    }

//    
//    public PtCollegeInfo getCollegeInfo(String clgCode) {
//        /* 查询所有年级 */
//        PtClassQuery classQuery = new PtClassQuery();
//        classQuery.setClgCode(collegeId);
//        List<Integer> grades = ptClassService.listGrade(classQuery);
//
//        /* 查询所有专业 */
//        PtMajorQuery majorQuery = new PtMajorQuery();
//        majorQuery.setCollegeId(collegeId);
//        List<PtMajor> majors = ptMajorService.listMajor(majorQuery);
//
//        PtCollegeInfo collegeInfo = new PtCollegeInfo();
//        collegeInfo.setGrades(grades);
//        collegeInfo.setMajors(majors);
//        collegeInfo.setId(collegeId);
//        return collegeInfo;
//        return null;
//    }

    
    public PtCollege getCollege(String clgCode) {
        return ptCollegeMapper.getCollege(clgCode);
    }

    
    public Integer uploadCollege(MultipartFile file) {
        PtCollegeExcelListener listener = new PtCollegeExcelListener(this);
        try {
            EasyExcel.read(file.getInputStream(), PtCollegeExcel.class, listener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listener.getHandledCount();
    }

    
    public Integer addCollege(List<PtCollegeExcel> cachedDataList) {
        if (cachedDataList.size() == 0) {
            return 0;
        }
        return ptCollegeMapper.insertBatch(cachedDataList);
    }

    
    public Resource fileTemplate() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream, PtCollegeExcel.class).sheet()
                .doWrite(Collections.emptyList());
        return new ByteArrayResource(outputStream.toByteArray());
    }
}
