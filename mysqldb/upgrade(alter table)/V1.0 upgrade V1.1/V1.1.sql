/*扩展日志明细字段到5000*/
alter table test_logdetail  modify column detail varchar(5000);
/*添加项目管理的权限控制*/
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (48, '项目管理', '增加', 'pro_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (49, '项目管理', '删除', 'pro_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (50, '项目管理', '修改', 'pro_3');