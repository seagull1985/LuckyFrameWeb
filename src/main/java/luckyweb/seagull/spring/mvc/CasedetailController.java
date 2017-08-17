package luckyweb.seagull.spring.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import luckyweb.seagull.quartz.QuartzJob;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.entity.TestCasedetail;
import luckyweb.seagull.spring.entity.TestTaskexcute;
import luckyweb.seagull.spring.service.CaseDetailService;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.spring.service.TestTastExcuteService;
import luckyweb.seagull.util.DateLib;
import luckyweb.seagull.util.StrLib;

@Controller
@RequestMapping("/caseDetail")
public class CasedetailController
{
	private static final Logger	log	     = Logger.getLogger(CasedetailController.class);

	@Resource(name = "casedetailService")
	private CaseDetailService	casedetailService;

	@Resource(name = "tastExcuteService")
	private TestTastExcuteService	tastExcuteService;

	@Resource(name = "operationlogService")
	private OperationLogService operationlogservice;
	
	@Resource(name = "sectorprojectsService")
	private SectorProjectsService sectorprojectsService;
	
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
			String taskId = req.getParameter("taskId");
			String status = req.getParameter("status");
			List<SectorProjects> prolist=sectorprojectsService.getAllProject();
			for(int i=0;i<prolist.size();i++){
				if(prolist.get(i).getProjecttype()==1){
					prolist.get(i).setProjectname(prolist.get(i).getProjectname()+"(TestLink项目)");
				}
			}
			
			TestTaskexcute task=null;
			List tasks =null;
			String dateStr=DateLib.today("yyyy-MM-dd");
			if(!StrLib.isEmpty(taskId)){
				task = tastExcuteService.get(Integer.valueOf(taskId));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				dateStr = sdf.format(task.getCreateTime());
				tasks = tastExcuteService.findTastList(dateStr, task.getTestJob().getProjectid().toString(), dateStr);
				model.addAttribute("projectid", task.getTestJob().getProjectid());
			}

			model.addAttribute("status", status);
			model.addAttribute("tasks", tasks);
			model.addAttribute("projects", prolist);
			model.addAttribute("date", dateStr);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/caseDetail/load.do");
			return "error";
		}
		return "/jsp/task/casedetail_list";
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/list.do")
	private void ajaxGetSellRecord(Integer limit, Integer offset, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		String search = request.getParameter("search");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String taskId = request.getParameter("taskId");
		String status = request.getParameter("status");
		TestCasedetail caseDetail = new TestCasedetail();
		if (null == offset && null == limit) {
			offset = 0;
		}
		if (null == limit || limit == 0) {
			limit = 10;
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(search)) {
			caseDetail.setCasename(search);
			caseDetail.setCaseno(search);
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(taskId)) {
			caseDetail.setTaskId(Integer.valueOf(taskId));
		}

		if (StrLib.isEmpty(startDate)) {
			caseDetail.setStartDate(DateLib.befor_Nd_format("yyyy-MM-dd", 6));
		} else {
			caseDetail.setStartDate(startDate);
		}
		if (StrLib.isEmpty(endDate)) {
			caseDetail.setEndDate(DateLib.today("yyyy-MM-dd"));
		} else {
			caseDetail.setEndDate(endDate);
		}
		if (!StrLib.isEmpty(status)) {
			caseDetail.setCasestatus(status);
		}
		
		List<TestCasedetail> caselist = casedetailService.findByPage(caseDetail, offset, limit);
		// 转换成json字符串
		String RecordJson = StrLib.listToJson(caselist);
		// 得到总记录数
		int total = casedetailService.findRows(caseDetail);
		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", RecordJson);
		pw.print(json.toString());
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

	/**
	 * 根据caseId执行用例
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
	@RequestMapping(value = "/runCase.do")
	public void delete(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			if (!UserLoginController.permissionboolean(req, "case_ex")) {
				json.put("status", "fail");
				json.put("ms", "执行用例失败,权限不足,请联系管理员!");
			} else {
				StringBuilder sb = new StringBuilder();
				try (BufferedReader reader = req.getReader();) {
					char[] buff = new char[1024];
					int len;
					while ((len = reader.read(buff)) != -1) {
						sb.append(buff, 0, len);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				JSONObject jsonObject = JSONObject.fromObject(sb.toString());
				JSONArray jsonarr = JSONArray.fromObject(jsonObject.getString("caseids"));
				String status="success";
				String ms="执行任务成功！";
                 
				if(jsonarr.size()==1){
					int id = Integer.valueOf(jsonarr.get(0).toString());
					TestCasedetail caseDetail = this.casedetailService.load(id);
					TestTaskexcute task = caseDetail.getTestTaskexcute();
					QuartzJob qj = new QuartzJob();
					ms = qj.toRunCase(task.getTestJob().getPlanproj(),task.getId(), caseDetail.getCaseno(), caseDetail.getCaseversion(),task.getTestJob().getClientip());						
					operationlogservice.add(req, "TEST_CASEDETAIL", id, 
								sectorprojectsService.getid(task.getTestJob().getPlanproj()),"自动化用例单条开始执行！任务名称："+task.getTaskId()+
								" 用例编号："+caseDetail.getCaseno()+" 结果："+ms);
				}else{
					String projName;
					if("ALLFAIL".equals(jsonarr.get(0).toString())){
						int taskid = Integer.valueOf(jsonarr.get(1).toString());
						TestTaskexcute task = tastExcuteService.load(taskid);
						projName = task.getTestJob().getPlanproj();
						// 批量执行用例
						QuartzJob qj = new QuartzJob();
						ms = qj.toRunCaseBatch(projName, task.getId(), "ALLFAIL",task.getTestJob().getClientip());
						operationlogservice.add(req, "TEST_CASEDETAIL", 0, 
								sectorprojectsService.getid(projName),"全部非成功自动化用例开始执行!"+" 结果："+ms);
					}else{
						StringBuffer caseInfo = new StringBuffer();
						TestTaskexcute task=null;
						for (int i = 0; i < jsonarr.size(); i++) {
							int id = Integer.valueOf(jsonarr.get(i).toString());
							TestCasedetail caseDetail = casedetailService.load(Integer.valueOf(id));
							String ocase=caseDetail.getCaseno()+"%"+caseDetail.getCaseversion();
							caseInfo.append(ocase).append("#");
							task = caseDetail.getTestTaskexcute();
						}
						projName = task.getTestJob().getPlanproj();
						// 批量执行用例
						QuartzJob qj = new QuartzJob();
						ms = qj.toRunCaseBatch(projName, task.getId(), caseInfo.toString(),task.getTestJob().getClientip());		

						operationlogservice.add(req, "TEST_CASEDETAIL", 0, 
								sectorprojectsService.getid(projName),"自动化用例批量(非成功)开始执行!"+" 结果："+ms);
					}
				}
				
				json.put("status", status);
				json.put("ms", ms);
			}
			pw.print(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value = "/getTastNameList.do")
	public void getTastNameList(TestCasedetail caseDetail, HttpServletRequest req, HttpServletResponse rsp,
	        Model model) throws Exception
	{
		req.setCharacterEncoding("utf-8");
		rsp.setContentType("text/html;charset=utf-8");
		PrintWriter pw;
		try
		{
			pw = rsp.getWriter();
			String startDate = req.getParameter("startDate");
			String endDate = req.getParameter("endDate");
			String projid = req.getParameter("projid");
			List<Object[]> tasks = tastExcuteService.findTastList(startDate, projid, endDate);
			JSONArray array = JSONArray.fromObject(tasks);
			pw.write(array.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException
	{

	}

}
