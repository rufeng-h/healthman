package com.rufeng.healthman.pojo.data;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-05-02 13:53
 * @package com.rufeng.healthman.pojo.data
 * @description 分享科目组
 */
@Data
public class PtSubGrpShareFormdata {
    @NotNull
    private Long grpId;
    private List<@NotEmpty String> teaIds;
}
