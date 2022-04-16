package com.rufeng.healthman.pojo.ptdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 班级、测量中间表
 *
 * @author rufeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PtClassMeasurement implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long cmsId;
    private Long msId;
    private String clsCode;
    private LocalDateTime cmsCreated;
    private LocalDateTime cmsModified;
    private String cmsCreatedAdmin;
}