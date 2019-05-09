package com.luckyframe.project.monitor.job.util;

import java.util.Date;
import java.util.concurrent.Future;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.luckyframe.common.constant.Constants;
import com.luckyframe.common.constant.JobConstants;
import com.luckyframe.common.constant.ScheduleConstants;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.bean.BeanUtils;
import com.luckyframe.common.utils.spring.SpringUtils;
import com.luckyframe.project.monitor.job.domain.Job;
import com.luckyframe.project.monitor.job.domain.JobLog;
import com.luckyframe.project.monitor.job.service.IJobLogService;
import com.luckyframe.project.monitor.job.service.IJobService;

/**
 * 定时任务
 * 
 * @author ruoyi
 *
 */
@DisallowConcurrentExecution
public class ScheduleJob extends QuartzJobBean
{    
    private static final Logger log = LoggerFactory.getLogger(ScheduleJob.class);

    private ThreadPoolTaskExecutor executor = SpringUtils.getBean("threadPoolTaskExecutor");

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException
    {
        Job job = new Job();
        BeanUtils.copyBeanProp(job, context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES));

        IJobLogService jobLogService = (IJobLogService) SpringUtils.getBean(IJobLogService.class);
        
        IJobService jobService = (IJobService) SpringUtils.getBean(IJobService.class);

        JobLog jobLog = new JobLog();
        jobLog.setJobName(job.getJobName());
        jobLog.setJobGroup(job.getJobGroup());
        jobLog.setMethodName(job.getMethodName());
        jobLog.setMethodParams(job.getMethodParams());
        jobLog.setCreateTime(new Date());

        long startTime = System.currentTimeMillis();

        try
        {
            // 执行任务
        	if(!JobConstants.JOB_JOBNAME_FOR_CLIENTHEART.equals(jobLog.getJobName())){
                log.info("任务开始执行 - 名称：{} 方法：{}", job.getJobName(), job.getMethodName());
        	}  
            ScheduleRunnable task = new ScheduleRunnable(job.getJobName(), job.getMethodName(), job.getMethodParams());
            Future<?> future = executor.submit(task);
            future.get();
            long times = System.currentTimeMillis() - startTime;
            // 任务状态 0：成功 1：失败
            jobLog.setStatus(Constants.SUCCESS);
            jobLog.setJobMessage(job.getJobName() + " 总共耗时：" + times + "毫秒");

        	if(!JobConstants.JOB_JOBNAME_FOR_CLIENTHEART.equals(jobLog.getJobName())){
        		log.info("任务执行结束 - 名称：{} 耗时：{} 毫秒", job.getJobName(), times);
        	}           
            
            /*如果是一次性的任务，运行后暂停*/
            if("2".equals(job.getMisfirePolicy())){
            	jobService.pauseOnceJob(job);
            }
        }
        catch (Exception e)
        {
            log.info("任务执行失败 - 名称：{} 方法：{}", job.getJobName(), job.getMethodName());
            log.error("任务执行异常  - ：", e);
            long times = System.currentTimeMillis() - startTime;
            jobLog.setJobMessage(job.getJobName() + " 总共耗时：" + times + "毫秒");
            // 任务状态 0：成功 1：失败
            jobLog.setStatus(Constants.FAIL);
            jobLog.setExceptionInfo(StringUtils.substring(e.getMessage(), 0, 2000));
        }
        finally
        {
        	if(!JobConstants.JOB_JOBNAME_FOR_CLIENTHEART.equals(jobLog.getJobName())){
        		jobLogService.addJobLog(jobLog);
        	}           
        }
    }
}
