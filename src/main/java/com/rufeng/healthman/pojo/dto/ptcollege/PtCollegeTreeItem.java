package com.rufeng.healthman.pojo.dto.ptcollege;

import com.rufeng.healthman.pojo.ptdo.PtCollege;
import com.rufeng.healthman.pojo.dto.support.TreeItem;

import java.io.Serializable;

/**
 * @author rufeng
 * @time 2022-03-12 10:08
 * @package com.rufeng.healthman.pojo.BO
 * @description TODO
 */
public class PtCollegeTreeItem implements TreeItem {
    private final String title;
    private final String key;

    public PtCollegeTreeItem(PtCollege college) {
        this.title = college.getClgName();
        this.key = college.getClgCode();
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
