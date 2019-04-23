package com.luckyframe.project.api;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.luckyframe.common.utils.file.FileUploadUtils;
import com.luckyframe.common.utils.file.FileUtils;
import com.luckyframe.framework.config.LuckyFrameConfig;
import com.luckyframe.framework.config.ServerConfig;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.project.testexecution.taskCaseExecute.domain.TaskCaseExecute;
import com.luckyframe.project.testexecution.taskCaseExecute.service.ITaskCaseExecuteService;
import com.luckyframe.project.testexecution.taskCaseLog.service.ITaskCaseLogService;
import com.luckyframe.project.testexecution.taskExecute.domain.TaskExecute;
import com.luckyframe.project.testexecution.taskExecute.service.ITaskExecuteService;
import com.luckyframe.project.testexecution.taskScheduling.domain.TaskScheduling;
import com.luckyframe.project.testexecution.taskScheduling.service.ITaskSchedulingService;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCase;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseDebug;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseSteps;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseDebugService;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseService;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseStepsService;
import com.luckyframe.project.testmanagmt.projectCaseParams.domain.ProjectCaseParams;
import com.luckyframe.project.testmanagmt.projectCaseParams.service.IProjectCaseParamsService;
import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlan;
import com.luckyframe.project.testmanagmt.projectPlan.service.IProjectPlanCaseService;
import com.luckyframe.project.testmanagmt.projectPlan.service.IProjectPlanService;

/**
 * 通用请求处理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/openGetApi")
public class OpenGetApiController
{
    private static final Logger log = LoggerFactory.getLogger(OpenGetApiController.class);

	@Autowired
	private IProjectPlanService projectPlanService;
	
	@Autowired
	private IProjectCaseService projectCaseService;
	
	@Autowired
	private IProjectCaseStepsService projectCaseStepsService;
	
	@Autowired
	private ITaskExecuteService taskExecuteService;
	
	@Autowired
	private IProjectCaseParamsService projectCaseParamsService;
	
	@Autowired
	private IProjectCaseDebugService projectCaseDebugService;
	
	@Autowired
	private ITaskSchedulingService taskSchedulingService;
	
	@Autowired
	private ITaskCaseExecuteService taskCaseExecuteService;
	
	@Autowired
	private ITaskCaseLogService taskCaseLogService;
	
	/**
	 * 通过计划ID获取用例列表
	 * @param req
	 * @param rsp
	 * @author Seagull
	 * @date 2019年4月16日
	 */
	@RequestMapping(value = "/clientGetCaseListByPlanId.do")
	public void clientGetCaseListByPlanId(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=GBK");
			req.setCharacterEncoding("GBK");
			PrintWriter pw = rsp.getWriter();
			Integer planId = Integer.valueOf(req.getParameter("planId"));

			ProjectCase projectCase=new ProjectCase();
			projectCase.setPlanId(planId);
			projectCase.setFlag(true);
			List<ProjectCase> projectcases=projectCaseService.selectProjectCaseListForPlan(projectCase);

			// 转换成json字符串
			JSONArray array= JSONArray.parseArray(JSON.toJSONString(projectcases));
			pw.print(array.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过计划名称获取用例列表
	 * @param req
	 * @param rsp
	 * @author Seagull
	 * @date 2019年4月16日
	 */
	@RequestMapping(value = "/clientGetCaseListByPlanName.do")
	public void clientGetCaseListByPlanName(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=GBK");
			req.setCharacterEncoding("GBK");
			PrintWriter pw = rsp.getWriter();
			String planName = req.getParameter("planName");
			ProjectPlan projectPlan= projectPlanService.selectProjectPlanByPlanName(planName);

			ProjectCase projectCase=new ProjectCase();
			projectCase.setPlanId(projectPlan.getPlanId());
			projectCase.setFlag(true);
			List<ProjectCase> projectcases=projectCaseService.selectProjectCaseListForPlan(projectCase);

			// 转换成json字符串
			JSONArray array= JSONArray.parseArray(JSON.toJSONString(projectcases));
			pw.print(array.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 通过用例ID获取步骤列表
	 * @param req
	 * @param rsp
	 * @author Seagull
	 * @date 2019年4月16日
	 */
	@RequestMapping(value = "/clientGetStepListByCaseId.do")
	public void clientGetStepListByCaseId(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=GBK");
			req.setCharacterEncoding("GBK");
			PrintWriter pw = rsp.getWriter();
			Integer caseId = Integer.valueOf(req.getParameter("caseId"));

			ProjectCaseSteps projectCaseSteps=new ProjectCaseSteps();
			projectCaseSteps.setCaseId(caseId);
			List<ProjectCaseSteps> projectCaseStepsList=projectCaseStepsService.selectProjectCaseStepsList(projectCaseSteps);

			// 转换成json字符串
			JSONArray array= JSONArray.parseArray(JSON.toJSONString(projectCaseStepsList));
			pw.print(array.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过taskId获取实体
	 * @param req
	 * @param rsp
	 * @author Seagull
	 * @date 2019年4月16日
	 */
	@RequestMapping(value = "/clientGetTaskByTaskId.do")
	public void clientGetTaskByTaskId(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=GBK");
			req.setCharacterEncoding("GBK");
			PrintWriter pw = rsp.getWriter();
			Integer taskId = Integer.valueOf(req.getParameter("taskId"));
			TaskExecute taskExecute = taskExecuteService.selectTaskExecuteById(taskId);

			pw.print(JSONObject.toJSONString(taskExecute));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过用例编号获取实体
	 * @param req
	 * @param rsp
	 * @author Seagull
	 * @date 2019年4月16日
	 */
	@RequestMapping(value = "/clientGetCaseByCaseId.do")
	public void clientGetCaseByCaseId(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=GBK");
			req.setCharacterEncoding("GBK");
			PrintWriter pw = rsp.getWriter();
			Integer caseId = Integer.valueOf(req.getParameter("caseId"));
			ProjectCase projectCase = projectCaseService.selectProjectCaseById(caseId);

			pw.print(JSONObject.toJSONString(projectCase));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过用例编号获取实体
	 * @param req
	 * @param rsp
	 * @author Seagull
	 * @date 2019年4月16日
	 */
	@RequestMapping(value = "/clientGetCaseByCaseSign.do")
	public void clientGetCaseByCaseSign(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=GBK");
			req.setCharacterEncoding("GBK");
			PrintWriter pw = rsp.getWriter();
			String caseSign = req.getParameter("caseSign");
			ProjectCase projectCase = projectCaseService.selectProjectCaseByCaseSign(caseSign);

			pw.print(JSONObject.toJSONString(projectCase));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据项目ID获取公共参数列表
	 * @param req
	 * @param rsp
	 * @author Seagull
	 * @date 2019年4月16日
	 */
	@RequestMapping(value = "/clientGetParamsByProjectId.do")
	public void clientGetParamsByProjectId(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=GBK");
			req.setCharacterEncoding("GBK");
			PrintWriter pw = rsp.getWriter();
			Integer projectId = Integer.valueOf(req.getParameter("projectId"));
			ProjectCaseParams projectCaseParams = new ProjectCaseParams();
			projectCaseParams.setProjectId(projectId);
			List<ProjectCaseParams> projectCaseParamsList = projectCaseParamsService.selectProjectCaseParamsList(projectCaseParams);

			// 转换成json字符串
			JSONArray array= JSONArray.parseArray(JSON.toJSONString(projectCaseParamsList));
			pw.print(array.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过执行任务ID获取调度实体
	 * @param req
	 * @param rsp
	 * @author Seagull
	 * @date 2019年4月16日
	 */
	@RequestMapping(value = "/clientGetTaskSchedulingByTaskId.do")
	public void clientGetTaskSchedulingByTaskId(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=GBK");
			req.setCharacterEncoding("GBK");
			PrintWriter pw = rsp.getWriter();
			Integer taskId = Integer.valueOf(req.getParameter("taskId"));
			TaskExecute taskExecute = taskExecuteService.selectTaskExecuteById(taskId);
			TaskScheduling taskScheduling = taskSchedulingService.selectTaskSchedulingById(taskExecute.getSchedulingId());

			pw.print(JSONObject.toJSONString(taskScheduling));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过计划名称获取用例列表
	 * @param req
	 * @param rsp
	 * @author Seagull
	 * @date 2019年4月16日
	 */
	@RequestMapping(value = "/clientGetCaseListForUnSucByTaskId.do")
	public void clientGetCaseListForUnSucByTaskId(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=GBK");
			req.setCharacterEncoding("GBK");
			PrintWriter pw = rsp.getWriter();
			Integer taskId = Integer.valueOf(req.getParameter("taskId"));
			List<TaskCaseExecute> taskCaseExecuteList= taskCaseExecuteService.selectTaskCaseExecuteListForUnSucByTaskId(taskId);

			Integer[] caseIdArr = new Integer[taskCaseExecuteList.size()];
			
			for(int i=0;i<taskCaseExecuteList.size();i++){
				caseIdArr[i]=taskCaseExecuteList.get(i).getCaseId();
			}

			// 转换成json字符串
			JSONArray array= JSONArray.parseArray(JSON.toJSONString(caseIdArr));
			pw.print(array.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
