package com.rufeng.healthman.pojo.DTO.ptstu;

import com.rufeng.healthman.enums.GenderEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rufeng
 * @time 2022-04-02 22:48
 * @package com.rufeng.healthman.pojo.DTO.ptstu
 * @description 基本信息
 */
@Data
@NoArgsConstructor
public class StudentBaseInfo {
    private String stuId;
    private GenderEnum stuGender;
    private Integer grade;
    private String stuName;
}
