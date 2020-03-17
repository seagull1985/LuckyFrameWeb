package com.luckyframe.project.testexecution.taskScheduling.service;

import java.util.List;

import com.luckyframe.project.testexecution.taskScheduling.domain.TaskScheduling;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * 测试任务调度 服务层
 * 
 * @author luckyframe
 * @date 2019-03-23
 */
@Component
@Primary
public interface ITaskSchedulingService 
{
	/**
     * 查询测试任务调度信息
     * 
     * @param schedulingId 测试任务调度ID
     * @return 测试任务调度信息
     */
	TaskScheduling selectTaskSchedulingById(Integer schedulingId);
	
	/**
     * 查询测试任务调度列表
     * 
     * @param taskScheduling 测试任务调度信息
     * @return 测试任务调度集合
     */
	List<TaskScheduling> selectTaskSchedulingList(TaskScheduling taskScheduling);
	
	/**
	 * 根据客户端ID查询调度列表
	 * @param clientId 客户端ID
	 * @author Seagull
	 * @date 2019年3月29日
	 */
	List<TaskScheduling> selectTaskSchedulingListByClientId(Integer clientId);
	
	/**
	 * 根据项目ID查询调度列表
	 * @param projectId 项目ID
	 * @author Seagull
	 * @date 2019年4月8日
	 */
	List<TaskScheduling> selectTaskSchedulingListByProjectId(Integer projectId);
	
	/**
     * 新增测试任务调度
     * 
     * @param taskScheduling 测试任务调度信息
     * @return 结果
     */
	int insertTaskScheduling(TaskScheduling taskScheduling);
	
	/**
     * 修改测试任务调度
     * 
     * @param taskScheduling 测试任务调度信息
     * @return 结果
     */
	int updateTaskScheduling(TaskScheduling taskScheduling);
		
	/**
     * 删除测试任务调度信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	int deleteTaskSchedulingByIds(String ids);
	
    /**
     * 检查调度任务名称唯一性
     * @param taskScheduling 调度任务对象
     * @author Seagull
     * @date 2019年3月27日
     */
    String checkSchedulingNameUnique(TaskScheduling taskScheduling);
    
    /**
     * 根据调度名称查询调度对象
     * @param schedulingName 调度任务名称
     * @author Seagull
     * @date 2019年9月4日
     */
    TaskScheduling selectTaskSchedulingByName(String schedulingName);
}
