package com.rufeng.healthman.service;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.mapper.PtClassMapper;
import com.rufeng.healthman.pojo.dto.ptclass.PtClassPageInfo;
import com.rufeng.healthman.pojo.file.PtClassExcel;
import com.rufeng.healthman.pojo.file.PtClassExcelListener;
import com.rufeng.healthman.pojo.ptdo.PtClass;
import com.rufeng.healthman.pojo.query.PtClassQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    private PtCollegeService ptCollegeService;
    private PtTeacherService ptTeacherService;

    public PtClassService(PtClassMapper ptClassMapper) {
        this.ptClassMapper = ptClassMapper;
    }

    @Autowired
    public void setPtCollegeService(PtCollegeService ptCollegeService) {
        this.ptCollegeService = ptCollegeService;
    }

    /**
     * 循环依赖 TODO
     */
    @Autowired
    public void setPtTeacherService(PtTeacherService ptTeacherService) {
        this.ptTeacherService = ptTeacherService;
    }

    public ApiPage<PtClassPageInfo> pageClassInfo(Integer page, Integer pageSize, @NonNull PtClassQuery ptClassQuery) {
        PageHelper.startPage(page, pageSize);
        Page<PtClass> classes = ptClassMapper.page(ptClassQuery);
        /* 查学院 */
        List<String> clgCodes = ptCollegeService.getClgCodeFromClasses(classes);
        Map<String, String> clgCodeNameMap = ptCollegeService.mapClgNameByIds(clgCodes);
        List<String> clsCodes = classes.stream().map(PtClass::getClsCode).collect(Collectors.toList());
        Map<String, Integer> map = ptClassMapper.countStudent(clsCodes);
        Map<String, String> teaIdNameMap = ptTeacherService.mapTeaNameByIds(
                classes.stream().map(PtClass::getTeaId).collect(Collectors.toList()));
        List<PtClassPageInfo> infos = classes.stream().map(c -> new PtClassPageInfo(
                c, clgCodeNameMap.get(c.getClgCode()), teaIdNameMap.get(c.getTeaId()), map.get(c.getClsCode()))).collect(Collectors.toList());
        return ApiPage.convert(classes, infos);
    }


    public List<PtClass> listClass(@NonNull PtClassQuery query) {
        return ptClassMapper.listClass(query);
    }

    public List<PtClass> listClass(List<String> clsCodes) {
        if (clsCodes.size() == 0) {
            return Collections.emptyList();
        }
        return ptClassMapper.listClassByClsCodes(clsCodes);
    }

    public List<PtClass> listClass() {
        return ptClassMapper.listClass(new PtClassQuery());
    }

    public List<Integer> listGrade(@Nullable String clgCode) {
        return ptClassMapper.listGrade(clgCode);
    }


    public PtClass getPtClass(String classCode) {
        return ptClassMapper.selectByPrimaryKey(classCode);
    }


    public Integer uploadClass(MultipartFile file, @Nullable String clgCode) {
        PtClassExcelListener excelListener = new PtClassExcelListener(this, ptCollegeService, ptTeacherService, clgCode);
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

    public Map<String, String> mapClsNameByIds(List<String> collect) {
        if (collect.size() == 0) {
            return Collections.emptyMap();
        }
        return ptClassMapper.mapClsNameByIds(collect);
    }

    public Map<String, Integer> countStudent(List<String> clsCodes) {
        if (clsCodes.size() == 0) {
            return Collections.emptyMap();
        }
        return ptClassMapper.countStudent(clsCodes);
    }

    public List<PtClass> listByTeaId(String teaId) {
        return ptClassMapper.listByTeaId(teaId);
    }

    public List<PtClass> listByTeaIds(List<String> teaIds) {
        if (teaIds.isEmpty()){
            return Collections.emptyList();
        }
        return ptClassMapper.listByTeaIds(teaIds);
    }
}
