package luckyweb.seagull.spring.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import luckyweb.seagull.comm.PublicConst;
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
@RequestMapping("/tastExecute")
public class TastExcuteController {
	private static final Logger log = Logger.getLogger(TastExcuteController.class);

	@Resource(name = "tastExcuteService")
	private TestTastExcuteService tastExcuteService;

	@Resource(name = "testJobsService")
	private TestJobsService testJobsService;

	@Resource(name = "casedetailService")
	private CaseDetailService casedetailService;

	@Resource(name = "logdetailService")
	private LogDetailService logdetailService;

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
			// 调度列表
			List<TestJobs> jobs = testJobsService.findJobsList();
			String jobid = req.getParameter("jobid");
			if (StrLib.isEmpty(jobid)) {
				jobid = "0";
			}
			model.addAttribute("jobs", jobs);
			model.addAttribute("jobid", jobid);

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/tastExecute/load.do");
			return "error";
		}
		return "/jsp/task/taskexcute_list";
	}

	@RequestMapping(value = "/list.do")
	private void ajaxGetSellRecord(Integer limit, Integer offset, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		String search = request.getParameter("search");
		String jobid = request.getParameter("jobid");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String status = request.getParameter("status");
		TestTaskexcute task = new TestTaskexcute();
		if (null == offset && null == limit) {
			offset = 0;
		}
		if (null == limit || limit == 0) {
			limit = 10;
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(search)) {
			task.setTaskId(search);
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(jobid)) {
			task.setJobid(Integer.valueOf(jobid));
		}

		if (StrLib.isEmpty(startDate)) {
			task.setStartDate(DateLib.beforNdFormat("yyyy-MM-dd", 7));
		} else {
			task.setStartDate(startDate);
		}
		if (StrLib.isEmpty(endDate)) {
			task.setEndDate(DateLib.today("yyyy-MM-dd"));
		} else {
			task.setEndDate(endDate);
		}
		if (!StrLib.isEmpty(status)) {
			task.setTaskStatus(status);
		}
		List<TestTaskexcute> tasklist = tastExcuteService.findByPage(task, offset, limit);
		// 转换成json字符串
		JSONArray recordJson = StrLib.listToJson(tasklist);
		// 得到总记录数
		int total = tastExcuteService.findRows(task);
		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", recordJson);
		pw.print(json.toString());
	}

	/**
	 * 根据任务Id删除任务
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
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHTASKEXDEL)) {
				json.put("status", "fail");
				json.put("ms", "删除任务失败,权限不足,请联系管理员!");
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
				JSONArray jsonarr = JSONArray.parseArray(jsonObject.getString("taskids"));
				
				String status="success";
				String ms="删除任务成功!";             
				for (int i = 0; i < jsonarr.size(); i++) {
					int id = Integer.valueOf(jsonarr.get(i).toString());
					TestTaskexcute task = tastExcuteService.get(id);

					if(!UserLoginController.oppidboolean(req, task.getTestJob().getProjectid())){
						status = "fail";
						ms = "您有任务没有项目权限进行删除，请确认！";
						break;
					}	
					
					if (task == null) {
						status = "fail";
						ms = "有任务不存在或已经删除，请确认！";
						break;
					}

					//30分钟前
					Date date = new Date(System.currentTimeMillis()-1000*60*30);
					boolean existed = ("0".equals(task.getTaskStatus()) || "1".equals(task.getTaskStatus()))
							&& task.getCreateTime().after(date);
					if (existed) {
						status = "fail";
						ms = "任务开始30分钟时间内不允许删除！";
						break;
					}

					try {
						this.logdetailService.delete(id);
						this.casedetailService.delete(id);
						this.tastExcuteService.delete(id);
					} catch (Exception e) {
						status = "fail";
						ms = "任务删除过程中异常，请确认！";
						break;
					}

					operationlogservice.add(req, "TEST_TASKEXCUTE", id,
							task.getTestJob().getProjectid(),1,
							"自动化用例已执行任务记录删除成功！任务名称：" + task.getTaskId());
				}

				json.put("status", status);
				json.put("ms", ms);
			}
			pw.print(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取进度条数据
	 * 
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/progressdata.do")
	public void progressdata(HttpServletRequest req, HttpServletResponse rsp) throws Exception {

		String taskid = req.getParameter("id");
		String[] per = new String[8];
		per[0] = taskid;

		TestTaskexcute task = tastExcuteService.get(Integer.valueOf(taskid));
		TestCasedetail caseDetail = new TestCasedetail();
		caseDetail.setTaskId(Integer.valueOf(taskid));

		if (null != task && task.getCasetotalCount() != 0) {
			int excount = casedetailService.findRows(caseDetail);
			// int excount =
			// task.getCasesuccCount()+task.getCasenoexecCount()+task.getCaselockCount()+task.getCasefailCount();
			int a = (int) ((Double.valueOf(excount) / Double.valueOf(task.getCasetotalCount())) * 100);
			per[1] = String.valueOf(a);
			per[7] = String.valueOf(task.getCasetotalCount());
			if (task.getCasetotalCount() == excount) {
				if (null != task.getFinishtime()) {
					// 定义格式，不显示毫秒
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					per[2] = df.format(task.getFinishtime());
				}
				per[3] = String.valueOf(task.getCasesuccCount());
				per[4] = String.valueOf(task.getCasefailCount());
				per[5] = String.valueOf(task.getCaselockCount());
				per[6] = String.valueOf(task.getCasenoexecCount());
			}
		} else {
			per[1] = "0";
		}

		rsp.setContentType("text/xml;charset=utf-8");
		JSONArray jsonArray =  (JSONArray)JSONArray.toJSON(per);
		JSONObject jsobjcet = new JSONObject();
		jsobjcet.put("data", jsonArray);

		rsp.getWriter().write(jsobjcet.toString());
	}

	@RequestMapping(value = "/cgettaskbyid.do")
	public void cgetCaseByPlan(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=GBK");
			req.setCharacterEncoding("GBK");
			PrintWriter pw = rsp.getWriter();
			String taskid = req.getParameter("taskid");

			TestTaskexcute task = tastExcuteService.load(Integer.valueOf(taskid));
			String jsonStr = JSONObject.toJSONString(task);
			pw.print(jsonStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
