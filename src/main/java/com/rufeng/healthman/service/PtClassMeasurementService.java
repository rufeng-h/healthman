package com.rufeng.healthman.service;

import com.rufeng.healthman.mapper.PtClassMesurementMapper;
import com.rufeng.healthman.pojo.ptdo.PtClass;
import com.rufeng.healthman.pojo.ptdo.PtClassMesurement;
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

    public int delByMsId(Long msId) {
        return ptClassMesurementMapper.deleteByMsId(msId);
    }

    public List<PtMeasurementClass> listClsMeasurementByMsIds(@NonNull List<Long> msIds) {
        if (msIds.size() == 0) {
            return Collections.emptyList();
        }
        return ptClassMesurementMapper.listClsMeasurementByMsIds(msIds);
    }

    public List<PtClass> listClassByMsId(Long msId) {
        return ptClassMesurementMapper.listClsMeasurementByMsId(msId);
    }
}
