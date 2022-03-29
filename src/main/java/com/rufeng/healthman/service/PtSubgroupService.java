package com.rufeng.healthman.service;

import com.rufeng.healthman.mapper.PtSubgroupMapper;
import com.rufeng.healthman.mapper.PtSubjectSubgroupMapper;
import com.rufeng.healthman.pojo.DO.PtSubgroup;
import com.rufeng.healthman.pojo.DO.PtSubjectSubgroup;
import com.rufeng.healthman.pojo.data.PtSubGroupFormdata;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rufeng
 * @time 2022-03-27 19:13
 * @package com.rufeng.healthman.service
 * @description TODO
 */

@Service
public class PtSubgroupService {

    private final PtSubgroupMapper ptSubgroupMapper;
    private final PtCommonService ptCommonService;
    private final PtSubjectSubgroupMapper ptSubjectSubgroupMapper;

    public PtSubgroupService(PtSubgroupMapper ptSubgroupMapper, PtCommonService ptCommonService, PtSubjectSubgroupMapper ptSubjectSubgroupMapper) {
        this.ptSubgroupMapper = ptSubgroupMapper;
        this.ptCommonService = ptCommonService;
        this.ptSubjectSubgroupMapper = ptSubjectSubgroupMapper;
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

    @Transactional(rollbackFor = Exception.class)
    public PtSubgroup addSubGroup(PtSubGroupFormdata formdata) {
        List<Long> subIds = formdata.getSubIds();
        String userId = ptCommonService.getCurrentUserId();
        PtSubgroup subgroup = PtSubgroup.builder()
                .grpDesp(formdata.getGrpDesp())
                .grpName(formdata.getGrpName())
                .grpCreatedAdmin(userId)
                .build();
        ptSubgroupMapper.insertSelective(subgroup);
        Long grpId = subgroup.getGrpId();
        List<PtSubjectSubgroup> subjectSubgroups = subIds.stream().map(
                        subId -> PtSubjectSubgroup.builder()
                                .subId(subId).subGrpAdmin(userId)
                                .grpId(grpId).build())
                .collect(Collectors.toList());
        ptSubjectSubgroupMapper.batchInsertSelective(subjectSubgroups);
        return subgroup;
    }
}
