package com.rufeng.healthman.enums;

/**
 * @author rufeng
 * @time 2022-04-21 19:58
 * @package com.rufeng.healthman.enums
 * @description TODO
 */
public enum ApiEnum {
    /**
     * enum
     */
    COLLEGE_GET("college:get", "学院"),
    COLLEGE_PAGE("college:page", "学院列表"),
    COLLEGE_TEMPLATE("college:template", "学院模板文件"),
    COLLEGE_UPLOAD("college:upload", "学院上传"),


    CLASS_PAGE("class:page", "班级列表"),
    CLASS_GET("class:get", "班级"),
    CLASS_LIST("class:list", "所有班级");
    private final String id;
    private final String desp;

    ApiEnum(String id, String desp) {
        this.id = id;
        this.desp = desp;
    }
}
