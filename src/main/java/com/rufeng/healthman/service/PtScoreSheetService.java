package com.rufeng.healthman.service;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.mapper.PtScoreSheetMapper;
import com.rufeng.healthman.pojo.dto.ptscoresheet.SubStudent;
import com.rufeng.healthman.pojo.file.PtScoreSheetExcel;
import com.rufeng.healthman.pojo.file.PtScoreSheetExcelListener;
import com.rufeng.healthman.pojo.ptdo.PtScoreSheet;
import com.rufeng.healthman.pojo.query.PtScoreSheetQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public int addScoreSheetSelective(List<PtScoreSheetExcel> sheets) {
        if (sheets.size() == 0) {
            return 0;
        }
        return ptScoreSheetMapper.batchInsertSelective(sheets);
    }

    public List<PtScoreSheet> listScoreSheet(SubStudent sheetKey) {
        return ptScoreSheetMapper.listScoreSheetBySubStudent(sheetKey);
    }

    public int uploadScoreSheet(Long subId, MultipartFile file) {
        PtScoreSheetExcelListener listener = new PtScoreSheetExcelListener(subId, this);
        try {
            EasyExcel.read(file.getInputStream(), PtScoreSheetExcel.class, listener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ApiPage<PtScoreSheet> pageScoreSheet(Integer page, Integer pageSize, PtScoreSheetQuery query) {
        PageHelper.startPage(page, pageSize);
        Page<PtScoreSheet> sheets = ptScoreSheetMapper.pageScoreSheet(query);
        return ApiPage.convert(sheets);
    }

    public boolean updateScoreSheet(PtScoreSheet scoreSheet) {
        return true;
    }


    public Map<Long, Boolean> mapHasScoreBySubIds(List<Long> subIds) {
        if (subIds.size() == 0) {
            return Collections.emptyMap();
        }
        List<Long> ids = ptScoreSheetMapper.listSubIdBySubIds(subIds);
        return subIds.stream().collect(Collectors.toMap(id -> id, ids::contains));
    }
}
