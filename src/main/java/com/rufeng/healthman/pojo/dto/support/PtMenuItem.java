package com.rufeng.healthman.pojo.dto.support;

import lombok.Data;

/**
 * @author rufeng
 * @time 2022-04-21 12:10
 * @package com.rufeng.healthman.pojo.dto.support
 * @description TODO
 */
@Data
public class PtMenuItem {
    public static final PtMenuItem ADMIN_MENU_ITEM = new PtMenuItem("admin");
    public static final PtMenuItem BASEDATA_MENU_ITEM = new PtMenuItem("basedata");
    public static final PtMenuItem MEASUREMENT_MENU_ITEM = new PtMenuItem("measurement");
    public static final PtMenuItem SUBJECT_MENU_ITEM = new PtMenuItem("subject");
    private String name;

    public PtMenuItem(String name) {
        this.name = name;
    }
}
