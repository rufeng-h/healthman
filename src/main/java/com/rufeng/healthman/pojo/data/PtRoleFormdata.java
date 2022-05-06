package com.rufeng.healthman.pojo.data;

import com.rufeng.healthman.validation.group.Insert;
import com.rufeng.healthman.validation.group.Update;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-05-06 9:59
 * @package com.rufeng.healthman.pojo.dto.ptteacher
 * @description 角色
 */
@Data
public class PtRoleFormdata {
    @Null(groups = Insert.class)
    @NotNull(groups = Update.class)
    private Long roleId;
    @NotEmpty
    private String roleName;
    @NotEmpty
    private String roleDesp;
    @NotEmpty(groups = Insert.class)
    private List<@NotNull Long> operIds;

    @Null(groups = Insert.class)
    @NotNull(groups = Update.class)
    private Boolean status;
}
