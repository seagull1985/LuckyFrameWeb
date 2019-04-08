package com.luckyframe.project.testexecution.taskExecute.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyframe.common.support.Convert;
import com.luckyframe.project.testexecution.taskExecute.domain.TaskExecute;
import com.luckyframe.project.testexecution.taskExecute.mapper.TaskExecuteMapper;

/**
 * 测试任务执行 服务层实现
 * 
 * @author luckyframe
 * @date 2019-04-08
 */
@Service
public class TaskExecuteServiceImpl implements ITaskExecuteService 
{
	@Autowired
	private TaskExecuteMapper taskExecuteMapper;

	/**
     * 查询测试任务执行信息
     * 
     * @param taskId 测试任务执行ID
     * @return 测试任务执行信息
     */
    @Override
	public TaskExecute selectTaskExecuteById(Integer taskId)
	{
	    return taskExecuteMapper.selectTaskExecuteById(taskId);
	}
	
	/**
     * 查询测试任务执行列表
     * 
     * @param taskExecute 测试任务执行信息
     * @return 测试任务执行集合
     */
	@Override
	public List<TaskExecute> selectTaskExecuteList(TaskExecute taskExecute)
	{
	    return taskExecuteMapper.selectTaskExecuteList(taskExecute);
	}
	
    /**
     * 新增测试任务执行
     * 
     * @param taskExecute 测试任务执行信息
     * @return 结果
     */
	@Override
	public int insertTaskExecute(TaskExecute taskExecute)
	{
	    return taskExecuteMapper.insertTaskExecute(taskExecute);
	}
	
	/**
     * 修改测试任务执行
     * 
     * @param taskExecute 测试任务执行信息
     * @return 结果
     */
	@Override
	public int updateTaskExecute(TaskExecute taskExecute)
	{
	    return taskExecuteMapper.updateTaskExecute(taskExecute);
	}

	/**
     * 删除测试任务执行对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteTaskExecuteByIds(String ids)
	{
		return taskExecuteMapper.deleteTaskExecuteByIds(Convert.toStrArray(ids));
	}
	
}
