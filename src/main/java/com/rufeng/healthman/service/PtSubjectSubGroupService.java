package com.rufeng.healthman.service;

import com.rufeng.healthman.mapper.PtSubjectSubgroupMapper;
import com.rufeng.healthman.pojo.DO.PtSubjectSubgroup;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
