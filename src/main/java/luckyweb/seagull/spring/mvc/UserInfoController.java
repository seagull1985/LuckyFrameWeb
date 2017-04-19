package luckyweb.seagull.spring.mvc;

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

import luckyweb.seagull.spring.entity.SecondarySector;
import luckyweb.seagull.spring.entity.UserAuthority;
import luckyweb.seagull.spring.entity.UserInfo;
import luckyweb.seagull.spring.entity.UserRole;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.SecondarySectorService;
import luckyweb.seagull.spring.service.UserAuthorityService;
import luckyweb.seagull.spring.service.UserInfoService;
import luckyweb.seagull.spring.service.UserRoleService;
import luckyweb.seagull.util.Endecrypt;
import luckyweb.seagull.util.StrLib;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
@RequestMapping("/userInfo")
public class UserInfoController {
	
	private int allPage;
	private int pageSize = 20;
	private int allRows;
	private int page = 1;
	private int offset;
	
	@Resource(name = "userinfoService")
	private UserInfoService userinfoservice;
	
	@Resource(name = "userroleService")
	private UserRoleService userroleservice;
	
	@Resource(name = "userauthorityService")
	private UserAuthorityService userauthorityservice;
	
	@Resource(name = "secondarysectorService")
	private SecondarySectorService secondarysectorService;
	
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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list.do")
	public String list(HttpServletRequest req, UserInfo userinfo, Model model)
			throws Exception {
		model.addAttribute("userinfo", userinfo);
		
		if(!UserLoginController.permissionboolean(req, "ui_4")){
			model.addAttribute("userinfo", new UserInfo());
			model.addAttribute("url", "/index.jsp");
			model.addAttribute("message", "您无权限使用用户管理功能，请联系管理员！");
			return "success";
		}

		try {
			String p = req.getParameter("page");

			if (StrLib.isEmpty(p) || Integer.valueOf(p) == 0) {
				page = 1;
			}

			String page2 = req.getParameter("page");
			if (StrLib.isEmpty(page2)) {
				page = 1;
			} else {
				try {
					page = Integer.parseInt(page2);
				} catch (Exception e) {
					page = 1;
				}
			}
			allRows = userinfoservice.findRows(userinfo);
			offset = (page - 1) * pageSize;
			if (allRows % pageSize == 0) {
				allPage = allRows / pageSize;
			} else {
				allPage = allRows / pageSize + 1;
			}

			model.addAttribute("allRows", allRows);
			model.addAttribute("page", page);
			model.addAttribute("offset", offset);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("allPage", allPage);
			List<UserRole> roleMap = userroleservice.listall();
			List<UserInfo> sssMap = userinfoservice.findByPage(userinfo, offset, pageSize);
			for(int i=0;i<sssMap.size();i++){
				String role="";
				String temp[]=sssMap.get(i).getRole().split(";",-1);
				for(int k=0;k<temp.length;k++){
					if(null==temp[k]||"".equals(temp[k])){
						continue;
					}
					for(int j=0;j<roleMap.size();j++){
						if(Integer.valueOf(temp[k])==roleMap.get(j).getId()){
							role = role+roleMap.get(j).getRole()+";";
							break;
						}
					}					
				}
				sssMap.get(i).setRole(role);
			}

			model.addAttribute("splist", sssMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/review/list.do");
			return "error";
		}
		return "/jsp/user/user";
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
			
			if(!UserLoginController.permissionboolean(req, "ui_1")){
				model.addAttribute("userinfo", new UserInfo());
				model.addAttribute("url", "/userInfo/list.do");
				model.addAttribute("message", "当前用户无权限添加用户，请联系管理员！");
				return "success";
			}

			String retVal = "/jsp/user/user_add";
			if (req.getMethod().equals("POST"))
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
					model.addAttribute("message", message);
					return retVal;
				}
				if(StrLib.isEmpty(userinfo.getUsercode())){
					message = "用户名不能为空！";
					model.addAttribute("userrole", userroleservice.listall());
				    model.addAttribute("secondarysector", secondarysectorService.listall());
					model.addAttribute("message", message);
					return retVal;
				}
				if(StrLib.isEmpty(userinfo.getSecondarySector().getDepartmentname())||"0".equals(userinfo.getSecondarySector().getDepartmentname())){
					message = "请至少选择一个所属部门！";
					model.addAttribute("userrole", userroleservice.listall());
				    model.addAttribute("secondarysector", secondarysectorService.listall());
					model.addAttribute("message", message);
					return retVal;
				}
				if(StrLib.isEmpty(userinfo.getPassword())){
					message = "密码不能为空！";
					model.addAttribute("userrole", userroleservice.listall());
				    model.addAttribute("secondarysector", secondarysectorService.listall());
					model.addAttribute("message", message);
					return retVal;
				}
				
				String temp[] = userinfo.getPassword().split(",",-1);
				if(!temp[0].equals(temp[1])){
					message = "两次输入的密码不一致，请确认！";
					model.addAttribute("userrole", userroleservice.listall());
				    model.addAttribute("secondarysector", secondarysectorService.listall());
					model.addAttribute("message", message);
					return retVal;
				}
				
				if(userinfoservice.findUsecode(userinfo.getUsercode())>0){
					message = "用户名已经存在，请重新输入！";
					model.addAttribute("userrole", userroleservice.listall());
				    model.addAttribute("secondarysector", secondarysectorService.listall());
					model.addAttribute("message", message);
					return retVal;
				}
				
				Endecrypt endecrypt = new Endecrypt();
				String cryptpwd = endecrypt.get3DESEncrypt(temp[0]);
				userinfo.setPassword(cryptpwd);
				userinfo.setRole(userinfo.getRole().replaceAll(",", ";"));
				
				SecondarySector ss = secondarysectorService.load(Integer.valueOf(userinfo.getSecondarySector().getDepartmentname()));
				userinfo.setSectorid(ss.getSectorid());
				userinfo.setSecondarySector(ss);
				
				int userid = userinfoservice.add(userinfo);
				
				operationlogservice.add(req, "USERINFO", userid, 
						99,"用户添加成功！用户名："+userinfo.getUsercode());
				
				model.addAttribute("message", "创建用户成功");
				model.addAttribute("url", "/userInfo/list.do");
				return "success";

			}
			    model.addAttribute("userrole", userroleservice.listall());
			    model.addAttribute("secondarysector", secondarysectorService.listall());
				model.addAttribute("userinfo", userinfo);
				return retVal;

		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/userInfo/list.do");
			return "error";
		}

	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete.do", method = RequestMethod.GET)
	public String delete(Model model,HttpServletRequest req) throws Exception
	{
		if(!UserLoginController.permissionboolean(req, "ui_2")){
			model.addAttribute("userinfo", new UserInfo());
			model.addAttribute("url", "/userInfo/list.do");
			model.addAttribute("message", "当前用户无权限删除用户，请联系管理员！");
			return "success";
		}
		
		int id = Integer.valueOf(req.getParameter("id"));
		UserInfo userinfo = userinfoservice.load(id);
		try
		{		
			userinfoservice.delete(id);
		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/userInfo/list.do");
			return "error";
		}
		
		operationlogservice.add(req, "USERINFO", id, 
				99,"用户信息删除成功！用户名："+userinfo.getUsercode());

		String message = "删除用户成功！";
		model.addAttribute("userinfo", new UserInfo());
		model.addAttribute("message", message);
		model.addAttribute("url", "/userInfo/list.do");
		return "success";
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
		
		if(!UserLoginController.permissionboolean(req, "ui_3")){
			model.addAttribute("userinfo", new UserInfo());
			model.addAttribute("url", "/userInfo/list.do");
			model.addAttribute("message", "当前用户无权限修改用户信息，请联系管理员！");
			return "success";
		}
		Endecrypt endecrypt = new Endecrypt();
		try{
		String retVal = "/jsp/user/user_update";
		if (req.getMethod().equals("POST"))
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
				model.addAttribute("message", message);
				return retVal;
			}
			if(StrLib.isEmpty(userinfo.getUsercode())){
				message = "用户名不能为空！";
				model.addAttribute("userrole", userroleservice.listall());
			    model.addAttribute("secondarysector", secondarysectorService.listall());
				model.addAttribute("message", message);
				return retVal;
			}
			if(StrLib.isEmpty(userinfo.getSecondarySector().getDepartmentname())||"0".equals(userinfo.getSecondarySector().getDepartmentname())){
				message = "请至少选择一个所属部门！";
				model.addAttribute("userrole", userroleservice.listall());
			    model.addAttribute("secondarysector", secondarysectorService.listall());
				model.addAttribute("message", message);
				return retVal;
			}
			if(StrLib.isEmpty(userinfo.getPassword())){
				message = "密码不能为空！";
				model.addAttribute("userrole", userroleservice.listall());
			    model.addAttribute("secondarysector", secondarysectorService.listall());
				model.addAttribute("message", message);
				return retVal;
			}
			String temp[] = userinfo.getPassword().split(",",-1);
			if(!temp[0].equals(temp[1])){
				message = "两次输入的密码不一致，请确认！";
				model.addAttribute("userrole", userroleservice.listall());
			    model.addAttribute("secondarysector", secondarysectorService.listall());
				model.addAttribute("message", message);
				return retVal;
			}
			
			String cryptpwd = endecrypt.get3DESEncrypt(temp[0]);
			userinfo.setPassword(cryptpwd);
			userinfo.setRole(userinfo.getRole().replaceAll(",", ";"));
			
			SecondarySector ss = secondarysectorService.load(Integer.valueOf(userinfo.getSecondarySector().getDepartmentname()));
			userinfo.setSectorid(ss.getSectorid());
			userinfo.setSecondarySector(ss);
			
			userinfoservice.modify(userinfo);
			
			operationlogservice.add(req, "USERINFO", id, 
					99,"用户信息修改成功！用户名"+userinfo.getUsercode());

			
			model.addAttribute("message", "修改用户信息成功");
			model.addAttribute("url", "/userInfo/list.do");
			return "success";

		}	
		userinfo = userinfoservice.load(id);
		userinfo.setPassword(endecrypt.get3DESDecrypt(userinfo.getPassword()));
		userinfo.setSectorid(userinfo.getSecondarySector().getSectorid());
		userinfo.setRole(userinfo.getRole().replaceAll(";", ","));
		
	    model.addAttribute("userrole", userroleservice.listall());
	    model.addAttribute("secondarysector", secondarysectorService.listall());
		model.addAttribute("userinfo", userinfo);
		return "/jsp/user/user_update";

	}
	catch (Exception e){
		model.addAttribute("message", e.getMessage());
		model.addAttribute("url", "/userInfo/list.do");
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
		if(req.getSession().getAttribute("usercode")!=null&&req.getSession().getAttribute("username")!=null){
			usercode = req.getSession().getAttribute("usercode").toString();
		}else{
			model.addAttribute("message", "获取用户信息失败！");
			model.addAttribute("url", "../index.jsp");
			return "error";
		}
		
		Endecrypt endecrypt = new Endecrypt();
		try{
		String retVal = "/jsp/user/user_pswupdate";
		if (req.getMethod().equals("POST"))
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
			String temp[] = userinfo.getPassword().split(",",-1);
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
			model.addAttribute("url", "/progressus/signin.html");
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

			if(!UserLoginController.permissionboolean(req, "role_3")){
				model.addAttribute("userinfo", new UserInfo());
				model.addAttribute("url", "/userInfo/list.do");
				model.addAttribute("message", "当前用户无权限查看角色权限信息，请联系管理员！");
				return "success";
			}
			
			String retVal = "/jsp/user/role";
			if (req.getMethod().equals("POST"))
			{
				if (br.hasErrors())
				{
					return retVal;
				}
				String message = "";
				if(StrLib.isEmpty(userrole.getRole())||"0".equals(userrole.getRole())){
					message = "请至少选择一个角色保存";
				    model.addAttribute("rolelist", userroleservice.listall());
				    model.addAttribute("authoritylist", userauthorityservice.listall());
					model.addAttribute("userrole", userrole);
					model.addAttribute("message", message);
					return retVal;
				}
				
				if(StrLib.isEmpty(userrole.getPermission())){
					message = "请至少选择一个权限项保存";
				    model.addAttribute("rolelist", userroleservice.listall());
				    model.addAttribute("authoritylist", userauthorityservice.listall());
					model.addAttribute("userrole", userrole);
					model.addAttribute("message", message);
					return retVal;
				}
				
				UserRole urole = userroleservice.load(Integer.valueOf(userrole.getRole()));
				userrole.setId(urole.getId());
				userrole.setRole(urole.getRole());
				userrole.setPermission(userrole.getPermission()+",");
				
				userroleservice.modify(userrole);
				
				operationlogservice.add(req, "USER_ROLE", userrole.getId(), 
						99,"角色权限修改成功！角色名称："+userrole.getRole());
				
				model.addAttribute("message", "修改角色权限成功");
				model.addAttribute("url", "/userInfo/role.do");
				return "success";

			}
			    model.addAttribute("rolelist", userroleservice.listall());
			    model.addAttribute("authoritylist", userauthorityservice.listall());
				model.addAttribute("userrole", userrole);
				return retVal;

		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/userInfo/list.do");
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

			if(!UserLoginController.permissionboolean(req, "role_1")){
				model.addAttribute("userinfo", new UserInfo());
				model.addAttribute("url", "/userInfo/list.do");
				model.addAttribute("message", "当前用户无权限添加角色权限信息，请联系管理员！");
				return "success";
			}
			
			String retVal = "/jsp/user/role_add";
			if (req.getMethod().equals("POST"))
			{
				if (br.hasErrors())
				{
					return retVal;
				}
				String message = "";
				if(StrLib.isEmpty(userrole.getRole())||"0".equals(userrole.getRole())){
					message = "请至少选择一个角色保存";
				    model.addAttribute("rolelist", userroleservice.listall());
				    model.addAttribute("authoritylist", userauthorityservice.listall());
					model.addAttribute("userrole", userrole);
					model.addAttribute("message", message);
					return retVal;
				}
				
				if(StrLib.isEmpty(userrole.getPermission())){
					message = "请至少选择一个权限项保存";
				    model.addAttribute("rolelist", userroleservice.listall());
				    model.addAttribute("authoritylist", userauthorityservice.listall());
					model.addAttribute("userrole", userrole);
					model.addAttribute("message", message);
					return retVal;
				}
				
				userrole.setPermission(userrole.getPermission()+",");
				
				userroleservice.add(userrole);
				
				operationlogservice.add(req, "USER_ROLE", userrole.getId(), 
						99,"角色权限添加成功！角色名称："+userrole.getRole());
				
				model.addAttribute("message", "添加角色权限成功");
				model.addAttribute("url", "/userInfo/role.do");
				return "success";

			}
			    model.addAttribute("authoritylist", userauthorityservice.listall());
				model.addAttribute("userrole", userrole);
				return retVal;

		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/userInfo/list.do");
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
		if(!UserLoginController.permissionboolean(req, "role_2")){
			model.addAttribute("userinfo", new UserInfo());
			model.addAttribute("url", "/userInfo/list.do");
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
			model.addAttribute("url", "/userInfo/list.do");
			return "error";
		}
		
		operationlogservice.add(req, "USER_ROLE", id, 
				99,"角色信息删除成功！角色名称："+userrole.getRole());

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
		ArrayList<String> templist=new ArrayList<String>();
		
		String temp[]=urole.getPermission().split(",",-1);
		for(int i=0;i<temp.length;i++){
			if(null==temp[i]||"".equals(temp[i])){
				continue;
			}				
			for(UserAuthority usth:listauth){
				if(temp[i].equals(usth.getAlias())){
					templist.add(String.valueOf(usth.getId()));
				}
			}
		}
		// 取集合
	    rsp.setContentType("text/xml;charset=utf-8");
		JSONArray jsonArray = JSONArray.fromObject(templist);
		JSONObject jsobjcet = new JSONObject();
		jsobjcet.put("data", jsonArray); 
		
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
		if(req.getSession().getAttribute("usercode")!=null&&req.getSession().getAttribute("username")!=null){
			usercode = req.getSession().getAttribute("usercode").toString();
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
			String temp[]=userinfo.getRole().split(";",-1);
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
