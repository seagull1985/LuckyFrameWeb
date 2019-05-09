package com.luckyframe.common.constant;

/**
 * 调度任务常量信息
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改 有任何疑问欢迎联系作者讨论。 QQ:1573584944 Seagull
 * =================================================================
 * @author Seagull
 * @date 2019年2月28日
 */
public class JobConstants {
	
    /** 调度任务在JOB中相关参数定义 */
    public final static String JOB_GROUPNAME_FOR_TASKSCHEDULING = "自动化任务调度";
    public final static String JOB_JOBNAME_FOR_TASKSCHEDULING = "runAutomationTestTask";
    public final static String JOB_METHODNAME_FOR_TASKSCHEDULING = "runTask";
    
    /** 客户端监听在JOB中相关参数定义 */
    public final static String JOB_GROUPNAME_FOR_CLIENTHEART = "客户端心跳监听";
    public final static String JOB_JOBNAME_FOR_CLIENTHEART = "clientHeart";
    public final static String JOB_METHODNAME_FOR_CLIENTHEART = "heartTask";
    public final static String JOB_STATUS_FOR_CLIENTHEART = "0";
}
