package com.luckyframe.project.testexecution.taskExecute.mapper;

import java.util.Date;
import java.util.List;

import com.luckyframe.project.testexecution.taskExecute.domain.TaskExecute;	

/**
 * 测试任务执行 数据层
 * 
 * @author luckyframe
 * @date 2019-04-08
 */
public interface TaskExecuteMapper 
{
	/**
     * 查询测试任务执行信息
     * 
     * @param taskId 测试任务执行ID
     * @return 测试任务执行信息
     */
	public TaskExecute selectTaskExecuteById(Integer taskId);
	
	/**
	 * 查询最后一条测试任务执行信息
	 * @return
	 * @author Seagull
	 * @date 2019年4月24日
	 */
	public TaskExecute selectTaskExecuteLastRecord();
	
	/**
	 * 查询当前项目下最后一条执行记录
	 * @param projectId
	 * @return
	 * @author Seagull
	 * @date 2019年5月15日
	 */
	public TaskExecute selectTaskExecuteLastRecordForProjectId(Integer projectId);
	
	/**
	 * 根据任务执行名称查询实体
	 * @param projectId
	 * @return
	 * @author Seagull
	 * @date 2019年6月18日
	 */
	public TaskExecute selectTaskExecuteByTaskName(String taskName);
	
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
     * 删除测试任务执行
     * 
     * @param taskId 测试任务执行ID
     * @return 结果
     */
	public int deleteTaskExecuteById(Integer taskId);
	
	/**
     * 批量删除测试任务执行
     * 
     * @param taskIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteTaskExecuteByIds(String[] taskIds);
	
    /**
     * 查询项目下有没有执行任务
     * @param projectId
     * @return
     * @author Seagull
     * @date 2019年4月12日
     */
    public int selectTaskExecuteCountByProjectId(Integer projectId);
    
    /**
     * 查询调度下有没有执行任务
     * @param schedulingId
     * @return
     * @author Seagull
     * @date 2019年4月13日
     */
    public int selectTaskExecuteCountBySchedulingId(Integer schedulingId);
    
    /**
     * 查询最早的任务执行日期
     * @return
     * @author Seagull
     * @date 2019年4月28日
     */
    public Date selectTaskExecuteMinData();
    
    /**
     * 查询所有任务数
     * @return
     * @author Seagull
     * @date 2019年4月28日
     */
    public int selectTaskExecuteCount();
    
    /**
     * 查询指定天数内的任务执行统计数据
     * @param updateTime
     * @return
     * @author Seagull
     * @date 2019年4月29日
     */
    public List<TaskExecute> selectTaskExecuteListForThirtyDays(String updateTime);
}