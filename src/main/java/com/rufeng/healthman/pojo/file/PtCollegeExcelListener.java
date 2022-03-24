package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.rufeng.healthman.service.PtCollegeService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-21 10:09
 * @package com.rufeng.healthman.pojo.file
 * @description TODO
 */
@Slf4j
public class PtCollegeExcelListener extends AnalysisEventListener<PtCollegeExcel> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    private final PtCollegeService ptCollegeService;
    private int handledCount = 0;
    /**
     * 缓存的数据
     */
    private List<PtCollegeExcel> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public PtCollegeExcelListener(PtCollegeService ptCollegeService) {
        this.ptCollegeService = ptCollegeService;
    }

    @Override
    public void invoke(PtCollegeExcel data, AnalysisContext context) {
        log.debug("{}", data);
        cachedDataList.add(data);
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
    }

    private void saveData() {
        if (cachedDataList.size() != 0) {
            handledCount += ptCollegeService.addCollege(cachedDataList);
        }
    }

    public int getHandledCount() {
        return handledCount;
    }
}
