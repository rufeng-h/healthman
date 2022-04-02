package com.rufeng.healthman.pojo.DO;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 测量记录表
 * @author rufeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PtMeasurement implements Serializable {
    private Long msId;

    private String msName;

    private String msDesp;

    private Date msCreated;

    private Date msModified;

    /**
     * 科目组id
     */
    private Long grpId;

    private String msCreatedAdmin;

    private static final long serialVersionUID = 1L;
}