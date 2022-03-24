package com.rufeng.healthman.pojo.DO;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 科目
 * </p>
 *
 * @author rufeng
 * @since 2022-03-19
 */
@Data
@Alias("PtSubject")
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
}
