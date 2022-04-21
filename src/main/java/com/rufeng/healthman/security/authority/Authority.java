package com.rufeng.healthman.security.authority;

import java.util.HashSet;
import java.util.Set;

/**
 * @author rufeng
 * @time 2022-04-21 23:54
 * @package com.rufeng.healthman.security.authority
 * @description TODO
 */
public class Authority {
    /**
     * class
     */
    public static final String CLASS_GET = "class:get";
    public static final String CLASS_GRADELIST = "class:gradeList";
    public static final String CLASS_LIST = "class:list";
    public static final String CLASS_PAGE = "class:page";
    public static final String CLASS_TEMPLATE = "class:template";
    public static final String CLASS_UPLOAD = "class:upload";
    /**
     * college
     */
    public static final String COLLEGE_GET = "college:get";
    public static final String COLLEGE_LIST = "college:list";
    public static final String COLLEGE_PAGE = "college:page";
    public static final String COLLEGE_TEMPLATE = "college:template";
    public static final String COLLEGE_UPLOAD = "college:upload";
    /**
     * measurement
     */
    public static final String MS_DELETE = "ms:delete";
    public static final String MS_DETAIL = "ms:detail";
    public static final String MS_INSERT = "ms:insert";
    public static final String MS_PAGE = "ms:page";
    public static final String MS_STUDETAIL = "ms:stuDetail";
    public static final String MS_TEMPLATE = "ms:template";
    public static final String MS_UPDATE = "ms:update";
    /**
     * score
     */
    public static final String SCORE_DOWNLOAD = "score:download";
    public static final String SCORE_PAGE = "score:page";
    public static final String SCORE_STU = "score:stu";
    public static final String SCORE_UPLOAD = "score:upload";
    /**
     * scoreSheet
     */
    public static final String SCOS_DELETE = "scos:delete";
    public static final String SCOS_PAGE = "scos:page";
    public static final String SCOS_UPDATE = "scos:update";
    public static final String SCOS_UPLOAD = "scos:upload";
    /**
     * student
     */
    public static final String STUDENT_GET = "student:get";
    public static final String STUDENT_PAGE = "student:page";
    public static final String STUDENT_UPLOAD = "student:upload";
    public static final String STUDENT_TEMPLATE = "student:template";
    /**
     * subGrp
     */
    public static final String SUBGRP_DELETE = "subGrp:delete";
    public static final String SUBGRP_INSERT = "subGrp:insert";
    public static final String SUBGRP_LIST = "subGrp:list";
    public static final String SUBGRP_PAGE = "subGrp:page";
    /**
     * subject
     */
    public static final String SUBJECT_DELETE = "subject:delete";
    public static final String SUBJECT_GET = "subject:get";
    public static final String SUBJECT_INSERT = "subject:insert";
    public static final String SUBJECT_LIST = "subject:list";
    public static final String SUBJECT_PAGE = "subject:page";
    public static final String SUBJECT_UPDATE = "subject:update";
    /**
     * teacher
     */
    public static final String TEACHER_PAGE = "teacher:page";
    public static final String TEACHER_TEMPLATE = "teacher:template";
    public static final String TEACHER_UPLOAD = "teacher:upload";

    public static final Set<String> DEFAULT_STUDENT_AUTHORITIES = new HashSet<>();
    public static final Set<String> DEFAULT_TEACHER_AUTHORITIES = new HashSet<>();
    public static final Set<String> DEFAULT_ADMIN_AUTHORITIES = new HashSet<>();

    static {
        DEFAULT_STUDENT_AUTHORITIES.add(STUDENT_GET);
        DEFAULT_STUDENT_AUTHORITIES.add(SUBJECT_GET);
        DEFAULT_STUDENT_AUTHORITIES.add(SUBJECT_PAGE);
        DEFAULT_STUDENT_AUTHORITIES.add(SCOS_PAGE);
        DEFAULT_STUDENT_AUTHORITIES.add(MS_STUDETAIL);
    }

    static {
        DEFAULT_TEACHER_AUTHORITIES.add(COLLEGE_LIST);
        DEFAULT_TEACHER_AUTHORITIES.add(COLLEGE_PAGE);
        DEFAULT_TEACHER_AUTHORITIES.add(COLLEGE_GET);

        DEFAULT_TEACHER_AUTHORITIES.add(TEACHER_PAGE);

        DEFAULT_TEACHER_AUTHORITIES.add(CLASS_PAGE);
        DEFAULT_TEACHER_AUTHORITIES.add(CLASS_TEMPLATE);
        DEFAULT_TEACHER_AUTHORITIES.add(CLASS_GRADELIST);
        DEFAULT_TEACHER_AUTHORITIES.add(CLASS_GET);
        DEFAULT_TEACHER_AUTHORITIES.add(CLASS_LIST);

        DEFAULT_TEACHER_AUTHORITIES.add(STUDENT_PAGE);
        DEFAULT_TEACHER_AUTHORITIES.add(STUDENT_GET);
        DEFAULT_TEACHER_AUTHORITIES.add(STUDENT_TEMPLATE);
        DEFAULT_TEACHER_AUTHORITIES.add(STUDENT_UPLOAD);

        DEFAULT_TEACHER_AUTHORITIES.add(SUBJECT_PAGE);
        DEFAULT_TEACHER_AUTHORITIES.add(SCOS_PAGE);
        DEFAULT_TEACHER_AUTHORITIES.add(SUBJECT_GET);
        DEFAULT_TEACHER_AUTHORITIES.add(SUBGRP_PAGE);
        DEFAULT_TEACHER_AUTHORITIES.add(SUBGRP_DELETE);
        DEFAULT_TEACHER_AUTHORITIES.add(SUBGRP_LIST);
        DEFAULT_TEACHER_AUTHORITIES.add(SUBGRP_INSERT);

        DEFAULT_TEACHER_AUTHORITIES.add(MS_PAGE);
        DEFAULT_TEACHER_AUTHORITIES.add(MS_DELETE);
        DEFAULT_TEACHER_AUTHORITIES.add(MS_STUDETAIL);
        DEFAULT_TEACHER_AUTHORITIES.add(MS_UPDATE);
        DEFAULT_TEACHER_AUTHORITIES.add(MS_INSERT);
        DEFAULT_TEACHER_AUTHORITIES.add(MS_TEMPLATE);
        DEFAULT_TEACHER_AUTHORITIES.add(MS_DETAIL);
        DEFAULT_TEACHER_AUTHORITIES.add(SCORE_PAGE);
        DEFAULT_TEACHER_AUTHORITIES.add(SCORE_DOWNLOAD);
        DEFAULT_TEACHER_AUTHORITIES.add(SCORE_UPLOAD);
    }

    static {
        DEFAULT_ADMIN_AUTHORITIES.add(MS_PAGE);
        DEFAULT_ADMIN_AUTHORITIES.add(MS_DETAIL);

        DEFAULT_ADMIN_AUTHORITIES.add(SCORE_PAGE);
        DEFAULT_ADMIN_AUTHORITIES.add(COLLEGE_PAGE);
        DEFAULT_ADMIN_AUTHORITIES.add(COLLEGE_GET);
        DEFAULT_ADMIN_AUTHORITIES.add(COLLEGE_TEMPLATE);
        DEFAULT_ADMIN_AUTHORITIES.add(COLLEGE_UPLOAD);
        DEFAULT_ADMIN_AUTHORITIES.add(COLLEGE_LIST);

        DEFAULT_ADMIN_AUTHORITIES.add(CLASS_PAGE);
        DEFAULT_ADMIN_AUTHORITIES.add(CLASS_GRADELIST);
    }
}
