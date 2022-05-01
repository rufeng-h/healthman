package com.rufeng.healthman.pojo.data;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

/**
 * @author rufeng
 * @time 2022-04-18 18:11
 * @package com.rufeng.healthman.pojo.dto.ptadmin
 * @description TODO
 */
@Data
public class PtUserFormdata {
    @NotEmpty
    private String desp;
    @NotEmpty
    private String avatar;
    private String email;
    private String phone;
    private LocalDate birth;
}
