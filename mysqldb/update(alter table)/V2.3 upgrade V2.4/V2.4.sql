/*调度表增加客户端LOAD驱动桩路径字段*/
alter table TEST_JOBS add clientpath VARCHAR(100) COMMENT '客户端测试驱动桩路径';
/*调度表增加客户端LOAD驱动桩路径字段*/
alter table TEST_CLIENT add clientpath VARCHAR(100) COMMENT '客户端测试驱动桩路径 多个;做分隔';
