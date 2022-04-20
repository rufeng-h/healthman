/*
 Navicat Premium Data Transfer

 Source Server         : healthman
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : healthman

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 19/04/2022 18:59:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pt_admin
-- ----------------------------
DROP TABLE IF EXISTS pt_teacher;
CREATE TABLE `pt_admin`  (
  `admin_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'e10adc3949ba59abbe56e057f20f883e',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'https://p1.music.126.net/Y3C5ob6SQjXRijaVNBu4Sw==/109951164400648086.jpg' COMMENT '头像',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `admin_desp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '暂无信息',
  `admin_created` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `admin_modified` timestamp(0) NULL DEFAULT NULL,
  `admin_last_login` timestamp(0) NULL DEFAULT NULL,
  `phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `admin_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `aid` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `clg_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '所属学院',
  `admin_gender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `admin_birth` date NOT NULL,
  PRIMARY KEY (`admin_id`) USING BTREE,
  UNIQUE INDEX `uid`(`aid`) USING BTREE,
  INDEX `index_admin_name`(`admin_name`) USING BTREE,
  INDEX `index_clg_code`(`clg_code`) USING BTREE,
  CONSTRAINT `pt_admin_ibfk_1` FOREIGN KEY (`clg_code`) REFERENCES `pt_college` (`clg_code`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2506 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pt_class
-- ----------------------------
DROP TABLE IF EXISTS `pt_class`;
CREATE TABLE `pt_class`  (
  `cls_id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cls_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `cls_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `clg_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `cls_entry_grade` int(0) NOT NULL COMMENT '录入系统时年级',
  `cls_entry_year` int(0) NOT NULL COMMENT '录入系统年份',
  `cls_created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `cls_modified` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`cls_code`) USING BTREE,
  UNIQUE INDEX `pt_class_id_uindex`(`cls_id`) USING BTREE,
  INDEX `clg_code`(`clg_code`) USING BTREE,
  CONSTRAINT `pt_class_ibfk_1` FOREIGN KEY (`clg_code`) REFERENCES `pt_college` (`clg_code`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9188 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '班级表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pt_class_measurement
-- ----------------------------
DROP TABLE IF EXISTS `pt_class_measurement`;
CREATE TABLE `pt_class_measurement`  (
  `cms_id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ms_id` bigint(0) UNSIGNED NOT NULL,
  `cls_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `cms_created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `cms_modified` timestamp(0) NULL DEFAULT NULL,
  `cms_created_admin` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`cms_id`) USING BTREE,
  INDEX `ms_id`(`ms_id`) USING BTREE,
  INDEX `cls_code`(`cls_code`) USING BTREE,
  INDEX `cms_created_admin`(`cms_created_admin`) USING BTREE,
  CONSTRAINT `pt_class_measurement_ibfk_1` FOREIGN KEY (`ms_id`) REFERENCES `pt_measurement` (`ms_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `pt_class_measurement_ibfk_2` FOREIGN KEY (`cls_code`) REFERENCES `pt_class` (`cls_code`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `pt_class_measurement_ibfk_3` FOREIGN KEY (`cms_created_admin`) REFERENCES pt_teacher (tea_id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 94 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '班级、测量中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pt_college
-- ----------------------------
DROP TABLE IF EXISTS `pt_college`;
CREATE TABLE `pt_college`  (
  `clg_id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `clg_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `clg_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '学院名称',
  `clg_principal` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '负责人',
  `clg_office` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '办公室',
  `clg_tel` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '电话',
  `clg_home` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT 'http://windcf.com' COMMENT '主页',
  `clg_created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `clg_modified` timestamp(0) NULL DEFAULT NULL COMMENT '上次修改',
  PRIMARY KEY (`clg_code`) USING BTREE,
  UNIQUE INDEX `college_name_uindex`(`clg_name`) USING BTREE,
  UNIQUE INDEX `pt_college_id_uindex`(`clg_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '学院表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pt_competency
-- ----------------------------
DROP TABLE IF EXISTS `pt_competency`;
CREATE TABLE `pt_competency`  (
  `comp_id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `comp_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `comp_desp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `comp_created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `comp_midified` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`comp_id`) USING BTREE,
  UNIQUE INDEX `pt_competency_comp_name_uindex`(`comp_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '运动能力' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pt_measurement
-- ----------------------------
DROP TABLE IF EXISTS `pt_measurement`;
CREATE TABLE `pt_measurement`  (
  `ms_id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ms_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `ms_desp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `ms_created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `ms_modified` timestamp(0) NULL DEFAULT NULL,
  `grp_id` bigint(0) UNSIGNED NOT NULL COMMENT '科目组id',
  `ms_created_admin` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`ms_id`) USING BTREE,
  UNIQUE INDEX `pt_measurement_ms_name_uindex`(`ms_name`) USING BTREE,
  INDEX `grp_id`(`grp_id`) USING BTREE,
  INDEX `ms_created_admin`(`ms_created_admin`) USING BTREE,
  CONSTRAINT `pt_measurement_ibfk_2` FOREIGN KEY (`grp_id`) REFERENCES `pt_subgroup` (`grp_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `pt_measurement_ibfk_3` FOREIGN KEY (`ms_created_admin`) REFERENCES pt_teacher (tea_id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '测量记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pt_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `pt_oper_log`;
CREATE TABLE `pt_oper_log`  (
  `oper_id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `oper_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '操作说明',
  `oper_req_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '请求参数',
  `oper_exp` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '返回结果',
  `oper_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '操作方法',
  `oper_admin_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '操作人',
  `oper_admin_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '操作人名称',
  `oper_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '请求uri',
  `oper_ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `oper_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `oper_status` int(0) NOT NULL DEFAULT 1 COMMENT '1 成功 0 失败',
  `oper_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`oper_id`) USING BTREE,
  INDEX `oper_admin_id`(`oper_admin_id`) USING BTREE,
  CONSTRAINT `pt_oper_log_ibfk_1` FOREIGN KEY (`oper_admin_id`) REFERENCES pt_teacher (tea_id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '操作记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pt_prescription
-- ----------------------------
DROP TABLE IF EXISTS `pt_prescription`;
CREATE TABLE `pt_prescription`  (
  `prs_id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `comp_id` bigint(0) UNSIGNED NOT NULL,
  `prs_level` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `prs_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `prs_created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `prs_modified` timestamp(0) NULL DEFAULT NULL,
  UNIQUE INDEX `pt_prescription_prs_id_uindex`(`prs_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '运动处方' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pt_role
-- ----------------------------
DROP TABLE IF EXISTS `pt_role`;
CREATE TABLE `pt_role`  (
  `admin_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `role_value` tinyint(0) NOT NULL COMMENT 'delete select update insert\n四位二进制依次表示',
  `role_created` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `role_modified` timestamp(0) NULL DEFAULT NULL,
  `role_id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色类型 SYSTEM COLLEGE CLASS',
  `target` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '班级或学院id',
  PRIMARY KEY (`role_id`) USING BTREE,
  INDEX `admin_id`(`admin_id`) USING BTREE,
  CONSTRAINT `pt_role_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES pt_teacher (tea_id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2515 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pt_score
-- ----------------------------
DROP TABLE IF EXISTS `pt_score`;
CREATE TABLE `pt_score`  (
  `sco_id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `stu_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `sub_id` bigint(0) UNSIGNED NOT NULL COMMENT '科目id',
  `sco_data` decimal(7, 3) NOT NULL COMMENT '测试数据',
  `sco_created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `sco_modified` timestamp(0) NULL DEFAULT NULL,
  `ms_id` bigint(0) UNSIGNED NOT NULL COMMENT '测量id',
  `sco_level` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `score` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`sco_id`) USING BTREE,
  UNIQUE INDEX `pt_score_id_uindex`(`sco_id`) USING BTREE,
  INDEX `stu_id`(`stu_id`) USING BTREE,
  INDEX `pt_score_index_all`(`ms_id`, `stu_id`, `sub_id`, `sco_data`) USING BTREE,
  CONSTRAINT `pt_score_ibfk_1` FOREIGN KEY (`stu_id`) REFERENCES `pt_student` (`stu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `pt_score_ibfk_2` FOREIGN KEY (`ms_id`) REFERENCES `pt_measurement` (`ms_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2090978 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '成绩表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pt_score_sheet
-- ----------------------------
DROP TABLE IF EXISTS `pt_score_sheet`;
CREATE TABLE `pt_score_sheet`  (
  `sub_id` bigint(0) UNSIGNED NOT NULL COMMENT '科目',
  `grade` int(0) NOT NULL,
  `gender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '性别',
  `upper` decimal(7, 3) NOT NULL DEFAULT 9999.990 COMMENT '上区间',
  `lower` decimal(7, 3) NOT NULL DEFAULT -9999.990 COMMENT '下区间',
  `score` int(0) NULL DEFAULT NULL,
  `created_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `last_modify_time` timestamp(0) NULL DEFAULT NULL,
  `level` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '级别',
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`sub_id`, `gender`, `grade`, `upper`, `lower`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE,
  INDEX `pt_score_sheet_all_index`(`upper`, `lower`, `sub_id`, `grade`, `gender`, `score`, `level`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6342 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '单项评分标准' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pt_student
-- ----------------------------
DROP TABLE IF EXISTS `pt_student`;
CREATE TABLE `pt_student`  (
  `sid` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `stu_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `stu_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `stu_birth` date NOT NULL COMMENT '出生日期',
  `stu_gender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '性别',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'e10adc3949ba59abbe56e057f20f883e' COMMENT '登陆密码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'https://p1.music.126.net/Y3C5ob6SQjXRijaVNBu4Sw==/109951164400648086.jpg' COMMENT '头像',
  `cls_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `stu_created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `stu_modified` timestamp(0) NULL DEFAULT NULL COMMENT '上次修改时间',
  `stu_last_login` timestamp(0) NULL DEFAULT NULL COMMENT '上次登录',
  `stu_desp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '暂无信息',
  PRIMARY KEY (`stu_id`) USING BTREE,
  UNIQUE INDEX `pt_student_id_uindex`(`sid`) USING BTREE,
  INDEX `cls_code`(`cls_code`) USING BTREE,
  CONSTRAINT `pt_student_ibfk_2` FOREIGN KEY (`cls_code`) REFERENCES `pt_class` (`cls_code`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 148636 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '学生表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pt_sub_student
-- ----------------------------
DROP TABLE IF EXISTS `pt_sub_student`;
CREATE TABLE `pt_sub_student`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `sub_id` bigint(0) UNSIGNED NOT NULL,
  `grade` int(0) NOT NULL,
  `gender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `subs_created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`sub_id`, `grade`, `gender`) USING BTREE,
  UNIQUE INDEX `pt_sub_student_id_uindex`(`id`) USING BTREE,
  CONSTRAINT `pt_sub_student_ibfk_1` FOREIGN KEY (`sub_id`) REFERENCES `pt_subject` (`sub_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 860 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '年级、性别对应的测试科目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pt_subgroup
-- ----------------------------
DROP TABLE IF EXISTS `pt_subgroup`;
CREATE TABLE `pt_subgroup`  (
  `grp_id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `grp_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `grp_desp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '无',
  `grp_created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `grp_created_admin` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `grp_modified` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`grp_id`) USING BTREE,
  UNIQUE INDEX `pt_subject_group_grp_name_uindex`(`grp_name`) USING BTREE,
  INDEX `grp_created_admin`(`grp_created_admin`) USING BTREE,
  CONSTRAINT `pt_subgroup_ibfk_1` FOREIGN KEY (`grp_created_admin`) REFERENCES pt_teacher (tea_id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '科目组' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pt_subject
-- ----------------------------
DROP TABLE IF EXISTS `pt_subject`;
CREATE TABLE `pt_subject`  (
  `sub_id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `sub_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '科目名称',
  `sub_desp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '无' COMMENT '备注信息',
  `sub_created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `sub_modified` timestamp(0) NULL DEFAULT NULL,
  `comp_id` bigint(0) UNSIGNED NULL DEFAULT NULL COMMENT '运动能力id',
  PRIMARY KEY (`sub_id`) USING BTREE,
  UNIQUE INDEX `pt_subject_sub_name_uindex`(`sub_name`) USING BTREE,
  INDEX `comp_id`(`comp_id`) USING BTREE,
  CONSTRAINT `pt_subject_ibfk_1` FOREIGN KEY (`comp_id`) REFERENCES `pt_competency` (`comp_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '科目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pt_subject_subgroup
-- ----------------------------
DROP TABLE IF EXISTS `pt_subject_subgroup`;
CREATE TABLE `pt_subject_subgroup`  (
  `sub_grp_id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `sub_id` bigint(0) UNSIGNED NOT NULL,
  `grp_id` bigint(0) UNSIGNED NULL DEFAULT NULL,
  `sub_grp_modified` timestamp(0) NULL DEFAULT NULL,
  `sub_grp_created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `sub_grp_admin` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '操作人id',
  PRIMARY KEY (`sub_grp_id`) USING BTREE,
  INDEX `grp_id`(`grp_id`) USING BTREE,
  INDEX `sub_id`(`sub_id`) USING BTREE,
  INDEX `sub_grp_admin`(`sub_grp_admin`) USING BTREE,
  CONSTRAINT `pt_subject_subgroup_ibfk_1` FOREIGN KEY (`grp_id`) REFERENCES `pt_subgroup` (`grp_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `pt_subject_subgroup_ibfk_2` FOREIGN KEY (`sub_id`) REFERENCES `pt_subject` (`sub_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `pt_subject_subgroup_ibfk_3` FOREIGN KEY (`sub_grp_admin`) REFERENCES pt_teacher (tea_id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '科目与科目组关联' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
