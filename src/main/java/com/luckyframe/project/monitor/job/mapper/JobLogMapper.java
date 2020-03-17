package com.luckyframe.project.monitor.job.mapper;

import java.util.List;
import com.luckyframe.project.monitor.job.domain.JobLog;
import org.springframework.stereotype.Component;

/**
 * 调度任务日志信息 数据层
 * 
 * @author ruoyi
 */
@Component
public interface JobLogMapper
{
    /**
     * 获取quartz调度器日志的计划任务
     * 
     * @param jobLog 调度日志信息
     * @return 调度任务日志集合
     */
    List<JobLog> selectJobLogList(JobLog jobLog);
    
    /**
     * 查询所有调度任务日志
     *
     * @return 调度任务日志列表
     */
    List<JobLog> selectJobLogAll();

    /**
     * 通过调度任务日志ID查询调度信息
     * 
     * @param jobLogId 调度任务日志ID
     * @return 调度任务日志对象信息
     */
    JobLog selectJobLogById(Long jobLogId);

    /**
     * 新增任务日志
     * 
     * @param jobLog 调度日志信息
     * @return 结果
     */
    int insertJobLog(JobLog jobLog);

    /**
     * 批量删除调度日志信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteJobLogByIds(String[] ids);

    /**
     * 删除任务日志
     * 
     * @param jobId 调度日志ID
     * @return 结果
     */
    int deleteJobLogById(Long jobId);
    
    /**
     * 清空任务日志
     */
    void cleanJobLog();
}
