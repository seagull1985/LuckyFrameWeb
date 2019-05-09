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
	 * 根据taskId跟caseId查询用例执行信息
	 * @param taskCaseExecute
	 * @return
	 * @author Seagull
	 * @date 2019年4月22日
	 */
	public TaskCaseExecute selectTaskCaseExecuteByTaskIdAndCaseId(TaskCaseExecute taskCaseExecute);
	
	/**
     * 查询任务用例执行记录列表
     * 
     * @param taskCaseExecute 任务用例执行记录信息
     * @return 任务用例执行记录集合
     */
	public List<TaskCaseExecute> selectTaskCaseExecuteList(TaskCaseExecute taskCaseExecute);
	
	/**
	 * 查询执行任务中所有执行未成功的用例
	 * @param taskId
	 * @return
	 * @author Seagull
	 * @date 2019年4月23日
	 */
	public List<TaskCaseExecute> selectTaskCaseExecuteListForUnSucByTaskId(Integer taskId);
	
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
	
	/**
	 * 查询已执行的总用例数
	 * @return
	 * @author Seagull
	 * @date 2019年4月28日
	 */
	public int selectTaskCaseExecuteCount();
	
	/**
	 * 查询已成功执行的总用例数
	 * @return
	 * @author Seagull
	 * @date 2019年4月28日
	 */
	public int selectTaskCaseExecuteCountForSuccess();
}
