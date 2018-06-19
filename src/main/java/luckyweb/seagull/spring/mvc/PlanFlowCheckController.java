package luckyweb.seagull.spring.mvc;

import java.io.BufferedReader;
import java.io.IOException;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import luckyweb.seagull.comm.PublicConst;
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
@RequestMapping("/planflowCheck")
public class PlanFlowCheckController {
	

	
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
	@RequestMapping(value = "/load.do")
	public String load(HttpServletRequest req) throws Exception {
		return "/jsp/flowcheck/planflowcheck";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list.do")
	private void ajaxGetSellRecord(Integer limit, Integer offset, HttpServletRequest request,
			HttpServletResponse response) throws NumberFormatException, Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		PrintWriter pw = response.getWriter();
		String projectid = request.getParameter("projectid");
		PlanFlowCheck pfcheck = new PlanFlowCheck();
		pfcheck.setStatus(1);
		if (null == offset && null == limit) {
			offset = 0;
		}
		if (null == limit || limit == 0) {
			limit = 10;
		}

		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(projectid)&&!PublicConst.STATUSSTR99.equals(projectid)) {
			pfcheck.setProjectid(Integer.valueOf(projectid));
		}
		
		List<PlanFlowCheck> pfclist = planflowcheckservice.findByPage(pfcheck, offset,
				limit);
		
		for(int i=0;i<pfclist.size();i++){
			FlowInfo fi = flowinfoService.load(Integer.valueOf(pfclist.get(i).getCheckentryid()));
			pfclist.get(i).setCheckentryid(fi.getCheckentry());
			pfclist.get(i).setChecknode(fi.getPhasenodename());
			pfclist.get(i).setCheckphase(fi.getPhasename());
		}
		// 转换成json字符串
		JSONArray recordJson = StrLib.listToJson(pfclist);
		// 得到总记录数
		int total = planflowcheckservice.findRows(pfcheck);
		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", recordJson);
		pw.print(json.toString());
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

			if(!UserLoginController.permissionboolean(req, PublicConst.AUTHPFCADD)){
				model.addAttribute("planflowcheck", new PlanFlowCheck());
				model.addAttribute("url", "load.do");
				model.addAttribute("message", "当前用户无权限添加项目检查计划，请联系管理员！");
				return "success";
			}
			
			String retVal = "/jsp/flowcheck/planflowcheck_add";
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
				}else{
					if(!UserLoginController.oppidboolean(req, planflowcheck.getProjectid())){
						@SuppressWarnings("unchecked")
						List<Object[]> phaselist = flowinfoService.listphaseinfo();
						model.addAttribute("phaselist", phaselist);
						model.addAttribute("projects", QueueListener.qa_projlist);			
						model.addAttribute("planflowcheck", planflowcheck);
						model.addAttribute("message", "当前用户无权限添加此项目流程计划检查信息，请联系管理员！");
						return retVal;
					}
				}
				
				if(PublicConst.STATUSSTR0.equals(planflowcheck.getCheckentryid())){
					message = "请选择检查内容!";
					model.addAttribute("message", message);
					@SuppressWarnings("unchecked")
					List<Object[]> phaselist = flowinfoService.listphaseinfo();
					model.addAttribute("phaselist", phaselist);
					model.addAttribute("projects", QueueListener.qa_projlist);			
					model.addAttribute("planflowcheck", planflowcheck);
					return retVal;
				}
				
				if("".equals(planflowcheck.getPlandate())){
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
						planflowcheck.getProjectid(),2,"流程检查计划添加成功！计划日期："+planflowcheck.getPlandate()+" 检查内容："+checkentry);
				
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
			model.addAttribute("url", "/planflowCheck/load.do");
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
			if(!UserLoginController.permissionboolean(req, PublicConst.AUTHPFCMOD)){
				model.addAttribute("planflowcheck", new PlanFlowCheck());
				model.addAttribute("url", "load.do");
				model.addAttribute("message", "当前用户无权修改项目检查计划，请联系管理员！");
				return "success";
			}
			
			String retVal = "/jsp/flowcheck/planflowcheck_update";
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
				
				if(planflowcheck.getProjectid()==0){
					message = "请选择项目!";
					model.addAttribute("message", message);
					return retVal;
				}
				
				if(PublicConst.STATUSSTR0.equals(planflowcheck.getCheckentryid())){
					message = "请选择检查内容!";
					model.addAttribute("message", message);
					return retVal;
				}
				
				if("".equals(planflowcheck.getPlandate())){
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
						planflowcheck.getProjectid(),1,"流程检查计划修改成功！计划日期："+planflowcheck.getPlandate()+" 检查内容："+checkentry);
				
				model.addAttribute("message", "修改成功");
				model.addAttribute("url", "/planflowCheck/load.do");
				return retVal;

			}//添加数据
			
			if(!UserLoginController.oppidboolean(req, pfc.getSectorProjects().getProjectid())){
				model.addAttribute("url", "/planflowCheck/load.do");
				model.addAttribute("message", "当前用户无权限修改此项目的流程检查计划，请联系管理员！");
				return "error";
			}
			
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
			model.addAttribute("url", "/planflowCheck/load.do");
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
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHPFCDEL)) {
				json.put("status", "fail");
				json.put("ms", "删除检查计划失败,权限不足,请联系管理员!");
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
				String ms="删除检查计划失败!";
				int suc=0;
				int fail=0;
				for (int i = 0; i < jsonarr.size(); i++) {
					int id = Integer.valueOf(jsonarr.get(i).toString());
					PlanFlowCheck pfc = planflowcheckservice.load(id); 
					
					if(!UserLoginController.oppidboolean(req, pfc.getSectorProjects().getProjectid())){
						fail++;
						continue;
					}
					planflowcheckservice.delete(id);
					String checkentry = flowinfoService.load(Integer.valueOf(pfc.getCheckentryid())).getCheckentry();
					operationlogservice.add(req, "QA_PLANFLOWCHECK", id, 
							pfc.getSectorProjects().getProjectid(),0,"流程检查计划删除成功！计划日期："+pfc.getPlandate()+" 检查内容："+checkentry);
					suc++;
				}
				if(suc>0){
					status="success";
					ms="删除检查计划成功!";
					if(fail>0){
						status="success";
						ms="删除检查计划"+suc+"条成功！"+fail+"条因为无项目权限删除失败！";
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

		if(!UserLoginController.permissionboolean(req, PublicConst.AUTHPFCTOCHECK)){
			model.addAttribute("flowcheck", new FlowCheck());
			model.addAttribute("url", "load.do");
			model.addAttribute("message", "当前用户无权限把计划转成检查结果，请联系管理员！");
			return "success";
		}
		
		try{
		String retVal = "/jsp/flowcheck/planflowcheck_tocheck";
		int id = Integer.valueOf(req.getParameter("id"));
		String aaa = req.getParameter("checkid");
		int checkid = Integer.valueOf(req.getParameter("checkid"));
		PlanFlowCheck pfc = planflowcheckservice.load(id); 
		
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
			//修改成已转检查记录状态
			pfc.setStatus(2);  
			planflowcheckservice.modify(pfc);
			String checkentry = flowinfoService.load(Integer.valueOf(flowcheck.getCheckentry())).getCheckentry();
			operationlogservice.add(req, "QA_FLOWCHECK", addid, 
					flowcheck.getProjectid(),1,"计划转检查结果添加成功！检查结果："+flowcheck.getCheckresult()+" 检查内容："+checkentry);
			
			model.addAttribute("message", "转检查结果成功");
			model.addAttribute("url", "/planflowCheck/load.do");
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
		//如果插入已有检查，那么版本号自动更改成原有的
		if(checkid==0){  
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
			model.addAttribute("url", "/planflowCheck/load.do");
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
		int checknum=5;
		if(checkid<=checknum){
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
		JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(list));
		JSONObject jsobjcet = new JSONObject();
		jsobjcet.put("data", jsonArray); 
		
		rsp.getWriter().write(jsobjcet.toString());
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

	
   }
}
