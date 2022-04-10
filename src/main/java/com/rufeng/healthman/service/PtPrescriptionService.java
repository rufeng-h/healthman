package com.rufeng.healthman.service;

import com.rufeng.healthman.mapper.PtPrescriptionMapper;
import com.rufeng.healthman.pojo.ptdo.PtPrescription;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-04-09 15:48
 * @package com.rufeng.healthman.service
 * @description TODO
 */
@Service
public class PtPrescriptionService {
    private final PtPrescriptionMapper ptPrescriptionMapper;

    public PtPrescriptionService(PtPrescriptionMapper ptPrescriptionMapper) {
        this.ptPrescriptionMapper = ptPrescriptionMapper;
    }

    public List<PtPrescription> listPrsByIds(List<Long> prsIds){
        if (prsIds.size() == 0){
            return Collections.emptyList();
        }
        return ptPrescriptionMapper.listPrsByIds(prsIds);
    }
}
