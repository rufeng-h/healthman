package com.rufeng.healthman.domain;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "2020-01-01 19:00:00", title = "创建时间", type = "string")
    private Date createTime;
    @Schema(example = "2020-01-01 19:00:00", title = "上次登录", type = "string")
    private Date lastLoginTime;
    private Integer status;
    private Integer age;
    private String gender;
}
