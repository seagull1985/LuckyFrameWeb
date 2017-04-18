package luckyweb.seagull.spring.mvc;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.spring.entity.Barchart4;
import luckyweb.seagull.spring.entity.FlowCheck;
import luckyweb.seagull.spring.entity.FlowInfo;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.service.FlowCheckService;
import luckyweb.seagull.spring.service.FlowInfoService;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.util.StrLib;

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

	public FlowCheckService getflowcheckService() {
		return flowcheckservice;
	}

	public void setSectorProjectsService(FlowCheckService flowcheckservice) {
		this.flowcheckservice = flowcheckservice;
	}
	
	public void setFlowInfoService(FlowInfoService flowinfoservice) {
		this.flowinfoService = flowinfoservice;
	}
	
	@Resource(name = "operationlogService")
	private OperationLogService operationlogservice;

	public OperationLogService getOperationlogService() {
		return operationlogservice;
	}
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
	public String list(HttpServletRequest req, FlowCheck flowcheck, Model model)
			throws Exception {
		model.addAttribute("flowcheck", flowcheck);
		try {
			String p = req.getParameter("page");
			String projectid = String.valueOf(flowcheck.getProjectid());
			String checkstartdate = flowcheck.getCheckstartdate();
			String checkenddate = flowcheck.getCheckenddate();

			if (!StrLib.isEmpty(projectid))
			{
				flowcheck.setProjectid(Integer.valueOf(projectid));
			}
			
			if (checkstartdate!=null&&!checkstartdate.equals(""))
			{
				flowcheck.setCheckstartdate(checkstartdate);
			}
			
			if (checkenddate!=null&&!checkenddate.equals(""))
			{
				flowcheck.setCheckenddate(checkenddate);
			}
			
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
			allRows = flowcheckservice.findRows(flowcheck);			
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
			model.addAttribute("projectid", projectid);
/*			// 调度列表
			List<SecondarySector> secondarySector = secondarysectorservice.findSecotorList();
			model.addAttribute("secondarySector", secondarySector);*/
			
			List<Object[]> sssMap = flowcheckservice.findByPage(flowcheck, offset,
					pageSize);
			for(int i=0;i<sssMap.size();i++){	
				Object[] project = (Object[])sectorprojectsService.load(Integer.valueOf(sssMap.get(i)[0].toString()));
				sssMap.get(i)[8] = project[1].toString();
				if(Integer.valueOf(sssMap.get(i)[4].toString())!=0&&Integer.valueOf(sssMap.get(i)[3].toString())!=0){
					sssMap.get(i)[7] = Double.valueOf(new DecimalFormat("#.00").format((Double.valueOf(sssMap.get(i)[4].toString())/Double.valueOf(sssMap.get(i)[3].toString()))*100));
				}else{
					sssMap.get(i)[7] = 0.00;
				}
				
			}
			model.addAttribute("projects", QueueListener.qa_projlist);
			model.addAttribute("splist", sssMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/flowCheck/list.do");
			return "error";
		}
		return "/jsp/flowcheck/flowcheck";
	}

	/**
	 * 
	 * 
	 * @param tj
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/projectchecklist.do")
	public String projectchecklist(@Valid @ModelAttribute("flowcheck") FlowCheck flowcheck, BindingResult br,HttpServletRequest req, HttpServletResponse rsp, Model model)
			throws Exception {
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			int projectid = Integer.valueOf(req.getParameter("projectid"));
			int checkid = Integer.valueOf(req.getParameter("checkid"));
			String versionnum = req.getParameter("versionnum");
			String p = req.getParameter("page");
			
			if (req.getMethod().equals("POST")&&(p==null||p.equals("")))
			{
				if(!UserLoginController.permissionboolean(req, "fc_1")){
					model.addAttribute("flowcheck", new FlowCheck());
					model.addAttribute("url", "/flowCheck/projectchecklist.do?projectid="+projectid+"&checkid="+checkid+"&version="+versionnum);
					model.addAttribute("message", "当前用户无权限添加项目检查信息，请联系软件质量管理室！");
					return "success";
				}
				String result = addinfodetail(flowcheck, br, projectid, checkid, req, rsp, model);
				if(result!=""){
					model.addAttribute("message", result);
				}
			}//提交结束
			
			flowcheck.setId(0);
		    flowcheck.setCheckid(checkid);
			String projectname = null;
			if(projectid!=0){
				flowcheck.setProjectid(projectid);
				Object[] project = (Object[])sectorprojectsService.load(projectid);
				projectname = project[1].toString();
			}
			
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
			allRows = flowcheckservice.findRowsTable(flowcheck);		
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
			model.addAttribute("projectname", projectname);
			model.addAttribute("projectid", projectid);
			model.addAttribute("checkid", checkid);
			model.addAttribute("versionnum", versionnum);
/*			// 调度列表
			List<SecondarySector> secondarySector = secondarysectorservice.findSecotorList();
			model.addAttribute("secondarySector", secondarySector);*/
			
			List<FlowCheck> sssMap = flowcheckservice.findByPageTable(flowcheck, offset,
					pageSize);
			
			List<Object[]> checkinfoMap = flowcheckservice.listcheckinfo(projectid,checkid);
			
			//取信息表中的中文名，放到MAP，从页面通过key value取出
			List<Object[]> allphase = flowinfoService.listphaseallinfo();
			List<Object[]> allnode = flowinfoService.listnodeallinfo();
			List<Object[]> allentry = flowinfoService.listentryallinfo();

			Map<String, String> mapphase = new HashMap<String, String>();
			Map<String, String> mapnode = new HashMap<String, String>();  
			Map<String, String> mapentry = new HashMap<String, String>();  
			
			for(Object[] obph : allphase){  
				   if(obph==null){  
				    continue;  
				   }     
				   mapphase.put(obph[0].toString(),obph[1].toString());  
				  }  
			
			for(Object[] obno : allnode){  
				   if(obno==null){  
				    continue;  
				   }     
				   mapnode.put(obno[0].toString(),obno[1].toString());  
				  } 
			
			for(Object[] oben : allentry){  
				   if(oben==null){  
				    continue;  
				   }     
				   mapentry.put(oben[0].toString(),oben[1].toString());  
				  }    //结束

			List<Object[]> phaselist = flowinfoService.listphaseinfo();
			model.addAttribute("phaselist", phaselist);
			model.addAttribute("projects", QueueListener.qa_projlist);
			model.addAttribute("splist", sssMap);
			model.addAttribute("mapphase", mapphase);
			model.addAttribute("mapnode", mapnode);
			model.addAttribute("mapentry", mapentry);
			model.addAttribute("checkinfoMap", checkinfoMap);
			model.addAttribute("checksum", allRows);
			model.addAttribute("unchecksum", checkinfoMap.size());
			Double sumper = (double)allRows/(allRows+checkinfoMap.size());
			model.addAttribute("checksumper", Double.valueOf(new DecimalFormat("#.00").format(sumper*100)));
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/flowCheck/list.do");
			return "error";
		}
		return "/jsp/flowcheck/flowcheckinfo";
	}
	
	/**
	 * 检查明细添加
	 * @param tj
	 * @param br
	 * @param model
	 * @param req
	 * @param rsp
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public String addinfodetail(FlowCheck flowcheck, BindingResult br,int projectid,int checkid,HttpServletRequest req, HttpServletResponse rsp, Model model) throws Exception
			{
		String result = "";
		if (br.hasErrors())
		{
			result = "添加失败！br.hasErrors()！";
			List<FieldError>  err=br.getFieldErrors(); 
	        FieldError fe; 
	        String field; 
	        String errorMessage; 
	        for (int i = 0; i < err.size(); i++) { 
	            fe=err.get(i); 
	            field=fe.getField();//得到那个字段验证出错 
	            errorMessage=fe.getDefaultMessage();//得到错误消息 
	            System.out.println("错误字段消息："+field +" : "+errorMessage); 
	        } 
	  // 打印结果 
			return result;
		}
		String message = "";
		
		if(flowcheck.getProjectphase().equals("0")){
			result = "请选择检查项目阶段!";
			return result;
		}
		
		if(flowcheck.getPhasenode().equals("0")){
			result = "请选择检查阶段节点!";
			return result;
		}
		
		if(flowcheck.getCheckentry().equals("0")){
			result = "请选择检查内容!";
			return result;
		}
		
		if(flowcheck.getCheckdate().equals("")){
			result = "请选择检查日期!";
			return result;
		}
		
		if(flowcheck.getCheckdescriptions().length()>200){
			result = "检查描述内容过长，请重新填写！";
			return result;
		}
		
		if(!flowcheckservice.determinerecord(projectid, checkid, flowcheck.getCheckentry())){
			result = "您所添加的数据已重复!";
			return result;
		}
		
		flowcheck.setCheckid(checkid);
/*		flowcheck.setProjectphase(flowcheck.getProjectphase());
		flowcheck.setPhasenode(flowcheck.getPhasenode());
		flowcheck.setCheckentry(flowcheck.getCheckentry());
		flowcheck.setCheckresult(flowcheck.getCheckresult());
		flowcheck.setCheckdate(flowcheck.getCheckdate());
		flowcheck.setCheckdescriptions(flowcheck.getCheckdescriptions());
		flowcheck.setStateupdate(flowcheck.getStateupdate());
		flowcheck.setUpdatedate(flowcheck.getUpdatedate());
		flowcheck.setRemark(flowcheck.getRemark());*/
		SectorProjects p = new SectorProjects();
		p.setProjectid(projectid);
		flowcheck.setSectorProjects(p);
		try
		{	
		int id = flowcheckservice.add(flowcheck);
		String checkentry = flowinfoService.load(Integer.valueOf(flowcheck.getCheckentry())).getCheckentry();
		operationlogservice.add(req, "QA_FLOWCHECK", id, 
				projectid,"流程检查明细信息添加成功！检查结果："+flowcheck.getCheckresult()+" 检查内容："+checkentry);
		
		return "添加成功";
		}
		catch (Exception e)
		{
			return "添加出现异常，请检查！";
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
			String temp[]=idstr.split("\\|");
			
		    String temp_datestr="";   
		    Date dt = new Date();   
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
		    temp_datestr=sdf.format(dt);
			for (int i = 0; i < temp.length; i++) {
				FlowCheck flowcheck = new FlowCheck();

				flowcheck.setCheckid(checkid);
				flowcheck.setVersionnum(version);
				flowcheck.setProjectphase(temp[i]);
				flowcheck.setPhasenode(temp[i]);
				flowcheck.setCheckentry(temp[i]);
				flowcheck.setCheckresult("未检查");
				flowcheck.setCheckdate(temp_datestr);
				flowcheck.setCheckdescriptions("");
				flowcheck.setStateupdate("");
				flowcheck.setUpdatedate("");
				flowcheck.setRemark("自动生成，请修改");
				SectorProjects p = new SectorProjects();
				p.setProjectid(projectid);
				flowcheck.setSectorProjects(p);

				flowcheckservice.add(flowcheck);
			}
			operationlogservice.add(req, "QA_FLOWCHECK", checkid, projectid,
					"批量自动生成流程检查明细信息成功！");

			model.addAttribute("message", "批量添加成功");
			model.addAttribute("url", "/flowCheck/projectchecklist.do?projectid="+projectid+"&checkid="+checkid+"&version="+version);
			return "success";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/flowCheck/list.do");
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

			if(!UserLoginController.permissionboolean(req, "fc_1")){
				model.addAttribute("flowcheck", new FlowCheck());
				model.addAttribute("url", "list.do");
				model.addAttribute("message", "当前用户无权限添加项目检查信息，请联系软件质量管理室！");
				return "success";
			}
			
			String retVal = "/jsp/flowcheck/flowcheck_add";
			if (req.getMethod().equals("POST"))
			{
				if (br.hasErrors())
				{
					List<FieldError>  err=br.getFieldErrors(); 
			        FieldError fe; 
			        String field; 
			        String errorMessage; 
			        for (int i = 0; i < err.size(); i++) { 
			            fe=err.get(i); 
			            field=fe.getField();//得到那个字段验证出错 
			            errorMessage=fe.getDefaultMessage();//得到错误消息 
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
				
				if(flowcheck.getProjectphase().equals("0")){
					message = "请选择检查项目阶段!";
					model.addAttribute("message", message);
					return retVal;
				}
				
				if(flowcheck.getPhasenode().equals("0")){
					message = "请选择检查阶段节点!";
					model.addAttribute("message", message);
					return retVal;
				}
				
				if(flowcheck.getCheckentry().equals("0")){
					message = "请选择检查内容!";
					model.addAttribute("message", message);
					return retVal;
				}
				
				if(flowcheck.getCheckdate().equals("")){
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
						flowcheck.getProjectid(),"流程检查信息添加成功（此次检查第一项）！检查结果："+flowcheck.getCheckresult()+" 检查内容："+checkentry);
				
				model.addAttribute("message", "添加成功");
				model.addAttribute("url", "/flowCheck/list.do");
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
			model.addAttribute("url", "/flowCheck/list.do");
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
			
			if(!UserLoginController.permissionboolean(req, "fc_3")){
				model.addAttribute("flowcheck", new FlowCheck());
				model.addAttribute("url", "/flowCheck/projectchecklist.do?projectid="+fc.getSectorProjects().getProjectid()+"&checkid="+fc.getCheckid()+"&version="+fc.getVersionnum());
				model.addAttribute("message", "当前用户无权限修改项目检查信息，请联系软件质量管理室！");
				return "success";
			}
			
			String retVal = "/jsp/flowcheck/flowcheck_update";
			if (req.getMethod().equals("POST"))
			{
				if (br.hasErrors())
				{
					List<FieldError>  err=br.getFieldErrors(); 
			        FieldError fe; 
			        String field; 
			        String errorMessage; 
			        for (int i = 0; i < err.size(); i++) { 
			            fe=err.get(i); 
			            field=fe.getField();//得到那个字段验证出错 
			            errorMessage=fe.getDefaultMessage();//得到错误消息 
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
				
				if(flowcheck.getProjectphase().equals("0")){
					message = "请选择检查项目阶段!";
					model.addAttribute("message", message);
					return retVal;
				}
				
				if(flowcheck.getPhasenode().equals("0")){
					message = "请选择检查阶段节点!";
					model.addAttribute("message", message);
					return retVal;
				}
				
				if(flowcheck.getCheckentry().equals("0")){
					message = "请选择检查内容!";
					model.addAttribute("message", message);
					return retVal;
				}
				
				if(flowcheck.getCheckdate().equals("")){
					message = "请选择检查日期!";
					model.addAttribute("message", message);
					return retVal;
				}	
				
				if(!flowcheck.getStateupdate().equals("0")&&flowcheck.getUpdatedate().equals("")){
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
				
				if(!flowcheck.getStateupdate().equals("0")&&!flowcheck.getUpdatedate().equals("")){
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
						fc.getSectorProjects().getProjectid(),"流程检查信息修改成功！检查结果："+flowcheck.getCheckresult()+" 检查内容："+checkentry);
	
				model.addAttribute("message", "修改成功");
				model.addAttribute("url", "/flowCheck/list.do");
				return retVal;

			}//添加修改数据
			
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
			model.addAttribute("url", "/flowCheck/list.do");
			return "error";
		}

	}
	
	/**
	 * 删除检查记录
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete.do", method = RequestMethod.GET)
	public String delete(FlowCheck flowcheck, HttpServletRequest req, Model model) throws Exception
	{
		int id = Integer.valueOf(req.getParameter("id"));		
		FlowCheck fc = flowcheckservice.load(id); 
		try
		{
			
			if(!UserLoginController.permissionboolean(req, "fc_2")){
				model.addAttribute("flowcheck", new FlowCheck());
				model.addAttribute("url", "/flowCheck/projectchecklist.do?projectid="+fc.getSectorProjects().getProjectid()+"&checkid="+fc.getCheckid()+"&version="+fc.getVersionnum());
				model.addAttribute("message", "当前用户无权限删除项目检查信息，请联系软件质量管理室！");
				return "success";
			}

			flowcheckservice.delete(id);
		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/flowCheck/list.do");
			return "error";
		}
		

		String checkentry = flowinfoService.load(Integer.valueOf(fc.getCheckentry())).getCheckentry();
		operationlogservice.add(req, "QA_FLOWCHECK", id, 
				fc.getSectorProjects().getProjectid(),"流程检查信息删除成功！检查结果："+fc.getCheckresult()+" 检查内容："+checkentry);

		String message = "删除成功！";
		
		fc.setId(0);
		int count = flowcheckservice.findRowsTable(fc);
		
		model.addAttribute("flowcheck", new FlowCheck());
		model.addAttribute("message", message);
		
		if(count==0){
			model.addAttribute("url", "/flowCheck/list.do");
		}else{
			model.addAttribute("url", "/flowCheck/projectchecklist.do?projectid="+fc.getSectorProjects().getProjectid()+"&checkid="+fc.getCheckid()+"&version="+fc.getVersionnum());
		}
		return "success";
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
	public void updateversion(HttpServletRequest req, HttpServletResponse rsp,Model model) throws Exception{
		String result="更新版本号成功！";
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
		JSONArray jsonArray = JSONArray.fromObject(list);
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
		
		Map itemStyleMap = new HashMap<String, Object>();
		Map normalMap = new HashMap<String, Object>();
		Map labelMap = new HashMap<String, Object>();
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
			if(checkstartdate.equals("")||checkstartdate==null||checkstartdate.equals("0")){
				startdatestr = "初始数据日期至";
			}
			if(checkenddate.equals("")||checkenddate==null||checkenddate.equals("9")){
				enddatestr = "今天";
			}
			title = "所有项目检查情况汇总("+startdatestr+enddatestr+")";
		}else{
			title = "当前选择日期段内无数据";
		}
		
		JSONArray  jsondata=JSONArray.fromObject(data);
		JSONArray  jsonprojectname=JSONArray.fromObject(projectname);
		JSONArray  jsoncolumnname=JSONArray.fromObject(columnname);
		
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
			model.addAttribute("url", "/flowCheck/list.do");
			return "error";
		}
		return "/jsp/flowcheck/flowcheck_report";
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String temp[]="12|11|".split("\\|");
		System.out.println(temp.length);
	}

}
