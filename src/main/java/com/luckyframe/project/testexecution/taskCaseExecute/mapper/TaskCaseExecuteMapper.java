package com.luckyframe.project.testexecution.taskCaseExecute.mapper;

import java.util.List;

import com.luckyframe.project.testexecution.taskCaseExecute.domain.TaskCaseExecute;	

/**
 * 任务用例执行记录 数据层
 * 
 * @author luckyframe
 * @date 2019-04-08
 */
public interface TaskCaseExecuteMapper 
{
	/**
     * 查询任务用例执行记录信息
     * 
     * @param taskCaseId 任务用例执行记录ID
     * @return 任务用例执行记录信息
     */
	public TaskCaseExecute selectTaskCaseExecuteById(Integer taskCaseId);
	
	/**
	 * 根据任务ID以及用例ID查询实体
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
	 * 查询任务中所有执行未成功的用例列表
	 * @param taskId
	 * @return
	 * @author Seagull
	 * @date 2019年4月23日
	 */
	public List<TaskCaseExecute> selectTaskCaseExecuteListForUnSucByTaskId(Integer taskId);
	
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
     * 删除任务用例执行记录
     * 
     * @param taskCaseId 任务用例执行记录ID
     * @return 结果
     */
	public int deleteTaskCaseExecuteById(Integer taskCaseId);
	
	/**
	 * 根据TaskId删除任务用例执行记录
	 * @param taskId
	 * @return
	 * @author Seagull
	 * @date 2019年4月13日
	 */
	public int deleteTaskCaseExecuteByTaskId(Integer taskId);
	
	/**
     * 批量删除任务用例执行记录
     * 
     * @param taskCaseIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteTaskCaseExecuteByIds(String[] taskCaseIds);
	
    /**
     * 查询项目下有没有执行用例明细
     * @param projectId
     * @return
     * @author Seagull
     * @date 2019年4月12日
     */
    public int selectTaskCaseExecuteCountByProjectId(Integer projectId);
    
    /**
     * 查询已执行的总用例数
     * @return
     * @author Seagull
     * @date 2019年4月28日
     */
    public int selectTaskCaseExecuteCount();
    
    /**
     * 查询已执行成功的总用例数
     * @return
     * @author Seagull
     * @date 2019年4月28日
     */
    public int selectTaskCaseExecuteCountForSuccess();
}