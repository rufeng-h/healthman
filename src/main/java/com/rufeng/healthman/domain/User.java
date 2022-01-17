package com.rufeng.healthman.domain;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author rufeng
 * @time 2022-01-10 19:20
 * @package com.rufeng.healthman.domain
 * @description TODO
 */
@Data
@Repository
public class User {
    private Long id;
    private String username;
    private String qq;
    private String password;
    private String mobile;
    private String introduction;
    private String email;
    private Date createTime;
    private Date lastLoginTime;
    private Integer status;
    private Integer age;
    private String gender;
}
