package com.rufeng.healthman.service;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.mapper.PtClassMapper;
import com.rufeng.healthman.pojo.ptdo.PtClass;
import com.rufeng.healthman.pojo.dto.ptclass.ClassInfo;
import com.rufeng.healthman.pojo.query.PtClassQuery;
import com.rufeng.healthman.pojo.file.PtClassExcel;
import com.rufeng.healthman.pojo.file.PtClassExcelListener;
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
    private final PtCollegeService ptCollegeService;

    public PtClassService(PtClassMapper ptClassMapper, PtCollegeService ptCollegeService) {
        this.ptClassMapper = ptClassMapper;
        this.ptCollegeService = ptCollegeService;
    }


    public ApiPage<ClassInfo> pageClassInfo(Integer page, Integer pageSize, @NonNull PtClassQuery ptClassQuery) {
        PageHelper.startPage(page, pageSize);
        Page<ClassInfo> infos = ptClassMapper.pageClassInfo(ptClassQuery);
        List<String> clsCodes = infos.stream().map(ClassInfo::getClsCode).collect(Collectors.toList());
        Map<String, Integer> map = ptClassMapper.countStudent(clsCodes);
        infos.forEach(info -> info.setStuCount(map.get(info.getClsCode())));
        return ApiPage.convert(infos);
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

    public List<Integer> listGrade(@NonNull PtClassQuery query) {
        return ptClassMapper.listGrade(query);
    }


    public PtClass getPtClass(String classCode) {
        return ptClassMapper.selectByPrimaryKey(classCode);
    }


    public Integer uploadClass(MultipartFile file, @Nullable String clgCode) {
        PtClassExcelListener excelListener = new PtClassExcelListener(this, ptCollegeService, clgCode);
        try {
            EasyExcel.read(file.getInputStream(), PtClassExcel.class, excelListener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return excelListener.getHandledCount();
    }


    public Integer addClassSelective(List<PtClassExcel> cachedDataList) {
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
}
