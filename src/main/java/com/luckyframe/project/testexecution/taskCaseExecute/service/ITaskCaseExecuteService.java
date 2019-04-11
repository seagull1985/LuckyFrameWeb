package com.luckyframe.project.testexecution.taskCaseExecute.service;

import java.util.List;

import com.luckyframe.project.testexecution.taskCaseExecute.domain.TaskCaseExecute;

/**
 * 任务用例执行记录 服务层
 * 
 * @author luckyframe
 * @date 2019-04-08
 */
public interface ITaskCaseExecuteService 
{
	/**
     * 查询任务用例执行记录信息
     * 
     * @param taskCaseId 任务用例执行记录ID
     * @return 任务用例执行记录信息
     */
	public TaskCaseExecute selectTaskCaseExecuteById(Integer taskCaseId);
	
	/**
     * 查询任务用例执行记录列表
     * 
     * @param taskCaseExecute 任务用例执行记录信息
     * @return 任务用例执行记录集合
     */
	public List<TaskCaseExecute> selectTaskCaseExecuteList(TaskCaseExecute taskCaseExecute);
	
	/**
	 * 根据任务ID查询用例执行列表
	 * @param taskId
	 * @return
	 * @author Seagull
	 * @date 2019年4月9日
	 */
	public List<TaskCaseExecute> selectTaskCaseExecuteListByTaskId(Integer taskId);
	
	/**
     * 新增任务用例执行记录
     * 
     * @param taskCaseExecute 任务用例执行记录信息
     * @return 结果
     */
	public int insertTaskCaseExecute(TaskCaseExecute taskCaseExecute);
	
	/**
     * 修改任务用例执行记录
     * 
     * @param taskCaseExecute 任务用例执行记录信息
     * @return 结果
     */
	public int updateTaskCaseExecute(TaskCaseExecute taskCaseExecute);
		
	/**
     * 删除任务用例执行记录信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteTaskCaseExecuteByIds(String ids);
	
}
