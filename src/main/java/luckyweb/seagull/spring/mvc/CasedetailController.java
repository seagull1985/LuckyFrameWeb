package luckyweb.seagull.spring.mvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.quartz.QuartzJob;
import luckyweb.seagull.spring.entity.TestCasedetail;
import luckyweb.seagull.spring.entity.TestTaskexcute;
import luckyweb.seagull.spring.service.CaseDetailService;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.spring.service.TestTastExcuteService;
import luckyweb.seagull.util.DateLib;
import luckyweb.seagull.util.DateUtil;
import luckyweb.seagull.util.StrLib;

@Controller
@RequestMapping("/caseDetail")
public class CasedetailController
{
	/** 总共有多少页 */
	private int	                allPage;
	/** 页面大小 */
	private int	                pageSize	= 20;
	/** 记录总数 */
	private int	                allRows;
	/** 当前页面数， 默认为1 */
	private int	                page	 = 1;
	/** 第一条记录的索引 */
	private int	                offset	 = 0;
	private static final Logger	log	     = Logger.getLogger(CasedetailController.class);

	@Resource(name = "casedetailService")
	private CaseDetailService	casedetailService;

	public TestTastExcuteService getTastExcuteService()
	{
		return tastExcuteService;
	}

	public void setTastExcuteService(TestTastExcuteService tastExcuteService)
	{
		this.tastExcuteService = tastExcuteService;
	}

	public CaseDetailService getCasedetailService()
	{
		return casedetailService;
	}

	public void setCasedetailService(CaseDetailService casedetailService)
	{
		this.casedetailService = casedetailService;
	}

	@Resource(name = "tastExcuteService")
	private TestTastExcuteService	tastExcuteService;

	@Resource(name = "operationlogService")
	private OperationLogService operationlogservice;

	public OperationLogService getOperationlogService() {
		return operationlogservice;
	}
	
	@Resource(name = "sectorprojectsService")
	private SectorProjectsService sectorprojectsService;
	/**
	 * 查询
	 * 
	 * @param caseDetail
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list.do")
	public String list(TestCasedetail caseDetail, HttpServletRequest req, HttpServletResponse rsp, Model model)
	        throws Exception
	{

		req.setCharacterEncoding("utf-8");

		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		String projName = req.getParameter("projName");
		String taskId = req.getParameter("taskId");
		String status = req.getParameter("status");
		
		List tasks = new ArrayList<>();
		if (!StrLib.isEmpty(status)){
			caseDetail.setCasestatus(status);
		}
		if (!StrLib.isEmpty(taskId)){
			if (!StrLib.isEmpty(taskId) && !taskId.equals("0"))
			{
				caseDetail.getTestTaskexcute().setId(Integer.valueOf(taskId));
				caseDetail.setTaskId(Integer.valueOf(taskId));
				TestTaskexcute task = tastExcuteService.get(Integer.valueOf(taskId));
				if (task == null){
					caseDetail.setStartDate(startDate);
					caseDetail.setEndDate(endDate);
				}else{
					caseDetail.setStartDate(DateUtil.format(task.getCreateTime()).substring(0, 10));
					projName = task.getTestJob().getPlanproj();
				}
				projName = StrLib.isEmpty(projName) ? "" : projName;

			}
			else
			{
				projName = StrLib.isEmpty(projName) ? "" : projName;
				caseDetail.setTaskId(0);
			}
		}else{
			if (StrLib.isEmpty(startDate)){
				caseDetail.setStartDate(DateLib.today("yyyy-MM-dd"));
			}else{
				caseDetail.setStartDate(startDate);
			}
			if (StrLib.isEmpty(endDate)){
				caseDetail.setEndDate(DateLib.today("yyyy-MM-dd"));
			}else{
				caseDetail.setEndDate(endDate);
			}
		}
		//tasks = tastExcuteService.findTastListByParam(caseDetail.getStartDate(), projName, null);
		tasks = tastExcuteService.findTastList(caseDetail.getStartDate(), projName, caseDetail.getEndDate());

		if (tasks.size() == 0)
		{
			model.addAttribute("tasks", null);
		}else{
			model.addAttribute("tasks", tasks);
		}
		model.addAttribute("size", tasks.size());

		caseDetail.setProjName(projName);
		model.addAttribute("projName", projName);

		// 任务列表
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		try
		{

			model.addAttribute("taskId", taskId);
			String page2 = req.getParameter("page");
			if (StrLib.isEmpty(page2))
			{
				page = 1;
			}
			else
			{
				if (page2.equals("0"))
				{
					page = 1;
				}
				try
				{
					page = Integer.parseInt(page2);
				}
				catch (Exception e)
				{
					page = 1;
				}
			}
			allRows = casedetailService.findRows(caseDetail);
			
			offset = (page - 1) * pageSize;
			if (allRows % pageSize == 0){
				allPage = allRows / pageSize;
			}else{
				allPage = allRows / pageSize + 1;
			}

			model.addAttribute("caseno", caseDetail.getCaseno());
			List<TestCasedetail> list = casedetailService.findByPage(caseDetail, offset, pageSize);
			
			model.addAttribute("allRows", allRows);
			if (list.size() != 0){
				model.addAttribute("page", page);
				model.addAttribute("offset", offset);
				model.addAttribute("pageSize", pageSize);
				model.addAttribute("allPage", allPage);
				model.addAttribute("list", list);
				
			}
			model.addAttribute("testCasedetail", caseDetail);
			// 项目
			model.addAttribute("projects", QueueListener.projlist);
		}catch (Exception e){
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/caseDetail/list.do");
			return "error";
		}
		return "/jsp/task/casedetail_list";
	}

	/**
	 * 单条用例重新执行
	 * 
	 * @param id
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/execCase.do")
	public String execCase(@PathVariable int id, HttpServletRequest req, HttpServletResponse rsp, Model model)
	        throws Exception
	{
		req.setCharacterEncoding("utf-8");
		rsp.setContentType("text/html;charset=utf-8");	
		PrintWriter pw;

		TestCasedetail caseDetail = this.casedetailService.load(id);
		String url = "/caseDetail/list.do?taskId=" + caseDetail.getTestTaskexcute().getId();

		try
		{
			pw = rsp.getWriter();

			TestTaskexcute task = caseDetail.getTestTaskexcute();

			QuartzJob qj = new QuartzJob();
			String message = qj.toRunCase(task.getTestJob().getPlanproj(),task.getId(), caseDetail.getCaseno(), caseDetail.getCaseversion(),task.getTestJob().getClientip());		
			
			operationlogservice.add(req, "TEST_CASEDETAIL", id, 
					sectorprojectsService.getid(task.getTestJob().getPlanproj()),"自动化用例单条开始执行！任务名称："+task.getTaskId()+
					" 用例编号："+caseDetail.getCaseno()+" 结果："+message);
			
			pw.write(message);

		}
		catch (Exception e)
		{
			model.addAttribute("message", "当前项目在服务器不存在！");
			model.addAttribute("url", url);
			return "error";
		}

		return null;
	}

	/**
	 * 批量执行非成功用例
	 * 
	 * @param req
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/execCaseBatch.do")
	public String execCaseBatch(HttpServletRequest req, HttpServletResponse rsp, Model model) throws Exception
	{
		req.setCharacterEncoding("utf-8");
		rsp.setContentType("text/html;charset=utf-8");
		
		if(!UserLoginController.permissionboolean(req, "case_ex")){
			model.addAttribute("testCasedetail", new TestCasedetail());
			model.addAttribute("url",  "/caseDetail/list.do");
			model.addAttribute("message", "当前用户无权限执行批量用例，请联系管理员！");
			return "success";
		}

		String taskId = req.getParameter("taskId");

		String url = "/caseDetail/list.do?flag=2&taskId=" + taskId;

		PrintWriter pw;
		try
		{
			String[] cases = req.getParameterValues("cases");
			String ckAll = req.getParameter("ckAll");
			String caseId = req.getParameter("caseId");

			TestCasedetail caseDetail = casedetailService.load(Integer.valueOf(caseId));
			TestTaskexcute task = caseDetail.getTestTaskexcute();
			
			if(task.getCasesuccCount()==task.getCasetotalCount()){
				model.addAttribute("message", "未在当前任务下找到非成功状态的用例，请确认！");
				model.addAttribute("url", url);
				return "error";
			}

			StringBuffer caseInfo = new StringBuffer();

			if (!StrLib.isEmpty(ckAll))
			{
				caseInfo.append(ckAll);
			}
			else
			{
				if ((cases==null&&ckAll==null)||cases.length == 0)
				{
					model.addAttribute("message", "请至少选择一个用例或是勾选所有非成功用例！");
					model.addAttribute("url", url);
					return "error";
				}

				String c;
				for (int i = 0; i < cases.length; i++)
				{
					c = cases[i];
					if (i == cases.length - 1)
					{
						caseInfo.append(c);
					}
					else
					{
						caseInfo.append(c).append("#");
					}
				}
			}

			String projName1 = task.getTestJob().getPlanproj();

			// 批量执行用例
			QuartzJob qj = new QuartzJob();
			String message = qj.toRunCaseBatch(projName1, task.getId(), caseInfo.toString(),task.getTestJob().getClientip());		

			operationlogservice.add(req, "TESTJOBS", 0, 
					sectorprojectsService.getid(projName1),"自动化用例批量(非成功)开始执行!"+" 结果："+message);
			
			model.addAttribute("message", message);
			model.addAttribute("url", url);
		}
		catch (Exception e)
		{
			model.addAttribute("message", e);
			model.addAttribute("url", url);
			return "error";
		}

		return "success";
	}

	@RequestMapping(value = "/getTastNameList.do")
	public String getTastNameList(TestCasedetail caseDetail, HttpServletRequest req, HttpServletResponse rsp,
	        Model model) throws Exception
	{
		req.setCharacterEncoding("utf-8");
		rsp.setContentType("text/html;charset=utf-8");
		PrintWriter pw;
		try
		{
			pw = rsp.getWriter();
			String startDate = req.getParameter("startDate");
			String projName = req.getParameter("projName");
			//projName=new String(projName.getBytes("ISO-8859-1"),"UTF-8");
			String json = getTastNameList(startDate, projName);
			pw.write(json);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public String getTastNameList(String startDate, String projName)
	{
		// 任务列表
		//List tasks = tastExcuteService.findTastListByParam(startDate, projName, null);
		List<Object[]> tasks = tastExcuteService.findTastList(startDate, projName, null);
		JSONArray array = JSONArray.fromObject(tasks);
		return array.toString();
	}

	public static void main(String[] args) throws UnsupportedEncodingException
	{

	}

}
