-- 任务调度表增加一列环境
alter table task_scheduling add env_name VARCHAR(255) COMMENT '测试环境';
-- 公共参数表增加一列环境
alter table project_case_params add env_name VARCHAR(255) COMMENT '测试环境';

-- 新建聚合计划表
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
-- 新建计划关系表
CREATE TABLE `project_suite_plan` (
`suite_plan_id` int NOT NULL AUTO_INCREMENT COMMENT '聚合计划ID',
`suite_id` int NOT NULL COMMENT '聚合ID',
`plan_id` int NOT NULL COMMENT '测试计划ID',
`priority` int NOT NULL,
PRIMARY KEY (`suite_plan_id`),
KEY `suite_id` (`suite_id`) USING BTREE,
KEY `plan_id` (`plan_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8 COMMENT='聚合计划';

-- 插入菜单
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('2000', '聚合计划', '4', '5', '/testmanagmt/projectSuite', 'C', '0', 'testmanagmt:projectSuite:view', '#', 'admin', '2021-01-14 03:43:57', '', NULL, '');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('2001', '聚合计划新增', '2000', '1', '#', 'F', '0', 'testmanagmt:projectSuite:add', '#', 'admin', '2021-01-14 03:44:23', '', NULL, '');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('2002', '聚合计划编辑', '2000', '2', '#', 'F', '0', 'testmanagmt:projectSuite:edit', '#', 'admin', '2021-01-14 03:44:39', '', NULL, '');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('2003', '聚合计划删除', '2000', '3', '#', 'F', '0', 'testmanagmt:projectSuite:remove', '#', 'admin', '2021-01-14 03:44:56', '', NULL, '');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('2004', '聚合计划查询', '2000', '4', '#', 'F', '0', 'testmanagmt:projectSuite:list', '#', 'admin', '2021-01-14 03:45:11', '', NULL, '');

-- 调度任务表添加两列
alter table task_scheduling add suite_id int COMMENT '聚合计划ID';
alter table task_scheduling add plan_type int DEFAULT 1 COMMENT '计划类型 1 单个计划 2聚合计划';
update task_scheduling set plan_type=1 where plan_type=0;
-- 任务执行表添加一列
alter table task_case_execute add plan_id int DEFAULT NULL COMMENT "计划ID";
-- 修改plan_id默认为null
alter table task_scheduling MODIFY plan_id int(11) DEFAULT NULL;