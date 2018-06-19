package luckyweb.seagull.spring.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.spring.entity.UserAuthority;
import luckyweb.seagull.spring.entity.UserInfo;
import luckyweb.seagull.spring.entity.UserRole;
import luckyweb.seagull.spring.service.UserInfoService;
import luckyweb.seagull.spring.service.UserRoleService;
import luckyweb.seagull.util.Endecrypt;

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
			String[] temp=userinfo.getRole().split(";",-1);
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


		rsp.setContentType("application/json");
		rsp.setCharacterEncoding("utf-8");
		JSONArray jsonArray =  (JSONArray)JSONArray.toJSON(result);
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
		
		rsp.setContentType("application/json");
		rsp.setCharacterEncoding("utf-8");
		JSONArray jsonArray =  (JSONArray)JSONArray.toJSON(result);
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
		if(req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE)!=null&&req.getSession().getAttribute(PublicConst.SESSIONKEYUSERNAME)!=null){
			status[0] = "login";
			status[1] = req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE).toString();
			status[2] = req.getSession().getAttribute(PublicConst.SESSIONKEYUSERNAME).toString();
		}else{
			status[0] = "logout";
			status[1] = "未登录";
			status[2] = "未登录";
		}
		
		rsp.setContentType("application/json");
		rsp.setCharacterEncoding("utf-8");
		JSONArray jsonArray =  (JSONArray)JSONArray.toJSON(status);
		JSONObject jsobjcet = new JSONObject();
		jsobjcet.put("data", jsonArray); 
		
		rsp.getWriter().write(jsobjcet.toString());
	}
	
	
	/**
	 * 判断用户操作权限范围，后台判断
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws Exception
	 * @Description:
	 */
	public static boolean permissionboolean(HttpServletRequest req,String opr) throws IOException, InterruptedException{
		boolean result;
		String fgf=",";
		if(req.getSession().getAttribute(PublicConst.SESSIONKEYPERMISSION)!=null){
			if(req.getSession().getAttribute(PublicConst.SESSIONKEYPERMISSION).toString().indexOf(fgf+opr+fgf)>=0){
				result = true;
			}else if("admin".equals(req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE).toString())){
				result = true;
			}else{
				result = false;
			}
		}else{
			result = false;
		}

		return result;
	}
	
	/**
	 * 判断用户项目权限范围，后台判断
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws Exception
	 * @Description:
	 */
	public static boolean oppidboolean(HttpServletRequest req, int projectids) {
		boolean result = false;
		String fgf=",";
		if (req.getSession().getAttribute(PublicConst.SESSIONKEYPROJECTID) != null) {
			if (req.getSession().getAttribute(PublicConst.SESSIONKEYPROJECTID).toString().indexOf(fgf+projectids + fgf) >= 0) {
				result = true;
			}else if("admin".equals(req.getSession().getAttribute(PublicConst.SESSIONKEYUSERCODE).toString())){
				result = true;
			}else {
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
			String[] temp = role.getPermission().split(",", -1);
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
		
		
		StringBuilder stringBuilder = new StringBuilder();
		for (UserAuthority auth : newlistauth) {
			stringBuilder.append(auth.getModule()+"_"+auth.getAuth_type()+";&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return stringBuilder.toString();
	}
	
	/**
	 * 判断用户权限范围，前台判断
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/permissionboolean.do")
	public void permissionBooleanJson(HttpServletRequest req, HttpServletResponse rsp) throws IOException, InterruptedException{
		String[] status = new String[2];
		String	opr = req.getParameter("permissioncode");
		String fgf=",";
		if(req.getSession().getAttribute(PublicConst.SESSIONKEYPERMISSION)!=null){
			if(req.getSession().getAttribute(PublicConst.SESSIONKEYPERMISSION).toString().indexOf(fgf+opr+fgf)>=0){
				status[0] = "true";
			}else{
				status[0]  = "false";
			}
		}else{
			status[0]  = "false";
		}
		
		rsp.setContentType("application/json");
		rsp.setCharacterEncoding("utf-8");
		JSONArray jsonArray =  (JSONArray)JSONArray.toJSON(status);
		JSONObject jsobjcet = new JSONObject();
		jsobjcet.put("data", jsonArray); 
		
		rsp.getWriter().write(jsobjcet.toString());
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
