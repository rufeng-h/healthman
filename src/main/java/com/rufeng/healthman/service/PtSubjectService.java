package com.rufeng.healthman.service;

import com.rufeng.healthman.pojo.DO.PtSubject;
import com.rufeng.healthman.pojo.data.PtScoreSheetFormdata;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-17 18:43
 * @package com.rufeng.healthman.service
 * @description TODO
 */
public interface PtSubjectService {
    /**
     * list科目
     *
     * @return list
     */
    List<PtSubject> listSubject();

    PtSubject addSubject(PtScoreSheetFormdata data);
}
