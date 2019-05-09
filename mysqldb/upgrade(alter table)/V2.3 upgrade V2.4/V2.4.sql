/*增加用例公共参数表*/
create table PUBLIC_CASEPARAMS
(
  id                    int(8) not null AUTO_INCREMENT,
  paramsname            VARCHAR(50) not null COMMENT '变量名称',
  paramsvalue           VARCHAR(500) not null COMMENT '变量值',
  projectid             int(8) not null COMMENT '项目ID',
  remark                VARCHAR(200) COMMENT '备注',
  primary key (ID)
)default character set utf8;
/*调度表增加客户端LOAD驱动桩路径字段*/
alter table TEST_JOBS add clientpath VARCHAR(100) COMMENT '客户端测试驱动桩路径';
/*调度表增加客户端LOAD驱动桩路径字段*/
alter table TEST_CLIENT add clientpath VARCHAR(100) COMMENT '客户端测试驱动桩路径 多个;做分隔';
/*协议模板表增加消息头字段*/
alter table PROJECT_PROTOCOLTEMPLATE add headmsg VARCHAR(500) COMMENT '消息头';
/*协议模板参数表增加参数类型*/
alter table PROJECT_TEMPLATEPARAMS add paramtype int(4) default 0 COMMENT '0 String 1 JSON对象 2 JSONARR对象 3 文件类型';
/*HTTP请求增加三种请求模式*/
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
/*增加公共参加控制权限*/
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (67, '公共参数', '增加', 'pcp_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (68, '公共参数', '删除', 'pcp_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (69, '公共参数', '修改', 'pcp_3');