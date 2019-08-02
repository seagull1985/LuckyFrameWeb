/*
 * LuckyFrame 自动化测试平台 SQL脚本从V3.0升级至V3.1
 * Version   3.1
 * Author    Seagull
 * Date      2019-07-10
 
 ************  WARNING  ************   
 执行此脚本升级
*/
-- ----------------------------
-- 增加质量管理-生产事故登记数据表
-- ----------------------------
create table qa_accident
(
  accident_id           int(8)         not null AUTO_INCREMENT  comment '事故ID',
  project_id            int(8)         not null                 comment '项目ID',
  accident_status       varchar(80)    not null                 comment '事故状态',
  accident_time         varchar(64)                             comment '事故发生时间',
  report_time           varchar(64)    not null                 comment '事故报告时间',
  accident_description  varchar(500)   not null                 comment '事故描述',
  accident_level        varchar(60)    not null                 comment '事故等级',
  accident_analysis     varchar(500)                            comment '事故分析',
  accident_type         varchar(100)                            comment '事故类型',
  accident_consequence  varchar(300)                            comment '事故影响后果',
  corrective_action     varchar(300)                            comment '纠正措施',
  resolution_time       varchar(64)                             comment '解决时间',
  resolutioner          varchar(20)                             comment '解决处理人',
  preventive_action     varchar(300)                            comment '预防措施',
  preventiver           varchar(20)                             comment '预防措施责任人',
  preventive_plan_date  varchar(64)                             comment '预防措施计划完成时间',
  preventive_over_date  varchar(64)                             comment '预防措施实际完成时间',
  duration_time         int(8)                                  comment '事故持续时间',
  impact_time           int(8)                                  comment '事故影响时间',
  accident_file_name    varchar(100)                            comment '事故报告附件路径',
  create_by             varchar(64)    default ''               comment '创建者',
  create_time 	        datetime                                comment '创建时间',
  update_by             varchar(64)    default ''               comment '更新者',
  update_time           datetime                                comment '更新时间',
  primary key (accident_id)
) engine=innodb default charset=utf8 comment = '生产事故登记';

-- ----------------------------
-- 增加质量管理菜单以及修改其他菜单显示顺序以及增加菜单按钮权限数据
-- ----------------------------
-- 增加质量管理一级菜单
insert into sys_menu 
values('6', '质量管理', '0', '3', '#', 'M', '0', '', 'fa fa-bug',         'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '质量过程管理');
update sys_menu set order_num=4 where menu_id=1;
update sys_menu set order_num=5 where menu_id=2;
update sys_menu set order_num=6 where menu_id=3;

-- 生产事故二级菜单
insert into sys_menu values('126',  '生产事故', '6', '1', '/qualitymanagmt/qaAccident', 'C', '0', 'qualitymanagmt:qaAccident:view', '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故菜单');

-- 生产事故模块按钮
insert into sys_menu 
values('1097','生产事故登记查询', '126', '1',  '#',  'F', '0', 'qualitymanagmt:qaAccident:list','#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu  
values('1098','生产事故登记新增', '126', '2',  '#',  'F', '0', 'qualitymanagmt:qaAccident:add','#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu  
values('1099','生产事故登记修改', '126', '3',  '#',  'F', '0', 'qualitymanagmt:qaAccident:edit','#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu  
values('1100','生产事故登记删除', '126', '4',  '#',  'F', '0', 'qualitymanagmt:qaAccident:remove','#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');

-- ----------------------------
-- 增加生产事故状态字典
-- ----------------------------
insert into sys_dict_data values(49, 1,  '已发生-初始状态', '0',  'qa_accident_status',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故状态');
insert into sys_dict_data values(50, 2,  '已发生-跟踪中-未处理', '1',  'qa_accident_status',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故状态');
insert into sys_dict_data values(51, 3,  '已发生-跟踪中-处理中', '2',  'qa_accident_status',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故状态');
insert into sys_dict_data values(52, 4,  '跟踪处理完成', '3',  'qa_accident_status',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故状态');

-- ----------------------------
-- 增加生产事故等级字典
-- ----------------------------
insert into sys_dict_data values(53, 1,  '一级事故', '1',  'qa_accident_level',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故等级');
insert into sys_dict_data values(54, 2,  '二级事故', '2',  'qa_accident_level',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故等级');
insert into sys_dict_data values(55, 3,  '三级事故', '3',  'qa_accident_level',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故等级');
insert into sys_dict_data values(56, 4,  '四级事故', '4',  'qa_accident_level',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故等级');
insert into sys_dict_data values(57, 5,  '五级及以下事故', '5',  'qa_accident_level',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故等级');

-- ----------------------------
-- 增加生产事故类型字典
-- ----------------------------
insert into sys_dict_data values(58, 1,  '测试人员漏测', '1',  'qa_accident_type',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故类型');
insert into sys_dict_data values(59, 2,  '紧急上线-未测试', '2',  'qa_accident_type',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故类型');
insert into sys_dict_data values(60, 3,  '紧急上线-漏测', '3',  'qa_accident_type',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故类型');
insert into sys_dict_data values(61, 4,  '开发私自上线-未测试', '4',  'qa_accident_type',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故类型');
insert into sys_dict_data values(62, 5,  '风险分析不足', '5',  'qa_accident_type',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故类型');
insert into sys_dict_data values(63, 6,  '项目文档不全', '6',  'qa_accident_type',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故类型');
insert into sys_dict_data values(64, 7,  '生产数据处理', '7',  'qa_accident_type',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故类型');
insert into sys_dict_data values(65, 8,  '需求或设计不合理', '8',  'qa_accident_type',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故类型');
insert into sys_dict_data values(66, 9,  '无法测试', '9',  'qa_accident_type',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故类型');
insert into sys_dict_data values(67, 10,  '系统配置异常', '10',  'qa_accident_type',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故类型');
insert into sys_dict_data values(68, 11,  '数据库异常', '11',  'qa_accident_type',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故类型');
insert into sys_dict_data values(69, 12,  '网络异常', '12',  'qa_accident_type',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故类型');
insert into sys_dict_data values(70, 13,  '服务器硬件异常', '13',  'qa_accident_type',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故类型');
insert into sys_dict_data values(71, 14,  '外部原因异常', '14',  'qa_accident_type',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故类型');
insert into sys_dict_data values(72, 15,  '未知原因异常', '15',  'qa_accident_type',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生产事故类型');