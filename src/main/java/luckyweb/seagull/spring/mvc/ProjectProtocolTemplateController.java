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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.spring.entity.ProjectProtocolTemplate;
import luckyweb.seagull.spring.entity.ProjectTemplateParams;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.entity.UserInfo;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.ProjectProtocolTemplateService;
import luckyweb.seagull.spring.service.ProjectTemplateParamsService;
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
			if (null != req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE)
					&& null != req.getSession().getAttribute(PublicConst.SESSIONKEYUSERNAME)) {
				String usercode = req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE).toString();
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
	
	@RequestMapping(value = "/list.do")
	private void ajaxGetSellRecord(Integer limit, Integer offset, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
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
			ProjectProtocolTemplate template = ptlist.get(i);
			for (SectorProjects projectlist : prolist) {
				if (template.getProjectid() == projectlist.getProjectid()) {
					template.setProjectname(projectlist.getProjectname());
					ptlist.set(i, template);
					break;
				}
			}

		}
		// 转换成json字符串
		JSONArray recordJson = StrLib.listToJson(ptlist);
		// 得到总记录数
		int total = ptemplateservice.findRows(ppt);
		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", recordJson);
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
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHPPTEMPLATEADD)) {
				json.put("status", "fail");
				json.put("ms", "增加协议模板失败,权限不足,请联系管理员!");
			} else {
				int copyid = Integer.valueOf(req.getParameter("copyid"));
				if(!UserLoginController.oppidboolean(req, ppt.getProjectid())){
					if(0!=copyid){
						json.put("status", "fail");
						json.put("ms", "复制协议模板失败,项目权限不足,请联系管理员!");
					}else{
						json.put("status", "fail");
						json.put("ms", "增加协议模板失败,项目权限不足,请联系管理员!");
					}
				}else{
					if (null != req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE)
							&& null != req.getSession().getAttribute(PublicConst.SESSIONKEYUSERNAME)) {
						String usercode = req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE).toString();
						ppt.setOperationer(usercode);
					}
					// 定义空格回车换行符
					String regExSpace = "\t|\r|\n";
					Pattern pSpace = Pattern.compile(regExSpace, Pattern.CASE_INSENSITIVE);
					Matcher mSpace = pSpace.matcher(ppt.getRemark());
					// 过滤空格回车标签
					ppt.setRemark(mSpace.replaceAll("")); 

					Date currentTime = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time = formatter.format(currentTime);
					ppt.setTime(time);

					int id = ptemplateservice.add(ppt);

					operationlogservice.add(req, "PROJECT_PROTOCOLTEMPLATE", id, ppt.getProjectid(),5, "添加协议模板成功!");

					String ms="添加协议模板【"+ppt.getName()+"】成功！";
					if(0!=copyid){
						List<ProjectTemplateParams> params=ptemplateparamsService.getParamsList(copyid);
						for(ProjectTemplateParams param:params){
							param.setTemplateid(id);
							int paramid=ptemplateparamsService.add(param);
						    operationlogservice.add(req, "PROJECT_TEMPLATEPARAMS", paramid, ppt.getProjectid(),2,
									"复制协议模板内容参数成功！");
						}
						ms="复制协议模板【"+ppt.getName()+"】成功！";
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
	
	
	@RequestMapping(value = "/update.do")
	public void update(HttpServletRequest req, HttpServletResponse rsp, ProjectProtocolTemplate ppt) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHPPTEMPLATEMOD)) {
				json.put("status", "fail");
				json.put("ms", "编辑模板失败,权限不足,请联系管理员!");
			} else {
				if(!UserLoginController.oppidboolean(req, ppt.getProjectid())){
					json.put("status", "fail");
					json.put("ms", "编辑模板失败,项目权限不足,请联系管理员!");
				}else{
					if (null != req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE)
							&& null != req.getSession().getAttribute(PublicConst.SESSIONKEYUSERNAME)) {
						String usercode = req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE).toString();
						ppt.setOperationer(usercode);
					}
					Date currentTime = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time = formatter.format(currentTime);
					ppt.setTime(time);

					ptemplateservice.modify(ppt);

					operationlogservice.add(req, "PROJECT_PROTOCOLTEMPLATE", ppt.getId(), ppt.getProjectid(),1,
							"修改测试协议模板成功!");
					json.put("status", "success");
					json.put("ms", "编辑测试协议模板成功!");
				}

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
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHPPTEMPLATEDEL)) {
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
				JSONObject jsonObject = JSONObject.parseObject(sb.toString());
				JSONArray jsonarr = JSONArray.parseArray(jsonObject.getString("templateids"));

				String status="fail";
				String ms="删除协议模板失败!";
				int suc=0;
				int fail=0;
				for (int i = 0; i < jsonarr.size(); i++) {
					int id = Integer.valueOf(jsonarr.get(i).toString());
					ProjectProtocolTemplate ppt = ptemplateservice.load(id);
					
					if(!UserLoginController.oppidboolean(req, ppt.getProjectid())){
						fail++;
						continue;
					}	
					ptemplateparamsService.delete(id);
					ptemplateservice.deleteforob(ppt);
					operationlogservice.add(req, "PROJECT_PROTOCOLTEMPLATE", id, ppt.getProjectid(),0, "删除协议模板成功!");
					suc++;
				}
				
				if(suc>0){
					status="success";
					ms="删除协议模板成功!";
					if(fail>0){
						status="success";
						ms="删除协议模板"+suc+"条成功！"+fail+"条因为无项目权限删除失败！";
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
			String recordJson = jsonarr.toString();
			String str = "{\"message\": \"\",\"value\": "+recordJson+",\"code\": 200,\"redirect\": \"\" }";
					
			json = JSONObject.parseObject(str);
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
			String recordJson = jsonarr.toString();
			String str = "{\"message\": \"\",\"value\": "+recordJson+",\"code\": 200,\"redirect\": \"\" }";
					
			json = JSONObject.parseObject(str);
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
			String jsonStr = JSONObject.toJSONString(ppt);
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
