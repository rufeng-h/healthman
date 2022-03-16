package com.rufeng.healthman.pojo.DTO.ptcollege;

import com.rufeng.healthman.pojo.DO.PtCollege;
import com.rufeng.healthman.pojo.DO.PtMajor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-13 9:07
 * @package com.rufeng.healthman.pojo.DTO.ptcollege
 * @description 学院信息，包含年级和专业
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PtCollegeInfo extends PtCollege {
    private List<Integer> grades;
    private List<PtMajor> majors;
}
