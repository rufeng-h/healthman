package com.rufeng.healthman.pojo.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author rufeng
 * @time 2022-04-18 18:11
 * @package com.rufeng.healthman.pojo.dto.ptadmin
 * @description TODO
 */
@Data
@NoArgsConstructor
public class AdminFormdata {
    @NotEmpty
    private String adminId;
    @NotEmpty
    private String desp;
    @NotNull
    private LocalDate birth;
    @NotEmpty
    private String email;
    @NotEmpty
    private String phone;
    @NotEmpty
    private String avatar;
}
