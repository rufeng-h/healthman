package com.rufeng.healthman.pojo.DO;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author rufeng
 * @time 2022-03-30 0:19
 * @package com.rufeng.healthman.pojo.DO
 * @description TODO
 */

/**
    * 班级、测量中间表
 * @author rufeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PtClassMesurement implements Serializable {
    private Long cmsId;

    private Long msId;

    private String clsCode;

    private Date cmsCreated;

    private Date cmsModified;

    /**
    * admin_id
    */
    private String cmsCreatedAdmin;

    private static final long serialVersionUID = 1L;
}