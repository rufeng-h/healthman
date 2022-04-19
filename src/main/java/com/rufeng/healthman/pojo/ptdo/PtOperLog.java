package com.rufeng.healthman.pojo.ptdo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.rufeng.healthman.enums.OperTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author rufeng
 * @time 2022-04-19 14:18
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
    private String operExp;

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

    private LocalDateTime operTime;

    /**
     * 1 成功 0 失败
     */
    private Integer operStatus;

    private OperTypeEnum operType;

    private static final long serialVersionUID = 1L;
}