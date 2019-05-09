/* 新增加用户状态字段*/
alter table userinfo add  column status varchar(2) DEFAULT '0' not null COMMENT '状态：（0：正常，1：注销）';
/* 新增加协议模板中可选择响应返回值头域以及响应码*/
alter table project_protocoltemplate add column responsehead int(2) DEFAULT '0' not null COMMENT '请求响应返回值是否带头域信息 0不带 1带';
alter table project_protocoltemplate add column responsecode int(2) DEFAULT '0' not null COMMENT '请求响应返回值是否带状态码 0不带 1带';
alter table userinfo add  column status varchar(2)   DEFAULT '0' not null COMMENT '状态：（0：正常，1：注销）';
/* 新增加项目ID字段*/
alter table test_taskexcute add	column projectid int(10)  not null COMMENT '项目ID';
/*历史数据项目ID初始化*/
update test_taskexcute t, test_jobs ts set t.projectid= ts.projectid where  ts.id = t.jobid  and t.projectid =0;
/*修改步骤表中备注字段的用处，升级为扩展字段，暂时用于HTTP模板的存储*/
ALTER TABLE project_casesteps change remark extend varchar(200) COMMENT '扩展字段，可用于备注、存储HTTP模板等';
/*把在action中存储的模板信息移到EXTEND字段中进行管理*/
update project_casesteps set extend=action where action like "【%" and action like "%】%";
update project_casesteps set action="" where action like "【%" and action like "%】%";