package com.rufeng.healthman.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.mapper.PtSubjectMapper;
import com.rufeng.healthman.pojo.data.PtSubjectFormdata;
import com.rufeng.healthman.pojo.dto.ptscoresheet.SubStudent;
import com.rufeng.healthman.pojo.dto.ptsubject.SubjectDetail;
import com.rufeng.healthman.pojo.dto.ptsubject.SubjectInfo;
import com.rufeng.healthman.pojo.ptdo.PtCompetency;
import com.rufeng.healthman.pojo.ptdo.PtMeasurement;
import com.rufeng.healthman.pojo.ptdo.PtScoreSheet;
import com.rufeng.healthman.pojo.ptdo.PtSubject;
import com.rufeng.healthman.pojo.query.PtSubjectQuery;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final PtCompetencyService ptCompetencyService;
    private final PtSubStudentService ptSubStudentService;
    private final PtScoreSheetService ptScoreSheetService;
    private final PtSubjectSubGroupService ptSubjectSubGroupService;
    private PtMesurementService ptMesurementService;

    public PtSubjectService(PtSubjectMapper ptSubjectMapper,
                            PtCompetencyService ptCompetencyService,
                            PtSubStudentService ptSubStudentService,
                            PtScoreSheetService ptScoreSheetService,
                            PtSubjectSubGroupService ptSubjectSubGroupService) {
        this.ptSubjectMapper = ptSubjectMapper;
        this.ptCompetencyService = ptCompetencyService;
        this.ptSubStudentService = ptSubStudentService;
        this.ptScoreSheetService = ptScoreSheetService;
        this.ptSubjectSubGroupService = ptSubjectSubGroupService;
    }

    /**
     * 循环依赖
     * TODO
     */
    @Autowired
    public void setPtMesurementService(PtMesurementService ptMesurementService) {
        this.ptMesurementService = ptMesurementService;
    }

    public List<PtSubject> listSubject() {
        return ptSubjectMapper.listSubject();
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
        return ptSubStudentService.addSubStuSelective(subStudents) == subStudents.size();
    }

    /**
     * 对应测试年级性别修改，删除其评分标准 TODO
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSubject(PtSubjectFormdata data) {
        Long subId = data.getSubId();
        PtSubject subject = new PtSubject();
        subject.setSubDesp(data.getSubDesp());
        subject.setSubName(data.getSubName());
        subject.setCompId(data.getCompId());
        subject.setSubId(subId);
        /* 处理年级、性别 */
        List<SubStudent> subStudents = data.getSubStudents();
        subStudents.forEach(item -> item.setSubId(subId));
        List<SubStudent> subStus = ptSubStudentService.listSubStudentBySubId(subId);
        Set<SubStudent> prev = new HashSet<>(subStus);
        Set<SubStudent> cur = new HashSet<>(subStudents);
        prev.removeAll(cur);
        if (prev.size() != 0) {
            ptSubStudentService.deleteByIds(prev.stream().map(SubStudent::getId).collect(Collectors.toList()));
            /* 删除评分标准 TODO 数据库设计问题 */
            List<PtScoreSheet> scoreSheets = ptScoreSheetService.listScoreSheetBySubId(subId);
            /* 找出要删的id */
            List<Long> ids = new ArrayList<>();
            for (PtScoreSheet s : scoreSheets) {
                if (prev.contains(new SubStudent(s.getGrade(), s.getGender(), s.getSubId()))) {
                    ids.add(s.getId());
                }
            }
            ptScoreSheetService.deleteByIds(ids);
        }
        prev = new HashSet<>(subStus);
        cur.removeAll(prev);
        if (cur.size() != 0) {
            ptSubStudentService.addSubStuSelective(new ArrayList<>(cur));
        }
        return ptSubjectMapper.updateByPrimaryKeySelective(subject) == 1;
    }

    public List<PtSubject> listSubject(List<Long> subIds) {
        if (subIds.size() == 0) {
            return Collections.emptyList();
        }
        return ptSubjectMapper.listSubjectByIds(subIds);
    }

    public Map<Long, String> mapSubIdSubNameByIds(List<Long> subIds) {
        if (subIds.size() == 0) {
            return Collections.emptyMap();
        }
        return ptSubjectMapper.mapSubIdSubNameByIds(subIds);
    }

    public ApiPage<SubjectInfo> pageSubjectInfo(Integer page, Integer pageSize, PtSubjectQuery query) {
        /* 符合年级的科目id */
        List<Long> gradeSubIds = ptSubStudentService.listSubIdsByGrade(query.getGrade());
        /* 科目主体 */
        PageHelper.startPage(page, pageSize);
        Page<PtSubject> subjects = ptSubjectMapper.pageSubjectByQueryAndSubIds(query, gradeSubIds);
        /* 运动能力 */
        List<Long> compIds = subjects.stream().map(PtSubject::getCompId).distinct().collect(Collectors.toList());
        List<PtCompetency> ptCompetencies = ptCompetencyService.listCompByIds(compIds);
        Map<Long, String> compMap = ptCompetencies.stream()
                .collect(Collectors.toMap(PtCompetency::getCompId, PtCompetency::getCompName));
        /* 科目对应需要测试的标准 */
        List<Long> subIds = subjects.stream().map(PtSubject::getSubId).collect(Collectors.toList());
        List<SubStudent> subStudents = ptSubStudentService.listSubStudentBySubIds(subIds);
        Map<Long, List<SubStudent>> subStuMap = new HashMap<>(10);
        subStudents.forEach(s -> subStuMap.computeIfAbsent(s.getSubId(), (sh) -> new ArrayList<>()).add(s));
        Map<Long, Boolean> hasScoreMap = ptScoreSheetService.mapHasScoreBySubIds(subIds);
        /* 组装 */
        List<SubjectInfo> infos = subjects.stream().map(s -> new SubjectInfo(
                s, compMap.get(s.getCompId()), subStuMap.get(s.getSubId()),
                hasScoreMap.get(s.getSubId()))).collect(Collectors.toList());
        return ApiPage.convert(subjects, infos);
    }

    public SubjectDetail getSubjectDetail(Long subId) {
        PtSubject subject = ptSubjectMapper.selectByPrimaryKey(subId);
        List<String> levels = ptSubjectMapper.listLevels(subject.getSubId());
        PtCompetency competency = ptCompetencyService.getComp(subject.getCompId());
        return new SubjectDetail(subject, competency == null ? null : competency.getCompName(), levels);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSubject(Long subId) {
        /* 删除科目对应测试学生 */
        ptSubStudentService.deleteBySubId(subId);
        /* 删除评分标准 */
        ptScoreSheetService.deleteBySubId(subId);
        return ptSubjectMapper.deleteByPrimaryKey(subId) == 1;
    }

    public List<PtSubject> listSubject(long msId) {
        PtMeasurement measurement = ptMesurementService.getMeasurement(msId);
        List<Long> subIds = ptSubjectSubGroupService.listSubIdByGrpId(measurement.getGrpId());
        return this.listSubject(subIds);
    }
}
