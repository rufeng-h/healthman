package com.rufeng.healthman.service;

import com.rufeng.healthman.pojo.DO.PtScoreSheet;
import com.rufeng.healthman.pojo.Query.PtScoreSheetQuery;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-17 17:18
 * @package com.rufeng.healthman.service
 * @description TODO
 */
public interface PtScoreSheetService {
    /**
     * 评分表
     *
     * @param query 查询参数
     * @return list
     */
    List<PtScoreSheet> listScoreSheet(@NonNull PtScoreSheetQuery query);
}
