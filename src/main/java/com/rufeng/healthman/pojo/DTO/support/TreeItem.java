package com.rufeng.healthman.pojo.DTO.support;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-12 10:05
 * @package com.rufeng.healthman.pojo.BO.support
 * @description 前端树型组件节点
 */
public interface TreeItem {
    /**
     * title
     *
     * @return title
     */
    String getTitle();

    /**
     * 节点key，必须唯一
     *
     * @return key
     */
    Serializable getKey();

    /**
     * 子数据
     *
     * @return chidren
     */
    default List<TreeItem> getChildren() {
        return getIsLeaf() ? null : Collections.emptyList();
    }

    /**
     * 是否叶节点
     *
     * @return bool
     */
    boolean getIsLeaf();
}
