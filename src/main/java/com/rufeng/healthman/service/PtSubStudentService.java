package com.rufeng.healthman.service;

import com.rufeng.healthman.enums.GradeEnum;
import com.rufeng.healthman.mapper.PtSubStudentMapper;
import com.rufeng.healthman.pojo.dto.ptscoresheet.SubStudent;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-04-11 19:21
 * @package com.rufeng.healthman.service
 * @description TODO
 */
@Service
public class PtSubStudentService {
    private final PtSubStudentMapper ptSubStudentMapper;

    public PtSubStudentService(PtSubStudentMapper ptSubStudentMapper) {
        this.ptSubStudentMapper = ptSubStudentMapper;
    }

    public List<SubStudent> listSubStudentBySubIds(List<Long> subIds) {
        if (subIds.size() == 0) {
            return Collections.emptyList();
        }
        return ptSubStudentMapper.listSubStudentBySubIds(subIds);
    }

    public int addSubStuSelective(List<SubStudent> subStudents) {
        if (subStudents.size() == 0) {
            return 0;
        }
        return ptSubStudentMapper.batchInsertSelective(subStudents);
    }

    public int deleteBySubId(Long subId) {
        return ptSubStudentMapper.deleteBySubId(subId);
    }

    public List<Long> listSubIdsByGrade(GradeEnum grade) {
        if (grade == null) {
            return Collections.emptyList();
        }
        return ptSubStudentMapper.listSubIdsByGrade(grade.getValue());
    }
}
