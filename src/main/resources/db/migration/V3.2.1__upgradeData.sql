/*
 * LuckyFrame 自动化测试平台 SQL脚本初始化
 * Version   3.2.1
 * Author    seagull
 * Date      2020-04-02
 
 ************  WARNING  ************   
 此脚本属于应用自动升级数据库表结构以及数据脚本，无需手动执行，请慎重！！！！
*/
-- ----------------------------
-- 1、增加用户个性化设置查询时间段
-- ----------------------------
alter table sys_user add date_quantum int(4) default 7 comment '用户日期默认查询条件，单位：天';
-- ----------------------------
-- 2、修改客户端驱动路径字段长度
-- ----------------------------
alter table sys_client modify column client_path varchar(500);