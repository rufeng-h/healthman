package com.rufeng.healthman.pojo.DO;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author rufeng
 * @time 2022-03-27 20:23
 * @package com.rufeng.healthman.pojo.DO
 * @description TODO
 */

/**
    * 科目组
    */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PtSubgroup implements Serializable {
    private Long grpId;

    private String grpName;

    private String grpDesp;

    private Date grpCreated;

    private String grpCreatedAdmin;

    private Date grpModified;

    private static final long serialVersionUID = 1L;
}