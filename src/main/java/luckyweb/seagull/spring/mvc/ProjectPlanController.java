package luckyweb.seagull.spring.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.spring.entity.ProjectPlan;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.entity.UserInfo;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.ProjectPlanCaseService;
import luckyweb.seagull.spring.service.ProjectPlanService;
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
@RequestMapping("/projectPlan")
public class ProjectPlanController {

	@Resource(name = "projectPlanCaseService")
	private ProjectPlanCaseService projectplancaseservice;

	@Resource(name = "projectPlanService")
	private ProjectPlanService projectplanservice;

	@Resource(name = "sectorprojectsService")
	private SectorProjectsService sectorprojectsService;

	@Resource(name = "operationlogService")
	private OperationLogService operationlogservice;

	@Resource(name = "userinfoService")
	private UserInfoService userinfoservice;

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
			model.addAttribute("url", "/projectPlan/load.do");
			return "error";
		}
		return "/jsp/plancase/projectplan";
	}

	@RequestMapping(value = "/list.do")
	private void ajaxGetSellRecord(Integer limit, Integer offset, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		PrintWriter pw = response.getWriter();
		String search = request.getParameter("search");
		String projectid = request.getParameter("projectid");
		ProjectPlan projectplan = new ProjectPlan();
		if (null == offset && null == limit) {
			offset = 0;
		}
		if (null == limit || limit == 0) {
			limit = 10;
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(search)) {
			projectplan.setName(search);
			projectplan.setOperationer(search);
			projectplan.setRemark(search);
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(projectid)) {
			projectplan.setProjectid(Integer.valueOf(projectid));
		}

		List<ProjectPlan> projectplans = projectplanservice.findByPage(projectplan, offset, limit);
		List<SectorProjects> prolist = QueueListener.qa_projlist;
		for (int i = 0; i < projectplans.size(); i++) {
			ProjectPlan pp = projectplans.get(i);
			for (SectorProjects projectlist : prolist) {
				if (pp.getProjectid() == projectlist.getProjectid()) {
					pp.setProjectname(projectlist.getProjectname());
					projectplans.set(i, pp);
					break;
				}
			}

		}
		// 转换成json字符串
		JSONArray recordJson = StrLib.listToJson(projectplans);
		// 得到总记录数
		int total = projectplanservice.findRows(projectplan);
		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", recordJson);
		pw.print(json.toString());
	}

	@RequestMapping(value = "/update.do")
	public void updateplan(HttpServletRequest req, HttpServletResponse rsp, ProjectPlan projectplan) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHTESTPLANMOD)) {
				json.put("status", "fail");
				json.put("ms", "编辑计划失败,权限不足,请联系管理员!");
			} else {
				if(!UserLoginController.oppidboolean(req, projectplan.getProjectid())){
					json.put("status", "fail");
					json.put("ms", "编辑计划失败,项目权限不足,请联系管理员!");
				}else{
					if (null != req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE)
							&& null != req.getSession().getAttribute(PublicConst.SESSIONKEYUSERNAME)) {
						String usercode = req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE).toString();
						projectplan.setOperationer(usercode);
					}
					Date currentTime = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time = formatter.format(currentTime);
					projectplan.setTime(time);

					projectplanservice.modify(projectplan);

					operationlogservice.add(req, "PROJECT_PLAN", projectplan.getId(), projectplan.getProjectid(),1,
							"修改测试计划成功!");
					json.put("status", "success");
					json.put("ms", "编辑计划成功!");
				}

			}
			pw.print(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 添加计划
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
	@RequestMapping(value = "/planadd.do")
	public void add(ProjectPlan projectplan, HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHTESTPLANADD)) {
				json.put("status", "fail");
				json.put("ms", "增加测试计划失败,权限不足,请联系管理员!");
			} else {
				if(!UserLoginController.oppidboolean(req, projectplan.getProjectid())){
					json.put("status", "fail");
					json.put("ms", "增加测试计划失败,此项目权限不足,请联系管理员!");
				}else{
					if (null != req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE)
							&& null != req.getSession().getAttribute(PublicConst.SESSIONKEYUSERNAME)) {
						String usercode = req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE).toString();
						projectplan.setOperationer(usercode);
					}
					// 定义空格回车换行符
					String regExSpace = "\t|\r|\n";
					Pattern pSpace = Pattern.compile(regExSpace, Pattern.CASE_INSENSITIVE);
					Matcher mSpace = pSpace.matcher(projectplan.getRemark());
					// 过滤空格回车标签
					projectplan.setRemark(mSpace.replaceAll("")); 

					Date currentTime = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time = formatter.format(currentTime);
					projectplan.setTime(time);

					int planid = projectplanservice.add(projectplan);

					operationlogservice.add(req, "PROJECT_PLAN", planid, projectplan.getProjectid(),3, "添加测试计划成功!");

					json.put("status", "success");
					json.put("ms", "添加测试计划成功!");
				}

			}
			pw.print(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 删除计划
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
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHTESTPLANDEL)) {
				json.put("status", "fail");
				json.put("ms", "删除计划失败,权限不足,请联系管理员!");
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
				JSONArray jsonarr = JSONArray.parseArray(jsonObject.getString("planids"));

				String status="fail";
				String ms="删除计划失败!";
				int suc=0;
				int fail=0;
				for (int i = 0; i < jsonarr.size(); i++) {
					int id = Integer.valueOf(jsonarr.get(i).toString());
					ProjectPlan projectplan = projectplanservice.load(id);
					
					if(!UserLoginController.oppidboolean(req, projectplan.getProjectid())){
						fail++;
						continue;
					}	
					projectplancaseservice.delforplanid(id);
					projectplanservice.delete(projectplan);
					operationlogservice.add(req, "PROJECT_PLAN", id, projectplan.getProjectid(),0, "删除测试计划成功!");
					suc++;
				}
				
				if(suc>0){
					status="success";
					ms="删除计划成功!";
					if(fail>0){
						status="success";
						ms="删除计划"+suc+"条成功！"+fail+"条因为无项目权限删除失败！";
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

	/**
	 * 联动查询测试计划
	 * @param tj
	 * @param br
	 * @param model
	 * @param req
	 * @param rsp
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/getplanlist.do")
	public void getplanlist(HttpServletRequest req, HttpServletResponse rsp) throws Exception{	    
		int	projectid = Integer.valueOf(req.getParameter("projectid"));

		ProjectPlan projectplan=new ProjectPlan();
		projectplan.setProjectid(projectid);
		List<ProjectPlan> listplan = projectplanservice.findByPage(projectplan, 0, 1000);
		
		// 取集合
	    rsp.setContentType("text/xml;charset=utf-8");

		JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(listplan));
		JSONObject jsobjcet = new JSONObject();
		jsobjcet.put("data", jsonArray); 
		
		rsp.getWriter().write(jsobjcet.toString());
	}
	
	public static void main(String[] args) throws Exception {

	}

}
