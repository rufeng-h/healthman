package com.rufeng.healthman.pojo.ptdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 学院表
 *
 * @author rufeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtCollege implements Serializable {
    private static final long serialVersionUID = 1L;
    private String clgCode;
    private Long clgId;
    /**
     * 学院名称
     */
    private String clgName;
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
}