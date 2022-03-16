package com.rufeng.healthman.pojo.DTO.ptclass;

import com.rufeng.healthman.pojo.DO.PtClass;
import com.rufeng.healthman.pojo.DTO.support.TreeItem;

import java.io.Serializable;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-12 12:03
 * @package com.rufeng.healthman.pojo.BO.ptclass
 * @description TODO
 */
public class PtClassTreeItem implements TreeItem {
    private final String key;
    private final String title;
    private final String collegeName;
    private final Integer grade;

    public PtClassTreeItem(PtClass ptClass) {
        this.key = ptClass.getCode();
        this.title = ptClass.getName();
        this.collegeName = ptClass.getCollegeName();
        this.grade = ptClass.getGrade();
    }

    public Integer getGrade() {
        return grade;
    }

    public String getCollegeName() {
        return collegeName;
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
        return true;
    }
}