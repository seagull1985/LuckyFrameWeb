/*创建客户端IP管理表*/
create table TEST_CLIENT
(
  id       int(8) not null AUTO_INCREMENT,
  clientip  VARCHAR(30) not null COMMENT '客户端IP',
  name  VARCHAR(30) not null COMMENT '客户端名称',
  projectper  VARCHAR(200) COMMENT '使用项目',
  status  int(2) COMMENT '客户端超时 0 正常 1 链接失败 2 状态未知',
  checkinterval int(6) not null COMMENT '检查客户端状态心跳间隔时间 单位:秒',
  remark  VARCHAR(100) COMMENT '备注',
  primary key (id)
)default character set utf8;
/*角色权限表增加操作项目权限字段*/
alter table USER_ROLE add opprojectid VARCHAR(200);
/*增加客户端操作相关权限*/
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (64, '客户端管理', '增加', 'client_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (65, '客户端管理', '删除', 'client_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (66, '客户端管理', '修改', 'client_3');