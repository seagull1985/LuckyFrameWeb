package com.luckyframe.project.testexecution.taskExecute.service;

import java.util.List;

import com.luckyframe.project.testexecution.taskExecute.domain.TaskExecute;

/**
 * 测试任务执行 服务层
 * 
 * @author luckyframe
 * @date 2019-04-08
 */
public interface ITaskExecuteService 
{
	/**
     * 查询测试任务执行信息
     * 
     * @param taskId 测试任务执行ID
     * @return 测试任务执行信息
     */
	public TaskExecute selectTaskExecuteById(Integer taskId);
	
	/**
	 * 查询最后一条执行记录
	 * @return
	 * @author Seagull
	 * @date 2019年4月24日
	 */
	public TaskExecute selectTaskExecuteLastRecord();
    
	/**
     * 查询测试任务执行列表
     * 
     * @param taskExecute 测试任务执行信息
     * @return 测试任务执行集合
     */
	public List<TaskExecute> selectTaskExecuteList(TaskExecute taskExecute);
	
	/**
     * 新增测试任务执行
     * 
     * @param taskExecute 测试任务执行信息
     * @return 结果
     */
	public int insertTaskExecute(TaskExecute taskExecute);
	
	/**
     * 修改测试任务执行
     * 
     * @param taskExecute 测试任务执行信息
     * @return 结果
     */
	public int updateTaskExecute(TaskExecute taskExecute);
		
	/**
     * 删除测试任务执行信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteTaskExecuteByIds(String ids);
	
}
