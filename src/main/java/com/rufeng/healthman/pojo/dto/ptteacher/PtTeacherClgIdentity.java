package com.rufeng.healthman.pojo.dto.ptteacher;

import lombok.Data;

/**
 * @author rufeng
 * @time 2022-05-01 14:15
 * @package com.rufeng.healthman.pojo.dto.ptteacher
 * @description 教师，仅包含id和学院信息
 */
@Data
public class PtTeacherClgIdentity {
    private String teaId;
    private String clgCode;
    private Boolean principal;
}
