package com.rufeng.healthman.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.mapper.PtMeasurementMapper;
import com.rufeng.healthman.pojo.DO.*;
import com.rufeng.healthman.pojo.DTO.ptmeasurement.MeasurementDetail;
import com.rufeng.healthman.pojo.DTO.ptmeasurement.MeasurementInfo;
import com.rufeng.healthman.pojo.Query.PtMeasurementQuery;
import com.rufeng.healthman.pojo.data.PtMeasurementFormdata;
import com.rufeng.healthman.pojo.m2m.PtMeasurementClass;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author rufeng
 * @time 2022-03-30 0:00
 * @package com.rufeng.healthman.service
 * @description TODO
 */
@Service
public class PtMesurementService {
    private final PtCommonService ptCommonService;
    private final PtMeasurementMapper ptMeasurementMapper;
    private final PtClassMeasurementService ptClassMeasurementService;
    private final PtSubjectSubGroupService ptSubjectSubGroupService;
    private final PtScoreService ptScoreService;
    private final PtAdminService ptAdminService;
    private final PtSubgroupService ptSubgroupService;
    private final PtClassService ptClassService;
    private final PtSubjectService ptSubjectService;

    public PtMesurementService(PtCommonService ptCommonService, PtMeasurementMapper ptMeasurementMapper, PtClassMeasurementService ptClassMeasurementService, PtSubjectSubGroupService ptSubjectSubGroupService, PtScoreService ptScoreService, PtAdminService ptAdminService, PtSubgroupService ptSubgroupService, PtClassService ptClassService, PtSubjectService ptSubjectService) {
        this.ptCommonService = ptCommonService;
        this.ptMeasurementMapper = ptMeasurementMapper;
        this.ptClassMeasurementService = ptClassMeasurementService;
        this.ptSubjectSubGroupService = ptSubjectSubGroupService;
        this.ptScoreService = ptScoreService;
        this.ptAdminService = ptAdminService;
        this.ptSubgroupService = ptSubgroupService;
        this.ptClassService = ptClassService;
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
        infoList.forEach(info -> info.setMsCreatedAdminName(aMap.get(info.getMsCreatedAdminId()).getAdminName()));
        /* 科目数 */
        List<Long> grpIds = measurements.stream().map(PtMeasurement::getGrpId).collect(Collectors.toList());
        Map<Long, Integer> grpSubCount = ptSubjectSubGroupService.countSubByGrpIds(grpIds);
        infoList.forEach(info -> info.setSubCnt(grpSubCount.get(info.getGrpId())));
        /* 班级 */
        List<PtMeasurementClass> classes = ptClassMeasurementService.listClsMeasurementByMsIds(msIds);
        Map<Long, MeasurementInfo> cMap = infoList.stream().collect(Collectors.toMap(MeasurementInfo::getMsId, s -> s));
        classes.forEach(c -> cMap.get(c.getMsId()).setClasses(c.getClasses()));
        /* 查询学生数 */
        Map<Long, Integer> msStuCount = ptClassMeasurementService.countStuByMsIds(msIds);
        infoList.forEach(info -> info.setStuCnt(msStuCount.get(info.getMsId())));
        /* 已完成测试的学生数 TODO */
        Map<Long, Integer> msCompStuCount = ptMeasurementMapper.countCompStuByMsIds(msIds);
        infoList.forEach(info -> info.setCompStuCnt(msCompStuCount.get(info.getMsId())));
        return ApiPage.convert(measurements, infoList);
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
        Map<String, Integer> stuCntMap = ptClassService.countStudent(classes.stream().map(PtClass::getClsCode).collect(Collectors.toList()));
        int totalStuCnt = stuCntMap.values().stream().reduce(0, Integer::sum);
        /* 查已完成人数 */
        int compStuCnt = ptMeasurementMapper.countCompStuByMsId(msId);
        return new MeasurementDetail(measurement, admin, subgroup, subjects, classes, totalStuCnt, compStuCnt);
    }
}
