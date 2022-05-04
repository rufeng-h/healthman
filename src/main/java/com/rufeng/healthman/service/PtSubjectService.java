package com.rufeng.healthman.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.mapper.PtCompetencyMapper;
import com.rufeng.healthman.mapper.PtScoreSheetMapper;
import com.rufeng.healthman.mapper.PtSubStudentMapper;
import com.rufeng.healthman.mapper.PtSubjectMapper;
import com.rufeng.healthman.pojo.data.PtSubjectFormdata;
import com.rufeng.healthman.pojo.dto.ptscoresheet.SubStudent;
import com.rufeng.healthman.pojo.dto.ptsubject.SubjectDetail;
import com.rufeng.healthman.pojo.dto.ptsubject.SubjectInfo;
import com.rufeng.healthman.pojo.ptdo.PtCompetency;
import com.rufeng.healthman.pojo.ptdo.PtScoreSheet;
import com.rufeng.healthman.pojo.ptdo.PtSubject;
import com.rufeng.healthman.pojo.query.PtSubjectQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author rufeng
 * @time 2022-03-17 18:44
 * @package com.rufeng.healthman.service.impl
 * @description 科目
 */
@Service
public class PtSubjectService {
    private final PtSubjectMapper ptSubjectMapper;
    private final PtSubStudentMapper ptSubStudentMapper;
    private final PtCompetencyMapper ptCompetencyMapper;
    private final PtScoreSheetMapper ptScoreSheetMapper;

    public PtSubjectService(PtSubjectMapper ptSubjectMapper,
                            PtSubStudentMapper ptSubStudentMapper,
                            PtCompetencyMapper ptCompetencyMapper,
                            PtScoreSheetMapper ptScoreSheetMapper) {
        this.ptSubjectMapper = ptSubjectMapper;
        this.ptScoreSheetMapper = ptScoreSheetMapper;
        this.ptSubStudentMapper = ptSubStudentMapper;
        this.ptCompetencyMapper = ptCompetencyMapper;
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean addSubject(PtSubjectFormdata data) {
        PtSubject subject = new PtSubject();
        subject.setSubDesp(data.getSubDesp());
        subject.setSubName(data.getSubName());
        subject.setCompId(data.getCompId());
        ptSubjectMapper.insertSelective(subject);
        Long subId = subject.getSubId();
        List<SubStudent> subStudents = data.getSubStudents();
        subStudents.forEach(item -> item.setSubId(subId));
        return ptSubStudentMapper.batchInsertSelective(subStudents) == subStudents.size();
    }

    /**
     * 对应测试年级性别修改，删除其评分标准 TODO
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSubject(PtSubjectFormdata data) {
        Long subId = data.getSubId();
        PtSubject subject = PtSubject.builder()
                .subDesp(data.getSubDesp())
                .subId(subId)
                .subName(data.getSubName())
                .compId(data.getCompId())
                .build();
        /* 处理年级、性别 */
        List<SubStudent> subStudents = data.getSubStudents();
        subStudents.forEach(item -> item.setSubId(subId));
        List<SubStudent> subStus = ptSubStudentMapper.listSubStudentBySubId(subId);
        Set<SubStudent> prev = new HashSet<>(subStus);
        Set<SubStudent> cur = new HashSet<>(subStudents);
        prev.removeAll(cur);
        if (prev.size() != 0) {
            ptSubStudentMapper.deleteByIds(prev.stream().map(SubStudent::getId).collect(Collectors.toList()));
            /* 删除评分标准 TODO 数据库设计问题 */
            List<PtScoreSheet> scoreSheets = ptScoreSheetMapper.listBySubId(subId);
            /* 找出要删的id */
            List<Long> ids = new ArrayList<>();
            for (PtScoreSheet s : scoreSheets) {
                if (prev.contains(new SubStudent(s.getGrade(), s.getGender(), s.getSubId()))) {
                    ids.add(s.getId());
                }
            }
            ptScoreSheetMapper.deleteByIds(ids);
        }
        prev = new HashSet<>(subStus);
        cur.removeAll(prev);
        if (cur.size() != 0) {
            ptSubStudentMapper.batchInsertSelective(new ArrayList<>(cur));
        }
        return ptSubjectMapper.updateByPrimaryKeySelective(subject) == 1;
    }

    public ApiPage<SubjectInfo> pageSubjectInfo(Integer page, Integer pageSize, PtSubjectQuery query) {
        List<Long> gradeSubIds = new ArrayList<>();
        if (query.getGrade() != null) {
            /* 符合年级的科目id */
            gradeSubIds = ptSubStudentMapper.listSubIdsByGrade(query.getGrade().getValue());
        }
        /* 科目主体 */
        PageHelper.startPage(page, pageSize);
        Page<PtSubject> subjects = ptSubjectMapper.pageSubjectByQueryAndSubIds(query, gradeSubIds);
        if (subjects.isEmpty()) {
            return ApiPage.empty(subjects);
        }
        /* 运动能力 */
        List<Long> compIds = subjects.stream().map(PtSubject::getCompId).distinct().collect(Collectors.toList());
        List<PtCompetency> ptCompetencies = ptCompetencyMapper.listCompByIds(compIds);
        Map<Long, String> compMap = ptCompetencies.stream()
                .collect(Collectors.toMap(PtCompetency::getCompId, PtCompetency::getCompName));
        /* 科目对应需要测试的标准 */
        List<Long> subIds = subjects.stream().map(PtSubject::getSubId).collect(Collectors.toList());
        List<SubStudent> subStudents = ptSubStudentMapper.listSubStudentBySubIds(subIds);
        Map<Long, List<SubStudent>> subStuMap = new HashMap<>(10);
        subStudents.forEach(s -> subStuMap.computeIfAbsent(s.getSubId(), (sh) -> new ArrayList<>()).add(s));
        List<Long> ids = ptScoreSheetMapper.listSubIdBySubIds(subIds);
        Map<Long, Boolean> hasScoreMap = subIds.stream().collect(Collectors.toMap(id -> id, ids::contains));
        /* 组装 */
        List<SubjectInfo> infos = subjects.stream().map(s -> new SubjectInfo(
                s, compMap.get(s.getCompId()), subStuMap.get(s.getSubId()),
                hasScoreMap.get(s.getSubId()))).collect(Collectors.toList());
        return ApiPage.convert(subjects, infos);
    }

    public SubjectDetail getSubjectDetail(Long subId) {
        PtSubject subject = ptSubjectMapper.selectByPrimaryKey(subId);
        List<String> levels = ptSubjectMapper.listLevels(subject.getSubId());
        PtCompetency competency = ptCompetencyMapper.selectByPrimaryKey(subject.getCompId());
        return new SubjectDetail(subject, competency == null ? null : competency.getCompName(), levels);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSubject(Long subId) {
        /* 删除科目对应测试学生 */
        ptSubStudentMapper.deleteBySubId(subId);
        /* 删除评分标准 */
        ptScoreSheetMapper.deleteBySubId(subId);
        return ptSubjectMapper.deleteByPrimaryKey(subId) == 1;
    }

}
