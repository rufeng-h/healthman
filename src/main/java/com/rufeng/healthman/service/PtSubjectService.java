package com.rufeng.healthman.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.enums.GenderEnum;
import com.rufeng.healthman.enums.GradeEnum;
import com.rufeng.healthman.mapper.PtSubjectMapper;
import com.rufeng.healthman.pojo.DO.PtScoreSheet;
import com.rufeng.healthman.pojo.DO.PtSubject;
import com.rufeng.healthman.pojo.DTO.ptscoresheet.SheetInfo;
import com.rufeng.healthman.pojo.DTO.ptsubject.SubjectInfo;
import com.rufeng.healthman.pojo.Query.PtSubjectQuery;
import com.rufeng.healthman.pojo.data.PtScoreSheetFormdata;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
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

    public PtSubjectService(PtSubjectMapper ptSubjectMapper, PtScoreSheetService ptScoreSheetService) {
        this.ptSubjectMapper = ptSubjectMapper;
        this.ptScoreSheetService = ptScoreSheetService;
    }


    public List<PtSubject> listSubject() {
        return ptSubjectMapper.listSubject();
    }

    @Transactional(rollbackFor = Exception.class)
    public PtSubject addSubject(PtScoreSheetFormdata data) {
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

    public ApiPage<SubjectInfo> pageSubjectInfo(Integer page, Integer pageSize, PtSubjectQuery query) {
        PageHelper.startPage(page, pageSize);
        Page<PtSubject> subjects = ptSubjectMapper.pageSubject(query);
        List<Long> subIds = subjects.stream().map(PtSubject::getSubId).collect(Collectors.toList());
        List<SheetInfo> sheetInfos = ptScoreSheetService.listSheetInfoBySubIds(subIds);
        Map<Long, SubjectInfo> infoMap = subjects.stream().collect(Collectors.toMap(PtSubject::getSubId, SubjectInfo::new));
        sheetInfos.forEach(s -> infoMap.get(s.getSubId()).getSheetInfos().add(s));
        return ApiPage.convert(subjects, infoMap.values());
    }
}
