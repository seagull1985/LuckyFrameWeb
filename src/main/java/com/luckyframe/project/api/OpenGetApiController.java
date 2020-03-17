package com.luckyframe.project.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.luckyframe.framework.config.LuckyFrameConfig;
import com.luckyframe.project.testexecution.taskCaseExecute.domain.TaskCaseExecute;
import com.luckyframe.project.testexecution.taskCaseExecute.service.ITaskCaseExecuteService;
import com.luckyframe.project.testexecution.taskExecute.domain.TaskExecute;
import com.luckyframe.project.testexecution.taskExecute.service.ITaskExecuteService;
import com.luckyframe.project.testexecution.taskScheduling.domain.TaskScheduling;
import com.luckyframe.project.testexecution.taskScheduling.service.ITaskSchedulingService;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCase;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseSteps;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseService;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseStepsService;
import com.luckyframe.project.testmanagmt.projectCaseParams.domain.ProjectCaseParams;
import com.luckyframe.project.testmanagmt.projectCaseParams.service.IProjectCaseParamsService;
import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlan;
import com.luckyframe.project.testmanagmt.projectPlan.service.IProjectPlanService;
import com.luckyframe.project.testmanagmt.projectProtocolTemplate.domain.ProjectProtocolTemplate;
import com.luckyframe.project.testmanagmt.projectProtocolTemplate.domain.ProjectTemplateParams;
import com.luckyframe.project.testmanagmt.projectProtocolTemplate.service.IProjectProtocolTemplateService;
import com.luckyframe.project.testmanagmt.projectProtocolTemplate.service.IProjectTemplateParamsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * 统一API对外获取数据类
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改 有任何疑问欢迎联系作者讨论。 QQ:1573584944 Seagull
 * =================================================================
 * @author Seagull
 * @date 2019年10月28日
 */
@Controller
@RequestMapping("/openGetApi")
public class OpenGetApiController
{
    private static final Logger log = LoggerFactory.getLogger(OpenGetApiController.class);

	@Resource
	private IProjectPlanService projectPlanService;
	
	@Resource
	private IProjectCaseService projectCaseService;
	
	@Resource
	private IProjectCaseStepsService projectCaseStepsService;
	
	@Resource
	private ITaskExecuteService taskExecuteService;
	
	@Resource
	private IProjectCaseParamsService projectCaseParamsService;
	
	@Resource
	private ITaskSchedulingService taskSchedulingService;
	
	@Resource
	private ITaskCaseExecuteService taskCaseExecuteService;
	
	@Resource
	private IProjectProtocolTemplateService projectProtocolTemplateService;
	
	@Resource
	private IProjectTemplateParamsService projectTemplateParamsService;
	
    @Resource
    private LuckyFrameConfig lfConfig;

	/**
	 * 通过计划ID获取用例列表
	 * @param req HTTP请求
	 * @param rsp HTTP响应
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
			log.error("通过计划ID获取用例列表出现异常", e);
		}
	}
	
	/**
	 * 获取服务器版本号
	 * @param req HTTP请求
	 * @param rsp HTTP响应
	 * @author Seagull
	 * @date 2019年4月24日
	 */
	@RequestMapping(value = "/clientGetServerVersion.do")
	public void clientGetServerVersion(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=GBK");
			req.setCharacterEncoding("GBK");
			PrintWriter pw = rsp.getWriter();
			pw.print("Version "+lfConfig.getVersion());
		} catch (Exception e) {
			log.error("获取服务器版本号出现异常", e);
		}
	}
	
	/**
	 * 通过计划名称获取用例列表
	 * @param req HTTP请求
	 * @param rsp HTTP响应
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
			log.error("通过计划名称获取用例列表出现异常", e);
		}
	}
	
	
	/**
	 * 通过用例ID获取步骤列表
	 * @param req HTTP请求
	 * @param rsp HTTP响应
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
			log.error("通过用例ID获取步骤列表出现异常", e);
		}
	}
	
	/**
	 * 通过taskId获取实体
	 * @param req HTTP请求
	 * @param rsp HTTP响应
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
			log.error("通过taskId获取实体出现异常", e);
		}
	}
	
	/**
	 * 通过用例ID获取实体
	 * @param req HTTP请求
	 * @param rsp HTTP响应
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
			log.error("通过用例编号获取实体出现异常", e);
		}
	}
	
	/**
	 * 通过用例编号获取实体
	 * @param req HTTP请求
	 * @param rsp HTTP响应
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
			log.error("通过用例编号获取实体出现异常", e);
		}
	}
	
	/**
	 * 根据项目ID获取公共参数列表
	 * @param req HTTP请求
	 * @param rsp HTTP响应
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
			List<ProjectCaseParams> projectCaseParamsList = projectCaseParamsService.selectProjectCaseParamsListByProjectId(projectId);

			// 转换成json字符串
			JSONArray array= JSONArray.parseArray(JSON.toJSONString(projectCaseParamsList));
			pw.print(array.toString());
		} catch (Exception e) {
			log.error("根据项目ID获取公共参数列表出现异常", e);
		}
	}
	
	/**
	 * 通过执行任务ID获取调度实体
	 * @param req HTTP请求
	 * @param rsp HTTP响应
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
			log.error("通过执行任务ID获取调度实体出现异常", e);
		}
	}
	
	/**
	 * 通过计划名称获取用例列表
	 * @param req HTTP请求
	 * @param rsp HTTP响应
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
			log.error("通过计划名称获取用例列表出现异常", e);
		}
	}
	
	/**
	 * 通过模板ID获取实体
	 * @param req HTTP请求
	 * @param rsp HTTP响应
	 * @author Seagull
	 * @date 2019年4月24日
	 */
	@RequestMapping(value = "/clientGetProjectProtocolTemplateByTemplateId.do")
	public void clientGetProjectProtocolTemplateByTemplateId(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=GBK");
			req.setCharacterEncoding("GBK");
			PrintWriter pw = rsp.getWriter();
			Integer templateId = Integer.valueOf(req.getParameter("templateId"));
			ProjectProtocolTemplate projectProtocolTemplate = projectProtocolTemplateService.selectProjectProtocolTemplateById(templateId);

			pw.print(JSONObject.toJSONString(projectProtocolTemplate));
		} catch (Exception e) {
			log.error("通过模板ID获取实体出现异常", e);
		}
	}
	
	/**
	 * 根据模板ID获取参数
	 * @param req HTTP请求
	 * @param rsp HTTP响应
	 * @author Seagull
	 * @date 2019年4月24日
	 */
	@RequestMapping(value = "/clientGetProjectTemplateParamsListByTemplateId.do")
	public void clientGetProjectTemplateParamsListByTemplateId(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=GBK");
			req.setCharacterEncoding("GBK");
			PrintWriter pw = rsp.getWriter();
			Integer templateId = Integer.valueOf(req.getParameter("templateId"));
			ProjectTemplateParams projectTemplateParams = new ProjectTemplateParams();
			projectTemplateParams.setTemplateId(templateId);
			List<ProjectTemplateParams> projectTemplateParamsList = projectTemplateParamsService.selectProjectTemplateParamsList(projectTemplateParams);

			// 转换成json字符串
			JSONArray array= JSONArray.parseArray(JSON.toJSONString(projectTemplateParamsList));
			pw.print(array.toString());
		} catch (Exception e) {
			log.error("根据模板ID获取参数出现异常", e);
		}
	}
}
