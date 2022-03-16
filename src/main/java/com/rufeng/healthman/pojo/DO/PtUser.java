package com.rufeng.healthman.pojo.DO;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String phone;
    private String email;
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
    @JsonIgnore
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
    /**
     * 上次登录
     */
    private LocalDateTime lastLoginTime;
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