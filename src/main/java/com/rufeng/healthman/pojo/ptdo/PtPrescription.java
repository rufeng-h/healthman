package com.rufeng.healthman.pojo.ptdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


/**
 * 运动处方
 *
 * @author rufeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PtPrescription implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long prsId;
    private Long compId;
    private String prsLevel;
    private String prsText;
    private Date prsCreated;
    private Date prsModified;
}