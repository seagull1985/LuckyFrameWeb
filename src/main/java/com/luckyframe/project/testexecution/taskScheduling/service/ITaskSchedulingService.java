package com.luckyframe.project.testexecution.taskScheduling.service;

import java.util.List;

import com.luckyframe.project.testexecution.taskScheduling.domain.TaskScheduling;

/**
 * 测试任务调度 服务层
 * 
 * @author luckyframe
 * @date 2019-03-23
 */
public interface ITaskSchedulingService 
{
	/**
     * 查询测试任务调度信息
     * 
     * @param schedulingId 测试任务调度ID
     * @return 测试任务调度信息
     */
	public TaskScheduling selectTaskSchedulingById(Integer schedulingId);
	
	/**
     * 查询测试任务调度列表
     * 
     * @param taskScheduling 测试任务调度信息
     * @return 测试任务调度集合
     */
	public List<TaskScheduling> selectTaskSchedulingList(TaskScheduling taskScheduling);
	
	/**
	 * 根据客户端ID查询调度列表
	 * @param clientId
	 * @return
	 * @author Seagull
	 * @date 2019年3月29日
	 */
	public List<TaskScheduling> selectTaskSchedulingListByClientId(Integer clientId);
	
	/**
	 * 根据项目ID查询调度列表
	 * @param projectId
	 * @return
	 * @author Seagull
	 * @date 2019年4月8日
	 */
	public List<TaskScheduling> selectTaskSchedulingListByProjectId(Integer projectId);
	
	/**
     * 新增测试任务调度
     * 
     * @param taskScheduling 测试任务调度信息
     * @return 结果
     */
	public int insertTaskScheduling(TaskScheduling taskScheduling);
	
	/**
     * 修改测试任务调度
     * 
     * @param taskScheduling 测试任务调度信息
     * @return 结果
     */
	public int updateTaskScheduling(TaskScheduling taskScheduling);
		
	/**
     * 删除测试任务调度信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteTaskSchedulingByIds(String ids);
	
    /**
     * 检查调度任务名称唯一性
     * @param taskScheduling
     * @return
     * @author Seagull
     * @date 2019年3月27日
     */
    public String checkSchedulingNameUnique(TaskScheduling taskScheduling);
}
