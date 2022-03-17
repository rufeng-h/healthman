package com.rufeng.healthman.pojo.DO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 科目
 *
 * @author rufeng
 * @TableName pt_subject
 */
@Data
@Alias("PtSubject")
public class PtSubject implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Long id;
    /**
     * 科目名称
     */
    private String name;
    /**
     * 备注信息
     */
    private String desp;
    /**
     *
     */
    private LocalDateTime createdTime;
    /**
     *
     */
    private LocalDateTime lastModifyTime;
    /**
     *
     */
    @JsonIgnore
    private String deleted;
}