/*
 * LuckyFrame 自动化测试平台 SQL脚本初始化
 * Version   3.5.1
 * Author    YSS陈再兴
 * Date      2022-05-26
 
 ************  WARNING  ************   
 此脚本属于应用自动升级数据库表结构以及数据脚本，无需手动执行，请慎重！！！！
*/
-- 1、PO模式功能 页面管理
CREATE TABLE  if  not exists  `project_page_object`  (
                                                         `project_id` int NULL DEFAULT NULL COMMENT '关联项目ID',
                                                         `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
                                                         `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                                         `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
                                                         `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                                         `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
                                                         `page_id` int NOT NULL AUTO_INCREMENT COMMENT '页面唯一编号',
                                                         `page_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '页面名称',
                                                         `page_parentmenu` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '页面父菜单',
                                                         `page_menu` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '页面菜单',
                                                         `project_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                         PRIMARY KEY (`page_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '页面配置管理' ROW_FORMAT = DYNAMIC;

-- 2、PO模式功能 页面元素管理
CREATE TABLE if  not exists  `project_page_detail`  (
                                                        `id` int NOT NULL AUTO_INCREMENT COMMENT '系统主键',
                                                        `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
                                                        `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                                        `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
                                                        `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                                        `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
                                                        `page_id` int NOT NULL COMMENT '页面唯一编号',
                                                        `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '定位类型，支持Byxpath等等',
                                                        `element` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '元素名称',
                                                        `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '具体的参数值',
                                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '页面详情' ROW_FORMAT = DYNAMIC;

INSERT INTO sys_menu
VALUES (1115, 'Web页面元素管理', 4, 7, '#', 'M', '0', '', '#', 'admin', '2022-05-27 17:48:28', 'dyl', '2022-05-27 17:48:28', '页面配置管理菜单');
INSERT INTO sys_menu
VALUES (1116, '页面管理', 1115, 1, '/testmanagmt/projectPageObject', 'C', '0', 'testmanagmt:projectPageObject:view', '#', 'admin', '2022-05-27 17:48:28', 'dyl', '2022-05-27 17:48:28', '页面配置管理菜单');
INSERT INTO sys_menu
VALUES (1117, '页面配置管理查询', 1116, 1, '#', 'F', '0', 'testmanagmt:projectPageObject:list', '#', 'admin', '2022-05-27 17:48:28', 'admin', '2022-05-27 17:48:28', '');
INSERT INTO sys_menu
VALUES (1118, '页面配置管理新增', 1116, 2, '#', 'F', '0', 'testmanagmt:projectPageObject:add', '#', 'admin', '2022-05-27 17:48:28', 'admin', '2022-05-27 17:48:28', '');
INSERT INTO sys_menu
VALUES (1119, '页面配置管理修改', 1116, 3, '#', 'F', '0', 'testmanagmt:projectPageObject:edit', '#', 'admin', '2022-05-27 17:48:28', 'admin', '2022-05-27 17:48:28', '');
INSERT INTO sys_menu
VALUES (1120, '页面配置管理删除', 1116, 4, '#', 'F', '0', 'testmanagmt:projectPageObject:remove', '#', 'admin', '2022-05-27 17:48:28', 'admin', '2022-05-27 17:48:28', '');
INSERT INTO sys_menu
VALUES (1121, '页面详情', 1115, 2, '/testmanagmt/projectPageDetail', 'C', '1', 'testmanagmt:projectPageDetail:view', '#', 'admin', '2022-05-27 17:48:28', 'admin', '2022-05-27 17:48:28', '页面详情菜单');
INSERT INTO sys_menu
VALUES (1122, '页面详情查询', 1121, 1, '#', 'F', '0', 'testmanagmt:projectPageDetail:list', '#', 'admin', '2022-05-27 17:48:28', 'admin', '2022-05-27 17:48:28', '');
INSERT INTO sys_menu
VALUES (1123, '页面详情新增', 1121, 2, '#', 'F', '0', 'testmanagmt:projectPageDetail:add', '#', 'admin', '2022-05-27 17:48:28', 'admin', '2022-05-27 17:48:28', '');
INSERT INTO sys_menu
VALUES (1124, '页面详情修改', 1121, 3, '#', 'F', '0', 'testmanagmt:projectPageDetail:edit', '#', 'admin', '2022-05-27 17:48:28', 'admin', '2022-05-27 17:48:28', '');
INSERT INTO sys_menu
VALUES (1125, '页面详情删除', 1121, 4, '#', 'F', '0', 'testmanagmt:projectPageDetail:remove', '#', 'admin', '2022-05-27 17:48:28', 'admin', '2022-05-27 17:48:28', '');