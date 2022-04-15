package com.rufeng.healthman.pojo.dto.ptstu;

import com.rufeng.healthman.enums.GenderEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author rufeng
 * @time 2022-04-02 22:48
 * @package com.rufeng.healthman.pojo.DTO.ptstu
 * @description 基本信息，查询评分标准、分页
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class StudentBaseInfo {
    private String stuId;
    private GenderEnum stuGender;
    private Integer grade;
    private String stuName;
}
