package com.rufeng.healthman.pojo.ptdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 科目组分享
 *
 * @author rufeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtSubGroupShare implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long sid;
    private Long grpId;
    private String teaId;
    private String shareTeaId;
    private LocalDateTime shareTime;
}