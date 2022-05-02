package com.rufeng.healthman.service;

import com.rufeng.healthman.mapper.PtOperLogMapper;
import com.rufeng.healthman.pojo.ptdo.PtOperLog;
import org.springframework.stereotype.Service;

/**
 * @author rufeng
 * @time 2022-04-19 11:36
 * @package com.rufeng.healthman.service
 */
@Service
public class PtOperLogService {
    private final PtOperLogMapper ptOperLogMapper;

    public PtOperLogService(PtOperLogMapper ptOperLogMapper) {
        this.ptOperLogMapper = ptOperLogMapper;
    }

    public boolean addLog(PtOperLog ptOperLog) {
        return ptOperLogMapper.insertSelective(ptOperLog) == 1;
    }
}
