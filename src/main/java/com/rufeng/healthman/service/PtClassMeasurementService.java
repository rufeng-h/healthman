package com.rufeng.healthman.service;

import com.rufeng.healthman.mapper.PtClassMesurementMapper;
import com.rufeng.healthman.pojo.DO.PtClassMesurement;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-30 0:27
 * @package com.rufeng.healthman.service
 * @description TODO
 */
@Service
public class PtClassMeasurementService {
    private final PtClassMesurementMapper ptClassMesurementMapper;

    public PtClassMeasurementService(PtClassMesurementMapper ptClassMesurementMapper) {
        this.ptClassMesurementMapper = ptClassMesurementMapper;
    }

    public int batchInsertSelective(List<PtClassMesurement> list) {
        if (list.size() == 0) {
            return 0;
        }
        return ptClassMesurementMapper.batchInsertSelective(list);
    }
}
