/*调度表增加客户端LOAD驱动桩路径字段*/
alter table TEST_JOBS add clientpath VARCHAR(100) COMMENT '客户端测试驱动桩路径';
/*调度表增加客户端LOAD驱动桩路径字段*/
alter table TEST_CLIENT add clientpath VARCHAR(100) COMMENT '客户端测试驱动桩路径 多个;做分隔';
/*协议模板表增加消息头字段*/
alter table PROJECT_PROTOCOLTEMPLATE add headmsg VARCHAR(500) COMMENT '消息头';
/*HTTP请求增加三种请求模式*/
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (47, 2, 0, 'operation','HttpClientPostJSON','使用HttpClient发送JSON格式post请求');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (48, 2, 0, 'operation','HttpURLDelete','使用HttpURLDelete发送delete请求');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (49, 2, 0, 'operation','httpClientPut','使用httpClientPut发送put请求');
