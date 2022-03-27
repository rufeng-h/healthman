package com.rufeng.healthman.service.impl;

import com.alibaba.excel.EasyExcel;
import com.rufeng.healthman.mapper.PtScoreMapper;
import com.rufeng.healthman.pojo.DO.PtScore;
import com.rufeng.healthman.pojo.DTO.ptscore.StuScoreInfo;
import com.rufeng.healthman.pojo.Query.StuScoreQuery;
import com.rufeng.healthman.pojo.file.PtScoreExcelListener;
import com.rufeng.healthman.service.PtScoreService;
import com.rufeng.healthman.service.PtSubjectService;
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
public class PtScoreServiceImpl implements PtScoreService {
    private final PtScoreMapper ptScoreMapper;
    private final PtSubjectService ptSubjectService;

    public PtScoreServiceImpl(PtScoreMapper ptScoreMapper, PtSubjectService ptSubjectService) {
        this.ptScoreMapper = ptScoreMapper;
        this.ptSubjectService = ptSubjectService;
    }

    @Override
    public List<StuScoreInfo> listScore(StuScoreQuery query) {
        return null;
    }

    @Override
    public Integer addScore(List<PtScore> dataList) {
        return ptScoreMapper.insertBatch(dataList);
    }

    @Override
    public Integer uploadScore(MultipartFile file) {
        PtScoreExcelListener listener = new PtScoreExcelListener(ptSubjectService, this);
        try {
            EasyExcel.read(file.getInputStream(), listener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listener.getHandledCount();
    }
}
