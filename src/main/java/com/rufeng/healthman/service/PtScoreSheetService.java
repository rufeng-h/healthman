package com.rufeng.healthman.service;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.mapper.PtScoreSheetMapper;
import com.rufeng.healthman.pojo.data.PtScoreSheetFormdata;
import com.rufeng.healthman.pojo.dto.ptscoresheet.SubStudent;
import com.rufeng.healthman.pojo.file.PtScoreSheetExcel;
import com.rufeng.healthman.pojo.file.PtScoreSheetExcelListener;
import com.rufeng.healthman.pojo.ptdo.PtScoreSheet;
import com.rufeng.healthman.pojo.query.PtScoreSheetQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author rufeng
 * @time 2022-03-17 17:19
 * @package com.rufeng.healthman.service
 * @description 评分记录
 */
@Service
public class PtScoreSheetService {
    private final PtScoreSheetMapper ptScoreSheetMapper;
    private final PtSubStudentService ptSubStudentService;

    public PtScoreSheetService(PtScoreSheetMapper ptScoreSheetMapper, PtSubStudentService ptSubStudentService) {
        this.ptScoreSheetMapper = ptScoreSheetMapper;
        this.ptSubStudentService = ptSubStudentService;
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
        PtScoreSheetExcelListener listener = new PtScoreSheetExcelListener(subId, this, ptSubStudentService);
        try {
            EasyExcel.read(file.getInputStream(), PtScoreSheetExcel.class, listener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listener.getHandleCount();
    }

    public ApiPage<PtScoreSheet> pageScoreSheet(Integer page, Integer pageSize, PtScoreSheetQuery query) {
        PageHelper.startPage(page, pageSize);
        Page<PtScoreSheet> sheets = ptScoreSheetMapper.pageScoreSheet(query);
        return ApiPage.convert(sheets);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateScoreSheet(PtScoreSheetFormdata data) {
        PtScoreSheet scoreSheet = PtScoreSheet.builder()
                .id(data.getId())
                .grade(data.getGrade().getValue())
                .gender(data.getGender())
                .score(data.getScore())
                .lower(data.getLower())
                .upper(data.getUpper())
                .level(data.getLevel())
                .subId(data.getSubId())
                .lastModifyTime(new Date()).build();
        return ptScoreSheetMapper.updateByIdSelective(scoreSheet) == 1;
    }


    public Map<Long, Boolean> mapHasScoreBySubIds(List<Long> subIds) {
        if (subIds.size() == 0) {
            return Collections.emptyMap();
        }
        List<Long> ids = ptScoreSheetMapper.listSubIdBySubIds(subIds);
        return subIds.stream().collect(Collectors.toMap(id -> id, ids::contains));
    }

    public int deleteBySubId(Long subId) {
        return ptScoreSheetMapper.deleteBySubId(subId);
    }

    public boolean deleteById(Long id) {
        return ptScoreSheetMapper.deleteById(id) == 1;
    }
}
