package com.rufeng.healthman.pojo.Query;

import com.rufeng.healthman.enums.GenderEnum;
import lombok.Data;

/**
 * @author rufeng
 * @time 2022-03-18 0:16
 * @package com.rufeng.healthman.pojo.Query
 * @description 查询学生成绩
 */
@Data
public class StuScoreQuery {
    private Long stuNumber;
    private String stuName;
    private Long collegeId;
    private String classCode;
    private Long subjectId;
    /**
     * 多少级，如2018，2019
     */
    private Integer grade;
    private GenderEnum gender;
    /**
     * 某一年的成绩
     */
    private Integer year;
}
