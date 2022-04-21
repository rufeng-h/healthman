package com.rufeng.healthman.pojo.ptdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 科目
 *
 * @author rufeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtSubject implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long subId;
    /**
     * 科目名称
     */
    private String subName;
    /**
     * 备注信息
     */
    private String subDesp;
    private LocalDateTime subCreated;
    private LocalDateTime subModified;
    /**
     * 运动能力id
     */
    private Long compId;
}