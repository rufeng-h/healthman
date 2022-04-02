package com.rufeng.healthman.service;

import com.alibaba.excel.EasyExcel;
import com.rufeng.healthman.mapper.PtScoreMapper;
import com.rufeng.healthman.pojo.DO.PtScore;
import com.rufeng.healthman.pojo.DTO.ptstu.StuScoreInfo;
import com.rufeng.healthman.pojo.Query.PtScoreQuery;
import com.rufeng.healthman.pojo.file.PtScoreExcelListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final PtStudentService ptStudentService;
    private final PtScoreSheetService ptScoreSheetService;

    public PtScoreService(PtScoreMapper ptScoreMapper, PtSubjectService ptSubjectService,
                          PtStudentService ptStudentService, PtScoreSheetService ptScoreSheetService) {
        this.ptScoreMapper = ptScoreMapper;
        this.ptSubjectService = ptSubjectService;
        this.ptStudentService = ptStudentService;
        this.ptScoreSheetService = ptScoreSheetService;
    }


    public Integer addScoreSelective(List<PtScore> dataList) {
        if (dataList.size() == 0) {
            return 0;
        }
        return ptScoreMapper.batchInsertSelective(dataList);
    }


    public Integer uploadScore(MultipartFile file, Long msId) {
        PtScoreExcelListener listener = new PtScoreExcelListener(ptSubjectService, this, ptStudentService, ptScoreSheetService, msId);
        try {
            EasyExcel.read(file.getInputStream(), listener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listener.getHandledCount();
    }

    public List<StuScoreInfo> listScore(PtScoreQuery query) {
        /* 查分数 */
        List<PtScore> scores = ptScoreMapper.listScore(query);
        List<String> stuIds = scores.stream().map(PtScore::getStuId).distinct().collect(Collectors.toList());
        List<StuScoreInfo> stuScoreInfos = ptStudentService.listStuScoreInfo(stuIds);
        Map<String, StuScoreInfo> infoMap = stuScoreInfos.stream()
                .collect(Collectors.toMap(StuScoreInfo::getStuId, s -> s));
        scores.forEach(s -> infoMap.get(s.getStuId()).getScores().add(s));
        return stuScoreInfos;
    }

    public int deleteByMsId(Long msId) {
        return ptScoreMapper.deleteByMsId(msId);

    }
}
