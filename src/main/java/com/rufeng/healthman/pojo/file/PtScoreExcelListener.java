package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.rufeng.healthman.exceptions.ExcelException;
import com.rufeng.healthman.pojo.dto.ptscoresheet.SubStudent;
import com.rufeng.healthman.pojo.dto.ptstu.StudentBaseInfo;
import com.rufeng.healthman.pojo.ptdo.PtScore;
import com.rufeng.healthman.pojo.ptdo.PtScoreSheet;
import com.rufeng.healthman.pojo.ptdo.PtSubject;
import com.rufeng.healthman.service.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 创建一个监听器，继承自AnalysisEventListener
 *
 * @author rufeng
 * TODO 校验数据是否包含在体测中
 */
public class PtScoreExcelListener extends AnalysisEventListener<Map<Integer, String>> {
    private final PtSubStudentService ptSubStudentService;
    private static final String STUDENT_ID_HEADER = "学号";
    private static final int BATCH_COUNT = 100;
    private final PtScoreService ptScoreService;
    private final PtScoreSheetService ptScoreSheetService;
    /**
     * 科目名查Id
     */
    private final Map<String, Long> subjectMap;
    /**
     * 列索引映射科目Id
     */
    private final Map<Integer, Long> colSubIdMap;
    private final long msId;
    private final Map<SubStudent, List<PtScoreSheet>> scoreSheetcache = new HashMap<>(10);
    private final Map<String, StudentBaseInfo> stuInfoMap;
    List<PtScore> dataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private int handledCount = 0;
    private int stuIdColumnIndex = -1;

    public PtScoreExcelListener(PtMesurementService ptMesurementService,
                                PtScoreService ptScoreService,
                                PtStudentService ptStudentService,
                                PtSubStudentService ptSubStudentService,
                                PtScoreSheetService ptScoreSheetService, long msId) {
        this.ptSubStudentService = ptSubStudentService;
        this.ptScoreSheetService = ptScoreSheetService;
        this.msId = msId;
        this.ptScoreService = ptScoreService;
        /* 所有参加本次测验的学生 */
        List<StudentBaseInfo> students = ptStudentService.listStuBaseInfoByMsId(msId);
        stuInfoMap = students.stream().collect(Collectors.toMap(StudentBaseInfo::getStuId, s -> s));
        /* 本次测验所有科目 TODO:方法移动到subjectService */
        List<PtSubject> subjects = ptMesurementService.listSubject(msId);
        subjectMap = subjects.stream().collect(Collectors.toMap(PtSubject::getSubName, PtSubject::getSubId));
        colSubIdMap = new HashMap<>(subjectMap.size());
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        Set<Map.Entry<Integer, String>> entrySet = headMap.entrySet();
        for (Map.Entry<Integer, String> entry : entrySet) {
            int key = entry.getKey();
            String value = entry.getValue();
            if (STUDENT_ID_HEADER.equals(value)) {
                stuIdColumnIndex = key;
            } else {
                if (!subjectMap.containsKey(value)) {
                    throw new ExcelException("未知的科目：" + value);
                }
                colSubIdMap.put(key, subjectMap.get(value));
            }
        }
        if (stuIdColumnIndex == -1) {
            throw new ExcelException("缺少" + STUDENT_ID_HEADER + "列!");
        }
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        String curStuId = data.get(stuIdColumnIndex);
        /* 该学生是否参加本次体测 */
        StudentBaseInfo baseInfo = stuInfoMap.get(curStuId);
        if (baseInfo == null) {
            throw new ExcelException("学号" + curStuId + "的学生不在该次测验中！");
        }
        Set<Map.Entry<Integer, String>> entrySet = data.entrySet();
        /* 只有一列学号，其余列为成绩 */
        for (Map.Entry<Integer, String> entry : entrySet) {
            String value = entry.getValue();
            int key = entry.getKey();
            /* 无成绩或者为学号列 */
            if (value == null || key == stuIdColumnIndex) {
                continue;
            }
            Long subId = colSubIdMap.get(key);
            SubStudent subStudent = new SubStudent(baseInfo, subId);
            /* 是否需要测试该科目 */
            if (!scoreSheetcache.containsKey(subStudent)){

            }
            BigDecimal scoData = new BigDecimal(value);
            PtScore ptScore = getFromCache(subStudent, scoData);
            if (ptScore == null) {
                List<PtScoreSheet> sheets = ptScoreSheetService.listScoreSheet(subStudent);
                scoreSheetcache.put(subStudent, sheets);
                ptScore = getFromCache(subStudent, scoData);
            }
            if (ptScore == null) {
                throw new ExcelException("数据异常，请检查！" + scoData);
            }
            ptScore.setStuId(curStuId);
            ptScore.setMsId(this.msId);
            ptScore.setSubId(subId);
            ptScore.setScoData(scoData);
            dataList.add(ptScore);
            if (dataList.size() >= BATCH_COUNT) {
                saveData();
                dataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
            }
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
    }

    private void saveData() {
        if (dataList.size() != 0) {
            handledCount += ptScoreService.addScoreSelective(dataList);
        }
    }

    public int getHandledCount() {
        return handledCount;
    }

    private PtScore getFromCache(SubStudent key, BigDecimal value) {
        List<PtScoreSheet> sheets = scoreSheetcache.get(key);
        if (sheets == null) {
            return null;
        }
        for (PtScoreSheet sheet : sheets) {
            BigDecimal upper = sheet.getUpper();
            BigDecimal lower = sheet.getLower();
            if (upper == null || lower == null) {
                return new PtScore();
            }
            if (lower.compareTo(value) <= 0 && upper.compareTo(value) > 0) {
                return PtScore.builder()
                        .score(sheet.getScore())
                        .scoLevel(sheet.getLevel()).build();
            }
        }
//        throw new ExcelException("未找到评分标准！请检查数据");
        return new PtScore();
    }
}