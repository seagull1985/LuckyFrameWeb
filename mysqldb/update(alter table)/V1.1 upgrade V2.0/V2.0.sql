create table PROJECT_CASE
(
  id                    int(8) not null AUTO_INCREMENT,
  sign                  VARCHAR(20) not null COMMENT '用例标识',
  name                  VARCHAR(200) not null COMMENT '用例名称',
  projectid             int(8) not null COMMENT '关联项目ID',
  moduleid              int(8) not null COMMENT '关联项目模块ID',
  projectindex          int(8) not null COMMENT '项目用例编号',
  time                  VARCHAR(30) COMMENT '最后更新时间',
  operationer           VARCHAR(20) COMMENT '最后更新人员',
  casetype              int(2) not null COMMENT '0 接口 1 UI',
  remark                VARCHAR(200) COMMENT '备注',
  primary key (ID)
)default character set utf8;

create table PROJECT_CASESTEPS
(
  id                    int(8) not null AUTO_INCREMENT,
  path                  VARCHAR(100) COMMENT '包路径|定位路径',
  operation             VARCHAR(100) COMMENT '方法名|操作',
  parameters            VARCHAR(500) COMMENT '参数',
  action                VARCHAR(50) COMMENT '步骤动作',
  caseid                int(8) not null COMMENT '用例ID',
  stepnum               int(2) not null COMMENT '步骤编号',
  expectedresult        VARCHAR(2000) COMMENT '预期结果',
  projectid             int(8) not null COMMENT '项目ID',
  steptype              int(2) not null COMMENT '0 接口 1 UI',
  time                  VARCHAR(30)  COMMENT '最后更新时间',
  operationer           VARCHAR(20)  COMMENT '最后更新人员',
  remark                VARCHAR(200) COMMENT '备注',
  primary key (ID)
)default character set utf8;

create table PROJECT_PLAN
(
  id                    int(9) not null AUTO_INCREMENT,
  name                  VARCHAR(50) not null COMMENT '测试计划名称',
  casecount             int(8) COMMENT '计划中用例总数',
  remark                VARCHAR(200) COMMENT '备注',
  projectid             int(8) not null COMMENT '项目ID',
  time                  VARCHAR(30) COMMENT '最后更新时间',
  operationer           VARCHAR(20) COMMENT '最后更新人员',
  primary key (ID)
)default character set utf8;

create table PROJECT_PLANCASE
(
  id                    int(9) not null AUTO_INCREMENT,
  caseid                int(8) not null COMMENT '用例ID',
  planid                int(8) not null COMMENT '项目计划ID',
  priority              int(8) not null COMMENT '用例优先级',
  primary key (ID),
  index (caseid),
  index (planid) 
)default character set utf8;

create table PROJECT_MODULE
(
  id                    int(9) not null AUTO_INCREMENT,
  projectid             int(8) not null COMMENT '项目ID',
  modulename            VARCHAR(50) COMMENT '模块名字',
  pid          int(4) COMMENT '层级关系',
  primary key (ID)
)default character set utf8;

/*用户增加默认选择字段*/
alter table userinfo add projectid int(4) default '0';
/*项目管理表增加项目标识字段*/
alter table QA_SECTORPROJECTS add projectsign VARCHAR(20) default 'sign' not null;
/*调度任务配置增加三个支持内部项目字段*/
alter table TEST_JOBS add projecttype int(8) default '0' COMMENT '项目类型 0 testlink 1 系统内项目';
alter table TEST_JOBS add projectid int(8) COMMENT '系统内项目ID';
alter table TEST_JOBS add planid int(8) COMMENT '系统内项目关联测试计划ID';
/*增加权限标识*/
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (51, '用例管理', '增加', 'case_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (52, '用例管理', '删除', 'case_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (53, '用例管理', '修改', 'case_3');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (54, '步骤管理', '增删改查', 'case_step');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (55, '测试计划管理', '增加', 'proplan_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (56, '测试计划管理', '删除', 'proplan_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (57, '测试计划管理', '修改', 'proplan_3');