/*
 * LuckyFrame 自动化测试平台 SQL脚本初始化
 * Version   3.3
 * Author    seagull
 * Date      2020-07-03
 
 ************  WARNING  ************   
 此脚本属于应用自动升级数据库表结构以及数据脚本，无需手动执行，请慎重！！！！
*/
-- ----------------------------
-- 1、增加客户端配置表
-- ----------------------------
create table if not exists sys_client_config (
  id           int(11)      not null AUTO_INCREMENT comment '主键',
  client_id    int(8)       not null                comment '客户端id',
  config_key   varchar(100) not null                comment '客户端配置名称',
  config_value varchar(400) not null                comment '客户端配置值',
  primary key (id)
) engine=innodb AUTO_INCREMENT=6 default charset=utf8 comment='客户端配置表';

-- ----------------------------
-- 2、增加客户端配置菜单&修改菜单排序
-- ----------------------------
update sys_menu t set t.order_num=8 where t.menu_id=106;
insert ignore into sys_menu values('128',  '客户端配置', '1', '7', '/system/clientConfig',    'C', '0', 'system:clientConfig:view','#', 'admin', '2020-07-07 19:15:32', 'luckyframe', '2020-07-07 19:15:32', '客户端配置菜单');

-- ----------------------------
-- 3、增加客户端配置页面按钮权限
-- ----------------------------
insert ignore into `luckyframe`.`sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) values ('1105', '客户端配置查询', '128', '1', '#', 'F', '0', 'system:clientConfig:list', '#', 'admin', '2020-07-07 19:15:32', 'luckyframe', '2020-07-07 19:15:32', '');
insert ignore into `luckyframe`.`sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) values ('1106', '客户端配置新增', '128', '2', '#', 'F', '0', 'system:clientConfig:add', '#', 'admin', '2020-07-07 19:15:32', 'luckyframe', '2020-07-07 19:15:32', '');
insert ignore into `luckyframe`.`sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) values ('1107', '客户端配置修改', '128', '3', '#', 'F', '0', 'system:clientConfig:edit', '#', 'admin', '2020-07-07 19:15:32', 'luckyframe', '2020-07-07 19:15:32', '');
insert ignore into `luckyframe`.`sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) values ('1108', '客户端配置删除', '128', '4', '#', 'F', '0', 'system:clientConfig:remove', '#', 'admin', '2020-07-07 19:15:32', 'luckyframe', '2020-07-07 19:15:32', '');

-- ----------------------------
-- 4、调度任务中增加第三方推送指定URL 例如：钉钉、企业微信
-- ----------------------------
alter table task_scheduling add push_url varchar(200) comment '第三方推送地址URL';