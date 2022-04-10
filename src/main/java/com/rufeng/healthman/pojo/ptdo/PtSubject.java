package com.rufeng.healthman.pojo.ptdo;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 科目
 * @author rufeng
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

    /**
     * 运动能力id
     */
    private Long compId;

    private static final long serialVersionUID = 1L;
}