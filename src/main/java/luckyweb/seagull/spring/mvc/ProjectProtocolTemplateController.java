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
import luckyweb.seagull.spring.entity.ProjectCasesteps;
import luckyweb.seagull.spring.entity.ProjectPlan;
import luckyweb.seagull.spring.entity.ProjectProtocolTemplate;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.entity.UserInfo;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.ProjectProtocolTemplateService;
import luckyweb.seagull.spring.service.ProjectTemplateParamsService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.spring.service.UserInfoService;
import luckyweb.seagull.util.StrLib;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
@RequestMapping("/projectprotocolTemplate")
public class ProjectProtocolTemplateController {
	
	@Resource(name = "projectprotocoltemplateService")
	private ProjectProtocolTemplateService ptemplateservice;
	
	@Resource(name = "projecttemplateparamsService")
	private ProjectTemplateParamsService ptemplateparamsService;
	
	@Resource(name = "sectorprojectsService")
	private SectorProjectsService sectorprojectsService;
	
	@Resource(name = "userinfoService")
	private UserInfoService userinfoservice;
	
	@Resource(name = "operationlogService")
	private OperationLogService operationlogservice;
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
			List<SectorProjects> prolist=sectorprojectsService.getAllProject();
			for(int i=0;i<prolist.size();i++){
				if(prolist.get(i).getProjecttype()==1){
					prolist.get(i).setProjectname(prolist.get(i).getProjectname()+"(TestLink项目)");
				}
			}
			model.addAttribute("projects", prolist);
			model.addAttribute("projectid", projectid);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/projectprotocolTemplate/load.do");
			return "error";
		}
		return "/jsp/plancase/ptctemplate";
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/list.do")
	private void ajaxGetSellRecord(Integer limit, Integer offset, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		String search = request.getParameter("search");
		String projectid = request.getParameter("projectid");
		ProjectProtocolTemplate ppt = new ProjectProtocolTemplate();
		if (null == offset && null == limit) {
			offset = 0;
		}
		if (null == limit || limit == 0) {
			limit = 10;
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(search)) {
			ppt.setOperationer(search);
			ppt.setName(search);
			ppt.setRemark(search);
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(projectid)) {
			ppt.setProjectid(Integer.valueOf(projectid));
		}
		
		List<ProjectProtocolTemplate> ptlist = ptemplateservice.findByPage(ppt, offset, limit);
		List<SectorProjects> prolist = QueueListener.qa_projlist;
		for (int i = 0; i < ptlist.size(); i++) {
			ProjectProtocolTemplate Template = ptlist.get(i);
			for (SectorProjects projectlist : prolist) {
				if (Template.getProjectid() == projectlist.getProjectid()) {
					Template.setProjectname(projectlist.getProjectname());
					ptlist.set(i, Template);
					break;
				}
			}

		}
		// 转换成json字符串
		String RecordJson = StrLib.listToJson(ptlist);
		// 得到总记录数
		int total = ptemplateservice.findRows(ppt);
		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", RecordJson);
		pw.print(json.toString());
	}
	
	/**
	 * 添加模板
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
	@RequestMapping(value = "/add.do")
	public void add(ProjectProtocolTemplate ppt, HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			if (!UserLoginController.permissionboolean(req, "ptct_1")) {
				json.put("status", "fail");
				json.put("ms", "增加协议模板失败,权限不足,请联系管理员!");
			} else {
				if (null != req.getSession().getAttribute("usercode")
						&& null != req.getSession().getAttribute("username")) {
					String usercode = req.getSession().getAttribute("usercode").toString();
					ppt.setOperationer(usercode);
				}

				String regEx_space = "\t|\r|\n";// 定义空格回车换行符
				Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
				Matcher m_space = p_space.matcher(ppt.getRemark());
				ppt.setRemark(m_space.replaceAll("")); // 过滤空格回车标签

				Date currentTime = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time = formatter.format(currentTime);
				ppt.setTime(time);

				int id = ptemplateservice.add(ppt);

				operationlogservice.add(req, "PROJECT_PROTOCOLTEMPLATE", id, ppt.getProjectid(), "添加协议模板成功!");

				json.put("status", "success");
				json.put("ms", "添加协议模板成功!");
			}
			pw.print(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	@RequestMapping(value = "/update.do")
	public void updateplan(HttpServletRequest req, HttpServletResponse rsp, ProjectProtocolTemplate ppt) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			if (!UserLoginController.permissionboolean(req, "ptct_3")) {
				json.put("status", "fail");
				json.put("ms", "编辑模板失败,权限不足,请联系管理员!");
			} else {
				if (null != req.getSession().getAttribute("usercode")
						&& null != req.getSession().getAttribute("username")) {
					String usercode = req.getSession().getAttribute("usercode").toString();
					ppt.setOperationer(usercode);
				}
				Date currentTime = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time = formatter.format(currentTime);
				ppt.setTime(time);

				ptemplateservice.modify(ppt);

				operationlogservice.add(req, "PROJECT_PROTOCOLTEMPLATE", ppt.getId(), ppt.getProjectid(),
						"修改测试协议模板成功!");
				json.put("status", "success");
				json.put("ms", "编辑测试协议模板成功!");
			}
			pw.print(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除模板
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
			if (!UserLoginController.permissionboolean(req, "ptct_2")) {
				json.put("status", "fail");
				json.put("ms", "删除模板失败,权限不足,请联系管理员!");
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
				JSONArray jsonarr = JSONArray.fromObject(jsonObject.getString("templateids"));

				for (int i = 0; i < jsonarr.size(); i++) {
					int id = Integer.valueOf(jsonarr.get(i).toString());
					ProjectProtocolTemplate ppt = ptemplateservice.load(id);
					ptemplateparamsService.delete(id);
					ptemplateservice.deleteforob(ppt);
					operationlogservice.add(req, "PROJECT_PROTOCOLTEMPLATE", id, ppt.getProjectid(), "删除协议模板成功!");
				}
				json.put("status", "success");
				json.put("ms", "删除协议模板成功!");
			}
			pw.print(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value = "/cgetPTemplateList.do")
	public void cgetPTemplateList(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setCharacterEncoding("utf-8");			
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			ProjectProtocolTemplate ppt=new ProjectProtocolTemplate();
			String projectid = req.getParameter("projectid");
			String steptype = req.getParameter("steptype");
			// 得到客户端传递的查询参数
			if (!StrLib.isEmpty(projectid)) {
				ppt.setProjectid(Integer.valueOf(projectid));
			}
			if (!StrLib.isEmpty(steptype)) {
				ppt.setProtocoltype(steptype);
			}
			List<ProjectProtocolTemplate> ptlist = ptemplateservice.findByPage(ppt, 0, 99999);
			JSONArray jsonarr = new JSONArray();
			for(ProjectProtocolTemplate obppt:ptlist){
				JSONObject jo = new JSONObject();
                jo.put("name", "【"+obppt.getId()+"】"+obppt.getName());
                jo.put("protocoltype", obppt.getProtocoltype());
                jo.put("operationer", obppt.getOperationer());
                jsonarr.add(jo);
			}
			String RecordJson = jsonarr.toString();
			String str = "{\"message\": \"\",\"value\": "+RecordJson+",\"code\": 200,\"redirect\": \"\" }";
					
			json = JSONObject.fromObject(str);
			pw.print(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/cgetStepParamList.do")
	public void cgetStepParamList(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setCharacterEncoding("utf-8");			
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			ProjectProtocolTemplate ppt=new ProjectProtocolTemplate();
			String steptype = req.getParameter("steptype");
			String parentid = req.getParameter("parentid");
			String fieldname = req.getParameter("fieldname");
			int isteptype=0;
			int iparentid=0;
			// 得到客户端传递的查询参数
			if (!StrLib.isEmpty(parentid)) {
				iparentid = Integer.valueOf(parentid);
			}
			if (!StrLib.isEmpty(steptype)) {
				isteptype = Integer.valueOf(steptype);
			}
			List<Object[]> sptlist = ptemplateservice.findstepsparamList(isteptype, iparentid, fieldname);
			JSONArray jsonarr = new JSONArray();
			for(Object[] obparam:sptlist){
				JSONObject jo = new JSONObject();
                jo.put("name", obparam[0]);
                jo.put("description", obparam[1]);
                jsonarr.add(jo);
			}
			String RecordJson = jsonarr.toString();
			String str = "{\"message\": \"\",\"value\": "+RecordJson+",\"code\": 200,\"redirect\": \"\" }";
					
			json = JSONObject.fromObject(str);
			pw.print(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/cgetPTemplateById.do")
	public void cgetPTemplateById(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=GBK");
			req.setCharacterEncoding("GBK");
			PrintWriter pw = rsp.getWriter();
			String templateid = req.getParameter("templateid");

			ProjectProtocolTemplate ppt = ptemplateservice.load(Integer.valueOf(templateid));
			String jsonStr = JSONObject.fromObject(ppt).toString();
			pw.print(jsonStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
	}

}
