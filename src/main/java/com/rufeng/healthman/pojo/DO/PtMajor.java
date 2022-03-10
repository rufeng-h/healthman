package com.rufeng.healthman.pojo.DO;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 专业表
 *
 * @author rufeng
 * @TableName pt_major
 */
@Data
@Alias("PtMajor")
public class PtMajor implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private String code;
    /**
     *
     */
    private Long id;
    /**
     * 专业名称(国际)
     */
    private String name;
    /**
     * 英文名称
     */
    private String enName;
    /**
     * 所属学院id
     */
    private Long collegeId;
    /**
     * 学院名称
     */
    private String collegeName;
    /**
     *
     */
    private LocalDateTime createdTime;
    /**
     *
     */
    private LocalDateTime lastMidifyTime;
    /**
     *
     */
    private String deleted;
}