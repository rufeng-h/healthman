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
    * 科目
    */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PtSubject implements Serializable {
    private Long subId;

    /**
    * 科目名称
    */
    private String subName;

    /**
    * 备注信息
    */
    private String subDesp;

    private Date subCreated;

    private Date subModified;

    private static final long serialVersionUID = 1L;
}