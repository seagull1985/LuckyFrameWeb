package com.luckyframe.project.testexecution.taskCaseExecute.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyframe.common.support.Convert;
import com.luckyframe.project.testexecution.taskCaseExecute.domain.TaskCaseExecute;
import com.luckyframe.project.testexecution.taskCaseExecute.mapper.TaskCaseExecuteMapper;

/**
 * 任务用例执行记录 服务层实现
 * 
 * @author luckyframe
 * @date 2019-04-08
 */
@Service
public class TaskCaseExecuteServiceImpl implements ITaskCaseExecuteService 
{
	@Autowired
	private TaskCaseExecuteMapper taskCaseExecuteMapper;

	/**
     * 查询任务用例执行记录信息
     * 
     * @param taskCaseId 任务用例执行记录ID
     * @return 任务用例执行记录信息
     */
    @Override
	public TaskCaseExecute selectTaskCaseExecuteById(Integer taskCaseId)
	{
	    return taskCaseExecuteMapper.selectTaskCaseExecuteById(taskCaseId);
	}
	
	/**
	 * 根据taskId跟caseId查询用例执行信息
	 * @param taskCaseExecute
	 * @return
	 * @author Seagull
	 * @date 2019年4月22日
	 */
    @Override
	public TaskCaseExecute selectTaskCaseExecuteByTaskIdAndCaseId(TaskCaseExecute taskCaseExecute)
	{
	    return taskCaseExecuteMapper.selectTaskCaseExecuteByTaskIdAndCaseId(taskCaseExecute);
	}
    
	/**
     * 查询任务用例执行记录列表
     * 
     * @param taskCaseExecute 任务用例执行记录信息
     * @return 任务用例执行记录集合
     */
	@Override
	public List<TaskCaseExecute> selectTaskCaseExecuteList(TaskCaseExecute taskCaseExecute)
	{
	    return taskCaseExecuteMapper.selectTaskCaseExecuteList(taskCaseExecute);
	}	
	
	/**
	 * 查询执行任务中所有执行未成功的用例
	 * @param taskId
	 * @return
	 * @author Seagull
	 * @date 2019年4月23日
	 */
	@Override
	public List<TaskCaseExecute> selectTaskCaseExecuteListForUnSucByTaskId(Integer taskId)
	{
	    return taskCaseExecuteMapper.selectTaskCaseExecuteListForUnSucByTaskId(taskId);
	}
	
	/**
	 * 根据任务ID查询用例执行列表
	 * @param taskId
	 * @return
	 * @author Seagull
	 * @date 2019年4月9日
	 */
	@Override
	public List<TaskCaseExecute> selectTaskCaseExecuteListByTaskId(Integer taskId)
	{
		TaskCaseExecute taskCaseExecute = new TaskCaseExecute();
		taskCaseExecute.setTaskId(taskId);
	    return taskCaseExecuteMapper.selectTaskCaseExecuteList(taskCaseExecute);
	}
	
    /**
     * 新增任务用例执行记录
     * 
     * @param taskCaseExecute 任务用例执行记录信息
     * @return 结果
     */
	@Override
	public int insertTaskCaseExecute(TaskCaseExecute taskCaseExecute)
	{
	    return taskCaseExecuteMapper.insertTaskCaseExecute(taskCaseExecute);
	}
	
	/**
     * 修改任务用例执行记录
     * 
     * @param taskCaseExecute 任务用例执行记录信息
     * @return 结果
     */
	@Override
	public int updateTaskCaseExecute(TaskCaseExecute taskCaseExecute)
	{
	    return taskCaseExecuteMapper.updateTaskCaseExecute(taskCaseExecute);
	}

	/**
     * 删除任务用例执行记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteTaskCaseExecuteByIds(String ids)
	{
		
		return taskCaseExecuteMapper.deleteTaskCaseExecuteByIds(Convert.toStrArray(ids));
	}
	
	/**
	 * 查询已执行的总用例数
	 * @return
	 * @author Seagull
	 * @date 2019年4月28日
	 */
    @Override
	public int selectTaskCaseExecuteCount()
	{
	    return taskCaseExecuteMapper.selectTaskCaseExecuteCount();
	}
    
	/**
	 * 查询已成功执行的总用例数
	 * @return
	 * @author Seagull
	 * @date 2019年4月28日
	 */
    @Override
	public int selectTaskCaseExecuteCountForSuccess()
	{
	    return taskCaseExecuteMapper.selectTaskCaseExecuteCountForSuccess();
	}
}
