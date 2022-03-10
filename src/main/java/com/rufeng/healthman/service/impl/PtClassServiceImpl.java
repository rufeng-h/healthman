package com.rufeng.healthman.service.impl;

import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.mapper.PtClassMapper;
import com.rufeng.healthman.pojo.DO.PtClass;
import com.rufeng.healthman.pojo.Query.PtClassQuery;
import com.rufeng.healthman.service.PtClassService;
import org.springframework.stereotype.Service;

/**
 * @author rufeng
 * @time 2022-03-09 22:09
 * @package com.rufeng.healthman.service.impl
 */
@Service
public class PtClassServiceImpl implements PtClassService {
    private final PtClassMapper ptClassMapper;

    public PtClassServiceImpl(PtClassMapper ptClassMapper) {
        this.ptClassMapper = ptClassMapper;
    }

    @Override
    public ApiPage<PtClass> pageClass(Integer page, Integer pageSize, PtClassQuery ptClassQuery) {
        PageHelper.startPage(page, pageSize);
        return ApiPage.convert(ptClassMapper.pageClass(ptClassQuery));
    }
}
