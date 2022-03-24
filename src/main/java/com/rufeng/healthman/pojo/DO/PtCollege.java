package com.rufeng.healthman.pojo.DO;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 学院表
 * </p>
 *
 * @author rufeng
 * @since 2022-03-19
 */
@Data
@Alias("PtCollege")
public class PtCollege implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long clgId;

    private String clgCode;

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
}
