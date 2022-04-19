package com.rufeng.healthman.enums;

import lombok.Getter;

/**
 * @author rufeng
 * @time 2022-04-19 14:12
 * @package com.rufeng.healthman.common.aop
 * @description TODO
 */
@Getter
public enum OperTypeEnum {
    /**
     * 操作类型
     */
    UPDATE("更新"),
    INSERT("添加"),
    OTHER("其他"),
    DELETE("删除");

    private final String value;

    OperTypeEnum(String value) {
        this.value = value;
    }
}
