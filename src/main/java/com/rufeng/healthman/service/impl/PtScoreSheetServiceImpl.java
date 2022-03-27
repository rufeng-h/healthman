package com.rufeng.healthman.service.impl;

import com.rufeng.healthman.mapper.PtScoreSheetMapper;
import com.rufeng.healthman.pojo.DO.PtScoreSheet;
import com.rufeng.healthman.pojo.Query.PtScoreSheetQuery;
import com.rufeng.healthman.service.PtScoreSheetService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-17 17:19
 * @package com.rufeng.healthman.service.impl
 * @description TODO
 */
@Service
public class PtScoreSheetServiceImpl implements PtScoreSheetService {
    private final PtScoreSheetMapper ptScoreSheetMapper;

    public PtScoreSheetServiceImpl(PtScoreSheetMapper ptScoreSheetMapper) {
        this.ptScoreSheetMapper = ptScoreSheetMapper;
    }

    @Override
    public List<PtScoreSheet> listScoreSheet(@NonNull PtScoreSheetQuery query) {
        return ptScoreSheetMapper.listScoreSheet(query);
    }

    @Override
    public Integer addScoreSheet(List<PtScoreSheet> sheets) {
        return ptScoreSheetMapper.insertBatch(sheets);
    }
}
