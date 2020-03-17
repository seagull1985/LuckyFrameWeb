package com.luckyframe.project.monitor.job.service;

import java.util.List;
import com.luckyframe.project.monitor.job.domain.Job;

/**
 * 定时任务调度信息信息 服务层
 * 
 * @author ruoyi
 */
public interface IJobService
{
    /**
     * 获取quartz调度器的计划任务
     * 
     * @param job 调度信息
     * @return 调度任务集合
     */
    List<Job> selectJobList(Job job);

    /**
     * 通过调度任务ID查询调度信息
     * 
     * @param jobId 调度任务ID
     * @return 调度任务对象信息
     */
    Job selectJobById(Long jobId);

    /**
     * 通过调度任务方法参数查询调度信息
     * @param methodParams job中的参数
     * @return 通过参数查询JOB
     */
    Job selectJobByMethodParams(String methodParams);

    /**
     * 暂停任务
     * 
     * @param job 调度信息
     * @return 结果
     */
    int pauseJob(Job job);
    
    /**
     * 停止单次任务
     * 
     * @param job 调度信息
     */
    int pauseOnceJob(Job job);

    /**
     * 恢复任务
     * 
     * @param job 调度信息
     * @return 结果
     */
    int resumeJob(Job job);

    /**
     * 删除任务后，所对应的trigger也将被删除
     * 
     * @param job 调度信息
     * @return 结果
     */
    int deleteJob(Job job);

    /**
     * 批量删除调度信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    void deleteJobByIds(String ids);

    /**
     * 任务调度状态修改
     * 
     * @param job 调度信息
     * @return 结果
     */
    int changeStatus(Job job);

    /**
     * 立即运行任务
     * 
     * @param job 调度信息
     * @return 结果
     */
    int run(Job job);

    /**
     * 新增任务表达式
     * 
     * @param job 调度信息
     * @return 结果
     */
    int insertJobCron(Job job);

    /**
     * 更新任务的时间表达式
     * 
     * @param job 调度信息
     * @return 结果
     */
    int updateJob(Job job);
    
    /**
     * 校验cron表达式是否有效
     * 
     * @param cronExpression 表达式
     * @return 结果
     */
    boolean checkCronExpressionIsValid(String cronExpression);
    
}
