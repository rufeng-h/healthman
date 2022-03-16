package com.rufeng.healthman.pojo.DTO.ptcollege;

import com.rufeng.healthman.pojo.DO.PtCollege;
import com.rufeng.healthman.pojo.DTO.support.TreeItem;

import java.io.Serializable;

/**
 * @author rufeng
 * @time 2022-03-12 10:08
 * @package com.rufeng.healthman.pojo.BO
 * @description TODO
 */
public class PtCollegeTreeItem implements TreeItem {
    private final String title;
    private final Long key;

    public PtCollegeTreeItem(PtCollege college) {
        this.title = college.getName();
        this.key = college.getId();
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public Serializable getKey() {
        return this.key;
    }

    @Override
    public boolean getIsLeaf() {
        return false;
    }
}
