package luckyweb.seagull.quartz;

import java.io.IOException;
import java.rmi.Naming;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.spring.entity.TestJobs;
import luckyweb.seagull.spring.entity.TestTaskexcute;
import luckyweb.seagull.spring.service.TestJobsService;
import luckyweb.seagull.spring.service.TestTastExcuteService;
import luckyweb.seagull.util.DateUtil;
import luckyweb.seagull.util.HibernateSessionFactoryUtil;
import rmi.model.RunBatchCaseEntity;
import rmi.model.RunCaseEntity;
import rmi.model.RunTaskEntity;
import rmi.service.RunService;

public class QuartzJob implements Job {
	private static final Logger log = Logger.getLogger(QuartzJob.class);
	@Resource(name = "tastExcuteService")
	private TestTastExcuteService	tastExcuteService;
	
	@Resource(name = "testJobsService")
	private TestJobsService	 testJobsService;
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			System.out.println("执行命令中。。。");
			String id = context.getJobDetail().getName();
			TestJobs job = null;
			for (int i = 0; i < QueueListener.list.size(); i++) {
				job = new TestJobs();
				job = QueueListener.list.get(i);
				if (id.equals("" + job.getId())) {
					break;
				}
			}
			toRunTask(job.getPlanproj(), job.getId(),job.getTaskName(),job.getClientip());
			System.out.println("调用程序结束。。。");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String toRunTask(String projname,int jobId,String jobname,String clientip) {
		Session s = null;
		Transaction tx = null;
		//System.out.println(tastId);
		String tastName = "";
		String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date().getTime());
		tastName ="【"+jobname+ "】_"+time;
		
		TestTaskexcute task=add(tastName, jobId, s, tx);
		projname = projname.replaceAll(" ","\" \""); //防止计划名称或是项目名称带了空格符号,在run.exec中会执行出错
		String result="启动失败！";
		try{
    		//调用远程对象，注意RMI路径与接口必须与服务器配置一致
    		RunService service=(RunService)Naming.lookup("rmi://"+clientip+":6633/RunService");
    		RunTaskEntity tasken = new RunTaskEntity();
    		tasken.setProjectname(projname);
    		tasken.setTaskid(String.valueOf(task.getId()));
    		result=service.runtask(tasken);
    		System.out.println(result);
    		return result;
		} catch (Exception e) {
			execFail(s, tx,task.getId(),jobId,tastName);
			e.printStackTrace();
			return result;
		}
			
	}
	
	
	public String toRunCase(String projname,int tastId,String  caseName,String casVersion,String clientip) {
		String result="启动失败！";
		try{
    		//调用远程对象，注意RMI路径与接口必须与服务器配置一致
    		RunService service=(RunService)Naming.lookup("rmi://"+clientip+":6633/RunService");
    		RunCaseEntity onecase = new RunCaseEntity();
    		onecase.setProjectname(projname);
    		onecase.setTaskid(String.valueOf(tastId));
    		onecase.setTestCaseExternalId(caseName);
    		onecase.setVersion(casVersion);
    		result=service.runcase(onecase);
    		System.out.println(result);
    		return result;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		} 
	}
	
	
	
	public String toRunCaseBatch(String projname,int taskid,String  caseInfo,String clientip) {
		String result="启动失败！";
		try{
    		//调用远程对象，注意RMI路径与接口必须与服务器配置一致
    		RunService service=(RunService)Naming.lookup("rmi://"+clientip+":6633/RunService");
    		RunBatchCaseEntity batchcase = new RunBatchCaseEntity();
    		batchcase.setProjectname(projname);
    		batchcase.setTaskid(String.valueOf(taskid));
    		batchcase.setBatchcase(caseInfo);
    		result=service.runbatchcase(batchcase);
    		System.out.println(result);
    		return result;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		} 
	}

	/**
	 * 加入 testJobs表
	 * @param tastId
	 * @param name
	 * @param jobId
	 * @param s
	 * @param tx
	 */
	private TestTaskexcute add(String tastName,int jobId,Session s,Transaction tx){
		TestTaskexcute tast = new TestTaskexcute();
		try {
			tast.getTestJob().setId(jobId);
			tast.setTaskId(tastName);
			tast.setCaseIsExec("0");
			tast.setTaskStatus("0");
			tast.setCreateTime(DateUtil.now());
			s = HibernateSessionFactoryUtil.openSession();
			tx = s.beginTransaction();
			s.save(tast);
			System.out.println("tastId:"+tast.getId());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			s.close();

		}
		return tast;
	}
	
	private void execFail(Session s,Transaction tx,int id,int jobId,String tastId){
		//执行失败时,写入任务表记录为执行失败
		TestTaskexcute tast = new TestTaskexcute();
		s = HibernateSessionFactoryUtil.openSession();
		tx = s.beginTransaction();
		tast=(TestTaskexcute) s.load(TestTaskexcute.class, id);
		tast.setTaskStatus("3");
		tast.setJobid(jobId);
		tast.setTaskId(tastId);
		tast.setCaseIsExec("1");
		tast.setTaskStatus("3");
		s.update(tast);
		tx.commit();
		s.close();
		
	}
	
	
	public static void main(String[] args) throws IOException {
	}
}
