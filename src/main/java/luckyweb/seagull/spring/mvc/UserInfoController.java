package luckyweb.seagull.spring.mvc;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.spring.entity.SecondarySector;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.entity.UserAuthority;
import luckyweb.seagull.spring.entity.UserInfo;
import luckyweb.seagull.spring.entity.UserRole;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.SecondarySectorService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.spring.service.UserAuthorityService;
import luckyweb.seagull.spring.service.UserInfoService;
import luckyweb.seagull.spring.service.UserRoleService;
import luckyweb.seagull.util.Endecrypt;
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
@RequestMapping("/userInfo")
public class UserInfoController {
	
	@Resource(name = "userinfoService")
	private UserInfoService userinfoservice;
	
	@Resource(name = "userroleService")
	private UserRoleService userroleservice;
	
	@Resource(name = "userauthorityService")
	private UserAuthorityService userauthorityservice;
	
	@Resource(name = "secondarysectorService")
	private SecondarySectorService secondarysectorService;
	
	@Resource(name = "sectorprojectsService")
	private SectorProjectsService sectorprojectsService;
	
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
		return "/jsp/user/user";
	}

	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/list.do")
	private void ajaxGetSellRecord(Integer limit, Integer offset, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		String search = request.getParameter("search");
		UserInfo userinfo= new UserInfo();
		if (null == offset && null == limit) {
			offset = 0;
		}
		if (null == limit || limit == 0) {
			limit = 10;
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(search)) {
			userinfo.setUsercode(search);
			userinfo.setUsername(search);
		}
		List<UserRole> roleMap = userroleservice.listall();
		List<SectorProjects> prolist = QueueListener.qa_projlist;
		List<UserInfo> users = userinfoservice.findByPage(userinfo, offset, limit);
		for(int i=0;i<users.size();i++){
			String role="";
			String[] temp=users.get(i).getRole().split(";",-1);
			for(SectorProjects projectlist:prolist){
				if(users.get(i).getProjectid()==projectlist.getProjectid()){
					users.get(i).setProjectname(projectlist.getProjectname());
					break;
				}
			}
			
			for(int k=0;k<temp.length;k++){
				if(null==temp[k]||"".equals(temp[k])){
					continue;
				}
				StringBuilder stringBuilder = new StringBuilder();
				for(int j=0;j<roleMap.size();j++){
					if(Integer.valueOf(temp[k])==roleMap.get(j).getId()){
						stringBuilder.append(roleMap.get(j).getRole()+";");
						break;
					}
				}
				role = role+stringBuilder.toString();
			}
			users.get(i).setRole(role);
			users.get(i).setPassword("");
		}
		// 转换成json字符串
		JSONArray recordJson = StrLib.listToJson(users);
		// 得到总记录数
		int total = userinfoservice.findRows(userinfo);
		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", recordJson);
		pw.print(json.toString());
	}

	/**
	 * 用户添加
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
	public String add(@Valid @ModelAttribute("userinfo") UserInfo userinfo, BindingResult br, Model model,
	        HttpServletRequest req, HttpServletResponse rsp) throws Exception
	{
		try
		{
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			
			if(!UserLoginController.permissionboolean(req, PublicConst.AUTHUSERINFOADD)){
				model.addAttribute("userinfo", new UserInfo());
				model.addAttribute("url", "/userInfo/load.do");
				model.addAttribute("message", "当前用户无权限添加用户，请联系管理员！");
				return "success";
			}

			String retVal = "/jsp/user/user_add";
			if (PublicConst.REQPOSTTYPE.equals(req.getMethod()))
			{
				if (br.hasErrors())
				{
					return retVal;
				}
				String message = "";
				
				if(StrLib.isEmpty(userinfo.getUsername())){
					message = "真实姓名不能为空！";
					model.addAttribute("userrole", userroleservice.listall());
				    model.addAttribute("secondarysector", secondarysectorService.listall());
				    model.addAttribute("projects", QueueListener.qa_projlist);
					model.addAttribute("message", message);
					return retVal;
				}
				if(StrLib.isEmpty(userinfo.getUsercode())){
					message = "用户名不能为空！";
					model.addAttribute("userrole", userroleservice.listall());
				    model.addAttribute("secondarysector", secondarysectorService.listall());
				    model.addAttribute("projects", QueueListener.qa_projlist);
					model.addAttribute("message", message);
					return retVal;
				}
				if(userinfo.getSectorid()==0){
					message = "请至少选择一个所属部门！";
					model.addAttribute("userrole", userroleservice.listall());
				    model.addAttribute("secondarysector", secondarysectorService.listall());
				    model.addAttribute("projects", QueueListener.qa_projlist);
					model.addAttribute("message", message);
					return retVal;
				}
				if(StrLib.isEmpty(userinfo.getPassword())){
					message = "密码不能为空！";
					model.addAttribute("userrole", userroleservice.listall());
				    model.addAttribute("secondarysector", secondarysectorService.listall());
				    model.addAttribute("projects", QueueListener.qa_projlist);
					model.addAttribute("message", message);
					return retVal;
				}
				
				String[] temp = userinfo.getPassword().split(",",-1);
				if(!temp[0].equals(temp[1])){
					message = "两次输入的密码不一致，请确认！";
					model.addAttribute("userrole", userroleservice.listall());
				    model.addAttribute("secondarysector", secondarysectorService.listall());
				    model.addAttribute("projects", QueueListener.qa_projlist);
					model.addAttribute("message", message);
					return retVal;
				}
				
				if(userinfoservice.findUsecode(userinfo.getUsercode())>0){
					message = "用户名已经存在，请重新输入！";
					model.addAttribute("userrole", userroleservice.listall());
				    model.addAttribute("secondarysector", secondarysectorService.listall());
				    model.addAttribute("projects", QueueListener.qa_projlist);
					model.addAttribute("message", message);
					return retVal;
				}
				
				Endecrypt endecrypt = new Endecrypt();
				String cryptpwd = endecrypt.get3DESEncrypt(temp[0]);
				userinfo.setPassword(cryptpwd);
				userinfo.setRole(userinfo.getRole().replaceAll(",", ";"));
				
				SecondarySector ss = secondarysectorService.load(userinfo.getSectorid());
				userinfo.setSectorid(ss.getSectorid());
				userinfo.setSecondarySector(ss);
				
				int userid = userinfoservice.add(userinfo);
				
				operationlogservice.add(req, "USERINFO", userid, 
						99,1,"用户添加成功！用户名："+userinfo.getUsercode());
				
				model.addAttribute("message", "创建用户成功");
				model.addAttribute("url", "/userInfo/load.do");
				return "success";

			}
			    model.addAttribute("userrole", userroleservice.listall());
			    model.addAttribute("secondarysector", secondarysectorService.listall());
			    model.addAttribute("projects", QueueListener.qa_projlist);
			    userinfo.setProjectid(99);
				model.addAttribute("userinfo", userinfo);
				return retVal;

		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/userInfo/load.do");
			return "error";
		}

	}

	/**
	 * 删除调度
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
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHUSERINFODEL)) {
				json.put("status", "fail");
				json.put("ms", "删除用户失败,权限不足,请联系管理员!");
			} else {
				int id = Integer.valueOf(req.getParameter("userid"));
				UserInfo userinfo = userinfoservice.load(id);
				try
				{
					userinfoservice.delete(id);
					
					operationlogservice.add(req, "USERINFO", id, 
							99,0,"用户信息删除成功！用户名："+userinfo.getUsercode());
					
					json.put("status", "success");
					json.put("ms", "删除用户成功!");
				}
				catch (Exception e)
				{
					json.put("status", "fail");
					json.put("ms", "删除用户过程中失败!");
				}
			}
			pw.print(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * 根据id更新用户资料
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update.do")
	public String update(@Valid @ModelAttribute("userinfo") UserInfo userinfo, BindingResult br,
	        Model model, HttpServletRequest req) throws Exception
	{
		req.setCharacterEncoding("utf-8");
		int id = Integer.valueOf(req.getParameter("id"));
		
		if(!UserLoginController.permissionboolean(req, PublicConst.AUTHUSERINFOMOD)){
			model.addAttribute("userinfo", new UserInfo());
			model.addAttribute("url", "/userInfo/load.do");
			model.addAttribute("message", "当前用户无权限修改用户信息，请联系管理员！");
			return "success";
		}
		Endecrypt endecrypt = new Endecrypt();
		try{
		String retVal = "/jsp/user/user_update";
		if (PublicConst.REQPOSTTYPE.equals(req.getMethod()))
		{
			if (br.hasErrors())
			{
				return retVal;
			}
			String message = "";
			if(StrLib.isEmpty(userinfo.getUsername())){
				message = "真实姓名不能为空！";
				model.addAttribute("userrole", userroleservice.listall());
			    model.addAttribute("secondarysector", secondarysectorService.listall());
			    model.addAttribute("projects", QueueListener.qa_projlist);
				model.addAttribute("message", message);
				return retVal;
			}
			if(StrLib.isEmpty(userinfo.getUsercode())){
				message = "用户名不能为空！";
				model.addAttribute("userrole", userroleservice.listall());
			    model.addAttribute("secondarysector", secondarysectorService.listall());
			    model.addAttribute("projects", QueueListener.qa_projlist);
				model.addAttribute("message", message);
				return retVal;
			}
			if(userinfo.getSectorid()==0){
				message = "请至少选择一个所属部门！";
				model.addAttribute("userrole", userroleservice.listall());
			    model.addAttribute("secondarysector", secondarysectorService.listall());
			    model.addAttribute("projects", QueueListener.qa_projlist);
				model.addAttribute("message", message);
				return retVal;
			}
			if(StrLib.isEmpty(userinfo.getPassword())){
				message = "密码不能为空！";
				model.addAttribute("userrole", userroleservice.listall());
			    model.addAttribute("secondarysector", secondarysectorService.listall());
			    model.addAttribute("projects", QueueListener.qa_projlist);
				model.addAttribute("message", message);
				return retVal;
			}
			String[] temp = userinfo.getPassword().split(",",-1);
			if(!temp[0].equals(temp[1])){
				message = "两次输入的密码不一致，请确认！";
				model.addAttribute("userrole", userroleservice.listall());
			    model.addAttribute("secondarysector", secondarysectorService.listall());
			    model.addAttribute("projects", QueueListener.qa_projlist);
				model.addAttribute("message", message);
				return retVal;
			}
			
			String cryptpwd = endecrypt.get3DESEncrypt(temp[0]);
			userinfo.setPassword(cryptpwd);
			userinfo.setRole(userinfo.getRole().replaceAll(",", ";"));
			
			SecondarySector ss = secondarysectorService.load(userinfo.getSectorid());
			userinfo.setSectorid(ss.getSectorid());
			userinfo.setSecondarySector(ss);
			
			userinfoservice.modify(userinfo);
			
			operationlogservice.add(req, "USERINFO", id, 
					99,1,"用户信息修改成功！用户名"+userinfo.getUsercode());

			
			model.addAttribute("message", "修改用户信息成功");
			model.addAttribute("url", "/userInfo/load.do");
			return "success";

		}	
		userinfo = userinfoservice.load(id);
		userinfo.setPassword(endecrypt.get3DESDecrypt(userinfo.getPassword()));
		userinfo.setSectorid(userinfo.getSecondarySector().getSectorid());
		userinfo.setRole(userinfo.getRole().replaceAll(";", ","));
		
	    model.addAttribute("userrole", userroleservice.listall());
	    model.addAttribute("secondarysector", secondarysectorService.listall());
	    model.addAttribute("projects", QueueListener.qa_projlist);
	    
		model.addAttribute("userinfo", userinfo);
		return "/jsp/user/user_update";

	}
	catch (Exception e){
		model.addAttribute("message", e.getMessage());
		model.addAttribute("url", "/userInfo/load.do");
		return "error";
	 }
	}
	
	/**
	 * 
	 * 用户修改密码
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatepw.do")
	public String updatepw(@Valid @ModelAttribute("userinfo") UserInfo userinfo, BindingResult br,
	        Model model, HttpServletRequest req) throws Exception
	{
		req.setCharacterEncoding("utf-8");
		String usercode="";
		if(req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE)!=null&&req.getSession().getAttribute(PublicConst.SESSIONKEYUSERNAME)!=null){
			usercode = req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE).toString();
		}else{
			model.addAttribute("message", "获取用户信息失败！");
			model.addAttribute("url", "../index.jsp");
			return "error";
		}
		
		Endecrypt endecrypt = new Endecrypt();
		try{
		String retVal = "/jsp/user/user_pswupdate";
		if (PublicConst.REQPOSTTYPE.equals(req.getMethod()))
		{
			if (br.hasErrors())
			{
				return retVal;
			}
			String message = "";
			if(StrLib.isEmpty(userinfo.getOldpassword())){
				message = "旧密码不能为空！";
				model.addAttribute("message", message);
				return retVal;
			}
			if(StrLib.isEmpty(userinfo.getPassword())){
				message = "新密码不能为空！";
				model.addAttribute("message", message);
				return retVal;
			}
			String[] temp = userinfo.getPassword().split(",",-1);
			if(!temp[0].equals(temp[1])){
				message = "新密码两次输入的不一致，请确认！";
				model.addAttribute("message", message);
				return retVal;
			}
			
			UserInfo ui = userinfoservice.getUseinfo(usercode);
			String oldcryptpwd = endecrypt.get3DESEncrypt(userinfo.getOldpassword());
			if(!ui.getPassword().equals(oldcryptpwd)){
				message = "您输入的旧密码不对，请确认！";
				model.addAttribute("message", message);
				return retVal;
			}								
			
			String newcryptpwd = endecrypt.get3DESEncrypt(temp[0]);
			ui.setPassword(newcryptpwd);
			
			userinfoservice.modify(ui);
			
			model.addAttribute("message", "修改密码成功,请重新登录！");
			//清空用户状态，重新登录
			req.getSession().setAttribute("usercode", null);
			req.getSession().setAttribute("username", null);
			req.getSession().setAttribute("permission", null);
			model.addAttribute("url", "/progressus/signin.jsp");
			return "success";

		}	

		model.addAttribute("userinfo", userinfo);
		return retVal;

	}
	catch (Exception e){
		model.addAttribute("message", e.getMessage());
		model.addAttribute("url", "/");
		return "error";
	 }
	}
	
	/**
	 * 
	 * 用户修改默认项目
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateproject.do")
	public String updateproject(@Valid @ModelAttribute("userinfo") UserInfo userinfo, BindingResult br,
	        Model model, HttpServletRequest req) throws Exception{
		
		req.setCharacterEncoding("utf-8");
		String usercode="";
		if(req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE)!=null&&req.getSession().getAttribute(PublicConst.SESSIONKEYUSERNAME)!=null){
			usercode = req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE).toString();
		}else{
			model.addAttribute("message", "获取用户信息失败！");
			model.addAttribute("url", "../index.jsp");
			return "error";
		}
		
		try{
		String retVal = "/jsp/user/user_proupdate";
		UserInfo ui = userinfoservice.getUseinfo(usercode);
		if (PublicConst.REQPOSTTYPE.equals(req.getMethod()))
		{
			if (br.hasErrors())
			{
				return retVal;
			}
			ui.setProjectid(userinfo.getProjectid());
			userinfoservice.modify(ui);
			
			model.addAttribute("message", "修改默认项目成功！");
			model.addAttribute("url", "/progressus/signin.jsp");
			return "success";

		}	
		model.addAttribute("projects", QueueListener.qa_projlist);
		model.addAttribute("userinfo", ui);
		return retVal;

	}
	catch (Exception e){
		model.addAttribute("message", e.getMessage());
		model.addAttribute("url", "/");
		return "error";
	 }
	}
	
	/**
	 * 修改角色权限
	 * @param tj
	 * @param br
	 * @param model
	 * @param req
	 * @param rsp
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/role.do")
	public String update(@Valid @ModelAttribute("userrole") UserRole userrole, BindingResult br, Model model,
	        HttpServletRequest req, HttpServletResponse rsp) throws Exception
	{
		try
		{
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");

			if(!UserLoginController.permissionboolean(req, PublicConst.AUTHUSERROLEMOD)){
				model.addAttribute("userinfo", new UserInfo());
				model.addAttribute("url", "/userInfo/load.do");
				model.addAttribute("message", "当前用户无权限查看角色权限信息，请联系管理员！");
				return "success";
			}
			
		    List<SectorProjects> lsp=sectorprojectsService.getAllProject();
		    for(SectorProjects sp:lsp){
				if(sp.getProjecttype()==1){
					sp.setProjectname(sp.getProjectname()+"(TestLink项目)");
				}
		    	if(null!=sp&&sp.getProjectid()==99){
				    lsp.remove(sp);
				    break;
		    	}
		    }
		    
			String retVal = "/jsp/user/role";
			if (PublicConst.REQPOSTTYPE.equals(req.getMethod()))
			{
				if (br.hasErrors())
				{
					return retVal;
				}
				String message = "";
				if(StrLib.isEmpty(userrole.getRole())||PublicConst.STATUSSTR0.equals(userrole.getRole())){
					message = "请至少选择一个角色保存";
					model.addAttribute("projectlist", lsp);
				    model.addAttribute("rolelist", userroleservice.listall());
				    model.addAttribute("authoritylist", userauthorityservice.listall());
					model.addAttribute("userrole", userrole);
					model.addAttribute("message", message);
					return retVal;
				}
				
				if(StrLib.isEmpty(userrole.getOpprojectid())){
					message = "请至少选择一个项目保存";
					model.addAttribute("projectlist", lsp);
				    model.addAttribute("rolelist", userroleservice.listall());
				    model.addAttribute("authoritylist", userauthorityservice.listall());
					model.addAttribute("userrole", userrole);
					model.addAttribute("message", message);
					return retVal;
				}
				
				if(StrLib.isEmpty(userrole.getPermission())){
					message = "请至少选择一个权限项保存";
					model.addAttribute("projectlist", lsp);
				    model.addAttribute("rolelist", userroleservice.listall());
				    model.addAttribute("authoritylist", userauthorityservice.listall());
					model.addAttribute("userrole", userrole);
					model.addAttribute("message", message);
					return retVal;
				}
				
				UserRole urole = userroleservice.load(Integer.valueOf(userrole.getRole()));
				userrole.setId(urole.getId());
				userrole.setRole(urole.getRole());
				userrole.setPermission(","+userrole.getPermission()+",");
				userrole.setOpprojectid(","+userrole.getOpprojectid()+",");
				
				userroleservice.modify(userrole);
				
				operationlogservice.add(req, "USER_ROLE", userrole.getId(), 
						99,1,"角色权限修改成功！角色名称："+userrole.getRole());
				
				model.addAttribute("message", "修改角色权限成功");
				model.addAttribute("url", "/userInfo/role.do");
				return "success";

			}

			    model.addAttribute("projectlist", lsp);
			    model.addAttribute("rolelist", userroleservice.listall());
			    model.addAttribute("authoritylist", userauthorityservice.listall());
				model.addAttribute("userrole", userrole);
				return retVal;

		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/userInfo/load.do");
			return "error";
		}

	}
	
	/**
	 * 角色权限
	 * @param tj
	 * @param br
	 * @param model
	 * @param req
	 * @param rsp
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/roleadd.do")
	public String add(@Valid @ModelAttribute("userrole") UserRole userrole, BindingResult br, Model model,
	        HttpServletRequest req, HttpServletResponse rsp) throws Exception
	{
		try
		{
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");

			if(!UserLoginController.permissionboolean(req, PublicConst.AUTHUSERINFOADD)){
				model.addAttribute("userinfo", new UserInfo());
				model.addAttribute("url", "/userInfo/load.do");
				model.addAttribute("message", "当前用户无权限添加角色权限信息，请联系管理员！");
				return "success";
			}
			
		    List<SectorProjects> lsp=sectorprojectsService.getAllProject();
		    for(SectorProjects sp:lsp){
				if(sp.getProjecttype()==1){
					sp.setProjectname(sp.getProjectname()+"(TestLink项目)");
				}
		    	if(null!=sp&&sp.getProjectid()==99){
				    lsp.remove(sp);
				    break;
		    	}
		    }
		    
			String retVal = "/jsp/user/role_add";
			if (PublicConst.REQPOSTTYPE.equals(req.getMethod()))
			{
				if (br.hasErrors())
				{
					return retVal;
				}
				String message = "";
				if(StrLib.isEmpty(userrole.getRole())||PublicConst.STATUSSTR0.equals(userrole.getRole())){
					message = "请至少选择一个角色保存";
					model.addAttribute("projectlist", lsp);
				    model.addAttribute("rolelist", userroleservice.listall());
				    model.addAttribute("authoritylist", userauthorityservice.listall());
					model.addAttribute("userrole", userrole);
					model.addAttribute("message", message);
					return retVal;
				}
				
				if(StrLib.isEmpty(userrole.getOpprojectid())){
					message = "请至少选择一个项目保存";
					model.addAttribute("projectlist", lsp);
				    model.addAttribute("rolelist", userroleservice.listall());
				    model.addAttribute("authoritylist", userauthorityservice.listall());
					model.addAttribute("userrole", userrole);
					model.addAttribute("message", message);
					return retVal;
				}
				
				if(StrLib.isEmpty(userrole.getPermission())){
					message = "请至少选择一个权限项保存";
					model.addAttribute("projectlist", lsp);
				    model.addAttribute("rolelist", userroleservice.listall());
				    model.addAttribute("authoritylist", userauthorityservice.listall());
					model.addAttribute("userrole", userrole);
					model.addAttribute("message", message);
					return retVal;
				}
				
				userrole.setPermission(","+userrole.getPermission()+",");
				userrole.setOpprojectid(","+userrole.getOpprojectid()+",");
				
				userroleservice.add(userrole);
				
				operationlogservice.add(req, "USER_ROLE", userrole.getId(), 
						99,1,"角色权限添加成功！角色名称："+userrole.getRole());
				
				model.addAttribute("message", "添加角色权限成功");
				model.addAttribute("url", "/userInfo/role.do");
				return "success";

			}
			    model.addAttribute("projectlist", lsp);
			    model.addAttribute("authoritylist", userauthorityservice.listall());
				model.addAttribute("userrole", userrole);
				return retVal;

		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/userInfo/load.do");
			return "error";
		}

	}
	
	/**
	 * 删除角色
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/roledelete.do", method = RequestMethod.GET)
	public String roledelete(Model model,HttpServletRequest req) throws Exception
	{
		if(!UserLoginController.permissionboolean(req, PublicConst.AUTHUSERINFODEL)){
			model.addAttribute("userinfo", new UserInfo());
			model.addAttribute("url", "/userInfo/load.do");
			model.addAttribute("message", "当前用户无权限删除角色，请联系管理员！");
			return "success";
		}
		
		int id = Integer.valueOf(req.getParameter("id"));
		UserRole userrole = userroleservice.load(id);
		try
		{		
			userroleservice.delete(id);
		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/userInfo/load.do");
			return "error";
		}
		
		operationlogservice.add(req, "USER_ROLE", id, 
				99,0,"角色信息删除成功！角色名称："+userrole.getRole());

		String message = "删除角色成功！";
		model.addAttribute("userinfo", new UserInfo());
		model.addAttribute("message", message);
		model.addAttribute("url", "/userInfo/role.do");
		return "success";
	}
	
	/**
	 * 角色权限查询
	 * @param tj
	 * @param br
	 * @param model
	 * @param req
	 * @param rsp
	 * @return id
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/getauth.do")
	public void getcheckinfo(HttpServletRequest req, HttpServletResponse rsp) throws Exception{
		int	roleId = Integer.valueOf(req.getParameter("roleId"));
		
		UserRole urole= userroleservice.load(roleId);
		List<UserAuthority> listauth = userauthorityservice.listall();
		ArrayList<String> templistpermi=new ArrayList<String>();
		ArrayList<String> templistoppro=new ArrayList<String>();
		if(!StrLib.isEmpty(urole.getPermission())){
			String[] temppermi=urole.getPermission().split(",",-1);		
			for(int i=0;i<temppermi.length;i++){
				if(null==temppermi[i]||"".equals(temppermi[i])){
					continue;
				}				
				for(UserAuthority usth:listauth){
					if(temppermi[i].equals(usth.getAlias())){
						templistpermi.add(String.valueOf(usth.getId()));
					}
				}
			}
		}

		if(!StrLib.isEmpty(urole.getOpprojectid())){
			String[] tempoppro=urole.getOpprojectid().split(",",-1);
			for(int i=0;i<tempoppro.length;i++){
				if(null==tempoppro[i]||"".equals(tempoppro[i])){
					continue;
				}				
				templistoppro.add(tempoppro[i]);
			}
		}

		// 取集合
	    rsp.setContentType("text/xml;charset=utf-8");
		JSONArray jsonArraypermi = JSONArray.parseArray(JSON.toJSONString(templistpermi));
		JSONArray jsonArrayoppro = JSONArray.parseArray(JSON.toJSONString(templistoppro));
		JSONObject jsobjcet = new JSONObject();
		jsobjcet.put("permi", jsonArraypermi);
		jsobjcet.put("oppro", jsonArrayoppro); 
		
		rsp.getWriter().write(jsobjcet.toString());
		System.out.println(jsobjcet.toString());
	}
	
	/**
	 * 获取登录用户基本信息
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getlogininfo.do", method = RequestMethod.GET)
	public String getloginuserinfo(Model model,HttpServletRequest req) throws Exception
	{
		String usercode="";
		if(req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE)!=null&&req.getSession().getAttribute(PublicConst.SESSIONKEYUSERNAME)!=null){
			usercode = req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE).toString();
		}else{
			model.addAttribute("message", "获取用户信息失败！");
			model.addAttribute("url", "../index.jsp");
			return "error";
		}

		List<UserAuthority> listauth = userauthorityservice.listall();
		try
		{		
			UserInfo userinfo = userinfoservice.getUseinfo(usercode);
			
			List<UserRole> rolelist = userroleservice.listall();
			String[] temp=userinfo.getRole().split(";",-1);
			List<String[]> list = new ArrayList<String[]>();
			UserRole[] urarr = new UserRole[temp.length];
			
			for(int i=0;i<temp.length;i++){
				String[] rolearr = new String[2];
				if(null==temp[i]||"".equals(temp[i])){
					continue;
				}
				for(int j=0;j<rolelist.size();j++){
					if(Integer.valueOf(temp[i])==rolelist.get(j).getId()){
						rolearr[0] = String.valueOf(rolelist.get(j).getId());
						rolearr[1] = rolelist.get(j).getRole();
						urarr[i] = rolelist.get(j);
						break;
					}
				}
				list.add(i, rolearr);
			}
			String userauth = UserLoginController.getroleauth(urarr,listauth);
			model.addAttribute("rolearr", list);
			model.addAttribute("userauth", userauth);
			model.addAttribute("userinfo", userinfo);
			return "/jsp/user/user_info";
		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/");
			return "error";
		}

	}
	
	public static void main(String[] args) throws Exception {
	}
	
}
