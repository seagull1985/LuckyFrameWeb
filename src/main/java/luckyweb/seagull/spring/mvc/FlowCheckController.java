package luckyweb.seagull.spring.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.spring.entity.Barchart4;
import luckyweb.seagull.spring.entity.FlowCheck;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.service.FlowCheckService;
import luckyweb.seagull.spring.service.FlowInfoService;
import luckyweb.seagull.spring.service.OperationLogService;
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
@RequestMapping("/flowCheck")
public class FlowCheckController {
	
	private int allPage;
	private int pageSize = 20;
	private int allRows;
	private int page = 1;
	private int offset;
	
	@Resource(name = "flowcheckService")
	private FlowCheckService flowcheckservice;
	
	@Resource(name = "sectorprojectsService")
	private SectorProjectsService sectorprojectsService;
	
	@Resource(name = "flowinfoService")
	private FlowInfoService flowinfoService;
	
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

			List<SectorProjects> prolist = QueueListener.qa_projlist;
			model.addAttribute("projects", prolist);
			model.addAttribute("projectid", projectid);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/flowCheck/load.do");
			return "error";
		}
		return "/jsp/flowcheck/flowcheck";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list.do")
	private void ajaxGetSellRecord(Integer limit, Integer offset, HttpServletRequest request,
			HttpServletResponse response) throws NumberFormatException, Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		PrintWriter pw = response.getWriter();
		String search = request.getParameter("search");
		String projectid = request.getParameter("projectid");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		FlowCheck flowcheck = new FlowCheck();
		if (null == offset && null == limit) {
			offset = 0;
		}
		if (null == limit || limit == 0) {
			limit = 10;
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(search)) {
			flowcheck.setVersionnum(search);
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(projectid)&&!PublicConst.STATUSSTR99.equals(projectid)) {
			flowcheck.setProjectid(Integer.valueOf(projectid));
		}
		
		if (!StrLib.isEmpty(startDate)) {
			flowcheck.setCheckstartdate(startDate);
		}
			
		if (!StrLib.isEmpty(endDate)) {
			flowcheck.setCheckenddate(endDate);
		}
		List<Object[]> sssMap = flowcheckservice.findByPage(flowcheck, offset, limit);
		JSONArray jsonArr = new JSONArray();
		for(int i=0;i<sssMap.size();i++){	
			Object[] project = (Object[])sectorprojectsService.load(Integer.valueOf(sssMap.get(i)[0].toString()));
			sssMap.get(i)[8] = project[1].toString();
			if(Integer.valueOf(sssMap.get(i)[4].toString())!=0&&Integer.valueOf(sssMap.get(i)[3].toString())!=0){
				sssMap.get(i)[7] = Double.valueOf(new DecimalFormat("#.00").format((Double.valueOf(sssMap.get(i)[4].toString())/Double.valueOf(sssMap.get(i)[3].toString()))*100));
			}else{
				sssMap.get(i)[7] = 0.00;
			}
			
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("projectid", sssMap.get(i)[0].toString());
			jsonobj.put("projectname", sssMap.get(i)[8].toString());
			jsonobj.put("versionnum", sssMap.get(i)[6].toString());
			jsonobj.put("checkid", sssMap.get(i)[1].toString());
			jsonobj.put("firstcheckdate", sssMap.get(i)[2].toString());
			jsonobj.put("checknum", sssMap.get(i)[3].toString());
			jsonobj.put("checksucnum", sssMap.get(i)[4].toString());
			jsonobj.put("checkunsucnum", sssMap.get(i)[5].toString());
			jsonobj.put("unchecknum", sssMap.get(i)[9].toString());
			jsonobj.put("persuc", sssMap.get(i)[7].toString()+"%");
			
			jsonArr.add(jsonobj);
		}

		// 得到总记录数
		int total = flowcheckservice.findRows(flowcheck);
		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", jsonArr);
		pw.print(json.toString());
	}

	/**
	 * 
	 * 
	 * @param tj
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loadinfo.do")
	public String loadinfo(HttpServletRequest req, Model model) throws Exception {
		try {
			int projectid = Integer.valueOf(req.getParameter("projectid"));
			int checkid = Integer.valueOf(req.getParameter("checkid"));
			String versionnum = req.getParameter("versionnum");
			String projectname="";
			if(projectid!=0){
				SectorProjects project = sectorprojectsService.loadob(projectid);
				projectname = project.getProjectname();
			}
			@SuppressWarnings("unchecked")
			List<Object[]> phaselist = flowinfoService.listphaseinfo();
			model.addAttribute("phaselist", phaselist);
			model.addAttribute("projectname", projectname);
			model.addAttribute("projectid", projectid);
			model.addAttribute("checkid", checkid);
			model.addAttribute("versionnum", versionnum);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/flowCheck/load.do");
			return "error";
		}
		return "/jsp/flowcheck/flowcheckinfo";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/listinfo.do")
	private void ajaxGetSellRecordinfo(Integer limit, Integer offset, HttpServletRequest request,
			HttpServletResponse response) throws NumberFormatException, Exception {
		response.setCharacterEncoding("utf-8");
		int projectid = Integer.valueOf(request.getParameter("projectid"));
		int checkid = Integer.valueOf(request.getParameter("checkid"));
		String versionnum = request.getParameter("versionnum");
		PrintWriter pw = response.getWriter();
		FlowCheck flowcheck = new FlowCheck();
		if (null == offset && null == limit) {
			offset = 0;
		}
		if (null == limit || limit == 0) {
			limit = 10;
		}

		flowcheck.setCheckid(checkid);
		flowcheck.setProjectid(projectid);
		flowcheck.setVersionnum(versionnum);
		List<FlowCheck> fclist = flowcheckservice.findByPageTable(flowcheck, offset,limit);
		
		//取信息表中的中文名，放到MAP，从页面通过key value取出
		List<Object[]> allphase = flowinfoService.listphaseallinfo();
		List<Object[]> allnode = flowinfoService.listnodeallinfo();
		List<Object[]> allentry = flowinfoService.listentryallinfo();
		
		for(int i=0;i<fclist.size();i++){
			FlowCheck fc = fclist.get(i);
			
			for(Object[] obph : allphase){
				   if(fc.getProjectphase().equals(obph[0].toString())){
					   fc.setProjectphase(obph[1].toString());
				   }
				  }  
			
			for(Object[] obno : allnode){  
				   if(fc.getPhasenode().equals(obno[0].toString())){
					   fc.setPhasenode(obno[1].toString());
				   } 
				  } 
			
			for(Object[] oben : allentry){    
				   if(fc.getCheckentry().equals(oben[0].toString())){
					   fc.setCheckentry(oben[1].toString());
				   }
				  }    //结束
			
			
			fclist.set(i, fc);
		}
		// 转换成json字符串
		JSONArray recordJson = StrLib.listToJson(fclist);
		// 得到总记录数
		int total = flowcheckservice.findRowsTable(flowcheck);
		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", recordJson);
		pw.print(json.toString());
	}
	
	/**
	 * 检查明细添加
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
	@RequestMapping(value = "/adddetail.do")
	public void adddetail(FlowCheck flowcheck, HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHFCADD)) {
				json.put("status", "fail");
				json.put("ms", "添加失败,权限不足,请联系管理员!");
			} else {
				if(!flowcheckservice.determinerecord(flowcheck.getProjectid(), flowcheck.getCheckid(), flowcheck.getCheckentry())){
					json.put("status", "fail");
					json.put("ms", "您所添加的数据在本次检查集中已存在!");
				}else{
					if(!UserLoginController.oppidboolean(req, flowcheck.getProjectid())){
						json.put("status", "fail");
						json.put("ms", "您没有权限为当前项目添加流程检查项！");
					}else{
						// 定义空格回车换行符
						String regExSpace = "\t|\r|\n";
						Pattern pSpace = Pattern.compile(regExSpace, Pattern.CASE_INSENSITIVE);
						Matcher mSpace = pSpace.matcher(flowcheck.getRemark());
						// 过滤空格回车标签
						flowcheck.setRemark(mSpace.replaceAll("")); 

						SectorProjects p = new SectorProjects();
						p.setProjectid(flowcheck.getProjectid());
						flowcheck.setSectorProjects(p);

						int id = flowcheckservice.add(flowcheck);
						String checkentry = flowinfoService.load(Integer.valueOf(flowcheck.getCheckentry())).getCheckentry();
						operationlogservice.add(req, "QA_FLOWCHECK", id, 
								flowcheck.getProjectid(),1,"流程检查明细信息添加成功！检查结果："+flowcheck.getCheckresult()+" 检查内容："+checkentry);

						json.put("status", "success");
						json.put("ms", "添加检查明细成功！");
					}

				}
				
			}
			pw.print(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * 补充剩余明细添加
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
	@RequestMapping(value = "/supplementdetail.do", method = RequestMethod.GET)
	public String supplementdetail(HttpServletRequest req, HttpServletResponse rsp, Model model) throws Exception {
		int projectid = Integer.valueOf(req.getParameter("projectid"));
		int checkid = Integer.valueOf(req.getParameter("checkid"));
		String version = req.getParameter("version");
		String idstr = req.getParameter("idstr");

		try {
			String[] temp=idstr.split("\\|");
			
		    String tempDateStr="";   
		    Date dt = new Date();   
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
		    tempDateStr=sdf.format(dt);
			for (int i = 0; i < temp.length; i++) {
				FlowCheck flowcheck = new FlowCheck();

				flowcheck.setCheckid(checkid);
				flowcheck.setVersionnum(version);
				flowcheck.setProjectphase(temp[i]);
				flowcheck.setPhasenode(temp[i]);
				flowcheck.setCheckentry(temp[i]);
				flowcheck.setCheckresult("未检查");
				flowcheck.setCheckdate(tempDateStr);
				flowcheck.setCheckdescriptions("");
				flowcheck.setStateupdate("");
				flowcheck.setUpdatedate("");
				flowcheck.setRemark("自动生成，请修改");
				SectorProjects p = new SectorProjects();
				p.setProjectid(projectid);
				flowcheck.setSectorProjects(p);

				flowcheckservice.add(flowcheck);
			}
			operationlogservice.add(req, "QA_FLOWCHECK", checkid, projectid,2,
					"批量自动生成流程检查明细信息成功！");

			model.addAttribute("message", "批量添加成功");
			model.addAttribute("url", "/flowCheck/loadinfo.do?projectid="+projectid+"&checkid="+checkid+"&version="+version);
			return "success";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/flowCheck/load.do");
			return "error";
		}
	}
	
	
	/**
	 * 检查信息添加
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
	public String add(@Valid @ModelAttribute("flowcheck") FlowCheck flowcheck, BindingResult br, Model model,
	        HttpServletRequest req, HttpServletResponse rsp) throws Exception
	{
		
		try
		{
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");

			if(!UserLoginController.permissionboolean(req, PublicConst.AUTHFCADD)){
				model.addAttribute("flowcheck", new FlowCheck());
				model.addAttribute("url", "load.do");
				model.addAttribute("message", "当前用户无权限添加项目检查信息，请联系管理员！");
				return "success";
			}
			
			String retVal = "/jsp/flowcheck/flowcheck_add";
			if (PublicConst.REQPOSTTYPE.equals(req.getMethod()))
			{
				if (br.hasErrors())
				{
					List<FieldError>  err=br.getFieldErrors(); 
			        FieldError fe; 
			        String field; 
			        String errorMessage; 
			        for (int i = 0; i < err.size(); i++) { 
			            fe=err.get(i); 
			          //得到那个字段验证出错 
			            field=fe.getField();
			          //得到错误消息 
			            errorMessage=fe.getDefaultMessage();
			            System.out.println("错误字段消息："+field +" : "+errorMessage); 
			        } 
			  // 打印结果 
					return retVal;
				}
				String message = "";
				
				if(flowcheck.getProjectid()==0){
					message = "请选择项目!";
					model.addAttribute("message", message);
					return retVal;
				}else{
					if(!UserLoginController.oppidboolean(req, flowcheck.getProjectid())){
						SectorProjects sp=sectorprojectsService.loadob(flowcheck.getProjectid());
						model.addAttribute("message", "当前用户无权限添加项目【"+sp.getProjectname()+"】的流程检查信息，请联系管理员！");
						return retVal;
					}
				}
				
				if(PublicConst.STATUSSTR0.equals(flowcheck.getProjectphase())){
					message = "请选择检查项目阶段!";
					model.addAttribute("message", message);
					return retVal;
				}
				
				if(PublicConst.STATUSSTR0.equals(flowcheck.getPhasenode())){
					message = "请选择检查阶段节点!";
					model.addAttribute("message", message);
					return retVal;
				}
				
				if(PublicConst.STATUSSTR0.equals(flowcheck.getCheckentry())){
					message = "请选择检查内容!";
					model.addAttribute("message", message);
					return retVal;
				}
				
				if("".equals(flowcheck.getCheckdate())){
					message = "请选择检查日期!";
					model.addAttribute("message", message);
					return retVal;
				}	
				
				int checkid = flowcheckservice.getcheckid(flowcheck.getProjectid());
				
				flowcheck.setCheckid(checkid+1);
				flowcheck.setVersionnum(flowcheck.getVersionnum());
				flowcheck.setProjectphase(flowcheck.getProjectphase());
				flowcheck.setPhasenode(flowcheck.getPhasenode());
				flowcheck.setCheckentry(flowcheck.getCheckentry());
				flowcheck.setCheckresult(flowcheck.getCheckresult());
				flowcheck.setCheckdate(flowcheck.getCheckdate());
				flowcheck.setCheckdescriptions(flowcheck.getCheckdescriptions());
				flowcheck.setStateupdate(flowcheck.getStateupdate());
				flowcheck.setUpdatedate(flowcheck.getUpdatedate());
				flowcheck.setRemark(flowcheck.getRemark());
				SectorProjects p = new SectorProjects();
				p.setProjectid(flowcheck.getProjectid());
				flowcheck.setSectorProjects(p);
				
				int id = flowcheckservice.add(flowcheck);
				String checkentry = flowinfoService.load(Integer.valueOf(flowcheck.getCheckentry())).getCheckentry();
				operationlogservice.add(req, "QA_FLOWCHECK", id, 
						flowcheck.getProjectid(),3,"流程检查信息添加成功（此次检查第一项）！检查结果："+flowcheck.getCheckresult()+" 检查内容："+checkentry);
				
				model.addAttribute("message", "添加成功");
				model.addAttribute("url", "/flowCheck/load.do");
				return retVal;

			}//添加数据
			
			@SuppressWarnings("unchecked")
			List<Object[]> phaselist = flowinfoService.listphaseinfo();
			model.addAttribute("phaselist", phaselist);
			model.addAttribute("projects", QueueListener.qa_projlist);
			
			model.addAttribute("flowcheck", flowcheck);
			return retVal;
						
		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/flowCheck/load.do");
			return "error";
		}

	}
	
	/**
	 * 版本修改
	 * @param tj
	 * @param br
	 * @param model
	 * @param req
	 * @param rsp
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/update.do")
	public String update(@Valid @ModelAttribute("flowcheck") FlowCheck flowcheck, BindingResult br, Model model,
	        HttpServletRequest req, HttpServletResponse rsp) throws Exception
	{
		int	id = Integer.valueOf(req.getParameter("id"));
		
		FlowCheck fc = flowcheckservice.load(id);
		try
		{
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			
			if(!UserLoginController.permissionboolean(req, PublicConst.AUTHFCMOD)){
				model.addAttribute("flowcheck", new FlowCheck());
				model.addAttribute("url", "/flowCheck/loadinfo.do?projectid="+fc.getSectorProjects().getProjectid()+"&checkid="+fc.getCheckid()+"&version="+fc.getVersionnum());
				model.addAttribute("message", "当前用户无权限修改项目检查信息，请联系管理员！");
				return "success";
			}
			
			String retVal = "/jsp/flowcheck/flowcheck_update";
			if (PublicConst.REQPOSTTYPE.equals(req.getMethod()))
			{
				if (br.hasErrors())
				{
					List<FieldError>  err=br.getFieldErrors(); 
			        FieldError fe; 
			        String field; 
			        String errorMessage; 
			        for (int i = 0; i < err.size(); i++) { 
			            fe=err.get(i); 
			          //得到那个字段验证出错 
			            field=fe.getField();
			          //得到错误消息 
			            errorMessage=fe.getDefaultMessage();
			            System.out.println("错误字段消息："+field +" : "+errorMessage); 
			        } 
			  // 打印结果 
					return retVal;
				}
				String message = "";
				
				if(flowcheck.getProjectid()==0){
					message = "请选择项目!";
					model.addAttribute("message", message);
					return retVal;
				}
				
				if(PublicConst.STATUSSTR0.equals(flowcheck.getProjectphase())){
					message = "请选择检查项目阶段!";
					model.addAttribute("message", message);
					return retVal;
				}
				
				if(PublicConst.STATUSSTR0.equals(flowcheck.getPhasenode())){
					message = "请选择检查阶段节点!";
					model.addAttribute("message", message);
					return retVal;
				}
				
				if(PublicConst.STATUSSTR0.equals(flowcheck.getCheckentry())){
					message = "请选择检查内容!";
					model.addAttribute("message", message);
					return retVal;
				}
				
				if("".equals(flowcheck.getCheckdate())){
					message = "请选择检查日期!";
					model.addAttribute("message", message);
					return retVal;
				}	
				
				if(!PublicConst.STATUSSTR0.equals(flowcheck.getStateupdate())&&"".equals(flowcheck.getUpdatedate())){
					message = "如果你已经更新二次检查结果，请选择更新二次检查日期";
					model.addAttribute("message", message);
					return retVal;
				}
								
				flowcheck.setCheckid(fc.getCheckid());
				flowcheck.setVersionnum(fc.getVersionnum());
				flowcheck.setProjectphase(flowcheck.getProjectphase());
				flowcheck.setPhasenode(flowcheck.getPhasenode());
				flowcheck.setCheckentry(flowcheck.getCheckentry());
				flowcheck.setCheckresult(flowcheck.getCheckresult());
				flowcheck.setCheckdate(flowcheck.getCheckdate());
				flowcheck.setCheckdescriptions(flowcheck.getCheckdescriptions());
				
				if(!PublicConst.STATUSSTR0.equals(flowcheck.getStateupdate())&&!"".equals(flowcheck.getUpdatedate())){
					flowcheck.setStateupdate(flowcheck.getStateupdate());
					flowcheck.setUpdatedate(flowcheck.getUpdatedate());
				}else{
					flowcheck.setStateupdate(null);
					flowcheck.setUpdatedate(null);
				}

				flowcheck.setRemark(flowcheck.getRemark());
				
				SectorProjects p = new SectorProjects();
				p.setProjectid(fc.getSectorProjects().getProjectid());
				flowcheck.setSectorProjects(p);
				
				flowcheck.setId(id);				
				
				flowcheckservice.modify(flowcheck);
				String checkentry = flowinfoService.load(Integer.valueOf(flowcheck.getCheckentry())).getCheckentry();
				operationlogservice.add(req, "QA_FLOWCHECK", id, 
						fc.getSectorProjects().getProjectid(),1,"流程检查信息修改成功！检查结果："+flowcheck.getCheckresult()+" 检查内容："+checkentry);
	
				model.addAttribute("message", "修改成功");
				model.addAttribute("url", "/flowCheck/load.do");
				return retVal;

			}//添加修改数据
			
			if(!UserLoginController.oppidboolean(req, fc.getSectorProjects().getProjectid())){
				SectorProjects sp=sectorprojectsService.loadob(fc.getSectorProjects().getProjectid());
				model.addAttribute("url", "/flowCheck/load.do");
				model.addAttribute("message", "当前用户无权限修改项目【"+sp.getProjectname()+"】的流程检查信息，请联系管理员！");
				return "error";
			}
			
			@SuppressWarnings("unchecked")
			List<Object[]> phaselist = flowinfoService.listphaseinfo();
			@SuppressWarnings("unchecked")
			List<Object[]> nodelist = flowinfoService.listnodeinfo(Integer.valueOf(fc.getCheckentry()));
			@SuppressWarnings("unchecked")
			List<Object[]> entrylist = flowinfoService.listentryinfo(Integer.valueOf(fc.getCheckentry()));
			
			model.addAttribute("phaselist", phaselist);
			model.addAttribute("nodelist", nodelist);
			model.addAttribute("entrylist", entrylist);
			model.addAttribute("projects", QueueListener.qa_projlist);
			
			fc.setProjectid(fc.getSectorProjects().getProjectid());
			String projectphase=nodelist.get(0)[0].toString();
			String phasenode=entrylist.get(0)[0].toString();
			fc.setProjectphase(projectphase);
			fc.setPhasenode(phasenode);
			model.addAttribute("projectid", fc.getSectorProjects().getProjectid());
			model.addAttribute("checkid", fc.getCheckid());
			model.addAttribute("versionnum", fc.getVersionnum());
			model.addAttribute("flowcheck", fc);
			return retVal;

						
		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/flowCheck/load.do");
			return "error";
		}

	}
	
	/**
	 * 删除计划检查记录
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete.do")
	public void delete(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHFCDEL)) {
				json.put("status", "fail");
				json.put("ms", "删除检查记录失败,权限不足,请联系管理员!");
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
				String ms="删除流程检查信息失败!";
				int suc=0;
				int fail=0;
				for (int i = 0; i < jsonarr.size(); i++) {
					int id = Integer.valueOf(jsonarr.get(i).toString());
					FlowCheck fc = flowcheckservice.load(id);
					
					if(!UserLoginController.oppidboolean(req, fc.getSectorProjects().getProjectid())){
						fail++;
						continue;
					}
					flowcheckservice.delete(id);
					String checkentry = flowinfoService.load(Integer.valueOf(fc.getCheckentry())).getCheckentry();
					operationlogservice.add(req, "QA_FLOWCHECK", id, 
							fc.getSectorProjects().getProjectid(),0,"流程检查信息删除成功！检查结果："+fc.getCheckresult()+" 检查内容："+checkentry);
					suc++;
				}
				
				if(suc>0){
					status="success";
					ms="删除流程检查信息成功!";
					if(fail>0){
						status="success";
						ms="删除流程检查信息"+suc+"条成功！"+fail+"条因为无项目权限删除失败！";
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
	 * 修改版本号
	 * @param tj
	 * @param br
	 * @param model
	 * @param req
	 * @param rsp
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/updateversion.do")
	public void updateversion(HttpServletRequest req, HttpServletResponse rsp) throws Exception{
/*		String result="更新版本号成功！";
		try{
			if(!UserLoginController.permissionboolean(req, "fc_3")){
				result="当前用户无权限修改项目检查信息，请联系管理员！";
			}
		String versionold = req.getParameter("versionold");
		String versionnew = req.getParameter("versionnew");
		int projectid = Integer.valueOf(req.getParameter("projectid"));
		flowcheckservice.updateversion(projectid, versionold, versionnew);
		}catch(Exception e){
			e.printStackTrace();
			result="更新版本号异常！";
		}finally{
			// 取集合
		    rsp.setContentType("text/xml;charset=utf-8");
		    JSONObject jsobjcet = new JSONObject();
		    jsobjcet.put("result", result); 
			rsp.getWriter().write(jsobjcet.toString());
		}*/
		
		// 更新实体
		JSONObject json = new JSONObject();
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();

			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHFCMOD)) {
				json.put("status", "fail");
				json.put("ms", "编辑失败,权限不足,请联系管理员!");
			} else {
				String versionold = req.getParameter("versionold");
				String versionnew = req.getParameter("versionnew");
				int projectid = Integer.valueOf(req.getParameter("projectid"));
				if(!UserLoginController.oppidboolean(req, projectid)){
					json.put("status", "fail");
					json.put("ms", "编辑失败,项目权限不足,请联系管理员!");
				}else{
					flowcheckservice.updateversion(projectid, versionold, versionnew);
					
					json.put("status", "success");
					json.put("ms", "编辑版本号成功!");
				}

			}
			pw.print(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 三级联动查询
	 * @param tj
	 * @param br
	 * @param model
	 * @param req
	 * @param rsp
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/getcheckinfo.do")
	public void getcheckinfo(HttpServletRequest req, HttpServletResponse rsp) throws Exception{	    
			int	phaseId = Integer.valueOf(req.getParameter("phaseId"));
			int nodeId = Integer.valueOf(req.getParameter("nodeId"));

		List list = null;
			if(phaseId==0&&nodeId==0){
				list = flowinfoService.listphaseinfo();
			}else if(phaseId!=0&&nodeId==0){
				list = flowinfoService.listnodeinfo(phaseId);
			}else if(phaseId!=0&&nodeId!=0){
				list = flowinfoService.listentryinfo(nodeId);
			}
		// 取集合
	    rsp.setContentType("text/xml;charset=utf-8");
		JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(list));
		JSONObject jsobjcet = new JSONObject();
		jsobjcet.put("data", jsonArray); 
		
		rsp.getWriter().write(jsobjcet.toString());
	}

	
	/**
	 * 
	 * HTML5  柱状图
	 * @param model
	 * @param req
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/barchart_html5.do")
	public String showallProject(HttpServletRequest req,Model model) throws Exception
	{
		String checkstartdate = req.getParameter("checkstartdate");
		String checkenddate = req.getParameter("checkenddate");
		
		if(checkstartdate==null||checkstartdate.equals("")){
			checkstartdate = "0";
		}
		if(checkenddate==null||checkenddate.equals("")){
			checkenddate = "9";
		}
		
		List<Object[]> ls = flowcheckservice.listdateper(checkstartdate, checkenddate);
		
		String[] columnname = {"检查通过数","检查未通过数"};
		
		Barchart4[] data = new Barchart4[columnname.length];

/*		Map pointMap = new HashMap<String, Object>();
        
        Map innerptMap = new HashMap<String, Object>();
        innerptMap.put("type", "max");
        innerptMap.put("name", "最大值");
        
        Map innerptMap1 = new HashMap<String, Object>();
        innerptMap1.put("type", "min");
        innerptMap1.put("name", "最小值");
        List<Map> datamapList = new ArrayList<Map>();
        datamapList.add(innerptMap);
        datamapList.add(innerptMap1);
        pointMap.put("data", datamapList);
        
        
		Map lineMap = new HashMap<String, Object>();
        
        Map innerliMap = new HashMap<String, Object>();
        innerliMap.put("type", "average");
        innerliMap.put("name", "平均值");
        List<Map> datamapList1 = new ArrayList<Map>();
        datamapList1.add(innerliMap);
        lineMap.put("data", datamapList1);*/
		
		Map itemStyleMap = new HashMap<String, Object>(0);
		Map normalMap = new HashMap<String, Object>(0);
		Map labelMap = new HashMap<String, Object>(0);
		labelMap.put("show", true);
		labelMap.put("position", "insideRight");
		normalMap.put("label", labelMap);
		itemStyleMap.put("normal", normalMap);
		//获取数据
		for(int i=0;i<columnname.length;i++){			
			Barchart4 barchart4 = new Barchart4();
			barchart4.setName(columnname[i]);	
			barchart4.setType("bar");
			barchart4.setStack("总量");
			barchart4.setItemStyle(itemStyleMap);
			double[] columnvalue = new double[ls.size()];
			for(int j=0;j<ls.size();j++){
				Object[] a = (Object[]) ls.get(j);
				if(a[i+1]!=null){
					columnvalue[j] = Double.valueOf(a[i+1].toString());
				}else{
					columnvalue[j] = 0;
				}
				
			}
			barchart4.setData(columnvalue);			
			data[i] = barchart4;
		}
		
		//项目名
		String[] projectname = new String[ls.size()];
		int i =0;
		for(Object[] ob : ls){
			Object[] project = (Object[])sectorprojectsService.load(Integer.valueOf(ob[0].toString()));
			projectname[i] = project[1].toString();
			i++;
		}
		
		String title;
		if(ls.size()!=0){
			String startdatestr = checkstartdate+"至";
			String enddatestr = checkenddate;
			if(checkstartdate.equals("")||checkstartdate==null||PublicConst.STATUSSTR0.equals(checkstartdate)){
				startdatestr = "初始数据日期至";
			}
			if(checkenddate.equals("")||checkenddate==null||PublicConst.STATUSSTR0.equals(checkenddate)){
				enddatestr = "今天";
			}
			title = "所有项目检查情况汇总("+startdatestr+enddatestr+")";
		}else{
			title = "当前选择日期段内无数据";
		}
		
		JSONArray  jsondata=(JSONArray) JSONArray.toJSON(data);
		JSONArray  jsonprojectname=(JSONArray) JSONArray.toJSON(projectname);
		JSONArray  jsoncolumnname=(JSONArray) JSONArray.toJSON(columnname);
		
		req.setAttribute("gdata", jsondata.toString());
		req.setAttribute("labels", jsonprojectname.toString());
		req.setAttribute("column", jsoncolumnname.toString());
		model.addAttribute("title", title);
		
		System.out.println(jsondata.toString());
		return "/jsp/echart/echarts_bar4";
		
	}
	  
	
	/**
	 * 
	 * HTML5 柱状图
	 * 
	 * @param model
	 * @param req
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/report_count.do")
	public String reportcountProject(HttpServletRequest req, Model model) throws Exception {
		String checkstartdate = req.getParameter("checkstartdate");
		String checkenddate = req.getParameter("checkenddate");

		try {
			FlowCheck flowcheck = new FlowCheck();

			if (checkstartdate != null && !checkstartdate.equals("")) {
				flowcheck.setCheckstartdate(checkstartdate);
			}

			if (checkenddate != null && !checkenddate.equals("")) {
				flowcheck.setCheckenddate(checkenddate);
			}
			
			allRows = flowcheckservice.findRowsReport(flowcheck);			
			/*allRows = aa.size();*/
			
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

			List<Object[]> sssMap = flowcheckservice.reportList(flowcheck, offset, pageSize);
			for (int i = 0; i < sssMap.size(); i++) {
				Object[] project = (Object[]) sectorprojectsService.load(Integer.valueOf(sssMap.get(i)[0].toString()));
				sssMap.get(i)[sssMap.get(i).length - 2] = project[1].toString();
				if (Integer.valueOf(sssMap.get(i)[1].toString()) != 0
						&& Integer.valueOf(sssMap.get(i)[2].toString()) != 0) {
					sssMap.get(i)[sssMap.get(i).length - 1] = Double.valueOf(new DecimalFormat("#.00").format(
							(Double.valueOf(sssMap.get(i)[2].toString()) / Double.valueOf(sssMap.get(i)[1].toString()))
									* 100));
				} else {
					sssMap.get(i)[sssMap.get(i).length - 1] = 0.00;
				}

			}
			model.addAttribute("reportlist", sssMap);

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/flowCheck/load.do");
			return "error";
		}
		return "/jsp/flowcheck/flowcheck_report";
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

}
