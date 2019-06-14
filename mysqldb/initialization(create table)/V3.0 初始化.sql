/*
 * LuckyFrame 自动化测试平台 SQL脚本初始化
 * Version   3.0
 * Author    seagull
 * Date      2019-02-20
 
 ************  WARNING  ************   
 执行此脚本，会导致数据库所有数据初始化，初次执行，请慎重！！！！
*/

DROP DATABASE IF EXISTS luckyframe;
CREATE DATABASE IF NOT EXISTS luckyframe DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

USE luckyframe;

-- ----------------------------
-- 1、存储每一个已配置的 jobDetail 的详细信息
-- ----------------------------
drop table if exists QRTZ_JOB_DETAILS;
create table QRTZ_JOB_DETAILS (
    sched_name           varchar(120)    not null,
    job_name             varchar(200)    not null,
    job_group            varchar(200)    not null,
    description          varchar(250)    null,
    job_class_name       varchar(250)    not null,
    is_durable           varchar(1)      not null,
    is_nonconcurrent     varchar(1)      not null,
    is_update_data       varchar(1)      not null,
    requests_recovery    varchar(1)      not null,
    job_data             blob            null,
    primary key (sched_name,job_name,job_group)
) engine=innodb default charset=utf8;

-- ----------------------------
-- 2、 存储已配置的 Trigger 的信息
-- ----------------------------
drop table if exists QRTZ_TRIGGERS;
create table QRTZ_TRIGGERS (
    sched_name           varchar(120)    not null,
    trigger_name         varchar(200)    not null,
    trigger_group        varchar(200)    not null,
    job_name             varchar(200)    not null,
    job_group            varchar(200)    not null,
    description          varchar(250)    null,
    next_fire_time       bigint(13)      null,
    prev_fire_time       bigint(13)      null,
    priority             integer         null,
    trigger_state        varchar(16)     not null,
    trigger_type         varchar(8)      not null,
    start_time           bigint(13)      not null,
    end_time             bigint(13)      null,
    calendar_name        varchar(200)    null,
    misfire_instr        smallint(2)     null,
    job_data             blob            null,
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,job_name,job_group) references QRTZ_JOB_DETAILS(sched_name,job_name,job_group)
) engine=innodb default charset=utf8;

-- ----------------------------
-- 3、 存储简单的 Trigger，包括重复次数，间隔，以及已触发的次数
-- ----------------------------
drop table if exists QRTZ_SIMPLE_TRIGGERS;
create table QRTZ_SIMPLE_TRIGGERS (
    sched_name           varchar(120)    not null,
    trigger_name         varchar(200)    not null,
    trigger_group        varchar(200)    not null,
    repeat_count         bigint(7)       not null,
    repeat_interval      bigint(12)      not null,
    times_triggered      bigint(10)      not null,
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,trigger_name,trigger_group) references QRTZ_TRIGGERS(sched_name,trigger_name,trigger_group)
) engine=innodb default charset=utf8;

-- ----------------------------
-- 4、 存储 Cron Trigger，包括 Cron 表达式和时区信息
-- ---------------------------- 
drop table if exists QRTZ_CRON_TRIGGERS;
create table QRTZ_CRON_TRIGGERS (
    sched_name           varchar(120)    not null,
    trigger_name         varchar(200)    not null,
    trigger_group        varchar(200)    not null,
    cron_expression      varchar(200)    not null,
    time_zone_id         varchar(80),
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,trigger_name,trigger_group) references QRTZ_TRIGGERS(sched_name,trigger_name,trigger_group)
) engine=innodb default charset=utf8;

-- ----------------------------
-- 5、 Trigger 作为 Blob 类型存储(用于 Quartz 用户用 JDBC 创建他们自己定制的 Trigger 类型，JobStore 并不知道如何存储实例的时候)
-- ---------------------------- 
drop table if exists QRTZ_BLOB_TRIGGERS;
create table QRTZ_BLOB_TRIGGERS (
    sched_name           varchar(120)    not null,
    trigger_name         varchar(200)    not null,
    trigger_group        varchar(200)    not null,
    blob_data            blob            null,
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,trigger_name,trigger_group) references QRTZ_TRIGGERS(sched_name,trigger_name,trigger_group)
) engine=innodb default charset=utf8;

-- ----------------------------
-- 6、 以 Blob 类型存储存放日历信息， quartz可配置一个日历来指定一个时间范围
-- ---------------------------- 
drop table if exists QRTZ_CALENDARS;
create table QRTZ_CALENDARS (
    sched_name           varchar(120)    not null,
    calendar_name        varchar(200)    not null,
    calendar             blob            not null,
    primary key (sched_name,calendar_name)
) engine=innodb default charset=utf8;

-- ----------------------------
-- 7、 存储已暂停的 Trigger 组的信息
-- ---------------------------- 
drop table if exists QRTZ_PAUSED_TRIGGER_GRPS;
create table QRTZ_PAUSED_TRIGGER_GRPS (
    sched_name           varchar(120)    not null,
    trigger_group        varchar(200)    not null,
    primary key (sched_name,trigger_group)
) engine=innodb default charset=utf8;

-- ----------------------------
-- 8、 存储与已触发的 Trigger 相关的状态信息，以及相联 Job 的执行信息
-- ---------------------------- 
drop table if exists QRTZ_FIRED_TRIGGERS;
create table QRTZ_FIRED_TRIGGERS (
    sched_name           varchar(120)    not null,
    entry_id             varchar(95)     not null,
    trigger_name         varchar(200)    not null,
    trigger_group        varchar(200)    not null,
    instance_name        varchar(200)    not null,
    fired_time           bigint(13)      not null,
    sched_time           bigint(13)      not null,
    priority             integer         not null,
    state                varchar(16)     not null,
    job_name             varchar(200)    null,
    job_group            varchar(200)    null,
    is_nonconcurrent     varchar(1)      null,
    requests_recovery    varchar(1)      null,
    primary key (sched_name,entry_id)
) engine=innodb default charset=utf8;

-- ----------------------------
-- 9、 存储少量的有关 Scheduler 的状态信息，假如是用于集群中，可以看到其他的 Scheduler 实例
-- ---------------------------- 
drop table if exists QRTZ_SCHEDULER_STATE; 
create table QRTZ_SCHEDULER_STATE (
    sched_name           varchar(120)    not null,
    instance_name        varchar(200)    not null,
    last_checkin_time    bigint(13)      not null,
    checkin_interval     bigint(13)      not null,
    primary key (sched_name,instance_name)
) engine=innodb default charset=utf8;

-- ----------------------------
-- 10、 存储程序的悲观锁的信息(假如使用了悲观锁)
-- ---------------------------- 
drop table if exists QRTZ_LOCKS;
create table QRTZ_LOCKS (
    sched_name           varchar(120)    not null,
    lock_name            varchar(40)     not null,
    primary key (sched_name,lock_name)
) engine=innodb default charset=utf8;

drop table if exists QRTZ_SIMPROP_TRIGGERS;
create table QRTZ_SIMPROP_TRIGGERS (
    sched_name           varchar(120)    not null,
    trigger_name         varchar(200)    not null,
    trigger_group        varchar(200)    not null,
    str_prop_1           varchar(512)    null,
    str_prop_2           varchar(512)    null,
    str_prop_3           varchar(512)    null,
    int_prop_1           int             null,
    int_prop_2           int             null,
    long_prop_1          bigint          null,
    long_prop_2          bigint          null,
    dec_prop_1           numeric(13,4)   null,
    dec_prop_2           numeric(13,4)   null,
    bool_prop_1          varchar(1)      null,
    bool_prop_2          varchar(1)      null,
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,trigger_name,trigger_group) references QRTZ_TRIGGERS(sched_name,trigger_name,trigger_group)
) engine=innodb default charset=utf8;
-- ----------------------------
-- 11、部门表
-- ----------------------------
drop table if exists sys_dept;
create table sys_dept (
  dept_id 			int(11) 		not null auto_increment    comment '部门id',
  parent_id 		int(11) 		default 0 			       comment '父部门id',
  ancestors 		varchar(50)     default '' 			       comment '祖级列表',
  dept_name 		varchar(30) 	default '' 				   comment '部门名称',
  order_num 		int(4) 			default 0 			       comment '显示顺序',
  leader            varchar(20)     default null               comment '负责人',
  phone             varchar(11)     default null               comment '联系电话',
  email             varchar(50)     default null               comment '邮箱',
  status 			char(1) 		default '0' 			   comment '部门状态（0正常 1停用）',
  del_flag			char(1) 		default '0' 			   comment '删除标志（0代表存在 2代表删除）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (dept_id)
) engine=innodb auto_increment=200 default charset=utf8 comment = '部门表';

-- ----------------------------
-- 初始化-部门表数据
-- ----------------------------
insert into sys_dept 
values(100,  0,   '0',          'LF总公司',   0, 'luckyframe', '15888888888', 'admin@luckyframe.cn', '0', '0', 'admin', '2019-02-13 10-27-32', 'admin', '2019-02-13 10-27-32');
insert into sys_dept 
values(101,  100, '0,100',      '深圳分公司', 1, 'luckyframe', '15888888888', 'admin@luckyframe.cn', '0', '0', 'admin', '2019-02-13 10-27-32', 'admin', '2019-02-13 10-27-32');
insert into sys_dept 
values(102,  100, '0,100',      '长沙分公司', 2, 'luckyframe', '15888888888', 'admin@luckyframe.cn', '0', '0', 'admin', '2019-02-13 10-27-32', 'admin', '2019-02-13 10-27-32');
insert into sys_dept 
values(103,  101, '0,100,101',  '研发部门',   1, 'luckyframe', '15888888888', 'admin@luckyframe.cn', '0', '0', 'admin', '2019-02-13 10-27-32', 'admin', '2019-02-13 10-27-32');
insert into sys_dept 
values(104,  101, '0,100,101',  '测试部门',   3, 'luckyframe', '15888888888', 'admin@luckyframe.cn', '0', '0', 'admin', '2019-02-13 10-27-32', 'admin', '2019-02-13 10-27-32');
insert into sys_dept 
values(105,  101, '0,100,101',  '运维部门',   5, 'luckyframe', '15888888888', 'admin@luckyframe.cn', '0', '0', 'admin', '2019-02-13 10-27-32', 'admin', '2019-02-13 10-27-32');
insert into sys_dept 
values(106,  102, '0,100,102',  '测试部门',   1, 'luckyframe', '15888888888', 'admin@luckyframe.cn', '0', '0', 'admin', '2019-02-13 10-27-32', 'admin', '2019-02-13 10-27-32');
insert into sys_dept 
values(107,  102, '0,100,102',  '研发部门',   2, 'luckyframe', '15888888888', 'admin@luckyframe.cn', '0', '0', 'admin', '2019-02-13 10-27-32', 'admin', '2019-02-13 10-27-32');

-- ----------------------------
-- 29、测试项目表
-- ----------------------------
drop table if exists sys_project;
create table sys_project (
  project_id 		int(8) 		    not null auto_increment    comment '项目ID',
  project_name 		varchar(50) 	not null 				   comment '项目名称',
  dept_id           int(11)         not null 				   comment '归属部门',
  project_sign      varchar(10)    default 'sign' not null     comment '项目标识',
  primary key (project_id)
) engine=innodb default charset=utf8 comment = '测试项目管理表';

-- ----------------------------
-- 初始化-测试项目表数据
-- ----------------------------
/*插入默认项目*/
insert into sys_project values (1, '测试项目一', 104, 'ITT');

-- ----------------------------
-- 12、用户信息表
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
  user_id 			int(11) 		not null auto_increment    comment '用户ID',
  dept_id 			int(11) 		default null			   comment '部门ID',
  login_name 		varchar(30) 	not null 				   comment '登录账号',
  user_name 		varchar(30) 	not null 				   comment '用户昵称',
  user_type 		varchar(2) 	    default '00' 		       comment '用户类型（00系统用户）',
  email  			varchar(50) 	default '' 				   comment '用户邮箱',
  phonenumber  		varchar(11) 	default '' 				   comment '手机号码',
  sex  		        char(1) 	    default '0' 			   comment '用户性别（0男 1女 2未知）',
  avatar            varchar(100) 	default '' 				   comment '头像路径',
  password 			varchar(50) 	default '' 				   comment '密码',
  salt 				varchar(20) 	default '' 				   comment '盐加密',
  status 			char(1) 		default '0' 			   comment '帐号状态（0正常 1停用）',
  del_flag			char(1) 		default '0' 			   comment '删除标志（0代表存在 2代表删除）',
  project_id        int(8) 		    default null			   comment '默认项目ID',
  login_ip          varchar(50)     default ''                 comment '最后登陆IP',
  login_date        datetime                                   comment '最后登陆时间',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark 		    varchar(500) 	default '' 				   comment '备注',
  primary key (user_id)
) engine=innodb auto_increment=100 default charset=utf8 comment = '用户信息表';

-- ----------------------------
-- 初始化-用户信息表数据
-- ----------------------------
insert into sys_user values(1,  103, 'admin', 'luckyframe', '00', 'admin@luckyframe.cn', '15888888888', '1', '', '5747f5f894ae6ed3082a1b809970e234', 'e30bbe', '0', '0', null, '127.0.0.1', '2019-02-13 10-27-32', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '管理员');


-- ----------------------------
-- 13、岗位信息表
-- ----------------------------
drop table if exists sys_post;
create table sys_post
(
    post_id       int(11)         not null auto_increment    comment '岗位ID',
	post_code     varchar(64)     not null                   comment '岗位编码',
	post_name     varchar(50)     not null                   comment '岗位名称',
	post_sort     int(4)          not null                   comment '显示顺序',
	status        char(1)         not null                   comment '状态（0正常 1停用）',
    create_by     varchar(64)     default ''                 comment '创建者',
    create_time   datetime                                   comment '创建时间',
    update_by     varchar(64) 	  default ''			     comment '更新者',
	update_time   datetime                                   comment '更新时间',
    remark 		  varchar(500) 	  default null 				 comment '备注',
	primary key (post_id)
) engine=innodb default charset=utf8 comment = '岗位信息表';

-- ----------------------------
-- 初始化-岗位信息表数据
-- ----------------------------
insert into sys_post values(1, 'PM',  '项目经理',    1, '0', 'admin', '2019-02-13 10-27-32', 'admin', '2019-02-13 10-27-32', '');
insert into sys_post values(2, 'DE',   '开发工程师',  2, '0', 'admin', '2019-02-13 10-27-32', 'admin', '2019-02-13 10-27-32', '');
insert into sys_post values(3, 'TM',   '测试经理',  3, '0', 'admin', '2019-02-13 10-27-32', 'admin', '2019-02-13 10-27-32', '');
insert into sys_post values(4, 'TE', '测试工程师',  4, '0', 'admin', '2019-02-13 10-27-32', 'admin', '2019-02-13 10-27-32', '');


-- ----------------------------
-- 14、角色信息表
-- ----------------------------
drop table if exists sys_role;
create table sys_role (
  role_id 			int(11) 		not null auto_increment    comment '角色ID',
  role_name 		varchar(30) 	not null 				   comment '角色名称',
  role_key 		    varchar(100) 	not null 				   comment '角色权限字符串',
  role_sort         int(4)          not null                   comment '显示顺序',
  data_scope        char(1) 	    default '1'				   comment '数据范围（1：全部数据权限 2：自定数据权限）',
  status 			char(1) 		not null 			       comment '角色状态（0正常 1停用）',
  del_flag			char(1) 		default '0' 			   comment '删除标志（0代表存在 2代表删除）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 		datetime                                   comment '创建时间',
  update_by 		varchar(64) 	default ''			       comment '更新者',
  update_time 		datetime                                   comment '更新时间',
  remark 			varchar(500) 	default null 			   comment '备注',
  primary key (role_id)
) engine=innodb auto_increment=100 default charset=utf8 comment = '角色信息表';

-- ----------------------------
-- 初始化-角色信息表数据
-- ----------------------------
insert into sys_role values('1', '管理员',   'admin',  1, 1, '0', '0', 'admin', '2019-02-13 10-27-32', 'admin', '2019-02-13 10-27-32', '管理员');
insert into sys_role values('2', '普通角色', 'common', 2, 2, '0', '0', 'admin', '2019-02-13 10-27-32', 'admin', '2019-02-13 10-27-32', '普通角色');


-- ----------------------------
-- 15、菜单权限表
-- ----------------------------
drop table if exists sys_menu;
create table sys_menu (
  menu_id 			int(11) 		not null auto_increment    comment '菜单ID',
  menu_name 		varchar(50) 	not null 				   comment '菜单名称',
  parent_id 		int(11) 		default 0 			       comment '父菜单ID',
  order_num 		int(4) 			default 0 			       comment '显示顺序',
  url 				varchar(200) 	default '#'				   comment '请求地址',
  menu_type 		char(1) 		default '' 			       comment '菜单类型（M目录 C菜单 F按钮）',
  visible 			char(1) 		default 0 				   comment '菜单状态（0显示 1隐藏）',
  perms 			varchar(100) 	default null 			   comment '权限标识',
  icon 				varchar(100) 	default '#' 			   comment '菜单图标',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 		datetime                                   comment '创建时间',
  update_by 		varchar(64) 	default ''			       comment '更新者',
  update_time 		datetime                                   comment '更新时间',
  remark 			varchar(500) 	default '' 				   comment '备注',
  primary key (menu_id)
) engine=innodb auto_increment=2000 default charset=utf8 comment = '菜单权限表';

-- ----------------------------
-- 初始化-菜单信息表数据
-- ----------------------------
-- 一级菜单
insert into sys_menu values('1', '系统管理', '0', '3', '#', 'M', '0', '', 'fa fa-gear',         'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '系统管理目录');
insert into sys_menu values('2', '系统监控', '0', '4', '#', 'M', '0', '', 'fa fa-video-camera', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '系统监控目录');
insert into sys_menu values('3', '系统工具', '0', '5', '#', 'M', '0', '', 'fa fa-bars',         'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '系统工具目录');
insert into sys_menu 
values('4', '测试管理', '0', '1', '#', 'M', '0', '', 'fa fa-th-large',         'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '测试过程管理');
insert into sys_menu 
values('5', '测试执行', '0', '2', '#', 'M', '0', '', 'fa fa-rocket',         'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '测试执行管理');
-- 二级菜单
insert into sys_menu values('100',  '用户管理', '1', '1', '/system/user',        'C', '0', 'system:user:view',         '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '用户管理菜单');
insert into sys_menu values('101',  '角色管理', '1', '2', '/system/role',        'C', '0', 'system:role:view',         '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '角色管理菜单');
insert into sys_menu values('103',  '部门管理', '1', '4', '/system/dept',        'C', '0', 'system:dept:view',         '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '部门管理菜单');
insert into sys_menu values('104',  '项目管理', '1', '5', '/system/project',     'C', '0', 'system:project:view',      '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '项目管理菜单');
insert into sys_menu values('105',  '客户端管理', '1', '6', '/system/client',    'C', '0', 'system:client:view',       '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '客户端管理菜单');
insert into sys_menu values('106',  '岗位管理', '1', '7', '/system/post',        'C', '0', 'system:post:view',         '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '岗位管理菜单');
insert into sys_menu values('108',  '参数设置', '1', '9', '/system/config',      'C', '0', 'system:config:view',       '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '参数设置菜单');
insert into sys_menu values('109',  '通知公告', '1', '10', '/system/notice',      'C', '0', 'system:notice:view',       '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '通知公告菜单');
insert into sys_menu values('110',  '日志管理', '1', '11', '#',                   'M', '0', '',                         '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '日志管理菜单');
insert into sys_menu values('111',  '在线用户', '2', '1', '/monitor/online',     'C', '0', 'monitor:online:view',      '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '在线用户菜单');
insert into sys_menu values('112',  '定时任务', '2', '2', '/monitor/job',        'C', '0', 'monitor:job:view',         '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '定时任务菜单');
insert into sys_menu values('113',  '数据监控', '2', '3', '/monitor/data',       'C', '0', 'monitor:data:view',        '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '数据监控菜单');
insert into sys_menu values('114',  '服务监控', '2', '4', '/monitor/server',     'C', '0', 'monitor:server:view',      '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '服务监控菜单');
insert into sys_menu values('115',  '表单构建', '3', '1', '/tool/build',         'C', '0', 'tool:build:view',          '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '表单构建菜单');
insert into sys_menu values('116',  '代码生成', '3', '2', '/tool/gen',           'C', '0', 'tool:gen:view',            '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '代码生成菜单');
insert into sys_menu values('117',  '系统接口', '3', '3', '/tool/swagger',       'C', '0', 'tool:swagger:view',        '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '系统接口菜单');
insert into sys_menu values('107',  '字典管理', '3', '4', '/system/dict',        'C', '0', 'system:dict:view',         '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '字典管理菜单');
insert into sys_menu values('102',  '菜单管理', '3', '5', '/system/menu',        'C', '0', 'system:menu:view',         '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '菜单管理菜单');
insert into sys_menu values('118',  '用例管理', '4', '1', '/testmanagmt/projectCase', 'C', '0', 'testmanagmt:projectCase:view', '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2018-03-01', '测试用例管理菜单');
insert into sys_menu values('119',  '用例模块', '4', '2', '/testmanagmt/projectCaseModule', 'C', '0', 'testmanagmt:projectCaseModule:view', '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2018-03-01', '测试用例模块管理菜单');
insert into sys_menu values('120',  '协议模板', '4', '3', '/testmanagmt/projectProtocolTemplate', 'C', '0', 'testmanagmt:projectProtocolTemplate:view', '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '协议模板管理菜单');
insert into sys_menu values('121',  '测试计划', '4', '4', '/testmanagmt/projectPlan', 'C', '0', 'testmanagmt:projectPlan:view', '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '测试计划菜单');
insert into sys_menu values('122',  '公共参数', '4', '5', '/testmanagmt/projectCaseParams', 'C', '0', 'testmanagmt:projectCaseParams:view', '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '用例公共参数菜单');
insert into sys_menu values('123',  '任务调度', '5', '1', '/testexecution/taskScheduling', 'C', '0', 'testexecution:taskScheduling:view', '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '测试任务调度菜单');
insert into sys_menu values('124',  '任务执行', '5', '2', '/testexecution/taskExecute', 'C', '0', 'testexecution:taskExecute:view', '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '测试任务执行菜单');
insert into sys_menu values('125',  '用例明细', '5', '3', '/testexecution/taskCaseExecute', 'C', '0', 'testexecution:taskCaseExecute:view', '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '用例明细菜单');
-- 三级菜单
insert into sys_menu values('500',  '操作日志', '110', '1', '/monitor/operlog',    'C', '0', 'monitor:operlog:view',     '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '操作日志菜单');
insert into sys_menu values('501',  '登录日志', '110', '2', '/monitor/logininfor', 'C', '0', 'monitor:logininfor:view',  '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '登录日志菜单');
-- 用户管理按钮
insert into sys_menu values('1000', '用户查询', '100', '1',  '#',  'F', '0', 'system:user:list',        '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1001', '用户新增', '100', '2',  '#',  'F', '0', 'system:user:add',         '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1002', '用户修改', '100', '3',  '#',  'F', '0', 'system:user:edit',        '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1003', '用户删除', '100', '4',  '#',  'F', '0', 'system:user:remove',      '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1004', '用户导出', '100', '5',  '#',  'F', '0', 'system:user:export',      '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1005', '用户导入', '100', '6',  '#',  'F', '0', 'system:user:import',      '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1006', '重置密码', '100', '7',  '#',  'F', '0', 'system:user:resetPwd',    '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
-- 角色管理按钮
insert into sys_menu values('1007', '角色查询', '101', '1',  '#',  'F', '0', 'system:role:list',        '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1008', '角色新增', '101', '2',  '#',  'F', '0', 'system:role:add',         '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1009', '角色修改', '101', '3',  '#',  'F', '0', 'system:role:edit',        '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1010', '角色删除', '101', '4',  '#',  'F', '0', 'system:role:remove',      '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1011', '角色导出', '101', '5',  '#',  'F', '0', 'system:role:export',      '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
-- 菜单管理按钮
insert into sys_menu values('1012', '菜单查询', '102', '1',  '#',  'F', '0', 'system:menu:list',        '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1013', '菜单新增', '102', '2',  '#',  'F', '0', 'system:menu:add',         '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1014', '菜单修改', '102', '3',  '#',  'F', '0', 'system:menu:edit',        '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1015', '菜单删除', '102', '4',  '#',  'F', '0', 'system:menu:remove',      '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
-- 部门管理按钮
insert into sys_menu values('1016', '部门查询', '103', '1',  '#',  'F', '0', 'system:dept:list',        '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1017', '部门新增', '103', '2',  '#',  'F', '0', 'system:dept:add',         '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1018', '部门修改', '103', '3',  '#',  'F', '0', 'system:dept:edit',        '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1019', '部门删除', '103', '4',  '#',  'F', '0', 'system:dept:remove',      '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
-- 岗位管理按钮
insert into sys_menu values('1020', '岗位查询', '106', '1',  '#',  'F', '0', 'system:post:list',        '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1021', '岗位新增', '106', '2',  '#',  'F', '0', 'system:post:add',         '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1022', '岗位修改', '106', '3',  '#',  'F', '0', 'system:post:edit',        '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1023', '岗位删除', '106', '4',  '#',  'F', '0', 'system:post:remove',      '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1024', '岗位导出', '106', '5',  '#',  'F', '0', 'system:post:export',      '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
-- 字典管理按钮
insert into sys_menu values('1025', '字典查询', '107', '1', '#',  'F', '0', 'system:dict:list',         '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1026', '字典新增', '107', '2', '#',  'F', '0', 'system:dict:add',          '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1027', '字典修改', '107', '3', '#',  'F', '0', 'system:dict:edit',         '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1028', '字典删除', '107', '4', '#',  'F', '0', 'system:dict:remove',       '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1029', '字典导出', '107', '5', '#',  'F', '0', 'system:dict:export',       '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
-- 参数设置按钮
insert into sys_menu values('1030', '参数查询', '108', '1', '#',  'F', '0', 'system:config:list',      '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1031', '参数新增', '108', '2', '#',  'F', '0', 'system:config:add',       '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1032', '参数修改', '108', '3', '#',  'F', '0', 'system:config:edit',      '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1033', '参数删除', '108', '4', '#',  'F', '0', 'system:config:remove',    '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1034', '参数导出', '108', '5', '#',  'F', '0', 'system:config:export',    '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
-- 通知公告按钮
insert into sys_menu values('1035', '公告查询', '109', '1', '#',  'F', '0', 'system:notice:list',      '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1036', '公告新增', '109', '2', '#',  'F', '0', 'system:notice:add',       '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1037', '公告修改', '109', '3', '#',  'F', '0', 'system:notice:edit',      '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1038', '公告删除', '109', '4', '#',  'F', '0', 'system:notice:remove',    '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
-- 操作日志按钮
insert into sys_menu values('1039', '操作查询', '500', '1', '#',  'F', '0', 'monitor:operlog:list',    '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1040', '操作删除', '500', '2', '#',  'F', '0', 'monitor:operlog:remove',  '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1041', '详细信息', '500', '3', '#',  'F', '0', 'monitor:operlog:detail',  '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1042', '日志导出', '500', '4', '#',  'F', '0', 'monitor:operlog:export',  '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
-- 登录日志按钮
insert into sys_menu values('1043', '登录查询', '501', '1', '#',  'F', '0', 'monitor:logininfor:list',         '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1044', '登录删除', '501', '2', '#',  'F', '0', 'monitor:logininfor:remove',       '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1045', '日志导出', '501', '3', '#',  'F', '0', 'monitor:logininfor:export',       '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
-- 在线用户按钮
insert into sys_menu values('1046', '在线查询', '111', '1', '#',  'F', '0', 'monitor:online:list',             '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1047', '批量强退', '111', '2', '#',  'F', '0', 'monitor:online:batchForceLogout', '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1048', '单条强退', '111', '3', '#',  'F', '0', 'monitor:online:forceLogout',      '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
-- 定时任务按钮
insert into sys_menu values('1049', '任务查询', '112', '1', '#',  'F', '0', 'monitor:job:list',                '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1050', '任务新增', '112', '2', '#',  'F', '0', 'monitor:job:add',                 '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1051', '任务修改', '112', '3', '#',  'F', '0', 'monitor:job:edit',                '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1052', '任务删除', '112', '4', '#',  'F', '0', 'monitor:job:remove',              '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1053', '状态修改', '112', '5', '#',  'F', '0', 'monitor:job:changeStatus',        '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1054', '任务详细', '112', '6', '#',  'F', '0', 'monitor:job:detail',              '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1055', '任务导出', '112', '7', '#',  'F', '0', 'monitor:job:export',              '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
-- 代码生成按钮
insert into sys_menu values('1056', '生成查询', '116', '1', '#',  'F', '0', 'tool:gen:list',  '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1057', '生成代码', '116', '2', '#',  'F', '0', 'tool:gen:code',  '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
-- 测试项目管理按钮
insert into sys_menu values('1058', '项目查询', '104', '1',  '#',  'F', '0', 'system:project:list', '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1059', '项目新增', '104', '2',  '#',  'F', '0', 'system:project:add',  '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1060', '项目修改', '104', '3',  '#',  'F', '0', 'system:project:edit', '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1061', '项目删除', '104', '4',  '#',  'F', '0', 'system:project:remove', '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
-- 客户端管理按钮
insert into sys_menu values('1062', '客户端查询', '105', '1',  '#',  'F', '0', 'system:client:list', '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1063', '客户端新增', '105', '2',  '#',  'F', '0', 'system:client:add', '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1064', '客户端修改', '105', '3',  '#',  'F', '0', 'system:client:edit', '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1065', '客户端删除', '105', '4',  '#',  'F', '0', 'system:client:remove', '#', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
-- 用例管理按钮
insert into sys_menu values('1066','用例查询','118','1', '#', 'F','0', 'testmanagmt:projectCase:list','#', 'admin','2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1067','用例新增','118','2', '#', 'F','0', 'testmanagmt:projectCase:add', '#', 'admin','2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1068','用例修改','118','3', '#', 'F','0', 'testmanagmt:projectCase:edit','#', 'admin','2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
insert into sys_menu values('1069','用例删除','118','4', '#', 'F','0', 'testmanagmt:projectCase:remove','#','admin','2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');
-- 用例模块按钮
insert into sys_menu values('1070','模块查询','119','1','#','F','0','testmanagmt:projectCaseModule:list','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
insert into sys_menu values('1071','模块新增','119','2','#','F','0','testmanagmt:projectCaseModule:add','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
insert into sys_menu values('1072','模块修改','119','3','#','F','0','testmanagmt:projectCaseModule:edit','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
insert into sys_menu values('1073','模块删除','119','4','#','F','0','testmanagmt:projectCaseModule:remove','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
-- 协议模板按钮
insert into sys_menu 
values('1074','协议查询','120','1','#','F','0','testmanagmt:projectProtocolTemplate:list','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
insert into sys_menu 
values('1075','协议新增','120','2','#','F','0','testmanagmt:projectProtocolTemplate:add','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
insert into sys_menu 
values('1076','协议修改','120','3','#','F','0','testmanagmt:projectProtocolTemplate:edit','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
insert into sys_menu 
values('1077','协议删除','120','4','#','F','0','testmanagmt:projectProtocolTemplate:remove','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
-- 测试计划按钮
insert into sys_menu 
values('1078','计划查询','121','1','#','F','0','testmanagmt:projectPlan:list','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
insert into sys_menu 
values('1079','计划新增','121','2','#','F','0','testmanagmt:projectPlan:add','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
insert into sys_menu 
values('1080','计划修改','121','3','#','F','0','testmanagmt:projectPlan:edit','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
insert into sys_menu 
values('1081','计划删除','121','4','#','F','0','testmanagmt:projectPlan:remove','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
-- 公共参数按钮
insert into sys_menu 
values('1082','参数查询','122','1','#','F','0','testmanagmt:projectCaseParams:list','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
insert into sys_menu 
values('1083','参数新增','122','2','#','F','0','testmanagmt:projectCaseParams:add','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
insert into sys_menu 
values('1084','参数修改','122','3','#','F','0','testmanagmt:projectCaseParams:edit','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
insert into sys_menu 
values('1085','参数删除','122','4','#','F','0','testmanagmt:projectCaseParams:remove','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
-- 调度任务按钮
insert into sys_menu 
values('1086','调度查询','123','1','#','F','0','testexecution:taskScheduling:list','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
insert into sys_menu 
values('1087','调度新增','123','2','#','F','0','testexecution:taskScheduling:add','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
insert into sys_menu 
values('1088','调度修改','123','3','#','F','0','testexecution:taskScheduling:edit','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
insert into sys_menu 
values('1089','调度删除','123','4','#','F','0','testexecution:taskScheduling:remove','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
insert into sys_menu 
values('1090','调度执行','123','5','#','F','0','testexecution:taskScheduling:execution','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
-- 任务执行按钮
insert into sys_menu 
values('1091','执行查询','124','1','#','F','0','testexecution:taskExecute:list','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
insert into sys_menu 
values('1092','执行删除','124','2','#','F','0','testexecution:taskExecute:remove','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
-- 用例明细按钮
insert into sys_menu 
values('1093','用例明细查询','125','1','#','F','0','testexecution:taskCaseExecute:list','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
insert into sys_menu 
values('1094','用例明细删除','125','2','#','F','0','testexecution:taskCaseExecute:remove','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
insert into sys_menu 
values('1095','用例明细执行','125','3','#','F','0','testexecution:taskCaseExecute:execution','#','admin','2019-02-13 10-27-32','luckyframe','2019-02-13 10-27-32','');
-- 用例模块按钮(新增导出功能)
insert into sys_menu values('1096','用例导出','118','5', '#', 'F','0', 'testmanagmt:projectCase:export','#','admin','2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '');

-- ----------------------------
-- 16、用户和角色关联表  用户N-1角色
-- ----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
  user_id 	int(11) not null comment '用户ID',
  role_id 	int(11) not null comment '角色ID',
  primary key(user_id, role_id)
) engine=innodb default charset=utf8 comment = '用户和角色关联表';

-- ----------------------------
-- 初始化-用户和角色关联表数据
-- ----------------------------
insert into sys_user_role values ('1', '1');


-- ----------------------------
-- 17、角色和菜单关联表  角色1-N菜单
-- ----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
  role_id 	int(11) not null comment '角色ID',
  menu_id 	int(11) not null comment '菜单ID',
  primary key(role_id, menu_id)
) engine=innodb default charset=utf8 comment = '角色和菜单关联表';

-- ----------------------------
-- 初始化-角色和菜单关联表数据
-- ----------------------------
insert into sys_role_menu values ('2', '1');
insert into sys_role_menu values ('2', '2');
insert into sys_role_menu values ('2', '3');
insert into sys_role_menu values ('2', '100');
insert into sys_role_menu values ('2', '101');
insert into sys_role_menu values ('2', '102');
insert into sys_role_menu values ('2', '103');
insert into sys_role_menu values ('2', '104');
insert into sys_role_menu values ('2', '105');
insert into sys_role_menu values ('2', '106');
insert into sys_role_menu values ('2', '107');
insert into sys_role_menu values ('2', '108');
insert into sys_role_menu values ('2', '109');
insert into sys_role_menu values ('2', '110');
insert into sys_role_menu values ('2', '111');
insert into sys_role_menu values ('2', '112');
insert into sys_role_menu values ('2', '113');
insert into sys_role_menu values ('2', '114');
insert into sys_role_menu values ('2', '115');
insert into sys_role_menu values ('2', '116');
insert into sys_role_menu values ('2', '500');
insert into sys_role_menu values ('2', '501');
insert into sys_role_menu values ('2', '1000');
insert into sys_role_menu values ('2', '1001');
insert into sys_role_menu values ('2', '1002');
insert into sys_role_menu values ('2', '1003');
insert into sys_role_menu values ('2', '1004');
insert into sys_role_menu values ('2', '1005');
insert into sys_role_menu values ('2', '1006');
insert into sys_role_menu values ('2', '1007');
insert into sys_role_menu values ('2', '1008');
insert into sys_role_menu values ('2', '1009');
insert into sys_role_menu values ('2', '1010');
insert into sys_role_menu values ('2', '1011');
insert into sys_role_menu values ('2', '1012');
insert into sys_role_menu values ('2', '1013');
insert into sys_role_menu values ('2', '1014');
insert into sys_role_menu values ('2', '1015');
insert into sys_role_menu values ('2', '1016');
insert into sys_role_menu values ('2', '1017');
insert into sys_role_menu values ('2', '1018');
insert into sys_role_menu values ('2', '1019');
insert into sys_role_menu values ('2', '1020');
insert into sys_role_menu values ('2', '1021');
insert into sys_role_menu values ('2', '1022');
insert into sys_role_menu values ('2', '1023');
insert into sys_role_menu values ('2', '1024');
insert into sys_role_menu values ('2', '1025');
insert into sys_role_menu values ('2', '1026');
insert into sys_role_menu values ('2', '1027');
insert into sys_role_menu values ('2', '1028');
insert into sys_role_menu values ('2', '1029');
insert into sys_role_menu values ('2', '1030');
insert into sys_role_menu values ('2', '1031');
insert into sys_role_menu values ('2', '1032');
insert into sys_role_menu values ('2', '1033');
insert into sys_role_menu values ('2', '1034');
insert into sys_role_menu values ('2', '1035');
insert into sys_role_menu values ('2', '1036');
insert into sys_role_menu values ('2', '1037');
insert into sys_role_menu values ('2', '1038');
insert into sys_role_menu values ('2', '1039');
insert into sys_role_menu values ('2', '1040');
insert into sys_role_menu values ('2', '1041');
insert into sys_role_menu values ('2', '1042');
insert into sys_role_menu values ('2', '1043');
insert into sys_role_menu values ('2', '1044');
insert into sys_role_menu values ('2', '1045');
insert into sys_role_menu values ('2', '1046');
insert into sys_role_menu values ('2', '1047');
insert into sys_role_menu values ('2', '1048');
insert into sys_role_menu values ('2', '1049');
insert into sys_role_menu values ('2', '1050');
insert into sys_role_menu values ('2', '1051');
insert into sys_role_menu values ('2', '1052');
insert into sys_role_menu values ('2', '1053');
insert into sys_role_menu values ('2', '1054');
insert into sys_role_menu values ('2', '1055');
insert into sys_role_menu values ('2', '1056');
insert into sys_role_menu values ('2', '1057');

-- ----------------------------
-- 18、角色和项目关联表  角色1-N项目
-- ----------------------------
drop table if exists sys_role_project;
create table sys_role_project (
  role_id 	    int(11) not null comment '角色ID',
  project_id 	int(8) not null comment '项目ID',
  primary key(role_id, project_id)
) engine=innodb default charset=utf8 comment = '角色和项目关联表';

-- ----------------------------
-- 初始化-角色和项目关联表数据
-- ----------------------------
insert into sys_role_project values ('2', '1');

-- ----------------------------
-- 8、角色和部门关联表  角色1-N部门
-- ----------------------------
drop table if exists sys_role_dept;
create table sys_role_dept (
  role_id 	int(11) not null comment '角色ID',
  dept_id 	int(11) not null comment '部门ID',
  primary key(role_id, dept_id)
) engine=innodb default charset=utf8 comment = '角色和部门关联表';

-- ----------------------------
-- 初始化-角色和部门关联表数据
-- ----------------------------
insert into sys_role_dept values ('2', '100');
insert into sys_role_dept values ('2', '101');
insert into sys_role_dept values ('2', '105');

-- ----------------------------
-- 19、用户与岗位关联表  用户1-N岗位
-- ----------------------------
drop table if exists sys_user_post;
create table sys_user_post
(
	user_id int(11) not null comment '用户ID',
	post_id int(11) not null comment '岗位ID',
	primary key (user_id, post_id)
) engine=innodb default charset=utf8 comment = '用户与岗位关联表';

-- ----------------------------
-- 初始化-用户与岗位关联表数据
-- ----------------------------
insert into sys_user_post values ('1', '1');


-- ----------------------------
-- 20、操作日志记录
-- ----------------------------
drop table if exists sys_oper_log;
create table sys_oper_log (
  oper_id 			int(11) 		not null auto_increment    comment '日志主键',
  title             varchar(50)     default ''                 comment '模块标题',
  business_type     int(2)          default 0                  comment '业务类型（0其它 1新增 2修改 3删除）',
  method            varchar(100)    default ''                 comment '方法名称',
  operator_type     int(1)          default 0                  comment '操作类别（0其它 1后台用户 2手机端用户）',
  oper_name 	    varchar(50)     default '' 		 	 	   comment '操作人员',
  dept_name 		varchar(50)     default '' 		 	 	   comment '部门名称',
  oper_url 		    varchar(255) 	default '' 				   comment '请求URL',
  oper_ip 			varchar(50) 	default '' 				   comment '主机地址',
  oper_location     varchar(255)    default ''                 comment '操作地点',
  oper_param 		varchar(255) 	default '' 				   comment '请求参数',
  status 			int(1) 		    default 0				   comment '操作状态（0正常 1异常）',
  error_msg 		varchar(2000) 	default '' 				   comment '错误消息',
  oper_time 		datetime                                   comment '操作时间',
  primary key (oper_id)
) engine=innodb auto_increment=100 default charset=utf8 comment = '操作日志记录';


-- ----------------------------
-- 21、字典类型表
-- ----------------------------
drop table if exists sys_dict_type;
create table sys_dict_type
(
	dict_id          int(11) 		 not null auto_increment    comment '字典主键',
	dict_name        varchar(100)    default ''                 comment '字典名称',
	dict_type        varchar(100)    default ''                 comment '字典类型',
    status 			 char(1) 		 default '0'			    comment '状态（0正常 1停用）',
    create_by        varchar(64)     default ''                 comment '创建者',
    create_time      datetime                                   comment '创建时间',
    update_by        varchar(64) 	 default ''			        comment '更新者',
	update_time      datetime                                   comment '更新时间',
    remark 	         varchar(500) 	 default null 				comment '备注',
	primary key (dict_id),
	unique (dict_type)
) engine=innodb auto_increment=100 default charset=utf8 comment = '字典类型表';

insert into sys_dict_type values(1,  '用户性别', 'sys_user_sex',        '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '用户性别列表');
insert into sys_dict_type values(2,  '菜单状态', 'sys_show_hide',       '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '菜单状态列表');
insert into sys_dict_type values(3,  '系统开关', 'sys_normal_disable',  '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '系统开关列表');
insert into sys_dict_type values(4,  '任务状态', 'sys_job_status',      '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '任务状态列表');
insert into sys_dict_type values(5,  '系统是否', 'sys_yes_no',          '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '系统是否列表');
insert into sys_dict_type values(6,  '通知类型', 'sys_notice_type',     '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '通知类型列表');
insert into sys_dict_type values(7,  '通知状态', 'sys_notice_status',   '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '通知状态列表');
insert into sys_dict_type values(8,  '操作类型', 'sys_oper_type',       '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '操作类型列表');
insert into sys_dict_type values(9,  '系统状态', 'sys_common_status',   '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '登录状态列表');
insert into sys_dict_type values(10, '用例类型', 'testmanagmt_case_type', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '用例步骤类型');
insert into sys_dict_type values(11, '步骤失败策略', 'testmanagmt_case_stepfailcontinue', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '步骤失败策略');
insert into sys_dict_type values(12, '模板参数类型', 'testmanagmt_templateparams_type', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '模板参数类型');
insert into sys_dict_type 
values(13, '步骤HTTP操作类型', 'testmanagmt_casestep_httpoperation', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '步骤关键字操作字典');
insert into sys_dict_type 
values(14, '步骤Web UI操作类型', 'testmanagmt_casestep_uioperation', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '步骤关键字操作字典');
insert into sys_dict_type 
values(15, '步骤移动端操作类型', 'testmanagmt_casestep_muioperation', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '步骤关键字操作字典');
-- ----------------------------
-- 22、字典数据表
-- ----------------------------
drop table if exists sys_dict_data;
create table sys_dict_data
(
	dict_code        int(11) 		 not null auto_increment    comment '字典编码',
	dict_sort        int(4)          default 0                  comment '字典排序',
	dict_label       varchar(100)    default ''                 comment '字典标签',
	dict_value       varchar(100)    default ''                 comment '字典键值',
	dict_type        varchar(100)    default ''                 comment '字典类型',
	css_class        varchar(100)    default null               comment '样式属性（其他样式扩展）',
	list_class       varchar(100)    default null               comment '表格回显样式',
	is_default       char(1)         default 'N'                comment '是否默认（Y是 N否）',
    status 			 char(1) 		 default '0'			    comment '状态（0正常 1停用）',
    create_by        varchar(64)     default ''                 comment '创建者',
    create_time      datetime                                   comment '创建时间',
    update_by        varchar(64) 	 default ''			        comment '更新者',
	update_time      datetime                                   comment '更新时间',
    remark 	         varchar(500) 	 default null 				comment '备注',
	primary key (dict_code)
) engine=innodb auto_increment=100 default charset=utf8 comment = '字典数据表';


insert into sys_dict_data values(1,  1,  '男',       '0',  'sys_user_sex',        '',   '',        'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '性别男');
insert into sys_dict_data values(2,  2,  '女',       '1',  'sys_user_sex',        '',   '',        'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '性别女');
insert into sys_dict_data values(3,  3,  '未知',     '2',  'sys_user_sex',        '',   '',        'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '性别未知');
insert into sys_dict_data values(4,  1,  '显示',     '0',  'sys_show_hide',       '',   'primary', 'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '显示菜单');
insert into sys_dict_data values(5,  2,  '隐藏',     '1',  'sys_show_hide',       '',   'danger',  'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '隐藏菜单');
insert into sys_dict_data values(6,  1,  '正常',     '0',  'sys_normal_disable',  '',   'primary', 'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '正常状态');
insert into sys_dict_data values(7,  2,  '停用',     '1',  'sys_normal_disable',  '',   'danger',  'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '停用状态');
insert into sys_dict_data values(8,  1,  '正常',     '0',  'sys_job_status',      '',   'primary', 'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '正常状态');
insert into sys_dict_data values(9,  2,  '暂停',     '1',  'sys_job_status',      '',   'danger',  'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '停用状态');
insert into sys_dict_data values(10, 1,  '是',       'Y',  'sys_yes_no',          '',   'primary', 'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '系统默认是');
insert into sys_dict_data values(11, 2,  '否',       'N',  'sys_yes_no',          '',   'danger',  'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '系统默认否');
insert into sys_dict_data values(12, 1,  '通知',     '1',  'sys_notice_type',     '',   'warning', 'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '通知');
insert into sys_dict_data values(13, 2,  '公告',     '2',  'sys_notice_type',     '',   'success', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '公告');
insert into sys_dict_data values(14, 1,  '正常',     '0',  'sys_notice_status',   '',   'primary', 'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '正常状态');
insert into sys_dict_data values(15, 2,  '关闭',     '1',  'sys_notice_status',   '',   'danger',  'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '关闭状态');
insert into sys_dict_data values(16, 1,  '新增',     '1',  'sys_oper_type',       '',   'info',    'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '新增操作');
insert into sys_dict_data values(17, 2,  '修改',     '2',  'sys_oper_type',       '',   'info',    'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '修改操作');
insert into sys_dict_data values(18, 3,  '删除',     '3',  'sys_oper_type',       '',   'danger',  'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '删除操作');
insert into sys_dict_data values(19, 4,  '授权',     '4',  'sys_oper_type',       '',   'primary', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '授权操作');
insert into sys_dict_data values(20, 5,  '导出',     '5',  'sys_oper_type',       '',   'warning', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '导出操作');
insert into sys_dict_data values(21, 6,  '导入',     '6',  'sys_oper_type',       '',   'warning', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '导入操作');
insert into sys_dict_data values(22, 7,  '强退',     '7',  'sys_oper_type',       '',   'danger',  'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '强退操作');
insert into sys_dict_data values(23, 8,  '生成代码', '8',  'sys_oper_type',       '',   'warning', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '生成操作');
insert into sys_dict_data values(24, 8,  '清空数据', '9',  'sys_oper_type',       '',   'danger',  'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '清空操作');
insert into sys_dict_data values(25, 1,  '成功',     '0',  'sys_common_status',   '',   'primary', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '正常状态');
insert into sys_dict_data values(26, 2,  '失败',     '1',  'sys_common_status',   '',   'danger',  'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '停用状态');
insert into sys_dict_data values(27, 1,  'HTTP接口', '0',  'testmanagmt_case_type',   '',   'info',  'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', 'HTTP接口');
insert into sys_dict_data values(28, 2,  'Web UI', '1',  'testmanagmt_case_type',   '',   'info',  'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', 'Web UI');
insert into sys_dict_data values(29, 3,  'API驱动', '2',  'testmanagmt_case_type',   '',   'info',  'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', 'API驱动');
insert into sys_dict_data values(30, 4,  '移动端', '3',  'testmanagmt_case_type',   '',   'info',  'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '移动端');
insert into sys_dict_data values(31, 1,  '中断', '0',  'testmanagmt_case_stepfailcontinue',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '后续步骤中断');
insert into sys_dict_data values(32, 2,  '继续', '1',  'testmanagmt_case_stepfailcontinue',   '',   'info',  'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '后续步骤继续');
insert into sys_dict_data values(33, 1,  'String', '0',  'testmanagmt_templateparams_type',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '模板参数类型 String');
insert into sys_dict_data values(34, 2,  'JSON对象', '1',  'testmanagmt_templateparams_type',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '模板参数类型 JSON对象');
insert into sys_dict_data values(35, 3,  'JSONARR对象', '2',  'testmanagmt_templateparams_type',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '模板参数类型 JSONARR对象');
insert into sys_dict_data values(36, 4,  'File对象', '3',  'testmanagmt_templateparams_type',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '模板参数类型 File对象');
insert into sys_dict_data values(37, 5,  'Number对象', '4',  'testmanagmt_templateparams_type',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '模板参数类型 Number对象');
insert into sys_dict_data values(38, 6,  'Boolean对象', '5',  'testmanagmt_templateparams_type',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '模板参数类型 Boolean对象');
insert into sys_dict_data values(39, 1,  '未执行', '0',  'task_execute_status',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '任务执行状态');
insert into sys_dict_data values(40, 2,  '执行中', '1',  'task_execute_status',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '任务执行状态');
insert into sys_dict_data values(41, 3,  '执行完成', '2',  'task_execute_status',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '任务执行状态');
insert into sys_dict_data values(42, 4,  '执行失败', '3',  'task_execute_status',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '任务执行状态');
insert into sys_dict_data values(43, 5,  '唤起客户端失败', '4',  'task_execute_status',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '任务执行状态');
insert into sys_dict_data values(44, 1,  '成功', '0',  'case_execute_status',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '用例执行状态');
insert into sys_dict_data values(45, 2,  '失败', '1',  'case_execute_status',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '用例执行状态');
insert into sys_dict_data values(46, 3,  '锁定', '2',  'case_execute_status',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '用例执行状态');
insert into sys_dict_data values(47, 4,  '执行中', '3',  'case_execute_status',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '用例执行状态');
insert into sys_dict_data values(48, 5,  '未执行', '4',  'case_execute_status',   '',   'info', 'N', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '用例执行状态');
/*HTTP请求方法*/
insert into sys_dict_data values(1000, 1,  'HttpClientPost发送Post请求', 'HttpClientPost',  'testmanagmt_casestep_httpoperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '使用HttpClient发送post请求');
insert into sys_dict_data values(1001, 2,  'HttpClientGet发送Get请求', 'HttpClientGet',  'testmanagmt_casestep_httpoperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '使用HttpClient发送get请求');
insert into sys_dict_data values(1002, 3,  'HttpClientPostJSON发送JSON格式Post请求', 'HttpClientPostJSON',  'testmanagmt_casestep_httpoperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '使用HttpClient发送JSON格式post请求');
insert into sys_dict_data values(1003, 4,  'httpClientPut发送Put请求', 'httpClientPut',  'testmanagmt_casestep_httpoperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '使用httpClientPut发送put请求');
insert into sys_dict_data values(1004, 5,  'httpClientPutJson发送JSON格式Put请求', 'httpClientPutJson',  'testmanagmt_casestep_httpoperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '使用httpClientPutJson发送put请求');
insert into sys_dict_data values(1005, 6,  'httpClientUploadFile上传文件', 'httpClientUploadFile',  'testmanagmt_casestep_httpoperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '使用httpClientUploadFile上传文件');
insert into sys_dict_data values(1006, 7,  'HttpURLPost发送Post请求', 'HttpURLPost',  'testmanagmt_casestep_httpoperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '使用HttpURLConnection发送post请求');
insert into sys_dict_data values(1007, 8,  'URLPost发送Post请求', 'URLPost',  'testmanagmt_casestep_httpoperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '使用URLConnection发送post');
insert into sys_dict_data values(1008, 9,  'GetAndSaveFile保存下载文件到客户端', 'GetAndSaveFile',  'testmanagmt_casestep_httpoperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '发送get请求保存下载文件到客户端');
insert into sys_dict_data values(1009, 10,  'HttpURLGet发送Get请求', 'HttpURLGet',  'testmanagmt_casestep_httpoperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '使用HttpURLConnection发送get请求');
insert into sys_dict_data values(1010, 11,  'URLGet发送Get请求', 'URLGet',  'testmanagmt_casestep_httpoperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '使用URLConnection发送get请求');
insert into sys_dict_data values(1011, 12,  'HttpURLDelete发送Delete请求', 'HttpURLDelete',  'testmanagmt_casestep_httpoperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '使用HttpURLDelete发送delete请求');
/*Web UI内置关键字*/
insert into sys_dict_data values(2000, 1,  'Click点击对象', 'click',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '点击对象');
insert into sys_dict_data values(2001, 2,  'Sendkeys输入', 'sendkeys',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '输入');
insert into sys_dict_data values(2002, 3,  'Clear清除输入框', 'clear',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '清除输入框');
insert into sys_dict_data values(2003, 4,  'Gotoframe跳转iframe框架', 'gotoframe',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '跳转框架（iframe）');
insert into sys_dict_data values(2004, 5,  'Isenabled判断对象是否可用', 'isenabled',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '判断对象是否可用');
insert into sys_dict_data values(2005, 6,  'Isdisplayed判断对象是否可见', 'isdisplayed',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '判断对象是否可见');
insert into sys_dict_data values(2006, 7,  'Exjsob针对对象执行JS脚本', 'exjsob',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '针对对象执行JS脚本');
insert into sys_dict_data values(2007, 8,  'Gettext获取对象文本属性', 'gettext',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '获取对象文本属性');
insert into sys_dict_data values(2008, 9,  'Gettagname获取对象标签类型', 'gettagname',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '获取对象标签类型');
insert into sys_dict_data values(2009, 10,  'Getcaptcha获取对象中的验证码(识别率较低)', 'getcaptcha',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '获取对象中的验证码(识别率较低)');
insert into sys_dict_data values(2010, 11,  'Selectbyvisibletext通过下拉框的文本进行选择', 'selectbyvisibletext',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '通过下拉框的文本进行选择');
insert into sys_dict_data values(2011, 12,  'Selectbyvalue通过下拉框的VALUE属性进行选择', 'selectbyvalue',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '通过下拉框的VALUE属性进行选择');
insert into sys_dict_data values(2012, 13,  'Selectbyindex通过下拉框的index属性进行选择(从0开始)', 'selectbyindex',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '通过下拉框的index属性进行选择(从0开始)');
insert into sys_dict_data values(2013, 14,  'Isselect判断是否已经被选择，同用于单选\复选框', 'isselect',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '判断是否已经被选择，同用于单选\复选框');
insert into sys_dict_data values(2014, 15,  'Open打开URL', 'open',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '打开URL');
insert into sys_dict_data values(2015, 16,  'Exjs执行js脚本', 'exjs',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '执行js脚本');
insert into sys_dict_data values(2016, 17,  'Gotodefaultcontent跳转回到默认iframe', 'gotodefaultcontent',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '跳转回到默认iframe');
insert into sys_dict_data values(2017, 18,  'Gettitle获取窗口标题', 'gettitle',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '获取窗口标题');
insert into sys_dict_data values(2018, 19,  'Getwindowhandle获取窗口句柄', 'getwindowhandle',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '获取窗口句柄');
insert into sys_dict_data values(2019, 20,  'Gotowindow跳转窗口句柄', 'gotowindow',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '跳转窗口句柄');
insert into sys_dict_data values(2020, 21,  'Timeout设置全局隐式等待时间(S)', 'timeout',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '设置全局隐式等待时间(S)');
insert into sys_dict_data values(2021, 22,  'Alertaccept弹出框点击OK', 'alertaccept',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '弹出框点击OK');
insert into sys_dict_data values(2022, 23,  'Alertdismiss弹出框点击取消', 'alertdismiss',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '弹出框点击取消');
insert into sys_dict_data values(2023, 24,  'Alertgettext获取弹出框TEXT', 'alertgettext',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '获取弹出框TEXT');
insert into sys_dict_data values(2024, 25,  'Mouselkclick模拟鼠标左键单击(可带页面对象)', 'mouselkclick',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '模拟鼠标左键单击(可带页面对象)');
insert into sys_dict_data values(2025, 26,  'Mouserkclick模拟鼠标右键单击(可带页面对象)', 'mouserkclick',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '模拟鼠标右键单击(可带页面对象)');
insert into sys_dict_data values(2026, 27,  'Mousedclick模拟鼠标双击(可带页面对象)', 'mousedclick',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '模拟鼠标双击(可带页面对象)');
insert into sys_dict_data values(2027, 28,  'Mouseclickhold模拟鼠标左键单击后不释放(可带页面对象)', 'mouseclickhold',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '模拟鼠标左键单击后不释放(可带页面对象)');
insert into sys_dict_data values(2028, 29,  'Mousedrag模拟鼠标拖拽(可带页面对象)', 'mousedrag',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '模拟鼠标拖拽(可带页面对象)');
insert into sys_dict_data values(2029, 30,  'Mouseto模拟鼠标移动到指定坐标(可带页面对象)', 'mouseto',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '模拟鼠标移动到指定坐标(可带页面对象)');
insert into sys_dict_data values(2030, 31,  'Mouserelease模拟鼠标释放(可带页面对象)', 'mouserelease',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '模拟鼠标释放(可带页面对象)');
insert into sys_dict_data values(2031, 32,  'Mousekey(tab)模拟键盘Tab键', 'mousekey(tab)',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '模拟键盘Tab键');
insert into sys_dict_data values(2032, 33,  'Mousekey(space)模拟键盘Space键', 'mousekey(space)',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '模拟键盘Space键');
insert into sys_dict_data values(2033, 34,  'Mousekey(ctrl)模拟键盘Ctrl键', 'mousekey(ctrl)',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '模拟键盘Ctrl键');
insert into sys_dict_data values(2034, 35,  'Mousekey(shift)模拟键盘Shift键', 'mousekey(shift)',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '模拟键盘Shift键');
insert into sys_dict_data values(2035, 36,  'Mousekey(enter)模拟键盘Enter键', 'mousekey(enter)',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '模拟键盘Enter键');
insert into sys_dict_data values(2036, 37,  'Runcase调用指定接口|Web UI用例', 'runcase',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '调用指定接口|Web UI用例');
insert into sys_dict_data values(2037, 38,  'Getattribute获取对象指定属性', 'getattribute',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '获取对象指定属性');
insert into sys_dict_data values(2038, 39,  'Getcssvalue获取对象指定CSS属性值', 'getcssvalue',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '获取对象指定CSS属性值');
insert into sys_dict_data values(2039, 40,  'Gotoparentframe跳转回到上一级iframe', 'gotoparentframe',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '跳转回到上一级iframe');
insert into sys_dict_data values(2040, 41,  'Scrollto滚动到目标对象', 'scrollto',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '滚动到目标对象');
insert into sys_dict_data values(2041, 42,  'Scrollintoview将目标对象滚动到可视', 'scrollintoview',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '将目标对象滚动到可视');
insert into sys_dict_data values(2042, 43,  'Closewindow关闭当前浏览器窗口', 'closewindow',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '关闭当前浏览器窗口');
insert into sys_dict_data values(2043, 44,  'Addcookie添加浏览器cookie', 'addcookie',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '添加浏览器cookie');
/*移动端内置关键字*/
insert into sys_dict_data values(3000, 1,  'Selectbyvisibletext通过下拉框的文本进行选择', 'selectbyvisibletext',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '通过下拉框的文本进行选择');
insert into sys_dict_data values(3001, 2,  'Selectbyvalue通过下拉框的VALUE属性进行选择', 'selectbyvalue',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '通过下拉框的VALUE属性进行选择');
insert into sys_dict_data values(3002, 3,  'Selectbyindex通过下拉框的index属性进行选择(从0开始)', 'selectbyindex',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '通过下拉框的index属性进行选择(从0开始)');
insert into sys_dict_data values(3003, 4,  'Isselect判断是否已经被选择，同用于单选\复选框', 'isselect',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '判断是否已经被选择，同用于单选\复选框');
insert into sys_dict_data values(3004, 5,  'Gettext获取对象文本属性', 'gettext',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '获取对象文本属性');
insert into sys_dict_data values(3005, 6,  'Gettagname获取对象标签类型', 'gettagname',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '获取对象标签类型');
insert into sys_dict_data values(3006, 7,  'Getattribute获取对象指定属性', 'getattribute',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '获取对象指定属性');
insert into sys_dict_data values(3007, 8,  'Getcssvalue获取对象指定CSS属性值', 'getcssvalue',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '获取对象指定CSS属性值');
insert into sys_dict_data values(3008, 9,  'Click点击对象', 'click',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '点击对象');
insert into sys_dict_data values(3009, 10,  'Sendkeys输入', 'sendkeys',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '输入');
insert into sys_dict_data values(3010, 11,  'Clear清除输入框', 'clear',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '清除输入框');
insert into sys_dict_data values(3011, 12,  'Isenabled判断对象是否可用', 'isenabled',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '判断对象是否可用');
insert into sys_dict_data values(3012, 13,  'Isdisplayed判断对象是否可见', 'isdisplayed',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '判断对象是否可见');
insert into sys_dict_data values(3013, 14,  'Exjsob针对对象执行JS脚本', 'exjsob',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '针对对象执行JS脚本');
insert into sys_dict_data values(3014, 15,  'Longpresselement长按指定页面对象(可设置时间)', 'longpresselement',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '长按指定页面对象(可设置时间)');
insert into sys_dict_data values(3015, 16,  'Alertaccept弹出框点击OK', 'alertaccept',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '弹出框点击OK');
insert into sys_dict_data values(3016, 17,  'Alertdismiss弹出框点击取消', 'alertdismiss',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '弹出框点击取消');
insert into sys_dict_data values(3017, 18,  'Alertgettext获取弹出框TEXT', 'alertgettext',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '获取弹出框TEXT');
insert into sys_dict_data values(3018, 19,  'Getcontexthandles获取指定context的值(参数指定1 第一个)', 'getcontexthandles',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '获取指定context的值(参数指定1 第一个)');
insert into sys_dict_data values(3019, 20,  'Exjs执行JS脚本', 'exjs',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '执行JS脚本');
insert into sys_dict_data values(3020, 21,  'Androidkeycode安卓模拟手机键盘发送指令，同PressKeyCode', 'androidkeycode',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '安卓模拟手机键盘发送指令，同PressKeyCode');
insert into sys_dict_data values(3021, 22,  'Gotocontext跳转到指定的context', 'gotocontext',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '跳转到指定的context');
insert into sys_dict_data values(3022, 23,  'Getcontext获取当前窗口的context', 'getcontext',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '获取当前窗口的context');
insert into sys_dict_data values(3023, 24,  'Gettitle获取当前窗口的title', 'gettitle',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '获取当前窗口的title');
insert into sys_dict_data values(3024, 25,  'Swipeup页面向上滑动(参数 持续时间|滚动次数)', 'swipeup',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '页面向上滑动(参数 持续时间|滚动次数)');
insert into sys_dict_data values(3025, 26,  'Swipedown页面向下滑动(参数 持续时间|滚动次数)', 'swipedown',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '页面向下滑动(参数 持续时间|滚动次数)');
insert into sys_dict_data values(3026, 27,  'Swipleft页面向左滑动(参数 持续时间|滚动次数)', 'swipleft',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '页面向左滑动(参数 持续时间|滚动次数)');
insert into sys_dict_data values(3027, 28,  'Swipright页面向右滑动(参数 持续时间|滚动次数)', 'swipright',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '页面向右滑动(参数 持续时间|滚动次数)');
insert into sys_dict_data values(3028, 29,  'Longpressxy长按指定坐标(参数 X坐标|Y坐标|持续时间(可选))', 'longpressxy',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '长按指定坐标(参数 X坐标|Y坐标|持续时间(可选))');
insert into sys_dict_data values(3029, 30,  'Pressxy点击指定坐标(参数 X坐标|Y坐标)', 'pressxy',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '点击指定坐标(参数 X坐标|Y坐标)');
insert into sys_dict_data values(3030, 31,  'Tapxy轻击指定坐标(参数 X坐标|Y坐标)', 'tapxy',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '轻击指定坐标(参数 X坐标|Y坐标)');
insert into sys_dict_data values(3031, 32,  'JspressxyS方式点击指定坐标(参数 X坐标|Y坐标|持续时间(可选))', 'jspressxy',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', 'JS方式点击指定坐标(参数 X坐标|Y坐标|持续时间(可选))');
insert into sys_dict_data values(3032, 33,  'Moveto拖动坐标(参数 startX,startY|X,Y|X,Y|X,Y...)', 'moveto',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '拖动坐标(参数 startX,startY|X,Y|X,Y|X,Y...)');
insert into sys_dict_data values(3033, 34,  'Screenshot保存当前页面截图', 'screenshot',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '保存当前页面截图');
insert into sys_dict_data values(3034, 35,  'Timeout设置全局页面加载&元素出现最大等待时间(S)', 'timeout',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '设置全局页面加载&元素出现最大等待时间(S)');
insert into sys_dict_data values(3035, 36,  'HideKeyboard隐藏系统手机键盘', 'hideKeyboard',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '隐藏系统手机键盘');
insert into sys_dict_data values(3036, 37,  'Runcase调用指定接口用例', 'runcase',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '调用指定接口用例');
insert into sys_dict_data values(3037, 38,  'ExAdbShell执行安卓adb命令', 'exAdbShell',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '执行安卓adb命令');


-- ----------------------------
-- 23、参数配置表
-- ----------------------------
drop table if exists sys_config;
create table sys_config (
	config_id 		   int(5) 	     not null auto_increment    comment '参数主键',
	config_name        varchar(100)  default ''                 comment '参数名称',
	config_key         varchar(100)  default ''                 comment '参数键名',
	config_value       varchar(100)  default ''                 comment '参数键值',
	config_type        char(1)       default 'N'                comment '系统内置（Y是 N否）',
    create_by          varchar(64)   default ''                 comment '创建者',
    create_time 	   datetime                                 comment '创建时间',
    update_by          varchar(64)   default ''                 comment '更新者',
    update_time        datetime                                 comment '更新时间',
	remark 	           varchar(500)  default null 				comment '备注',
	primary key (config_id)
) engine=innodb auto_increment=100 default charset=utf8 comment = '参数配置表';

insert into sys_config values(1, '主框架页-默认皮肤样式名称', 'sys.index.skinName',     'skin-blue',     'Y', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow' );
insert into sys_config values(2, '用户管理-账号初始密码',     'sys.user.initPassword',  '123456',        'Y', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '初始化密码 123456' );


-- ----------------------------
-- 24、系统访问记录
-- ----------------------------
drop table if exists sys_logininfor;
create table sys_logininfor (
  info_id 		 int(11) 	   not null auto_increment   comment '访问ID',
  login_name 	 varchar(50)   default '' 			     comment '登录账号',
  ipaddr 		 varchar(50)   default '' 			     comment '登录IP地址',
  login_location varchar(255)  default ''                comment '登录地点',
  browser  		 varchar(50)   default '' 			     comment '浏览器类型',
  os      		 varchar(50)   default '' 			     comment '操作系统',
  status 		 char(1) 	   default '0' 			     comment '登录状态（0成功 1失败）',
  msg      		 varchar(255)  default '' 			     comment '提示消息',
  login_time 	 datetime                                comment '访问时间',
  primary key (info_id)
) engine=innodb auto_increment=100 default charset=utf8 comment = '系统访问记录';


-- ----------------------------
-- 25、在线用户记录
-- ----------------------------
drop table if exists sys_user_online;
create table sys_user_online (
  sessionId 	    varchar(50)  default ''              	comment '用户会话id',
  login_name 	    varchar(50)  default '' 		 	 	comment '登录账号',
  dept_name 		varchar(50)  default '' 		 	 	comment '部门名称',
  ipaddr 		    varchar(50)  default '' 			 	comment '登录IP地址',
  login_location    varchar(255) default ''                 comment '登录地点',
  browser  		    varchar(50)  default '' 			 	comment '浏览器类型',
  os      		    varchar(50)  default '' 			 	comment '操作系统',
  status      	    varchar(10)  default '' 			 	comment '在线状态on_line在线off_line离线',
  start_timestamp 	datetime                                comment 'session创建时间',
  last_access_time  datetime                                comment 'session最后访问时间',
  expire_time 	    int(5) 		 default 0 			 	    comment '超时时间，单位为分钟',
  primary key (sessionId)
) engine=innodb default charset=utf8 comment = '在线用户记录';


-- ----------------------------
-- 26、定时任务调度表
-- ----------------------------
drop table if exists sys_job;
create table sys_job (
  job_id 		      int(11) 	    not null auto_increment    comment '调度任务ID',
  job_name            varchar(64)   default ''                 comment '调度任务名称',
  job_group           varchar(64)   default ''                 comment '调度任务组名',
  method_name         varchar(500)  default ''                 comment '调度任务方法',
  method_params       varchar(50)   default null               comment '方法参数',
  cron_expression     varchar(255)  default ''                 comment 'cron执行表达式',
  misfire_policy      varchar(20)   default '3'                comment '计划执行策略（1立即执行 2执行一次 3放弃执行）',
  status              char(1)       default '0'                comment '状态（0正常 1暂停）',
  create_by           varchar(64)   default ''                 comment '创建者',
  create_time         datetime                                 comment '创建时间',
  update_by           varchar(64)   default ''                 comment '更新者',
  update_time         datetime                                 comment '更新时间',
  remark              varchar(500)  default ''                 comment '备注信息',
  primary key (job_id, job_name, job_group)
) engine=innodb auto_increment=100 default charset=utf8 comment = '定时任务调度表';


-- ----------------------------
-- 27、定时任务调度日志表
-- ----------------------------
drop table if exists sys_job_log;
create table sys_job_log (
  job_log_id          int(11) 	    not null auto_increment    comment '任务日志ID',
  job_name            varchar(64)   not null                   comment '任务名称',
  job_group           varchar(64)   not null                   comment '任务组名',
  method_name         varchar(500)                             comment '任务方法',
  method_params       varchar(50)   default null               comment '方法参数',
  job_message         varchar(500)                             comment '日志信息',
  status              char(1)       default '0'                comment '执行状态（0正常 1失败）',
  exception_info      varchar(2000) default ''                 comment '异常信息',
  create_time         datetime                                 comment '创建时间',
  primary key (job_log_id)
) engine=innodb default charset=utf8 comment = '定时任务调度日志表';


-- ----------------------------
-- 28、通知公告表
-- ----------------------------
drop table if exists sys_notice;
create table sys_notice (
  notice_id 		int(4) 		    not null auto_increment    comment '公告ID',
  notice_title 		varchar(50) 	not null 				   comment '公告标题',
  notice_type 		char(1) 	    not null 			       comment '公告类型（1通知 2公告）',
  notice_content    varchar(2000)   default null               comment '公告内容',
  status 			char(1) 		default '0' 			   comment '公告状态（0正常 1关闭）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 		datetime                                   comment '创建时间',
  update_by 		varchar(64) 	default ''			       comment '更新者',
  update_time 		datetime                                   comment '更新时间',
  remark 			varchar(255) 	default null 			   comment '备注',
  primary key (notice_id)
) engine=innodb auto_increment=10 default charset=utf8 comment = '通知公告表';

-- ----------------------------
-- 初始化-公告信息表数据
-- ----------------------------
insert into sys_notice values('1', '温馨提醒：2018-07-01 LuckyFrame新版本发布啦', '2', '新版本内容', '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '管理员');
insert into sys_notice values('2', '维护通知：2018-07-01 LuckyFrame系统凌晨维护', '1', '维护内容',   '0', 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '管理员');


-- ----------------------------
-- 29、客户端管理表
-- ----------------------------
drop table if exists sys_client;
create table sys_client
(
  client_id       int(8)      not null AUTO_INCREMENT comment '客户端ID',
  job_id 		  int(11) 	  not null                comment '心跳任务ID',
  client_name     varchar(30) not null                comment '客户端名称',
  client_ip       varchar(30) not null                comment '客户端IP',
  status          int(2)                              comment '客户端状态 0 正常 1 链接失败 2 状态未知',
  checkinterval   int(6)      not null                comment '检查客户端状态心跳间隔时间 单位:秒',
  client_path     varchar(100)                        comment '客户端测试驱动桩路径 多个;做分隔',
  remark          varchar(100) comment '备注',
  primary key (client_id)
) engine=innodb auto_increment=10 default charset=utf8 comment = '客户端管理表';

-- ----------------------------
-- 30、客户端与项目关联表  客户端1-N项目
-- ----------------------------
drop table if exists sys_client_project;
create table sys_client_project (
  client_id 	int(8) not null comment '客户端ID',
  project_id 	int(8) not null comment '项目ID',
  primary key(client_id, project_id)
) engine=innodb default charset=utf8 comment = '客户端与项目关联表';

-- ----------------------------
-- 31、测试用例管理
-- ----------------------------
drop table if exists project_case;
create table project_case
(
  case_id               int(8)       not null AUTO_INCREMENT comment '测试用例ID',
  case_serial_number    int(8)       not null                comment '用例编号排序',
  case_sign             varchar(20)  not null                comment '用例标识',
  case_name             varchar(200) not null                comment '用例名称',
  project_id            int(8)       not null                comment '关联项目ID',
  module_id             int(8)       not null                comment '关联项目模块ID',
  case_type             int(2)       not null                comment '默认类型 0 HTTP接口 1 Web UI 2 API驱动  3移动端',
  failcontinue          int(2)       default 0               comment '前置步骤失败，后续步骤是否继续，0：中断，1：继续',
  create_by             varchar(64)  default ''              comment '创建者',
  create_time 		    datetime                             comment '创建时间',
  update_by 		    varchar(64)  default ''			     comment '更新者',
  update_time 		    datetime                             comment '更新时间',
  remark                varchar(200)                         comment '备注',
  primary key (case_id)
) engine=innodb default charset=utf8 comment = '项目测试用例管理';

-- ----------------------------
-- 32、测试用例步骤管理
-- ----------------------------
drop table if exists project_case_steps;
create table project_case_steps
(
  step_id               int(8)       not null AUTO_INCREMENT  comment '步骤ID',
  case_id               int(8)       not null                 comment '用例ID',
  project_id            int(8)       not null                 comment '项目ID',
  step_serial_number    int(2)       not null                 comment '步骤序号',
  step_path             varchar(500)                          comment '包路径|定位路径',
  step_operation        varchar(100)                          comment '方法名|操作',
  step_parameters       varchar(500)                          comment '参数',
  action                varchar(50)                           comment '步骤动作',
  expected_result       varchar(2000)                         comment '预期结果',
  step_type             int(2)       not null                 comment '默认类型 0 HTTP接口 1 Web UI 2 API驱动  3移动端',
  extend                varchar(200)                          comment '扩展字段，可用于备注、存储HTTP模板等',
  create_by             varchar(64)  default ''               comment '创建者',
  create_time 		    datetime                              comment '创建时间',
  update_by 		    varchar(64)  default ''			      comment '更新者',
  update_time 		    datetime                              comment '更新时间',
  primary key (step_id)
) engine=innodb default charset=utf8 comment = '测试用例步骤管理';

-- ----------------------------
-- 33、用例模块管理
-- ----------------------------
drop table if exists project_case_module;
create table project_case_module
(
  module_id             int(8)          not null AUTO_INCREMENT comment '模块ID',
  module_name           varchar(50)                             comment '模块名称',
  project_id            int(8)          not null                comment '项目ID',
  parent_id 		    int(11) 		default 0 	            comment '父模块id',
  ancestors 		    varchar(100)    default ''              comment '祖模块列表',
  order_num 		    int(4) 			default 0 		        comment '显示顺序',
  create_by             varchar(64)     default ''              comment '创建者',
  create_time 	        datetime                                comment '创建时间',
  update_by             varchar(64)     default ''              comment '更新者',
  update_time           datetime                                comment '更新时间',
  remark                varchar(200)                            comment '备注',
  primary key (module_id)
) engine=innodb default charset=utf8 comment = '测试用例模块管理';

-- ----------------------------
-- 初始化-用例模块数据
-- ----------------------------
insert into project_case_module values (1, '测试项目一', 1, 0, 0, 0, 'admin', '2019-02-13 10-27-32', 'luckyframe', '2019-02-13 10-27-32', '初始化数据');

-- ----------------------------
-- 34、协议模板管理
-- ----------------------------
drop table if exists project_protocol_template;
create table project_protocol_template
(
  template_id           int(8)          not null AUTO_INCREMENT comment '模板ID',
  template_name         varchar(50)     not null                comment '模板名称',
  project_id            int(8)          not null                comment '项目ID',
  head_msg              varchar(3000)                           comment '消息头',
  cerificate_path       varchar(300)                            comment '客户端中的证书路径',
  encoding              varchar(20)     not null                comment '编码格式',
  timeout               int(8)          not null                comment '超时时间',
  is_response_head      int(2)          not null                comment '请求响应返回值是否带头域信息 0不带 1带',
  is_response_code      int(2)          not null                comment '请求响应返回值是否带状态码 0不带 1带',
  create_by             varchar(64)     default ''              comment '创建者',
  create_time 	        datetime                                comment '创建时间',
  update_by             varchar(64)     default ''              comment '更新者',
  update_time           datetime                                comment '更新时间',
  remark                varchar(200)                            comment '备注',
  primary key (template_id)
) engine=innodb default charset=utf8 comment = '协议模板管理';

-- ----------------------------
-- 35、协议模板参数管理
-- ----------------------------
drop table if exists project_template_params;
create table project_template_params
(
  params_id             int(9)          not null AUTO_INCREMENT comment '模板参数ID',
  template_id           int(8)          not null                comment '模板ID',
  param_name            varchar(50)     not null                comment '参数名',
  param_value           varchar(5000)                           comment '参数默认值',
  param_type            int(4)          not null                comment '0 String 1 JSON对象 2 JSONARR对象 3 文件类型 4数字类型 5 布尔类型',
  primary key (params_id)
) engine=innodb default charset=utf8 comment = '模板参数管理';

-- ----------------------------
-- 36、用例调试记录
-- ----------------------------
drop table if exists project_case_debug;
create table project_case_debug
(
  debug_id              int(8)          not null AUTO_INCREMENT  comment '调试ID',
  case_id               int(8)          not null                 comment '用例ID',
  user_id               int(11) 		not null                 comment '用户ID',
  debug_isend           int(2) 		    not null                 comment '调试结束标识 0 进行中 1结束 2异常',
  log_level             varchar(10)     not null                 comment '日志级别 info 记录 warning 警告 error 异常',
  log_detail            varchar(5000)   not null                 comment '日志',
  primary key (debug_id)
) engine=innodb default charset=utf8 comment = '用例调试日志记录';

-- ----------------------------
-- 37、测试计划
-- ----------------------------
drop table if exists project_plan;
create table project_plan
(
  plan_id               int(9)          not null AUTO_INCREMENT  comment '测试计划ID',
  plan_name             varchar(50)     not null                 comment '测试计划名称',
  plan_case_count       int(8)                                   comment '计划中用例总数',
  project_id            int(8)          not null                 comment '项目ID',
  create_by             varchar(64)     default ''               comment '创建者',
  create_time 	        datetime                                 comment '创建时间',
  update_by             varchar(64)     default ''               comment '更新者',
  update_time           datetime                                 comment '更新时间',
  remark                varchar(200)                             comment '备注',
  primary key (plan_id)
) engine=innodb default charset=utf8 comment = '测试计划';

-- ----------------------------
-- 38、测试计划用例
-- ----------------------------
drop table if exists project_plan_case;
create table project_plan_case
(
  plan_case_id          int(9)          not null AUTO_INCREMENT  comment '计划用例ID',
  case_id               int(8)          not null                 comment '用例ID',
  plan_id               int(8)          not null                 comment '测试计划ID',
  priority              int(8)          not null                 comment '用例优先级 数字越小，优先级越高',
  primary key (plan_case_id),
  index (case_id),
  index (plan_id) 
) engine=innodb default charset=utf8 comment = '测试计划用例';

-- ----------------------------
-- 39、用例公共参数
-- ----------------------------
drop table if exists project_case_params;
create table project_case_params
(
  params_id             int(8)          not null AUTO_INCREMENT  comment '用例参数ID',
  params_name           varchar(50)     not null                 comment '参数名称',
  params_value          varchar(500)    not null                 comment '参数值',
  project_id            int(8)          not null                 comment '项目ID',
  create_by             varchar(64)     default ''               comment '创建者',
  create_time 	        datetime                                 comment '创建时间',
  update_by             varchar(64)     default ''               comment '更新者',
  update_time           datetime                                 comment '更新时间',
  remark                varchar(200)                             comment '备注',
  primary key (params_id)
) engine=innodb default charset=utf8 comment = '用例公共参数';

-- ----------------------------
-- 40、测试任务调度
-- ----------------------------
drop table if exists task_scheduling;
create table task_scheduling
(
  scheduling_id         int(8)         not null AUTO_INCREMENT  comment '调度ID',
  scheduling_name       varchar(50)     not null                 comment '调度名称',
  job_id 		        int(11) 	    not null                 comment '调度任务ID',
  project_id            int(8)          not null                 comment '项目ID',
  plan_id               int(9)          not null                 comment '测试计划ID', 
  client_id             int(8)          not null                 comment '客户端ID',
  email_send_condition  int(2)          default 0                comment '发送邮件通知时的具体逻辑, -1-不通知 0-全部，1-成功，2-失败',
  email_address         varchar(300)                             comment '邮件通知地址',
  building_link         varchar(200)                             comment 'jenkins构建链接',
  remote_shell          varchar(200)                             comment '远程执行Shell脚本',
  ex_thread_count       int(8)          not null                 comment '客户端执行线程数',
  task_type             int(2)          not null default 0       comment '任务类型 0 接口 1 Web UI 2 移动',
  browser_type          int(2)                                   comment 'UI自动化浏览器类型 0 IE 1 火狐 2 谷歌 3 Edge',
  task_timeout          int(8)          not null default 60      comment '任务超时时间(分钟)',
  client_driver_path    varchar(100)                             comment '客户端测试驱动桩路径',
  primary key (scheduling_id)
) engine=innodb default charset=utf8 comment = '测试任务调度';

-- ----------------------------
-- 41、测试任务执行
-- ----------------------------
drop table if exists task_execute;
create table task_execute
(
  task_id               int(8)         not null AUTO_INCREMENT  comment '任务ID',
  scheduling_id         int(8)         not null                 comment '调度ID',
  project_id            int(8)         not null                 comment '项目ID',
  task_name             varchar(150)   not null                 comment '任务名称',
  task_status           int(2)         default 0                comment '状态 0未执行 1执行中 2执行完成 3任务超时中断 4唤起客户端失败',
  case_total_count      int(8)         default 0                comment '总用例数',
  case_succ_count       int(8)         default 0                comment '成功数',
  case_fail_count       int(8)         default 0                comment '失败数',
  case_lock_count       int(8)         default 0                comment '锁定数',
  case_noexec_count     int(8)         default 0                comment '未执行用例',
  finish_time           datetime                                comment '任务结束时间',
  create_by             varchar(64)    default ''               comment '创建者',
  create_time 	        datetime                                comment '创建时间',
  update_by             varchar(64)    default ''               comment '更新者',
  update_time           datetime                                comment '更新时间',
  primary key (task_id),
  index (scheduling_id)
) engine=innodb default charset=utf8 comment = '测试任务执行';

-- ----------------------------
-- 42、用例执行情况
-- ----------------------------
drop table if exists task_case_execute;
create table task_case_execute
(
  task_case_id          int(8)         not null AUTO_INCREMENT  comment '用例执行ID',
  task_id               int(8)         not null                 comment '任务ID',
  project_id            int(8)         not null                 comment '项目ID',
  case_id               int(8)         not null                 comment '用例ID',
  case_sign             varchar(20)    not null                 comment '用例标识',
  case_name             varchar(200)   not null                 comment '用例名称',
  case_status           int(2)         not null default 4       comment '用例执行状态 0成功 1失败 2锁定 3执行中 4未执行',
  finish_time           datetime                                comment '用例结束时间',
  create_by             varchar(64)    default ''               comment '创建者',
  create_time 	        datetime                                comment '创建时间',
  update_by             varchar(64)    default ''               comment '更新者',
  update_time           datetime                                comment '更新时间',
  primary key (task_case_id),
  index (task_id),
  index (case_id)
) engine=innodb default charset=utf8 comment = '任务用例执行记录';

-- ----------------------------
-- 43、用例日志明细
-- ----------------------------
drop table if exists task_case_log;
create table task_case_log
(
  log_id                int(8)         not null AUTO_INCREMENT  comment '日志ID',
  task_case_id          int(8)         not null                 comment '用例执行ID',
  task_id               int(8)         not null                 comment '任务ID',
  log_detail            varchar(5000)  not null                 comment '日志明细',
  log_grade             varchar(20)                             comment '日志级别',
  log_step              varchar(20)                             comment '日志用例步骤',
  imgname               varchar(50)                             comment 'UI自动化自动截图地址',
  create_time 	        datetime                                comment '创建时间',
  primary key (log_id),
  index (task_case_id),
  index (task_id)
) engine=innodb default charset=utf8 comment = '用例日志明细';