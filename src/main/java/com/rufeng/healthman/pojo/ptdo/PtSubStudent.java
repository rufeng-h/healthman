package com.rufeng.healthman.pojo.ptdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 年级、性别对应测试的科目
 *
 * @author rufeng
 * @time 2022-04-11 14:22
 * @package com.rufeng.healthman.pojo.ptdo
 * @description TODO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PtSubStudent implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long subId;
    private Integer grade;
    private String gender;
    private Long id;
    private Date subsCreated;
}