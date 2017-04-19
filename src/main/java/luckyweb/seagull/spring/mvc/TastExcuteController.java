package luckyweb.seagull.spring.mvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import luckyweb.seagull.spring.entity.TestCasedetail;
import luckyweb.seagull.spring.entity.TestJobs;
import luckyweb.seagull.spring.entity.TestTaskexcute;
import luckyweb.seagull.spring.service.CaseDetailService;
import luckyweb.seagull.spring.service.LogDetailService;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.spring.service.TestJobsService;
import luckyweb.seagull.spring.service.TestTastExcuteService;
import luckyweb.seagull.util.DateLib;
import luckyweb.seagull.util.StrLib;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/tastExecute")
public class TastExcuteController
{
	private static final Logger	  log	   = Logger.getLogger(TastExcuteController.class);

	/** 总共有多少页 */
	private int	                  allPage;
	/** 页面大小 */
	private int	                  pageSize	= 20;
	/** 记录总数 */
	private int	                  allRows;
	/** 当前页面数， 默认为1 */
	private int	                  page	   = 1;
	/** 第一条记录的索引 */
	private int	                  offset	= 0;

	@Resource(name = "tastExcuteService")
	private TestTastExcuteService	tastExcuteService;

	@Resource(name = "testJobsService")
	private TestJobsService	      testJobsService;

	@Resource(name = "casedetailService")
	private CaseDetailService	  casedetailService;

	@Resource(name = "logdetailService")
	private LogDetailService	  logdetailService;

	public CaseDetailService getCasedetailService()
	{
		return casedetailService;
	}

	public void setCasedetailService(CaseDetailService casedetailService)
	{
		this.casedetailService = casedetailService;
	}

	public LogDetailService getLogdetailService()
	{
		return logdetailService;
	}

	public void setLogdetailService(LogDetailService logdetailService)
	{
		this.logdetailService = logdetailService;
	}

	public TestJobsService getTestJobsService()
	{
		return testJobsService;
	}

	public void setTestJobsService(TestJobsService testJobsService)
	{
		this.testJobsService = testJobsService;
	}

	public TestTastExcuteService getTastExcuteService()
	{
		return tastExcuteService;
	}

	public void setTastExcuteService(TestTastExcuteService tastExcuteService)
	{
		this.tastExcuteService = tastExcuteService;
	}

	@Resource(name = "operationlogService")
	private OperationLogService operationlogservice;

	public OperationLogService getOperationlogService() {
		return operationlogservice;
	}
	
	@Resource(name = "sectorprojectsService")
	private SectorProjectsService sectorprojectsService;
	/**
	 * 任务 查询
	 * 
	 * @param tast
	 * @param req
	 * @param model
	 * @return
	 * @Description:
	 */
	@RequestMapping(value = "/list.do")
	public String list(TestTaskexcute tast, HttpServletRequest req,Model model)
	{
		model.addAttribute("testTastexcute", tast);

		try
		{
			String jobid = req.getParameter("jobid");
			String tastid = req.getParameter("tastId");
			String startDate = req.getParameter("startDate");
			String endDate = req.getParameter("endDate");

			if (StrLib.isEmpty(startDate))
			{
				tast.setStartDate(DateLib.befor_Nd_format("yyyy-MM-dd", 6));
			}
			else
			{
				tast.setStartDate(startDate);
			}
			if (StrLib.isEmpty(endDate))
			{
				tast.setEndDate(DateLib.today("yyyy-MM-dd"));
			}
			else
			{
				tast.setEndDate(endDate);
			}

			if (!StrLib.isEmpty(jobid))
			{
				tast.getTestJob().setId(Integer.valueOf(jobid));
				tast.setJobid(Integer.valueOf(jobid));
			}
			else
			{
				jobid = "0";
			}
			tast.setTaskId(tastid);

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
			model.addAttribute("jobid", jobid);

			allRows = tastExcuteService.findRows(tast);

			offset = (page - 1) * pageSize;
			if (allRows % pageSize == 0)
			{
				allPage = allRows / pageSize;
			}
			else
			{
				allPage = allRows / pageSize + 1;
			}
			List<TestTaskexcute> list = tastExcuteService.findByPage(tast, offset, pageSize);

			model.addAttribute("allRows", allRows);
			if (list.size() != 0)
			{
				List<Integer> idlist = new ArrayList<Integer>(); 
				for(TestTaskexcute tte:list){   //处理进度条需要的数据
					if("1".equals(tte.getTaskStatus())||"0".equals(tte.getTaskStatus())){
						idlist.add(tte.getId());
					}					
				}
				JSONArray jsonArray = JSONArray.fromObject(idlist);
				
				model.addAttribute("idlist", jsonArray);
				model.addAttribute("idlistsize", idlist.size());
				model.addAttribute("offset", offset);
				model.addAttribute("pageSize", pageSize);
				model.addAttribute("allPage", allPage);
			}

			model.addAttribute("page", page);
			model.addAttribute("jobid", jobid);
			model.addAttribute("list", list);

			// 调度列表
			List<TestJobs> jobs = testJobsService.findJobsList();
			model.addAttribute("jobs", jobs);
		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/tastExecute/list.do");
			return "error";
		}

		return "/jsp/task/taskexcute_list";
	}

	/**
	 * 根据任务Id删除任务
	 * 
	 * @param id
	 * @param model
	 * @param req
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/delete.do")
	public String delete(Model model, HttpServletRequest req) throws Exception
	{
		
		if(!UserLoginController.permissionboolean(req, "tastex_2")){
			model.addAttribute("testTastexcute", new TestTaskexcute());
			model.addAttribute("url",  "/tastExecute/list.do");
			model.addAttribute("message", "当前用户无权限删除已执行的任务，请联系管理员！");
			return "success";
		}
		int id = Integer.valueOf(req.getParameter("id"));
		TestTaskexcute tast = tastExcuteService.get(id);
		
		if(tast==null){
			model.addAttribute("message", "当前任务不存在或已经删除，请确认！");
			model.addAttribute("url", "/tastExecute/list.do");
			return "error";
		}
		
		Date date = new Date();
		date.setMinutes(date.getMinutes()-30);
		
		if(("0".equals(tast.getTaskStatus())||"1".equals(tast.getTaskStatus()))&&tast.getCreateTime().after(date)){
			model.addAttribute("message", "任务开始30分钟时间内不允许删除！");
			model.addAttribute("url", "/tastExecute/list.do");
			return "error";
		}
		
		try
		{				
			this.logdetailService.delete(id);
			this.casedetailService.delete(id);
			this.tastExcuteService.delete(id);
		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/tastExecute/list.do");
			return "error";
		}		
					
		operationlogservice.add(req, "TESTJOBS", id, 
			sectorprojectsService.getid(tast.getTestJob().getPlanproj()),"自动化用例已执行任务记录删除成功！任务名称："+tast.getTaskId());
		
		String message = "删除成功！";
		model.addAttribute("message", message);
		model.addAttribute("url", "/tastExecute/list.do");
		return "success";
	}

	/**
	 * 根据任务Id删除任务
	 * 
	 * @param id
	 * @param model
	 * @param req
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/deletebatch.do")
	public String deletebatch(Model model, HttpServletRequest req) throws Exception
	{

		if (!UserLoginController.permissionboolean(req, "tastex_2")) {
			model.addAttribute("testTastexcute", new TestTaskexcute());
			model.addAttribute("url", "/tastExecute/list.do");
			model.addAttribute("message", "当前用户无权限删除已执行的任务，请联系管理员！");
			return "success";
		}
		String[] reqid = req.getParameterValues("deletebox");
		
		if(reqid==null){
			model.addAttribute("message", "批量任务删除失败，请重新操作！");
			model.addAttribute("url", "/tastExecute/list.do");
			return "error";
		}
		
		int id = 0;
		TestTaskexcute tast;
		for (int i = 0; i < reqid.length; i++) {
			id = Integer.valueOf(reqid[i]);
			tast = tastExcuteService.get(id);

			if (tast == null) {
				model.addAttribute("message", "当前任务不存在或已经删除，批量删除中断，请确认！");
				model.addAttribute("url", "/tastExecute/list.do");
				return "error";
			}

			Date date = new Date();
			date.setMinutes(date.getMinutes() - 30);

			if (("0".equals(tast.getTaskStatus()) || "1".equals(tast.getTaskStatus()))
					&& tast.getCreateTime().after(date)) {
				model.addAttribute("message", "当前任务开始30分钟时间内不允许删除！【"+tast.getName()+"】,请重新选择！");
				model.addAttribute("url", "/tastExecute/list.do");
				return "error";
			}

			try {
				this.logdetailService.delete(id);
				this.casedetailService.delete(id);
				this.tastExcuteService.delete(id);
			} catch (Exception e) {
				model.addAttribute("message", e.getMessage());
				model.addAttribute("url", "/tastExecute/list.do");
				return "error";
			}
		}
		operationlogservice.add(req, "TESTJOBS", 0, 99, "批量删除任务执行记录成功！");

		String message = "批量删除成功！";
		model.addAttribute("message", message);
		model.addAttribute("url", "/tastExecute/list.do");
		return "success";
	}
	
	/**
	 * 获取进度条数据
	 * @throws Exception 
	 * @Description:
	 */
	@RequestMapping(value = "/progressdata.do")
	public void progressdata(HttpServletRequest req, HttpServletResponse rsp) throws Exception{
		
		String	taskid = req.getParameter("id");
		String[] per = new String[8];
		per[0] = taskid;
		
		TestTaskexcute task = tastExcuteService.get(Integer.valueOf(taskid));
		TestCasedetail caseDetail = new TestCasedetail();
		caseDetail.setTaskId(Integer.valueOf(taskid));
		
		if(task.getCasetotalCount()!=0){
			int excount = casedetailService.findRows(caseDetail);
			//int excount = task.getCasesuccCount()+task.getCasenoexecCount()+task.getCaselockCount()+task.getCasefailCount();
			int a = (int)((Double.valueOf(excount)/Double.valueOf(task.getCasetotalCount()))*100);
			per[1] = String.valueOf(a);
			per[7] = String.valueOf(task.getCasetotalCount());
			if(task.getCasetotalCount()==excount){
				if(null!=task.getFinishtime()){
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
					per[2] = df.format(task.getFinishtime());
				}
				per[3] = String.valueOf(task.getCasesuccCount());
				per[4] = String.valueOf(task.getCasefailCount());
				per[5] = String.valueOf(task.getCaselockCount());
				per[6] = String.valueOf(task.getCasenoexecCount());
			}
		}else{
			per[1] = "0";
		}

	    rsp.setContentType("text/xml;charset=utf-8");
	    JSONArray jsonArray = JSONArray.fromObject(per);
		JSONObject jsobjcet = new JSONObject();
		jsobjcet.put("data", jsonArray); 
		
		rsp.getWriter().write(jsobjcet.toString());
	}
	
}
