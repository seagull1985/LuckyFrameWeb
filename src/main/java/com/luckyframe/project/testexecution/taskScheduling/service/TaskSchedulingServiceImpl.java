package com.luckyframe.project.testexecution.taskScheduling.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyframe.common.constant.TaskSchedulingConstants;
import com.luckyframe.common.exception.BusinessException;
import com.luckyframe.common.support.Convert;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.security.PermissionUtils;
import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.project.monitor.job.domain.Job;
import com.luckyframe.project.monitor.job.service.IJobService;
import com.luckyframe.project.testexecution.taskExecute.mapper.TaskExecuteMapper;
import com.luckyframe.project.testexecution.taskScheduling.domain.TaskScheduling;
import com.luckyframe.project.testexecution.taskScheduling.mapper.TaskSchedulingMapper;

/**
 * 测试任务调度 服务层实现
 * 
 * @author luckyframe
 * @date 2019-03-23
 */
@Service
public class TaskSchedulingServiceImpl implements ITaskSchedulingService 
{
	@Autowired
	private TaskSchedulingMapper taskSchedulingMapper;
	
	@Autowired
	private TaskExecuteMapper taskExecuteMapper;
	
    @Autowired
    private IJobService jobService;

	/**
     * 查询测试任务调度信息
     * 
     * @param schedulingId 测试任务调度ID
     * @return 测试任务调度信息
     */
    @Override
	public TaskScheduling selectTaskSchedulingById(Integer schedulingId)
	{
	    return taskSchedulingMapper.selectTaskSchedulingById(schedulingId);
	}
	
	/**
     * 查询测试任务调度列表
     * 
     * @param taskScheduling 测试任务调度信息
     * @return 测试任务调度集合
     */
	@Override
	public List<TaskScheduling> selectTaskSchedulingList(TaskScheduling taskScheduling)
	{
	    return taskSchedulingMapper.selectTaskSchedulingList(taskScheduling);
	}
	
	/**
	 * 根据客户端ID查询调度列表
	 * @param clientId 客户端ID
	 * @author Seagull
	 * @date 2019年3月29日
	 */
	@Override
	public List<TaskScheduling> selectTaskSchedulingListByClientId(Integer clientId)
	{
		TaskScheduling taskScheduling = new TaskScheduling();
		taskScheduling.setClientId(clientId);
	    return taskSchedulingMapper.selectTaskSchedulingList(taskScheduling);
	}
	
	/**
	 * 根据项目ID查询调度列表
	 * @param projectId 项目ID
	 * @author Seagull
	 * @date 2019年4月8日
	 */
	@Override
	public List<TaskScheduling> selectTaskSchedulingListByProjectId(Integer projectId)
	{
		TaskScheduling taskScheduling = new TaskScheduling();
		taskScheduling.setProjectId(projectId);
	    return taskSchedulingMapper.selectTaskSchedulingList(taskScheduling);
	}
	
    /**
     * 新增测试任务调度
     * 
     * @param taskScheduling 测试任务调度信息
     * @return 结果
     */
	@Override
	public int insertTaskScheduling(TaskScheduling taskScheduling)
	{
		taskScheduling.setCreateBy(ShiroUtils.getLoginName());
		taskScheduling.setCreateTime(new Date());
		taskScheduling.setUpdateBy(ShiroUtils.getLoginName());
		taskScheduling.setUpdateTime(new Date());
		
	    return taskSchedulingMapper.insertTaskScheduling(taskScheduling);
	}
	
	/**
     * 修改测试任务调度
     * 
     * @param taskScheduling 测试任务调度信息
     * @return 结果
     */
	@Override
	public int updateTaskScheduling(TaskScheduling taskScheduling)
	{
		taskScheduling.setUpdateBy(ShiroUtils.getLoginName());
		taskScheduling.setUpdateTime(new Date());
	    return taskSchedulingMapper.updateTaskScheduling(taskScheduling);
	}

	/**
     * 删除测试任务调度对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteTaskSchedulingByIds(String ids)
	{
		String[] tsIds = Convert.toStrArray(ids);
		int result=0;
		for(String tsId:tsIds){
			TaskScheduling taskScheduling = taskSchedulingMapper.selectTaskSchedulingById(Integer.valueOf(tsId));
			
			if(taskExecuteMapper.selectTaskExecuteCountBySchedulingId(taskScheduling.getSchedulingId())>0){
				throw new BusinessException(String.format("【%1$s】已有执行记录,请先删除任务记录", taskScheduling.getSchedulingName()));
			}
			
			if(!PermissionUtils.isProjectPermsPassByProjectId(taskScheduling.getProjectId())){	
				  throw new BusinessException(String.format("调度【%1$s】没有项目删除权限", taskScheduling.getSchedulingName()));
			}
			
			Job job = taskScheduling.getJob();
			jobService.deleteJob(job);
			taskSchedulingMapper.deleteTaskSchedulingById(taskScheduling.getSchedulingId());
			result++;
		}
		return result;
	}
	
    /**
     * 检查调度任务名称唯一性
     * @param taskScheduling 调度任务对象
     * @author Seagull
     * @date 2019年3月27日
     */
    @Override
    public String checkSchedulingNameUnique(TaskScheduling taskScheduling)
    {
        long schedulingId = StringUtils.isNull(taskScheduling.getSchedulingId()) ? -1L : taskScheduling.getSchedulingId();
        TaskScheduling info = taskSchedulingMapper.checkSchedulingNameUnique(taskScheduling.getSchedulingName());
        if (StringUtils.isNotNull(info) && info.getSchedulingId().longValue() != schedulingId)
        {
            return TaskSchedulingConstants.TASKSCHEDULING_NAME_NOT_UNIQUE;
        }
        return TaskSchedulingConstants.TASKSCHEDULING_NAME_UNIQUE;
    }
    
    /**
     * 根据调度名称查询调度对象
     * @param schedulingName 调度名称
     * @author Seagull
     * @date 2019年9月4日
     */
    @Override
    public TaskScheduling selectTaskSchedulingByName(String schedulingName)
    {
        return taskSchedulingMapper.checkSchedulingNameUnique(schedulingName);
    }
}
