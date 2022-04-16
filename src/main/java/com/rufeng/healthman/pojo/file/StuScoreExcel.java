package com.rufeng.healthman.pojo.file;

import com.alibaba.excel.annotation.ExcelProperty;
import com.rufeng.healthman.enums.GenderEnum;
import com.rufeng.healthman.pojo.ptdo.PtScore;
import com.rufeng.healthman.pojo.dto.ptstu.StudentBaseInfo;
import com.rufeng.healthman.pojo.file.converter.StringGenderConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author rufeng
 * @time 2022-04-03 20:11
 * @package com.rufeng.healthman.pojo.file
 * @description 导出学生成绩excel
 */
@Data
@NoArgsConstructor
public class StuScoreExcel implements Comparable<StuScoreExcel> {
    public static final short MERGE_COLS = 3;
    @ExcelProperty("学号")
    private String stuId;
    @ExcelProperty("姓名")
    private String stuName;
    @ExcelProperty(value = "性别", converter = StringGenderConverter.class)
    private GenderEnum stuGender;
    @ExcelProperty("测试编号")
    private Long msId;
    @ExcelProperty("科目")
    private String subName;
    @ExcelProperty("体测数据")
    private BigDecimal scoData;
    @ExcelProperty("成绩")
    private Integer score;
    @ExcelProperty("等级")
    private String scoLevel;
    @ExcelProperty(value = "测试时间")
    private LocalDateTime scoCreated;

    public StuScoreExcel(StudentBaseInfo stu, String subName, PtScore score) {
        this.msId = score.getMsId();
        this.stuId = stu.getStuId();
        this.stuName = stu.getStuName();
        this.stuGender = stu.getStuGender();
        this.subName = subName;
        this.scoData = score.getScoData();
        this.score = score.getScore();
        this.scoLevel = score.getScoLevel();
        this.scoCreated = score.getScoCreated();
    }

    @Override
    public int compareTo(StuScoreExcel o) {
        return this.stuId.compareTo(o.getStuId());
    }
}
