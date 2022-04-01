package com.rufeng.healthman.service;

import com.rufeng.healthman.mapper.PtSubjectSubgroupMapper;
import com.rufeng.healthman.pojo.DO.PtSubjectSubgroup;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author rufeng
 * @time 2022-03-29 19:20
 * @package com.rufeng.healthman.service
 * @description TODO
 */
@Service
public class PtSubjectSubGroupService {
    private final PtSubjectSubgroupMapper ptSubjectSubgroupMapper;

    public PtSubjectSubGroupService(PtSubjectSubgroupMapper ptSubjectSubgroupMapper) {
        this.ptSubjectSubgroupMapper = ptSubjectSubgroupMapper;
    }

    public int batchInsertSelective(List<PtSubjectSubgroup> list) {
        if (list.size() == 0) {
            return 0;
        }
        return ptSubjectSubgroupMapper.batchInsertSelective(list);
    }

    public Map<Long, Integer> countSubByGrpIds(List<Long> grpIds) {
        if (grpIds.size() == 0) {
            return Collections.emptyMap();
        }
        return ptSubjectSubgroupMapper.countSubByGrpIds(grpIds);
    }
}
