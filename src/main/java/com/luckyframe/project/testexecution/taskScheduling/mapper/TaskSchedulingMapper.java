package com.luckyframe.project.testexecution.taskScheduling.mapper;

import java.util.List;

import com.luckyframe.project.testexecution.taskScheduling.domain.TaskScheduling;
import org.springframework.stereotype.Component;

/**
 * 测试任务调度 数据层
 * 
 * @author luckyframe
 * @date 2019-03-23
 */
@Component
public interface TaskSchedulingMapper 
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
     * 删除测试任务调度
     * 
     * @param schedulingId 测试任务调度ID
     * @return 结果
     */
	int deleteTaskSchedulingById(Integer schedulingId);
	
	/**
     * 批量删除测试任务调度
     * 
     * @param schedulingIds 需要删除的数据ID
     * @return 结果
     */
	int deleteTaskSchedulingByIds(String[] schedulingIds);
	
	/**
	 * 检查调度任务名称唯一性
	 * @param schedulingName 调度任务名称
	 * @author Seagull
	 * @date 2019年3月27日
	 */
	TaskScheduling checkSchedulingNameUnique(String schedulingName);
	
    /**
     * 查询项目下有没有调度
     * @param projectId 项目ID
     * @author Seagull
     * @date 2019年2月28日
     */
    int selectTaskSchedulingCountByProjectId(Integer projectId);
    
    /**
     * 查询客户端下有没有调度
     * @param clientId 客户端ID
     * @author Seagull
     * @date 2019年2月28日
     */
    int selectTaskSchedulingCountByClientId(Integer clientId);
    
    /**
     * 查询测试计划下有没有调度
     * @param planId 测试计划ID
     * @author Seagull
     * @date 2019年4月12日
     */
    int selectTaskSchedulingCountByPlanId(Integer planId);
}