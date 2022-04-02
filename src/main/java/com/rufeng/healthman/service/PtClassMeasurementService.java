package com.rufeng.healthman.service;

import com.rufeng.healthman.mapper.PtClassMesurementMapper;
import com.rufeng.healthman.pojo.DO.PtClass;
import com.rufeng.healthman.pojo.m2m.PtMeasurementClass;
import com.rufeng.healthman.pojo.DO.PtClassMesurement;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    public Map<Long, Integer> countClsByMsIds(List<Long> msIds) {
        if (msIds.size() == 0) {
            return Collections.emptyMap();
        }
        return ptClassMesurementMapper.countClsByMsIds(msIds);
    }

    public Map<Long, Integer> countStuByMsIds(List<Long> msIds) {
        if (msIds.size() == 0) {
            return Collections.emptyMap();
        }
        return ptClassMesurementMapper.countStuByMsIds(msIds);
    }

    public int delByMsId(Long msId) {
        return ptClassMesurementMapper.deleteByMsId(msId);
    }

    public List<PtMeasurementClass> listClsMeasurementByMsIds(@NonNull List<Long> msIds) {
        if (msIds.size() == 0){
            return Collections.emptyList();
        }
        return ptClassMesurementMapper.listClsMeasurementByMsIds(msIds);
    }

    public List<PtClass> listClassByMsId(Long msId) {
        return ptClassMesurementMapper.listClsMeasurementByMsId(msId);
    }
}
