package luckyweb.seagull.quartz;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.spring.entity.TestClient;
import luckyweb.seagull.spring.entity.TestJobs;
import luckyweb.seagull.spring.entity.TestTaskexcute;
import luckyweb.seagull.spring.service.TestJobsService;
import luckyweb.seagull.spring.service.TestTastExcuteService;
import luckyweb.seagull.util.DateUtil;
import luckyweb.seagull.util.HibernateSessionFactoryUtil;
import luckyweb.seagull.util.StrLib;
import rmi.model.RunBatchCaseEntity;
import rmi.model.RunCaseEntity;
import rmi.model.RunTaskEntity;
import rmi.service.RunService;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 * 
 */
public class QuartzJob implements Job {
	private static final Logger log = Logger.getLogger(QuartzJob.class);
	@Resource(name = "tastExcuteService")
	private TestTastExcuteService	tastExcuteService;
	
	@Resource(name = "testJobsService")
	private TestJobsService	 testJobsService;
	
	public static Session session = HibernateSessionFactoryUtil.getCurrentSession();
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			String name = context.getJobDetail().getName();
			
			if(name.indexOf(PublicConst.JOBTASKNAMETYPE)>-1){
				TestJobs job = new TestJobs();
				String id=name.substring(0,name.indexOf(PublicConst.JOBTASKNAMETYPE));
				System.out.println("执行命令中。。。");
				for (int i = 0; i < QueueListener.list.size(); i++) {
					job = QueueListener.list.get(i);
					if (id.equals("" + job.getId())) {
						break;
					}else{
						job=null;
					}
				}
				if(null!=job){
					toRunTask(job.getPlanproj(), job.getId(),job.getTaskName(),job.getClientip(),job.getClientpath());	
					System.out.println("调用程序结束。。。");
				}else{
					System.out.println("没有定时任务需要启动。。。");
				}
			}else if(name.indexOf(PublicConst.JOBCLIENTNAMETYPE)>-1){
				TestClient tc = new TestClient();
				String id=name.substring(0,name.indexOf(PublicConst.JOBCLIENTNAMETYPE));
				for (int i = 0; i < QueueListener.listen_Clientlist.size(); i++) {
					tc = QueueListener.listen_Clientlist.get(i);
					if (id.equals("" + tc.getId())) {
						if(null!=tc){
							String clientip=tc.getClientip();
							try{
							 //调用远程对象，注意RMI路径与接口必须与服务器配置一致
							RunService service=(RunService)Naming.lookup("rmi://"+clientip+":6633/RunService");
							String result=service.getClientStatus();
							if("success".equals(result)){
								if(tc.getStatus()!=0){
									tc.setStatus(0);
									QueueListener.listen_Clientlist.set(i, tc);
									Query query = session.createQuery("update TestClient t set t.status =0  where id="+tc.getId());
									query.executeUpdate();
								}
							}else{
								log.error("【IP:"+tc.getClientip()+"】检查客户端异常！");
								if(tc.getStatus()!=1){
									tc.setStatus(1);
									QueueListener.listen_Clientlist.set(i, tc);
									Query query = session.createQuery("update TestClient t set t.status =1  where id="+tc.getId());
									query.executeUpdate();
									log.error("【IP:"+tc.getClientip()+"】客户端异常，修改客户端状态！");
								}
							}
							}catch (RemoteException e) {
								log.error(e);
								log.error("【IP:"+tc.getClientip()+"】检查客户端异常(RemoteException)！");
								if(tc.getStatus()!=1){
									tc.setStatus(1);
									QueueListener.listen_Clientlist.set(i, tc);
									Query query = session.createQuery("update TestClient t set t.status =1  where id="+tc.getId());
									query.executeUpdate();
									log.error("【IP:"+tc.getClientip()+"】客户端异常(RemoteException)，修改客户端状态！");
								}
								break;
							}
						}
						break;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String toRunTask(String projname,int jobId,String jobname,String clientip,String loadpath){
		Session s = null;
		Transaction tx = null;
		//System.out.println(tastId);
		String tastName = "";
		String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date().getTime());
		tastName ="【"+jobname+ "】_"+time;
		
		TestTaskexcute task=add(tastName, jobId, s, tx);
		//防止计划名称或是项目名称带了空格符号,在run.exec中会执行出错
		projname = projname.replaceAll(" ","\" \""); 
		String result="启动失败！";
		try{
			 //调用远程对象，注意RMI路径与接口必须与服务器配置一致
			RunService service=(RunService)Naming.lookup("rmi://"+clientip+":6633/RunService"); 

    		RunTaskEntity tasken = new RunTaskEntity();
    		tasken.setProjectname(projname);
    		tasken.setTaskid(String.valueOf(task.getId()));
    		if(StrLib.isEmpty(loadpath)){
    			loadpath="/TestDriven";
    		}
    		result=service.runtask(tasken,loadpath);
    		System.out.println(result);
    		return result;
		}catch (Exception e) {
			execFail(s, tx,task.getId(),jobId,tastName);
			e.printStackTrace();
			return result;
		}
			
	}
	
	
	public String toRunCase(String projname,int tastId,String  caseName,String casVersion,String clientip,String loadpath) {
		String result="启动失败！";
		try{
    		//调用远程对象，注意RMI路径与接口必须与服务器配置一致
    		RunService service=(RunService)Naming.lookup("rmi://"+clientip+":6633/RunService");
    		RunCaseEntity onecase = new RunCaseEntity();
    		onecase.setProjectname(projname);
    		onecase.setTaskid(String.valueOf(tastId));
    		onecase.setTestCaseExternalId(caseName);
    		onecase.setVersion(casVersion);
    		if(StrLib.isEmpty(loadpath)){
    			loadpath="/TestDriven";
    		}
    		result=service.runcase(onecase,loadpath);
    		System.out.println(result);
    		return result;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		} 
	}
	
	
	
	public String toRunCaseBatch(String projname,int taskid,String  caseInfo,String clientip,String loadpath) {
		String result="启动失败！";
		try{
    		//调用远程对象，注意RMI路径与接口必须与服务器配置一致
    		RunService service=(RunService)Naming.lookup("rmi://"+clientip+":6633/RunService");
    		RunBatchCaseEntity batchcase = new RunBatchCaseEntity();
    		batchcase.setProjectname(projname);
    		batchcase.setTaskid(String.valueOf(taskid));
    		batchcase.setBatchcase(caseInfo);
    		if(StrLib.isEmpty(loadpath)){
    			loadpath="/TestDriven";
    		}
    		result=service.runbatchcase(batchcase,loadpath);
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
