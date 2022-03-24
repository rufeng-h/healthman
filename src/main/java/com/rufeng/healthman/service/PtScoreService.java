package com.rufeng.healthman.service;

import com.rufeng.healthman.pojo.DTO.ptscore.StuScoreInfo;
import com.rufeng.healthman.pojo.Query.StuScoreQuery;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-18 9:12
 * @package com.rufeng.healthman.service
 * @description TODO
 */
public interface PtScoreService {
    /**
     * 查询成绩
     *
     * @param query 查询参数
     * @return list
     */
    List<StuScoreInfo> listScore(StuScoreQuery query);
}
