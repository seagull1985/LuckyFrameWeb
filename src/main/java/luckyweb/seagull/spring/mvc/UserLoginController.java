package luckyweb.seagull.spring.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import luckyweb.seagull.spring.entity.UserAuthority;
import luckyweb.seagull.spring.entity.UserInfo;
import luckyweb.seagull.spring.entity.UserRole;
import luckyweb.seagull.spring.service.UserInfoService;
import luckyweb.seagull.spring.service.UserRoleService;
import luckyweb.seagull.util.Endecrypt;


@Controller
@RequestMapping("/userlogin")
public class UserLoginController {	
	@Resource(name = "userinfoService")
	private UserInfoService userinfoservice;
	
	@Resource(name = "userroleService")
	private UserRoleService userroleservice;

	public UserInfoService getuserinfoService() {
		return userinfoservice;
	}
	
	/**
	 * 登录校验用户名密码
	 * @throws Exception 
	 * @Description:
	 */
	@RequestMapping(value = "/userlogin.do")
	public void userlogin(HttpServletRequest req, HttpServletResponse rsp) throws Exception{
		Endecrypt endecrypt = new Endecrypt();
		String	usercode = req.getParameter("username");
		String  password = req.getParameter("password");
		password = endecrypt.get3DESEncrypt(password);
		
		int id = userinfoservice.getid(usercode, password);
		
		String[] result = new String[3];
		if(id!=0){
			String auth = "";
			String oppid = "";
			UserInfo userinfo = userinfoservice.load(id);
			String temp[]=userinfo.getRole().split(";",-1);
			for(int i=0;i<temp.length;i++){
				if(null==temp[i]||"".equals(temp[i])){
					continue;
				}				
				UserRole userrole = userroleservice.load(Integer.valueOf(temp[i]));
				auth = auth+userrole.getPermission();
				oppid = oppid+userrole.getOpprojectid();
			}
			
			req.getSession().setAttribute("permission", auth);
			req.getSession().setAttribute("oppid", oppid);
			req.getSession().setAttribute("usercode", userinfo.getUsercode());
			req.getSession().setAttribute("username", userinfo.getUsername());

			result[0] = "true";
			result[1] = userinfo.getUsercode();
			result[2] = userinfo.getUsername();
		}else{
			result[0] = "false";
			result[1] = "用户名或是密码错误！";
		}


	    rsp.setContentType("text/xml;charset=utf-8");
		JSONArray jsonArray = JSONArray.fromObject(result);
		JSONObject jsobjcet = new JSONObject();
		jsobjcet.put("data", jsonArray); 
		
		rsp.getWriter().write(jsobjcet.toString());
	}
	
	
	/**
	 * 注销用户
	 * @throws IOException 
	 * @Description:
	 */
	@RequestMapping(value = "/userlogout.do")
	public void userlogout(HttpServletRequest req, HttpServletResponse rsp) throws IOException{	    
		String[] result = new String[2];
		req.getSession().setAttribute("usercode", null);
		req.getSession().setAttribute("username", null);
		req.getSession().setAttribute("permission", null);
		req.getSession().setAttribute("oppid", null);
		
		result[0] = "true";		
		
	    rsp.setContentType("text/xml;charset=utf-8");
		JSONArray jsonArray = JSONArray.fromObject(result);
		JSONObject jsobjcet = new JSONObject();
		jsobjcet.put("data", jsonArray); 
		
		rsp.getWriter().write(jsobjcet.toString());
	}

	/**
	 * 判断是否已经登录
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/loginboolean.do")
	public void loginboolean(HttpServletRequest req, HttpServletResponse rsp) throws IOException, InterruptedException{
		String[] status = new String[3];
		if(req.getSession().getAttribute("usercode")!=null&&req.getSession().getAttribute("username")!=null){
			status[0] = "login";
			status[1] = req.getSession().getAttribute("usercode").toString();
			status[2] = req.getSession().getAttribute("username").toString();
		}else{
			status[0] = "logout";
			status[1] = "未登录";
			status[2] = "未登录";
		}
		
		rsp.setContentType("text/xml;charset=utf-8");
		JSONArray jsonArray = JSONArray.fromObject(status);
		JSONObject jsobjcet = new JSONObject();
		jsobjcet.put("data", jsonArray); 
		
		rsp.getWriter().write(jsobjcet.toString());
	}
	
	
	/**
	 * 判断用户权限范围，后台判断
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws Exception
	 * @Description:
	 */
	public static boolean permissionboolean(HttpServletRequest req,String opr) throws IOException, InterruptedException{
		boolean result;
		
		if(req.getSession().getAttribute("permission")!=null){
			if(req.getSession().getAttribute("permission").toString().indexOf(","+opr+",")>=0){
				result = true;
			}else{
				result = false;
			}
		}else{
			result = false;
		}

		return result;
	}
	
	public static boolean oppidboolean(HttpServletRequest req, int projectids) {
		boolean result = false;

		if (req.getSession().getAttribute("oppid") != null) {
			if (req.getSession().getAttribute("oppid").toString().indexOf(","+projectids + ",") >= 0) {
				result = true;
			} else {
				result = false;
			}
		} else {
			result = false;
		}

		return result;
	}
	
	/**
	 * 角色权限查询
	 * @param tj
	 * @param br
	 * @param model
	 * @param req
	 * @param rsp
	 * @return auth
	 * @throws Exception
	 * @Description:
	 */
	public static String getroleauth(UserRole[] rolearr,List<UserAuthority> listauth) throws Exception{
		List<UserAuthority> newlistauth = new ArrayList<UserAuthority>();
		
		for (UserRole role : rolearr) {
			if(null==role){
				continue;
			}
			String temp[] = role.getPermission().split(",", -1);
			authlist:for (int i = 0; i < temp.length; i++) {
				if (null == temp[i] || "".equals(temp[i])) {
					continue;
				}
				for (UserAuthority usth : listauth) {
					if (temp[i].equals(usth.getAlias())) {
						for (UserAuthority auth : newlistauth) {
							if(usth.getId()==auth.getId()){
								continue authlist;
							}
						}
						newlistauth.add(usth);
					}
				}
			}
		}
		
		String authstr="";
		for (UserAuthority auth : newlistauth) {
			authstr = authstr+auth.getModule()+"_"+auth.getAuth_type()+";&nbsp;&nbsp;&nbsp;&nbsp;";
		}
		
		return authstr;
	}
	
	/**
	 * 判断用户权限范围，前台判断
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/permissionboolean.do")
	public void permissionboolean_jason(HttpServletRequest req, HttpServletResponse rsp) throws IOException, InterruptedException{
		String[] status = new String[2];
		String	opr = req.getParameter("permissioncode");
		
		if(req.getSession().getAttribute("permission")!=null){
			if(req.getSession().getAttribute("permission").toString().indexOf(","+opr+",")>=0){
				status[0] = "true";
			}else{
				status[0]  = "false";
			}
		}else{
			status[0]  = "false";
		}
		
		rsp.setContentType("text/xml;charset=utf-8");
		JSONArray jsonArray = JSONArray.fromObject(status);
		JSONObject jsobjcet = new JSONObject();
		jsobjcet.put("data", jsonArray); 
		
		rsp.getWriter().write(jsobjcet.toString());
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
	}

}
