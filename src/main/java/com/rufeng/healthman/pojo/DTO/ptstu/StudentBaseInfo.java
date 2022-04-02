package com.rufeng.healthman.pojo.DTO.ptstu;

import com.rufeng.healthman.enums.GenderEnum;
import lombok.Data;

/**
 * @author rufeng
 * @time 2022-04-02 22:48
 * @package com.rufeng.healthman.pojo.DTO.ptstu
 * @description 基本信息
 */
@Data
public class StudentBaseInfo {
    private String stuId;
    private GenderEnum gender;
    private Integer grade;
}
