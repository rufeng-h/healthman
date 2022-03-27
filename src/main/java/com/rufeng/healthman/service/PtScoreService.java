package com.rufeng.healthman.service;

import com.alibaba.excel.EasyExcel;
import com.rufeng.healthman.mapper.PtScoreMapper;
import com.rufeng.healthman.pojo.DO.PtScore;
import com.rufeng.healthman.pojo.DTO.ptscore.StuScoreInfo;
import com.rufeng.healthman.pojo.Query.StuScoreQuery;
import com.rufeng.healthman.pojo.file.PtScoreExcelListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-18 9:13
 * @package com.rufeng.healthman.service.impl
 * @description TODO
 */
@Service
public class PtScoreService {
    private final PtScoreMapper ptScoreMapper;
    private final PtSubjectService ptSubjectService;

    public PtScoreService(PtScoreMapper ptScoreMapper, PtSubjectService ptSubjectService) {
        this.ptScoreMapper = ptScoreMapper;
        this.ptSubjectService = ptSubjectService;
    }


    public Integer addScoreSelective(List<PtScore> dataList) {
        if (dataList.size() == 0){
            return 0;
        }
        return ptScoreMapper.batchInsertSelective(dataList);
    }


    public Integer uploadScore(MultipartFile file) {
        PtScoreExcelListener listener = new PtScoreExcelListener(ptSubjectService, this);
        try {
            EasyExcel.read(file.getInputStream(), listener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listener.getHandledCount();
    }

    public List<StuScoreInfo>  listScore(StuScoreQuery query) {
        return null;
    }
}
