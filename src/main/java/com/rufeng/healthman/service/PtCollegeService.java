package com.rufeng.healthman.service;

import com.alibaba.excel.EasyExcel;
import com.rufeng.healthman.mapper.PtCollegeMapper;
import com.rufeng.healthman.pojo.ptdo.PtAdmin;
import com.rufeng.healthman.pojo.ptdo.PtClass;
import com.rufeng.healthman.pojo.ptdo.PtCollege;
import com.rufeng.healthman.pojo.file.PtCollegeExcel;
import com.rufeng.healthman.pojo.file.PtCollegeExcelListener;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author rufeng
 * @time 2022-03-12 10:18
 * @package com.rufeng.healthman.service.impl
 * @description 学院
 */
@Service
public class PtCollegeService {
    private final PtCollegeMapper ptCollegeMapper;

    public PtCollegeService(PtCollegeMapper ptCollegeMapper) {
        this.ptCollegeMapper = ptCollegeMapper;
    }


    public List<PtCollege> listCollege() {
        return ptCollegeMapper.listCollege();
    }

    public PtCollege getCollege(String clgCode) {
        return ptCollegeMapper.getCollege(clgCode);
    }

    public Integer uploadCollege(MultipartFile file) {
        PtCollegeExcelListener listener = new PtCollegeExcelListener(this);
        try {
            EasyExcel.read(file.getInputStream(), PtCollegeExcel.class, listener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listener.getHandledCount();
    }


    public int addCollegeSelective(List<PtCollegeExcel> cachedDataList) {
        if (cachedDataList.size() == 0) {
            return 0;
        }
        return ptCollegeMapper.batchInsertSelective(cachedDataList);
    }


    public Resource fileTemplate() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream, PtCollegeExcel.class).sheet()
                .doWrite(Collections.emptyList());
        return new ByteArrayResource(outputStream.toByteArray());
    }

    public Map<String, String> mapClgNameByIds(List<String> collect) {
        if (collect.size() == 0) {
            return Collections.emptyMap();
        }
        return ptCollegeMapper.mapClgNameByIds(collect);
    }

    public List<String> getClsCodeFromAdmins(List<PtAdmin> admins) {
        List<String> adminClsCodes = new ArrayList<>();
        admins.stream().map(PtAdmin::getClgCode).forEach(clgCode -> {
            if (clgCode != null) {
                adminClsCodes.add(clgCode);
            }
        });
        return adminClsCodes;
    }

    public List<String> getClsCodeFromClasses(List<PtClass> classes) {
        List<String> clgCodes = new ArrayList<>();
        classes.stream().map(PtClass::getClgCode).forEach(clgCode -> {
            if (clgCode != null) {
                clgCodes.add(clgCode);
            }
        });
        return clgCodes;
    }
}
