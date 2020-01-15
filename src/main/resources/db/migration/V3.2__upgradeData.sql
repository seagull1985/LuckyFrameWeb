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