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

import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.spring.entity.ProjectCase;
import luckyweb.seagull.spring.entity.ProjectCasesteps;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.entity.UserInfo;
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
	 * 添加用例
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
			PrintWriter pw = rsp.getWriter();
			/*
			 * if(!UserLoginController.permissionboolean(req, "case_1")){
			 * model.addAttribute("casesteps", new ProjectCasesteps());
			 * model.addAttribute("url", "/projectCase/list.do");
			 * model.addAttribute("message", "当前用户无权限添加用例，请联系管理员！"); return
			 * "success"; }
			 */

			String caseid = req.getParameter("caseid");
			ProjectCase prcase = null;
			if (!StrLib.isEmpty(caseid) && !"0".equals(caseid)) {
				prcase = projectcaseservice.load(Integer.valueOf(caseid));
			}
			String retVal = "/jsp/plancase/step_add";
			List<ProjectCasesteps> steps = casestepsservice.getSteps(Integer.valueOf(caseid));
			if(steps.size()==0){
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
		PrintWriter pw = response.getWriter();
		StringBuilder sb = new StringBuilder();
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
		String usercode="";
		if (null != request.getSession().getAttribute("usercode")
				&& null != request.getSession().getAttribute("username")) {
			usercode = request.getSession().getAttribute("usercode").toString();
		}
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = formatter.format(currentTime);

		
		for (int i = 0; i < list.size(); i++) {
			ProjectCasesteps step = (ProjectCasesteps) list.get(i);
			if(i == 0){
				casestepsservice.delforcaseid(step.getCaseid());
			}
			step.setOperationer(usercode);
			step.setTime(time);
			
			int stepsid = casestepsservice.add(step);
			/*operationlogservice.add(req, "PROJECT_CASESTEPS", stepsid, step.getProjectid(), "添加用例"+prcase.getSign()+"步骤"+step.getStepnum()+"成功！");*/

		}

		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("status", "success");
		json.put("data", "dddSSSSSSS");
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
	public void updatecase(HttpServletRequest req, HttpServletResponse rsp, ProjectCasesteps casesteps) {
		// 更新实体
		try {
			PrintWriter pw = rsp.getWriter();
			if (null != req.getSession().getAttribute("usercode")
					&& null != req.getSession().getAttribute("username")) {
				String usercode = req.getSession().getAttribute("usercode").toString();
				casesteps.setOperationer(usercode);
			}
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = formatter.format(currentTime);
			casesteps.setTime(time);

			casestepsservice.modify(casesteps);
			String RecordJson = StrLib.objectToJson(casesteps);
			// 需要返回的数据有总记录数和行数据
			JSONObject json = new JSONObject();
			json.put("status", "success");
			json.put("data", RecordJson);
			pw.print(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		String aaa = "\"[{\"setpnum\":1,\"path\":\"aaa\",\"operation\":\"ss\",\"parameters\":\"ss\",\"action\":\"ss\",\"expectedresult\":\"sfda\",\"steptype\":0,\"remark\":\"ddd\"},{\"setpnum\":2,\"path\":\"ffff\",\"operation\":\"fdsa\",\"parameters\":\"csa\",\"action\":\"\",\"expectedresult\":\"\",\"steptype\":1,\"remark\":\"csa\"},{\"setpnum\":3,\"path\":\"fdsa\",\"operation\":\"fss\",\"parameters\":\"akuy\",\"action\":\"a\",\"expectedresult\":\"\",\"steptype\":0,\"remark\":\"fdsa\"},{\"setpnum\":4,\"path\":\"fdsa\",\"operation\":\"dss\",\"parameters\":\"cdxddd\",\"action\":\"sa\",\"expectedresult\":\"\",\"steptype\":0,\"remark\":\"czxc\"}]\"";
		String jsonstr = aaa.substring(1, aaa.length() - 1);
		System.out.println(jsonstr);
		/* JSONObject jsonObject = JSONObject.fromObject(aaa); */

	}

}
