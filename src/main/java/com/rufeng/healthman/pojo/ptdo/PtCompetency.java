package com.rufeng.healthman.pojo.ptdo;

import com.rufeng.healthman.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 运动能力
 *
 * @author rufeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PtCompetency implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long compId;
    private String compName;
    private String compDesp;
    private Date compCreated;
    private Date compMidified;
    private Map<GenderEnum, List<Integer>> map;
}