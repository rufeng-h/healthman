package com.rufeng.healthman.pojo.DO;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 学院表
 *
 * @author rufeng
 * @TableName pt_college
 */
@Data
@Alias("PtCollege")
public class PtCollege implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Long id;
    /**
     * 学院名称
     */
    private String name;
    /**
     * 负责人
     */
    private String principal;
    /**
     * 办公室
     */
    private String office;
    /**
     * 电话
     */
    private String tel;
    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
    /**
     * 上次修改
     */
    private LocalDateTime lastModifyTime;
    /**
     * 是否删除
     */
    private String deleted;
}