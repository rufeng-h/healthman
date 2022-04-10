package com.rufeng.healthman.service;

import com.rufeng.healthman.mapper.PtCompetencyMapper;
import com.rufeng.healthman.pojo.ptdo.PtCompetency;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-04-09 15:53
 * @package com.rufeng.healthman.service
 * @description TODO
 */
@Service
public class PtCompetencyService {
    private final PtCompetencyMapper ptCompetencyMapper;

    public PtCompetencyService(PtCompetencyMapper ptCompetencyMapper) {
        this.ptCompetencyMapper = ptCompetencyMapper;
    }

    public List<PtCompetency> listCompByIds(List<Long> compIds) {
        if (compIds.size() == 0) {
            return Collections.emptyList();
        }
        return ptCompetencyMapper.listCompByIds(compIds);
    }
}
