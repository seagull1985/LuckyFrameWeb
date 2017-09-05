package luckyweb.seagull.spring.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import luckyweb.seagull.spring.entity.ProjectCase;
import luckyweb.seagull.spring.entity.ProjectCasesteps;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.ProjectCaseService;
import luckyweb.seagull.spring.service.ProjectCasestepsService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.spring.service.UserInfoService;
import luckyweb.seagull.util.StrLib;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/projectCasesteps")
public class ProjectCasestepsController {

	@Resource(name = "projectCaseService")
	private ProjectCaseService projectcaseservice;

	@Resource(name = "projectCasestepsService")
	private ProjectCasestepsService casestepsservice;

	@Resource(name = "sectorprojectsService")
	private SectorProjectsService sectorprojectsService;

	@Resource(name = "operationlogService")
	private OperationLogService operationlogservice;

	@Resource(name = "userinfoService")
	private UserInfoService userinfoservice;

	/**
	 * 添加步骤
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
	@RequestMapping(value = "/stepadd.do")
	public String add(@Valid @ModelAttribute("casesteps") ProjectCasesteps casesteps, BindingResult br, Model model,
			HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");

			if (!UserLoginController.permissionboolean(req, "case_step")) {
				model.addAttribute("casesteps", new ProjectCasesteps());
				model.addAttribute("url", "/projectCase/load.do");
				model.addAttribute("message", "当前用户无权限管理用例步骤，请联系管理员！");
				return "success";
			}

			String caseid = req.getParameter("caseid");
			ProjectCase prcase = null;
			if (!StrLib.isEmpty(caseid) && !"0".equals(caseid)) {
				prcase = projectcaseservice.load(Integer.valueOf(caseid));
			}
			String retVal = "/jsp/plancase/step_add";
			List<ProjectCasesteps> steps = casestepsservice.getSteps(Integer.valueOf(caseid));
			if (steps.size() == 0) {
				ProjectCasesteps step = new ProjectCasesteps();
				step.setPath("");
				step.setOperation("");
				step.setParameters("");
				step.setAction("");
				step.setCaseid(Integer.valueOf(caseid));
				step.setStepnum(1);
				step.setExpectedresult("");
				step.setProjectid(prcase.getProjectid());
				step.setSteptype(prcase.getCasetype());
				step.setRemark("");
				steps.add(step);
			}

			model.addAttribute("casesign", prcase.getSign());
			model.addAttribute("caseid", caseid);
			model.addAttribute("projectid", prcase.getProjectid());
			model.addAttribute("steps", steps);
			return retVal;

		} catch (Exception e) {
			System.out.println(e);
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/projectCase/load.do");
			return "error";
		}

	}

	/**
	 * 
	 * 
	 * @param tj
	 * @param model
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/editsteps.do")
	private void editsteps(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		StringBuilder sb = new StringBuilder();
		JSONObject json = new JSONObject();
		if (!UserLoginController.permissionboolean(request, "case_step")) {
			json.put("status", "fail");
			json.put("ms", "编辑失败,权限不足,请联系管理员!");
		} else {
			try (BufferedReader reader = request.getReader();) {
				char[] buff = new char[1024];
				int len;
				while ((len = reader.read(buff)) != -1) {
					sb.append(buff, 0, len);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			String jsonstr = sb.toString().substring(1, sb.toString().length() - 1);
			jsonstr = jsonstr.replace("\\\"", "\"");
			jsonstr = jsonstr.replace("undefined", "0");
			System.out.println(jsonstr);
			JSONArray jsonarr = JSONArray.fromObject(jsonstr);
			List<?> list = JSONArray.toList(jsonarr, new ProjectCasesteps(), new JsonConfig());// 参数1为要转换的JSONArray数据，参数2为要转换的目标数据，即List盛装的数据
			String usercode = "";
			if (null != request.getSession().getAttribute("usercode")
					&& null != request.getSession().getAttribute("username")) {
				usercode = request.getSession().getAttribute("usercode").toString();
			}
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = formatter.format(currentTime);
			ProjectCase projectcase = new ProjectCase();
			for (int i = 0; i < list.size(); i++) {
				ProjectCasesteps step = (ProjectCasesteps) list.get(i);
				if (i == 0) {
					casestepsservice.delforcaseid(step.getCaseid());
					projectcase=projectcaseservice.load(step.getCaseid());
				}
				step.setOperationer(usercode);
				step.setTime(time);
				step.setOperation(step.getOperation().replaceAll("&quot;", "\""));
				step.setPath(step.getPath().replaceAll("&quot;", "\""));
				step.setParameters(step.getParameters().replaceAll("&quot;", "\""));
				step.setAction(step.getAction().replaceAll("&quot;", "\""));
				step.setExpectedresult(step.getExpectedresult().replaceAll("&quot;", "\""));
				step.setRemark(step.getRemark().replaceAll("&quot;", "\""));
				casestepsservice.add(step);
				
				projectcase.setOperationer(usercode);
				projectcase.setTime(time);
				projectcaseservice.modify(projectcase);
			}
			json.put("status", "success");
			json.put("ms", "编辑步骤成功!");
		}
		pw.print(json.toString());
	}

	/**
	 * 
	 * 
	 * @param tj
	 * @param model
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/list.do")
	private void ajaxGetSellRecord(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		String strParentID = request.getParameter("strParentID");
		int caseid = 0;
		if (!StrLib.isEmpty(strParentID)) {
			caseid = Integer.valueOf(strParentID);
		}
		List<ProjectCasesteps> casesteps = new ArrayList<ProjectCasesteps>();
		try {
			casesteps = casestepsservice.getSteps(caseid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 转换成json字符串
		String RecordJson = StrLib.listToJson(casesteps);
		pw.print(RecordJson);
	}

	@RequestMapping(value = "/update.do")
	public void updatestep(HttpServletRequest req, HttpServletResponse rsp, ProjectCasesteps casesteps) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();
			StringBuilder sb = new StringBuilder();
			JSONObject json = new JSONObject();
			if (!UserLoginController.permissionboolean(req, "case_step")) {
				json.put("status", "fail");
				json.put("ms", "编辑失败,权限不足,请联系管理员!");
			} else {
				ProjectCase projectcase = new ProjectCase();
				if (null != req.getSession().getAttribute("usercode")
						&& null != req.getSession().getAttribute("username")) {
					String usercode = req.getSession().getAttribute("usercode").toString();
					casesteps.setOperationer(usercode);
					projectcase=projectcaseservice.load(casesteps.getCaseid());
					projectcase.setOperationer(usercode);
				}
				Date currentTime = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time = formatter.format(currentTime);
				casesteps.setTime(time);

				casestepsservice.modify(casesteps);
				
				projectcase.setTime(time);
				projectcaseservice.modify(projectcase);

				json.put("status", "success");
				json.put("ms", "编辑步骤成功!");
			}
			pw.print(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/cgetStepsByCase.do")
	public void cgetStepsByCase(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=GBK");
			req.setCharacterEncoding("GBK");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			String caseid = req.getParameter("caseid");
			
			List<ProjectCasesteps> steps = casestepsservice.getSteps(Integer.valueOf(caseid));

			// 转换成json字符串
			String RecordJson = StrLib.listToJson(steps);

			// 需要返回的数据有总记录数和行数据
			json.put("steps", RecordJson);
			pw.print(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/cpoststep.do")
	public void cpoststep(HttpServletRequest req, HttpServletResponse rsp) throws IOException {
		// 更新实体
		rsp.setContentType("text/html;charset=GBK");
		req.setCharacterEncoding("GBK");
		PrintWriter pw = rsp.getWriter();
		try {
			ProjectCasesteps steps=new ProjectCasesteps();
			
			String path = req.getParameter("path");
			if(null!=path){
				path=path.replace("DHDHDH", "=");
			}
			String operation = req.getParameter("operation");
			String parameters = req.getParameter("parameters");
			if(null!=parameters){
				parameters=parameters.replace("BBFFHH", "%");
			}
			String action = req.getParameter("action");
			String caseid = req.getParameter("caseid");
			String stepnum = req.getParameter("stepnum");
			String expectedresult = req.getParameter("expectedresult");
			if(null!=expectedresult){
				expectedresult=expectedresult.replace("BBFFHH", "%");
			}
			String projectid = req.getParameter("projectid");
			String steptype = req.getParameter("steptype");
			steps.setPath(path);
			steps.setOperation(operation);
			steps.setParameters(parameters);
			steps.setAction(action);
			steps.setCaseid(Integer.valueOf(caseid));
			steps.setStepnum(Integer.valueOf(stepnum));
			steps.setExpectedresult(expectedresult);
			steps.setProjectid(Integer.valueOf(projectid));
			steps.setSteptype(Integer.valueOf(steptype));
			
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = formatter.format(currentTime);
			steps.setTime(time);
			steps.setOperationer("http-post");

			int stepid = casestepsservice.add(steps);

			pw.print(stepid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pw.print("fail");
		}
	}
	
	@RequestMapping(value = "/cUpdateStepExpectedResults.do")
	public void cUpdateStepExpectedResults(HttpServletRequest req, HttpServletResponse rsp) {
		try {
			rsp.setContentType("text/html;charset=GBK");
			req.setCharacterEncoding("GBK");
			PrintWriter pw = rsp.getWriter();
			String caseno = req.getParameter("caseno");
			String stepnum = req.getParameter("stepnum");
			String expectedresults = req.getParameter("expectedresults");
			expectedresults=expectedresults.replace("BBFFHH", "%");
			expectedresults=expectedresults.replace("DHDHDH", "=");
			expectedresults=expectedresults.replace("ANDAND", "&");
			
			String result="更新用例【"+caseno+"】第【"+stepnum+"】步失败，预期结果【"+expectedresults+"】";
			ProjectCase pc=projectcaseservice.getCaseBySign(caseno);
			List<ProjectCasesteps> steps= casestepsservice.getSteps(pc.getId());
			
			for(ProjectCasesteps step:steps){
				if(stepnum.equals(String.valueOf(step.getStepnum()))){				
					Date currentTime = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time = formatter.format(currentTime);
					
					step.setTime(time);
					step.setOperationer("http-post");
					step.setExpectedresult(expectedresults);
					
					pc.setTime(time);
					pc.setOperationer("http-post");
					
					casestepsservice.modify(step);
					projectcaseservice.modify(pc);
					result = "更新用例【"+caseno+"】第【"+stepnum+"】步成功，预期结果【"+expectedresults+"】";
					break;
				}
			}
			pw.print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {

	}

}
