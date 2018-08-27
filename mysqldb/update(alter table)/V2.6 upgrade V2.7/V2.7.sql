/*增加协议模板中头域字段的长度*/
alter table PROJECT_PROTOCOLTEMPLATE modify column headmsg varchar(1500);