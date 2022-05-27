/*
 * LuckyFrame 自动化测试平台 SQL脚本初始化
 * Version   3.4.1
 * Author    seagull
 * Date      2021-06-30
 
 ************  WARNING  ************   
 此脚本属于应用自动升级数据库表结构以及数据脚本，无需手动执行，请慎重！！！！
*/
-- ----------------------------
-- 1、数据字典表增加Web UI内置关键字 getvalue，switchtowindow，windowsetsize
-- ----------------------------
INSERT INTO sys_dict_data
VALUES(2048, 49,  'GetValue获取指定对象值', 'getvalue',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2021-06-30 14-27-32', 'luckyframe', '2021-06-30 14-27-32', '获取指定对象值');
INSERT INTO sys_dict_data
(dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark)
VALUES(2049, 50, 'Switchtowindow切换句柄并关闭之前窗口', 'switchtowindow', 'testmanagmt_casestep_uioperation', '', 'info', 'Y', '0', 'admin', '2021-12-07 20:24:26', 'admin', '2021-12-07 20:25:39', '切换句柄并关闭之前窗口');
INSERT INTO sys_dict_data
(dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark)
VALUES(2050, 51, 'Windowsetsize设置浏览器窗口大小', 'windowsetsize', 'testmanagmt_casestep_uioperation', '', 'info', 'Y', '0', 'admin', '2021-12-30 15:26:27', 'admin', '2021-12-30 15:26:43', '设置浏览器窗口大小');
-- ----------------------------
-- 2、任务调度表增加一列环境
-- ----------------------------
ALTER TABLE task_scheduling ADD env_name VARCHAR(255) COMMENT '测试环境';
-- ----------------------------
-- 3、公共参数表增加一列环境
-- ----------------------------
ALTER TABLE project_case_params ADD env_name VARCHAR(255) COMMENT '测试环境';
-- ----------------------------
-- 11、在公共参数表以及任务调度表中增加环境变量字段后，处理存量数据。
-- ----------------------------
update project_case_params t set t.env_name=(select a.project_name from sys_project a where t.project_id=a.project_id);

update task_scheduling t set t.env_name=(select a.project_name from sys_project a where t.project_id=a.project_id);
-- ----------------------------
-- 4、新建聚合计划表
-- ----------------------------
CREATE TABLE `project_suite` (
                                 `suite_id` int NOT NULL AUTO_INCREMENT COMMENT '聚合计划ID',
                                 `suite_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '聚合计划名称',
                                 `suite_plan_count` int DEFAULT NULL COMMENT '聚合计划中的计划总数',
                                 `project_id` int NOT NULL COMMENT '项目ID',
                                 `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '创建者',
                                 `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                 `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '更新者',
                                 `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                 `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
                                 PRIMARY KEY (`suite_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COMMENT='聚合计划';
-- ----------------------------
-- 5、新建计划关系表
-- ----------------------------
CREATE TABLE `project_suite_plan` (
                                      `suite_plan_id` int NOT NULL AUTO_INCREMENT COMMENT '聚合计划ID',
                                      `suite_id` int NOT NULL COMMENT '聚合ID',
                                      `plan_id` int NOT NULL COMMENT '测试计划ID',
                                      `priority` int NOT NULL,
                                      PRIMARY KEY (`suite_plan_id`),
                                      KEY `suite_id` (`suite_id`) USING BTREE,
                                      KEY `plan_id` (`plan_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8 COMMENT='聚合计划';
-- ----------------------------
-- 6、调度任务表添加两列
-- ----------------------------
ALTER TABLE task_scheduling ADD suite_id int COMMENT '聚合计划ID';
ALTER TABLE task_scheduling ADD plan_type int DEFAULT 1 COMMENT '计划类型 1 单个计划 2聚合计划';
UPDATE task_scheduling SET plan_type=1 where plan_type=0;
-- ----------------------------
-- 7、任务执行表添加一列
-- ----------------------------
ALTER TABLE task_case_execute ADD plan_id int DEFAULT NULL COMMENT "计划ID";
-- ----------------------------
-- 8、修改plan_id默认为null
-- ----------------------------
ALTER TABLE task_scheduling MODIFY plan_id int(11) DEFAULT NULL;
-- ----------------------------
-- 9、增加用例导出导入权限控制菜单
-- ----------------------------
INSERT INTO sys_menu
(menu_id, menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES('1109', '用例步骤导入', '118', '6', '#', 'F', '0', 'testmanagmt:projectCase:import', '#', 'admin', '2021-12-13 17:36:51', 'admin', '2021-12-13 17:39:59', '');
-- ----------------------------
-- 10、插入菜单
-- ----------------------------
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('1110', '聚合计划', '4', '6', '/testmanagmt/projectSuite', 'C', '0', 'testmanagmt:projectSuite:view', '#', 'admin', '2021-01-14 03:43:57', '', NULL, '');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('1111', '聚合计划新增', '1110', '1', '#', 'F', '0', 'testmanagmt:projectSuite:add', '#', 'admin', '2021-01-14 03:44:23', '', NULL, '');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('1112', '聚合计划编辑', '1110', '2', '#', 'F', '0', 'testmanagmt:projectSuite:edit', '#', 'admin', '2021-01-14 03:44:39', '', NULL, '');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('1113', '聚合计划删除', '1110', '3', '#', 'F', '0', 'testmanagmt:projectSuite:remove', '#', 'admin', '2021-01-14 03:44:56', '', NULL, '');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES ('1114', '聚合计划查询', '1110', '4', '#', 'F', '0', 'testmanagmt:projectSuite:list', '#', 'admin', '2021-01-14 03:45:11', '', NULL, '');