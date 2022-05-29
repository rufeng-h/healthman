package com.rufeng.healthman.service;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.aop.OperLogRecord;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.enums.OperTypeEnum;
import com.rufeng.healthman.mapper.*;
import com.rufeng.healthman.pojo.data.PtMeasurementFormdata;
import com.rufeng.healthman.pojo.dto.ptmeasurement.MeasurementDetail;
import com.rufeng.healthman.pojo.dto.ptmeasurement.MeasurementInfo;
import com.rufeng.healthman.pojo.dto.ptmeasurement.MeasurementSubStatus;
import com.rufeng.healthman.pojo.dto.ptmeasurement.StuMeasurementDetail;
import com.rufeng.healthman.pojo.dto.ptscore.PtScoreInfo;
import com.rufeng.healthman.pojo.dto.ptstu.PtStudentBaseInfo;
import com.rufeng.healthman.pojo.dto.ptsubject.SubjectStatus;
import com.rufeng.healthman.pojo.m2m.PtMeasurementClass;
import com.rufeng.healthman.pojo.ptdo.*;
import com.rufeng.healthman.pojo.query.PtMeasurementQuery;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author rufeng
 * @time 2022-03-30 0:00
 * @package com.rufeng.healthman.service
 * @description 体测
 */
@Service
public class PtMeasurementService {
    private final PtCommonService ptCommonService;
    private final PtMeasurementMapper ptMeasurementMapper;
    private final PtSubjectSubgroupMapper ptSubjectSubgroupMapper;
    private final PtScoreMapper ptScoreMapper;
    private final PtStudentMapper ptStudentMapper;
    private final PtSubjectMapper ptSubjectMapper;
    private final PtClassMeasurementMapper ptClassMeasurementMapper;
    private final PtClassMapper ptClassMapper;
    private final PtSubgroupMapper ptSubgroupMapper;
    private final PtTeacherMapper ptTeacherMapper;

    public PtMeasurementService(PtCommonService ptCommonService,
                                PtMeasurementMapper ptMeasurementMapper,
                                PtSubjectSubgroupMapper ptSubjectSubgroupMapper,
                                PtScoreMapper ptScoreMapper,
                                PtStudentMapper ptStudentMapper,
                                PtSubjectMapper ptSubjectMapper,
                                PtClassMeasurementMapper ptClassMeasurementMapper,
                                PtClassMapper ptClassMapper,
                                PtSubgroupMapper ptSubgroupMapper,
                                PtTeacherMapper ptTeacherMapper) {
        this.ptCommonService = ptCommonService;
        this.ptMeasurementMapper = ptMeasurementMapper;
        this.ptSubjectSubgroupMapper = ptSubjectSubgroupMapper;
        this.ptScoreMapper = ptScoreMapper;
        this.ptStudentMapper = ptStudentMapper;
        this.ptSubjectMapper = ptSubjectMapper;
        this.ptClassMeasurementMapper = ptClassMeasurementMapper;
        this.ptClassMapper = ptClassMapper;
        this.ptSubgroupMapper = ptSubgroupMapper;
        this.ptTeacherMapper = ptTeacherMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public PtMeasurement addMesurement(PtMeasurementFormdata formdata) {
        String teacherId = ptCommonService.getCurrentTeacherId();
        PtMeasurement measurement = PtMeasurement.builder()
                .grpId(formdata.getGrpId())
                .msName(formdata.getMsName())
                .msCreatedAdmin(teacherId)
                .msDesp(formdata.getMsDesp())
                .build();
        ptMeasurementMapper.insertSelective(measurement);
        long msId = measurement.getMsId();
        List<PtClassMeasurement> list = formdata.getClsCodes().stream().map(code -> PtClassMeasurement.builder()
                .clsCode(code)
                .msId(msId).build()).collect(Collectors.toList());
        ptClassMeasurementMapper.batchInsertSelective(list);
        return measurement;
    }

    public ApiPage<MeasurementInfo> pageMeasurementInfo(Integer page, Integer pageSize, PtMeasurementQuery query) {
        PageHelper.startPage(page, pageSize);
        query.setTeaId(ptCommonService.getCurrentTeacherId());
        Page<PtMeasurement> measurements = ptMeasurementMapper.pageMeasurement(query);
        if (measurements.isEmpty()) {
            return ApiPage.empty(measurements);
        }
        List<MeasurementInfo> infoList = measurements.stream().map(MeasurementInfo::new).collect(Collectors.toList());
        List<Long> msIds = measurements.stream().map(PtMeasurement::getMsId).collect(Collectors.toList());
        /* 查teacher */
        List<PtTeacher> teachers = ptTeacherMapper.listByIds(measurements.stream()
                .map(PtMeasurement::getMsCreatedAdmin).collect(Collectors.toList()));
        Map<String, PtTeacher> aMap = teachers.stream().collect(Collectors.toMap(PtTeacher::getTeaId, a -> a));
        /* 科目数 */
        List<Long> grpIds = measurements.stream().map(PtMeasurement::getGrpId).collect(Collectors.toList());
        Map<Long, Integer> grpSubCount = ptSubjectSubgroupMapper.countSubByGrpIds(grpIds);
        Map<Long, String> grpNameMap = ptSubgroupMapper.mapGrpIdGrpNameByIds(grpIds);
        /* 班级 */
        List<PtMeasurementClass> classes = ptClassMeasurementMapper.listClsMeasurementByMsIds(msIds);
        Map<Long, MeasurementInfo> cMap = infoList.stream().collect(Collectors.toMap(MeasurementInfo::getMsId, s -> s));
        /* 查询学生数 */
        Map<Long, Integer> msStuCount = this.countStuByMsIds(msIds);
        /* 已完成测试的学生数 TODO */
        Map<Long, Integer> msCompStuCount = this.countCompStuByMsIds(msIds);
        classes.forEach(c -> cMap.get(c.getMsId()).setClasses(c.getClasses()));
        infoList.forEach(info -> {
            info.setGrpName(grpNameMap.get(info.getGrpId()));
            info.setSubCnt(grpSubCount.get(info.getGrpId()));
            info.setCompStuCnt(msCompStuCount.get(info.getMsId()));
            info.setMsCreatedAdminName(aMap.get(info.getMsCreatedAdminId()).getTeaName());
            info.setStuCnt(msStuCount.get(info.getMsId()));
        });
        return ApiPage.convert(measurements, infoList);
    }

    private Map<Long, Integer> countCompStuByMsIds(List<Long> msIds) {
        if (msIds.size() == 0) {
            return Collections.emptyMap();
        }
        return ptMeasurementMapper.countCompStuByMsIds(msIds);
    }

    private Map<Long, Integer> countStuByMsIds(List<Long> msIds) {
        if (msIds.size() == 0) {
            return Collections.emptyMap();
        }
        return ptMeasurementMapper.countStuByMsIds(msIds);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long msId) {
        /* 删除关联记录 */
        ptClassMeasurementMapper.deleteByMsId(msId);
        /* 删除成绩记录 */
        ptScoreMapper.deleteByMsId(msId);
        return ptMeasurementMapper.deleteByPrimaryKey(msId) == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateMeasurement(PtMeasurementFormdata formdata) {
        PtMeasurement measurement = PtMeasurement.builder()
                .grpId(formdata.getGrpId())
                .msId(formdata.getMsId())
                .msDesp(formdata.getMsDesp())
                .msName(formdata.getMsName())
                .msModified(LocalDateTime.now()).build();
        Long msId = measurement.getMsId();
        ptMeasurementMapper.updateByPrimaryKeySelective(measurement);
        /* 删除原来的关联记录 */
        ptClassMeasurementMapper.deleteByMsId(msId);
        /* 插入现在的关联记录 */
        List<PtClassMeasurement> list = formdata.getClsCodes().stream().map(
                code -> PtClassMeasurement.builder()
                        .msId(msId)
                        .clsCode(code)
                        .build()).collect(Collectors.toList());
        return ptClassMeasurementMapper.batchInsertSelective(list) != 0;
    }

    public MeasurementDetail getMeasurementDetail(Long msId) {
        PtMeasurement measurement = ptMeasurementMapper.selectByPrimaryKey(msId);
        /* 查teacher */
        PtTeacher admin = ptTeacherMapper.selectByPrimaryKey(measurement.getMsCreatedAdmin());
        /* 查科目组 */
        PtSubgroup subgroup = ptSubgroupMapper.selectByPrimaryKey(measurement.getGrpId());
        /* 查科目 */
        List<Long> subIds = ptSubjectSubgroupMapper.listSubIdByGrpId(subgroup.getGrpId());
        List<PtSubject> subjects = ptSubjectMapper.listSubjectByIds(subIds);
        /* 查班级 */
        List<PtClass> classes = ptClassMapper.listClassByMsId(msId);
        /* 查学生总人数 */
        int totalStuCnt = ptMeasurementMapper.countStuByMsId(msId);
        /* 查已完成人数 */
        int compStuCnt = ptMeasurementMapper.countCompStuByMsId(msId);
        return new MeasurementDetail(measurement, admin, subgroup, subjects, classes, totalStuCnt, compStuCnt);
    }


    public ApiPage<StuMeasurementDetail> pageStuMsDetail(Integer page, Integer pageSize, String stuId) {
        PageHelper.startPage(page, pageSize);
        Page<PtMeasurement> measurements = ptMeasurementMapper.pageStuMs(stuId);
        if (measurements.isEmpty()) {
            return ApiPage.empty(measurements);
        }
        List<Long> msIds = measurements.stream().map(PtMeasurement::getMsId).collect(Collectors.toList());
        /* 查询体测各科目完成情况 */
        List<MeasurementSubStatus> subStatuses = ptMeasurementMapper.listMsSubStatus(stuId, msIds);
        List<Long> subIds = subStatuses.stream().map(MeasurementSubStatus::getSubId).collect(Collectors.toList());
        List<PtSubject> subjects = ptSubjectMapper.listSubjectByIds(subIds);
        Map<Long, PtSubject> subMap = subjects.stream().collect(Collectors.toMap(PtSubject::getSubId, s -> s));
        /* 查分数 */
        List<PtScore> scores = ptScoreMapper.listScoreByStuIdAndMsIds(stuId, msIds);
        /* 查admin */
        List<String> teaIds = measurements.stream()
                .map(PtMeasurement::getMsCreatedAdmin).distinct().collect(Collectors.toList());
        List<PtTeacher> teachers = ptTeacherMapper.listByIds(teaIds);
        Map<String, PtTeacher> teacherMap = teachers.stream().collect(Collectors.toMap(PtTeacher::getTeaId, a -> a));
        /* 组装科目 */
        Map<Long, List<SubjectStatus>> resSubMap = new HashMap<>(10);
        subStatuses.forEach(status -> {
            List<SubjectStatus> subjectStatuses = resSubMap.computeIfAbsent(status.getMsId(), k -> new ArrayList<>());
            subjectStatuses.add(new SubjectStatus(subMap.get(status.getSubId()), status.getStatus()));
        });
        /* 组装分数 */
        Map<Long, List<PtScoreInfo>> resScoreMap = new HashMap<>(10);
        scores.forEach(s -> {
            List<PtScoreInfo> ptScoreInfos = resScoreMap.computeIfAbsent(s.getMsId(), k -> new ArrayList<>());
            ptScoreInfos.add(new PtScoreInfo(s, subMap.get(s.getSubId()).getSubName()));
        });
        List<StuMeasurementDetail> res = measurements.stream().map(m -> new StuMeasurementDetail(m,
                teacherMap.get(m.getMsCreatedAdmin()).getTeaName(),
                resSubMap.get(m.getMsId()),
                resScoreMap.get(m.getMsId()))).collect(Collectors.toList());
        return ApiPage.convert(measurements, res);
    }

    public PtMeasurement getMeasurement(long msId) {
        return ptMeasurementMapper.selectByPrimaryKey(msId);
    }

    public Resource excelTemplate(Long msId) {
        List<PtStudentBaseInfo> students = ptStudentMapper.listStuBaseInfoByMsId(msId);
        List<List<String>> data = students.stream().map(s -> List.of(s.getStuId())).collect(Collectors.toList());
        List<PtSubject> ptSubjects = ptSubjectMapper.listSubjectByMsId(msId);
        List<List<String>> header = ptSubjects.stream().map(s -> List.of(s.getSubName())).collect(Collectors.toList());
        header.add(0, List.of("学号"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream).head(header).sheet().doWrite(data);
        return new ByteArrayResource(outputStream.toByteArray());
    }
}
