package com.rufeng.healthman.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.mapper.PtScoreMapper;
import com.rufeng.healthman.pojo.DO.PtScore;
import com.rufeng.healthman.pojo.DTO.ptmeasurement.MeasurementScoreInfo;
import com.rufeng.healthman.pojo.DTO.ptscore.ScoreInfo;
import com.rufeng.healthman.pojo.DTO.ptstu.StudentBaseInfo;
import com.rufeng.healthman.pojo.Query.PtScoreQuery;
import com.rufeng.healthman.pojo.file.PtScoreExcelListener;
import com.rufeng.healthman.pojo.file.StuScoreExcel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @description TODO
 */
@Service
public class PtScoreService {
    private final PtScoreMapper ptScoreMapper;
    private final PtStudentService ptStudentService;
    private final PtScoreSheetService ptScoreSheetService;
    private final PtSubjectService ptSubjectService;
    private PtMesurementService ptMesurementService;

    public PtScoreService(PtScoreMapper ptScoreMapper,
                          PtStudentService ptStudentService,
                          PtScoreSheetService ptScoreSheetService,
                          PtSubjectService ptSubjectService) {
        this.ptScoreMapper = ptScoreMapper;
        this.ptStudentService = ptStudentService;
        this.ptScoreSheetService = ptScoreSheetService;
        this.ptSubjectService = ptSubjectService;
    }

    /**
     * TODO 循环依赖
     */
    @Autowired
    public void setPtMesurementService(PtMesurementService ptMesurementService) {
        this.ptMesurementService = ptMesurementService;
    }

    public Integer addScoreSelective(List<PtScore> dataList) {
        if (dataList.size() == 0) {
            return 0;
        }
        return ptScoreMapper.batchInsertSelective(dataList);
    }


    public Integer uploadScore(MultipartFile file, Long msId) {
        PtScoreExcelListener listener = new PtScoreExcelListener(ptMesurementService, this, ptStudentService, ptScoreSheetService, msId);
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
        List<String> stuIds = measurementScoreInfos.stream().map(MeasurementScoreInfo::getStuId).collect(Collectors.toList());
        List<PtScore> scores = this.listScoreByStuIds(stuIds, query);
        /* 学生基本信息 */
        Map<String, MeasurementScoreInfo> infoMap = measurementScoreInfos.stream()
                .collect(Collectors.toMap(MeasurementScoreInfo::getStuId, s -> s));
        /* 查科目 */
        List<Long> subIds = scores.stream().map(PtScore::getSubId).distinct().collect(Collectors.toList());
        Map<Long, String> sMap = ptSubjectService.mapSubIdSubNameByIds(subIds);
        scores.forEach(s -> infoMap.get(s.getStuId()).getScores().add(new ScoreInfo(s, sMap.get(s.getSubId()))));
        return ApiPage.convert(measurementScoreInfos);
    }

    public List<PtScore> listScoreByStuIds(List<String> stuIds, PtScoreQuery query) {
        if (stuIds.size() == 0) {
            return Collections.emptyList();
        }
        return ptScoreMapper.listScoreByStuIds(stuIds, query);
    }

    public int deleteByMsId(Long msId) {
        return ptScoreMapper.deleteByMsId(msId);
    }

    public Resource downloadScore(PtScoreQuery query) {
        /* 基本信息 */
        List<StudentBaseInfo> stuInfos = ptScoreMapper.listStuBaseInfo(query);
        Map<String, StudentBaseInfo> stuMap = stuInfos.stream().collect(Collectors.toMap(StudentBaseInfo::getStuId, s -> s));
        List<String> stuIds = new ArrayList<>(stuMap.keySet());
        /* 查成绩 */
        List<PtScore> scores = this.listScoreByStuIds(stuIds, query);
        /* 科目信息 */
        List<Long> subIds = scores.stream().map(PtScore::getSubId).distinct().collect(Collectors.toList());
        Map<Long, String> subMap = ptSubjectService.mapSubIdSubNameByIds(subIds);
        /* excel数据 */
        List<StuScoreExcel> excels = scores.stream().map(s ->
                        new StuScoreExcel(stuMap.get(s.getStuId()), subMap.get(s.getSubId()), s))
                .sorted(StuScoreExcel::compareTo).collect(Collectors.toList());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream, StuScoreExcel.class)
                .registerWriteHandler(new RowWriteHandler() {
                    private int prevIdx = -1;
                    private String prevStuId = null;

                    @Override
                    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex, Boolean isHead) {
                        if (isHead) {
                            return;
                        }
                        if (prevIdx == -1) {
                            prevStuId = row.getCell(0).getStringCellValue();
                            prevIdx = row.getRowNum();
                        } else {
                            String stuId = row.getCell(0).getStringCellValue();
                            if (!stuId.equals(prevStuId)) {
                                Sheet sheet = writeSheetHolder.getSheet();
                                int mergedCols = 3;
                                for (int i = mergedCols - 1; i >= 0; i--) {
                                    sheet.addMergedRegion(new CellRangeAddress(prevIdx, row.getRowNum() - 1, i, i));
                                }
                                prevIdx = row.getRowNum();
                                prevStuId = stuId;
                            }
                        }
                    }
                })
                .sheet().doWrite(excels);
        return new ByteArrayResource(outputStream.toByteArray());
    }

    public ApiPage<ScoreInfo> pageStuScore(Integer page, Integer pageSize, PtScoreQuery query) {
        PageHelper.startPage(page, pageSize);
        Page<PtScore> scores = ptScoreMapper.pageScore(query);
        /* 查科目 */
        List<Long> subIds = scores.stream().map(PtScore::getSubId).distinct().collect(Collectors.toList());
        Map<Long, String> subNameMap = ptSubjectService.mapSubIdSubNameByIds(subIds);
        List<ScoreInfo> infos = scores.stream().map(s -> new ScoreInfo(s, subNameMap.get(s.getSubId()))).collect(Collectors.toList());
        return ApiPage.convert(scores, infos);
    }
}
