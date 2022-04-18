package com.rufeng.healthman.pojo.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author rufeng
 * @time 2022-04-18 17:20
 * @package com.rufeng.healthman.pojo.data
 * @description 更新学生信息
 */
@NoArgsConstructor
@Data
public class StudentFormData {
    @NotEmpty
    private String stuId;
    @NotEmpty
    private String avatar;
    @NotEmpty
    private String desp;
    @NotNull
    private LocalDate birth;
}
