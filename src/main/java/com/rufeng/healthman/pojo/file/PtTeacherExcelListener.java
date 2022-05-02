package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.rufeng.healthman.common.util.StringUtils;
import com.rufeng.healthman.exceptions.ExcelException;
import com.rufeng.healthman.mapper.PtCollegeMapper;
import com.rufeng.healthman.pojo.dto.ptteacher.PtTeacherClgIdentity;
import com.rufeng.healthman.pojo.ptdo.PtCollege;
import com.rufeng.healthman.service.PtTeacherService;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author rufeng
 * @time 2022-03-27 23:23
 * @package com.rufeng.healthman.pojo.file
 * @description 教师excel
 */
public class PtTeacherExcelListener extends AnalysisEventListener<PtTeacherExcel> {
    private static final int BATCH_COUNT = 100;
    private final Map<String, String> clgMap;
    private final PtTeacherService ptTeacherService;
    @SuppressWarnings("UnstableApiUsage")
    private final BloomFilter<String> teaIdBloomFilter;
    private final Set<String> principalClgs = new HashSet<>();
    private int handledCount = 0;
    private List<PtTeacherExcel> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    @SuppressWarnings("UnstableApiUsage")
    public PtTeacherExcelListener(PtTeacherService ptTeacherService,
                                  PtCollegeMapper ptCollegeMapper) {
        this.ptTeacherService = ptTeacherService;
        clgMap = ptCollegeMapper.listCollege().stream().collect(
                Collectors.toMap(PtCollege::getClgName, PtCollege::getClgCode));
        List<PtTeacherClgIdentity> clgIdentities = ptTeacherService.listClgIdentity();
        teaIdBloomFilter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), 1 << 16, 0.001);
        clgIdentities.forEach(t -> {
            teaIdBloomFilter.put(t.getTeaId());
            if (Boolean.TRUE.equals(t.getPrincipal())) {
                principalClgs.add(t.getClgCode());
            }
        });
    }

    @Override
    public void invoke(PtTeacherExcel data, AnalysisContext context) {
        validate(data);
        data.setClgCode(clgMap.get(data.getClgName()));
        cachedDataList.add(data);
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    private void saveData() {
        if (cachedDataList.size() != 0) {
            handledCount += ptTeacherService.addTeacherSelective(cachedDataList);
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    private void validate(PtTeacherExcel data) {
        String teaId = data.getTeaId();
        /* 工号 */
        if (!StringUtils.isLetterNumeric(teaId)) {
            throw new ExcelException("工号不能空，且只能包含数字和字母");
        }
        if (teaIdBloomFilter.mightContain(teaId)) {
            throw new ExcelException("工号不能重复：" + teaId);
        }
        /* 名称 */
        if (StringUtils.isEmptyOrBlank(data.getTeaName())) {
            throw new ExcelException("姓名不能为空！");
        }
        /* 学院 */
        if (data.getClgName() != null && !clgMap.containsKey(data.getClgName())) {
            throw new ExcelException("不能识别学院：" + data.getClgName());
        }
        /* 性别 */
        if (data.getTeaGender() == null) {
            throw new ExcelException("性别不能为空");
        }
        if (data.getTeaBirth() == null) {
            throw new ExcelException("出生日期不能为空");
        }
        /* 负责人只能有一个 */
        if (Boolean.TRUE.equals(data.getPrincipal())) {
            String clgCode = clgMap.get(data.getClgName());
            if (principalClgs.contains(clgCode)) {
                throw new ExcelException(data.getClgName() + "已有负责人！");
            }
            principalClgs.add(clgCode);
        }
        teaIdBloomFilter.put(teaId);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
    }

    public int getHandledCount() {
        return handledCount;
    }
}
