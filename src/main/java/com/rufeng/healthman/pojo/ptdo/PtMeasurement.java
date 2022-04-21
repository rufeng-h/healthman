package com.rufeng.healthman.pojo.ptdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 测量记录表
 * @author rufeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtMeasurement implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long msId;
    private String msName;
    private String msDesp;
    private LocalDateTime msCreated;
    private LocalDateTime msModified;
    /**
     * 科目组id
     */
    private Long grpId;
    private String msCreatedAdmin;
}