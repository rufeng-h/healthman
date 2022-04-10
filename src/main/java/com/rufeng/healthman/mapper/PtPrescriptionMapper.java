package com.rufeng.healthman.mapper;

import com.rufeng.healthman.pojo.ptdo.PtPrescription;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author rufeng
 * @time 2022-04-09 15:14
 * @package com.rufeng.healthman.mapper
 * @description TODO
 */

@Mapper
public interface PtPrescriptionMapper {
    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(PtPrescription record);

    int insertOrUpdate(PtPrescription record);

    int insertOrUpdateSelective(PtPrescription record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(PtPrescription record);

    int batchInsert(@Param("list") List<PtPrescription> list);

    List<PtPrescription> listPrsByIds(List<Long> prsIds);
}