create table OPERATION_LOG
(
  id                    int(9) not null AUTO_INCREMENT,
  tablename             VARCHAR(30) not null COMMENT '被记录日志表名',
  tableid               int(8) not null COMMENT '被记录日志ID',
  operation_time        VARCHAR(30) not null COMMENT '操作时间',
  operation_integral    int(2) COMMENT '操作积分',
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
  projectsign    VARCHAR(20) default 'sign' not null,
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
  clientpath   VARCHAR(100) COMMENT '客户端测试驱动桩路径',
  projecttype  int(4) default "0" COMMENT '项目类型 系统内项目 0 testlink 1',
  projectid    int(8) COMMENT '系统内项目ID',
  planid       int(8) COMMENT '系统内项目关联测试计划ID',
  sendcondition int(4) default 0 COMMENT '发送邮件通知时的具体逻辑, 0-全部，1-成功，-1-失败',
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
  detail   VARCHAR(5000) not null COMMENT '日志',   /*V1.1 扩展日志明细字段到2000*/
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
  projectid int(4) default 0 not null COMMENT '默认选择项目',
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
  opprojectid VARCHAR(200) COMMENT '可操作的项目',
  primary key (id)
)default character set utf8;
create table TEST_CLIENT
(
  id       int(8) not null AUTO_INCREMENT,
  clientip  VARCHAR(30) not null COMMENT '客户端IP',
  name  VARCHAR(30) not null COMMENT '客户端名称',
  projectper  VARCHAR(200) COMMENT '使用项目',
  status  int(2) COMMENT '客户端超时 0 正常 1 链接失败 2 状态未知',
  checkinterval int(6) not null COMMENT '检查客户端状态心跳间隔时间 单位:秒',
  clientpath VARCHAR(100) COMMENT '客户端测试驱动桩路径 多个;做分隔',
  remark  VARCHAR(100) COMMENT '备注',
  primary key (id)
)default character set utf8;
/*用例管理模块*/
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
  failcontinue          int(2) DEFAULT 0 COMMENT '失败了是否继续，0：不继续，1：继续',
  primary key (ID)
)default character set utf8;

create table PROJECT_CASESTEPS
(
  id                    int(8) not null AUTO_INCREMENT,
  path                  VARCHAR(200) COMMENT '包路径|定位路径',
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
  pid                   int(4) COMMENT '层级关系',
  primary key (ID)
)default character set utf8;

create table PROJECT_PROTOCOLTEMPLATE
(
  id                    int(8) not null AUTO_INCREMENT,
  projectid             int(8) not null COMMENT '项目ID',
  name                  VARCHAR(50) not null COMMENT '模板名称',
  headmsg               VARCHAR(1500) COMMENT '消息头',
  protocoltype          VARCHAR(20) not null COMMENT '协议类型',
  cerpath               VARCHAR(300) COMMENT '客户端中的证书路径',
  contentencoding       VARCHAR(20) not null COMMENT '编码格式',
  connecttimeout        int(8) not null COMMENT '超时时间',
  time                  VARCHAR(30) COMMENT '最后更新时间',
  operationer           VARCHAR(20) COMMENT '最后更新人员',
  remark                VARCHAR(200) COMMENT '备注',
  primary key (ID)
)default character set utf8;

create table PROJECT_TEMPLATEPARAMS
(
  id                    int(9) not null AUTO_INCREMENT,
  templateid            int(8) not null COMMENT '模块ID',
  paramname             VARCHAR(50) not null COMMENT '参数名',
  param                 VARCHAR(2000) COMMENT '参数默认值',
  paramtype             int(4) not null COMMENT '0 String 1 JSON对象 2 JSONARR对象 3 文件类型',
  primary key (ID)
)default character set utf8;

create table PROJECT_CASESTEPSPARAMS
(
  id                    int(8) not null AUTO_INCREMENT,
  steptype              int(2) not null COMMENT '步骤类型',
  parentid              int(8) not null COMMENT '父节点ID',
  fieldname             VARCHAR(50) COMMENT '所属字段名',
  paramvalue            VARCHAR(200) COMMENT '参数值',
  description           VARCHAR(50) COMMENT '描述',
  primary key (ID)
)default character set utf8;

create table TEMP_CASESTEPDEBUG
(
  id                    int(8) not null AUTO_INCREMENT,
  sign                  VARCHAR(20) not null COMMENT '用例标识',
  executor              VARCHAR(20) not null COMMENT '执行人',
  loglevel              VARCHAR(10) not null COMMENT '日志级别',
  detail                VARCHAR(5000) not null COMMENT '日志',   /*V1.1  扩展日志明细字段到5000*/
  primary key (ID)
)default character set utf8;

create table PUBLIC_CASEPARAMS
(
  id                    int(8) not null AUTO_INCREMENT,
  paramsname            VARCHAR(50) not null COMMENT '变量名称',
  paramsvalue           VARCHAR(500) not null COMMENT '变量值',
  projectid             int(8) not null COMMENT '项目ID',
  remark                VARCHAR(200) COMMENT '备注',
  primary key (ID)
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
values (28, '自动化-任务列表', '删除', 'tastex_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (29, '自动化-用例列表', '执行', 'case_ex');
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
values (46, '用户管理-权限角色', '增加', 'role_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (47, '用户管理-权限角色', '删除', 'role_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (48, '项目管理', '增加', 'pro_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (49, '项目管理', '删除', 'pro_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (50, '项目管理', '修改', 'pro_3');
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
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (58, '部门管理', '增加', 'dpmt_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (59, '部门管理', '删除', 'dpmt_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (60, '部门管理', '修改', 'dpmt_3');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (61, '协议模板', '增加', 'ptct_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (62, '协议模板', '删除', 'ptct_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (63, '协议模板', '修改', 'ptct_3');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (64, '客户端管理', '增加', 'client_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (65, '客户端管理', '删除', 'client_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (66, '客户端管理', '修改', 'client_3');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (67, '公共参数', '增加', 'pcp_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (68, '公共参数', '删除', 'pcp_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (69, '公共参数', '修改', 'pcp_3');
/*插入步骤界面定义各个字段的默认值*/
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (1, 2, 0, 'operation','HttpURLPost','使用HttpURLConnection发送post请求');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (2, 2, 0, 'operation','URLPost','使用URLConnection发送post');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (3, 2, 0, 'operation','GetAndSaveFile','发送get请求保存下载文件到客户端');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (4, 2, 0, 'operation','HttpURLGet','使用HttpURLConnection发送get请求');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (5, 2, 0, 'operation','URLGet','使用URLConnection发送get请求');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (6, 2, 0, 'operation','HttpClientPost','使用HttpClient发送post请求');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (7, 2, 0, 'operation','HttpClientGet','使用HttpClient发送get请求');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (8, 3, 0, 'operation','SocketPost','使用socket发送post请求');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (9, 3, 0, 'operation','SocketGet','使用socket发送get请求');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (10, 1, 0, 'operation','click','点击对象');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (11, 1, 0, 'operation','sendkeys','输入');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (12, 1, 0, 'operation','clear','清除输入框');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (13, 1, 0, 'operation','gotoframe','跳转框架（iframe）');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (14, 1, 0, 'operation','isenabled','判断对象是否可用');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (15, 1, 0, 'operation','isdisplayed','判断对象是否可见');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (16, 1, 0, 'operation','exjsob','针对对象执行JS脚本');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (17, 1, 0, 'operation','gettext','获取对象文本属性');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (18, 1, 0, 'operation','gettagname','获取对象标签类型');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (19, 1, 0, 'operation','getcaptcha','获取对象中的验证码(识别率较低)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (20, 1, 0, 'operation','selectbyvisibletext','通过下拉框的文本进行选择');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (21, 1, 0, 'operation','selectbyvalue','通过下拉框的VALUE属性进行选择');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (22, 1, 0, 'operation','selectbyindex','通过下拉框的index属性进行选择(从0开始)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (23, 1, 0, 'operation','isselect','判断是否已经被选择，同用于单选\复选框');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (24, 1, 0, 'operation','open','打开URL');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (25, 1, 0, 'operation','exjs','执行js脚本');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (26, 1, 0, 'operation','gotodefaultcontent','跳转回到默认iframe');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (27, 1, 0, 'operation','gettitle','获取窗口标题');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (28, 1, 0, 'operation','getwindowhandle','获取窗口句柄');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (29, 1, 0, 'operation','gotowindow','跳转窗口句柄');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (30, 1, 0, 'operation','timeout','设置全局隐式等待时间(S)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (31, 1, 0, 'operation','alertaccept','弹出框点击OK');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (32, 1, 0, 'operation','alertdismiss','弹出框点击取消');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (33, 1, 0, 'operation','alertgettext','获取弹出框TEXT');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (34, 1, 0, 'operation','mouselkclick','模拟鼠标左键单击(可带页面对象)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (35, 1, 0, 'operation','mouserkclick','模拟鼠标右键单击(可带页面对象)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (36, 1, 0, 'operation','mousedclick','模拟鼠标双击(可带页面对象)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (37, 1, 0, 'operation','mouseclickhold','模拟鼠标左键单击后不释放(可带页面对象)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (38, 1, 0, 'operation','mousedrag','模拟鼠标拖拽(可带页面对象)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (39, 1, 0, 'operation','mouseto','模拟鼠标移动到指定坐标(可带页面对象)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (40, 1, 0, 'operation','mouserelease','模拟鼠标释放(可带页面对象)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (41, 1, 0, 'operation','mousekey(tab)','模拟键盘Tab键');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (42, 1, 0, 'operation','mousekey(space)','模拟键盘Space键');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (43, 1, 0, 'operation','mousekey(ctrl)','模拟键盘Ctrl键');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (44, 1, 0, 'operation','mousekey(shift)','模拟键盘Shift键');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (45, 1, 0, 'operation','mousekey(enter)','模拟键盘Enter键');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (46, 1, 0, 'operation','runcase','调用指定接口用例');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (47, 2, 0, 'operation','HttpClientPostJSON','使用HttpClient发送JSON格式post请求');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (48, 2, 0, 'operation','HttpURLDelete','使用HttpURLDelete发送delete请求');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (49, 2, 0, 'operation','httpClientPut','使用httpClientPut发送put请求');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (50, 2, 0, 'operation','httpClientPutJson','使用httpClientPutJson发送put请求');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (51, 2, 0, 'operation','httpClientUploadFile','使用httpClientUploadFile上传文件');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (52, 1, 0, 'operation','getattribute','获取对象指定属性');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (53, 1, 0, 'operation','getcssvalue','获取对象指定CSS属性值');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (54, 4, 0, 'operation','selectbyvisibletext','通过下拉框的文本进行选择');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (55, 4, 0, 'operation','selectbyvalue','通过下拉框的VALUE属性进行选择');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (56, 4, 0, 'operation','selectbyindex','通过下拉框的index属性进行选择(从0开始)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (57, 4, 0, 'operation','isselect','判断是否已经被选择，同用于单选\复选框');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (58, 4, 0, 'operation','gettext','获取对象文本属性');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (59, 4, 0, 'operation','gettagname','获取对象标签类型');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (60, 4, 0, 'operation','getattribute','获取对象指定属性');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (61, 4, 0, 'operation','getcssvalue','获取对象指定CSS属性值');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (62, 4, 0, 'operation','click','点击对象');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (63, 4, 0, 'operation','sendkeys','输入');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (64, 4, 0, 'operation','clear','清除输入框');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (65, 4, 0, 'operation','isenabled','判断对象是否可用');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (66, 4, 0, 'operation','isdisplayed','判断对象是否可见');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (67, 4, 0, 'operation','exjsob','针对对象执行JS脚本');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (68, 4, 0, 'operation','longpresselement','长按指定页面对象(可设置时间)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (69, 4, 0, 'operation','alertaccept','弹出框点击OK');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (70, 4, 0, 'operation','alertdismiss','弹出框点击取消');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (71, 4, 0, 'operation','alertgettext','获取弹出框TEXT');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (72, 4, 0, 'operation','getcontexthandles','获取指定context的值(参数指定1 第一个)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (73, 4, 0, 'operation','exjs','执行JS脚本');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (74, 4, 0, 'operation','androidkeycode','安卓模拟手机键盘发送指令，同PressKeyCode');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (75, 4, 0, 'operation','gotocontext','跳转到指定的context');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (76, 4, 0, 'operation','getcontext','获取当前窗口的context');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (77, 4, 0, 'operation','gettitle','获取当前窗口的title');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (78, 4, 0, 'operation','swipeup','页面向上滑动(参数 持续时间|滚动次数)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (79, 4, 0, 'operation','swipedown','页面向下滑动(参数 持续时间|滚动次数)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (80, 4, 0, 'operation','swipleft','页面向左滑动(参数 持续时间|滚动次数)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (81, 4, 0, 'operation','swipright','页面向右滑动(参数 持续时间|滚动次数)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (82, 4, 0, 'operation','longpressxy','长按指定坐标(参数 X坐标|Y坐标|持续时间(可选))');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (83, 4, 0, 'operation','pressxy','点击指定坐标(参数 X坐标|Y坐标)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (84, 4, 0, 'operation','tapxy','轻击指定坐标(参数 X坐标|Y坐标)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (85, 4, 0, 'operation','jspressxy','JS方式点击指定坐标(参数 X坐标|Y坐标|持续时间(可选))');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (86, 4, 0, 'operation','moveto','拖动坐标(参数 startX,startY|X,Y|X,Y|X,Y...)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (87, 4, 0, 'operation','screenshot','保存当前页面截图');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (88, 4, 0, 'operation','timeout','设置全局页面加载&元素出现最大等待时间(S)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (89, 4, 0, 'operation','hideKeyboard','隐藏系统手机键盘');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (90, 1, 0, 'operation','gotoparentframe','跳转回到上一级iframe');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (91, 1, 0, 'operation','scrollto','滚动到目标对象');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (92, 1, 0, 'operation','scrollintoview','将目标对象滚动到可视');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (93, 4, 0, 'operation','runcase','调用指定接口用例');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (94, 1, 0, 'operation','closewindow','关闭当前浏览器窗口');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (95, 4, 0, 'operation','exAdbShell','执行安卓adb命令');
/*插入角色默认定义*/
insert into USER_ROLE (id, role, permission)
values (2, '测试工程师', 'acc_1,acc_2,acc_3,');
insert into USER_ROLE (id, role, permission)
values (3, '自动化工程师', 'acc_1,acc_2,acc_3,tast_run,tast_remove,tast_ex,tast_1,tast_3,tast_2,tast_upload,tastex_2,case_ex,');
insert into USER_ROLE (id, role, permission)
values (4, '质量工程师', 'pv_1,pv_2,pv_3,pvp_1,fc_1,fc_2,fc_3,acc_1,acc_2,acc_3,pfc_1,pfc_2,pfc_3,fc_tocheck,revinfo_1,revinfo_2,revinfo_3,rev_2,rev_3,acc_upload,');
insert into USER_ROLE (id, role, permission)
values (1, '管理员', 'pv_1,pv_2,pv_3,pvp_1,fc_1,fc_2,fc_3,acc_1,acc_2,acc_3,tast_run,tast_remove,tast_ex,tast_1,tast_3,tast_2,tast_upload,tastex_2,case_ex,pfc_1,pfc_2,pfc_3,fc_tocheck,revinfo_1,revinfo_2,revinfo_3,rev_2,rev_3,acc_upload,ui_1,ui_2,ui_3,ui_4,role_3,role_1,role_2,pro_1,pro_2,pro_3,case_1,case_2,case_3,case_step,proplan_1,proplan_2,proplan_3,dpmt_1,dpmt_2,dpmt_3,ptct_1,ptct_2,ptct_3,client_1,client_2,client_3,pcp_1,pcp_2,pcp_3,');

/*插入默认部门分级*/
insert into QA_SECONDARYSECTOR (sectorid, departmenthead, departmentname)
values (99, '系统管理员', '总部');
/*插入默认项目*/
insert into QA_SECTORPROJECTS (projectid, projectname, projectmanager, sectorid, projecttype)
values (99, '全部项目/未知项目', '系统管理员', 99, 0);
/*插入默认管理员*/
insert into USERINFO (id, usercode, password, username, role, sectorid,projectid)
values (1, 'admin', 'uJ45aIeS9N80kaSFDjvk%2FA%3D%3D', '系统管理员', '1', 99,99);
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