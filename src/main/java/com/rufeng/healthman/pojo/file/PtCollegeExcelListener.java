package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.rufeng.healthman.common.util.StringUtils;
import com.rufeng.healthman.exceptions.ExcelException;
import com.rufeng.healthman.pojo.ptdo.PtCollege;
import com.rufeng.healthman.service.PtCollegeService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author rufeng
 * @time 2022-03-21 10:09
 * @package com.rufeng.healthman.pojo.file
 * @description 学院excel
 */
public class PtCollegeExcelListener extends AnalysisEventListener<PtCollegeExcel> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    private final Set<String> clgCodes;
    private final Set<String> clgNames;
    private final PtCollegeService ptCollegeService;
    private int handledCount = 0;
    /**
     * 缓存的数据
     */
    private List<PtCollegeExcel> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public PtCollegeExcelListener(PtCollegeService ptCollegeService) {
        this.ptCollegeService = ptCollegeService;
        List<PtCollege> colleges = ptCollegeService.listCollege();
        clgCodes = colleges.stream().map(PtCollege::getClgCode).collect(Collectors.toSet());
        clgNames = colleges.stream().map(PtCollege::getClgName).collect(Collectors.toSet());
    }

    @Override
    public void invoke(PtCollegeExcel data, AnalysisContext context) {
        validate(data);
        cachedDataList.add(data);
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    private void validate(PtCollegeExcel data) {
        if (!StringUtils.isLetterNumeric(data.getClgCode())) {
            throw new ExcelException("学院代码不能为空且必须为数字和字母！");
        }
        if (clgCodes.contains(data.getClgCode())) {
            throw new ExcelException("学院代码重复！");
        }
        if (StringUtils.isEmptyOrBlank(data.getClgName())) {
            throw new ExcelException("学院名称为空！");
        }
        if (clgNames.contains(data.getClgName())) {
            throw new ExcelException("学院名重复！");
        }
        if (StringUtils.isEmptyOrBlank(data.getClgHome())){
            throw new ExcelException("学院主页不能为空！");
        }
        clgNames.add(data.getClgName());
        clgCodes.add(data.getClgCode());
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
    }

    private void saveData() {
        if (cachedDataList.size() != 0) {
            handledCount += ptCollegeService.addCollegeSelective(cachedDataList);
        }
    }

    public int getHandledCount() {
        return handledCount;
    }
}
