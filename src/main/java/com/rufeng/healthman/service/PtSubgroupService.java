package com.rufeng.healthman.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.aop.OperLogRecord;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.enums.OperTypeEnum;
import com.rufeng.healthman.mapper.PtSubgroupMapper;
import com.rufeng.healthman.pojo.data.PtSubGroupFormdata;
import com.rufeng.healthman.pojo.dto.subgroup.SubGroupInfo;
import com.rufeng.healthman.pojo.m2m.PtSubGrpSubject;
import com.rufeng.healthman.pojo.ptdo.PtSubgroup;
import com.rufeng.healthman.pojo.ptdo.PtSubject;
import com.rufeng.healthman.pojo.ptdo.PtSubjectSubgroup;
import com.rufeng.healthman.pojo.ptdo.PtTeacher;
import com.rufeng.healthman.pojo.query.PtSubgroupQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author rufeng
 * @time 2022-03-27 19:13
 * @package com.rufeng.healthman.service
 * @description 科目组
 */

@Service
public class PtSubgroupService {

    private final PtSubgroupMapper ptSubgroupMapper;
    private final PtCommonService ptCommonService;
    private final PtSubjectSubGroupService ptSubjectSubGroupService;
    private final PtTeacherService ptTeacherService;

    public PtSubgroupService(PtSubgroupMapper ptSubgroupMapper,
                             PtCommonService ptCommonService,
                             PtSubjectSubGroupService ptSubjectSubGroupService,
                             PtTeacherService ptTeacherService) {
        this.ptSubgroupMapper = ptSubgroupMapper;
        this.ptCommonService = ptCommonService;
        this.ptSubjectSubGroupService = ptSubjectSubGroupService;
        this.ptTeacherService = ptTeacherService;
    }

    public List<PtSubgroup> listSubGroup() {
        return ptSubgroupMapper.listSubGroup();
    }

    @OperLogRecord(description = "添加科目组", operType = OperTypeEnum.INSERT)
    @Transactional(rollbackFor = Exception.class)
    public PtSubgroup addSubGroup(PtSubGroupFormdata formdata) {
        List<Long> subIds = formdata.getSubIds();
        String userId = ptCommonService.getCurrentUserId();
        PtSubgroup subgroup = PtSubgroup.builder()
                .grpDesp(formdata.getGrpDesp())
                .grpName(formdata.getGrpName())
                .grpCreatedAdmin(userId)
                .build();
        ptSubgroupMapper.insertSelective(subgroup);
        Long grpId = subgroup.getGrpId();
        List<PtSubjectSubgroup> subjectSubgroups = subIds.stream().map(
                        subId -> PtSubjectSubgroup.builder()
                                .subId(subId).subGrpAdmin(userId)
                                .grpId(grpId).build())
                .collect(Collectors.toList());
        ptSubjectSubGroupService.batchInsertSelective(subjectSubgroups);
        return subgroup;
    }

    public ApiPage<SubGroupInfo> pageSubGroupInfo(Integer page, Integer pageSize, PtSubgroupQuery query) {
        PageHelper.startPage(page, pageSize);
        /* 为分页 */
        Page<PtSubgroup> subgroups = ptSubgroupMapper.pageSubGroup(query);
        /* 查询 */
        List<Long> grpIds = subgroups.stream().map(PtSubgroup::getGrpId).collect(Collectors.toList());
        List<PtSubGrpSubject> subGrpSubjects = ptSubjectSubGroupService.listSubGrpSubject(grpIds);
        Map<Long, List<PtSubject>> map = subGrpSubjects.stream().collect(
                Collectors.toMap(PtSubGrpSubject::getGrpId, PtSubGrpSubject::getSubjects));
        List<String> adminIds = subgroups.stream().map(PtSubgroup::getGrpCreatedAdmin)
                .collect(Collectors.toList());
        List<PtTeacher> admins = ptTeacherService.listByIds(adminIds);
        Map<String, String> aMap = admins.stream().collect(
                Collectors.toMap(PtTeacher::getTeaId, PtTeacher::getTeaName));
        List<SubGroupInfo> groupInfos = subgroups.stream()
                .map(s -> new SubGroupInfo(s, aMap.get(s.getGrpCreatedAdmin()),
                        map.get(s.getGrpId()))).collect(Collectors.toList());
        return ApiPage.convert(subgroups, groupInfos);
    }

    public PtSubgroup getSubGrp(Long grpId) {
        return ptSubgroupMapper.selectByPrimaryKey(grpId);
    }

    public Map<Long, String> mapGrpIdGrpNameByIds(List<Long> grpIds) {
        if (grpIds.size() == 0) {
            return Collections.emptyMap();
        }
        return ptSubgroupMapper.mapGrpIdGrpNameByIds(grpIds);
    }

    @OperLogRecord(description = "删除科目组", operType = OperTypeEnum.DELETE)
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteGrp(Long grpId) {
        ptSubjectSubGroupService.deleteByGrpId(grpId);
        return ptSubgroupMapper.deleteByPrimaryKey(grpId) == 1;
    }
}
