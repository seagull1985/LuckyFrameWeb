/*
 * LuckyFrame 自动化测试平台 SQL脚本初始化
 * Version   3.4.1
 * Author    seagull
 * Date      2021-06-30
 
 ************  WARNING  ************   
 此脚本属于应用自动升级数据库表结构以及数据脚本，无需手动执行，请慎重！！！！
*/
-- ----------------------------
-- 1、数据字典表增加Web UI内置关键字 getvalue
-- ----------------------------
insert ignore into sys_dict_data values(2048, 49,  'GetValue获取指定对象值', 'getvalue',  'testmanagmt_casestep_uioperation',   '',   'info',  'Y', '0', 'admin', '2021-06-30 14-27-32', 'luckyframe', '2021-06-30 14-27-32', '获取指定对象值');
