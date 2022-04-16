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
 * 操作记录表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtOperLog implements Serializable {
    private Long operId;

    /**
     * 操作模块
     */
    private String operModule;

    /**
     * 操作类型
     */
    private String operType;

    /**
     * 操作说明
     */
    private String operDesc;

    /**
     * 请求参数
     */
    private String operReqParam;

    /**
     * 返回结果
     */
    private String operRes;

    /**
     * 操作方法
     */
    private String operMethod;

    /**
     * 操作人
     */
    private String operAdminId;

    /**
     * 操作人名称
     */
    private String operAdminName;

    /**
     * 请求uri
     */
    private String operUri;

    private String operIp;

    private LocalDateTime operCreatedTime;

    private static final long serialVersionUID = 1L;
}