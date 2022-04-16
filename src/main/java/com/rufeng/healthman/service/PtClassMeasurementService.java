package com.rufeng.healthman.service;

import com.rufeng.healthman.mapper.PtClassMeasurementMapper;
import com.rufeng.healthman.pojo.ptdo.PtClass;
import com.rufeng.healthman.pojo.ptdo.PtClassMeasurement;
import com.rufeng.healthman.pojo.m2m.PtMeasurementClass;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-30 0:27
 * @package com.rufeng.healthman.service
 * @description TODO
 */
@Service
public class PtClassMeasurementService {
    private final PtClassMeasurementMapper ptClassMeasurementMapper;

    public PtClassMeasurementService(PtClassMeasurementMapper ptClassMeasurementMapper) {
        this.ptClassMeasurementMapper = ptClassMeasurementMapper;
    }

    public int batchInsertSelective(List<PtClassMeasurement> list) {
        if (list.size() == 0) {
            return 0;
        }
        return ptClassMeasurementMapper.batchInsertSelective(list);
    }

    public int delByMsId(Long msId) {
        return ptClassMeasurementMapper.deleteByMsId(msId);
    }

    public List<PtMeasurementClass> listClsMeasurementByMsIds(@NonNull List<Long> msIds) {
        if (msIds.size() == 0) {
            return Collections.emptyList();
        }
        return ptClassMeasurementMapper.listClsMeasurementByMsIds(msIds);
    }

    public List<PtClass> listClassByMsId(Long msId) {
        return ptClassMeasurementMapper.listClsMeasurementByMsId(msId);
    }
}
