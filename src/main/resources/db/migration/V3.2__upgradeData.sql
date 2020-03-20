/*
 * LuckyFrame 自动化测试平台 SQL脚本初始化
 * Version   3.2
 * Author    seagull
 * Date      2020-01-09
 
 ************  WARNING  ************   
 此脚本属于应用自动升级数据库表结构以及数据脚本，无需手动执行，请慎重！！！！
*/

-- ----------------------------
-- 1、增加HTTP接口测试的patch、delete（支持JSON格式）两种类型请求
-- ----------------------------
insert into sys_dict_data values(1013, 14,  'httpClientDeleteJson发送Delete请求', 'httpClientDeleteJson',  'testmanagmt_casestep_httpoperation',   '',   'info',  'Y', '0', 'admin', '2020-01-15 16-20-32', 'luckyframe', '2020-01-15 16-20-32', '使用httpClientDeleteJson发送delete Json请求');
insert into sys_dict_data values(1014, 15,  'httpClientPatchJson发送Patch请求', 'httpClientPatchJson',  'testmanagmt_casestep_httpoperation',   '',   'info',  'Y', '0', 'admin', '2020-01-15 16-20-32', 'luckyframe', '2020-01-15 16-20-32', '使用httpClientPatchJson发送PatchJson请求');

-- ----------------------------
-- 2、对移动端安卓关键字(keycode)描述优化
-- ----------------------------
update sys_dict_data set dict_label='AndroidKeyCode安卓模拟手机按键，参考Android KeyCode',remark='AndroidKeyCode安卓模拟手机按键，参考Android KeyCode' where dict_code=3020;

-- ----------------------------
-- 3、优化以及增加页面滑动关键字，增加手指滑动关键字
-- ----------------------------
update sys_dict_data set dict_label='SwipePageUp页面向上滑动(参数 持续时间|滚动次数)',dict_value='swipepageup' where dict_code=3024;
update sys_dict_data set dict_label='SwipePageDown页面向下滑动(参数 持续时间|滚动次数)',dict_value='swipepagedown' where dict_code=3025;
update sys_dict_data set dict_label='SwipePageLeft页面向左滑动(参数 持续时间|滚动次数)',dict_value='swipepageleft' where dict_code=3026;
update sys_dict_data set dict_label='SwipePageRight页面向右滑动(参数 持续时间|滚动次数)',dict_value='swipepageright' where dict_code=3027;
insert into sys_dict_data values(3038, 39,  'SwipeUp手指向上滑动(参数 持续时间|滚动次数)', 'swipeup',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2020-01-19 09-27-32', 'luckyframe', '2020-01-19 09-27-32', '手指向上滑动(参数 持续时间|滚动次数)');
insert into sys_dict_data values(3039, 40,  'SwipeDown手指向下滑动(参数 持续时间|滚动次数)', 'swipedown',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2020-01-19 09-27-32', 'luckyframe', '2020-01-19 09-27-32', '手指向下滑动(参数 持续时间|滚动次数)');
insert into sys_dict_data values(3040, 41,  'SwipeLeft手指向左滑动(参数 持续时间|滚动次数)', 'swipeleft',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2020-01-19 09-27-32', 'luckyframe', '2020-01-19 09-27-32', '手指向左滑动(参数 持续时间|滚动次数)');
insert into sys_dict_data values(3041, 42,  'SwipeRight手指向右滑动(参数 持续时间|滚动次数)', 'swiperight',  'testmanagmt_casestep_muioperation',   '',   'info',  'Y', '0', 'admin', '2020-01-19 09-27-32', 'luckyframe', '2020-01-19 09-27-32', '手指向右滑动(参数 持续时间|滚动次数)');

-- ----------------------------
-- 4、步骤表添加备注字段
-- ----------------------------
alter table project_case_steps add step_remark varchar(200) comment '备注字段，给接口类型的用例步骤使用';

-- ----------------------------
-- 5、修改客户端表适配netty协议
-- ----------------------------
alter table sys_client modify column job_id int(8) default null COMMENT '心跳任务ID';
alter table sys_client add client_type int(2) default 0 comment '客户端链接类型 0 普通HTTP 1 NETTY通信';