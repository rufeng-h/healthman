package com.rufeng.healthman.pojo.ptdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 接口表
 * @author rufeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtOperation implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long operId;
    private String operName;
    private String operSummary;
    private String operDesp;
    private LocalDateTime created;
    private Long pid;
}