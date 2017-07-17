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

import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.spring.entity.ProjectCase;
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

@Controller
@RequestMapping("/projectCase")
public class ProjectCaseController {

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
			if (null != req.getSession().getAttribute("usercode")
					&& null != req.getSession().getAttribute("username")) {
				String usercode = req.getSession().getAttribute("usercode").toString();
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
		PrintWriter pw = response.getWriter();
		String search = request.getParameter("search");
		String projectid = request.getParameter("projectid");
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
		if (!StrLib.isEmpty(projectid) && !"99".equals(projectid)) {
			projectcase.setProjectid(Integer.valueOf(projectid));
		}

		List<ProjectCase> projectcases = projectcaseservice.findByPage(projectcase, offset, limit);
		List<SectorProjects> prolist = QueueListener.qa_projlist;
		for (int i = 0; i < projectcases.size(); i++) {
			ProjectCase pcase = projectcases.get(i);
			for (SectorProjects projectlist : prolist) {
				if (pcase.getProjectid() == projectlist.getProjectid()) {
					pcase.setProjectname(projectlist.getProjectname());
					projectcases.set(i, pcase);
				}
			}

		}
		// 转换成json字符串
		String RecordJson = StrLib.listToJson(projectcases);
		// 得到总记录数
		int total = projectcaseservice.findRows(projectcase);
		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", RecordJson);
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

			if (!UserLoginController.permissionboolean(req, "case_3")) {
				json.put("status", "fail");
				json.put("ms", "编辑失败,权限不足,请联系管理员!");
			} else {
				if (null != req.getSession().getAttribute("usercode")
						&& null != req.getSession().getAttribute("username")) {
					String usercode = req.getSession().getAttribute("usercode").toString();
					projectcase.setOperationer(usercode);
				}
				Date currentTime = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time = formatter.format(currentTime);
				projectcase.setTime(time);

				projectcaseservice.modify(projectcase);
				operationlogservice.add(req, "PROJECT_CASE", projectcase.getId(), projectcase.getProjectid(),
						"编辑用例成功！用例编号：" + projectcase.getSign());
				json.put("status", "success");
				json.put("ms", "编辑成功!");
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
			if (!UserLoginController.permissionboolean(req, "case_1")) {
				json.put("status", "fail");
				json.put("ms", "添加失败,权限不足,请联系管理员!");
			} else {
				if (null != req.getSession().getAttribute("usercode")
						&& null != req.getSession().getAttribute("username")) {
					String usercode = req.getSession().getAttribute("usercode").toString();
					projectcase.setOperationer(usercode);
				}
				String regEx_space = "\t|\r|\n";// 定义空格回车换行符
				Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
				Matcher m_space = p_space.matcher(projectcase.getRemark());
				projectcase.setRemark(m_space.replaceAll("")); // 过滤空格回车标签

				Date currentTime = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time = formatter.format(currentTime);
				projectcase.setTime(time);

				SectorProjects sp = sectorprojectsService.loadob(projectcase.getProjectid());
				projectcase.setSign(sp.getProjectsign() + "-0000");
				int caseid = projectcaseservice.add(projectcase);
				projectcase.setSign(sp.getProjectsign() + "-" + caseid);
				projectcaseservice.modify(projectcase);
				operationlogservice.add(req, "PROJECT_CASE", caseid, projectcase.getProjectid(),
						"添加用例成功！用例编号：" + projectcase.getSign());

				json.put("status", "success");
				json.put("ms", "添加用例成功！");
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
			if (!UserLoginController.permissionboolean(req, "case_2")) {
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
				JSONObject jsonObject = JSONObject.fromObject(sb.toString());
				JSONArray jsonarr = JSONArray.fromObject(jsonObject.getString("caseids"));

				for (int i = 0; i < jsonarr.size(); i++) {
					int id = Integer.valueOf(jsonarr.get(i).toString());
					ProjectCase pc=projectcaseservice.load(id);
					casestepsservice.delforcaseid(id);
					projectcaseservice.delete(id);
					operationlogservice.add(req, "PROJECT_CASE", pc.getId(), pc.getProjectid(),
							"删除用例成功！");
				}
				json.put("status", "success");
				json.put("ms", "删除成功!");
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
			
			ProjectCase pc=projectcaseservice.getCaseBySign(sign);
			String jsonStr=JSONObject.fromObject(pc).toString();
			pw.print(jsonStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {

	}

}
