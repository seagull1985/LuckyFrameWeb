/*增加APP测试中runcase方法以及增加web测试中的几个方法*/
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (90, 1, 0, 'operation','gotoparentframe','跳转回到上一级iframe');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (91, 1, 0, 'operation','scrollto','滚动到目标对象');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (92, 1, 0, 'operation','scrollintoview','将目标对象滚动到可视');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (93, 4, 0, 'operation','runcase','调用指定接口用例');
/*增加用例步骤中path字段的长度*/
alter table PROJECT_CASESTEPS modify column path varchar(200);
/*增加协议模板中cerpath字段,用于存放客户端的证书路径*/
alter table PROJECT_PROTOCOLTEMPLATE add cerpath varchar(300) COMMENT '客户端中的证书路径';
/*增加协议模板中cerpath字段,用于存放客户端的证书路径*/
alter table OPERATION_LOG add operation_integral int(2) COMMENT '操作积分';