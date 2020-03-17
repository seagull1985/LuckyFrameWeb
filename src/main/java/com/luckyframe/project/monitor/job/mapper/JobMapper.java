package com.luckyframe.project.monitor.job.mapper;

import java.util.List;

import com.luckyframe.project.monitor.job.domain.Job;
import org.springframework.stereotype.Component;

/**
 * 调度任务信息 数据层
 * 
 * @author ruoyi
 */
@Component
public interface JobMapper
{
    /**
     * 查询调度任务日志集合
     * 
     * @param job 调度信息
     * @return 操作日志集合
     */
    List<Job> selectJobList(Job job);

    /**
     * 查询所有调度任务
     * 
     * @return 调度任务列表
     */
    List<Job> selectJobAll();

    /**
     * 通过调度ID查询调度任务信息
     * 
     * @param jobId 调度ID
     * @return 角色对象信息
     */
    Job selectJobById(Long jobId);
    
    /**
     * 通过调度参数查询调度任务信息
     * @param methodParams 方法参数
     * @author Seagull
     * @date 2019年3月29日
     */
    Job selectJobByMethodParams(String methodParams);

    /**
     * 通过调度ID删除调度任务信息
     * 
     * @param jobId 调度ID
     * @return 结果
     */
    int deleteJobById(Long jobId);

    /**
     * 批量删除调度任务信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteJobByIds(Long[] ids);

    /**
     * 修改调度任务信息
     * 
     * @param job 调度任务信息
     * @return 结果
     */
    int updateJob(Job job);

    /**
     * 新增调度任务信息
     * 
     * @param job 调度任务信息
     * @return 结果
     */
    int insertJob(Job job);
}
