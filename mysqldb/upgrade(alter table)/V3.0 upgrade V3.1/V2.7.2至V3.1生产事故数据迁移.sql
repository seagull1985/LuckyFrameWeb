/*
 * LuckyFrame 自动化测试平台 SQL脚本从V2.7.2升级至V3.1的生产事故数据迁移
 * Version   3.1
 * Author    Seagull
 * Date      2019-08-27
 
 ************  WARNING  ************   
 执行此脚本会将V2.7.2中生产事故的相关数据迁移到最新V3.1的表中
 如果你没有V2.7.2这个版本，不用执行此脚本
*/
-- ----------------------------
-- 质量管理-生产事故登记数据迁移
-- ----------------------------
insert into qa_accident (accident_id,project_id,accident_status,accident_time,report_time,accident_description,accident_level,accident_analysis,accident_type,accident_consequence,corrective_action,resolution_time,resolutioner,preventive_action,preventiver,preventive_plan_date,preventive_over_date,duration_time,impact_time,accident_file_name,create_by)
select id,projectid,accstatus,eventtime,reporttime,accdescription,acclevel,causalanalysis,causaltype,consequenceanalysis,correctiveaction,resolutiontime,resolutioner,preventiveaction,preventiver,preventiveplandate,preventiveaccdate,trouble_duration,impact_time,filename,reporter from luckyframedb.qa_accident qa  
where 1=1 and   NOT EXISTS(select 1 from qa_accident  where accident_id = qa.id);

/*修改NULL字符串为空*/
update qa_accident t set t.resolution_time='' where t.resolution_time='NULL';
update qa_accident t set t.resolutioner='' where t.resolutioner='NULL';
update qa_accident t set t.preventive_plan_date='' where t.preventive_plan_date='NULL';
update qa_accident t set t.preventive_over_date='' where t.preventive_over_date='NULL';
update qa_accident t set t.preventiver='' where t.preventiver='NULL';
update qa_accident t set t.report_time='' where t.report_time='NULL';

/*不是跟踪处理完成状态的记录，把解决时间跟解决人员修改成NULL*/
update qa_accident t set t.resolution_time=null,t.resolutioner=null where t.accident_status!='跟踪处理完成'