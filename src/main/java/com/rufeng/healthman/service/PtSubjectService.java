package com.rufeng.healthman.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.enums.GenderEnum;
import com.rufeng.healthman.enums.GradeEnum;
import com.rufeng.healthman.mapper.PtSubjectMapper;
import com.rufeng.healthman.pojo.data.PtSubjectFormdata;
import com.rufeng.healthman.pojo.dto.ptscoresheet.SheetInfo;
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
 * @description TODO
 */
@Service
public class PtSubjectService {
    private final PtSubjectMapper ptSubjectMapper;
    private final PtScoreSheetService ptScoreSheetService;
    private final PtCompetencyService ptCompetencyService;

    public PtSubjectService(PtSubjectMapper ptSubjectMapper,
                            PtScoreSheetService ptScoreSheetService,
                            PtCompetencyService ptCompetencyService) {
        this.ptSubjectMapper = ptSubjectMapper;
        this.ptScoreSheetService = ptScoreSheetService;
        this.ptCompetencyService = ptCompetencyService;
    }


    public List<PtSubject> listSubject() {
        return ptSubjectMapper.listSubject();
    }

    @Transactional(rollbackFor = Exception.class)
    public PtSubject addSubject(PtSubjectFormdata data) {
        PtSubject subject = new PtSubject();
        subject.setSubDesp(data.getSubDesp());
        subject.setSubName(data.getSubName());
        ptSubjectMapper.insertSelective(subject);

        Long subId = subject.getSubId();
        Set<GenderEnum> genders = data.getGenders();
        List<GradeEnum> grades = data.getGrades();
        List<PtScoreSheet> sheets = data.getScoreSheet();
        sheets.forEach(s -> s.setSubjectId(subId));
        for (GenderEnum gender : genders) {
            for (GradeEnum grade : grades) {
                for (PtScoreSheet sheet : sheets) {
                    sheet.setGender(gender);
                    sheet.setGrade(grade.getValue());
                }
                ptScoreSheetService.addScoreSheetSelective(sheets);
            }
        }
        return subject;
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
        PageHelper.startPage(page, pageSize);
        Page<PtSubject> subjects = ptSubjectMapper.pageSubject(query);
        List<Long> compIds = subjects.stream().map(PtSubject::getCompId).collect(Collectors.toList());
        List<PtCompetency> ptCompetencies = ptCompetencyService.listCompByIds(compIds);
        Map<Long, String> compMap = ptCompetencies.stream()
                .collect(Collectors.toMap(PtCompetency::getCompId, PtCompetency::getCompName));
        List<Long> subIds = subjects.stream().map(PtSubject::getSubId).collect(Collectors.toList());
        List<SheetInfo> sheetInfos = ptScoreSheetService.listSheetInfoBySubIds(subIds);
        Map<Long, List<SheetInfo>> sheetMap = new HashMap<>(10);
        sheetInfos.forEach(s -> sheetMap.computeIfAbsent(s.getSubId(), (sh) -> new ArrayList<>()).add(s));
        List<SubjectInfo> infos = subjects.stream().map(s -> new SubjectInfo(
                s, compMap.get(s.getCompId()), sheetMap.get(s.getSubId()))).collect(Collectors.toList());
        return ApiPage.convert(subjects, infos);
    }
}
