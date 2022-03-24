package com.rufeng.healthman.pojo.DTO.ptclass;

import com.rufeng.healthman.pojo.DO.PtClass;
import com.rufeng.healthman.pojo.DTO.support.TreeItem;

import java.io.Serializable;
import java.time.LocalDateTime;

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
        this.key = ptClass.getClsCode();
        this.title = ptClass.getClsName();
        this.collegeName = ptClass.getClsName();
        int year = LocalDateTime.now().getYear();
        this.grade = ptClass.getClsEntryGrade() + ptClass.getClsEntryYear() - year;
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