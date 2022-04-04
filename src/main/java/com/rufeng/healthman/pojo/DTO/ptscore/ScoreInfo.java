package com.rufeng.healthman.pojo.DTO.ptscore;

import com.rufeng.healthman.pojo.DO.PtScore;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author rufeng
 * @time 2022-04-03 9:49
 * @package com.rufeng.healthman.pojo.DTO.ptscore
 * @description 返回前端的成绩记录
 */
@Data
public class ScoreInfo {
    private Long subId;
    private Long scoId;
    private String subName;
    private BigDecimal scoData;
    private Date scoCreated;
    private String scoLevel;
    private Integer score;

    public ScoreInfo(PtScore score, String subName) {
        this.scoId = score.getScoId();
        this.scoCreated = score.getScoCreated();
        this.scoData = score.getScoData();
        this.scoLevel = score.getScoLevel();
        this.subName = subName;
        this.score = score.getScore();
        this.subId = score.getSubId();
    }
}
