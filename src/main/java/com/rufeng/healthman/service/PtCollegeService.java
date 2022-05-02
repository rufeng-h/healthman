package com.rufeng.healthman.service;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.mapper.PtCollegeMapper;
import com.rufeng.healthman.mapper.PtTeacherMapper;
import com.rufeng.healthman.pojo.data.PtCollegeFomrdata;
import com.rufeng.healthman.pojo.dto.ptcollege.PtCollegePageInfo;
import com.rufeng.healthman.pojo.file.PtCollegeExcel;
import com.rufeng.healthman.pojo.file.PtCollegeExcelListener;
import com.rufeng.healthman.pojo.ptdo.PtClass;
import com.rufeng.healthman.pojo.ptdo.PtCollege;
import com.rufeng.healthman.pojo.ptdo.PtTeacher;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author rufeng
 * @time 2022-03-12 10:18
 * @package com.rufeng.healthman.service.impl
 * @description 学院
 */
@Service
public class PtCollegeService {
    private final PtCollegeMapper ptCollegeMapper;
    private final PtTeacherMapper ptTeacherMapper;

    public PtCollegeService(PtCollegeMapper ptCollegeMapper, PtTeacherMapper ptTeacherMapper) {
        this.ptCollegeMapper = ptCollegeMapper;
        this.ptTeacherMapper = ptTeacherMapper;
    }

    public static List<String> getClgCodeFromClasses(List<PtClass> classes) {
        List<String> clgCodes = new ArrayList<>();
        classes.stream().map(PtClass::getClgCode).forEach(clgCode -> {
            if (clgCode != null) {
                clgCodes.add(clgCode);
            }
        });
        return clgCodes;
    }

    public static List<String> getClgCodeFromTeachers(List<PtTeacher> teachers) {
        List<String> clgCodes = new ArrayList<>();
        teachers.stream().map(PtTeacher::getClgCode).forEach(clgCode -> {
            if (clgCode != null) {
                clgCodes.add(clgCode);
            }
        });
        return clgCodes;
    }

    public List<PtCollege> listCollege() {
        return ptCollegeMapper.listCollege();
    }

    public PtCollege getCollege(@Nullable String clgCode) {
        if (clgCode == null) {
            return null;
        }
        return ptCollegeMapper.selectByPrimaryKey(clgCode);
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

    public ApiPage<PtCollegePageInfo> pageCollegeInfo(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        Page<PtCollege> colleges = ptCollegeMapper.page();
        if (colleges.isEmpty()) {
            return ApiPage.empty(colleges);
        }
        List<String> clgCodes = colleges.stream().map(PtCollege::getClgCode).collect(Collectors.toList());
        List<PtTeacher> teachers = ptTeacherMapper.listPrincipal(clgCodes);
        Map<String, PtTeacher> teacherMap = teachers.stream().collect(Collectors.toMap(PtTeacher::getClgCode, t -> t));
        List<PtCollegePageInfo> infos = colleges.stream().map(c -> new PtCollegePageInfo(c,
                teacherMap.get(c.getClgCode()))).collect(Collectors.toList());
        return ApiPage.convert(colleges, infos);
    }

    public boolean deleteCollege(String clgCode) {
        return ptCollegeMapper.deleteByPrimaryKey(clgCode) == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateCollege(PtCollegeFomrdata collegeFomrdata) {
        PtCollege ptCollege = PtCollege.builder()
                .clgCode(collegeFomrdata.getClgCode())
                .clgHome(collegeFomrdata.getClgHome())
                .clgName(collegeFomrdata.getClgName())
                .clgTel(collegeFomrdata.getClgTel())
                .clgOffice(collegeFomrdata.getClgOffice())
                .clgModified(LocalDateTime.now()).build();
        /* 原负责人 */
        PtTeacher teacher = ptTeacherMapper.selectPrincipal(collegeFomrdata.getClgCode());
        if (teacher != null) {
            teacher.setPrincipal(false);
            ptTeacherMapper.updateByPrimaryKeySelective(teacher);
        }
        /* 新负责人 */
        PtTeacher ptTeacher = PtTeacher.builder()
                .teaId(collegeFomrdata.getTeaId())
                .principal(true).build();
        ptTeacherMapper.updateByPrimaryKeySelective(ptTeacher);
        /* 更新学院 */
        return ptCollegeMapper.updateByPrimaryKeySelective(ptCollege) == 1;
    }
}
