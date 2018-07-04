package luckyweb.seagull.spring.mvc;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.Naming;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.quartz.QuartzJob;
import luckyweb.seagull.quartz.QuartzManager;
import luckyweb.seagull.quartz.QuratzJobDataMgr;
import luckyweb.seagull.spring.entity.ProjectPlan;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.entity.TestClient;
import luckyweb.seagull.spring.entity.TestJobs;
import luckyweb.seagull.spring.entity.UserInfo;
import luckyweb.seagull.spring.service.CaseDetailService;
import luckyweb.seagull.spring.service.LogDetailService;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.ProjectPlanService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.spring.service.TestClientService;
import luckyweb.seagull.spring.service.TestJobsService;
import luckyweb.seagull.spring.service.TestTastExcuteService;
import luckyweb.seagull.spring.service.UserInfoService;
import luckyweb.seagull.util.DateLib;
import luckyweb.seagull.util.DateUtil;
import luckyweb.seagull.util.StrLib;
import rmi.service.RunService;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
@Controller
@RequestMapping("/testJobs")
public class TestJobsController
{
	private static final Logger	log	     = Logger.getLogger(TestJobsController.class);
	
	@Resource(name = "testJobsService")
	private TestJobsService	 testJobsService;
	
	@Resource(name = "tastExcuteService")
	private TestTastExcuteService	tastExcuteService;
	
	@Resource(name = "casedetailService")
	private CaseDetailService	casedetailService;
	
	@Resource(name = "projectPlanService")
	private ProjectPlanService projectplanservice;
	
	@Resource(name = "logdetailService")
	private LogDetailService	logdetailService;
	
	@Resource(name = "operationlogService")
	private OperationLogService operationlogservice;

	@Resource(name = "userinfoService")
	private UserInfoService userinfoservice;
	
	@Resource(name = "sectorprojectsService")
	private SectorProjectsService sectorprojectsService;
	
	@Resource(name = "testclientService")
	private TestClientService tcservice;
	
	/**
	 * 
	 * 
	 * @param tj
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/load.do")
	public String load(HttpServletRequest req, Model model) throws Exception {

		try {
			int projectid = 99;
			if (null != req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE)
					&& null != req.getSession().getAttribute(PublicConst.SESSIONKEYUSERNAME)) {
				String usercode = req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE).toString();
				UserInfo userinfo = userinfoservice.getUseinfo(usercode);
				projectid = userinfo.getProjectid();
			}

			List<SectorProjects> prolist=sectorprojectsService.getAllProject();
			for(int i=0;i<prolist.size();i++){
				if(prolist.get(i).getProjecttype()==1){
					prolist.get(i).setProjectname(prolist.get(i).getProjectname()+"(TestLink项目)");
				}
			}
			
			List<TestClient> iplist = tcservice.getClientList();
			for(TestClient tc:iplist){
				if(tc.getStatus()==0){
					tc.setName("【"+tc.getName()+"】"+tc.getClientip()+" 【客户端状态正常】");
				}else if(tc.getStatus()==1){
					tc.setName("【"+tc.getName()+"】"+tc.getClientip()+" 【客户端状态异常】");
				}else{
					tc.setName("【"+tc.getName()+"】"+tc.getClientip()+" 【客户端状态未知】");
				}
			}
			model.addAttribute("iplist", iplist);
			
			model.addAttribute("projects", prolist);
			model.addAttribute("projectid", projectid);
			model.addAttribute("date", DateLib.today("yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/testJobs/load.do");
			return "error";
		}
		return "/jsp/task/job_list";
	}

	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/list.do")
	private void ajaxGetSellRecord(Integer limit, Integer offset, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		String search = request.getParameter("search");
		String projectid = request.getParameter("projectid");
		TestJobs tj= new TestJobs();
		if (null == offset && null == limit) {
			offset = 0;
		}
		if (null == limit || limit == 0) {
			limit = 10;
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(search)) {
			tj.setTaskName(search);
            tj.setPlanproj(search);
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(projectid)) {
			tj.setProjectid(Integer.valueOf(projectid));
		}

		List<TestJobs> jobs = testJobsService.findByPage(tj, offset, limit);
		List<TestClient> iplist = tcservice.getClientList();
		for(TestJobs tjs:jobs){
			for(TestClient tc:iplist){
				if(tc.getClientip().equals(tjs.getClientip())){
					tjs.setClientip(tjs.getClientip()+"**"+tc.getStatus());
				}
			}
		}
		// 转换成json字符串
		JSONArray recordJson = StrLib.listToJson(jobs);
		// 得到总记录数
		int total = testJobsService.findRows(tj);
		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", recordJson);
		pw.print(json.toString());
	}
	
	/**
	 * 新增Job
	 * @param tj
	 * @param br
	 * @param model
	 * @param req
	 * @param rsp
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/add.do")
	public String add(@Valid @ModelAttribute("taskjob") TestJobs tj, BindingResult br, Model model,
	        HttpServletRequest req, HttpServletResponse rsp) throws Exception
	{
		try
		{
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			
			if(!UserLoginController.permissionboolean(req, PublicConst.AUTHJOBADD)){
				model.addAttribute("taskjob", new TestJobs());
				model.addAttribute("url",  "/testJobs/load.do");
				model.addAttribute("message", "当前用户无权限添加计划任务，请联系管理员！");
				return "success";
			}
			
			List<SectorProjects> qaprolist=QueueListener.qa_projlist;
			List<SectorProjects> prolist=QueueListener.projlist;
			String retVal = "/jsp/task/task_add";
			if (PublicConst.REQPOSTTYPE.equals(req.getMethod()))
			{

				if (br.hasErrors())
				{
					return retVal;
				}
				
				String message = "";
				boolean existed = tj.getProjecttype()==1&&(StrLib.isEmpty(tj.getPlanproj())||"0".equals(tj.getPlanproj()));
				if (existed)
				{
					message = "项目名必选!";
					model.addAttribute("message", message);
					return retVal;
				}else if(tj.getProjecttype()==1){
					if(!UserLoginController.oppidboolean(req, Integer.valueOf(tj.getPlanproj()))){
						model.addAttribute("message", "当前用户无权限添加当前项目的调度任务，请联系管理员！");
						return retVal;
					}
				}
				
				if (tj.getProjecttype()==0&&tj.getProjectid()==0)
				{
					message = "项目名必选!";
					model.addAttribute("message", message);
					return retVal;
				}else if(tj.getProjecttype()==0){
					if(!UserLoginController.oppidboolean(req, tj.getProjectid())){
						model.addAttribute("message", "当前用户无权限添加当前项目的调度任务，请联系管理员！");
						return retVal;
					}
				}
				
				if (StrLib.isEmpty(tj.getClientip())||PublicConst.STATUSSTR0.equals(tj.getClientip())) {
					message = "客户端IP不能为空！";
					model.addAttribute("message", message);
					return retVal;
				}
				
				if (StrLib.isEmpty(tj.getClientpath())||PublicConst.STATUSSTR0.equals(tj.getClientpath())) {
					message = "客户端驱动桩路径不能为空！";
					model.addAttribute("message", message);
					return retVal;
				}
				
				if (StrLib.isEmpty(tj.getTaskName())||PublicConst.STATUSSTR0.equals(tj.getTaskName()))
				{
					message = "计划名称不能为空!";
					model.addAttribute("message", message);
					return retVal;
				}
				int threadMax=20;
				if (tj.getThreadCount() < 1 || tj.getThreadCount() > threadMax)
				{
					message = "线程数在1-20之间";
					model.addAttribute("message", message);
					return retVal;
				}
				int timeOutMax=120;
				if (tj.getTimeout() < 1 || tj.getTimeout() > timeOutMax)
				{
					message = "超时时间必须在1-120分钟之间";
					model.addAttribute("message", message);
					return retVal;
				}

				String startDate = DateLib.today("yyyy-MM-dd");
				String startTime = DateLib.today("HH:mm:ss");
				tj.setStartDate(startDate);
				tj.setStartTime(startTime);

				tj.setEndTimestr(null);
				tj.setState("1");
				long runtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
				        tj.getStartDate() + " " + tj.getStartTime()).getTime();
				tj.setRunTime(new Timestamp(runtime));
				tj.setCreateTime(DateUtil.now());
				//邮件处理
				String sendMail="1";
				if (tj.getIsSendMail().equals(sendMail))
				{
					message = "";
					if (StrLib.isEmpty(tj.getEmailer()))
					{
						message = "确定要发送邮件时，收件人不能为空！";
					}
					else
					{
						String fgf=";";
						if (!tj.getEmailer().contains(fgf))
						{
							message = "收件人分隔格式不对！";
						}
						String gz="@";
						if (!tj.getEmailer().contains(gz))
						{// 未用正则表达式
							message = "收件人邮箱格式不对！";
						}
					}
					if (!StrLib.isEmpty(message))
					{
						model.addAttribute("message", message);
						return "/jsp/task/task_add";
					}

				}
				else
				{
					tj.setEmailer("");
				}
				
				//构建方式处理
				String building="1";
				if (tj.getIsbuilding().equals(building))
				{
					message = "";
					if (StrLib.isEmpty(tj.getBuildname()))
					{
						message = "确定要自动构建时，构建项目名不能为空！";
					}
					else
					{
						String fgf=";";
						if (!tj.getBuildname().contains(fgf))
						{
							message = "构建项目名分隔格式不对！";
						}
					}
					if (!StrLib.isEmpty(message))
					{
						model.addAttribute("message", message);
						return "/jsp/task/task_add";
					}

				}
				else
				{
					tj.setBuildname("");
				}
				
				//重启TOMCAT处理
				String restart="1";
				if (tj.getIsrestart().equals(restart))
				{
					message = "";
					if (StrLib.isEmpty(tj.getRestartcomm()))
					{
						message = "确定要自动重启时，重启脚本行不能为空！";
					}
					else
					{
						String fgf=";";
						if (!tj.getRestartcomm().contains(fgf))
						{
							message = "构建项目名分隔格式不对！";
						}
					}
					if (!StrLib.isEmpty(message))
					{
						model.addAttribute("message", message);
						return "/jsp/task/task_add";
					}

				}
				else
				{
					tj.setRestartcomm("");
				}
				
				if(tj.getProjecttype()==0){
					String projectname="0";
					for(int i=0;i<qaprolist.size();i++){
						if(qaprolist.get(i).getProjectid()==tj.getProjectid()){
							projectname=qaprolist.get(i).getProjectname();
							break;
						}
					}
					ProjectPlan pp=projectplanservice.load(tj.getPlanid());
					tj.setPlanproj(projectname);
					tj.setTestlinkname(pp.getName());
				}else{
					for(int i=0;i<prolist.size();i++){
						if(prolist.get(i).getProjectid()==Integer.valueOf(tj.getPlanproj())){
							tj.setProjectid(prolist.get(i).getProjectid());
							tj.setPlanproj(prolist.get(i).getProjectname());
							break;
						}
					}
				}
				int id = testJobsService.add(tj);
				if (id != 0)
				{
					QuratzJobDataMgr mgr = new QuratzJobDataMgr();
					mgr.addJobRunTime(tj, id);
					QueueListener.list.add(tj);
					
					operationlogservice.add(req, "TESTJOBS", id, 
							sectorprojectsService.getid(tj.getPlanproj()),8,"自动化用例计划任务添加成功！计划名称："+tj.getTaskName());
		
					model.addAttribute("message", "添加成功");
					model.addAttribute("url", "/testJobs/load.do");
					return retVal;
				}
				else
				{
					model.addAttribute("message", "添加失败");
					model.addAttribute("url", "/testJobs/load.do");
					return retVal;
				}

			}
			tj.setTaskType("O");
			tj.setIsSendMail("0");
			tj.setIsbuilding("0");
			tj.setIsrestart("0");
			tj.setThreadCount(1);
			tj.setTimeout(60);
			tj.setProjecttype(1);
			model.addAttribute("taskjob", tj);
			model.addAttribute("projects", prolist);
			model.addAttribute("sysprojects", qaprolist);
			return retVal;

		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/testJobs/load.do");
			return "error";
		}

	}

	/**
	 * Job详情
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/show.do", method = RequestMethod.GET)
	public String show(HttpServletRequest req,Model model) throws Exception
	{
		req.setCharacterEncoding("utf-8");
		int id = Integer.valueOf(req.getParameter("id"));
		TestJobs job = testJobsService.load(id);
		model.addAttribute("taskjob", job);
		return "/jsp/task/task_show";
	}

	

	/**
	 * 
	 * 根据Id更新Job信息
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update.do")
	public String update(@Valid @ModelAttribute("taskjob") TestJobs tj, BindingResult br,
	        Model model, HttpServletRequest req) throws Exception
	{
		req.setCharacterEncoding("utf-8");
		int id = Integer.valueOf(req.getParameter("id"));
		List<SectorProjects> qaprolist=QueueListener.qa_projlist;
		List<SectorProjects> prolist=QueueListener.projlist;
		
		model.addAttribute("projects", prolist);
		model.addAttribute("sysprojects", qaprolist);
		if(!UserLoginController.permissionboolean(req, PublicConst.AUTHJOBMOD)){
			model.addAttribute("taskjob", new TestJobs());
			model.addAttribute("url",  "/testJobs/load.do");
			model.addAttribute("message", "当前用户无权限修改计划任务，请联系管理员！");
			return "success";
		}
		
		TestJobs jobload = testJobsService.load(id);
		if (PublicConst.REQPOSTTYPE.equals(req.getMethod()))
		{
			try
			{
				if (br.hasErrors())
				{
					return "/jsp/task/task_update";
				}
				String message = "";
				
				if (StrLib.isEmpty(tj.getClientip())||PublicConst.STATUSSTR0.equals(tj.getClientip())) {
					message = "客户端IP不能为空！";
					model.addAttribute("message", message);
					return "/jsp/task/task_add";
				}
				
				if (StrLib.isEmpty(tj.getClientpath())||PublicConst.STATUSSTR0.equals(tj.getClientpath())) {
					message = "客户端驱动桩路径不能为空！";
					model.addAttribute("message", message);
					return "/jsp/task/task_add";
				}
				
				if (StrLib.isEmpty(tj.getTaskName())||PublicConst.STATUSSTR0.equals(tj.getTaskName()))
				{
					message = "计划名称不能为空!";
					model.addAttribute("message", message);
					return "/jsp/task/task_update";
				}
				boolean existed = tj.getProjecttype()==1&&(StrLib.isEmpty(tj.getPlanproj())||"0".equals(tj.getPlanproj()));
				if (existed)
				{
					message = "项目名必选!";
					model.addAttribute("message", message);
					return "/jsp/task/task_update";
				}else if(tj.getProjecttype()==1){
					if(!UserLoginController.oppidboolean(req, Integer.valueOf(tj.getPlanproj()))){
						model.addAttribute("message", "当前用户无权限修改当前项目的调度任务，请联系管理员！");
						return "/jsp/task/task_update";
					}
				}
				
				if (tj.getProjecttype()==0&&tj.getProjectid()==0)
				{
					message = "项目名必选!";
					model.addAttribute("message", message);
					return "/jsp/task/task_update";
				}else if(tj.getProjecttype()==0){
					if(!UserLoginController.oppidboolean(req, tj.getProjectid())){
						model.addAttribute("message", "当前用户无权限修改当前项目的调度任务，请联系管理员！");
						return "/jsp/task/task_update";
					}
				}
				int threadMax=20;
				if (tj.getThreadCount() < 1 || tj.getThreadCount() > threadMax)
				{
					message = "线程数在1-20之间";
					model.addAttribute("message", message);
					return "/jsp/task/task_update";
				}
				int timeOutMax=120;
				if (tj.getTimeout() < 1 || tj.getTimeout() > timeOutMax)
				{
					message = "超时时间必须在1-120分钟之间";
					model.addAttribute("message", message);
					return "/jsp/task/task_update";
				}
				String sendMail="1";
				if (tj.getIsSendMail().equals(sendMail))
				{
					message = "";
					if (StrLib.isEmpty(tj.getEmailer()))
					{
						message = "确定要发送邮件时，收件人不能为空！";
					}
					else
					{
						String fgf=";";
						if (!tj.getEmailer().contains(fgf))
						{
							message = "收件人分隔格式不对！";
						}
						String gz="@";
						if (!tj.getEmailer().contains(gz))
						{// 未用正则表达式
							message = "收件人邮箱格式不对！";
						}
					}
					if (!StrLib.isEmpty(message))
					{
						model.addAttribute("message", message);
						return "/jsp/task/task_update";
					}

				}
				else
				{
					tj.setEmailer("");
				}
				
				//构建方式处理
				String building="1";
				if (tj.getIsbuilding().equals(building))
				{
					message = "";
					if (StrLib.isEmpty(tj.getBuildname()))
					{
						message = "确定要自动构建时，构建项目名不能为空！";
					}
					else
					{
						String fgf=";";
						if (!tj.getBuildname().contains(fgf))
						{
							message = "构建项目名分隔格式不对！";
						}
					}
					if (!StrLib.isEmpty(message))
					{
						model.addAttribute("message", message);
						return "/jsp/task/task_update";
					}

				}
				else
				{
					tj.setBuildname("");
				}
				
				
				//重启TOMCAT处理
				String restart="1";
				if (tj.getIsrestart().equals(restart))
				{
					message = "";
					if (StrLib.isEmpty(tj.getRestartcomm()))
					{
						message = "确定要自动构建时，构建项目名不能为空！";
					}
					else
					{
						String fgf=";";
						if (!tj.getRestartcomm().contains(fgf))
						{
							message = "构建项目名分隔格式不对！";
						}
					}
					if (!StrLib.isEmpty(message))
					{
						model.addAttribute("message", message);
						return "/jsp/task/task_update";
					}

				}
				else
				{
					tj.setRestartcomm("");
				}
				
				String yes="1";
				if(yes.equals(tj.getIsSendMail())){
					tj.setEmailer(tj.getEmailer());
				}else{
					tj.setEmailer(jobload.getEmailer());
				}				
				
				if(yes.equals(tj.getIsbuilding())){
					tj.setBuildname(tj.getBuildname());
				}else{
					tj.setBuildname(jobload.getBuildname());
				}
				
				if(yes.equals(tj.getIsrestart())){
					tj.setRestartcomm(tj.getRestartcomm());
				}else{
					tj.setRestartcomm(jobload.getRestartcomm());
				}
				

				String startDate = DateLib.today("yyyy-MM-dd");
				String startTime = DateLib.today("HH:mm:ss");
				tj.setStartDate(startDate);
				tj.setStartTime(startTime);

				long runtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
				        tj.getStartDate() + " " + tj.getStartTime()).getTime();
				tj.setRunTime(new Timestamp(runtime));

				String startTimestr = tj.getStartTimestr();
				tj.setStartTimestr(startTimestr);

				if(tj.getProjecttype()==0){
					String projectname="0";
					for(int i=0;i<qaprolist.size();i++){
						if(qaprolist.get(i).getProjectid()==tj.getProjectid()){
							projectname=qaprolist.get(i).getProjectname();
							break;
						}
					}
					ProjectPlan pp=projectplanservice.load(tj.getPlanid());
					tj.setPlanproj(projectname);
					tj.setTestlinkname(pp.getName());
				}else{
					for(int i=0;i<prolist.size();i++){
						if(prolist.get(i).getProjectid()==Integer.valueOf(tj.getPlanproj())){
							tj.setProjectid(prolist.get(i).getProjectid());
							tj.setPlanproj(prolist.get(i).getProjectname());
							break;
						}
					}
				}
				
				// 写入数据库
				testJobsService.modify(tj);
				// 更新内存，替换原来的调度
				for (int i = 0; i < QueueListener.list.size(); i++) {
					TestJobs job = QueueListener.list.get(i);
					if (job.getId() == tj.getId()) {
						QueueListener.list.remove(job);
						QueueListener.list.add(tj);
						break;
					}
				}

				String msg = QuartzManager.modifyJobTime(id + "*JOB", tj.getStartTimestr());
				if (!msg.equals("")) {
					model.addAttribute("message", msg);
					return "/jsp/task/task_update";
				}

				model.addAttribute("message", "修改成功,请返回查询！");
				model.addAttribute("url", "/testJobs/load.do");
				return "/jsp/task/task_update";
			}
			catch (Exception e)
			{
				model.addAttribute("message", e.getMessage());
				model.addAttribute("url", "/testJobs/load.do");
				return "error";
			}
		}

		model.addAttribute("isSendMail", jobload.getIsSendMail());
		model.addAttribute("isrestart", jobload.getIsrestart());
		model.addAttribute("isbuilding", jobload.getIsbuilding());
		model.addAttribute("projectid", jobload.getProjectid());
		model.addAttribute("extype", jobload.getExtype());
		model.addAttribute("browsertype", jobload.getBrowsertype());
		model.addAttribute("projecttype", jobload.getProjecttype());
		List<TestClient> tclist=tcservice.getClientListForProid(jobload.getProjectid());
		for(TestClient tc:tclist){
			if(tc.getStatus()==0){
				tc.setName("【"+tc.getName()+"】"+tc.getClientip()+" 【客户端状态正常】");
			}else if(tc.getStatus()==1){
				tc.setName("【"+tc.getName()+"】"+tc.getClientip()+" 【客户端状态异常】");
			}else{
				tc.setName("【"+tc.getName()+"】"+tc.getClientip()+" 【客户端状态未知】");
			}
		}
		model.addAttribute("tclist", tclist);
		ArrayList<String> pathlist=new ArrayList<String>();
		TestClient tc=tcservice.getClient(jobload.getClientip());

		String clientpath = tc.getClientpath();
		String[] temp = clientpath.split(";", -1);
		for (String pathtemp : temp) {
			if (!StrLib.isEmpty(pathtemp)) {
				pathlist.add(pathtemp);
			}
		}

		model.addAttribute("tcpathlist", pathlist);
		if(jobload.getProjecttype()==0){
			ProjectPlan projectplan=new ProjectPlan();
			projectplan.setProjectid(jobload.getProjectid());
			List<ProjectPlan> listplan = projectplanservice.findByPage(projectplan, 0, 1000);
			model.addAttribute("planlist", listplan);
		}
		model.addAttribute("taskjob", jobload);
		return "/jsp/task/task_update";
	}

	/**
	 * 删除调度
	 * 
	 * @param tj
	 * @param br
	 * @param model
	 * @param req
	 * @param rsp
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/delete.do")
	public void delete(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHJOBDEL)) {
				json.put("status", "fail");
				json.put("ms", "删除调度失败,权限不足,请联系管理员!");
			} else {
				int id = Integer.valueOf(req.getParameter("jobid"));
				TestJobs tj = testJobsService.get(id);
				if(!UserLoginController.oppidboolean(req, tj.getProjectid())){
					json.put("status", "fail");
					json.put("ms", "删除调度失败,项目权限不足,请联系管理员!");
				}else{					
					List idlist = tastExcuteService.getidlist(id);
					try
					{
						int tastid = 0;
						for(int i=0;i<idlist.size();i++){
							tastid = Integer.valueOf(idlist.get(i).toString());
							this.logdetailService.delete(tastid);
							this.casedetailService.delete(tastid);
							this.tastExcuteService.delete(tastid);
						}
						testJobsService.delete(id);
						QuartzManager.removeJob(id + "*JOB");
						QueueListener.list.remove(tj);
						
						operationlogservice.add(req, "TESTJOBS", id, 
								tj.getProjectid(),0,"自动化用例计划任务删除成功！计划名称："+tj.getTaskName());
						
						json.put("status", "success");
						json.put("ms", "删除调度任务成功!");
					}
					catch (Exception e)
					{
						json.put("status", "fail");
						json.put("ms", "删除调度过程中失败!");
					}
				}
			}
			pw.print(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 *启动
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/run.do")
	public String run(Model model, HttpServletRequest req, HttpServletResponse rsp)
	        throws Exception
	{
		PrintWriter pw = null;
		rsp.setContentType("text/html;charset=utf-8");
		String id = req.getParameter("id");
		try
		{
			pw = rsp.getWriter();
			TestJobs tb = testJobsService.get(Integer.valueOf(id));
			tb.setState("1");
			// String startTime = setStartTime(tb);
			String startTime = tb.getStartTimestr();
			testJobsService.modify(tb);
			QuartzManager.addJob(id + "*JOB", QuartzJob.class, startTime);
			
			operationlogservice.add(req, "TESTJOBS", Integer.valueOf(id), 
					sectorprojectsService.getid(tb.getPlanproj()),2,"自动化用例计划任务被启动成功！计划名称："+tb.getTaskName());

		}
		catch (Exception e)
		{
			pw.write(e.getMessage());
			return null;
		}
		
		pw.write("已经启动！！");
		return null;
	}

	/**
	 * 关闭JOb
	 * @param id
	 * @param model
	 * @param req
	 * @param rsp
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/remove.do")
	public String removeSchudle(Model model, HttpServletRequest req, HttpServletResponse rsp)
	        throws Exception
	{
		PrintWriter pw = null;
		rsp.setContentType("text/html;charset=utf-8");
		String id = req.getParameter("id");
		try
		{
			pw = rsp.getWriter();
			TestJobs tb = testJobsService.get(Integer.valueOf(id));
			tb.setState("0");
			testJobsService.modify(tb);
			QuartzManager.removeJob(id+"*JOB");
			
			operationlogservice.add(req, "TESTJOBS", Integer.valueOf(id), 
					sectorprojectsService.getid(tb.getPlanproj()),1,"自动化用例计划任务被关闭成功！计划名称："+tb.getTaskName());
		}
		catch (Exception e)
		{
			pw.write(e.getMessage());
			return null;
		}
		pw.write("已经关闭！！");
		return null;
	}

	/**
	 * 立即启动
	 * 
	 * @param tj
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/startNow.do")
	public void startNow(Model model, HttpServletRequest req, HttpServletResponse rsp)
	        throws Exception
	{
		PrintWriter pw = null;
		rsp.setContentType("text/html;charset=utf-8");
		int id = Integer.valueOf(req.getParameter("id"));
		try
		{
			TestJobs tj = testJobsService.load(id);
			pw = rsp.getWriter();

			String startDate = DateLib.today("yyyy-MM-dd");
			String startTime = DateLib.today("HH:mm:ss");
			tj.setStartDate(startDate);
			tj.setStartTime(startTime);

			long runtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
			        tj.getStartDate() + " " + tj.getStartTime()).getTime();
			tj.setRunTime(new Timestamp(runtime));

			String startTimestr = tj.getStartTimestr();
			tj.setStartTimestr(startTimestr);

			tj.setCreateTime(DateUtil.now());

			try
			{
				QuartzJob qj = new QuartzJob();
				String message = qj.toRunTask(tj.getPlanproj(), id,tj.getTaskName(),tj.getClientip(),tj.getClientpath());				
				
				operationlogservice.add(req, "TESTJOBS", Integer.valueOf(id), 
						sectorprojectsService.getid(tj.getPlanproj()),2,"自动化用例计划任务被执行！计划名称："+tj.getTaskName()+" 结果："+message);
				pw.write(message);
			}
			catch (Exception e)
			{
				String message = "当前项目在服务器不存在！";
				pw.write(message);
			}

		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			pw.write(e.getMessage());
		}

	}

	/**
	 * 页面日志展示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/down.do")
	public String download(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		response.setContentType("text/html;charset=gbk");
		request.setCharacterEncoding("gbk");
		String contentType = "application/octet-stream";
		response.setContentType(contentType);
		response.setContentType("multipart/form-data");
		
		String storeName = "ERROR.log";
		String startDate = request.getParameter("startDate");
		String clientip = request.getParameter("clientip");
		String dateformat = "yyyy-MM-dd";
		if (!DateLib.today(dateformat).equals(startDate))
		{
			storeName = storeName + "." + startDate;
		}
		String result="获取日志远程链接失败！";
		try{
    		//调用远程对象，注意RMI路径与接口必须与服务器配置一致
    		RunService service=(RunService)Naming.lookup("rmi://"+clientip+":6633/RunService");
    		result=service.getlogdetail(storeName);
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		model.addAttribute("filename", storeName);
		model.addAttribute("data", result);
		return "down";
	}

	/**
	 * 上传
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/to_upload.do")
	public String toUpload(Model model,HttpServletRequest req, HttpServletResponse response) throws Exception
	{
		if(!UserLoginController.permissionboolean(req, PublicConst.AUTHJOBUPLOAD)){
			model.addAttribute("taskjob", new TestJobs());
			model.addAttribute("url",  "/testJobs/load.do");
			model.addAttribute("message", "当前用户无权限上传测试项目，请联系管理员！");
			return "error";
		}
		String clientip = req.getParameter("clientip");
		TestClient tc=tcservice.getClient(clientip);
		ArrayList<String> pathlist=new ArrayList<String>();
		String path=tc.getClientpath();
		String[] temp=path.split(";",-1);
		for(String pathtemp:temp){
			if(!StrLib.isEmpty(pathtemp)){
				pathlist.add(pathtemp);
			}
		}
		model.addAttribute("clientip", clientip);
		model.addAttribute("pathlist", pathlist);
		return "/jsp/task/file_upload";
	}

	/**
	 * 上传
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload.do")
	public String upload(@RequestParam(value = "file", required = false) MultipartFile file, Model model,
	        HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int filelength=4;
		if(null==file.getOriginalFilename()||file.getOriginalFilename().length()<filelength){
			String message = "请选择一个文件后，再进行上传操作！";
			model.addAttribute("message", message);
			return "/jsp/task/file_upload";
		}
		String fileName = file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4,
		        file.getOriginalFilename().length());
		String clientip = request.getParameter("clientip");
		String clientpath = request.getParameter("clientpath");
		String filetype=".jar";
		if (!fileName.toLowerCase().equals(filetype))
		{
			String message = "只能上传.jar的文件！";
			model.addAttribute("message", message);
			return "/jsp/task/file_upload";
		}
		// 文件目录
		String path = System.getProperty("user.dir")+"\\";
		String pathName = path + file.getOriginalFilename();
		System.out.println(pathName);
		File targetFile = new File(pathName);
		if (targetFile.exists()){
			targetFile.delete();
		}
		else
		{
			targetFile.mkdir();
		}
		// 保存
		try
		{
			file.transferTo(targetFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/testJobs/load.do");
			return "error";
		}

		byte[] b = null;
		String result="获取日志远程链接失败！";
		try {
			b = new byte[(int) targetFile.length()];
			BufferedInputStream is = new BufferedInputStream(new FileInputStream(targetFile));
			is.read(b);
			try{
	    		//调用远程对象，注意RMI路径与接口必须与服务器配置一致
	    		RunService service=(RunService)Naming.lookup("rmi://"+clientip+":6633/RunService");
	    		result=service.uploadjar(b, file.getOriginalFilename(),clientpath);
			} catch (Exception e) {
				e.printStackTrace();
			}
			is.close();
			//删除服务器上的文件
			if (targetFile.exists()){
				targetFile.delete();
			}
			operationlogservice.add(request, "TESTJOBS", 0, 
					99,5,"项目jar包被上传，包名："+file.getOriginalFilename());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "服务端未找到正确文件路径！";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "服务端IOException！";
		}
		
		model.addAttribute("url", "/testJobs/load.do");
		model.addAttribute("message", result);
		return "success";
	}
	
	/**
	 * 提供任务调度接口
	 * 
	 * @param tj
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/runJobForInterface.do")
	public void runJobForInterface(HttpServletRequest req, HttpServletResponse rsp)throws Exception{
		PrintWriter pw = null;
		rsp.setContentType("text/html;charset=utf-8");
		int jobid = Integer.valueOf(req.getParameter("jobid"));
		try
		{
			TestJobs tj = testJobsService.load(jobid);
			pw = rsp.getWriter();

			String startDate = DateLib.today("yyyy-MM-dd");
			String startTime = DateLib.today("HH:mm:ss");
			tj.setStartDate(startDate);
			tj.setStartTime(startTime);

			long runtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
			        tj.getStartDate() + " " + tj.getStartTime()).getTime();
			tj.setRunTime(new Timestamp(runtime));

			String startTimestr = tj.getStartTimestr();
			tj.setStartTimestr(startTimestr);

			tj.setCreateTime(DateUtil.now());

			try
			{
				QuartzJob qj = new QuartzJob();
				String message = qj.toRunTask(tj.getPlanproj(), jobid,tj.getTaskName(),tj.getClientip(),tj.getClientpath());
				pw.write(JSONObject.parseObject("{result:\""+message+"\"}").toString());
			}
			catch (Exception e)
			{
				String message = "当前项目在服务器不存在！";
				pw.write(JSONObject.parseObject("{result:"+message+"}").toString());
			}

		}
		catch (Exception e)
		{
			pw.write(JSONObject.parseObject("{result:"+e.getMessage()+"}").toString());
		}

	}
}
