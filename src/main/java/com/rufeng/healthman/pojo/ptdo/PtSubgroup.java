package com.rufeng.healthman.pojo.ptdo;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author rufeng
 * @time 2022-04-16 14:17
 * @package com.rufeng.healthman.pojo.ptdo
 * @description TODO
 */

/**
 * 科目组
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

    private String grpCreatedAdmin;

    private LocalDateTime grpModified;

    private static final long serialVersionUID = 1L;
}