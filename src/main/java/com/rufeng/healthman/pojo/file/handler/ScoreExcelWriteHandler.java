package com.rufeng.healthman.pojo.file.handler;

import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.rufeng.healthman.pojo.file.PtStuScoreExportExcel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * @author rufeng
 * @time 2022-04-05 10:36
 * @package com.rufeng.healthman.pojo.file.handler
 * @description 合并单元格
 */
public class ScoreExcelWriteHandler implements RowWriteHandler {
    private final int length;
    private int prevIdx = -1;
    private String prevStuId = null;

    public ScoreExcelWriteHandler(int length) {
        this.length = length;
    }

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                Row row, Integer relativeRowIndex, Boolean isHead) {
        if (isHead) {
            return;
        }
        Sheet sheet = writeSheetHolder.getSheet();
        int curIdx = row.getRowNum();
        if (prevIdx == -1) {
            prevStuId = row.getCell(0).getStringCellValue();
            prevIdx = curIdx;
        } else {
            String stuId = row.getCell(0).getStringCellValue();
            if (!stuId.equals(prevStuId)) {
                /* 只有一个科目，不合并单元格 */
                if (curIdx != prevIdx + 1) {
                    for (int i = 0; i < PtStuScoreExportExcel.MERGE_COLS; i++) {
                        sheet.addMergedRegion(new CellRangeAddress(prevIdx, curIdx - 1, i, i));
                    }
                }
                prevIdx = curIdx;
                prevStuId = stuId;
            }
        }
        /* 处理最后一行 */
        if (curIdx == length) {
            if (curIdx == prevIdx) {
                return;
            }
            for (int i = 0; i < PtStuScoreExportExcel.MERGE_COLS; i++) {
                sheet.addMergedRegion(new CellRangeAddress(prevIdx, curIdx, i, i));
            }
        }
    }
}
