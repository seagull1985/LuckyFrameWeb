/* 新增加用户状态字段*/
alter table userinfo add  column status varchar(2) DEFAULT '0' not null COMMENT '状态：（0：正常，1：注销）';
/* 新增加协议模板中可选择响应返回值头域以及响应码*/
alter table project_protocoltemplate add column responsehead int(2) DEFAULT '0' not null COMMENT '请求响应返回值是否带头域信息 0不带 1带';
alter table project_protocoltemplate add column responsecode int(2) DEFAULT '0' not null COMMENT '请求响应返回值是否带状态码 0不带 1带';