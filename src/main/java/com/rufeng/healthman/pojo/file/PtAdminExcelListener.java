package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.rufeng.healthman.exceptions.ExcelException;
import com.rufeng.healthman.pojo.DO.PtClass;
import com.rufeng.healthman.pojo.DO.PtCollege;
import com.rufeng.healthman.pojo.Query.PtClassQuery;
import com.rufeng.healthman.service.PtAdminService;
import com.rufeng.healthman.service.PtClassService;
import com.rufeng.healthman.service.PtCollegeService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author rufeng
 * @time 2022-03-27 23:23
 * @package com.rufeng.healthman.pojo.file
 * @description TODO
 */
public class PtAdminExcelListener extends AnalysisEventListener<PtAdminExcel> {
    private static final int BATCH_COUNT = 100;
    private final Map<String, String> clgMap;
    private final Map<String, String> clsMap;
    private final PtAdminService ptAdminService;
    private int handledCount = 0;
    private List<PtAdminExcel> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public PtAdminExcelListener(PtAdminService ptAdminService, PtClassService ptClassService, PtCollegeService ptCollegeService) {
        this.ptAdminService = ptAdminService;
        clgMap = ptCollegeService.listCollege().stream().collect(Collectors.toMap(PtCollege::getClgName, PtCollege::getClgCode));
        clsMap = ptClassService.listClass(new PtClassQuery()).stream().collect(Collectors.toMap(PtClass::getClsName, PtClass::getClsCode));
    }

    @Override
    public void invoke(PtAdminExcel data, AnalysisContext context) {
        if (data.getClgRole() != null) {
            data.setClgCodes(Arrays.stream(data.getClgRole().split(",")).map((clg) -> {
                if (!clgMap.containsKey(clg)) {
                    throw new ExcelException("不存在学院:" + clg);
                }
                return clgMap.get(clg);
            }).collect(Collectors.toList()));
        }
        if (data.getClsRole() != null) {
            data.setClsCodes(Arrays.stream(data.getClsRole().split(",")).map((cls) -> {
                if (!clsMap.containsKey(cls)) {
                    throw new ExcelException("不存在班级:" + cls);
                }
                return clsMap.get(cls);
            }).collect(Collectors.toList()));
        }
        if (data.getClgName() != null) {
            data.setClgCode(clgMap.get(data.getClgName()));
        }
        cachedDataList.add(data);
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    private void saveData() {
        if (cachedDataList.size() != 0) {
            handledCount += ptAdminService.addAdmin(cachedDataList);
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
