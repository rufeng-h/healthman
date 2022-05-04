package com.rufeng.healthman.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.aop.OperLogRecord;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.enums.OperTypeEnum;
import com.rufeng.healthman.mapper.PtSubGroupShareMapper;
import com.rufeng.healthman.mapper.PtSubgroupMapper;
import com.rufeng.healthman.mapper.PtSubjectSubgroupMapper;
import com.rufeng.healthman.mapper.PtTeacherMapper;
import com.rufeng.healthman.pojo.data.PtSubGroupFormdata;
import com.rufeng.healthman.pojo.data.PtSubGrpShareFormdata;
import com.rufeng.healthman.pojo.dto.subgroup.SubGroupInfo;
import com.rufeng.healthman.pojo.m2m.PtSubGrpShareTeaId;
import com.rufeng.healthman.pojo.m2m.PtSubGrpSubject;
import com.rufeng.healthman.pojo.ptdo.PtSubGroupShare;
import com.rufeng.healthman.pojo.ptdo.PtSubgroup;
import com.rufeng.healthman.pojo.ptdo.PtSubject;
import com.rufeng.healthman.pojo.ptdo.PtSubjectSubgroup;
import com.rufeng.healthman.pojo.query.PtSubgroupQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    private final PtSubGroupShareMapper ptSubGroupShareMapper;
    private final PtTeacherMapper ptTeacherMapper;
    private final PtSubjectSubgroupMapper ptSubjectSubgroupMapper;

    public PtSubgroupService(PtSubgroupMapper ptSubgroupMapper,
                             PtCommonService ptCommonService,
                             PtSubGroupShareMapper ptSubGroupShareMapper,
                             PtTeacherMapper ptTeacherMapper, PtSubjectSubgroupMapper ptSubjectSubgroupMapper) {
        this.ptSubgroupMapper = ptSubgroupMapper;
        this.ptCommonService = ptCommonService;
        this.ptSubGroupShareMapper = ptSubGroupShareMapper;
        this.ptTeacherMapper = ptTeacherMapper;
        this.ptSubjectSubgroupMapper = ptSubjectSubgroupMapper;
    }

    public List<PtSubgroup> listSubGroup() {
        return ptSubgroupMapper.listSubGroup(ptCommonService.getCurrentTeacherId());
    }

    @OperLogRecord(description = "添加科目组", operType = OperTypeEnum.INSERT)
    @Transactional(rollbackFor = Exception.class)
    public PtSubgroup addSubGroup(PtSubGroupFormdata formdata) {
        List<Long> subIds = formdata.getSubIds();
        String teacherId = ptCommonService.getCurrentTeacherId();
        PtSubgroup subgroup = PtSubgroup.builder()
                .grpDesp(formdata.getGrpDesp())
                .grpName(formdata.getGrpName())
                .grpCreatedTeaId(teacherId)
                .build();
        ptSubgroupMapper.insertSelective(subgroup);
        Long grpId = subgroup.getGrpId();
        List<PtSubjectSubgroup> subjectSubgroups = subIds.stream().map(
                        subId -> PtSubjectSubgroup.builder()
                                .subId(subId)
                                .grpId(grpId).build())
                .collect(Collectors.toList());
        ptSubjectSubgroupMapper.batchInsertSelective(subjectSubgroups);
        return subgroup;
    }

    /**
     * 查询所有，自己创建的和他人分享的
     */
    private ApiPage<SubGroupInfo> pageAllSubgroupInfo(PtSubgroupQuery query) {
        Page<SubGroupInfo> groupInfos = ptSubgroupMapper.pageAllSubGroup(query);
        List<String> teaIds = groupInfos.stream().map(SubGroupInfo::getGrpCreatedTeaId).collect(Collectors.toList());
        Map<String, String> teaMap = ptTeacherMapper.mapTeaNameByIds(teaIds);
        groupInfos.forEach(g -> g.setGrpCreatedTeaName(teaMap.get(g.getGrpCreatedTeaId())));
        /* 查教师 */
        return ApiPage.convert(groupInfos);
    }

    private ApiPage<SubGroupInfo> pageOwnerSubGroupInfo(PtSubgroupQuery query) {
        Page<PtSubgroup> subgroups = ptSubgroupMapper.pageSubGroup(query);
        List<SubGroupInfo> groupInfos = subgroups.stream()
                .map(s -> new SubGroupInfo(s, ptCommonService.getCurrentUserName())).collect(Collectors.toList());
        return ApiPage.convert(subgroups, groupInfos);
    }

    public ApiPage<SubGroupInfo> pageSubGroupInfo(Integer page, Integer pageSize, PtSubgroupQuery query) {
        query.setTeaId(ptCommonService.getCurrentTeacherId());
        PageHelper.startPage(page, pageSize);
        ApiPage<SubGroupInfo> groupInfos = query.getSelf() ?
                this.pageOwnerSubGroupInfo(query) : this.pageAllSubgroupInfo(query);
        if (groupInfos.getItems().isEmpty()) {
            return groupInfos;
        }
        /* 查询科目 */
        List<Long> grpIds = groupInfos.getItems().stream().map(SubGroupInfo::getGrpId).collect(Collectors.toList());
        List<PtSubGrpSubject> subGrpSubjects = ptSubjectSubgroupMapper.listSubGrpSubject(grpIds);
        Map<Long, List<PtSubject>> map = subGrpSubjects.stream().collect(
                Collectors.toMap(PtSubGrpSubject::getGrpId, PtSubGrpSubject::getSubjects));
        /* 已分享对象 */
        /* TODO 开启对话框时获取 */
        List<PtSubGrpShareTeaId> shareTeaIds = ptSubGroupShareMapper.listSharedTeaIds(grpIds);
        Map<Long, List<String>> sharedTo = shareTeaIds.stream()
                .collect(Collectors.toMap(PtSubGrpShareTeaId::getGrpId, PtSubGrpShareTeaId::getTeaIds));
        groupInfos.getItems().forEach(g -> {
            g.setSubjects(map.get(g.getGrpId()));
            g.setSharedTeaIds(sharedTo.get(g.getGrpId()));
        });
        return groupInfos;
    }

    @OperLogRecord(description = "删除科目组", operType = OperTypeEnum.DELETE)
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteGrp(Long grpId) {
        ptSubjectSubgroupMapper.deleteByGrpId(grpId);
        return ptSubgroupMapper.deleteByPrimaryKey(grpId) == 1;
    }

    /**
     * 应该主键删除 TODO
     */
    @OperLogRecord(description = "从科目组删除科目", operType = OperTypeEnum.DELETE)
    public boolean deleteSub(Long grpId, Long subId) {
        return ptSubjectSubgroupMapper.deleteByGrpIdAndSubId(grpId, subId) == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean shareSubGrp(PtSubGrpShareFormdata formdata) {
        String teacherId = ptCommonService.getCurrentTeacherId();
        List<PtSubGroupShare> raw = ptSubGroupShareMapper.selectByShareTeaId(teacherId);
        Set<PtSubGroupShare> rawSet = new HashSet<>(raw);
        Set<PtSubGroupShare> curSet = formdata.getTeaIds().stream().map(id ->
                PtSubGroupShare
                        .builder()
                        .shareTeaId(teacherId)
                        .teaId(id)
                        .grpId(formdata.getGrpId())
                        .build()).collect(Collectors.toSet());
        rawSet.removeAll(curSet);
        if (rawSet.size() > 0) {
            ptSubGroupShareMapper.deleteByIds(rawSet.stream().map(PtSubGroupShare::getSid).collect(Collectors.toList()));
        }
        rawSet = new HashSet<>(raw);
        curSet.removeAll(rawSet);
        if (curSet.size() > 0) {
            ptSubGroupShareMapper.batchInsertSelective(new ArrayList<>(curSet));
        }
        return true;
    }
}
