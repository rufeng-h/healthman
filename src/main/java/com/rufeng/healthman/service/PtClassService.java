package com.rufeng.healthman.service;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.mapper.*;
import com.rufeng.healthman.pojo.data.PtClassFormdata;
import com.rufeng.healthman.pojo.dto.ptclass.PtClassPageInfo;
import com.rufeng.healthman.pojo.file.PtClassExcel;
import com.rufeng.healthman.pojo.file.PtClassExcelListener;
import com.rufeng.healthman.pojo.ptdo.PtClass;
import com.rufeng.healthman.pojo.ptdo.PtStudent;
import com.rufeng.healthman.pojo.query.PtClassQuery;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author rufeng
 * @time 2022-03-09 22:09
 * @package com.rufeng.healthman.service.impl
 */
@Service
public class PtClassService {
    private final PtClassMapper ptClassMapper;
    private final PtStudentMapper ptStudentMapper;
    private final PtClassMeasurementMapper ptClassMeasurementMapper;
    private final PtScoreMapper ptScoreMapper;
    private final PtCollegeMapper ptCollegeMapper;
    private final PtTeacherMapper ptTeacherMapper;

    public PtClassService(PtClassMapper ptClassMapper,
                          PtStudentMapper ptStudentMapper,
                          PtClassMeasurementMapper ptClassMeasurementMapper,
                          PtScoreMapper ptScoreMapper,
                          PtCollegeMapper ptCollegeMapper,
                          PtTeacherMapper ptTeacherMapper) {
        this.ptClassMapper = ptClassMapper;
        this.ptStudentMapper = ptStudentMapper;
        this.ptClassMeasurementMapper = ptClassMeasurementMapper;
        this.ptScoreMapper = ptScoreMapper;
        this.ptCollegeMapper = ptCollegeMapper;
        this.ptTeacherMapper = ptTeacherMapper;
    }

    public ApiPage<PtClassPageInfo> pageClassInfo(Integer page, Integer pageSize, @NonNull PtClassQuery ptClassQuery) {
        PageHelper.startPage(page, pageSize);
        Page<PtClass> classes = ptClassMapper.page(ptClassQuery);
        if (classes.isEmpty()) {
            return ApiPage.empty(classes);
        }
        /* 查学院 */
        List<String> clgCodes = PtCollegeService.getClgCodeFromClasses(classes);
        Map<String, String> clgCodeNameMap = ptCollegeMapper.mapClgNameByIds(clgCodes);
        List<String> clsCodes = classes.stream().map(PtClass::getClsCode).collect(Collectors.toList());
        Map<String, Integer> map = ptClassMapper.countStudent(clsCodes);
        Map<String, String> teaIdNameMap = ptTeacherMapper.mapTeaNameByIds(
                classes.stream().map(PtClass::getTeaId).collect(Collectors.toList()));
        List<PtClassPageInfo> infos = classes.stream().map(c -> new PtClassPageInfo(
                        c, clgCodeNameMap.get(c.getClgCode()),
                        teaIdNameMap.get(c.getTeaId()),
                        map.get(c.getClsCode())))
                .collect(Collectors.toList());
        return ApiPage.convert(classes, infos);
    }

    public List<PtClass> listClass(PtClassQuery query) {
        return ptClassMapper.listClassByQuery(query);
    }


    public PtClass getPtClass(String classCode) {
        return ptClassMapper.selectByPrimaryKey(classCode);
    }


    public Integer uploadClass(MultipartFile file, @Nullable String clgCode) {
        PtClassExcelListener excelListener = new PtClassExcelListener(this, ptCollegeMapper, ptTeacherMapper, clgCode);
        try {
            EasyExcel.read(file.getInputStream(), PtClassExcel.class, excelListener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return excelListener.getHandledCount();
    }


    public Integer batchInsertSelective(List<PtClassExcel> cachedDataList) {
        if (cachedDataList.size() == 0) {
            return 0;
        }
        return ptClassMapper.batchInsertSelective(cachedDataList);
    }


    public Resource fileTemplate() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream, PtClassExcel.class).sheet().doWrite(Collections.emptyList());
        return new ByteArrayResource(outputStream.toByteArray());
    }

    public List<PtClass> listByTeaId(String teaId) {
        return ptClassMapper.listByTeaId(teaId);
    }

    public List<PtClass> listByTeaIds(List<String> teaIds) {
        if (teaIds.isEmpty()) {
            return Collections.emptyList();
        }
        return ptClassMapper.listByTeaIds(teaIds);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deletePtClass(String clsCode) {
        List<PtStudent> students = ptStudentMapper.listByClsCode(clsCode);
        List<String> stuIds = students.stream().map(PtStudent::getStuId).collect(Collectors.toList());
        /* 删成绩 */
        ptScoreMapper.deleteByStuIds(stuIds);
        /* 删学生 */
        ptStudentMapper.deleteByClsCode(clsCode);
        /* 删班级测试 */
        ptClassMeasurementMapper.deleteByClsCode(clsCode);
        /* 删班级 */
        int cnt = ptClassMapper.deleteByPrimaryKey(clsCode);
        /* TODO 教师个人信息缓存 */
        return cnt == 1;
    }

    public boolean updatePtClass(PtClassFormdata classFormdata) {
        PtClass ptClass = PtClass.builder()
                .clsCode(classFormdata.getClsCode())
                .clgCode(classFormdata.getClgCode())
                .clsModified(LocalDateTime.now())
                .clsName(classFormdata.getClsName())
                .teaId(classFormdata.getTeaId())
                .build();
        return ptClassMapper.updateByPrimaryKeySelective(ptClass) == 1;
    }
}
