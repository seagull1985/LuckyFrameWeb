/*增加支持协议模板表*/
create table PROJECT_PROTOCOLTEMPLATE
(
  id                    int(8) not null AUTO_INCREMENT,
  projectid             int(8) not null COMMENT '项目ID',
  name                  VARCHAR(50) not null COMMENT '模板名称',
  protocoltype          VARCHAR(20) not null COMMENT '协议类型',
  contentencoding       VARCHAR(20) not null COMMENT '编码格式',
  connecttimeout        int(8) not null COMMENT '超时时间',
  time                  VARCHAR(30) COMMENT '最后更新时间',
  operationer           VARCHAR(20) COMMENT '最后更新人员',
  remark                VARCHAR(200) COMMENT '备注',
  primary key (ID)
)default character set utf8;

create table PROJECT_TEMPLATEPARAMS
(
  id                    int(9) not null AUTO_INCREMENT,
  templateid            int(8) not null COMMENT '模块ID',
  paramname             VARCHAR(50) not null COMMENT '参数名',
  param                 VARCHAR(2000) COMMENT '参数默认值',
  primary key (ID)
)default character set utf8;

create table PROJECT_CASESTEPSPARAMS
(
  id                    int(8) not null AUTO_INCREMENT,
  steptype              int(2) not null COMMENT '步骤类型',
  parentid              int(8) not null COMMENT '父节点ID',
  fieldname             VARCHAR(50) COMMENT '所属字段名',
  paramvalue            VARCHAR(200) COMMENT '参数值',
  description           VARCHAR(50) COMMENT '描述',
  primary key (ID)
)default character set utf8;

create table TEMP_CASESTEPDEBUG
(
  id                    int(8) not null AUTO_INCREMENT,
  sign                  VARCHAR(20) not null COMMENT '用例标识',
  executor              VARCHAR(20) not null COMMENT '执行人',
  loglevel              VARCHAR(10) not null COMMENT '日志级别',
  detail                VARCHAR(5000) not null COMMENT '日志', 
  primary key (ID)
)default character set utf8;

/*插入部门管理权限*/
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (61, '协议模板', '增加', 'ptct_1');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (62, '协议模板', '删除', 'ptct_2');
insert into USER_AUTHORITY (id, module, auth_type, alias)
values (63, '协议模板', '修改', 'ptct_3');

/*插入步骤界面定义各个字段的默认值*/
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (1, 2, 0, 'operation','HttpURLPost','使用HttpURLConnection发送post请求');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (2, 2, 0, 'operation','URLPost','使用URLConnection发送post');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (3, 2, 0, 'operation','GetAndSaveFile','发送get请求保存下载文件到客户端');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (4, 2, 0, 'operation','HttpURLGet','使用HttpURLConnection发送get请求');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (5, 2, 0, 'operation','URLGet','使用URLConnection发送get请求');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (6, 2, 0, 'operation','HttpClientPost','使用HttpClient发送post请求');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (7, 2, 0, 'operation','HttpClientGet','使用HttpClient发送get请求');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (8, 3, 0, 'operation','SocketPost','使用socket发送post请求');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (9, 3, 0, 'operation','SocketGet','使用socket发送get请求');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (10, 1, 0, 'operation','click','点击对象');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (11, 1, 0, 'operation','sendkeys','输入');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (12, 1, 0, 'operation','clear','清除输入框');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (13, 1, 0, 'operation','gotoframe','跳转框架（iframe）');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (14, 1, 0, 'operation','isenabled','判断对象是否可用');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (15, 1, 0, 'operation','isdisplayed','判断对象是否可见');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (16, 1, 0, 'operation','exjsob','针对对象执行JS脚本');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (17, 1, 0, 'operation','gettext','获取对象文本属性');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (18, 1, 0, 'operation','gettagname','获取对象标签类型');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (19, 1, 0, 'operation','getcaptcha','获取对象中的验证码(识别率较低)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (20, 1, 0, 'operation','selectbyvisibletext','通过下拉框的文本进行选择');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (21, 1, 0, 'operation','selectbyvalue','通过下拉框的VALUE属性进行选择');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (22, 1, 0, 'operation','selectbyindex','通过下拉框的index属性进行选择(从0开始)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (23, 1, 0, 'operation','isselect','判断是否已经被选择，同用于单选\复选框');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (24, 1, 0, 'operation','open','打开URL');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (25, 1, 0, 'operation','exjs','执行js脚本');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (26, 1, 0, 'operation','gotodefaultcontent','跳转回到默认iframe');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (27, 1, 0, 'operation','gettitle','获取窗口标题');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (28, 1, 0, 'operation','getwindowhandle','获取窗口句柄');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (29, 1, 0, 'operation','gotowindow','跳转窗口句柄');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (30, 1, 0, 'operation','wait','等待时间(S)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (31, 1, 0, 'operation','alertaccept','弹出框点击OK');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (32, 1, 0, 'operation','alertdismiss','弹出框点击取消');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (33, 1, 0, 'operation','alertgettext','获取弹出框TEXT');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (34, 1, 0, 'operation','mouselkclick','模拟鼠标左键单击(可带页面对象)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (35, 1, 0, 'operation','mouserkclick','模拟鼠标右键单击(可带页面对象)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (36, 1, 0, 'operation','mousedclick','模拟鼠标双击(可带页面对象)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (37, 1, 0, 'operation','mouseclickhold','模拟鼠标左键单击后不释放(可带页面对象)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (38, 1, 0, 'operation','mousedrag','模拟鼠标拖拽(可带页面对象)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (39, 1, 0, 'operation','mouseto','模拟鼠标移动到指定坐标(可带页面对象)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (40, 1, 0, 'operation','mouserelease','模拟鼠标释放(可带页面对象)');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (41, 1, 0, 'operation','mousekey(tab)','模拟键盘Tab键');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (42, 1, 0, 'operation','mousekey(space)','模拟键盘Space键');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (43, 1, 0, 'operation','mousekey(ctrl)','模拟键盘Ctrl键');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (44, 1, 0, 'operation','mousekey(shift)','模拟键盘Shift键');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (45, 1, 0, 'operation','mousekey(enter)','模拟键盘Enter键');
insert into PROJECT_CASESTEPSPARAMS (id, steptype, parentid, fieldname,paramvalue,description)
values (46, 1, 0, 'operation','runcase','调用指定接口用例');