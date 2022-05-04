package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.rufeng.healthman.common.util.StringUtils;
import com.rufeng.healthman.common.util.TranslationUtils;
import com.rufeng.healthman.exceptions.ExcelException;
import com.rufeng.healthman.mapper.PtSubStudentMapper;
import com.rufeng.healthman.pojo.dto.ptscoresheet.SubStudent;
import com.rufeng.healthman.service.PtScoreSheetService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.rufeng.healthman.pojo.ptdo.PtScoreSheet.MAX_UPPER;
import static com.rufeng.healthman.pojo.ptdo.PtScoreSheet.MIN_LOWER;

/**
 * @author rufeng
 * @time 2022-04-10 15:58
 * @package com.rufeng.healthman.pojo.file
 * @description 读取评分标准
 * TODO 区间不能交叉
 */
public class PtScoreSheetExcelListener extends AnalysisEventListener<PtScoreSheetExcel> {
    private static final int BATCH_COUNT = 100;
    private final PtScoreSheetService ptScoreSheetService;
    private final long subId;
    private final Set<SubStudent> subStudents;
    private int handleCount = 0;
    private List<PtScoreSheetExcel> scoreSheetList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public PtScoreSheetExcelListener(long subId,
                                     PtScoreSheetService ptScoreSheetService,
                                     PtSubStudentMapper ptSubStudentMapper) {
        this.subId = subId;
        this.ptScoreSheetService = ptScoreSheetService;
        this.subStudents = new HashSet<>(ptSubStudentMapper.listSubStudentBySubId(subId));
    }

    @Override
    public void invoke(PtScoreSheetExcel data, AnalysisContext context) {
        if (data.getLower() == null) {
            data.setLower(MIN_LOWER);
        }
        if (data.getUpper() == null) {
            data.setUpper(MAX_UPPER);
        }
        data.setSubId(subId);

        validate(data);

        scoreSheetList.add(data);
        if (scoreSheetList.size() >= BATCH_COUNT) {
            saveData();
            scoreSheetList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    private void saveData() {
        if (scoreSheetList.size() > 0) {
            handleCount += ptScoreSheetService.addScoreSheetSelective(scoreSheetList);
        }
    }

    private void validate(PtScoreSheetExcel data) {
        if (data.getGrade() == null) {
            throw new ExcelException("年级不能为空！");
        }
        if (data.getGender() == null) {
            throw new ExcelException("性别不能为空！");
        }
        if (data.getScore() == null) {
            throw new ExcelException("分数不能为空！");
        }
        if (StringUtils.isEmptyOrBlank(data.getLevel())) {
            throw new ExcelException("等级不能为空！");
        }
        if (data.getUpper().compareTo(data.getLower()) <= 0
                || data.getUpper().compareTo(MAX_UPPER) >= 0
                || data.getLower().compareTo(MIN_LOWER) <= 0) {
            throw new ExcelException("数据异常！请检查文件：");
        }
        if (!subStudents.contains(new SubStudent(data.getGrade(), data.getGender(), subId))) {
            throw new ExcelException(TranslationUtils.translateGrade(data.getGrade()) +
                    data.getGender().getGender() + "不参与此科目！");
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (scoreSheetList.size() != 0) {
            saveData();
        }
    }

    public int getHandleCount() {
        return handleCount;
    }
}
