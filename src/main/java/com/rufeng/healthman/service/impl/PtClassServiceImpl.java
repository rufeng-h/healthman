package com.rufeng.healthman.service.impl;

import com.github.pagehelper.PageHelper;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.mapper.PtClassMapper;
import com.rufeng.healthman.pojo.DO.PtClass;
import com.rufeng.healthman.pojo.Query.PtClassQuery;
import com.rufeng.healthman.service.PtClassService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public ApiPage<PtClass> pageClass(Integer page, Integer pageSize, @NonNull PtClassQuery ptClassQuery) {
        PageHelper.startPage(page, pageSize);
        return ApiPage.convert(ptClassMapper.pageClass(ptClassQuery));
    }

    @Override
    public List<PtClass> listClass(@NonNull PtClassQuery query) {
        return ptClassMapper.listClass(query);
    }

    @Override
    public List<Integer> listGrade(@NonNull PtClassQuery query) {
        return ptClassMapper.listGrade(query);
    }

    @Override
    public PtClass getPtClass(String classCode) {
        return ptClassMapper.getPtClass(classCode);
    }
}
