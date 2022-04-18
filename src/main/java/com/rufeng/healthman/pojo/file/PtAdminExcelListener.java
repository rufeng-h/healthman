package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.rufeng.healthman.common.util.StringUtils;
import com.rufeng.healthman.exceptions.ExcelException;
import com.rufeng.healthman.pojo.ptdo.PtClass;
import com.rufeng.healthman.pojo.ptdo.PtCollege;
import com.rufeng.healthman.service.PtAdminService;
import com.rufeng.healthman.service.PtClassService;
import com.rufeng.healthman.service.PtCollegeService;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author rufeng
 * @time 2022-03-27 23:23
 * @package com.rufeng.healthman.pojo.file
 * @description 管理员excel
 */
public class PtAdminExcelListener extends AnalysisEventListener<PtAdminExcel> {
    private static final int BATCH_COUNT = 100;
    private final Map<String, String> clgMap;
    private final Map<String, String> clsMap;
    private final PtAdminService ptAdminService;
    @SuppressWarnings("UnstableApiUsage")
    private final BloomFilter<String> adminIdBloomFilter;
    private int handledCount = 0;
    private List<PtAdminExcel> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    @SuppressWarnings("UnstableApiUsage")
    public PtAdminExcelListener(PtAdminService ptAdminService,
                                PtClassService ptClassService,
                                PtCollegeService ptCollegeService) {
        this.ptAdminService = ptAdminService;
        clgMap = ptCollegeService.listCollege().stream().collect(
                Collectors.toMap(PtCollege::getClgName, PtCollege::getClgCode));
        clsMap = ptClassService.listClass().stream().collect(
                Collectors.toMap(PtClass::getClsName, PtClass::getClsCode));
        List<String> adminIds = ptAdminService.listAdminId();
        adminIdBloomFilter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), 1 << 16, 0.001);
        adminIds.forEach(adminIdBloomFilter::put);
    }

    @Override
    public void invoke(PtAdminExcel data, AnalysisContext context) {
        String clgRole = data.getClgRole();
        String clsRole = data.getClsRole();
        String[] clgRoles = StringUtils.isEmptyOrBlank(clgRole) ? new String[0] : clgRole.split(",");
        String[] clsRoles = StringUtils.isEmptyOrBlank(clsRole) ? new String[0] : clsRole.split(",");
        validate(data, clgRoles, clsRoles);
        data.setClgCodes(Arrays.stream(clgRoles).map(clgMap::get).collect(Collectors.toList()));
        data.setClsCodes(Arrays.stream(clsRoles).map(clsMap::get).collect(Collectors.toList()));
        data.setClgCode(clgMap.get(data.getClgName()));
        cachedDataList.add(data);
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    private void saveData() {
        if (cachedDataList.size() != 0) {
            handledCount += ptAdminService.addAdminSelective(cachedDataList);
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    private void validate(PtAdminExcel data, String[] clgRoles, String[] clsRoles) {
        String adminId = data.getAdminId();
        /* 工号 */
        if (!StringUtils.isLetterNumeric(adminId)) {
            throw new ExcelException("工号不能空，且只能包含数字和字母");
        }
        if (adminIdBloomFilter.mightContain(adminId)) {
            System.out.println(adminIdBloomFilter);
            throw new ExcelException("工号不能重复：" + adminId);
        }
        /* 名称 */
        if (StringUtils.isEmptyOrBlank(data.getAdminName())) {
            throw new ExcelException("姓名不能为空！");
        }
        /* 学院 */
        if (data.getClgName() != null && !clgMap.containsKey(data.getClgName())) {
            throw new ExcelException("不能识别学院：" + data.getClgName());
        }
        /* 性别 */
        if (data.getAdminGender() == null) {
            throw new ExcelException("性别不能为空");
        }
        if (data.getAdminBirth() == null) {
            throw new ExcelException("出生日期不能为空");
        }
        if (clgRoles.length == 0 && clsRoles.length == 0) {
            throw new ExcelException("权限不能为空");
        }
        for (String role : clsRoles) {
            if (!clsMap.containsKey(role)) {
                throw new ExcelException("未知班级：" + role);
            }
        }
        for (String clgRole : clgRoles) {
            if (!clgMap.containsKey(clgRole)) {
                throw new ExcelException("未知学院：" + clgRole);
            }
        }
        adminIdBloomFilter.put(adminId);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
    }

    public int getHandledCount() {
        return handledCount;
    }
}
