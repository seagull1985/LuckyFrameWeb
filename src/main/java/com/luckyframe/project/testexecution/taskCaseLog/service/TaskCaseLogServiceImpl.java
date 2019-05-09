package com.luckyframe.project.testexecution.taskCaseLog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyframe.common.support.Convert;
import com.luckyframe.project.testexecution.taskCaseLog.domain.TaskCaseLog;
import com.luckyframe.project.testexecution.taskCaseLog.mapper.TaskCaseLogMapper;

/**
 * 用例日志明细 服务层实现
 * 
 * @author luckyframe
 * @date 2019-04-08
 */
@Service
public class TaskCaseLogServiceImpl implements ITaskCaseLogService 
{
	@Autowired
	private TaskCaseLogMapper taskCaseLogMapper;

	/**
     * 查询用例日志明细信息
     * 
     * @param logId 用例日志明细ID
     * @return 用例日志明细信息
     */
    @Override
	public TaskCaseLog selectTaskCaseLogById(Integer logId)
	{
	    return taskCaseLogMapper.selectTaskCaseLogById(logId);
	}
	
	/**
     * 查询用例日志明细列表
     * 
     * @param taskCaseLog 用例日志明细信息
     * @return 用例日志明细集合
     */
	@Override
	public List<TaskCaseLog> selectTaskCaseLogList(TaskCaseLog taskCaseLog)
	{
	    return taskCaseLogMapper.selectTaskCaseLogList(taskCaseLog);
	}
	
	/**
	 * 根据执行用例ID查询日志
	 * @param taskCaseId
	 * @return
	 * @author Seagull
	 * @date 2019年4月11日
	 */
	@Override
	public List<TaskCaseLog> selectTaskCaseLogListByTaskCaseId(Integer taskCaseId)
	{
		TaskCaseLog taskCaseLog = new TaskCaseLog();
		taskCaseLog.setTaskCaseId(taskCaseId);
	    return taskCaseLogMapper.selectTaskCaseLogList(taskCaseLog);
	}
	
    /**
     * 新增用例日志明细
     * 
     * @param taskCaseLog 用例日志明细信息
     * @return 结果
     */
	@Override
	public int insertTaskCaseLog(TaskCaseLog taskCaseLog)
	{
	    return taskCaseLogMapper.insertTaskCaseLog(taskCaseLog);
	}
	
	/**
     * 修改用例日志明细
     * 
     * @param taskCaseLog 用例日志明细信息
     * @return 结果
     */
	@Override
	public int updateTaskCaseLog(TaskCaseLog taskCaseLog)
	{
	    return taskCaseLogMapper.updateTaskCaseLog(taskCaseLog);
	}

	/**
     * 删除用例日志明细对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteTaskCaseLogByIds(String ids)
	{
		return taskCaseLogMapper.deleteTaskCaseLogByIds(Convert.toStrArray(ids));
	}
	
	/**
	 * 根据TaskCaseId删除执行日志
	 * @param taskCaseId
	 * @return
	 * @author Seagull
	 * @date 2019年4月23日
	 */
	@Override
	public int deleteTaskCaseLogByTaskCaseId(Integer taskCaseId)
	{
		return taskCaseLogMapper.deleteTaskCaseLogByTaskCaseId(taskCaseId);
	}
	
	/**
	 * 查询总日志数
	 * @return
	 * @author Seagull
	 * @date 2019年4月28日
	 */
    @Override
	public int selectTaskCaseLogCount()
	{
	    return taskCaseLogMapper.selectTaskCaseLogCount();
	}
}
