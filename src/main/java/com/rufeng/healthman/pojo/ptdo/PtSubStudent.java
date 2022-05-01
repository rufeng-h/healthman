package com.rufeng.healthman.pojo.ptdo;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 年级、性别对应的测试科目
 * @author rufeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtSubStudent implements Serializable {
    private Long subId;

    private Integer grade;

    private String gender;

    private Long id;

    private LocalDateTime subsCreated;

    private static final long serialVersionUID = 1L;
}