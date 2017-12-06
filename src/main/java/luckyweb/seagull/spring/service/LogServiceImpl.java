package luckyweb.seagull.spring.service;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
@Aspect
public class LogServiceImpl
{
	Logger	log	= Logger.getLogger(LogServiceImpl.class);

	@Pointcut("execution(* com.yangl.spring.dao..*.*(..))")
	public void pointcut()
	{
		System.out.println("pointcut  function  exec .. ");
	}

	@Before("pointcut()")
	public void before(String name)
	{
		log.info("开始执行:" + name);
	}

	@After("pointcut()")
	public void after(String name)
	{
		log.info("结束执行:" + name);
	}

	@Around("")
	public void around(JoinPoint point, Object returnObj)
	{

	}

}
