package com.rufeng.healthman.pojo.DTO.ptscore;

import com.rufeng.healthman.pojo.DO.PtScore;
import lombok.Data;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-18 0:22
 * @package com.rufeng.healthman.pojo.DTO.ptscore
 * @description TODO
 */
@Data
public class StuScoreInfo {
    List<PtScore> scores;
    private Long stuNumber;
    private String stuName;
}
