package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.rufeng.healthman.common.util.StringUtils;
import com.rufeng.healthman.exceptions.ExcelException;
import com.rufeng.healthman.pojo.ptdo.PtClass;
import com.rufeng.healthman.service.PtClassService;
import com.rufeng.healthman.service.PtStudentService;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author rufeng
 * @time 2022-03-20 17:16
 * @package com.rufeng.healthman.pojo.file
 * @description stu excel import
 */
public class PtStudentExcelListener extends AnalysisEventListener<PtStudentExcel> {
    private static final int BATCH_COUNT = 100;
    private final PtStudentService ptStudentService;
    @SuppressWarnings("UnstableApiUsage")
    private final BloomFilter<String> stuIdBloomFilter;
    private final String clsCode;
    private final Map<String, String> clsMap;
    private int handledCount = 0;
    /**
     * 缓存的数据
     */
    private List<PtStudentExcel> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    @SuppressWarnings("UnstableApiUsage")
    public PtStudentExcelListener(PtStudentService ptStudentService, PtClassService ptClassService, String clsCode) {
        this.ptStudentService = ptStudentService;
        this.clsCode = clsCode;
        List<String> stuIds = ptStudentService.listStuId();
        stuIdBloomFilter = BloomFilter.create(
                Funnels.stringFunnel(StandardCharsets.UTF_8), stuIds.size() * 2, 0.001);
        stuIds.forEach(stuIdBloomFilter::put);
        /* 可以只返回名称和代码 */
        List<PtClass> classes = ptClassService.listClass();
        clsMap = classes.stream().collect(Collectors.toMap(PtClass::getClsName, PtClass::getClsCode));
    }

    @Override
    public void invoke(PtStudentExcel data, AnalysisContext context) {
        validate(data);
        data.setClsCode(this.clsCode == null ? clsMap.get(data.getClsName()) : this.clsCode);
        cachedDataList.add(data);
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    private void validate(PtStudentExcel data) {
        /* 学号 */
        if (!StringUtils.isLetterNumeric(data.getStuId())) {
            throw new ExcelException("学号不能为空且必须为字母和数字！");
        }
        if (stuIdBloomFilter.mightContain(data.getStuId())) {
            throw new ExcelException(String.format("学号%s已存在", data.getStuId()));
        }
        /* 姓名 */
        if (StringUtils.isEmptyOrBlank(data.getStuName())) {
            throw new ExcelException("姓名不能为空！");
        }
        /* 性别 */
        if (data.getStuGender() == null) {
            throw new ExcelException("性别不能为空！");
        }
        /* 出生日期 */
        if (data.getStuBirth() == null) {
            throw new ExcelException("出生日期不能为空！");
        }
        /* 班级 */
        if (StringUtils.isEmptyOrBlank(data.getClsName())) {
            throw new ExcelException("班级不能为空！");
        }
        String clsCode = clsMap.get(data.getClsName());
        if (clsCode == null) {
            throw new ExcelException(String.format("不能识别班级：%s", data.getClsName()));
        }
        if (this.clsCode != null && !this.clsCode.equals(clsCode)) {
            throw new ExcelException("班级代码与文件内容不符！");
        }
        stuIdBloomFilter.put(data.getStuId());
    }

    private void saveData() {
        if (cachedDataList.size() != 0) {
            handledCount += ptStudentService.addStudentSelective(cachedDataList);
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
