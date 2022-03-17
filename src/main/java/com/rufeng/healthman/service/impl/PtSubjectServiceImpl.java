package com.rufeng.healthman.service.impl;

import com.rufeng.healthman.mapper.PtSubjectMapper;
import com.rufeng.healthman.pojo.DO.PtSubject;
import com.rufeng.healthman.service.PtSubjectService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-17 18:44
 * @package com.rufeng.healthman.service.impl
 * @description TODO
 */
@Service
public class PtSubjectServiceImpl implements PtSubjectService {
    private final PtSubjectMapper ptSubjectMapper;

    public PtSubjectServiceImpl(PtSubjectMapper ptSubjectMapper) {
        this.ptSubjectMapper = ptSubjectMapper;
    }

    @Override
    public List<PtSubject> listSubject() {
        return ptSubjectMapper.listSubject();
    }
}
