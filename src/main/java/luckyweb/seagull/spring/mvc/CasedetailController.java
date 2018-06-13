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

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import luckyweb.seagull.comm.PublicConst;
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
				model.addAttribute("taskid", taskId);
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

	@RequestMapping(value = "/list.do")
	private void ajaxGetSellRecord(Integer limit, Integer offset, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
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
			caseDetail.setStartDate(DateLib.beforNdFormat("yyyy-MM-dd", 6));
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
		JSONArray recordJson = StrLib.listToJson(caselist);
		// 得到总记录数
		int total = casedetailService.findRows(caseDetail);
		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", recordJson);
		pw.print(json.toString());
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
	public void runCase(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHCASEEXC)) {
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
				JSONObject jsonObject = JSONObject.parseObject(sb.toString());
				JSONArray jsonarr = JSONArray.parseArray(jsonObject.getString("caseids"));
				String status="success";
				String ms="执行任务成功！";
                
				TestTaskexcute task=null;
				if (PublicConst.AUTHCASEALLFAIL.equals(jsonarr.get(0).toString())){
					int taskid = Integer.valueOf(jsonarr.get(1).toString());
					task = tastExcuteService.load(taskid);
				}else{
					int id = Integer.valueOf(jsonarr.get(0).toString());
					TestCasedetail caseDetail = this.casedetailService.load(id);
					task = caseDetail.getTestTaskexcute();
				}
				
				if (!UserLoginController.oppidboolean(req, task.getTestJob().getProjectid())) {
					status = "fail";
					ms = "您没有执行此项目用例的权限！";
				} else {
					if (jsonarr.size() == 1) {
						int id = Integer.valueOf(jsonarr.get(0).toString());
						TestCasedetail caseDetail = this.casedetailService.load(id);
						
						QuartzJob qj = new QuartzJob();
						ms = qj.toRunCase(task.getTestJob().getPlanproj(), task.getId(), caseDetail.getCaseno(),
								caseDetail.getCaseversion(), task.getTestJob().getClientip(),task.getTestJob().getClientpath());
						operationlogservice.add(req, "TEST_CASEDETAIL", id,
								sectorprojectsService.getid(task.getTestJob().getPlanproj()), 1,"自动化用例单条开始执行！任务名称："
										+ task.getTaskId() + " 用例编号：" + caseDetail.getCaseno() + " 结果：" + ms);
					} else {
						String projName;
						if (PublicConst.AUTHCASEALLFAIL.equals(jsonarr.get(0).toString())) {
							projName = task.getTestJob().getPlanproj();
							// 批量执行用例
							QuartzJob qj = new QuartzJob();
							ms = qj.toRunCaseBatch(projName, task.getId(), "ALLFAIL", task.getTestJob().getClientip(),task.getTestJob().getClientpath());
							operationlogservice.add(req, "TEST_CASEDETAIL", 0, sectorprojectsService.getid(projName),1,
									"全部非成功自动化用例开始执行!" + " 结果：" + ms);
						} else {
							StringBuffer caseInfo = new StringBuffer();
							for (int i = 0; i < jsonarr.size(); i++) {
								int id = Integer.valueOf(jsonarr.get(i).toString());
								TestCasedetail caseDetail = casedetailService.load(Integer.valueOf(id));
								String ocase = caseDetail.getCaseno() + "%" + caseDetail.getCaseversion();
								caseInfo.append(ocase).append("#");
							}
							projName = task.getTestJob().getPlanproj();
							// 批量执行用例
							QuartzJob qj = new QuartzJob();
							ms = qj.toRunCaseBatch(projName, task.getId(), caseInfo.toString(),
									task.getTestJob().getClientip(),task.getTestJob().getClientpath());

							operationlogservice.add(req, "TEST_CASEDETAIL", 0, sectorprojectsService.getid(projName),1,
									"自动化用例批量(非成功)开始执行!" + " 结果：" + ms);
						}
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
			JSONArray array = JSONArray.parseArray(JSON.toJSONString(tasks));
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
