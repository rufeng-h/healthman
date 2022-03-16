package com.rufeng.healthman.service.impl;

import com.rufeng.healthman.mapper.PtMajorMapper;
import com.rufeng.healthman.pojo.DO.PtMajor;
import com.rufeng.healthman.pojo.Query.PtMajorQuery;
import com.rufeng.healthman.service.PtMajorService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-13 9:34
 * @package com.rufeng.healthman.service.impl
 * @description TODO
 */
@Service
public class PtMajorServiceImpl implements PtMajorService {
    private final PtMajorMapper ptMajorMapper;

    public PtMajorServiceImpl(PtMajorMapper ptMajorMapper) {
        this.ptMajorMapper = ptMajorMapper;
    }

    @Override
    public List<PtMajor> listMajor(PtMajorQuery query) {
        return ptMajorMapper.listMajor(query);
    }
}
