-- 任务调度表增加一列环境
alter table task_scheduling add env_name VARCHAR(255) COMMENT '测试环境';
-- 公共参数表增加一列环境
alter table project_case_params add env_name VARCHAR(255) COMMENT '测试环境';