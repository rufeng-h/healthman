package com.rufeng.healthman.pojo.data;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author rufeng
 * @time 2022-05-01 13:45
 * @package com.rufeng.healthman.pojo.data
 * @description 更新学院信息
 */
@Data
public class PtCollegeFomrdata {
    @NotEmpty
    private String clgCode;
    @NotEmpty
    private String teaId;
    @NotEmpty
    private String clgName;
    @NotEmpty
    private String clgHome;
    @NotEmpty
    private String clgTel;
    @NotEmpty
    private String clgOffice;
}
