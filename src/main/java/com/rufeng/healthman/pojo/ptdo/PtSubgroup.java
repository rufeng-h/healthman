package com.rufeng.healthman.pojo.ptdo;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 科目组
 * @author rufeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtSubgroup implements Serializable {
    private Long grpId;

    private String grpName;

    private String grpDesp;

    private LocalDateTime grpCreated;

    private String grpCreatedTeaId;

    private LocalDateTime grpModified;

    private static final long serialVersionUID = 1L;
}