package com.rufeng.healthman.pojo.DO;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 *
 * @author rufeng
 * @TableName pt_user
 */
@Data
@Alias("PtUser")
public class PtUser implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String username;
    /**
     *
     */
    private String password;
    /**
     * 头像
     */
    private String avatar;
    /**
     *
     */
    private String desp;
    /**
     *
     */
    private LocalDateTime createdTime;
    private LocalDateTime lastLoginTime;
    /**
     *
     */
    private LocalDateTime lastModifyTime;
    /**
     *
     */
    private String deleted;
}