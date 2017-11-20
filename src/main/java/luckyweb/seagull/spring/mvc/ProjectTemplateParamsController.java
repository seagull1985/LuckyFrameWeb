package luckyweb.seagull.spring.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.spring.entity.ProjectProtocolTemplate;
import luckyweb.seagull.spring.entity.ProjectTemplateParams;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.ProjectProtocolTemplateService;
import luckyweb.seagull.spring.service.ProjectTemplateParamsService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.spring.service.UserInfoService;
import luckyweb.seagull.util.StrLib;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/projectTemplateParams")
public class ProjectTemplateParamsController {

	@Resource(name = "projecttemplateparamsService")
	private ProjectTemplateParamsService ptemplateparamsService;
	
	@Resource(name = "projectprotocoltemplateService")
	private ProjectProtocolTemplateService ptemplateservice;
	
	@Resource(name = "sectorprojectsService")
	private SectorProjectsService sectorprojectsService;

	@Resource(name = "operationlogService")
	private OperationLogService operationlogservice;

	@Resource(name = "userinfoService")
	private UserInfoService userinfoservice;

	private List<ProjectTemplateParams> oldParamsList;
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
	@RequestMapping(value = "/templateParams.do")
	public String templateParams(Model model,HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");

			if (!UserLoginController.permissionboolean(req, "ptct_3")) {
				model.addAttribute("url", "/projectprotocolTemplate/load.do");
				model.addAttribute("message", "当前用户无权限编辑协议模板，请联系管理员！");
				return "success";
			}

			String templateid = req.getParameter("templateid");
			ProjectProtocolTemplate ppt = null;
			if (!StrLib.isEmpty(templateid) && !"0".equals(templateid)) {
				ppt = ptemplateservice.load(Integer.valueOf(templateid));
				if(!UserLoginController.oppidboolean(req, ppt.getProjectid())){
					SectorProjects sp=sectorprojectsService.loadob(ppt.getProjectid());
					model.addAttribute("url", "/projectprotocolTemplate/load.do");
					model.addAttribute("message", "当前用户无权限编辑项目【"+sp.getProjectname()+"】的协议模板，请联系管理员！");
					return "error";
				}
				List<SectorProjects> prolist = QueueListener.qa_projlist;
				for (SectorProjects project : prolist) {
						if (ppt.getProjectid() == project.getProjectid()) {
							ppt.setProjectname(project.getProjectname());
							break;
						}
					}
			}
			String retVal = "/jsp/plancase/templateparam";
			List<ProjectTemplateParams> paramslist = ptemplateparamsService.getParamsList(Integer.valueOf(templateid));
			oldParamsList = paramslist;
			
			if (paramslist.size() == 0) {
				ProjectTemplateParams ptp = new ProjectTemplateParams();
				ptp.setParam("");
				ptp.setParamname("");
				ptp.setTemplateid(Integer.valueOf(templateid));
				paramslist.add(ptp);
			}else{
				for(int i=0;i<paramslist.size();i++){
					ProjectTemplateParams ptp = paramslist.get(i);
					ptp.setParam(ptp.getParam().replaceAll("\"", "&quot;"));
				}
			}

			model.addAttribute("ptemplate", ppt);
			model.addAttribute("templateparams", paramslist);
			return retVal;

		} catch (Exception e) {
			System.out.println(e);
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/projectprotocolTemplate/load.do");
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

	@RequestMapping(value = "/editparam.do")
	private void editparam(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		StringBuilder sb = new StringBuilder();
		JSONObject json = new JSONObject();
		if (!UserLoginController.permissionboolean(request, "ptct_3")) {
			json.put("status", "fail");
			json.put("ms", "保存参数失败,权限不足,请联系管理员!");
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

			JSONArray jsonarr = JSONArray.fromObject(jsonstr);

			//处理json-lib 2.4版本当遇到json格式字符串时，把它当成对象处理的bug
			for(int i=0;i<jsonarr.size();i++){
				JSONObject tempobj=(JSONObject) jsonarr.get(i);
				String str=tempobj.get("param").toString();
				if("[".equals(str.substring(0, 1))&&"]".equals(str.substring(str.length()-1))){
				   tempobj.element("param", "***"+str);
				   jsonarr.set(i, tempobj);
				}
			}

			List<?> list = JSONArray.toList(jsonarr, new ProjectTemplateParams(), new JsonConfig());// 参数1为要转换的JSONArray数据，参数2为要转换的目标数据，即List盛装的数据
			String usercode = "";
			if (null != request.getSession().getAttribute("usercode")
					&& null != request.getSession().getAttribute("username")) {
				usercode = request.getSession().getAttribute("usercode").toString();
			}
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = formatter.format(currentTime);
			ProjectProtocolTemplate ppt = new ProjectProtocolTemplate();
			
			List<ProjectTemplateParams> paramslist =oldParamsList;
			for (int i = 0; i < list.size(); i++) {
				ProjectTemplateParams param = (ProjectTemplateParams) list.get(i);
				if(param.getParam().indexOf("***[")>-1&&"***[".equals(param.getParam().substring(0, 4))){
					param.setParam(param.getParam().substring(3));
				}
				
				if(i==0){
					ppt = ptemplateservice.load(param.getTemplateid());
				}
				int tag=0;   //标识是否已有参数
				for (ProjectTemplateParams oldparam:paramslist) {
					if(param.getId()==oldparam.getId()&&oldparam.getId()!=0){
						tag=1;
						param.setParam(param.getParam().replaceAll("&quot;", "\""));
						param.setParamname(param.getParamname().replaceAll("&quot;", "\""));
						ptemplateparamsService.modify(param);
						paramslist.remove(oldparam);
						break;
					}
				}
				
				if(tag==0){
					param.setParam(param.getParam().replaceAll("&quot;", "\""));
					param.setParamname(param.getParamname().replaceAll("&quot;", "\""));
					ptemplateparamsService.add(param);
				}
			}
			
			for (ProjectTemplateParams oldparam:paramslist) { // 删除原有列表中多的步骤
				ptemplateparamsService.deleteforob(oldparam);
			}
			
			ppt.setOperationer(usercode);
			ppt.setTime(time);
			ptemplateservice.modify(ppt);
			
			json.put("status", "success");
			json.put("ms", "编辑模板参数成功!");
		}
		pw.print(json.toString());
	}
	
	@RequestMapping(value = "/cgetParamsByTemplate.do")
	public void cgetParamsByTemplate(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=GBK");
			req.setCharacterEncoding("GBK");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			String templateid = req.getParameter("templateid");
			
			List<ProjectTemplateParams> params = ptemplateparamsService.getParamsList(Integer.valueOf(templateid));

			// 转换成json字符串
			String RecordJson = StrLib.listToJson(params);

			// 需要返回的数据有总记录数和行数据
			json.put("params", RecordJson);
			pw.print(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {

	}

}
