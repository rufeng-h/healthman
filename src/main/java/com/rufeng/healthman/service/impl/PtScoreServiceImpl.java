package com.rufeng.healthman.service.impl;

import com.rufeng.healthman.mapper.PtScoreMapper;
import com.rufeng.healthman.pojo.DTO.ptscore.StuScoreInfo;
import com.rufeng.healthman.pojo.Query.StuScoreQuery;
import com.rufeng.healthman.service.PtScoreService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-18 9:13
 * @package com.rufeng.healthman.service.impl
 * @description TODO
 */
@Service
public class PtScoreServiceImpl implements PtScoreService {
    private final PtScoreMapper ptScoreMapper;

    public PtScoreServiceImpl(PtScoreMapper ptScoreMapper) {
        this.ptScoreMapper = ptScoreMapper;
    }

    @Override
    public List<StuScoreInfo> listScore(StuScoreQuery query) {
        return null;
//        return ptScoreMapper.listScore(query);
    }
}
