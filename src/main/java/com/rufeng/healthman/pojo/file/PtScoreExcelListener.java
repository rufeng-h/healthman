package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.rufeng.healthman.exceptions.ExcelHeaderException;
import com.rufeng.healthman.pojo.DO.PtScore;
import com.rufeng.healthman.pojo.DO.PtSubject;
import com.rufeng.healthman.service.PtScoreService;
import com.rufeng.healthman.service.PtSubjectService;

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
 */
public class PtScoreExcelListener extends AnalysisEventListener<Map<Integer, String>> {

    private static final String STUDENT_ID_HEADER = "学号";
    private static final String YEAR_HEADER = "年份";
    private static final int BATCH_COUNT = 100;
    private final PtScoreService ptScoreService;
    private final Map<String, Long> subjectMap;
    private final Map<Integer, Long> colSubIdMap;
    private int handledCount = 0;

    List<PtScore> dataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private int stuIdColumnIndex = -1;
    private int yearColumnIndex = -1;

    public PtScoreExcelListener(PtSubjectService ptSubjectService, PtScoreService ptScoreService) {
        this.ptScoreService = ptScoreService;
        List<PtSubject> subjectList = ptSubjectService.listSubject();
        subjectMap = subjectList.stream().collect(Collectors.toMap(PtSubject::getSubName, PtSubject::getSubId));
        colSubIdMap = new HashMap<>(subjectMap.size());
    }


    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        Set<Map.Entry<Integer, String>> entrySet = headMap.entrySet();
        for (Map.Entry<Integer, String> entry : entrySet) {
            Integer key = entry.getKey();
            String value = entry.getValue();
            switch (value) {
                case STUDENT_ID_HEADER:
                    stuIdColumnIndex = key;
                    break;
                case YEAR_HEADER:
                    yearColumnIndex = key;
                    break;
                default:
                    if (!subjectMap.containsKey(value)) {
                        throw new ExcelHeaderException("未知的科目：" + value);
                    }
                    colSubIdMap.put(key, subjectMap.get(value));
            }
        }
        if (stuIdColumnIndex == -1) {
            throw new ExcelHeaderException("缺少" + STUDENT_ID_HEADER + "列!");
        }
        if (yearColumnIndex == -1) {
            throw new ExcelHeaderException("缺少" + YEAR_HEADER + "列！");
        }
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        Set<Map.Entry<Integer, String>> entrySet = data.entrySet();
        Integer year = null;
        String stuId = null;
        for (Map.Entry<Integer, String> entry : entrySet) {
            String value = entry.getValue();
            Integer key = entry.getKey();
            if (key == stuIdColumnIndex) {
                stuId = value;
            } else if (key == yearColumnIndex) {
                year = Integer.parseInt(value);
            } else {
                PtScore ptScore = new PtScore();
                ptScore.setStuId(stuId);
//                ptScore.setMsId(year);
                ptScore.setScoData(new BigDecimal(value));
                ptScore.setSubId(colSubIdMap.get(key));
                dataList.add(ptScore);
                if (dataList.size() >= BATCH_COUNT) {
                    saveData();
                    dataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                }
            }
            handledCount += 1;
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
    }

    private void saveData() {
        if (dataList.size() != 0) {
            ptScoreService.addScore(dataList);
        }
    }

    public int getHandledCount() {
        return handledCount;
    }
}