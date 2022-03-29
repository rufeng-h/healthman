package com.rufeng.healthman.pojo.DTO.ptscoresheet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rufeng.healthman.enums.GenderEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rufeng
 * @time 2022-03-29 9:09
 * @package com.rufeng.healthman.pojo.DTO.ptscore
 * @description TODO
 */
@Data
@NoArgsConstructor
public class SheetInfo {
    @JsonIgnore
    private Long subId;
    private Integer grade;
    private GenderEnum gender;
}
