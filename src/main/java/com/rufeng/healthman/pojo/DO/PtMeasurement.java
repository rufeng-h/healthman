package com.rufeng.healthman.pojo.DO;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author rufeng
 * @time 2022-03-30 0:18
 * @package com.rufeng.healthman.pojo.DO
 * @description TODO
 */

/**
 * 测量记录表
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

    private Integer msModified;

    /**
     * 科目组id
     */
    private Long grpId;

    private String msCreatedAdmin;

    private static final long serialVersionUID = 1L;
}