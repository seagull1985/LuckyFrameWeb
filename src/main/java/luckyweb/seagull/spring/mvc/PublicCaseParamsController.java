package luckyweb.seagull.spring.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.spring.entity.PublicCaseParams;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.entity.UserInfo;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.PublicCaseParamsService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.spring.service.UserInfoService;
import luckyweb.seagull.util.StrLib;

/**
 * 
 * @ClassName: PublicCaseParamsController
 * @Description: 用例公共参数控制 
 * @author： seagull
 * @date 2017年11月29日 上午9:29:40
 * 
 */
@Controller
@RequestMapping("/publicCaseParams")
public class PublicCaseParamsController {
	
	@Resource(name = "publiccaseparamsService")
	private PublicCaseParamsService pcpservice;
	
	@Resource(name = "sectorprojectsService")
	private SectorProjectsService sectorprojectsService;
	
	@Resource(name = "operationlogService")
	private OperationLogService operationlogservice;
	
	@Resource(name = "userinfoService")
	private UserInfoService userinfoservice;
	
	private static String accesscode_paramadd="pcp_1";
	private static String accesscode_paramdel="pcp_2";
	private static String accesscode_paramupdate="pcp_3";
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
	    List<SectorProjects> lsp=sectorprojectsService.getAllProject();
	    for(SectorProjects sp:lsp){
			if(sp.getProjecttype()==1){
				sp.setProjectname(sp.getProjectname()+"(TestLink项目)");
			}
	    }
	    int projectid = 99;
		if (null != req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE)
				&& null != req.getSession().getAttribute(PublicConst.SESSIONKEYUSERNAME)) {
			String usercode = req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE).toString();
			UserInfo userinfo = userinfoservice.getUseinfo(usercode);
			projectid = userinfo.getProjectid();
		}
		model.addAttribute("projectid", projectid);
		model.addAttribute("projectlist", lsp);
		return "/jsp/plancase/public_caseparams";
	}

	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/list.do")
	private void ajaxGetSellRecord(Integer limit, Integer offset, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		PrintWriter pw = response.getWriter();
		String search = request.getParameter("search");
		String projectid = request.getParameter("projectid");
		PublicCaseParams pcp= new PublicCaseParams();
		if (null == offset && null == limit) {
			offset = 0;
		}
		if (null == limit || limit == 0) {
			limit = 10;
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(search)) {
			pcp.setParamsname(search);
			pcp.setParamsvalue(search);
			pcp.setRemark(search);
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(projectid)) {
			pcp.setProjectid(Integer.valueOf(projectid));
		}
		List<SectorProjects> prolist = sectorprojectsService.getAllProject();
		List<PublicCaseParams> pcps = pcpservice.findByPage(pcp, offset, limit);
		for(PublicCaseParams param:pcps){
			for(SectorProjects sp:prolist){
				if(param.getProjectid()==sp.getProjectid()){
					param.setProjectname(sp.getProjectname());
				}
			}
		}
		// 转换成json字符串
		JSONArray recordJson = StrLib.listToJson(pcps);
		// 得到总记录数
		int total = pcpservice.findRows(pcp);
		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", recordJson);
		pw.print(json.toString());
	}
	
	/**
	 * 添加客户端
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
	public void add(PublicCaseParams pcp, HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			if(pcp.getId()==0){
				if (!UserLoginController.permissionboolean(req, accesscode_paramadd)) {
					json.put("status", "fail");
					json.put("ms", "增加公共参数失败,权限不足,请联系管理员!");
				}else if(!UserLoginController.oppidboolean(req, pcp.getProjectid())){
					json.put("status", "fail");
					json.put("ms", "增加公共参数失败,项目权限不足,请联系管理员!");
				}else {
		        	PublicCaseParams existPcp = pcpservice.getParamByName(pcp.getParamsname(),String.valueOf(pcp.getProjectid()));           
		            if(null!=existPcp){
						json.put("status", "fail");
						json.put("ms", "增加公共参数失败,参数名称已经在项目中存在!");
		            }else{
						int id=pcpservice.add(pcp);
						operationlogservice.add(req, "PUBLIC_CASEPARAMS", id, pcp.getProjectid(),3,"公共参数【"+pcp.getParamsname()+"】添加成功！");
						json.put("status", "success");
						json.put("ms", "添加公共参数成功!");
		            }
				}
			}else{
				if (!UserLoginController.permissionboolean(req, accesscode_paramupdate)) {
					json.put("status", "fail");
					json.put("ms", "编辑公共参数失败,权限不足,请联系管理员!");
				}else if(!UserLoginController.oppidboolean(req, pcp.getProjectid())){
					json.put("status", "fail");
					json.put("ms", "编辑公共参数失败,项目权限不足,请联系管理员!");
				} else {
		        	PublicCaseParams existPcp = pcpservice.load(Integer.valueOf(pcp.getId()));
		        	Boolean result=false;
		        	if(pcp.getParamsname().equals(existPcp.getParamsname())){
		        		result=true;
		        	}else{
		        		existPcp=pcpservice.getParamByName(pcp.getParamsname(),String.valueOf(pcp.getProjectid()));
		                if(null==existPcp){
		                	result=true;
		                }
		        	}
		        	if(result){
						pcpservice.modify(pcp);
						operationlogservice.add(req, "PUBLIC_CASEPARAMS", pcp.getId(), pcp.getProjectid(),1, "公共参数【"+pcp.getParamsname()+"】编辑成功！");
						json.put("status", "success");
						json.put("ms", "编辑公共参数成功!");
		        	}else{
						json.put("status", "fail");
						json.put("ms", "增加公共参数失败,参数名称已经在项目中存在!");
		        	}

				}
			}
			pw.print(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 删除参数
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
			if (!UserLoginController.permissionboolean(req, accesscode_paramdel)) {
				json.put("status", "fail");
				json.put("ms", "删除参数失败,权限不足,请联系管理员!");
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
				JSONArray jsonarr = JSONArray.parseArray(jsonObject.getString("ids"));

				String status="fail";
				String ms="删除参数失败!";
				int suc=0;
				int delfail=0;
				for (int i = 0; i < jsonarr.size(); i++) {
					int id = Integer.valueOf(jsonarr.get(i).toString());
					PublicCaseParams pcp = pcpservice.load(id);

					if(!UserLoginController.oppidboolean(req, pcp.getProjectid())){
						delfail++;
						continue;
					}
					pcpservice.delete(pcp);
					operationlogservice.add(req, "PUBLIC_CASEPARAMS", pcp.getId(), pcp.getProjectid(),0, "删除公共参数【"+pcp.getParamsname()+"】成功！");
					suc++;
				}
				
				if(suc>0){
					status="success";
					ms="删除参数成功!";
					if(delfail>0){
						status="warning";
						ms="删除参数"+suc+"条成功！"+delfail+"条因为无项目权限删除失败！";
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
     * 返回String类型的结果  
     * 检查用户名的合法性,如果用户已经存在，返回false，否则返回true(返回json数据，格式为{"valid",true})  
     */  
    @RequestMapping(value = "/checkParamsNameExists", produces = "application/json;charset=UTF-8")  
    public @ResponseBody  
    String checkParamsNameExists(@RequestParam String paramsname,@RequestParam String projectid,@RequestParam String id) throws Exception {
    	Boolean result=false;
        if(!PublicConst.STATUSSTR0.equals(id)){
        	PublicCaseParams pcp = pcpservice.load(Integer.valueOf(id));
        	if(paramsname.equals(pcp.getParamsname())){
        		result=true;
        	}else{
        		pcp=pcpservice.getParamByName(paramsname,projectid);
                if(null==pcp){
                	result=true;
                }
        	}
        	
        }else{
        	PublicCaseParams pcp = pcpservice.getParamByName(paramsname,projectid);           
            if(null==pcp){
            	result=true;
            }
        }
        return "{\"valid\":"+result+"}";
    } 
    
	@RequestMapping(value = "/cgetParamsByProjectid.do")
	public void cgetParamsByProjectid(HttpServletRequest req, HttpServletResponse rsp) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=GBK");
			req.setCharacterEncoding("GBK");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			String projectid = req.getParameter("projectid");

			List<PublicCaseParams> pcplist = pcpservice.getParamListByProjectid(projectid);
			// 转换成json字符串
			JSONArray recordJson = StrLib.listToJson(pcplist);

			// 需要返回的数据有总记录数和行数据
			json.put("params", recordJson);
			pw.print(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
