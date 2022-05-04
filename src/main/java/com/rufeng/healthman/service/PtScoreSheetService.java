package com.rufeng.healthman.service;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.mapper.PtScoreSheetMapper;
import com.rufeng.healthman.mapper.PtSubStudentMapper;
import com.rufeng.healthman.pojo.data.PtScoreSheetFormdata;
import com.rufeng.healthman.pojo.file.PtScoreSheetExcel;
import com.rufeng.healthman.pojo.file.PtScoreSheetExcelListener;
import com.rufeng.healthman.pojo.ptdo.PtScoreSheet;
import com.rufeng.healthman.pojo.query.PtScoreSheetQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-17 17:19
 * @package com.rufeng.healthman.service
 * @description 评分记录
 */
@Service
public class PtScoreSheetService {
    private final PtScoreSheetMapper ptScoreSheetMapper;
    private final PtSubStudentMapper ptSubStudentMapper;

    public PtScoreSheetService(PtScoreSheetMapper ptScoreSheetMapper,
                               PtSubStudentMapper ptSubStudentMapper) {
        this.ptScoreSheetMapper = ptScoreSheetMapper;
        this.ptSubStudentMapper = ptSubStudentMapper;
    }

    public int addScoreSheetSelective(List<PtScoreSheetExcel> sheets) {
        if (sheets.size() == 0) {
            return 0;
        }
        return ptScoreSheetMapper.batchInsertSelective(sheets);
    }

    public int uploadScoreSheet(Long subId, MultipartFile file) {
        PtScoreSheetExcelListener listener = new PtScoreSheetExcelListener(subId,
                this, ptSubStudentMapper);
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
                .lastModifyTime(LocalDateTime.now()).build();
        return ptScoreSheetMapper.updateByIdSelective(scoreSheet) == 1;
    }

    public boolean deleteById(Long id) {
        return ptScoreSheetMapper.deleteById(id) == 1;
    }
}
