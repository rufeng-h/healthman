package com.rufeng.healthman.service;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.aop.OperLogRecord;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.enums.OperTypeEnum;
import com.rufeng.healthman.mapper.*;
import com.rufeng.healthman.pojo.dto.ptmeasurement.MeasurementScoreInfo;
import com.rufeng.healthman.pojo.dto.ptscore.PtScoreInfo;
import com.rufeng.healthman.pojo.dto.ptstu.PtStudentBaseInfo;
import com.rufeng.healthman.pojo.file.PtScoreExcelListener;
import com.rufeng.healthman.pojo.file.PtStuScoreExportExcel;
import com.rufeng.healthman.pojo.file.handler.ScoreExcelWriteHandler;
import com.rufeng.healthman.pojo.ptdo.PtScore;
import com.rufeng.healthman.pojo.query.PtScoreQuery;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author rufeng
 * @time 2022-03-18 9:13
 * @package com.rufeng.healthman.service.impl
 * @description 成绩
 */
@Service
public class PtScoreService {
    private final PtScoreMapper ptScoreMapper;
    private final PtSubStudentMapper ptSubStudentMapper;
    private final PtStudentMapper ptStudentMapper;
    private final PtSubjectMapper ptSubjectMapper;
    private final PtScoreSheetMapper ptScoreSheetMapper;

    public PtScoreService(PtScoreMapper ptScoreMapper,
                          PtSubStudentMapper ptSubStudentMapper,
                          PtStudentMapper ptStudentMapper,
                          PtSubjectMapper ptSubjectMapper,
                          PtScoreSheetMapper ptScoreSheetMapper) {
        this.ptScoreMapper = ptScoreMapper;
        this.ptSubStudentMapper = ptSubStudentMapper;
        this.ptSubjectMapper = ptSubjectMapper;
        this.ptStudentMapper = ptStudentMapper;
        this.ptScoreSheetMapper = ptScoreSheetMapper;
    }

    public Integer addScoreSelective(List<PtScore> dataList) {
        if (dataList.size() == 0) {
            return 0;
        }
        return ptScoreMapper.batchInsertSelective(dataList);
    }


    @OperLogRecord(description = "上传体测成绩", operType = OperTypeEnum.INSERT)
    public Integer uploadScore(MultipartFile file, Long msId) {
        PtScoreExcelListener listener = new PtScoreExcelListener(ptSubjectMapper, this,
                ptStudentMapper, ptSubStudentMapper, ptScoreSheetMapper, msId);
        try {
            EasyExcel.read(file.getInputStream(), listener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listener.getHandledCount();
    }

    public ApiPage<MeasurementScoreInfo> pageMsScore(Integer page, Integer pageSize, PtScoreQuery query) {
        /* 查分数 */
        PageHelper.startPage(page, pageSize);
        /* 为分页 */
        Page<MeasurementScoreInfo> measurementScoreInfos = ptScoreMapper.pageStuScoreInfo(query);
        if (measurementScoreInfos.size() == 0) {
            return ApiPage.empty(measurementScoreInfos);
        }
        List<String> stuIds = measurementScoreInfos.stream().map(MeasurementScoreInfo::getStuId).collect(Collectors.toList());
        List<PtScore> scores = this.listScoreByStuIds(stuIds, query);
        /* 学生基本信息 */
        Map<String, MeasurementScoreInfo> infoMap = measurementScoreInfos.stream()
                .collect(Collectors.toMap(MeasurementScoreInfo::getStuId, s -> s));
        /* 查科目 */
        List<Long> subIds = scores.stream().map(PtScore::getSubId).distinct().collect(Collectors.toList());
        Map<Long, String> sMap = ptSubjectMapper.mapSubIdSubNameByIds(subIds);
        scores.forEach(s -> infoMap.get(s.getStuId()).getScores().add(new PtScoreInfo(s, sMap.get(s.getSubId()))));
        return ApiPage.convert(measurementScoreInfos);
    }

    public List<PtScore> listScoreByStuIds(List<String> stuIds, PtScoreQuery query) {
        if (stuIds.size() == 0) {
            return Collections.emptyList();
        }
        return ptScoreMapper.listScoreByStuIds(stuIds, query);
    }

    public Resource downloadScore(PtScoreQuery query) {
        /* 基本信息 */
        List<PtStudentBaseInfo> stuInfos = ptScoreMapper.listStuBaseInfo(query);
        Map<String, PtStudentBaseInfo> stuMap = stuInfos.stream().collect(Collectors.toMap(PtStudentBaseInfo::getStuId, s -> s));
        List<String> stuIds = new ArrayList<>(stuMap.keySet());
        /* 查成绩 */
        List<PtScore> scores = this.listScoreByStuIds(stuIds, query);
        /* 科目信息 */
        List<Long> subIds = scores.stream().map(PtScore::getSubId).distinct().collect(Collectors.toList());
        Map<Long, String> subMap = ptSubjectMapper.mapSubIdSubNameByIds(subIds);
        /* excel数据 */
        List<PtStuScoreExportExcel> excels = scores.stream().map(s ->
                        new PtStuScoreExportExcel(stuMap.get(s.getStuId()), subMap.get(s.getSubId()), s))
                .sorted(PtStuScoreExportExcel::compareTo).collect(Collectors.toList());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream, PtStuScoreExportExcel.class)
                .registerWriteHandler(new ScoreExcelWriteHandler(excels.size()))
                .sheet().doWrite(excels);
        return new ByteArrayResource(outputStream.toByteArray());
    }

    public ApiPage<PtScoreInfo> pageStuScore(Integer page, Integer pageSize, PtScoreQuery query) {
        PageHelper.startPage(page, pageSize);
        Page<PtScore> scores = ptScoreMapper.pageScore(query);
        if (scores.isEmpty()) {
            return ApiPage.empty(scores);
        }
        /* 查科目 */
        List<Long> subIds = scores.stream().map(PtScore::getSubId).distinct().collect(Collectors.toList());
        Map<Long, String> subNameMap = ptSubjectMapper.mapSubIdSubNameByIds(subIds);
        List<PtScoreInfo> infos = scores.stream().map(s -> new PtScoreInfo(s, subNameMap.get(s.getSubId()))).collect(Collectors.toList());
        return ApiPage.convert(scores, infos);
    }
}
