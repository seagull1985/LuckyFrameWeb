package com.luckyframe.project.testexecution.taskCaseLog.mapper;

import java.util.List;

import com.luckyframe.project.testexecution.taskCaseLog.domain.TaskCaseLog;
import org.springframework.stereotype.Component;

/**
 * 用例日志明细 数据层
 * 
 * @author luckyframe
 * @date 2019-04-08
 */
@Component
public interface TaskCaseLogMapper 
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
     * 删除用例日志明细
     * 
     * @param logId 用例日志明细ID
     * @return 结果
     */
	int deleteTaskCaseLogById(Integer logId);
	
	/**
	 * 根据TaskId删除日志明细
	 * @param taskId 测试任务ID
	 * @return 返回删除数量
	 * @author Seagull
	 * @date 2019年4月13日
	 */
	int deleteTaskCaseLogByTaskId(Integer taskId);
	
	/**
	 * 根据TaskCaseId删除日志明细
	 * @param taskCaseId 用例执行ID
	 * @return 返回删除数量
	 * @author Seagull
	 * @date 2019年4月23日
	 */
	int deleteTaskCaseLogByTaskCaseId(Integer taskCaseId);
	
	/**
     * 批量删除用例日志明细
     * 
     * @param logIds 需要删除的数据ID
     * @return 结果
     */
	int deleteTaskCaseLogByIds(String[] logIds);
	
    /**
     * 查询已执行的总日志数
     * @return 人财两空总日志数量
     * @author Seagull
     * @date 2019年4月28日
     */
    int selectTaskCaseLogCount();
}