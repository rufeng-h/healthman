package com.rufeng.healthman.service;

import com.rufeng.healthman.mapper.PtScoreSheetMapper;
import com.rufeng.healthman.pojo.ptdo.PtScoreSheet;
import com.rufeng.healthman.pojo.dto.ptscoresheet.ScoreSheetKey;
import com.rufeng.healthman.pojo.dto.ptscoresheet.SheetInfo;
import com.rufeng.healthman.pojo.query.PtScoreSheetQuery;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-17 17:19
 * @package com.rufeng.healthman.service.impl
 * @description 评分记录
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
        if (subIds.size() == 0) {
            return Collections.emptyList();
        }
        return ptScoreSheetMapper.listSheetInfoBySubIds(subIds);
    }

    public List<PtScoreSheet> listScoreSheet(ScoreSheetKey sheetKey) {
        return ptScoreSheetMapper.listScoreSheetBySheetKey(sheetKey);
    }
}
