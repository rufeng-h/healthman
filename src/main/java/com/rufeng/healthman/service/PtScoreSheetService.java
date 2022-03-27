package com.rufeng.healthman.service;

import com.rufeng.healthman.mapper.PtScoreSheetMapper;
import com.rufeng.healthman.pojo.DO.PtScoreSheet;
import com.rufeng.healthman.pojo.Query.PtScoreSheetQuery;
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
public class PtScoreSheetService {
    private final PtScoreSheetMapper ptScoreSheetMapper;

    public PtScoreSheetService(PtScoreSheetMapper ptScoreSheetMapper) {
        this.ptScoreSheetMapper = ptScoreSheetMapper;
    }

    
    public List<PtScoreSheet> listScoreSheet(@NonNull PtScoreSheetQuery query) {
        return ptScoreSheetMapper.listScoreSheet(query);
    }

    
    public Integer addScoreSheet(List<PtScoreSheet> sheets) {
        return ptScoreSheetMapper.insertBatch(sheets);
    }
}
