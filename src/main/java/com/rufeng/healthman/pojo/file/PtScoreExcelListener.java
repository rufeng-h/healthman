package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.rufeng.healthman.common.util.StringUtils;
import com.rufeng.healthman.exceptions.ExcelException;
import com.rufeng.healthman.mapper.PtScoreSheetMapper;
import com.rufeng.healthman.mapper.PtStudentMapper;
import com.rufeng.healthman.mapper.PtSubStudentMapper;
import com.rufeng.healthman.mapper.PtSubjectMapper;
import com.rufeng.healthman.pojo.dto.ptscoresheet.SubStudent;
import com.rufeng.healthman.pojo.dto.ptstu.PtStudentBaseInfo;
import com.rufeng.healthman.pojo.ptdo.PtScore;
import com.rufeng.healthman.pojo.ptdo.PtScoreSheet;
import com.rufeng.healthman.pojo.ptdo.PtSubStudent;
import com.rufeng.healthman.pojo.ptdo.PtSubject;
import com.rufeng.healthman.service.PtScoreService;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.rufeng.healthman.pojo.ptdo.PtScoreSheet.MAX_UPPER;
import static com.rufeng.healthman.pojo.ptdo.PtScoreSheet.MIN_LOWER;

/**
 * 创建一个监听器，继承自AnalysisEventListener
 *
 * @author rufeng
 * TODO 校验数据是否包含在体测中
 */
public class PtScoreExcelListener extends AnalysisEventListener<Map<Integer, String>> {
    private static final String STUDENT_ID_HEADER = "学号";
    private static final int BATCH_COUNT = 100;
    private final PtScoreService ptScoreService;
    private final PtSubStudentMapper ptSubStudentMapper;
    private final PtScoreSheetMapper ptScoreSheetMapper;
    /**
     * 科目名查Id
     */
    private final Map<String, Long> subjectMap;
    /**
     * 列索引映射科目Id
     */
    private final Map<Integer, Long> colSubIdMap;
    private final Map<Integer, String> headMap = new HashMap<>();
    private final long msId;
    private final Map<SubStudent, List<PtScoreSheet>> scoreSheetcache = new HashMap<>(10);
    private final Map<String, PtStudentBaseInfo> stuInfoMap;
    private List<PtScore> dataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private int handledCount = 0;
    private int stuIdColumnIndex = -1;

    public PtScoreExcelListener(
            PtSubjectMapper ptSubjectMapper,
            PtScoreService ptScoreService,
            PtStudentMapper ptStudentMapper,
            PtSubStudentMapper ptSubStudentMapper,
            PtScoreSheetMapper ptScoreSheetMapper,
            long msId) {
        this.ptSubStudentMapper = ptSubStudentMapper;
        this.ptScoreSheetMapper = ptScoreSheetMapper;
        this.msId = msId;
        this.ptScoreService = ptScoreService;
        /* 所有参加本次测验的学生 */
        List<PtStudentBaseInfo> students = ptStudentMapper.listStuBaseInfoByMsId(msId);
        stuInfoMap = students.stream().collect(Collectors.toMap(PtStudentBaseInfo::getStuId, s -> s));
        /* 本次测验所有科目  */
        List<PtSubject> subjects = ptSubjectMapper.listSubjectByMsId(msId);
        subjectMap = subjects.stream().collect(Collectors.toMap(PtSubject::getSubName, PtSubject::getSubId));
        colSubIdMap = new HashMap<>(subjectMap.size());
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        this.headMap.putAll(headMap);
        Set<Map.Entry<Integer, String>> entrySet = headMap.entrySet();
        for (Map.Entry<Integer, String> entry : entrySet) {
            int key = entry.getKey();
            String value = entry.getValue();
            if (STUDENT_ID_HEADER.equals(value)) {
                stuIdColumnIndex = key;
            } else {
                if (!subjectMap.containsKey(value)) {
                    throw new ExcelException("科目" + value + "不在本次测验中！");
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
        PtStudentBaseInfo baseInfo = stuInfoMap.get(curStuId);
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
            if (!scoreSheetcache.containsKey(subStudent)) {
                PtSubStudent s = ptSubStudentMapper.selectByPrimaryKey(subStudent.getSubId(), subStudent.getGrade(), subStudent.getGender().name());
                if (s == null) {
                    throw new ExcelException("学号" + curStuId + "无需测试" + headMap.get(key) + "科目");
                }
                /* 评分标准 */
                List<PtScoreSheet> sheets = ptScoreSheetMapper.listScoreSheetBySubStudent(subStudent);
                scoreSheetcache.put(subStudent, sheets);
            }
            if (!StringUtils.isValidNumber(value)) {
                throw new ExcelException("异常值：" + value);
            }
            BigDecimal scoData = new BigDecimal(value);
            if (scoData.compareTo(MIN_LOWER) <= 0 || scoData.compareTo(MAX_UPPER) >= 0) {
                throw new ExcelException("异常值：" + value);
            }
            PtScore ptScore = getFromCache(subStudent, scoData);
            if (ptScore == null) {
                throw new ExcelException(String.format("数据异常！无评分标准 %s: %s", key, value));
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

    @Nullable
    private PtScore getFromCache(SubStudent key, BigDecimal value) {
        List<PtScoreSheet> sheets = scoreSheetcache.get(key);
        /* 无需评分 */
        if (sheets.size() == 0) {
            return new PtScore();
        }
        for (PtScoreSheet sheet : sheets) {
            BigDecimal upper = sheet.getUpper();
            BigDecimal lower = sheet.getLower();
            if (lower.compareTo(value) <= 0 && upper.compareTo(value) > 0) {
                return PtScore.builder()
                        .score(sheet.getScore())
                        .scoLevel(sheet.getLevel()).build();
            }
        }
        /* 数据异常 */
        return null;
    }
}