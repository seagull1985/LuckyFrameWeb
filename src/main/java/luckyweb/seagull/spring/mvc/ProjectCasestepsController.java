package luckyweb.seagull.spring.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.Naming;
import java.rmi.RemoteException;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.spring.entity.ProjectCase;
import luckyweb.seagull.spring.entity.ProjectCasesteps;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.entity.TempCasestepDebug;
import luckyweb.seagull.spring.entity.TestClient;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.ProjectCaseService;
import luckyweb.seagull.spring.service.ProjectCasestepsService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.spring.service.TempCasestepDebugService;
import luckyweb.seagull.spring.service.TestClientService;
import luckyweb.seagull.spring.service.UserInfoService;
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
@RequestMapping("/projectCasesteps")
public class ProjectCasestepsController {

	@Resource(name = "projectCaseService")
	private ProjectCaseService projectcaseservice;

	@Resource(name = "projectCasestepsService")
	private ProjectCasestepsService casestepsservice;

	@Resource(name = "tempcasestepdebugService")
	private TempCasestepDebugService tempdebugservice;

	@Resource(name = "sectorprojectsService")
	private SectorProjectsService sectorprojectsService;

	@Resource(name = "operationlogService")
	private OperationLogService operationlogservice;

	@Resource(name = "userinfoService")
	private UserInfoService userinfoservice;
	
	@Resource(name = "testclientService")
	private TestClientService tcservice;

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

			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHCASESTEPS)) {
				model.addAttribute("casesteps", new ProjectCasesteps());
				model.addAttribute("url", "/projectCase/load.do");
				model.addAttribute("message", "当前用户无权限管理用例步骤，请联系管理员！");
				return "success";
			}

			String caseid = req.getParameter("caseid");
			ProjectCase prcase = null;
			if (!StrLib.isEmpty(caseid) && !PublicConst.STATUSSTR0.equals(caseid)) {
				prcase = projectcaseservice.load(Integer.valueOf(caseid));
			}
			
			if(!UserLoginController.oppidboolean(req, prcase.getProjectid())){
				SectorProjects sp=sectorprojectsService.loadob(prcase.getProjectid());
				model.addAttribute("url", "/projectCase/load.do");
				model.addAttribute("message", "当前用户无权限管理项目【"+sp.getProjectname()+"】用例步骤，请联系管理员！");
				return "error";
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
			model.addAttribute("casename", prcase.getName());
			model.addAttribute("casetype", prcase.getCasetype());
			model.addAttribute("caseid", caseid);
			model.addAttribute("projectid", prcase.getProjectid());
			model.addAttribute("steps", steps);
			List<TestClient> iplist = tcservice.getClientListForProid(prcase.getProjectid());
			for(TestClient tc:iplist){
				if(tc.getStatus()==0){
					tc.setProjectper("【"+tc.getName()+"】"+tc.getClientip()+" 状态正常");
				}else if(tc.getStatus()==1){
					tc.setProjectper("【"+tc.getName()+"】"+tc.getClientip()+" 状态异常");
				}else{
					tc.setProjectper("【"+tc.getName()+"】"+tc.getClientip()+" 状态未知");
				}
			}
			model.addAttribute("iplist", iplist);
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
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		StringBuilder sb = new StringBuilder();
		JSONObject json = new JSONObject();
		if (!UserLoginController.permissionboolean(request, PublicConst.AUTHCASESTEPS)) {
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

			List<ProjectCasesteps> list = JSON.parseArray(jsonstr, ProjectCasesteps.class);
			String usercode = "";
			if (null != request.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE)
					&& null != request.getSession().getAttribute(PublicConst.SESSIONKEYUSERNAME)) {
				usercode = request.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE).toString();
			}
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = formatter.format(currentTime);
			ProjectCase projectcase = new ProjectCase();
			for (int i = 0; i < list.size(); i++) {
				ProjectCasesteps step = list.get(i);
				if (i == 0) {
					casestepsservice.delforcaseid(step.getCaseid());
					projectcase = projectcaseservice.load(step.getCaseid());
				}
				step.setOperationer(usercode);
				step.setTime(time);
				step.setOperation(step.getOperation().replace("&quot;", "\""));
				step.setPath(step.getPath().replace("&quot;", "\""));
				step.setParameters(step.getParameters().replace("&quot;", "\""));
				step.setAction(step.getAction().replace("&quot;", "\""));
				step.setExpectedresult(step.getExpectedresult().replace("&quot;", "\""));
				step.setRemark(step.getRemark().replace("&quot;", "\""));
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
		JSONArray recordJson = StrLib.listToJson(casesteps);
		pw.print(recordJson);
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
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHCASESTEPS)) {
				json.put("status", "fail");
				json.put("ms", "编辑失败,权限不足,请联系管理员!");
			} else {
				ProjectCase projectcase = new ProjectCase();
				if (null != req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE)
						&& null != req.getSession().getAttribute(PublicConst.SESSIONKEYUSERNAME)) {
					String usercode = req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE).toString();
					casesteps.setOperationer(usercode);
					projectcase = projectcaseservice.load(casesteps.getCaseid());
					projectcase.setOperationer(usercode);
				}
				if(!UserLoginController.oppidboolean(req, projectcase.getProjectid())){
					json.put("status", "fail");
					json.put("ms", "编辑失败,项目权限不足,请联系管理员!");
				}else{
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

			}
			pw.print(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	@RequestMapping(value = "/debugcase.do")
	private void debugCase(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		JSONObject json = new JSONObject();
		String casesign = request.getParameter("casesign");
		String clientip = request.getParameter("clientip");
		String clientpath = request.getParameter("clientpath");
		String status="error";
		String ms="调试用例启动失败！";
		try {
			if (null != request.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE)
					&& null != request.getSession().getAttribute(PublicConst.SESSIONKEYUSERNAME)) {
				String usercode = request.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE).toString();
				
				tempdebugservice.delete(casesign, usercode);

				// 调用远程对象，注意RMI路径与接口必须与服务器配置一致
				RunService service = (RunService) Naming.lookup("rmi://" + clientip + ":6633/RunService");
				String result = service.webdebugcase(casesign, usercode,clientpath);
				
				status="success";
				ms=result;
			}else{
				status="fail";
				ms="检测到用户未登录，请重新登录！";
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			ms="远程链接异常，请检查客户端！";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			json.put("status", status);
			json.put("ms", ms);

			pw.print(json.toString());
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

	@RequestMapping(value = "/refreshlog.do")
	private void refreshDebugLog(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		JSONObject json = new JSONObject();
		String casesign = request.getParameter("casesign");
		String status="error";
		String ms="获取调试日志失败！";
		try {
			if (null != request.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE)
					&& null != request.getSession().getAttribute(PublicConst.SESSIONKEYUSERNAME)) {
				String usercode = request.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE).toString();

				List<TempCasestepDebug> tcdlist=tempdebugservice.getList(casesign, usercode);
				StringBuilder stringBuilder = new StringBuilder();
				for(TempCasestepDebug tcd:tcdlist){
					stringBuilder.append(tcd.getLoglevel()+": "+tcd.getDetail()+"</br>");
				}
				ms=stringBuilder.toString();
				if(null!=tcdlist&&tcdlist.size()>1){
					String lastloglevel=tcdlist.get(tcdlist.size()-1).getLoglevel();
					String over="over";
					if(lastloglevel.indexOf(over)>-1){
						status="info";
					}else{
						status="success";
					}
				}else{
					status="success";
				}

				
			}else{
				status="fail";
				ms="检测到用户未登录，请重新登录！";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			status="fail";
			ms="刷新日志出现异常！";
			e.printStackTrace();
		}
		json.put("status", status);
		json.put("ms", ms);

		pw.print(json.toString());
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
			JSONArray recordJson = StrLib.listToJson(steps);

			// 需要返回的数据有总记录数和行数据
			json.put("steps", recordJson);
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
			ProjectCasesteps steps = new ProjectCasesteps();

			String path = req.getParameter("path");
			if (null != path) {
				path = path.replace("DHDHDH", "=");
			}
			String operation = req.getParameter("operation");
			String parameters = req.getParameter("parameters");
			if (null != parameters) {
				parameters = parameters.replace("BBFFHH", "%");
			}
			String action = req.getParameter("action");
			String caseid = req.getParameter("caseid");
			String stepnum = req.getParameter("stepnum");
			String expectedresult = req.getParameter("expectedresult");
			if (null != expectedresult) {
				expectedresult = expectedresult.replace("BBFFHH", "%");
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
			expectedresults = expectedresults.replace("BBFFHH", "%");
			expectedresults = expectedresults.replace("DHDHDH", "=");
			expectedresults = expectedresults.replace("ANDAND", "&");

			String result = "更新用例【" + caseno + "】第【" + stepnum + "】步失败，预期结果【" + expectedresults + "】";
			ProjectCase pc = projectcaseservice.getCaseBySign(caseno);
			List<ProjectCasesteps> steps = casestepsservice.getSteps(pc.getId());

			for (ProjectCasesteps step : steps) {
				if (stepnum.equals(String.valueOf(step.getStepnum()))) {
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
					result = "更新用例【" + caseno + "】第【" + stepnum + "】步成功，预期结果【" + expectedresults + "】";
					break;
				}
			}
			pw.print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/cPostDebugLog.do")
	public void cPostDebugLog(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=GBK");
			req.setCharacterEncoding("GBK");
			PrintWriter pw = rsp.getWriter();
			String sign = req.getParameter("sign");
			String executor = req.getParameter("executor");
			String loglevel = req.getParameter("loglevel");
			String detail = req.getParameter("detail");
			sign = sign.replace("BBFFHH", "%");
			sign = sign.replace("DHDHDH", "=");
			sign = sign.replace("ANDAND", "&");
			detail = detail.replace("BBFFHH", "%");
			detail = detail.replace("DHDHDH", "=");
			detail = detail.replace("ANDAND", "&");

			TempCasestepDebug tcd = new TempCasestepDebug();
			tcd.setSign(sign);
			tcd.setExecutor(executor);
			tcd.setLoglevel(loglevel);
			tcd.setDetail(detail);
			int id = tempdebugservice.add(tcd);

			pw.print(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

	}

}
