package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.rufeng.healthman.common.util.SpringUtils;
import com.rufeng.healthman.common.util.StringUtils;
import com.rufeng.healthman.exceptions.ExcelException;
import com.rufeng.healthman.pojo.ptdo.PtClass;
import com.rufeng.healthman.pojo.ptdo.PtCollege;
import com.rufeng.healthman.service.PtClassService;
import com.rufeng.healthman.service.PtCollegeService;
import com.rufeng.healthman.service.PtTeacherService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author rufeng
 * @time 2022-03-20 10:01
 * @package com.rufeng.healthman.pojo.upload
 * @description class excel
 */
public class PtClassExcelListener extends AnalysisEventListener<PtClassExcel> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    private final PtClassService ptClassService;
    private final String clgCode;
    private final Map<String, String> clgNameMap;
    private final Set<String> clsNames;
    private final Set<String> clsCodes;
    private final Set<String> teaIds;
    /**
     * 从班级中获取年级，dev模式
     */
    private final Pattern pattern = Pattern.compile("(\\d{4})-");
    private int handledCount = 0;
    private final boolean isDevMode = SpringUtils.isDevMode();
    /**
     * 缓存的数据
     */
    private List<PtClassExcel> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public PtClassExcelListener(PtClassService ptClassService,
                                PtCollegeService ptCollegeService,
                                PtTeacherService ptTeacherService,
                                String clgCode) {
        this.ptClassService = ptClassService;
        this.clgCode = clgCode;
        clgNameMap = ptCollegeService.listCollege().stream().collect(
                Collectors.toMap(PtCollege::getClgName, PtCollege::getClgCode));
        List<PtClass> classes = ptClassService.listClass();
        clsNames = classes.stream().map(PtClass::getClsName).collect(Collectors.toSet());
        clsCodes = classes.stream().map(PtClass::getClsCode).collect(Collectors.toSet());
        teaIds = new HashSet<>(ptTeacherService.listTeaId());
    }

    @Override
    public void invoke(PtClassExcel row, AnalysisContext context) {
        validate(row);
        row.setClgCode(this.clgCode == null ? clgNameMap.get(row.getClgName()) : this.clgCode);
        if (isDevMode) {
            Matcher matcher = pattern.matcher(row.getClsName());
            boolean b = matcher.find();
            if (!b) {
                throw new ExcelException("开发模式下错误的班级名！");
            }
            row.setClsEntryYear(Integer.parseInt(matcher.group(1)));
        } else {
            row.setClsEntryYear(LocalDateTime.now().getYear());
        }
        cachedDataList.add(row);
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 允许并发情况下报错，概率很小
     */
    private void validate(PtClassExcel data) {
        /* 校验学院 */
        if (data.getClgName() != null) {
            String clgCode = clgNameMap.get(data.getClgName());
            if (clgCode == null) {
                throw new ExcelException("未知的学院：" + data.getClgName());
            }
            if (this.clgCode != null && !clgCode.equals(this.clgCode)) {
                throw new ExcelException("学院代码与文件内容不符!");
            }
        }
        /* 班级代码 */
        if (!StringUtils.isLetterNumeric(data.getClsCode())) {
            throw new ExcelException("班级代码不能为空且必须为数字或字母！" + data.getClsCode());
        }
        if (clsCodes.contains(data.getClsCode())) {
            throw new ExcelException("班级代码重复" + data.getClsCode());
        }
        /* 班级名称 */
        if (StringUtils.isEmptyOrBlank(data.getClsName())) {
            throw new ExcelException("班级名称不能为空！");
        }
        if (clsNames.contains(data.getClsName())) {
            throw new ExcelException("班级名称重复：" + data.getClsName());
        }
        /* 年级 */
        if (data.getClsEntryGrade() == null) {
            throw new ExcelException("年级不能为空！");
        }
        /* 教师编号 */
        if (StringUtils.isEmptyOrBlank(data.getTeaId())) {
            throw new ExcelException("教师编号不能为空！");
        }
        if (!teaIds.contains(data.getTeaId())) {
            throw new ExcelException("不存在教师编号！" + data.getTeaId());
        }
        clsCodes.add(data.getClsCode());
        clsNames.add(data.getClsName());
    }

    private void saveData() {
        if (cachedDataList.size() != 0) {
            handledCount += ptClassService.batchInsertSelective(cachedDataList);
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
