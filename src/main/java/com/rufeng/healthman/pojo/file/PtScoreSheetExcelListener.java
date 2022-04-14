package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.rufeng.healthman.service.PtScoreSheetService;

import java.util.List;

import static com.rufeng.healthman.pojo.ptdo.PtScoreSheet.MAX_UPPER;
import static com.rufeng.healthman.pojo.ptdo.PtScoreSheet.MIN_LOWER;

/**
 * @author rufeng
 * @time 2022-04-10 15:58
 * @package com.rufeng.healthman.pojo.file
 * @description 读取评分标准
 */
public class PtScoreSheetExcelListener extends AnalysisEventListener<PtScoreSheetExcel> {
    private static final int BATCH_COUNT = 100;
    private final PtScoreSheetService ptScoreSheetService;
    private final long subId;
    private int handleCount = 0;
    private List<PtScoreSheetExcel> scoreSheetList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public PtScoreSheetExcelListener(long subId, PtScoreSheetService ptScoreSheetService) {
        this.subId = subId;
        this.ptScoreSheetService = ptScoreSheetService;
    }

    @Override
    public void invoke(PtScoreSheetExcel data, AnalysisContext context) {
        data.setSubId(subId);
        if (data.getLower() == null) {
            data.setLower(MIN_LOWER);
        }
        if (data.getUpper() == null) {
            data.setUpper(MAX_UPPER);
        }
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
