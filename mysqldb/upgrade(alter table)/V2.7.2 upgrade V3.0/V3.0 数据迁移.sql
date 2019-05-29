/*
 * LuckyFrame 自动化测试平台 SQL脚本从V 2.7.2升级至V 3.0
 * Version   3.0
 * Author    小熊
 * Date      2019-05-08
 
 ************  WARNING  ************   
 执行此脚本
 是默认把V 2.7.2（库名：luckyframedb）中的部分重要数据迁移至 V 3.0（库名：luckyframe）中，
 主要包含了项目数据、用例数据、用例步骤数据、用例公共参数数据、项目模块数据、项目测试计划以及计划用例数据、协议模板以及参数相关数据这几种类型的数据，
 未包含用户数据、调度数据、以及其他测试执行相关的数据，需要在新版本中手动配置。
 质量模块因V 3.0版本中暂未开发提供，所以数据暂时未做迁移，请用户自行先保存在旧版本中，待后续提供相关功能后再做迁移。
*/

USE luckyframe;
-- ----------------------------
-- 1、导入项目表数据 所属部门ID统一修改成104
-- ----------------------------
delete from sys_project;
insert into sys_project  (project_id,project_name,project_sign,dept_id)  select qs.projectid,qs.projectname,qs.projectsign,qs.sectorid from luckyframedb.qa_sectorprojects qs 
where 1=1 and  projecttype=0 and projectid!=99 and NOT EXISTS(select 1 from sys_project  where project_id = qs.projectid);
update sys_project set dept_id=104;
-- ----------------------------
-- 2、导入测试用例数据 ----------------------------
delete from project_case;
insert into project_case  (case_id,case_sign,case_name,project_id,module_id,case_serial_number,create_time,create_by,case_type,remark,failcontinue,update_by,update_time)  select id,sign,name,projectid,moduleid,projectindex,time,operationer,casetype,remark,failcontinue,operationer,time from luckyframedb.project_case pc
where 1=1 and   NOT EXISTS(select 1 from project_case  where case_id = pc.id);

-- ----------------------------
-- 3、导入测试用例步骤表数据  新表与旧表类型对应的值发生了变化 
-- ------     旧的步骤表 接口 (2)   1 Web UI(1)  2 HTTP接口(0)  3 socket（不用了） 4 移动端(3)     
-- ------     新的步骤表 HTTP接口   1 Web UI     2 API驱动      3 移动端
-- ----------------------------
delete from project_case_steps;
insert into project_case_steps  (step_id,case_id,project_id,step_serial_number,step_path,step_operation,step_parameters,action,expected_result,step_type,extend,create_by,create_time,update_by,update_time)  select id,caseid,projectid,stepnum,path,operation,parameters,action,expectedresult,steptype,extend,operationer,time,operationer,time from luckyframedb.project_casesteps pc
where 1=1 and   NOT EXISTS(select 1 from project_case_steps  where step_id = pc.id);
-- ------更新测试用例步骤类型		
update project_case_steps set step_type = 5 where  step_type='0';
update project_case_steps set step_type = 0 where  step_type='2';
update project_case_steps set step_type = 3 where  step_type='4';
update project_case_steps set step_type = 2 where  step_type='5';
-- ----------------------------
-- 4、导入用例参数表数据
-- ----------------------------
delete from project_case_params;
insert into project_case_params  (params_id,params_name,params_value,project_id,remark)  select id,paramsname,paramsvalue,projectid,remark from luckyframedb.public_caseparams pc
where 1=1 and   NOT EXISTS(select 1 from project_case_params  where params_id = pc.id);

-- ----------------------------
-- 5、导入项目模块表数据
-- ----------------------------
delete from project_case_module;
insert into project_case_module  (module_id,module_name,project_id,parent_id)  select id,modulename,projectid,pid from luckyframedb.project_module pm
where 1=1 and   NOT EXISTS(select 1 from project_case_module  where module_id = pm.id);

-- -------初始项目表的基础数据到项目模块表
insert into project_case_module  (module_name,project_id,parent_id,remark)  select projectname,projectid,0,"project_init" from luckyframedb.qa_sectorprojects qs where projecttype=0 and projectid!=99;

-- -------创建临时表
create table project_case_module_tmp as select * from project_case_module t where t.parent_id=0 and t.remark = "project_init";

-- -------更新项目模块父节点
update project_case_module pcm, project_case_module_tmp pt 
set pcm.parent_id =pt.module_id 
where  pcm.parent_id=0 and   (pcm.remark is null or pcm.remark != "project_init") and pcm.project_id = pt.project_id;

-- -------删除临时表
drop table project_case_module_tmp;

-- ------更新项目模块表的层级关系
SET GLOBAL log_bin_trust_function_creators = 1;
CREATE FUNCTION `getParentList4` ( rootId VARCHAR ( 100 ) ) RETURNS VARCHAR ( 1000 ) BEGIN
	DECLARE
		fid VARCHAR ( 100 ) DEFAULT '';
	DECLARE
		str VARCHAR ( 1000 ) DEFAULT '';
	WHILE
			rootId IS NOT NULL DO
			
			SET fid = ( SELECT parent_id FROM project_case_module WHERE module_id = rootId );
		IF
			fid IS NOT NULL THEN
			IF
				str = '' THEN
					
					SET str = fid;
				ELSE 
					SET str = concat( fid, ',', str );
				
			END IF;
			
			SET rootId = fid;
			ELSE 
				SET rootId = fid;
			
		END IF;
		
	END WHILE;
	RETURN str;
END
;
update project_case_module  pcm set pcm.ancestors= getParentList4(pcm.module_id);

DROP FUNCTION `getParentList4`;

-- ----------------------------
-- 6、导入测试计划表数据
-- ----------------------------
delete from project_plan;
insert into project_plan  (plan_id,plan_name,plan_case_count,project_id,create_by,create_time,update_by,update_time,remark)  select id,name,casecount,projectid,operationer,time,operationer,time,remark from luckyframedb.project_plan pc
where 1=1 and   NOT EXISTS(select 1 from project_plan  where plan_id = pc.id);

-- ----------------------------
-- 7、导入测试用例计划表数据
-- ----------------------------
delete from project_plan_case;
insert into project_plan_case  (plan_case_id,case_id,plan_id,priority)  select id,caseid,planid,priority from luckyframedb.project_plancase pc
where 1=1 and   NOT EXISTS(select 1 from project_plan_case  where plan_id = pc.id);

-- ----------------------------
-- 8、导入协议模板表数据
-- ----------------------------
delete from project_protocol_template;
insert into project_protocol_template  (template_id,template_name,project_id,head_msg,cerificate_path,encoding,timeout,is_response_head,is_response_code,create_by,create_time,update_by,update_time,remark)  select id,name,projectid,headmsg,cerpath,contentencoding,connecttimeout,responsehead,responsecode,operationer,time,operationer,time,remark from luckyframedb.project_protocoltemplate pp
where 1=1 and   NOT EXISTS(select 1 from project_protocol_template  where template_id = pp.id);

-- ----------------------------
-- 9、导入协议模板参数表数据
-- ----------------------------
delete from project_template_params;
insert into project_template_params  (params_id,template_id,param_name,param_value,param_type)  select id,templateid,paramname,param,paramtype from luckyframedb.project_templateparams pt
where 1=1 and   NOT EXISTS(select 1 from project_template_params  where params_id = pt.id);