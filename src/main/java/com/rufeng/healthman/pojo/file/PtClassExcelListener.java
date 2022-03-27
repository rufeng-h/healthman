package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.rufeng.healthman.service.PtClassService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-20 10:01
 * @package com.rufeng.healthman.pojo.upload
 * @description TODO
 */
@Slf4j
public class PtClassExcelListener extends AnalysisEventListener<PtClassExcel> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    private final PtClassService ptClassService;
    private int handledCount = 0;
    /**
     * 缓存的数据
     */
    private List<PtClassExcel> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public PtClassExcelListener(PtClassService ptClassService) {
        this.ptClassService = ptClassService;
    }

    @Override
    public void invoke(PtClassExcel row, AnalysisContext context) {
        if (row.getClsEntryYear() == null) {
            row.setClsEntryYear(LocalDateTime.now().getYear());
        }
        cachedDataList.add(row);
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    private void saveData() {
        if (cachedDataList.size() != 0) {
            Integer cnt = ptClassService.addClassSelective(cachedDataList);
            handledCount += cnt;
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
    }

    public int getHandledCount() {
        return handledCount;
    }
}
