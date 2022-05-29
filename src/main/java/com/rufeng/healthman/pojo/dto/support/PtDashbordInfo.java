package com.rufeng.healthman.pojo.dto.support;

import lombok.Data;

/**
 * @author chunf
 * @time 2022-05-27 8:54
 * @package com.rufeng.healthman.pojo.dto.support
 * @description TODO
 */
@Data
public class PtDashbordInfo {
    private Long stuCnt;
    private Long teaCnt;
    private Long scoreCnt;
    private Long msCnt;


    public PtDashbordInfo(Long stuCnt, Long teaCnt, Long scoreCnt, Long msCnt) {
        this.stuCnt = stuCnt;
        this.teaCnt = teaCnt;
        this.scoreCnt = scoreCnt;
        this.msCnt = msCnt;
    }
}
