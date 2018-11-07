/* 表名全部小写 */
rename table OPERATION_LOG to operation_log;
rename table QA_ACCIDENT to qa_accident;
rename table QA_FLOWCHECK to qa_flowcheck;
rename table QA_FLOWINFO to qa_flowinfo;
rename table QA_PLANFLOWCHECK to qa_planflowcheck;
rename table QA_PROJECTVERSION to qa_projectversion;
rename table QA_REVIEW to qa_review;
rename table QA_REVIEWINFO to qa_reviewinfo;
rename table QA_SECONDARYSECTOR to qa_secondarysector;
rename table QA_SECTORPROJECTS to qa_sectorprojects;
rename table QA_ZTTASK to qa_zttask;
rename table TEST_JOBS to test_jobs;
rename table TEST_CASEDETAIL to test_casedetail;
rename table TEST_LOGDETAIL to test_logdetail;
rename table TEST_TASKEXCUTE to test_taskexcute;
rename table USERINFO to userinfo;
rename table USER_AUTHORITY to user_authority;
rename table USER_ROLE to user_role;
rename table TEST_CLIENT to test_client;
rename table PROJECT_CASE to project_case;
rename table PROJECT_CASESTEPS to project_casesteps;
rename table PROJECT_PLAN to project_plan;
rename table PROJECT_PLANCASE to project_plancase;
rename table PROJECT_MODULE to project_module;
rename table PROJECT_PROTOCOLTEMPLATE to project_protocoltemplate;
rename table PROJECT_TEMPLATEPARAMS to project_templateparams;
rename table PROJECT_CASESTEPSPARAMS to project_casestepsparams;
rename table TEMP_CASESTEPDEBUG to temp_casestepdebug;
rename table PUBLIC_CASEPARAMS to public_caseparams;

/* 初始化‘addcookie’数据 */
insert into project_casestepsparams (id, steptype, parentid, fieldname, paramvalue, description)
values (96, 1, 0, 'operation', 'addcookie', '添加浏览器cookie');
/* 修改Web UI中的一些描述*/
update project_casestepsparams set description='调用指定接口|Web UI用例' where id=46;
/* 增加发送邮件通知的逻辑控制*/
ALTER TABLE test_jobs ADD COLUMN sendcondition int(4) NULL DEFAULT 0 COMMENT '发送邮件通知时的具体逻辑, 0-全部，1-成功，2-失败';
/* 控制用例步骤失败后是否继续*/
ALTER TABLE project_case ADD COLUMN failcontinue int(2) NULL DEFAULT 0 COMMENT '失败了是否继续，0：不继续，1：继续';
