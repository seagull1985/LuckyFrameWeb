/*��չ��־��ϸ�ֶε�5000*/
alter table test_logdetail  modify column detail varchar(5000);
/*�����Ŀ�����Ȩ�޿���*/
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (48, '��Ŀ����', '����', 'pro_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (49, '��Ŀ����', 'ɾ��', 'pro_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (50, '��Ŀ����', '�޸�', 'pro_3');