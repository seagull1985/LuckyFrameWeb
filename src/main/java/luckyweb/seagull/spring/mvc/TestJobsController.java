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

import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.quartz.QuartzJob;
import luckyweb.seagull.quartz.QuartzManager;
import luckyweb.seagull.quartz.QuratzJobDataMgr;
import luckyweb.seagull.spring.entity.TestJobs;
import luckyweb.seagull.spring.service.CaseDetailService;
import luckyweb.seagull.spring.service.LogDetailService;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.spring.service.TestJobsService;
import luckyweb.seagull.spring.service.TestTastExcuteService;
import luckyweb.seagull.util.DateLib;
import luckyweb.seagull.util.DateUtil;
import luckyweb.seagull.util.StrLib;
import rmi.service.RunService;

@Controller
@RequestMapping("/testJobs")
public class TestJobsController
{
	private static final Logger	log	     = Logger.getLogger(TestJobsController.class);
	private int	 allPage;
	private int	 pageSize = 20;
	private int	 allRows;
	private int	 page = 1;
	private int	 offset;
	
	
	@Resource(name = "testJobsService")
	private TestJobsService	 testJobsService;
	
	@Resource(name = "tastExcuteService")
	private TestTastExcuteService	tastExcuteService;
	
	@Resource(name = "casedetailService")
	private CaseDetailService	casedetailService;
	
	@Resource(name = "logdetailService")
	private LogDetailService	logdetailService;

	public TestJobsService getTestJobsService()
	{
		return testJobsService;
	}

	@Resource(name = "testJobsService")
	public void setTestJobsService(TestJobsService testJobsService)
	{
		this.testJobsService = testJobsService;
	}

	
	@Resource(name = "operationlogService")
	private OperationLogService operationlogservice;

	public OperationLogService getOperationlogService() {
		return operationlogservice;
	}
	
	@Resource(name = "sectorprojectsService")
	private SectorProjectsService sectorprojectsService;
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
			
			if(!UserLoginController.permissionboolean(req, "tast_1")){
				model.addAttribute("taskjob", new TestJobs());
				model.addAttribute("url",  "/testJobs/list.do");
				model.addAttribute("message", "当前用户无权限添加计划任务，请联系管理员！");
				return "success";
			}
			
			String retVal = "/jsp/task/task_add";
			if (req.getMethod().equals("POST"))
			{

				if (br.hasErrors())
				{
					return retVal;
				}
				
				String message = "";

				if (StrLib.isEmpty(tj.getClientip())) {
					message = "客户端IP不能为空！";
					model.addAttribute("message", message);
					return "/jsp/task/task_add";
				}
				
				if (tj.getThreadCount() < 1 || tj.getThreadCount() > 20)
				{
					message = "线程数在1-20之间";
					model.addAttribute("message", message);
					return retVal;
				}
				
				if (tj.getTimeout() < 1 || tj.getTimeout() > 120)
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
				if (tj.getIsSendMail().equals("1"))
				{
					message = "";
					if (StrLib.isEmpty(tj.getEmailer()))
					{
						message = "确定要发送邮件时，收件人不能为空！";
					}
					else
					{
						if (!tj.getEmailer().contains(";"))
						{
							message = "收件人分隔格式不对！";
						}
						if (!tj.getEmailer().contains("@"))
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
				if (tj.getIsbuilding().equals("1"))
				{
					message = "";
					if (StrLib.isEmpty(tj.getBuildname()))
					{
						message = "确定要自动构建时，构建项目名不能为空！";
					}
					else
					{
						if (!tj.getBuildname().contains(";"))
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
				if (tj.getIsrestart().equals("1"))
				{
					message = "";
					if (StrLib.isEmpty(tj.getRestartcomm()))
					{
						message = "确定要自动重启时，重启脚本行不能为空！";
					}
					else
					{
						if (!tj.getRestartcomm().contains(";"))
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
				
				int id = testJobsService.add(tj);
				if (id != 0)
				{
					QuratzJobDataMgr mgr = new QuratzJobDataMgr();
					mgr.addRunTime(tj, id);
					QueueListener.list.add(tj);
					
					operationlogservice.add(req, "TESTJOBS", id, 
							sectorprojectsService.getid(tj.getPlanproj()),"自动化用例计划任务添加成功！计划名称："+tj.getTaskName());
		
					model.addAttribute("message", "添加成功");
					model.addAttribute("url", "/testJobs/list.do");
					return retVal;
				}
				else
				{
					model.addAttribute("message", "添加失败");
					model.addAttribute("url", "/testJobs/list.do");
					return retVal;
				}

			}
			tj.setTaskType("O");
			tj.setIsSendMail("0");
			tj.setIsbuilding("0");
			tj.setIsrestart("0");
			tj.setThreadCount(1);
			tj.setTimeout(60);
			model.addAttribute("taskjob", tj);
			model.addAttribute("projects", QueueListener.projlist);//
			return retVal;

		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/testJobs/list.do");
			return "error";
		}

	}

	/**
	 * 
	 * Job查询
	 * @param tj
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list.do")
	public String list(HttpServletRequest req, TestJobs tj, Model model) throws Exception
	{
		
		model.addAttribute("taskjob", tj);
		try
		{
			String p = req.getParameter("page");
			if (StrLib.isEmpty(p) || Integer.valueOf(p) == 0)
			{
				page = 1;
			}

			String page2 = req.getParameter("page");
			if (StrLib.isEmpty(page2))
			{
				page = 1;
			}
			else
			{
				try
				{
					page = Integer.parseInt(page2);
				}
				catch (Exception e)
				{
					page = 1;
				}
			}
			allRows = testJobsService.findRows(tj);
			offset = (page - 1) * pageSize;
			if (allRows % pageSize == 0)
			{
				allPage = allRows / pageSize;
			}
			else
			{
				allPage = allRows / pageSize + 1;
			}

			model.addAttribute("allRows", allRows);
			model.addAttribute("page", page);
			model.addAttribute("offset", offset);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("allPage", allPage);

			List<TestJobs> tjjobsMap = testJobsService.findByPage(tj, offset, pageSize);
			List iplist = testJobsService.getipList();
			model.addAttribute("tjlist", tjjobsMap);
			model.addAttribute("iplist", iplist);
		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/testJobs/list.do");
			return "error";
		}

		model.addAttribute("startDate", DateLib.today("yyyy-MM-dd"));
		return "/jsp/task/task_list";
		
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
		model.addAttribute("projects", QueueListener.projlist);
		
		if(!UserLoginController.permissionboolean(req, "tast_3")){
			model.addAttribute("taskjob", new TestJobs());
			model.addAttribute("url",  "/testJobs/list.do");
			model.addAttribute("message", "当前用户无权限修改计划任务，请联系管理员！");
			return "success";
		}
		
		TestJobs jobload = testJobsService.load(id);
		if (req.getMethod().equals("POST"))
		{
			try
			{
				if (br.hasErrors())
				{
					return "/jsp/task/task_update";
				}
				String message = "";
				
				if (StrLib.isEmpty(tj.getClientip())) {
					message = "客户端IP不能为空！";
					model.addAttribute("message", message);
					return "/jsp/task/task_add";
				}
				
				if (StrLib.isEmpty(tj.getTaskName()))
				{
					message = "计划名称不能为空!";
					model.addAttribute("message", message);
					return "/jsp/task/task_update";
				}
				if (StrLib.isEmpty(tj.getPlanproj()))
				{
					message = "项目名必选!";
					model.addAttribute("message", message);
					return "/jsp/task/task_update";
				}

				if (tj.getThreadCount() < 1 || tj.getThreadCount() > 20)
				{
					message = "线程数在1-20之间";
					model.addAttribute("message", message);
					return "/jsp/task/task_update";
				}

				if (tj.getTimeout() < 1 || tj.getTimeout() > 120)
				{
					message = "超时时间必须在1-120分钟之间";
					model.addAttribute("message", message);
					return "/jsp/task/task_update";
				}

				if (tj.getIsSendMail().equals("1"))
				{
					message = "";
					if (StrLib.isEmpty(tj.getEmailer()))
					{
						message = "确定要发送邮件时，收件人不能为空！";
					}
					else
					{
						if (!tj.getEmailer().contains(";"))
						{
							message = "收件人分隔格式不对！";
						}
						if (!tj.getEmailer().contains("@"))
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
				if (tj.getIsbuilding().equals("1"))
				{
					message = "";
					if (StrLib.isEmpty(tj.getBuildname()))
					{
						message = "确定要自动构建时，构建项目名不能为空！";
					}
					else
					{
						if (!tj.getBuildname().contains(";"))
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
				if (tj.getIsrestart().equals("1"))
				{
					message = "";
					if (StrLib.isEmpty(tj.getRestartcomm()))
					{
						message = "确定要自动构建时，构建项目名不能为空！";
					}
					else
					{
						if (!tj.getRestartcomm().contains(";"))
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
				
				
				if("1".equals(tj.getIsSendMail())){
					tj.setEmailer(tj.getEmailer());
				}else{
					tj.setEmailer(jobload.getEmailer());
				}				
				
				if("1".equals(tj.getIsbuilding())){
					tj.setBuildname(tj.getBuildname());
				}else{
					tj.setBuildname(jobload.getBuildname());
				}
				
				if("1".equals(tj.getIsrestart())){
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

				// 写入数据库
				testJobsService.modify(tj);
				// 更新内存，替换原来的调度
				TestJobs job = null;
				for (int i = 0; i < QueueListener.list.size(); i++)
				{
					job = new TestJobs();
					job = QueueListener.list.get(i);
					if (job.getId() == tj.getId())
					{
						QueueListener.list.remove(job);
						QueueListener.list.add(tj);
						break;
					}
				}

				for (int i = 0; i < QueueListener.list.size(); i++)
				{
					job = new TestJobs();
					job = QueueListener.list.get(i);
					if (job.getId() == tj.getId())
					{
						break;
					}
				}
				String msg = QuartzManager.modifyJobTime(id + "", tj.getStartTimestr());
				if (!msg.equals(""))
				{
					model.addAttribute("message", msg);
					return "/jsp/task/task_update";
				}
				model.addAttribute("message", "修改成功,请返回查询！");
				model.addAttribute("url", "/testJobs/list.do");
				return "/jsp/task/task_update";
			}
			catch (Exception e)
			{
				model.addAttribute("message", e.getMessage());
				model.addAttribute("url", "/testJobs/list.do");
				return "error";
			}
		}
		model.addAttribute("taskjob", jobload);
		model.addAttribute("isSendMail", jobload.getIsSendMail());
		model.addAttribute("isrestart", jobload.getIsrestart());
		model.addAttribute("isbuilding", jobload.getIsbuilding());
		model.addAttribute("extype", jobload.getExtype());
		model.addAttribute("browsertype", jobload.getBrowsertype());
		return "/jsp/task/task_update";
	}

	/**
	 * 删除Job
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public String delete(Model model,HttpServletRequest req) throws Exception
	{
		if(!UserLoginController.permissionboolean(req, "tast_2")){
			model.addAttribute("taskjob", new TestJobs());
			model.addAttribute("url",  "/testJobs/list.do");
			model.addAttribute("message", "当前用户无权限删除计划任务，请联系管理员！");
			return "success";
		}
		int id = Integer.valueOf(req.getParameter("id"));
		TestJobs tj = testJobsService.get(id);
		List<Object[]> idlist = tastExcuteService.getidlist(id);
		try
		{
			int tastid = 0;
			for(int i=0;i<idlist.size();i++){
				tastid = Integer.valueOf(idlist.get(i)[0].toString());
				this.logdetailService.delete(tastid);
				this.casedetailService.delete(tastid);
				this.tastExcuteService.delete(tastid);
			}
			testJobsService.delete(id);
			QuartzManager.removeJob(id + "");
		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/testJobs/list.do");
			return "error";
		}
		
		operationlogservice.add(req, "TESTJOBS", id, 
				sectorprojectsService.getid(tj.getPlanproj()),"自动化用例计划任务删除成功！计划名称："+tj.getTaskName());
		
		String message = "删除成功！";
		model.addAttribute("taskjob", new TestJobs());
		model.addAttribute("message", message);
		model.addAttribute("url", "/testJobs/list.do");
		return "success";
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
			QuartzManager.addJob(id, QuartzJob.class, startTime);
			
			operationlogservice.add(req, "TESTJOBS", Integer.valueOf(id), 
					sectorprojectsService.getid(tb.getPlanproj()),"自动化用例计划任务被启动成功！计划名称："+tb.getTaskName());

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
			QuartzManager.removeJob(id);
			
			operationlogservice.add(req, "TESTJOBS", Integer.valueOf(id), 
					sectorprojectsService.getid(tb.getPlanproj()),"自动化用例计划任务被关闭成功！计划名称："+tb.getTaskName());
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
	public String startNow(Model model, HttpServletRequest req, HttpServletResponse rsp)
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
				String message = qj.toRunTask(tj.getPlanproj(), id,tj.getTaskName(),tj.getClientip());				
				
				operationlogservice.add(req, "TESTJOBS", Integer.valueOf(id), 
						sectorprojectsService.getid(tj.getPlanproj()),"自动化用例计划任务被执行！计划名称："+tj.getTaskName()+" 结果："+message);
				pw.write(message);
				return null;
			}
			catch (Exception e)
			{
				String message = "当前项目在服务器不存在！";
				pw.write(message);
				return null;
			}

		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			pw.write(e.getMessage());
			return null;
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
		// String name = date;
		if (!DateLib.today("yyyy-MM-dd").equals(startDate))
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
	public String to_upload(Model model,HttpServletRequest req, HttpServletResponse response) throws Exception
	{
		if(!UserLoginController.permissionboolean(req, "tast_upload")){
			model.addAttribute("taskjob", new TestJobs());
			model.addAttribute("url",  "/testJobs/list.do");
			model.addAttribute("message", "当前用户无权限上传测试项目，请联系管理员！");
			return "error";
		}
		String clientip = req.getParameter("clientip");
		model.addAttribute("clientip", clientip);
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
		if(null==file.getOriginalFilename()||file.getOriginalFilename().length()<4){
			String message = "请选择一个文件后，再进行上传操作！";
			model.addAttribute("message", message);
			return "/jsp/task/file_upload";
		}
		String fileName = file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4,
		        file.getOriginalFilename().length());
		String clientip = request.getParameter("clientip");
		if (!fileName.toLowerCase().equals(".jar"))
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
			model.addAttribute("url", "/testJobs/list.do");
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
	    		result=service.uploadjar(b, file.getOriginalFilename());
			} catch (Exception e) {
				e.printStackTrace();
			}
			is.close();
			//删除服务器上的文件
			if (targetFile.exists()){
				targetFile.delete();
			}
			operationlogservice.add(request, "TESTJOBS", 0, 
					99,"项目jar包被上传，包名："+file.getOriginalFilename());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "服务端未找到正确文件路径！";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "服务端IOException！";
		}
		
		model.addAttribute("url", "/testJobs/list.do");
		model.addAttribute("message", result);
		return "success";
	}

	
}
