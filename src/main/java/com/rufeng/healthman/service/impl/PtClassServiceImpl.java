package com.rufeng.healthman.service.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.mapper.PtClassMapper;
import com.rufeng.healthman.pojo.DO.PtClass;
import com.rufeng.healthman.pojo.DTO.ptclass.ClassInfo;
import com.rufeng.healthman.pojo.Query.PtClassQuery;
import com.rufeng.healthman.pojo.file.PtClassExcel;
import com.rufeng.healthman.pojo.file.PtClassExcelListener;
import com.rufeng.healthman.service.PtClassService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
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
public class PtClassServiceImpl implements PtClassService {
    private final PtClassMapper ptClassMapper;

    public PtClassServiceImpl(PtClassMapper ptClassMapper) {
        this.ptClassMapper = ptClassMapper;
    }

    @Override
    public ApiPage<ClassInfo> pageClassInfo(Integer page, Integer pageSize, @NonNull PtClassQuery ptClassQuery) {
        PageHelper.startPage(page, pageSize);
        Page<ClassInfo> infos = ptClassMapper.pageClassInfo(ptClassQuery);
        List<String> clsCodes = infos.stream().map(ClassInfo::getClsCode).collect(Collectors.toList());
        Map<String, Integer> map = ptClassMapper.countStudent(clsCodes);
        infos.forEach(info -> info.setStuCount(map.get(info.getClsCode())));
        return ApiPage.convert(infos);
    }

    @Override
    public List<PtClass> listClass(@NonNull PtClassQuery query) {
        return ptClassMapper.listClass(query);
    }

    @Override
    public List<Integer> listGrade(@NonNull PtClassQuery query) {
        return ptClassMapper.listGrade(query);
    }

    @Override
    public PtClass getPtClass(String classCode) {
        return ptClassMapper.getPtClass(classCode);
    }

    @Override
    public Integer uploadClass(MultipartFile file) {
        PtClassExcelListener excelListener = new PtClassExcelListener(this);
        try {
            EasyExcel.read(file.getInputStream(), PtClassExcel.class, excelListener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return excelListener.getHandledCount();
    }

    @Override
    public Integer addClass(List<PtClassExcel> cachedDataList) {
        if (cachedDataList.size() == 0) {
            return 0;
        }
        return ptClassMapper.insertBatch(cachedDataList);
    }

    @Override
    public Resource fileTemplate() {
        PageHelper.startPage(1, 10);
//        List<PtClass> list = ptClassMapper.pageClassInfo(new PtClassQuery());
//        List<PtClassExcel> data = list.stream().map(PtClassExcel::new).collect(Collectors.toList());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream, PtClassExcel.class).sheet().doWrite(Collections.emptyList());
        return new ByteArrayResource(outputStream.toByteArray());
    }
}
