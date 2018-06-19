package luckyweb.seagull.spring.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
import luckyweb.seagull.quartz.QuartzManager;
import luckyweb.seagull.quartz.QuratzJobDataMgr;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.entity.TestClient;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.spring.service.TestClientService;
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
@RequestMapping("/testClient")
public class TestClientController {
	
	@Resource(name = "testclientService")
	private TestClientService tcservice;
	
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
		model.addAttribute("projectlist", lsp);
		return "/jsp/base/testclient";
	}

	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/list.do")
	private void ajaxGetSellRecord(Integer limit, Integer offset, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		String search = request.getParameter("search");
		TestClient tc= new TestClient();
		if (null == offset && null == limit) {
			offset = 0;
		}
		if (null == limit || limit == 0) {
			limit = 10;
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(search)) {
			tc.setClientip(search);
			tc.setName(search);
		}
		List<TestClient> tcs = tcservice.findByPage(tc, offset, limit);
		for(TestClient tct:tcs){
			String[] projectids=tct.getProjectper().split(",");
			StringBuffer sbnames = new StringBuffer();
			for(String proid:projectids){
				if(!StrLib.isEmpty(proid)&&!"0".equals(proid)){
					SectorProjects sp = sectorprojectsService.loadob(Integer.valueOf(proid));
					String projectname=sp.getProjectname();
					if(sp.getProjecttype()==1){
						sbnames.append(projectname+"(TestLink),");
					}else{
						sbnames.append(projectname+",");
					}
				}
			}
			tct.setProjectpername(sbnames.toString());
		}
		// 转换成json字符串
		JSONArray recordJson = StrLib.listToJson(tcs);
		// 得到总记录数
		int total = tcservice.findRows(tc);
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
	public void add(TestClient tc, HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			TestClient tctest =tcservice.getClient(tc.getClientip());
			if(tc.getId()==0){
				if (!UserLoginController.permissionboolean(req, PublicConst.AUTHCLIENTADD)) {
					json.put("status", "fail");
					json.put("ms", "增加客户端失败,权限不足,请联系管理员!");
				} else if(null==tc.getProjectper()||"".equals(tc.getProjectper())){
					json.put("status", "fail");
					json.put("ms", "增加客户端失败,请至少选择一个项目添加至客户端中!");
				} else {
					tc.setProjectper(","+tc.getProjectper()+",");
					tc.setStatus(2);
					
					if(tctest==null){
						int tcid = tcservice.add(tc);
						tc.setId(tcid);
						
						//将客户端心跳监听加入到定时任务中
						QueueListener.listen_Clientlist.add(tc);
						QuratzJobDataMgr mgr = new QuratzJobDataMgr();
						mgr.addTestClient(tc, tcid);
						
						operationlogservice.add(req, "TestClient", tcid, 
								99,5,"客户端【"+tc.getName()+"】添加成功！");
						
						json.put("status", "success");
						json.put("ms", "添加客户端成功!");
					}else{
						json.put("status", "fail");
						json.put("ms", "添加失败，客户端IP重复啦");
					}

				}
			}else{
				if (!UserLoginController.permissionboolean(req, PublicConst.AUTHCLIENTMOD)) {
					json.put("status", "fail");
					json.put("ms", "编辑客户端失败,权限不足,请联系管理员!");
				} else if(null==tc.getProjectper()||"".equals(tc.getProjectper())){
					json.put("status", "fail");
					json.put("ms", "增加客户端失败,请至少选择一个项目添加至客户端中!");
				} else {
					TestClient tctemp=tcservice.load(tc.getId());
					if(tctest==null||tctemp.getClientip().equals(tc.getClientip())){
						tc.setProjectper(","+tc.getProjectper()+",");
						tc.setStatus(2);
						tcservice.modify(tc);
						TestClient tct=null;
						for (int i = 0; i < QueueListener.listen_Clientlist.size(); i++)
						{
							tct = new TestClient();
							tct = QueueListener.listen_Clientlist.get(i);
							if (tc.getId() == tct.getId())
							{
								QueueListener.listen_Clientlist.remove(tct);
								QueueListener.listen_Clientlist.add(tc);
								break;
							}
						}

						QuartzManager.modifyJobTime(tc.getId() + "*CLIENT", "0/"+tc.getCheckinterval()+" * * * * ?");
						
						operationlogservice.add(req, "TestClient", tc.getId(), 
								99,3,"客户端【"+tc.getName()+"】编辑成功！");
						
						json.put("status", "success");
						json.put("ms", "编辑客户端成功!");
					}else{
						json.put("status", "fail");
						json.put("ms", "编辑失败，客户端IP重复啦");
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
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHCLIENTDEL)) {
				json.put("status", "fail");
				json.put("ms", "删除客户端失败,权限不足,请联系管理员!");
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
				String ms="删除客户端失败!";
				for (int i = 0; i < jsonarr.size(); i++) {
					int id = Integer.valueOf(jsonarr.get(i).toString());
					TestClient tc = tcservice.load(id);
					
					tcservice.delete(tc);
					QuartzManager.removeJob(id + "*CLIENT");
					QueueListener.listen_Clientlist.remove(tc);
					
					operationlogservice.add(req, "TestClient", id, 
							99,1,"客户端删除成功！");
					status="success";
					ms="删除客户端成功!";
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
	 * 联动查询客户端列表
	 * @param tj
	 * @param br
	 * @param model
	 * @param req
	 * @param rsp
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/getclientlist.do")
	public void getclientlist(HttpServletRequest req, HttpServletResponse rsp) throws Exception{	    
		int	projectid = Integer.valueOf(req.getParameter("projectid"));

		List<TestClient> listc = tcservice.getClientListForProid(projectid);
		
		// 取集合
	    rsp.setContentType("text/xml;charset=utf-8");

		JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(listc));
		JSONObject jsobjcet = new JSONObject();
		jsobjcet.put("data", jsonArray);
		
		rsp.getWriter().write(jsobjcet.toString());
	}
	
	/**
	 * 联动查询客户端驱动路径列表
	 * @param tj
	 * @param br
	 * @param model
	 * @param req
	 * @param rsp
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/getclientpathlist.do")
	public void getclientpathlist(HttpServletRequest req, HttpServletResponse rsp) throws Exception{	    
		String	clientip = req.getParameter("clientip");

		TestClient tc=tcservice.getClient(clientip);
		ArrayList<String> pathlist=new ArrayList<String>();
		String path=tc.getClientpath();
		String[] temp=path.split(";",-1);
		for(String pathtemp:temp){
			if(!StrLib.isEmpty(pathtemp)){
				pathlist.add(pathtemp);
			}
		}
		
		// 取集合
	    rsp.setContentType("text/xml;charset=utf-8");

		JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(pathlist));
		JSONObject jsobjcet = new JSONObject();
		jsobjcet.put("data", jsonArray);
		
		rsp.getWriter().write(jsobjcet.toString());
	}
	
	public static void main(String[] args) throws Exception {
	}
	
}
