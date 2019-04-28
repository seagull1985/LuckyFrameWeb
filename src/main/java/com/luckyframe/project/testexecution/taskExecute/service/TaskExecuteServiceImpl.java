package com.luckyframe.project.testexecution.taskExecute.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyframe.common.exception.BusinessException;
import com.luckyframe.common.support.Convert;
import com.luckyframe.common.utils.security.PermissionUtils;
import com.luckyframe.project.testexecution.taskCaseExecute.mapper.TaskCaseExecuteMapper;
import com.luckyframe.project.testexecution.taskCaseLog.mapper.TaskCaseLogMapper;
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
	
	@Autowired
	private TaskCaseExecuteMapper taskCaseExecuteMapper;
	
	@Autowired
	private TaskCaseLogMapper taskCaseLogMapper;

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
	 * 查询最后一条执行记录
	 * @return
	 * @author Seagull
	 * @date 2019年4月24日
	 */
    @Override
	public TaskExecute selectTaskExecuteLastRecord()
	{
	    return taskExecuteMapper.selectTaskExecuteLastRecord();
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
		taskExecute.setCreateBy("自动创建");
		taskExecute.setCreateTime(new Date());
		taskExecute.setUpdateBy("自动创建");
		taskExecute.setUpdateTime(new Date());
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
		taskExecute.setUpdateTime(new Date());
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
		String[] taskIds=Convert.toStrArray(ids);
		Integer result=0;
		for(String taskIdstr:taskIds){
			Integer taskId=Integer.valueOf(taskIdstr);
			TaskExecute taskExecute = taskExecuteMapper.selectTaskExecuteById(taskId);
			
			if(!PermissionUtils.isProjectPermsPassByProjectId(taskExecute.getProjectId())){	
				  throw new BusinessException(String.format("执行任务【%1$s】没有项目删除权限", taskExecute.getTaskName()));
			}
			
			//30分钟内的任务不允许删除，防止客户端提交脏数据
			Date date = new Date(System.currentTimeMillis()-1000*60*30);
			boolean isTaskDeleteTime = ("0".equals(taskExecute.getTaskStatus()) || "1".equals(taskExecute.getTaskStatus()))
					&& taskExecute.getUpdateTime().after(date);			
			if (isTaskDeleteTime) {
				throw new BusinessException(String.format("执行中的任务【%1$s】必须30分钟后才可以删除", taskExecute.getTaskName()));
			}
			
			taskCaseLogMapper.deleteTaskCaseLogByTaskId(taskId);
			taskCaseExecuteMapper.deleteTaskCaseExecuteByTaskId(taskId);
			taskExecuteMapper.deleteTaskExecuteById(taskId);
			result++;
		}
		
		return result;
	}
	
}
