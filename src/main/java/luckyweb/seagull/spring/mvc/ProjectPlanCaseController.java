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

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import luckyweb.seagull.spring.entity.ProjectCase;
import luckyweb.seagull.spring.entity.ProjectModule;
import luckyweb.seagull.spring.entity.ProjectPlan;
import luckyweb.seagull.spring.entity.ProjectPlanCase;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.ProjectCaseService;
import luckyweb.seagull.spring.service.ProjectModuleService;
import luckyweb.seagull.spring.service.ProjectPlanCaseService;
import luckyweb.seagull.spring.service.ProjectPlanService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.spring.service.UserInfoService;
import luckyweb.seagull.util.StrLib;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/projectPlanCase")
public class ProjectPlanCaseController {

	@Resource(name = "projectPlanCaseService")
	private ProjectPlanCaseService projectplancaseservice;

	@Resource(name = "projectPlanService")
	private ProjectPlanService projectplanservice;

	@Resource(name = "projectModuleService")
	private ProjectModuleService moduleservice;
	
	@Resource(name = "projectCaseService")
	private ProjectCaseService projectcaseservice;

	@Resource(name = "sectorprojectsService")
	private SectorProjectsService sectorprojectsService;

	@Resource(name = "operationlogService")
	private OperationLogService operationlogservice;

	@Resource(name = "userinfoService")
	private UserInfoService userinfoservice;
	
	private List<ProjectCase> viewToSaveCase;
	
	private List<Integer> listmoduleid=new ArrayList<Integer>();

	/**
	 * 
	 * 获取计划用例列表
	 * 
	 * @param tj
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/casesload.do")
	public String casesload(HttpServletRequest req, Model model) throws Exception {

		try {
			if (!UserLoginController.permissionboolean(req, "proplan_3")) {
				model.addAttribute("url", "/projectPlan/load.do");
				model.addAttribute("message", "当前用户无权限管理计划用例，请联系管理员！");
				return "success";
			}

			String planid = req.getParameter("planid");
			ProjectPlan propaln = projectplanservice.load(Integer.valueOf(planid));
			
			if(!UserLoginController.oppidboolean(req, propaln.getProjectid())){
				SectorProjects sp=sectorprojectsService.loadob(propaln.getProjectid());
				model.addAttribute("url", "/projectPlan/load.do");
				model.addAttribute("message", "当前用户无权限管理项目【"+sp.getProjectname()+"】的计划用例，请联系管理员！");
				return "error";
			}
			model.addAttribute("projectid", propaln.getProjectid());
			model.addAttribute("planid", planid);
			model.addAttribute("planname", propaln.getName());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/projectPlan/load.do");
			return "error";
		}
		return "/jsp/plancase/projectplancase";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCaseList.do")
	private void getCaseList(Integer limit, Integer offset, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		String search = request.getParameter("search");
		String projectid = request.getParameter("projectid");
		String planidstr = request.getParameter("planid");
		String moduleid = request.getParameter("moduleid");
		String onlypcase = request.getParameter("onlypcase");
		
		List<ProjectModule> modulelist = moduleservice.getModuleList();
		List<ProjectCase> projectcases=new ArrayList<ProjectCase>();
		List<ProjectPlanCase> plancases=new ArrayList<ProjectPlanCase>();
		int total=0;
		if (null == offset && null == limit) {
			offset = 0;
		}
		if (null == limit || limit == 0) {
			limit = 10;
		}
		// 得到客户端传递的查询参数
		int planid = 0;
		if (!StrLib.isEmpty(planidstr)) {
			planid = Integer.valueOf(planidstr);
		}
		ProjectCase projectcase = new ProjectCase();	
		if ("0".equals(onlypcase)) {		
			// 得到客户端传递的查询参数
			if (!StrLib.isEmpty(search)) {
				projectcase.setSign(search);
				projectcase.setName(search);
				projectcase.setOperationer(search);
				projectcase.setRemark(search);
			}
			// 得到客户端传递的查询参数
			if (!StrLib.isEmpty(projectid) && !"99".equals(projectid)) {
				projectcase.setProjectid(Integer.valueOf(projectid));
			}

			// 得到客户端传递的查询参数
			if (!StrLib.isEmpty(moduleid)) {
				projectcase.setModuleid(Integer.valueOf(moduleid));
				if(0!=projectcase.getModuleid()){
					listmoduleid.clear();
					getchildmoduList(projectcase.getProjectid(),projectcase.getModuleid());
					Integer[] moduleidarr=new Integer[listmoduleid.size()+1];
					moduleidarr[0]=projectcase.getModuleid();
					for(int i=0;i<listmoduleid.size();i++){
						moduleidarr[i+1]=listmoduleid.get(i);
					}
					projectcase.setModuleidarr(moduleidarr);
					listmoduleid.clear();
				}
			}
			
			projectcases = projectcaseservice.findByPage(projectcase, offset, limit);
			plancases = projectplancaseservice.getcases(planid);
			for (int i = 0; i < projectcases.size(); i++) {
				ProjectCase pc = projectcases.get(i);
				for (int j = 0; j < plancases.size(); j++) {
					ProjectPlanCase ppc = plancases.get(j);
					if (pc.getId() == ppc.getCaseid()) {
						projectcases.get(i).setChecktype(1);
						projectcases.get(i).setPriority(plancases.get(j).getPriority());
					}
				}
				// 更新模块名
				for (ProjectModule module : modulelist) {
					if (pc.getModuleid() == module.getId()) {
						projectcases.get(i).setModulename(module.getModulename());
					}
				}
			}
			// 得到总记录数
			total = projectcaseservice.findRows(projectcase);
		} else {
			ProjectPlanCase plancase = new ProjectPlanCase();
			plancase.setPlanid(planid);
			plancases = projectplancaseservice.findByPage(plancase, offset, limit);
			for(ProjectPlanCase pc: plancases){
				projectcase=projectcaseservice.load(pc.getCaseid());
				projectcase.setChecktype(1);
				projectcase.setPriority(pc.getPriority());
				// 更新模块名
				for (ProjectModule module : modulelist) {
					if (projectcase.getModuleid() == module.getId()) {
						projectcase.setModulename(module.getModulename());
					}
				}
				projectcases.add(projectcase);
			}
			
			// 得到总记录数
			total = projectplancaseservice.getcases(planid).size();
		}

		
		viewToSaveCase = projectcases;
		// 转换成json字符串
		String RecordJson = StrLib.listToJson(projectcases);

		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", RecordJson);
		pw.print(json.toString());
	}

	/**
	 * 保存计划用例
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
	@RequestMapping(value = "/savePlanCase.do")
	public void savePlanCase(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			String planid = req.getParameter("planid");
			if (!UserLoginController.permissionboolean(req, "proplan_3")) {
				json.put("status", "fail");
				json.put("ms", "编辑计划用例失败,权限不足,请联系管理员!");
			} else {
				ProjectPlan pc = projectplanservice.load(Integer.valueOf(planid));
				List<ProjectPlanCase> plancases = projectplancaseservice.getcases(Integer.valueOf(planid));
				ProjectPlanCase projectplancase = new ProjectPlanCase();
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
				List<ProjectCase> caselist=viewToSaveCase;
				for (int i = 0; i < jsonarr.size(); i++) { // 添加列表中多的用例
					int caseid = Integer.valueOf(jsonarr.get(i).toString());
					for (ProjectCase  viewcase:caselist) {
						if (viewcase.getId() == caseid) {
							if(viewcase.getChecktype()==1){
								caselist.remove(viewcase);
								break;
							}else{
								projectplancase.setCaseid(caseid);
								projectplancase.setPlanid(Integer.valueOf(planid));
								projectplancase.setPriority(0);

								projectplancaseservice.add(projectplancase);
								caselist.remove(viewcase);
								break;
							}
						}
					}
				}

				for (int k = 0; k < caselist.size(); k++) { // 删除原有列表中多的用例
					for(ProjectPlanCase ppc:plancases){
						if(ppc.getCaseid()==caselist.get(k).getId()){
							projectplancaseservice.delete(ppc);
						}
					}
				}

				if (null != req.getSession().getAttribute("usercode")
						&& null != req.getSession().getAttribute("username")) {
					String usercode = req.getSession().getAttribute("usercode").toString();
					pc.setOperationer(usercode);
				}
				Date currentTime = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time = formatter.format(currentTime);
				pc.setTime(time);
				int count = projectplancaseservice.getcases(Integer.valueOf(planid)).size();
				pc.setCasecount(count);
				projectplanservice.modify(pc);

				operationlogservice.add(req, "PROJECT_PLAN", Integer.valueOf(planid), pc.getProjectid(), "保存计划用例成功!");
				json.put("status", "success");
				json.put("ms", "保存计划用例成功!");
			}
			pw.print(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/update.do")
	public void updatecase(HttpServletRequest req, HttpServletResponse rsp, ProjectCase projectcase) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			if (!UserLoginController.permissionboolean(req, "proplan_3")) {
				json.put("status", "fail");
				json.put("ms", "编辑计划用例失败,权限不足,请联系管理员!");
			} else {
				String planid = req.getParameter("planid");
				String result = "编辑成功！";
				ProjectPlanCase projectplancase = new ProjectPlanCase();
				projectplancase.setCaseid(projectcase.getId());
				projectplancase.setPlanid(Integer.valueOf(planid));
				projectplancase.setPriority(projectcase.getPriority());
				int id = projectplancaseservice.findRows(projectplancase);
				if (id == 0) {
					result = "编辑成功！并已帮您把此用例加入到计划中！";
					projectplancaseservice.add(projectplancase);
				} else {
					projectplancase.setId(id);
					projectplancaseservice.modify(projectplancase);
				}

				json.put("status", "success");
				json.put("ms", result);
			}
			pw.print(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/cgetcasebyplanid.do")
	public void cgetCaseByPlanid(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=GBK");
			req.setCharacterEncoding("GBK");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			String planid = req.getParameter("planid");
			
			List<ProjectPlanCase> plancases = projectplancaseservice.getcases(Integer.valueOf(planid));
			List<ProjectCase> projectcases=new ArrayList<ProjectCase>();

			for(int i=0;i<plancases.size();i++){
				ProjectCase projectcase=projectcaseservice.load(plancases.get(i).getCaseid());
				Integer[] moduleidarr={projectcase.getModuleid()};
				projectcase.setModuleidarr(moduleidarr);
				projectcases.add(i, projectcase);
			}

			// 转换成json字符串
			String RecordJson = StrLib.listToJson(projectcases);

			// 需要返回的数据有总记录数和行数据
			json.put("cases", RecordJson);
			pw.print(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/cgetcasebyplanname.do")
	public void cgetCaseByPlanname(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=GBK");
			req.setCharacterEncoding("GBK");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			String name = req.getParameter("name");
			ProjectPlan pp=projectplanservice.getcases(name);
			List<ProjectPlanCase> plancases = projectplancaseservice.getcases(pp.getId());
			List<ProjectCase> projectcases=new ArrayList<ProjectCase>();

			for(int i=0;i<plancases.size();i++){
				ProjectCase projectcase=projectcaseservice.load(plancases.get(i).getCaseid());
				Integer[] moduleidarr={projectcase.getModuleid()};
				projectcase.setModuleidarr(moduleidarr);
				projectcases.add(i, projectcase);
			}

			// 转换成json字符串
			String RecordJson = StrLib.listToJson(projectcases);

			// 需要返回的数据有总记录数和行数据
			json.put("cases", RecordJson);
			pw.print(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * 使用递归查询指定测试ID中的所有子测试集
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
    private List<Integer> getchildmoduList(int projectid,int pid){
    	List<ProjectModule> modules = moduleservice.getModuleListByProjectid(projectid, pid);
        for(ProjectModule pm: modules){
        	listmoduleid.add(pm.getId());
            //递归遍历下一级  
        	getchildmoduList(projectid,pm.getId());
        }  
     return listmoduleid;  
    } 
}
