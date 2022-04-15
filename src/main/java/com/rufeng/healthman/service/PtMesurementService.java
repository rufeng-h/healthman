package com.rufeng.healthman.service;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.mapper.PtMeasurementMapper;
import com.rufeng.healthman.pojo.data.PtMeasurementFormdata;
import com.rufeng.healthman.pojo.dto.ptmeasurement.MeasurementDetail;
import com.rufeng.healthman.pojo.dto.ptmeasurement.MeasurementInfo;
import com.rufeng.healthman.pojo.dto.ptmeasurement.MeasurementSubStatus;
import com.rufeng.healthman.pojo.dto.ptmeasurement.StuMeasurementDetail;
import com.rufeng.healthman.pojo.dto.ptscore.ScoreInfo;
import com.rufeng.healthman.pojo.dto.ptstu.StudentBaseInfo;
import com.rufeng.healthman.pojo.dto.ptsubject.SubjectStatus;
import com.rufeng.healthman.pojo.m2m.PtMeasurementClass;
import com.rufeng.healthman.pojo.ptdo.*;
import com.rufeng.healthman.pojo.query.PtMeasurementQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author rufeng
 * @time 2022-03-30 0:00
 * @package com.rufeng.healthman.service
 * @description 体测
 */
@Service
public class PtMesurementService {
    private final PtCommonService ptCommonService;
    private final PtMeasurementMapper ptMeasurementMapper;
    private final PtClassMeasurementService ptClassMeasurementService;
    private final PtSubjectSubGroupService ptSubjectSubGroupService;
    private final PtAdminService ptAdminService;
    private final PtSubgroupService ptSubgroupService;

    private PtSubjectService ptSubjectService;
    private PtScoreService ptScoreService;
    private PtStudentService ptStudentService;

    public PtMesurementService(PtCommonService ptCommonService,
                               PtMeasurementMapper ptMeasurementMapper,
                               PtClassMeasurementService ptClassMeasurementService,
                               PtSubjectSubGroupService ptSubjectSubGroupService,
                               PtAdminService ptAdminService,
                               PtSubgroupService ptSubgroupService) {
        this.ptCommonService = ptCommonService;
        this.ptMeasurementMapper = ptMeasurementMapper;
        this.ptClassMeasurementService = ptClassMeasurementService;
        this.ptSubjectSubGroupService = ptSubjectSubGroupService;
        this.ptAdminService = ptAdminService;
        this.ptSubgroupService = ptSubgroupService;
    }

    /**
     * 循环依赖
     * TODO
     */
    @Autowired
    public void setPtStudentService(PtStudentService ptStudentService) {
        this.ptStudentService = ptStudentService;
    }

    @Autowired
    public void setPtScoreService(PtScoreService ptScoreService) {
        this.ptScoreService = ptScoreService;
    }

    @Autowired
    public void setPtSubjectService(PtSubjectService ptSubjectService) {
        this.ptSubjectService = ptSubjectService;
    }

    @Transactional(rollbackFor = Exception.class)
    public PtMeasurement addMesurement(PtMeasurementFormdata formdata) {
        String adminId = ptCommonService.getCurrentUserId();
        PtMeasurement measurement = PtMeasurement.builder()
                .grpId(formdata.getGrpId())
                .msName(formdata.getMsName())
                .msCreatedAdmin(adminId)
                .msDesp(formdata.getMsDesp())
                .build();
        ptMeasurementMapper.insertSelective(measurement);
        long msId = measurement.getMsId();
        List<PtClassMesurement> list = formdata.getClsCodes().stream().map(code -> PtClassMesurement.builder()
                .clsCode(code)
                .cmsCreatedAdmin(adminId)
                .msId(msId).build()).collect(Collectors.toList());
        ptClassMeasurementService.batchInsertSelective(list);
        return measurement;
    }

    public ApiPage<MeasurementInfo> pageMeasurementInfo(Integer page, Integer pageSize, PtMeasurementQuery query) {
        PageHelper.startPage(page, pageSize);
        Page<PtMeasurement> measurements = ptMeasurementMapper.pageMeasurement(query);
        List<MeasurementInfo> infoList = measurements.stream().map(MeasurementInfo::new).collect(Collectors.toList());
        List<Long> msIds = measurements.stream().map(PtMeasurement::getMsId).collect(Collectors.toList());
        /* 查admin */
        List<PtAdmin> admins = ptAdminService.listAdminByIds(measurements.stream()
                .map(PtMeasurement::getMsCreatedAdmin).collect(Collectors.toList()));
        Map<String, PtAdmin> aMap = admins.stream().collect(Collectors.toMap(PtAdmin::getAdminId, a -> a));
        /* 科目数 */
        List<Long> grpIds = measurements.stream().map(PtMeasurement::getGrpId).collect(Collectors.toList());
        Map<Long, Integer> grpSubCount = ptSubjectSubGroupService.countSubByGrpIds(grpIds);
        Map<Long, String> grpNameMap = ptSubgroupService.mapGrpIdGrpNameByIds(grpIds);
        /* 班级 */
        List<PtMeasurementClass> classes = ptClassMeasurementService.listClsMeasurementByMsIds(msIds);
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
            info.setMsCreatedAdminName(aMap.get(info.getMsCreatedAdminId()).getAdminName());
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
        ptClassMeasurementService.delByMsId(msId);
        /* 删除成绩记录 */
        ptScoreService.deleteByMsId(msId);
        return ptMeasurementMapper.deleteByPrimaryKey(msId) == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateMeasurement(PtMeasurementFormdata formdata) {
        String adminId = ptCommonService.getCurrentUserId();
        PtMeasurement measurement = PtMeasurement.builder()
                .grpId(formdata.getGrpId())
                .msId(formdata.getMsId())
                .msDesp(formdata.getMsDesp())
                .msName(formdata.getMsName())
                .msModified(new Date()).build();
        Long msId = measurement.getMsId();
        ptMeasurementMapper.updateByPrimaryKeySelective(measurement);
        /* 删除原来的关联记录 */
        ptClassMeasurementService.delByMsId(msId);
        /* 插入现在的关联记录 */
        List<PtClassMesurement> list = formdata.getClsCodes().stream().map(
                code -> PtClassMesurement.builder()
                        .msId(msId)
                        .clsCode(code)
                        .cmsCreatedAdmin(adminId).build()).collect(Collectors.toList());
        return ptClassMeasurementService.batchInsertSelective(list) != 0;
    }

    public MeasurementDetail getMeasurementDetail(Long msId) {
        PtMeasurement measurement = ptMeasurementMapper.selectByPrimaryKey(msId);
        /* 查admin */
        PtAdmin admin = ptAdminService.getAdmin(measurement.getMsCreatedAdmin());
        /* 查科目组 */
        PtSubgroup subgroup = ptSubgroupService.getSubGrp(measurement.getGrpId());
        /* 查科目 */
        List<Long> subIds = ptSubjectSubGroupService.listSubIdByGrpId(subgroup.getGrpId());
        List<PtSubject> subjects = ptSubjectService.listSubject(subIds);
        /* 查班级 */
        List<PtClass> classes = ptClassMeasurementService.listClassByMsId(msId);
        /* 查学生总人数 */
        int totalStuCnt = ptMeasurementMapper.countStuByMsId(msId);
        /* 查已完成人数 */
        int compStuCnt = ptMeasurementMapper.countCompStuByMsId(msId);
        return new MeasurementDetail(measurement, admin, subgroup, subjects, classes, totalStuCnt, compStuCnt);
    }


    public List<PtMeasurement> listMeasurement(List<Long> msIds) {
        if (msIds.size() == 0) {
            return Collections.emptyList();
        }
        return ptMeasurementMapper.listMeasurement(msIds);
    }

    public ApiPage<StuMeasurementDetail> pageStuMsDetail(Integer page, Integer pageSize, String stuId) {
        PageHelper.startPage(page, pageSize);
        Page<PtMeasurement> measurements = ptMeasurementMapper.pageStuMs(stuId);
        List<Long> msIds = measurements.stream().map(PtMeasurement::getMsId).collect(Collectors.toList());
        /* 查询体测各科目完成情况 */
        List<MeasurementSubStatus> subStatuses = ptMeasurementMapper.listMsSubStatus(stuId, msIds);
        List<Long> subIds = subStatuses.stream().map(MeasurementSubStatus::getSubId).collect(Collectors.toList());
        List<PtSubject> subjects = ptSubjectService.listSubject(subIds);
        Map<Long, PtSubject> subMap = subjects.stream().collect(Collectors.toMap(PtSubject::getSubId, s -> s));
        /* 查分数 */
        List<PtScore> scores = ptScoreService.listScoreByStuIdAndMsIds(stuId, msIds);
        /* 查admin */
        List<String> adminIds = measurements.stream().map(PtMeasurement::getMsCreatedAdmin).distinct().collect(Collectors.toList());
        List<PtAdmin> admins = ptAdminService.listAdminByIds(adminIds);
        Map<String, PtAdmin> adminMap = admins.stream().collect(Collectors.toMap(PtAdmin::getAdminId, a -> a));
        /* 组装科目 */
        Map<Long, List<SubjectStatus>> resSubMap = new HashMap<>(10);
        subStatuses.forEach(status -> {
            List<SubjectStatus> subjectStatuses = resSubMap.computeIfAbsent(status.getMsId(), k -> new ArrayList<>());
            subjectStatuses.add(new SubjectStatus(subMap.get(status.getSubId()), status.getStatus()));
        });
        /* 组装分数 */
        Map<Long, List<ScoreInfo>> resScoreMap = new HashMap<>(10);
        scores.forEach(s -> {
            List<ScoreInfo> scoreInfos = resScoreMap.computeIfAbsent(s.getMsId(), k -> new ArrayList<>());
            scoreInfos.add(new ScoreInfo(s, subMap.get(s.getSubId()).getSubName()));
        });
        List<StuMeasurementDetail> res = measurements.stream().map(m -> new StuMeasurementDetail(m,
                adminMap.get(m.getMsCreatedAdmin()).getAdminName(),
                resSubMap.get(m.getMsId()),
                resScoreMap.get(m.getMsId()))).collect(Collectors.toList());
        return ApiPage.convert(measurements, res);
    }

    public Map<Long, Boolean> listStuMsStatus(String stuId) {
        return ptMeasurementMapper.listStuMsStatus(stuId);
    }

    public PtMeasurement getMeasurement(long msId) {
        return ptMeasurementMapper.selectByPrimaryKey(msId);
    }

    public Resource excelTemplate(Long msId) {
        List<StudentBaseInfo> students = ptStudentService.listStuBaseInfoByMsId(msId);
        List<List<String>> data = students.stream().map(s -> List.of(s.getStuId())).collect(Collectors.toList());
        List<PtSubject> ptSubjects = ptSubjectService.listSubject(msId);
        List<List<String>> header = ptSubjects.stream().map(s -> List.of(s.getSubName())).collect(Collectors.toList());
        header.add(0, List.of("学号"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream).head(header).sheet().doWrite(data);
        return new ByteArrayResource(outputStream.toByteArray());
    }
}
