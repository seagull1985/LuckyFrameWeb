/* 新增加用户状态字段*/
alter table userinfo add  column status varchar(2)   DEFAULT '0' not null COMMENT '状态：（0：正常，1：注销）';
