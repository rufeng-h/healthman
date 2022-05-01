package com.rufeng.healthman.pojo.data;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author rufeng
 * @time 2022-05-01 10:39
 * @package com.rufeng.healthman.pojo.data
 * @description 更新班级信息
 */
@Data
public class PtClassFormdata {
    /**
     * 唯一性，存在校验
     */
    @NotEmpty
    private String clsCode;
    @NotEmpty
    private String clsName;
    @NotEmpty
    private String teaId;
    private String clgCode;
}
