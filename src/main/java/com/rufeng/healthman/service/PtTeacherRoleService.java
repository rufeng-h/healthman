package com.rufeng.healthman.service;

import com.rufeng.healthman.common.util.StringUtils;
import com.rufeng.healthman.mapper.PtTeacherRoleMapper;
import com.rufeng.healthman.pojo.ptdo.PtTeacherRole;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-04-19 23:38
 * @package com.rufeng.healthman.service
 * @description TODO
 */
@Service
public class PtTeacherRoleService {
    private final PtTeacherRoleMapper ptTeacherRoleMapper;

    public PtTeacherRoleService(PtTeacherRoleMapper ptTeacherRoleMapper) {
        this.ptTeacherRoleMapper = ptTeacherRoleMapper;
    }

    public int addTeaRoleSelective(List<PtTeacherRole> list) {
        if (list.size() == 0) {
            return 0;
        }
        return ptTeacherRoleMapper.batchInsertSelective(list);
    }

    public List<PtTeacherRole> listByTeaId(String teaId) {
        if (StringUtils.isEmptyOrBlank(teaId)) {
            return Collections.emptyList();
        }
        return ptTeacherRoleMapper.listByTeaId(teaId);
    }
}
