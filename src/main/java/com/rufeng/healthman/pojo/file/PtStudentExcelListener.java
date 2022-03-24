package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.rufeng.healthman.service.PtStudentService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-20 17:16
 * @package com.rufeng.healthman.pojo.file
 * @description TODO
 */
@Slf4j
public class PtStudentExcelListener extends AnalysisEventListener<PtStudentExcel> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    private final PtStudentService ptStudentService;
    private int handledCount = 0;
    /**
     * 缓存的数据
     */
    private List<PtStudentExcel> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public PtStudentExcelListener(PtStudentService ptStudentService) {
        this.ptStudentService = ptStudentService;
    }

    @Override
    public void invoke(PtStudentExcel data, AnalysisContext context) {
        log.info("解析到一条数据:{}", data);
        cachedDataList.add(data);
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    private void saveData() {
        if (cachedDataList.size() != 0) {
            Integer cnt = ptStudentService.addStudent(cachedDataList);
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
