package com.rufeng.healthman.pojo.m2m;

import com.rufeng.healthman.pojo.ptdo.PtSubject;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-04-06 12:43
 * @package com.rufeng.healthman.pojo.m2m
 * @description 科目组对应科目
 */
@Data
public class PtSubGrpSubject {
    private List<PtSubject> subjects;
    private Long grpId;
}
