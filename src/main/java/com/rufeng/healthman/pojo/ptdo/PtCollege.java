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
 * 学院表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtCollege implements Serializable {
    private String clgCode;

    private Long clgId;

    /**
     * 学院名称
     */
    private String clgName;

    /**
     * 负责人
     */
    private String clgPrincipal;

    /**
     * 办公室
     */
    private String clgOffice;

    /**
     * 电话
     */
    private String clgTel;

    /**
     * 主页
     */
    private String clgHome;

    /**
     * 创建时间
     */
    private LocalDateTime clgCreated;

    /**
     * 上次修改
     */
    private LocalDateTime clgModified;

    private static final long serialVersionUID = 1L;
}