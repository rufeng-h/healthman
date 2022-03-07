package com.rufeng.healthman.service;

import com.rufeng.healthman.domain.PtStudent;
import com.rufeng.healthman.mapper.PtStudentMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author rufeng
 * @time 2022-03-07 16:10
 * @package com.rufeng.healthman.service
 * @description stu service
 */
@Service
public class PtStudentService {
    private final PtStudentMapper ptStudentMapper;

    public PtStudentService(PtStudentMapper ptStudentMapper) {
        this.ptStudentMapper = ptStudentMapper;
    }

    @Nullable
    public PtStudent getPtStudentByNo(@NonNull Long number) {
        Assert.notNull(number, "学号不能为null");
        return ptStudentMapper.getPtStudentByNo(number);
    }
}
