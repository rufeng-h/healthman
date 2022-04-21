package com.rufeng.healthman.pojo.ptdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 运动能力
 *
 * @author rufeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtCompetency implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long compId;
    private String compName;
    private String compDesp;
    private LocalDateTime compCreated;
    private LocalDateTime compMidified;
}