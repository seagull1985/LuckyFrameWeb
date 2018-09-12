/*增加协议模板中头域字段的长度*/
alter table PROJECT_PROTOCOLTEMPLATE modify column headmsg varchar(1500);
/*WEB UI增加关闭当前浏览器窗口方法*/
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (94, 1, 0, 'operation','closewindow','关闭当前浏览器窗口');