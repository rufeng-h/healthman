package com.rufeng.healthman.security.authority;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author rufeng
 * @time 2022-04-21 23:54
 * @package com.rufeng.healthman.security.authority
 * @description TODO
 */
public class Authority {
    public static final Set<String> DEFAULT_STUDENT_AUTHORITIES = new TreeSet<>();
    public static final Set<String> DEFAULT_TEACHER_AUTHORITIES = new TreeSet<>();
    public static final Set<String> DEFAULT_ADMIN_AUTHORITIES = new TreeSet<>();

    static {
        DEFAULT_STUDENT_AUTHORITIES.add(PtCollege.COLLEGE_PAGE);
        DEFAULT_STUDENT_AUTHORITIES.add(PtCollege.COLLEGE_LIST);
        DEFAULT_STUDENT_AUTHORITIES.add(PtClass.CLASS_PAGE);
        DEFAULT_STUDENT_AUTHORITIES.add(PtTeacher.TEACHER_PAGE);

        DEFAULT_STUDENT_AUTHORITIES.add(PtStudent.STUDENT_GET);
        DEFAULT_STUDENT_AUTHORITIES.add(PtSubject.SUBJECT_GET);
        DEFAULT_STUDENT_AUTHORITIES.add(PtSubject.SUBJECT_PAGE);
        DEFAULT_STUDENT_AUTHORITIES.add(PtScoreSheet.SCOS_PAGE);
        DEFAULT_STUDENT_AUTHORITIES.add(PtMs.MS_STUDETAIL);
    }

    static {
        DEFAULT_TEACHER_AUTHORITIES.add(PtCollege.COLLEGE_LIST);
        DEFAULT_TEACHER_AUTHORITIES.add(PtCollege.COLLEGE_PAGE);
        DEFAULT_TEACHER_AUTHORITIES.add(PtCollege.COLLEGE_GET);

        DEFAULT_TEACHER_AUTHORITIES.add(PtTeacher.TEACHER_PAGE);
        DEFAULT_TEACHER_AUTHORITIES.add(PtTeacher.TEACHER_LIST);

        DEFAULT_TEACHER_AUTHORITIES.add(PtClass.CLASS_PAGE);
        DEFAULT_TEACHER_AUTHORITIES.add(PtClass.CLASS_GET);
        DEFAULT_TEACHER_AUTHORITIES.add(PtClass.CLASS_LIST);

        DEFAULT_TEACHER_AUTHORITIES.add(PtStudent.STUDENT_PAGE);
        DEFAULT_TEACHER_AUTHORITIES.add(PtStudent.STUDENT_GET);

        DEFAULT_TEACHER_AUTHORITIES.add(PtSubject.SUBJECT_PAGE);
        DEFAULT_TEACHER_AUTHORITIES.add(PtScoreSheet.SCOS_PAGE);
        DEFAULT_TEACHER_AUTHORITIES.add(PtSubject.SUBJECT_GET);
        DEFAULT_TEACHER_AUTHORITIES.add(PtSubGroup.SUBGRP_PAGE);
        DEFAULT_TEACHER_AUTHORITIES.add(PtSubGroup.SUBGRP_DELETE);
        DEFAULT_TEACHER_AUTHORITIES.add(PtSubGroup.SUBGRP_LIST);
        DEFAULT_TEACHER_AUTHORITIES.add(PtSubGroup.SUBGRP_INSERT);
        DEFAULT_TEACHER_AUTHORITIES.add(PtSubGroup.SUBGRP_SHARE);
        DEFAULT_TEACHER_AUTHORITIES.add(PtSubGroup.SUB_DELETE);

        DEFAULT_TEACHER_AUTHORITIES.add(PtMs.MS_PAGE);
        DEFAULT_TEACHER_AUTHORITIES.add(PtMs.MS_DELETE);
        DEFAULT_TEACHER_AUTHORITIES.add(PtMs.MS_STUDETAIL);
        DEFAULT_TEACHER_AUTHORITIES.add(PtMs.MS_UPDATE);
        DEFAULT_TEACHER_AUTHORITIES.add(PtMs.MS_INSERT);
        DEFAULT_TEACHER_AUTHORITIES.add(PtMs.MS_TEMPLATE);
        DEFAULT_TEACHER_AUTHORITIES.add(PtMs.MS_DETAIL);
        DEFAULT_TEACHER_AUTHORITIES.add(PtScore.SCORE_PAGE);
        DEFAULT_TEACHER_AUTHORITIES.add(PtScore.SCORE_DOWNLOAD);
        DEFAULT_TEACHER_AUTHORITIES.add(PtScore.SCORE_UPLOAD);
    }

    static {
        DEFAULT_ADMIN_AUTHORITIES.add(PtCollege.COLLEGE_PAGE);
        DEFAULT_ADMIN_AUTHORITIES.add(PtCollege.COLLEGE_GET);
        DEFAULT_ADMIN_AUTHORITIES.add(PtCollege.COLLEGE_TEMPLATE);
        DEFAULT_ADMIN_AUTHORITIES.add(PtCollege.COLLEGE_UPLOAD);
        DEFAULT_ADMIN_AUTHORITIES.add(PtCollege.COLLEGE_LIST);
        DEFAULT_ADMIN_AUTHORITIES.add(PtCollege.COLLEGE_DELETE);
        DEFAULT_ADMIN_AUTHORITIES.add(PtCollege.COLLEGE_UPDATE);

        DEFAULT_ADMIN_AUTHORITIES.add(PtClass.CLASS_LIST);
        DEFAULT_ADMIN_AUTHORITIES.add(PtClass.CLASS_UPLOAD);
        DEFAULT_ADMIN_AUTHORITIES.add(PtClass.CLASS_TEMPLATE);
        DEFAULT_ADMIN_AUTHORITIES.add(PtClass.CLASS_PAGE);
        DEFAULT_ADMIN_AUTHORITIES.add(PtClass.CLASS_GET);
        DEFAULT_ADMIN_AUTHORITIES.add(PtClass.CLASS_DELETE);
        DEFAULT_ADMIN_AUTHORITIES.add(PtClass.CLASS_UPDATE);

        DEFAULT_ADMIN_AUTHORITIES.add(PtTeacher.TEACHER_PAGE);
        DEFAULT_ADMIN_AUTHORITIES.add(PtTeacher.TEACHER_TEMPLATE);
        DEFAULT_ADMIN_AUTHORITIES.add(PtTeacher.TEACHER_UPLOAD);
        DEFAULT_ADMIN_AUTHORITIES.add(PtTeacher.TEACHER_LIST);
        DEFAULT_ADMIN_AUTHORITIES.add(PtTeacher.PWD_RESET);
        DEFAULT_ADMIN_AUTHORITIES.add(PtTeacher.TEAHCER_DELETE);

        DEFAULT_ADMIN_AUTHORITIES.add(PtStudent.STUDENT_PAGE);
        DEFAULT_ADMIN_AUTHORITIES.add(PtStudent.STUDENT_GET);
        DEFAULT_ADMIN_AUTHORITIES.add(PtStudent.STUDENT_UPLOAD);
        DEFAULT_ADMIN_AUTHORITIES.add(PtStudent.STUDENT_TEMPLATE);
        DEFAULT_ADMIN_AUTHORITIES.add(PtStudent.STUDENT_DELETE);
        DEFAULT_ADMIN_AUTHORITIES.add(PtStudent.PWD_RESET);


        DEFAULT_ADMIN_AUTHORITIES.add(PtMs.MS_PAGE);
        DEFAULT_ADMIN_AUTHORITIES.add(PtMs.MS_DETAIL);

        DEFAULT_ADMIN_AUTHORITIES.add(PtScore.SCORE_PAGE);

        DEFAULT_ADMIN_AUTHORITIES.add(PtOperation.OPERATION_LIST);
    }

    public static class PtSubGroup {

        /**
         * subGrp
         */
        public static final String SUBGRP_DELETE = "subGrp:delete";
        public static final String SUBGRP_INSERT = "subGrp:insert";
        public static final String SUBGRP_LIST = "subGrp:list";
        public static final String SUBGRP_PAGE = "subGrp:page";
        public static final String SUB_DELETE = "subGrp:subDelete";
        public static final String SUBGRP_SHARE = "subGrp:share";
    }

    public static class PtTeacher {
        /**
         * teacher
         */
        public static final String TEACHER_PAGE = "teacher:page";
        public static final String TEACHER_TEMPLATE = "teacher:template";
        public static final String TEACHER_UPLOAD = "teacher:upload";
        public static final String TEAHCER_DELETE = "teacher:delete";
        public static final String TEACHER_LIST = "teacher:list";
        public static final String PWD_RESET = "teacher:pwdReset";
    }

    public static class PtMs {

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
    }

    public static class PtScoreSheet {

        /**
         * scoreSheet
         */
        public static final String SCOS_DELETE = "scos:delete";
        public static final String SCOS_PAGE = "scos:page";
        public static final String SCOS_UPDATE = "scos:update";
        public static final String SCOS_UPLOAD = "scos:upload";
    }

    public static class PtStudent {

        /**
         * student
         */
        public static final String STUDENT_GET = "student:get";
        public static final String STUDENT_PAGE = "student:page";
        public static final String STUDENT_UPLOAD = "student:upload";
        public static final String STUDENT_TEMPLATE = "student:template";
        public static final String STUDENT_DELETE = "student:delete";
        public static final String PWD_RESET = "student:pwdReset";
    }

    public static class PtScore {

        /**
         * score
         */
        public static final String SCORE_DOWNLOAD = "score:download";
        public static final String SCORE_PAGE = "score:page";
        public static final String SCORE_STU = "score:stu";
        public static final String SCORE_UPLOAD = "score:upload";
    }

    public static class PtCollege {
        /**
         * college
         */
        public static final String COLLEGE_GET = "college:get";
        public static final String COLLEGE_LIST = "college:list";
        public static final String COLLEGE_PAGE = "college:page";
        public static final String COLLEGE_TEMPLATE = "college:template";
        public static final String COLLEGE_UPLOAD = "college:upload";
        public static final String COLLEGE_DELETE = "college:delete";
        public static final String COLLEGE_UPDATE = "college:update";
    }

    public static class PtSubject {

        /**
         * subject
         */
        public static final String SUBJECT_DELETE = "subject:delete";
        public static final String SUBJECT_GET = "subject:get";
        public static final String SUBJECT_INSERT = "subject:insert";
        public static final String SUBJECT_LIST = "subject:list";
        public static final String SUBJECT_PAGE = "subject:page";
        public static final String SUBJECT_UPDATE = "subject:update";
    }

    public static class PtClass {
        /**
         * class
         */
        public static final String CLASS_GET = "class:get";
        public static final String CLASS_LIST = "class:list";
        public static final String CLASS_PAGE = "class:page";
        public static final String CLASS_TEMPLATE = "class:template";
        public static final String CLASS_UPLOAD = "class:upload";
        public static final String CLASS_DELETE = "class:delete";
        public static final String CLASS_UPDATE = "class:update";
    }

    public static class PtOperation {
        public static final String OPERATION_LIST = "operation:list";
    }
}
