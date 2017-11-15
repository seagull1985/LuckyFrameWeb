package luckyweb.seagull.spring.service;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

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
