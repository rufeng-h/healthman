package com.rufeng.healthman.service;

import com.rufeng.healthman.mapper.PtScoreSheetMapper;
import com.rufeng.healthman.pojo.DO.PtScoreSheet;
import com.rufeng.healthman.pojo.DTO.ptscoresheet.SheetInfo;
import com.rufeng.healthman.pojo.Query.PtScoreSheetQuery;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collections;
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


    public int addScoreSheetSelective(List<PtScoreSheet> sheets) {
        if (sheets.size() == 0) {
            return 0;
        }
        return ptScoreSheetMapper.batchInsertSelective(sheets);
    }

    public List<SheetInfo> listSheetInfoBySubIds(List<Long> subIds) {
        if (subIds.size() == 0){
            return Collections.emptyList();
        }

        return ptScoreSheetMapper.listSheetInfoBySubIds(subIds);
    }
}
