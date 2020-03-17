package com.luckyframe.project.testexecution.taskCaseLog.service;

import java.util.List;

import com.luckyframe.project.testexecution.taskCaseLog.domain.TaskCaseLog;

/**
 * 用例日志明细 服务层
 * 
 * @author luckyframe
 * @date 2019-04-08
 */
public interface ITaskCaseLogService 
{
	/**
     * 查询用例日志明细信息
     * 
     * @param logId 用例日志明细ID
     * @return 用例日志明细信息
     */
	TaskCaseLog selectTaskCaseLogById(Integer logId);
	
	/**
     * 查询用例日志明细列表
     * 
     * @param taskCaseLog 用例日志明细信息
     * @return 用例日志明细集合
     */
	List<TaskCaseLog> selectTaskCaseLogList(TaskCaseLog taskCaseLog);
	
	/**
	 * 根据执行用例ID查询日志
	 * @param taskCaseId 执行用例ID
	 * @return 返回执行日志集合
	 * @author Seagull
	 * @date 2019年4月11日
	 */
	List<TaskCaseLog> selectTaskCaseLogListByTaskCaseId(Integer taskCaseId);
	
	/**
     * 新增用例日志明细
     * 
     * @param taskCaseLog 用例日志明细信息
     * @return 结果
     */
	int insertTaskCaseLog(TaskCaseLog taskCaseLog);
	
	/**
     * 修改用例日志明细
     * 
     * @param taskCaseLog 用例日志明细信息
     * @return 结果
     */
	int updateTaskCaseLog(TaskCaseLog taskCaseLog);
		
	/**
     * 删除用例日志明细信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	int deleteTaskCaseLogByIds(String ids);
	
	/**
	 * 根据TaskCaseId删除执行日志
	 * @param taskCaseId 用例执行ID
	 * @return 返回删除数量
	 * @author Seagull
	 * @date 2019年4月23日
	 */
	int deleteTaskCaseLogByTaskCaseId(Integer taskCaseId);
	
	/**
	 * 查询总日志数
	 * @return 返回日志数量
	 * @author Seagull
	 * @date 2019年4月28日
	 */
	int selectTaskCaseLogCount();
}
