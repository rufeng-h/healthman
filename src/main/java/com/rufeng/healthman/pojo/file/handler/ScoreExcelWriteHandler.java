package com.rufeng.healthman.pojo.file.handler;

import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.rufeng.healthman.pojo.file.StuScoreExcel;
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
    private int prevIdx = -1;
    private String prevStuId = null;

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                Row row, Integer relativeRowIndex, Boolean isHead) {
        if (isHead) {
            return;
        }
        if (prevIdx == -1) {
            prevStuId = row.getCell(0).getStringCellValue();
            prevIdx = row.getRowNum();
        } else {
            int curIdx = row.getRowNum();
            String stuId = row.getCell(0).getStringCellValue();
            /* 只有一个科目，不合并单元格 */
            if (!stuId.equals(prevStuId)) {
                Sheet sheet = writeSheetHolder.getSheet();
                if (curIdx != prevIdx + 1) {
                    for (int i = 0; i < StuScoreExcel.MERGE_COLS; i++) {
                        sheet.addMergedRegion(new CellRangeAddress(prevIdx, curIdx - 1, i, i));
                    }
                }
                prevIdx = curIdx;
                prevStuId = stuId;
            }
        }
    }
}
