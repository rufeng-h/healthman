package com.rufeng.healthman.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.mapper.PtMeasurementMapper;
import com.rufeng.healthman.pojo.DO.PtClassMesurement;
import com.rufeng.healthman.pojo.DO.PtMeasurement;
import com.rufeng.healthman.pojo.DTO.ptmeasurement.MeasurementInfo;
import com.rufeng.healthman.pojo.Query.PtMeasurementQuery;
import com.rufeng.healthman.pojo.data.PtMesurementFormdata;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public PtMesurementService(PtCommonService ptCommonService, PtMeasurementMapper ptMeasurementMapper, PtClassMeasurementService ptClassMeasurementService, PtSubjectSubGroupService ptSubjectSubGroupService) {
        this.ptCommonService = ptCommonService;
        this.ptMeasurementMapper = ptMeasurementMapper;
        this.ptClassMeasurementService = ptClassMeasurementService;
        this.ptSubjectSubGroupService = ptSubjectSubGroupService;
    }

    @Transactional(rollbackFor = Exception.class)
    public PtMeasurement addMesurement(PtMesurementFormdata formdata) {
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
        /* 为分页 */
        PageHelper.startPage(page, pageSize);
        Page<PtMeasurement> measurements = ptMeasurementMapper.pageMeasurement(query);
        /* 查info */
        List<Long> msIds = measurements.stream().map(PtMeasurement::getMsId).collect(Collectors.toList());
        List<MeasurementInfo> infoList = ptMeasurementMapper.pageMesurementInfo(msIds);
        /* 科目数 */
        List<Long> grpIds = measurements.stream().map(PtMeasurement::getGrpId).collect(Collectors.toList());
        Map<Long, Integer> grpSubCount = ptSubjectSubGroupService.countSubByGrpIds(grpIds);
        infoList.forEach(info -> info.setSubCnt(grpSubCount.get(info.getGrpId())));
        /* 查询班级数 */
        Map<Long, Integer> msClsCount = ptClassMeasurementService.countClsByMsIds(msIds);
        infoList.forEach(info -> info.setClsCnt(msClsCount.get(info.getMsId())));
        /* 查询学生数 */
        Map<Long, Integer> msStuCount = ptClassMeasurementService.countStuByMsIds(msIds);
        infoList.forEach(info -> info.setStuCnt(msStuCount.get(info.getMsId())));
        /* 已完成测试的学生数 */
        Map<Long, Integer> msCompStuCount = ptMeasurementMapper.countCompStuByMsIds(msIds);
        infoList.forEach(info -> info.setCompStuCnt(msCompStuCount.get(info.getMsId())));
        return ApiPage.convert(measurements, infoList);
    }

    public boolean deleteById(Long msId) {
        /* 删除关联记录 */
        ptClassMeasurementService.delByMsId(msId);
        return ptMeasurementMapper.deleteByPrimaryKey(msId) == 1;
    }
}
