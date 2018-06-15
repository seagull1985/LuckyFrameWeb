package luckyweb.seagull.spring.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.spring.entity.ProjectCase;
import luckyweb.seagull.spring.entity.ProjectCasesteps;
import luckyweb.seagull.spring.entity.ProjectModule;
import luckyweb.seagull.spring.entity.ProjectModuleJson;
import luckyweb.seagull.spring.entity.ProjectPlanCase;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.entity.UserInfo;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.ProjectCaseService;
import luckyweb.seagull.spring.service.ProjectCasestepsService;
import luckyweb.seagull.spring.service.ProjectModuleService;
import luckyweb.seagull.spring.service.ProjectPlanCaseService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.spring.service.UserInfoService;
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
@RequestMapping("/projectCase")
public class ProjectCaseController {

	@Resource(name = "projectCaseService")
	private ProjectCaseService projectcaseservice;

	@Resource(name = "projectPlanCaseService")
	private ProjectPlanCaseService projectplancaseservice;
	
	@Resource(name = "projectCasestepsService")
	private ProjectCasestepsService casestepsservice;

	@Resource(name = "projectModuleService")
	private ProjectModuleService moduleservice;

	@Resource(name = "sectorprojectsService")
	private SectorProjectsService sectorprojectsService;

	@Resource(name = "operationlogService")
	private OperationLogService operationlogservice;

	@Resource(name = "userinfoService")
	private UserInfoService userinfoservice;

	private List<Integer> listmoduleid=new ArrayList<Integer>();
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
			int projectid = 99;
			if (null != req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE)
					&& null != req.getSession().getAttribute(PublicConst.SESSIONKEYUSERNAME)) {
				String usercode = req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE).toString();
				UserInfo userinfo = userinfoservice.getUseinfo(usercode);
				projectid = userinfo.getProjectid();
			}

			List<SectorProjects> prolist = QueueListener.qa_projlist;
			model.addAttribute("projects", prolist);
			model.addAttribute("projectid", projectid);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/projectCase/load.do");
			return "error";
		}
		return "/jsp/plancase/projectcase";
	}

	@RequestMapping(value = "/list.do")
	private void ajaxGetSellRecord(Integer limit, Integer offset, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		PrintWriter pw = response.getWriter();
		String search = request.getParameter("search");
		String projectid = request.getParameter("projectid");
		String moduleid = request.getParameter("moduleid");
		ProjectCase projectcase = new ProjectCase();
		if (null == offset && null == limit) {
			offset = 0;
		}
		if (null == limit || limit == 0) {
			limit = 10;
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(search)) {
			projectcase.setSign(search);
			projectcase.setName(search);
			projectcase.setOperationer(search);
			projectcase.setRemark(search);
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(projectid) && !PublicConst.STATUSSTR99.equals(projectid)) {
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
		List<ProjectCase> projectcases = projectcaseservice.findByPage(projectcase, offset, limit);
		List<SectorProjects> prolist = QueueListener.qa_projlist;
		List<ProjectModule> modulelist = moduleservice.getModuleList();
		for (int i = 0; i < projectcases.size(); i++) {
			ProjectCase pcase = projectcases.get(i);
			// 更新项目名
			for (SectorProjects projectlist : prolist) {
				if (pcase.getProjectid() == projectlist.getProjectid()) {
					projectcases.get(i).setProjectname(projectlist.getProjectname());
				}
			}
			// 更新模块名
			for (ProjectModule module : modulelist) {
				if (pcase.getModuleid() == module.getId()) {
					projectcases.get(i).setModulename(module.getModulename());
				}
			}

		}
		// 转换成json字符串
		JSONArray recordJson = StrLib.listToJson(projectcases);
		// 得到总记录数
		int total = projectcaseservice.findRows(projectcase);
		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", recordJson);
		pw.print(json.toString());
	}

	@RequestMapping(value = "/update.do")
	public void updatecase(HttpServletRequest req, HttpServletResponse rsp, ProjectCase projectcase) {
		// 更新实体
		JSONObject json = new JSONObject();
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();

			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHCASEMOD)) {
				json.put("status", "fail");
				json.put("ms", "编辑失败,权限不足,请联系管理员!");
			} else {
				if(!UserLoginController.oppidboolean(req, projectcase.getProjectid())){
					json.put("status", "fail");
					json.put("ms", "编辑失败,项目权限不足,请联系管理员!");
				}else{
					if (null != req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE)
							&& null != req.getSession().getAttribute(PublicConst.SESSIONKEYUSERNAME)) {
						String usercode = req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE).toString();
						projectcase.setOperationer(usercode);
					}
					Date currentTime = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time = formatter.format(currentTime);
					projectcase.setTime(time);

					projectcaseservice.modify(projectcase);
					operationlogservice.add(req, "PROJECT_CASE", projectcase.getId(), projectcase.getProjectid(),1,
							"编辑用例成功！用例编号：" + projectcase.getSign());
					json.put("status", "success");
					json.put("ms", "编辑成功!");
				}

			}
			pw.print(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
	@RequestMapping(value = "/caseadd.do")
	public void add(ProjectCase projectcase, HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHCASEADD)) {
				json.put("status", "fail");
				json.put("ms", "添加失败,权限不足,请联系管理员!");
			} else {
				int copyid = Integer.valueOf(req.getParameter("copyid"));
				if(!UserLoginController.oppidboolean(req, projectcase.getProjectid())){
					if(0!=copyid){
						json.put("status", "fail");
						json.put("ms", "复制用例失败,项目权限不足,请联系管理员!");
					}else{
						json.put("status", "fail");
						json.put("ms", "添加用例失败,项目权限不足,请联系管理员!");
					}
				}else{
					if (null != req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE)
							&& null != req.getSession().getAttribute(PublicConst.SESSIONKEYUSERNAME)) {
						String usercode = req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE).toString();
						projectcase.setOperationer(usercode);
					}
					// 定义空格回车换行符
					String regExSpace = "\t|\r|\n";
					Pattern pSpace = Pattern.compile(regExSpace, Pattern.CASE_INSENSITIVE);
					Matcher mSpace = pSpace.matcher(projectcase.getRemark());
					 // 过滤空格回车标签
					projectcase.setRemark(mSpace.replaceAll(""));

					Date currentTime = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time = formatter.format(currentTime);
					projectcase.setTime(time);

					SectorProjects sp = sectorprojectsService.loadob(projectcase.getProjectid());
					String maxindex = projectcaseservice.getCaseMaxIndex(projectcase.getProjectid());
					int index = Integer.valueOf(maxindex) + 1;
					projectcase.setSign(sp.getProjectsign() + "-" + index);
					projectcase.setProjectindex(index);
					int caseid = projectcaseservice.add(projectcase);
					operationlogservice.add(req, "PROJECT_CASE", caseid, projectcase.getProjectid(),3,
							"添加用例成功！用例编号：" + projectcase.getSign());
					
					String ms="添加用例【"+projectcase.getSign()+"】成功！";
					if(0!=copyid){
						List<ProjectCasesteps> steps=casestepsservice.getSteps(copyid);
						for(ProjectCasesteps step:steps){
							step.setCaseid(caseid);
							int stepid=casestepsservice.add(step);
						    operationlogservice.add(req, "PROJECT_CASESTEPS", stepid, projectcase.getProjectid(),1,
									"复制用例步骤成功！");
						}
						ms="复制用例【"+projectcase.getSign()+"】成功！";
					}

					json.put("status", "success");
					json.put("ms", ms);
				}

			}
			pw.print(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 删除用例
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
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHCASEDEL)) {
				json.put("status", "fail");
				json.put("ms", "删除失败,权限不足,请联系管理员!");
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

				String status="fail";
				String ms="删除用例失败!";
				int suc=0;
				int planfail=0;
				int proprefail=0;
				ProjectPlanCase projectplancase=new ProjectPlanCase();
				for (int i = 0; i < jsonarr.size(); i++) {
					int id = Integer.valueOf(jsonarr.get(i).toString());
					projectplancase.setCaseid(id);
					int plancase=projectplancaseservice.findRows(projectplancase);
					ProjectCase pc = projectcaseservice.load(id);

					if(plancase>0){
						planfail++;
						continue;
					}
					if(!UserLoginController.oppidboolean(req, pc.getProjectid())){
						proprefail++;
						ms="因为没有项目删除权限导致用例删除失败！";
						continue;
					}
					casestepsservice.delforcaseid(id);
					projectcaseservice.delete(id);
					operationlogservice.add(req, "PROJECT_CASE", pc.getId(), pc.getProjectid(), 0,"删除用例成功！");
					suc++;
				}
				
				if(suc>0){
					status="success";
					ms="删除用例成功!";
					if(proprefail>0&&planfail==0){
						status="warning";
						ms="删除用例"+suc+"条成功！"+proprefail+"条因为无项目权限删除失败！";
					}else if(proprefail==0&&planfail>0){
						status="warning";
						ms="删除用例"+suc+"条成功！"+planfail+"条因为在测试计划中无法删除！";
					}else if(proprefail>0&&planfail>0){
						status="warning";
						ms="删除用例"+suc+"条成功！"+planfail+"条因为在测试计划中无法删除！"+proprefail+"条因为无项目权限删除失败！";
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

	@RequestMapping(value = "/cgetcasebysign.do")
	public void cgetcasebysign(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=GBK");
			req.setCharacterEncoding("GBK");
			PrintWriter pw = rsp.getWriter();
			String sign = req.getParameter("sign");

			ProjectCase pc = projectcaseservice.getCaseBySign(sign);
			String jsonStr = JSONObject.toJSONString(pc);
			pw.print(jsonStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 添加用例集
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
	@RequestMapping(value = "/moduleadd.do")
	public void addModule(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		rsp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		PrintWriter pw = rsp.getWriter();
		JSONObject json = new JSONObject();
		int id=0;
		try {
			ProjectModule projectmodule = new ProjectModule();
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHCASEADD)) {
				json.put("status", "fail");
				json.put("ms", "添加失败,权限不足,请联系管理员!");
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
				String ms="保存用例集失败!";
				String status="fail";
				int projectid = Integer.valueOf(jsonObject.getString("projectid"));
				int pid = Integer.valueOf(jsonObject.getString("pid"));
				String oldName = jsonObject.getString("oldName");
				String name = jsonObject.getString("name");
				id = Integer.valueOf(jsonObject.getString("id"));
				
				if(!UserLoginController.oppidboolean(req, projectid)){
					status="fail";
					ms="项目权限不足,请联系管理员!";
				}else{
					if(id!=0){
						ProjectModule pm=moduleservice.load(id);
						if(null!=pm&&pm.getProjectid()==projectid&&pm.getPid()==pid&&pm.getModulename().equals(oldName)){
							pm.setModulename(name);
							moduleservice.modify(pm);
							operationlogservice.add(req, "PROJECT_MODULE", id, pm.getProjectid(),1, "编辑用例集成功！");
							ms="编辑用例集成功!";
							status="success";
						}
					}else{					
						projectmodule.setProjectid(projectid);
						projectmodule.setPid(pid);
						projectmodule.setModulename(name);
						id = moduleservice.add(projectmodule);
						operationlogservice.add(req, "PROJECT_MODULE", id, projectmodule.getProjectid(),1, "新增用例集成功！");
						ms="新增用例集成功!";
						status="success";
					}
				}

				json.put("status", status);
				json.put("ms", ms);
				json.put("id", id);
			}
			pw.print(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
			json.put("status", "保存用例集出现异常!");
			json.put("ms", "fail");
			pw.print(json.toString());
		}

	}

	/**
	 * 删除用例集
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
	@RequestMapping(value = "/moduledel.do")
	public void delModule(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHCASEDEL)) {
				json.put("status", "fail");
				json.put("ms", "删除失败,权限不足,请联系管理员!");
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

				int id = Integer.valueOf(jsonObject.getString("id"));
				ProjectModule projectmodule = moduleservice.load(id);
				
				if(!UserLoginController.oppidboolean(req, projectmodule.getProjectid())){
					json.put("status", "fail");
					json.put("ms", "删除失败,项目权限不足,请联系管理员!");
				}else{
					ProjectCase projectcase = new ProjectCase();
					projectcase.setModuleid(id);
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
					int casecount = projectcaseservice.findRows(projectcase);

					if (casecount == 0) {
						moduleservice.delete(projectmodule);
						operationlogservice.add(req, "PROJECT_MODULE", id, projectmodule.getProjectid(),0, "删除用例集成功！");

						json.put("status", "success");
						json.put("ms", "删除用例集成功！");
					} else {
						json.put("status", "fail");
						json.put("ms", "此用例集中包括了" + casecount + "条用例，不能删除!");
					}
				}

			}
			pw.print(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 查询测试集
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
	@RequestMapping(value = "/getmodulelist.do")
	public void getmodulelist(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		rsp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		int id = 0;
		int projectid = Integer.valueOf(req.getParameter("projectid"));
		String idstr = req.getParameter("id");
		JSONArray jsonarr = new JSONArray();
		if (!StrLib.isEmpty(idstr)) {
			id = Integer.valueOf(idstr);

			List<ProjectModule> modules = moduleservice.getModuleListByProjectid(projectid, id);
			for (ProjectModule projectmodule : modules) {
				ProjectModuleJson modulejson = new ProjectModuleJson();
				modulejson.setId(projectmodule.getId());
				modulejson.setName(projectmodule.getModulename());
				boolean isParent = moduleservice.getModuleIsParent(projectmodule.getId());
				modulejson.setisParent(isParent);
				jsonarr.add(JSONObject.toJSON(modulejson));
			}
		} else {
			List<SectorProjects> prolist = QueueListener.qa_projlist;
			for (SectorProjects sp : prolist) {
				if (sp.getProjectid() == projectid) {
					ProjectModuleJson modulejson = new ProjectModuleJson();
					modulejson.setId(0);
					modulejson.setName(sp.getProjectname());
					List<ProjectModule> modules = moduleservice.getModuleListByProjectid(projectid, 0);
					if (modules.size() > 0) {
						modulejson.setisParent(true);
					} else {
						modulejson.setisParent(false);
					}

					jsonarr.add(JSONObject.toJSON(modulejson));
				}
			}
		}

		String jsonstr = jsonarr.toString().replace("parent", "isParent");
		rsp.getWriter().write(jsonstr);
	}

	/**
	 * 查询项目中的所有测试集
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
	@RequestMapping(value = "/getmodulealllist.do")
	public void getmodulealllist(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		rsp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		int projectid = Integer.valueOf(req.getParameter("projectid"));
		JSONArray jsonarr = new JSONArray();
		List<ProjectModule> modules = moduleservice.getModuleAllListByProjectid(projectid);
		for (ProjectModule projectmodule : modules) {
			JSONObject json = new JSONObject();
			json.put("moduleid", projectmodule.getId());
			json.put("name", projectmodule.getModulename());
			jsonarr.add(json);
		}

		String jsonstr = jsonarr.toString();
		rsp.getWriter().write(jsonstr);
	}

	@RequestMapping(value = "/cpostcase.do")
	public void cpostcase(HttpServletRequest req, HttpServletResponse rsp) throws IOException {
		// 更新实体
		rsp.setContentType("text/html;charset=GBK");
		req.setCharacterEncoding("GBK");
		PrintWriter pw = rsp.getWriter();
		try {
			ProjectCase projectcase = new ProjectCase();
			int moduleid=0;

			String name = req.getParameter("name").replace("BBFFHH", "%");
			String projectid = req.getParameter("projectid");
			String modulename = req.getParameter("modulename");
			String casetype = req.getParameter("casetype");
			String remark = req.getParameter("remark");
			
			moduleid=moduleservice.getModuleIdByName(modulename);
			
			projectcase.setName(name);
			projectcase.setProjectid(Integer.valueOf(projectid));
			projectcase.setModuleid(moduleid);
			projectcase.setCasetype(Integer.valueOf(casetype));
			projectcase.setRemark(remark);

			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = formatter.format(currentTime);
			projectcase.setTime(time);
			projectcase.setOperationer("http-post");

			SectorProjects sp = sectorprojectsService.loadob(projectcase.getProjectid());
			String maxindex = projectcaseservice.getCaseMaxIndex(projectcase.getProjectid());
			int index = Integer.valueOf(maxindex) + 1;
			projectcase.setSign(sp.getProjectsign() + "-" + index);
			projectcase.setProjectindex(index);
			int caseid = projectcaseservice.add(projectcase);

			pw.print(caseid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pw.print("fail");
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
	
	public static void main(String[] args) throws Exception {

	}

}
