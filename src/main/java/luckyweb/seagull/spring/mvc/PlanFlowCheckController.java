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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.spring.entity.FlowCheck;
import luckyweb.seagull.spring.entity.FlowInfo;
import luckyweb.seagull.spring.entity.PlanFlowCheck;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.service.FlowCheckService;
import luckyweb.seagull.spring.service.FlowInfoService;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.PlanFlowCheckService;
import luckyweb.seagull.util.StrLib;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
@RequestMapping("/planflowCheck")
public class PlanFlowCheckController {
	
	private int allPage;
	private int pageSize = 20;
	private int allRows;
	private int page = 1;
	private int offset;
	
	@Resource(name = "planflowcheckService")
	private PlanFlowCheckService planflowcheckservice;
	
	@Resource(name = "flowinfoService")
	private FlowInfoService flowinfoService;

	public PlanFlowCheckService getPlanFlowCheckService() {
		return planflowcheckservice;
	}
	
	@Resource(name = "operationlogService")
	private OperationLogService operationlogservice;

	
	@Resource(name = "flowcheckService")
	private FlowCheckService flowcheckservice;
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
	public String list(HttpServletRequest req, PlanFlowCheck pfc, Model model)
			throws Exception {
		model.addAttribute("planflowcheck", pfc);

		try {
			String p = req.getParameter("page");
			
			pfc.setStatus(1);
			if(pfc.getProjectid()!=0){
				pfc.setProjectid(pfc.getProjectid());
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
			allRows = planflowcheckservice.findRows(pfc);
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
/*			// 调度列表
			List<SecondarySector> secondarySector = secondarysectorservice.findSecotorList();
			model.addAttribute("secondarySector", secondarySector);*/
			
			List<PlanFlowCheck> sssMap = planflowcheckservice.findByPage(pfc, offset,
					pageSize);
			
			for(PlanFlowCheck pfcheck : sssMap){
				FlowInfo fi = flowinfoService.load(Integer.valueOf(pfcheck.getCheckentryid()));
				pfcheck.setCheckentryid(fi.getCheckentry());
				pfcheck.setChecknode(fi.getPhasenodename());
				pfcheck.setCheckphase(fi.getPhasename());
			}
			
			model.addAttribute("splist", sssMap);
			model.addAttribute("projects", QueueListener.qa_projlist);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/planflowCheck/list.do");
			return "error";
		}
		return "/jsp/flowcheck/planflowcheck";
	}
	
	/**
	 * 检查计划添加
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
	public String add(@Valid @ModelAttribute("planflowcheck") PlanFlowCheck planflowcheck, BindingResult br, Model model,
	        HttpServletRequest req, HttpServletResponse rsp) throws Exception
	{
		
		try
		{
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");

			if(!UserLoginController.permissionboolean(req, "pfc_1")){
				model.addAttribute("planflowcheck", new PlanFlowCheck());
				model.addAttribute("url", "list.do");
				model.addAttribute("message", "当前用户无权限添加项目检查计划，请联系软件质量管理室！");
				return "success";
			}
			
			String retVal = "/jsp/flowcheck/planflowcheck_add";
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
					@SuppressWarnings("unchecked")
					List<Object[]> phaselist = flowinfoService.listphaseinfo();
					model.addAttribute("phaselist", phaselist);
					model.addAttribute("projects", QueueListener.qa_projlist);			
					model.addAttribute("planflowcheck", planflowcheck);
					return retVal;
				}
				String message = "";
				
				if(planflowcheck.getProjectid()==0){
					message = "请选择项目!";
					model.addAttribute("message", message);
					@SuppressWarnings("unchecked")
					List<Object[]> phaselist = flowinfoService.listphaseinfo();
					model.addAttribute("phaselist", phaselist);
					model.addAttribute("projects", QueueListener.qa_projlist);			
					model.addAttribute("planflowcheck", planflowcheck);
					return retVal;
				}
				
				if(planflowcheck.getCheckentryid().equals("0")){
					message = "请选择检查内容!";
					model.addAttribute("message", message);
					@SuppressWarnings("unchecked")
					List<Object[]> phaselist = flowinfoService.listphaseinfo();
					model.addAttribute("phaselist", phaselist);
					model.addAttribute("projects", QueueListener.qa_projlist);			
					model.addAttribute("planflowcheck", planflowcheck);
					return retVal;
				}
				
				if(planflowcheck.getPlandate().equals("")){
					message = "请选择计划检查日期!";
					model.addAttribute("message", message);
					@SuppressWarnings("unchecked")
					List<Object[]> phaselist = flowinfoService.listphaseinfo();
					model.addAttribute("phaselist", phaselist);
					model.addAttribute("projects", QueueListener.qa_projlist);			
					model.addAttribute("planflowcheck", planflowcheck);
					return retVal;
				}	
				
				planflowcheck.setVersionnum(planflowcheck.getVersionnum());
				planflowcheck.setCheckentryid(planflowcheck.getCheckentryid());
				planflowcheck.setPlandate(planflowcheck.getPlandate());
				planflowcheck.setStatus(1);
				SectorProjects p = new SectorProjects();
				p.setProjectid(planflowcheck.getProjectid());
				planflowcheck.setSectorProjects(p);
				
				int id = planflowcheckservice.add(planflowcheck);
				String checkentry = flowinfoService.load(Integer.valueOf(planflowcheck.getCheckentryid())).getCheckentry();
				operationlogservice.add(req, "QA_PLANFLOWCHECK", id, 
						planflowcheck.getProjectid(),"流程检查计划添加成功！计划日期："+planflowcheck.getPlandate()+" 检查内容："+checkentry);
				
				model.addAttribute("message", "添加成功");
				@SuppressWarnings("unchecked")
				List<Object[]> phaselist = flowinfoService.listphaseinfo();
				model.addAttribute("phaselist", phaselist);
				model.addAttribute("projects", QueueListener.qa_projlist);			
				model.addAttribute("planflowcheck", planflowcheck);
				return retVal;

			}//添加数据
			
			@SuppressWarnings("unchecked")
			List<Object[]> phaselist = flowinfoService.listphaseinfo();
			model.addAttribute("phaselist", phaselist);
			model.addAttribute("projects", QueueListener.qa_projlist);			
			model.addAttribute("planflowcheck", planflowcheck);
			return retVal;
						
		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/planflowCheck/list.do");
			return "error";
		}

	}
	
	/**
	 * 检查计划修改
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
	public String update(@Valid @ModelAttribute("planflowcheck") PlanFlowCheck planflowcheck, BindingResult br, Model model,
	        HttpServletRequest req, HttpServletResponse rsp) throws Exception
	{
		
		try
		{
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");

			int id  = Integer.valueOf(req.getParameter("id"));
			PlanFlowCheck pfc = planflowcheckservice.load(id);
			if(!UserLoginController.permissionboolean(req, "pfc_3")){
				model.addAttribute("planflowcheck", new PlanFlowCheck());
				model.addAttribute("url", "list.do");
				model.addAttribute("message", "当前用户无权修改项目检查计划，请联系软件质量管理室！");
				return "success";
			}
			
			String retVal = "/jsp/flowcheck/planflowcheck_update";
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
				
				if(planflowcheck.getProjectid()==0){
					message = "请选择项目!";
					model.addAttribute("message", message);
					return retVal;
				}
				
				if(planflowcheck.getCheckentryid().equals("0")){
					message = "请选择检查内容!";
					model.addAttribute("message", message);
					return retVal;
				}
				
				if(planflowcheck.getPlandate().equals("")){
					message = "请选择计划检查日期!";
					model.addAttribute("message", message);
					return retVal;
				}	
				
				planflowcheck.setVersionnum(planflowcheck.getVersionnum());
				planflowcheck.setCheckentryid(planflowcheck.getCheckentryid());
				planflowcheck.setPlandate(planflowcheck.getPlandate());
				planflowcheck.setStatus(1);
				SectorProjects p = new SectorProjects();
				p.setProjectid(planflowcheck.getProjectid());
				planflowcheck.setSectorProjects(p);
				
				planflowcheckservice.modify(planflowcheck);
				String checkentry = flowinfoService.load(Integer.valueOf(planflowcheck.getCheckentryid())).getCheckentry();
				operationlogservice.add(req, "QA_PLANFLOWCHECK", id, 
						planflowcheck.getProjectid(),"流程检查计划修改成功！计划日期："+planflowcheck.getPlandate()+" 检查内容："+checkentry);
				
				model.addAttribute("message", "修改成功");
				model.addAttribute("url", "/planflowCheck/list.do");
				return retVal;

			}//添加数据
			
			@SuppressWarnings("unchecked")
			List<Object[]> phaselist = flowinfoService.listphaseinfo();
			@SuppressWarnings("unchecked")
			List<Object[]> nodelist = flowinfoService.listnodeinfo(Integer.valueOf(pfc.getCheckentryid()));
			@SuppressWarnings("unchecked")
			List<Object[]> entrylist = flowinfoService.listentryinfo(Integer.valueOf(pfc.getCheckentryid()));
			
			model.addAttribute("phaselist", phaselist);
			model.addAttribute("nodelist", nodelist);
			model.addAttribute("entrylist", entrylist);
			
			pfc.setProjectid(pfc.getSectorProjects().getProjectid());
			String projectphase=nodelist.get(0)[0].toString();
			String phasenode=entrylist.get(0)[0].toString();
			pfc.setCheckphase(projectphase);;
			pfc.setChecknode(phasenode);
			
			model.addAttribute("projects", QueueListener.qa_projlist);			
			model.addAttribute("planflowcheck", pfc);
			return retVal;
						
		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/planflowCheck/list.do");
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
	@RequestMapping(value = "/delete.do", method = RequestMethod.GET)
	public String delete(HttpServletRequest req, Model model) throws Exception
	{
		int id = Integer.valueOf(req.getParameter("id"));		
		PlanFlowCheck pfc = planflowcheckservice.load(id); 
		try
		{
			
			if(!UserLoginController.permissionboolean(req, "pfc_2")){
				model.addAttribute("flowcheck", new FlowCheck());
				model.addAttribute("url", "list.do");
				model.addAttribute("message", "当前用户无权限删除项目检查计划，请联系软件质量管理室！");
				return "success";
			}

			planflowcheckservice.delete(id);
		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/planflowCheck/list.do");
			return "error";
		}
		

		String checkentry = flowinfoService.load(Integer.valueOf(pfc.getCheckentryid())).getCheckentry();
		operationlogservice.add(req, "QA_PLANFLOWCHECK", id, 
				pfc.getSectorProjects().getProjectid(),"流程检查计划删除成功！计划日期："+pfc.getPlandate()+" 检查内容："+checkentry);

		String message = "删除成功！";
		
		model.addAttribute("planflowcheck", new PlanFlowCheck());
		model.addAttribute("message", message);
		model.addAttribute("url", "/planflowCheck/list.do");
		return "success";
	}
	
	/**
	 * 检查计划转检查记录
	 * @param tj
	 * @param br
	 * @param model
	 * @param req
	 * @param rsp
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/tocheck.do")
	public String tocheck(@Valid @ModelAttribute("flowcheck") FlowCheck flowcheck,BindingResult br, Model model,HttpServletRequest req, HttpServletResponse rsp) throws Exception
	{
		rsp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");

		if(!UserLoginController.permissionboolean(req, "fc_tocheck")){
			model.addAttribute("flowcheck", new FlowCheck());
			model.addAttribute("url", "list.do");
			model.addAttribute("message", "当前用户无权限把计划转成检查结果，请联系软件质量管理室！");
			return "success";
		}
		
		try{
		String retVal = "/jsp/flowcheck/planflowcheck_tocheck";
		int id = Integer.valueOf(req.getParameter("id"));
		String aaa = req.getParameter("checkid");
		int checkid = Integer.valueOf(req.getParameter("checkid"));
		PlanFlowCheck pfc = planflowcheckservice.load(id); 
		
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
			
			if(checkid==0){
				checkid = flowcheckservice.getcheckid(flowcheck.getProjectid());
				flowcheck.setCheckid(checkid+1);
			}else{
				if(!flowcheckservice.determinerecord(flowcheck.getProjectid(), checkid, flowcheck.getCheckentry())){
					message = "你添加的检查内容在当次检查中已存在!";
					model.addAttribute("message", message);
					return retVal;
				}
				flowcheck.setCheckid(checkid);
			}
			
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
			
			int addid = flowcheckservice.add(flowcheck);
			pfc.setStatus(2);  //修改成已转检查记录状态
			planflowcheckservice.modify(pfc);
			String checkentry = flowinfoService.load(Integer.valueOf(flowcheck.getCheckentry())).getCheckentry();
			operationlogservice.add(req, "QA_FLOWCHECK", addid, 
					flowcheck.getProjectid(),"计划转检查结果添加成功！检查结果："+flowcheck.getCheckresult()+" 检查内容："+checkentry);
			
			model.addAttribute("message", "转检查结果成功");
			model.addAttribute("url", "/planflowCheck/list.do");
			return retVal;

		}//添加数据
		
		@SuppressWarnings("unchecked")
		List<Object[]> phaselist = flowinfoService.listphaseinfo();
		@SuppressWarnings("unchecked")
		List<Object[]> nodelist = flowinfoService.listnodeinfo(Integer.valueOf(pfc.getCheckentryid()));
		@SuppressWarnings("unchecked")
		List<Object[]> entrylist = flowinfoService.listentryinfo(Integer.valueOf(pfc.getCheckentryid()));
		
		model.addAttribute("phaselist", phaselist);
		model.addAttribute("nodelist", nodelist);
		model.addAttribute("entrylist", entrylist);
		model.addAttribute("projects", QueueListener.qa_projlist);
		
		flowcheck.setProjectid(pfc.getSectorProjects().getProjectid());
		flowcheck.setCheckdate(pfc.getPlandate());
		if(checkid==0){  //如果插入已有检查，那么版本号自动更改成原有的
			flowcheck.setVersionnum(pfc.getVersionnum());
		}else{			
			flowcheck.setVersionnum(flowcheckservice.getversionnum(pfc.getSectorProjects().getProjectid(), checkid));
		}
		flowcheck.setProjectphase(pfc.getCheckentryid());
		flowcheck.setPhasenode(pfc.getCheckentryid());
		flowcheck.setCheckentry(pfc.getCheckentryid());
		
		model.addAttribute("projectid", pfc.getSectorProjects().getProjectid());
		model.addAttribute("flowcheck", flowcheck);
		return retVal;
		
		}catch(Exception e){
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/planflowCheck/list.do");
			return "error";
		}
	}
	
	/**
	 * 第几次检查查询
	 * @param tj
	 * @param br
	 * @param model
	 * @param req
	 * @param rsp
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/getcheckid.do")
	public void getcheckinfo(HttpServletRequest req, HttpServletResponse rsp) throws Exception{	    
		int	id = Integer.valueOf(req.getParameter("id"));
		int projectid = planflowcheckservice.load(id).getSectorProjects().getProjectid();
		int checkid = flowcheckservice.getcheckid(projectid);
		List<String> list = new ArrayList<String>();
		int size;
		if(checkid<=5){
			size = checkid;
		}else{
			size = 5;
		}
		for(int i=0;i<size;i++){
			list.add(i, String.valueOf(checkid));
			checkid--;
		}
		// 取集合
	    rsp.setContentType("text/xml;charset=utf-8");
		JSONArray jsonArray = JSONArray.fromObject(list);
		JSONObject jsobjcet = new JSONObject();
		jsobjcet.put("data", jsonArray); 
		
		rsp.getWriter().write(jsobjcet.toString());
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

	
   }
}
