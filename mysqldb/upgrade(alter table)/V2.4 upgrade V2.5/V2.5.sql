/*增加UI测试获取对象属性值方法*/
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (52, 1, 0, 'operation','getattribute','获取对象指定属性');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (53, 1, 0, 'operation','getcssvalue','获取对象指定CSS属性值');
/*修改wait方法为timeout 设置全局隐式等待时间*/
update PROJECT_CASESTEPSPARAMS set paramvalue='timeout',description='设置全局页面加载&元素出现最大等待时间(S)' where id=30;
 
/*补充协议模板中漏掉的connecttimeout字段*/
alter table PROJECT_PROTOCOLTEMPLATE add connecttimeout int(8) not null COMMENT '超时时间';

/*增加APP测试封装方法*/
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (54, 4, 0, 'operation','selectbyvisibletext','通过下拉框的文本进行选择');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (55, 4, 0, 'operation','selectbyvalue','通过下拉框的VALUE属性进行选择');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (56, 4, 0, 'operation','selectbyindex','通过下拉框的index属性进行选择(从0开始)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (57, 4, 0, 'operation','isselect','判断是否已经被选择，同用于单选\复选框');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (58, 4, 0, 'operation','gettext','获取对象文本属性');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (59, 4, 0, 'operation','gettagname','获取对象标签类型');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (60, 4, 0, 'operation','getattribute','获取对象指定属性');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (61, 4, 0, 'operation','getcssvalue','获取对象指定CSS属性值');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (62, 4, 0, 'operation','click','点击对象');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (63, 4, 0, 'operation','sendkeys','输入');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (64, 4, 0, 'operation','clear','清除输入框');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (65, 4, 0, 'operation','isenabled','判断对象是否可用');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (66, 4, 0, 'operation','isdisplayed','判断对象是否可见');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (67, 4, 0, 'operation','exjsob','针对对象执行JS脚本');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (68, 4, 0, 'operation','longpresselement','长按指定页面对象(可设置时间)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (69, 4, 0, 'operation','alertaccept','弹出框点击OK');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (70, 4, 0, 'operation','alertdismiss','弹出框点击取消');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (71, 4, 0, 'operation','alertgettext','获取弹出框TEXT');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (72, 4, 0, 'operation','getcontexthandles','获取指定context的值(参数指定1 第一个)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (73, 4, 0, 'operation','exjs','执行JS脚本');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (74, 4, 0, 'operation','androidkeycode','安卓模拟手机键盘发送指令，同PressKeyCode');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (75, 4, 0, 'operation','gotocontext','跳转到指定的context');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (76, 4, 0, 'operation','getcontext','获取当前窗口的context');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (77, 4, 0, 'operation','gettitle','获取当前窗口的title');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (78, 4, 0, 'operation','swipeup','页面向上滑动(参数 持续时间|滚动次数)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (79, 4, 0, 'operation','swipedown','页面向下滑动(参数 持续时间|滚动次数)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (80, 4, 0, 'operation','swipleft','页面向左滑动(参数 持续时间|滚动次数)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (81, 4, 0, 'operation','swipright','页面向右滑动(参数 持续时间|滚动次数)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (82, 4, 0, 'operation','longpressxy','长按指定坐标(参数 X坐标|Y坐标|持续时间(可选))');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (83, 4, 0, 'operation','pressxy','点击指定坐标(参数 X坐标|Y坐标)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (84, 4, 0, 'operation','tapxy','轻击指定坐标(参数 X坐标|Y坐标)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (85, 4, 0, 'operation','jspressxy','JS方式点击指定坐标(参数 X坐标|Y坐标|持续时间(可选))');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (86, 4, 0, 'operation','moveto','拖动坐标(参数 startX,startY|X,Y|X,Y|X,Y...)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (87, 4, 0, 'operation','screenshot','保存当前页面截图');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (88, 4, 0, 'operation','timeout','设置全局页面加载&元素出现最大等待时间(S)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (89, 4, 0, 'operation','hideKeyboard','隐藏系统手机键盘');

