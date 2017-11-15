create table OPERATION_LOG
(
  id                    int(9) not null AUTO_INCREMENT,
  tablename             VARCHAR(30) not null COMMENT '被记录日志表名',
  tableid               int(8) not null COMMENT '被记录日志ID',
  operation_time        VARCHAR(30) not null COMMENT '操作时间',
  operationer           VARCHAR(20) not null COMMENT '操作人员',
  operation_description VARCHAR(200) not null COMMENT '描述',
  projectid             int(8) COMMENT '项目ID',
  primary key (ID)
)default character set utf8;
create table QA_ACCIDENT
(
  id                  int(8) not null AUTO_INCREMENT,
  projectid           int(8) not null COMMENT '项目ID',
  accstatus           VARCHAR(80) not null COMMENT '事故状态',
  eventtime           VARCHAR(30) COMMENT '事故发生时间',
  reporter            VARCHAR(20) not null COMMENT '报告人',
  reporttime          VARCHAR(30) not null COMMENT '事故报告时间',
  accdescription      VARCHAR(500) not null COMMENT '事故描述',
  acclevel            VARCHAR(60) not null COMMENT '事故等级',
  causalanalysis      VARCHAR(500) COMMENT '原因分析',
  causaltype          VARCHAR(100) COMMENT '原因类型',
  consequenceanalysis VARCHAR(300) COMMENT '后果分析',
  correctiveaction    VARCHAR(300) COMMENT '纠正措施',
  resolutiontime      VARCHAR(30) COMMENT '解决时间',
  resolutioner        VARCHAR(20) COMMENT '解决处理人',
  preventiveaction    VARCHAR(300) COMMENT '预防措施',
  preventiver         VARCHAR(20) COMMENT '预防措施责任人',
  preventiveplandate  VARCHAR(30) COMMENT '预防措施计划完成时间',
  preventiveaccdate   VARCHAR(30) COMMENT '预防措施实际完成时间',
  trouble_duration    int(8) COMMENT '事故持续时间',
  impact_time         int(8),
  filename            VARCHAR(100) COMMENT '附件路径',
  primary key (ID)
)default character set utf8;
create table QA_FLOWCHECK
(
  id                int(10) not null AUTO_INCREMENT,
  checkid           int(10) not null COMMENT '检查id',
  projectid         int(10) not null COMMENT '项目id',
  projectphase      VARCHAR(20) not null COMMENT '项目阶段',
  phasenode         VARCHAR(50) not null COMMENT '阶段节点',
  checkentry        VARCHAR(100) not null COMMENT '检查内容',
  checkresult       VARCHAR(20) COMMENT '检查结果',
  checkdate         VARCHAR(20) COMMENT '检查日期',
  checkdescriptions VARCHAR(400) COMMENT '不符合项描述',
  stateupdate       VARCHAR(20) COMMENT '状态更新',
  updatedate        VARCHAR(20) COMMENT '更新日期',
  remark            VARCHAR(200) COMMENT '备注',
  versionnum        VARCHAR(30) COMMENT '版本号',
  primary key (ID)
)default character set utf8;
create table QA_FLOWINFO
(
  id            int(10) not null AUTO_INCREMENT,
  phaseid       int(10) not null,
  phasename     VARCHAR(20) not null,
  phasenodeid   int(10) not null,
  phasenodename VARCHAR(20) not null,
  checkentryid  int(10) not null,
  checkentry    VARCHAR(200) not null,
  remark        VARCHAR(100),
   primary key (ID)
)default character set utf8;
create table QA_PLANFLOWCHECK
(
  id           int(8) not null AUTO_INCREMENT,
  projectid    int(8) not null COMMENT '项目ID',
  versionnum   VARCHAR(30) COMMENT '版本号',
  checkentryid VARCHAR(20) not null COMMENT '检查ID',
  plandate     VARCHAR(20) not null COMMENT '计划检查日期',
  status       int(4) not null COMMENT '计划状态：1 检查计划 2 已检查',
   primary key (ID)
)default character set utf8;
create table QA_PROJECTVERSION
(
  versionid           int(10) not null AUTO_INCREMENT,
  versionnumber       VARCHAR(20) COMMENT '版本号',
  plan_launchdate     VARCHAR(20) COMMENT '计划上线日期',
  actually_launchdate VARCHAR(20) COMMENT '实际上线日期',
  plan_devstart       VARCHAR(20) COMMENT '计划开发开始时间',
  plan_devend         VARCHAR(20) COMMENT '计划开发结束时间',
  actually_devstart   VARCHAR(20) COMMENT '实际开发开始时间',
  actually_devend     VARCHAR(20) COMMENT '实际开发结束时间',
  plan_teststart      VARCHAR(20) COMMENT '计划测试开始时间',
  plan_testend        VARCHAR(20) COMMENT '计划测试结束时间',
  actually_teststart  VARCHAR(20) COMMENT '实际测试开始时间',
  actually_testend    VARCHAR(20) COMMENT '实际测试结束时间',
  plan_demand         int(10) COMMENT '计划上线需求数',
  actually_demand     int(10) COMMENT '实际上线需求数',
  codeline            FLOAT(10) COMMENT '代码变动行数',
  testcasenum         int(10) COMMENT '测试用例数',
  changetestingreturn int(10) COMMENT '版本转测试打回次数',
  dev_member          VARCHAR(50) COMMENT '开发投入人力',
  test_member         VARCHAR(50) COMMENT '测试投入人力',
  human_cost          VARCHAR(20) COMMENT '耗费人力 人/天',
  per_dev             VARCHAR(20) COMMENT '开发平均生产率  人/天/行',
  per_test            VARCHAR(20) COMMENT '用例编写/用例执行',
  code_di             VARCHAR(20) COMMENT '代码DI值',
  qualityreview       VARCHAR(500) COMMENT '质量回溯',
  imprint             VARCHAR(500) COMMENT '版本说明',
  remark              VARCHAR(500) COMMENT '备注',
  projectid           int(10) COMMENT '项目ID',
  devtime_deviation   VARCHAR(20) COMMENT '开发偏移率',
  devdelay_days       VARCHAR(20) COMMENT '开发延迟时间',
  testtime_deviation  VARCHAR(20) COMMENT '测试偏移率',
  testdelay_days      VARCHAR(20) COMMENT '测试延迟时间',
  protime_deviation   VARCHAR(20),
  prodelay_days       VARCHAR(20),
  bug_zm              int(10),
  bug_yz              int(10),
  bug_yb              int(10),
  bug_ts              int(10),
  versiontype         int(8) default 1 not null COMMENT '版本类型',
  perdemand           FLOAT(8) default 0.00,
  codestandard_zd     int(10) default 0 not null,
  codestandard_yz     int(10) default 0 not null,
  codestandard_zy     int(10) default 0 not null,
  zt_versionlink      VARCHAR(100) COMMENT '禅道链接',
  human_costdev       VARCHAR(20) default '0' COMMENT '开发人力成本',
  human_costtest      VARCHAR(20) default '0' COMMENT '测试人力成本',
  primary key (versionid)
)default character set utf8;
create table QA_REVIEW
(
  id               int(8) not null AUTO_INCREMENT,
  projectid        int(8) not null COMMENT '项目ID',
  version          VARCHAR(20) not null COMMENT '版本号',
  review_type      VARCHAR(20) COMMENT '评审类型',
  review_date      VARCHAR(20) COMMENT '评审日期',
  bug_num          int(8) COMMENT '问题数',
  repair_num       int(8) COMMENT '已修复问题数',
  confirm_date     VARCHAR(20) COMMENT '最后确认日期',
  review_object    VARCHAR(100) COMMENT '评审对象',
  review_result    VARCHAR(50) COMMENT '评审结果',
  result_confirmor VARCHAR(50) COMMENT '结果确认人',
  remark           VARCHAR(500) COMMENT '备注',
   primary key (id)
)default character set utf8;
create table QA_REVIEWINFO
(
  id              int(8) not null AUTO_INCREMENT,
  review_id       int(8) not null COMMENT '评审ID',
  bug_description VARCHAR(500) COMMENT '问题描述',
  status          VARCHAR(20) COMMENT '状态',
  duty_officer    VARCHAR(100) COMMENT '责任人',
  confirm_date    VARCHAR(20) COMMENT '最后确认日期',
  corrective      VARCHAR(500) COMMENT '纠正措施',
  primary key (id)
)default character set utf8;
create table QA_SECONDARYSECTOR
(
  sectorid       int(10) not null AUTO_INCREMENT,
  departmenthead VARCHAR(20),
  departmentname VARCHAR(20),
  primary key (sectorid)
)default character set utf8;
create table QA_SECTORPROJECTS
(
  projectid      int(10) not null AUTO_INCREMENT,
  projectname    VARCHAR(100) not null,
  projectmanager VARCHAR(100) not null,
  sectorid       int(10) not null,
  projecttype    int(4) default 0 not null,
  primary key (projectid)
)default character set utf8;
create table QA_ZTTASK
(
  id           int(8) not null AUTO_INCREMENT,
  versionid    int(8) COMMENT '版本ID',
  versionname  VARCHAR(100) COMMENT '版本名称',
  taskname     VARCHAR(300) COMMENT '任务名称',
  assigneddate VARCHAR(50) COMMENT '完成时间',
  estimate     int(8) COMMENT '预期工时',
  consumed     int(8) COMMENT '实际工时',
  finishedby   VARCHAR(40) COMMENT '完成人账号',
  finishedname VARCHAR(40) COMMENT '完成人姓名',
  deadline     VARCHAR(40) COMMENT '计划截止日期',
  delaystatus  int(4) COMMENT '延期状态',
   primary key (id)
)default character set utf8;
create table TEST_JOBS
(
  id           int(10) not null AUTO_INCREMENT,
  name         VARCHAR(100) not null COMMENT '调度名称',
  startdate    VARCHAR(100) COMMENT '开始日期',
  starttime    VARCHAR(100) COMMENT '开始时间',
  enddate      VARCHAR(100) COMMENT '结束日期',
  endtime      VARCHAR(100) COMMENT '结束时间',
  runtime      datetime COMMENT '执行时间',
  remark       VARCHAR(800) COMMENT '备注',
  planproj     VARCHAR(100) COMMENT '项目名（testlink）中',
  state        VARCHAR(4) not null COMMENT '状态',
  tasktype     VARCHAR(4) not null COMMENT '执行类型 O执行一次 D每天执行',
  starttimestr VARCHAR(100) COMMENT '执行时间表达式',
  endtimestr   VARCHAR(100) COMMENT '结束时间',
  createtime   datetime not null,
  noenddate    VARCHAR(10) COMMENT '是否有结束日期',
  time         int default 0,
  timetype     VARCHAR(4),
  issendmail   VARCHAR(4) default "0" COMMENT '是否发送邮件通知',
  emailer      VARCHAR(250) COMMENT '邮件地址',
  threadcount  int default 1 COMMENT '客户端线程数(接口)',
  testlinkname VARCHAR(200) COMMENT 'TESTLINK中的计划名称',
  isbuilding   VARCHAR(4) default '0' not null COMMENT '是否自动构建',
  buildname    VARCHAR(100) COMMENT '构建链接',
  isrestart    VARCHAR(4) default '0' not null COMMENT '是否自动重启',
  restartcomm  VARCHAR(200) COMMENT '重启脚本',
  extype       int(4) default 0 not null COMMENT '执行类型',
  browsertype  int(4) COMMENT 'UI自动化浏览器类型',
  timeout      int(8) default 60 not null COMMENT '任务超时中断时间(分钟)',
  clientip     VARCHAR(30) default '127.0.0.1' not null COMMENT '版本ID',
  primary key (id)
)default character set utf8;
create table TEST_CASEDETAIL
(
  id          int(10) not null AUTO_INCREMENT,
  taskid      int(10) not null,
  caseno      VARCHAR(20) not null COMMENT '用例编号',
  caseversion VARCHAR(20) not null COMMENT '用例版本',
  casetime    datetime not null COMMENT '执行时间',
  casename    VARCHAR(200) not null COMMENT '用例名称',
  casestatus  VARCHAR(2) default '4' not null COMMENT ' pass:0    fail:1   lock:2   unexcute:4',
  primary key (id)
)default character set utf8;
create table TEST_LOGDETAIL
(
  logid    int(10) not null AUTO_INCREMENT,
  logtime  datetime not null COMMENT '日志时间',
  detail   VARCHAR(5000) not null COMMENT '日志',   /*V1.1  扩展日志明细字段到2000*/
  loggrade VARCHAR(20) COMMENT '日志级别',
  caseid   int(10) not null COMMENT '用例编号',
  taskid   int(10),
  step     VARCHAR(20) COMMENT '用例步骤',
  imgname  VARCHAR(30) COMMENT 'UI自动化自动截图地址',
  primary key (logid)
)default character set utf8;
create table TEST_TASKEXCUTE
(
  id               int(10) not null AUTO_INCREMENT,
  taskid           VARCHAR(150) not null,
  casetotal_count  int(10) COMMENT '总用例数',
  casesucc_count   int(10) COMMENT '成功数',
  casefail_count   int(10) COMMENT '失败数',
  caselock_count   int(10) COMMENT '锁定数',
  createtime       datetime COMMENT '时间',
  jobid            int(10) not null,
  finishtime       datetime COMMENT '完成时间',
  taskstatus       VARCHAR(2) default '' COMMENT '状态 0未执行 1执行中 2 成功 4失败 ',
  casenoexec_count int default 0 COMMENT '未执行用例',
  caseisexec       VARCHAR(2) COMMENT '任务是否被成功吊起 0 成功吊起  3  吊起失败',
    primary key (id)
)default character set utf8;
create table USERINFO
(
  id       int(8) not null AUTO_INCREMENT,
  usercode VARCHAR(20) not null,
  password VARCHAR(200) not null,
  username VARCHAR(20),
  role     VARCHAR(100) COMMENT '角色',
  sectorid int(8) default 0 not null COMMENT '所属部门',
  primary key (id)
)default character set utf8;

create table USER_AUTHORITY
(
  id        int(9) not null AUTO_INCREMENT,
  module    VARCHAR(50) not null COMMENT '模块',
  auth_type VARCHAR(50) not null COMMENT '权限类型',
  alias     VARCHAR(50) not null COMMENT '别名',
  primary key (id)
)default character set utf8;
create table USER_ROLE
(
  id         int(9) not null AUTO_INCREMENT,
  role       VARCHAR(50) not null COMMENT '角色',
  permission VARCHAR(1000) COMMENT '权限',
  primary key (id)
)default character set utf8;

/*插入索引
CREATE INDEX index_caseid ON TEST_LOGDETAIL (caseid);
CREATE INDEX index_taskid ON TEST_CASEDETAIL (taskid);*/

/*插入权限默认定义*/
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (1, '质量-版本信息', '增加', 'pv_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (2, '质量-版本信息', '删除', 'pv_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (3, '质量-版本信息', '修改', 'pv_3');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (4, '质量-版本计划信息', '增加', 'pvp_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (5, '质量-流程检查信息', '增加', 'fc_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (6, '质量-流程检查信息', '删除', 'fc_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (7, '质量-流程检查信息', '修改', 'fc_3');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (8, '质量-生产故障信息', '增加', 'acc_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (9, '质量-生产故障信息', '删除', 'acc_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (10, '质量-生产故障信息', '修改', 'acc_3');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (21, '自动化-任务调度', '启动', 'tast_run');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (22, '自动化-任务调度', '移除', 'tast_remove');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (23, '自动化-任务调度', '执行', 'tast_ex');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (24, '自动化-任务调度', '增加', 'tast_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (25, '自动化-任务调度', '删除', 'tast_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (26, '自动化-任务调度', '修改', 'tast_3');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (27, '自动化-任务调度', '上传', 'tast_upload');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (11, '质量-流程检查计划', '增加', 'pfc_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (12, '质量-流程检查计划', '删除', 'pfc_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (13, '质量-流程检查计划', '修改', 'pfc_3');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (14, '质量-流程检查计划', '转计划', 'fc_tocheck');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (15, '质量-评审信息详情', '增加', 'revinfo_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (16, '质量-评审信息详情', '删除', 'revinfo_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (17, '质量-评审信息详情', '修改', 'revinfo_3');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (18, '质量-评审信息', '删除', 'rev_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (19, '质量-评审信息', '修改', 'rev_3');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (20, '质量-生产故障信息', '上传', 'acc_upload');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (41, '用户管理', '增加', 'ui_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (42, '用户管理', '删除', 'ui_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (43, '用户管理', '修改', 'ui_3');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (44, '用户管理', '查看', 'ui_4');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (45, '用户管理-权限角色', '查看修改', 'role_3');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (28, '自动化-任务列表', '删除', 'tastex_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (29, '自动化-用例列表', '执行', 'case_ex');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (46, '用户管理-权限角色', '增加', 'role_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (47, '用户管理-权限角色', '删除', 'role_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (48, '项目管理', '增加', 'pro_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (49, '项目管理', '删除', 'pro_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (50, '项目管理', '修改', 'pro_3');

/*插入角色默认定义*/
insert into USER_ROLE (id, role, permission)
values (2, '测试工程师', 'acc_1,acc_2,acc_3,');
insert into USER_ROLE (id, role, permission)
values (3, '自动化工程师', 'acc_1,acc_2,acc_3,tast_run,tast_remove,tast_ex,tast_1,tast_3,tast_2,tast_upload,tastex_2,case_ex,');
insert into USER_ROLE (id, role, permission)
values (4, '质量工程师', 'pv_1,pv_2,pv_3,pvp_1,fc_1,fc_2,fc_3,acc_1,acc_2,acc_3,pfc_1,pfc_2,pfc_3,fc_tocheck,revinfo_1,revinfo_2,revinfo_3,rev_2,rev_3,acc_upload,');
insert into USER_ROLE (id, role, permission)
values (1, '管理员', 'pv_1,pv_2,pv_3,pvp_1,fc_1,fc_2,fc_3,acc_1,acc_2,acc_3,tast_run,tast_remove,tast_ex,tast_1,tast_3,tast_2,tast_upload,tastex_2,case_ex,pfc_1,pfc_2,pfc_3,fc_tocheck,revinfo_1,revinfo_2,revinfo_3,rev_2,rev_3,acc_upload,ui_1,ui_2,ui_3,ui_4,role_3,role_1,role_2,');

/*插入默认部门分级*/
insert into QA_SECONDARYSECTOR (sectorid, departmenthead, departmentname)
values (99, '系统管理员', '总部');
/*插入默认项目*/
insert into QA_SECTORPROJECTS (projectid, projectname, projectmanager, sectorid, projecttype)
values (99, '全部项目/未知项目', '系统管理员', 99, 0);
/*插入默认管理员*/
insert into USERINFO (id, usercode, password, username, role, sectorid)
values (1, 'admin', 'uJ45aIeS9N80kaSFDjvk%2FA%3D%3D', '系统管理员', '1', 99);
/*插入流程检查定义数据*/
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (36, 5, '总结阶段', 2, '计划变更', 1, '如与计划不合，是否提交计划变更邮件', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (37, 5, '总结阶段', 3, '代码质量', 1, 'sonar代码检查是否刷新构建', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (38, 5, '总结阶段', 3, '代码质量', 2, 'sonar代码问题数是否符合要求', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (35, 2, '开发阶段', 3, '开发与编码', 6, '是否每一条禅道开发任务都提交转测申请', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (34, 4, '验收与上线', 2, '上线部署', 3, 'SVN代码是否提交并标注', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (1, 1, '需求阶段', 1, '需求编制', 1, '《产品需求说明书》是否组织评审会', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (2, 1, '需求阶段', 1, '需求编制', 2, '《产品需求说明书》评审是否通过', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (3, 1, '需求阶段', 1, '需求编制', 3, '《产品需求说明书》是否归档到SVN', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (4, 1, '需求阶段', 1, '需求编制', 4, '《产品需求说明书》评审记录表是否签字通过', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (5, 2, '开发阶段', 1, '需求分解', 1, '是否有需求分解/任务讨论会', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (6, 2, '开发阶段', 1, '需求分解', 2, '《项目计划》邮件是否及时发出', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (7, 2, '开发阶段', 1, '需求分解', 3, '《项目计划》邮件内容是否完整', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (10, 2, '开发阶段', 2, '软件设计', 1, '《产品开发概要设计》是否组织评审会', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (11, 2, '开发阶段', 2, '软件设计', 2, '《产品开发概要设计》评审是否通过', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (12, 2, '开发阶段', 2, '软件设计', 3, '《产品开发概要设计》是否归档到SVN', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (13, 2, '开发阶段', 2, '软件设计', 4, '《产品开发概要设计》评审记录表是否签字通过', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (14, 2, '开发阶段', 3, '开发与编码', 1, '是否进行每日晨会', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (15, 2, '开发阶段', 3, '开发与编码', 2, '是否进行代码走读', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (16, 2, '开发阶段', 3, '开发与编码', 3, '是否每一条禅道开发任务都提交转测申请', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (17, 2, '开发阶段', 3, '开发与编码', 4, '转测邮件是否合格', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (18, 2, '开发阶段', 3, '开发与编码', 5, '转测邮件打回次数为0', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (19, 3, '测试阶段', 1, '软件测试', 1, '是否回复测试计划', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (20, 3, '测试阶段', 1, '软件测试', 2, '是否回复测试用例', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (21, 3, '测试阶段', 1, '软件测试', 3, '测试用例是否通过评审/确认', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (22, 3, '测试阶段', 1, '软件测试', 4, '《软件测试报告》是否组织评审会', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (23, 3, '测试阶段', 1, '软件测试', 5, '《软件测试报告》是否评审通过', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (24, 3, '测试阶段', 1, '软件测试', 6, '《软件测试报告》/测试结果是否发出邮件', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (25, 3, '测试阶段', 1, '软件测试', 7, '《软件测试报告》评审记录表是否签字通过', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (26, 4, '验收与上线', 1, '产品验收', 1, '《产品验收报告》是否组织评审会', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (27, 4, '验收与上线', 1, '产品验收', 2, '《产品验收报告》是否评审通过', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (28, 4, '验收与上线', 1, '产品验收', 3, '《产品验收报告》是否归档到SVN', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (29, 4, '验收与上线', 1, '产品验收', 4, '《产品验收报告》评审记录表是否签字通过', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (30, 4, '验收与上线', 2, '上线部署', 1, '产品/开发是否提交上线流程（OA）', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (31, 4, '验收与上线', 2, '上线部署', 2, '开发是否提交上线相关资料', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (32, 5, '总结阶段', 1, '项目总结', 1, '《项目总结》邮件是否及时发出', null);
insert into QA_FLOWINFO (id, phaseid, phasename, phasenodeid, phasenodename, checkentryid, checkentry, remark)
values (33, 5, '总结阶段', 1, '项目总结', 2, '《项目总结》邮件内容是否完整', null);