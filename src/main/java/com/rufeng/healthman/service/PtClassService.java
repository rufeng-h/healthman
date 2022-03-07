package com.rufeng.healthman.service;

import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.ApiPage;
import com.rufeng.healthman.domain.PtClass;
import com.rufeng.healthman.mapper.PtClassMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * @author rufeng
 * @time 2022-03-06 22:15
 * @package com.rufeng.healthman.service
 * @description 班级
 */
@Service
public class PtClassService {
    private final PtClassMapper ptClassMapper;

    public PtClassService(PtClassMapper ptClassMapper) {
        this.ptClassMapper = ptClassMapper;
    }

    public ApiPage<PtClass> pagePtClass(Integer page, Integer pageSize, @NonNull PtClass ptClass) {
        PageHelper.startPage(page, pageSize);
        return ApiPage.convert(ptClassMapper.pagePtClass(ptClass));
    }
}
