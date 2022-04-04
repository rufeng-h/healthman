package com.rufeng.healthman.pojo.DTO.ptstu;

import com.rufeng.healthman.enums.GenderEnum;
import com.rufeng.healthman.pojo.DTO.ptscore.ScoreInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-04-03 0:23
 * @package com.rufeng.healthman.pojo.DTO.ptstu
 * @description TODO
 */
@Data
public class StuScoreInfo {
    private String stuName;
    private String stuId;
    private GenderEnum stuGender;
    private List<ScoreInfo> scores = new ArrayList<>();
}
