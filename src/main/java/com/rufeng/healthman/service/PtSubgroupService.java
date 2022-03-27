package com.rufeng.healthman.service;

import com.rufeng.healthman.mapper.PtSubgroupMapper;
import com.rufeng.healthman.pojo.DO.PtSubgroup;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-27 19:13
 * @package com.rufeng.healthman.service
 * @description TODO
 */

@Service
public class PtSubgroupService {

    private final PtSubgroupMapper ptSubgroupMapper;

    public PtSubgroupService(PtSubgroupMapper ptSubgroupMapper) {
        this.ptSubgroupMapper = ptSubgroupMapper;
    }


    public int deleteByPrimaryKey(Long grpId) {
        return ptSubgroupMapper.deleteByPrimaryKey(grpId);
    }


    public int insert(PtSubgroup record) {
        return ptSubgroupMapper.insert(record);
    }


    public int insertSelective(PtSubgroup record) {
        return ptSubgroupMapper.insertSelective(record);
    }


    public PtSubgroup selectByPrimaryKey(Long grpId) {
        return ptSubgroupMapper.selectByPrimaryKey(grpId);
    }


    public int updateByPrimaryKeySelective(PtSubgroup record) {
        return ptSubgroupMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(PtSubgroup record) {
        return ptSubgroupMapper.updateByPrimaryKey(record);
    }

    public List<PtSubgroup> listSubGroup() {
        return ptSubgroupMapper.listSubGroup();
    }
}
