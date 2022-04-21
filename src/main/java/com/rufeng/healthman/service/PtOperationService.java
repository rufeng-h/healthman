package com.rufeng.healthman.service;

import com.rufeng.healthman.mapper.PtOperationMapper;
import com.rufeng.healthman.pojo.ptdo.PtOperation;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-04-22 16:45
 * @package com.rufeng.healthman.service
 * @description TODO
 */
@Service
public class PtOperationService {
    private final PtOperationMapper ptOperationMapper;

    public PtOperationService(PtOperationMapper ptOperationMapper) {
        this.ptOperationMapper = ptOperationMapper;
    }

    List<PtOperation> listByIds(List<String> operIds){
        return ptOperationMapper.listByIds(operIds);
    }
}
