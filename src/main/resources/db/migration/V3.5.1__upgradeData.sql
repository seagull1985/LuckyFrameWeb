/*
 * LuckyFrame 自动化测试平台 SQL脚本初始化
 * Version   3.5.1
 * Author    YSS陈再兴
 * Date      2022-05-26
 
 ************  WARNING  ************   
 此脚本属于应用自动升级数据库表结构以及数据脚本，无需手动执行，请慎重！！！！
*/
-- ----------------------------
-- 页面配置
-- ----------------------------
CREATE TABLE if  not exists  `project_page_object`  (
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
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '页面配置管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- 页面元素关联表
-- ----------------------------
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

INSERT INTO sys_menu (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2001, '页面管理', 4, 3, '/testmanagmt/projectPageObject', 'C', '0', 'testmanagmt:projectPageObject:view', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2022-03-31 17:48:28', '页面配置管理菜单');
INSERT INTO sys_menu (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2002, '页面配置管理查询', 2001, 1, '#', 'F', '0', 'testmanagmt:projectPageObject:list', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2022-04-01 11:28:36', '');
INSERT INTO sys_menu (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2003, '页面配置管理新增', 2001, 2, '#', 'F', '0', 'testmanagmt:projectPageObject:add', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2022-04-01 11:28:52', '');
INSERT INTO sys_menu (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2004, '页面配置管理修改', 2001, 3, '#', 'F', '0', 'testmanagmt:projectPageObject:edit', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2022-04-01 11:29:00', '');
INSERT INTO sys_menu (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2005, '页面配置管理删除', 2001, 4, '#', 'F', '0', 'testmanagmt:projectPageObject:remove', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2022-04-01 11:29:08', '');
INSERT INTO sys_menu (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2006, '页面详情', 4, 1, '/testmanagmt/projectPageDetail', 'C', '1', 'testmanagmt:projectPageDetail:view', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2022-03-10 17:32:07', '页面详情菜单');
INSERT INTO sys_menu (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2007, '页面详情查询', 2006, 1, '#', 'F', '0', 'testmanagmt:projectPageDetail:list', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2022-04-01 11:28:24', '');
INSERT INTO sys_menu (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2008, '页面详情新增', 2006, 2, '#', 'F', '0', 'testmanagmt:projectPageDetail:add', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2022-04-01 11:29:47', '');
INSERT INTO sys_menu (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2009, '页面详情修改', 2006, 3, '#', 'F', '0', 'testmanagmt:projectPageDetail:edit', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2022-04-01 11:29:53', '');
INSERT INTO sys_menu (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2010, '页面详情删除', 2006, 4, '#', 'F', '0', 'testmanagmt:projectPageDetail:remove', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2022-04-01 11:30:00', '');
