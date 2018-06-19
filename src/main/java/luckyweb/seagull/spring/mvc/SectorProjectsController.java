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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.spring.entity.ProjectCase;
import luckyweb.seagull.spring.entity.ProjectPlan;
import luckyweb.seagull.spring.entity.ProjectProtocolTemplate;
import luckyweb.seagull.spring.entity.PublicCaseParams;
import luckyweb.seagull.spring.entity.SecondarySector;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.entity.TestCasedetail;
import luckyweb.seagull.spring.entity.TestClient;
import luckyweb.seagull.spring.entity.TestJobs;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.ProjectCaseService;
import luckyweb.seagull.spring.service.ProjectPlanService;
import luckyweb.seagull.spring.service.ProjectProtocolTemplateService;
import luckyweb.seagull.spring.service.PublicCaseParamsService;
import luckyweb.seagull.spring.service.SecondarySectorService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.spring.service.TestClientService;
import luckyweb.seagull.spring.service.TestJobsService;
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
@RequestMapping("/sectorProjects")
public class SectorProjectsController {
	
	@Resource(name = "sectorprojectsService")
	private SectorProjectsService sectorprojectsservice;
	
	@Resource(name = "secondarysectorService")
	private SecondarySectorService secondarysectorservice;

	@Resource(name = "projectCaseService")
	private ProjectCaseService projectcaseservice;
	
	@Resource(name = "testJobsService")
	private TestJobsService	 testJobsService;
	
	@Resource(name = "projectPlanService")
	private ProjectPlanService projectplanservice;
	
	@Resource(name = "projectprotocoltemplateService")
	private ProjectProtocolTemplateService ptemplateservice;
	
	@Resource(name = "publiccaseparamsService")
	private PublicCaseParamsService pcpservice;
	
	@Resource(name = "operationlogService")
	private OperationLogService operationlogservice;
	
	@Resource(name = "testclientService")
	private TestClientService tcservice;
	
	@RequestMapping(value = "/sectorprojects.do")
	public String qualityshow(TestCasedetail caseDetail, HttpServletRequest req,HttpServletResponse rsp,
			Model model) throws Exception {
		return "/jsp/base/sectorprojects";
	}
	
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
		List<SecondarySector> sectors=secondarysectorservice.findSecotorList();
		model.addAttribute("sectors", sectors);
		return "/jsp/base/sectorprojects";
	}
	
	@RequestMapping(value = "/list.do")
	private void ajaxGetSellRecord(Integer limit, Integer offset, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		String search = request.getParameter("search");
		SectorProjects sectorprojects=new SectorProjects();
		if (null == offset && null == limit) {
			offset = 0;
		}
		if (null == limit || limit == 0) {
			limit = 10;
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(search)) {
			sectorprojects.setProjectname(search);
			sectorprojects.setProjectmanager(search);
			sectorprojects.setProjectsign(search);
		}

		List<SectorProjects> projects = sectorprojectsservice.findByPage(sectorprojects, offset, limit);
		List<SecondarySector> sectors = secondarysectorservice.findSecotorList();
		for(int i=0;i<projects.size();i++){			
			if(projects.get(i).getProjectid()==99){
				projects.remove(i);
				i--;
				continue;
			}
			for(SecondarySector sector:sectors){
				if(projects.get(i).getSectorid()==sector.getSectorid()){
					projects.get(i).setDepartmentname(sector.getDepartmentname());
					projects.get(i).setDepartmenthead(sector.getDepartmenthead());
				}
			}
		}
		
		// 转换成json字符串
		JSONArray recordJson = StrLib.listToJson(projects);
		// 得到总记录数
		int total = sectorprojectsservice.findRows(sectorprojects);
		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total-1);
		json.put("rows", recordJson);
		pw.print(json.toString());
	}
	
	@RequestMapping(value = "/update.do")
	public void updateproject(HttpServletRequest req, HttpServletResponse rsp, SectorProjects sectorprojects) {
		// 更新实体
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHPROJECTMOD)) {
				json.put("status", "fail");
				json.put("ms", "当前用户无权限修改项目信息，请联系管理员！");
			} else {
				
				sectorprojectsservice.modify(sectorprojects);
				if(sectorprojects.getProjecttype()==0){
					List<SectorProjects> qaprolist=QueueListener.qa_projlist;
					for(int i=0;i<qaprolist.size();i++){
						SectorProjects sp=qaprolist.get(i);
						if(sp.getProjectid()==sectorprojects.getProjectid()){
							qaprolist.set(i, sectorprojects);
							break;
						}
					}
				}else if(sectorprojects.getProjecttype()==1){
					List<SectorProjects> prolist=QueueListener.projlist;
					for(int i=0;i<prolist.size();i++){
						SectorProjects sp=prolist.get(i);
						if(sp.getProjectid()==sectorprojects.getProjectid()){
							prolist.set(i, sectorprojects);
							break;
						}
					}
				}
				
				operationlogservice.add(req, "SectorProjects", sectorprojects.getProjectid(), 
						sectorprojects.getProjectid(),1,"项目信息修改成功！项目名"+sectorprojects.getProjectname());
				json.put("status", "success");
				json.put("ms", "编辑项目成功!");
			}
			pw.print(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加项目
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
	@RequestMapping(value = "/projectadd.do")
	public void add(SectorProjects sectorprojects, HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHPROJECTADD)) {
				json.put("status", "fail");
				json.put("ms", "增加项目失败,权限不足,请联系管理员!");
			} else {
				int proid = sectorprojectsservice.add(sectorprojects);
				
				if(sectorprojects.getProjecttype()==0){
					List<SectorProjects> qaprolist=QueueListener.qa_projlist;
					qaprolist.add(sectorprojects);
				}else if(sectorprojects.getProjecttype()==1){
					List<SectorProjects> prolist=QueueListener.projlist;
					prolist.add(sectorprojects);
				}
				
				operationlogservice.add(req, "SectorProjects", proid, 
						proid,3,"项目添加成功！项目名："+sectorprojects.getProjectname());
				
				json.put("status", "success");
				json.put("ms", "添加项目成功!");
			}
			pw.print(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 删除项目
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
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHPROJECTDEL)) {
				json.put("status", "fail");
				json.put("ms", "删除项目失败,权限不足,请联系管理员!");
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
				JSONArray jsonarr = JSONArray.parseArray(jsonObject.getString("proids"));

				String status="fail";
				String ms="删除项目失败!";
				for (int i = 0; i < jsonarr.size(); i++) {
					int id = Integer.valueOf(jsonarr.get(i).toString());
					SectorProjects sectorproject = sectorprojectsservice.loadob(id);
					
					if(null!=sectorproject){
						
						//检查质量模块项目关联
						int qarows = sectorprojectsservice.projectrow(id);
						if(qarows>=1){
							status="fail";
							ms=sectorproject.getProjectname()+"有关联质量模块的记录，不能删除!";
							break;
						}
						
						//检查质量模块项目关联
						List <TestClient> tcrows = tcservice.getClientListForProid(id);
						if(tcrows.size()>=1){
							status="fail";
							ms=sectorproject.getProjectname()+"有关联客户端IP，不能删除!";
							break;
						}
						
						//检查调度任务项目关联
						TestJobs testjobs = new TestJobs();
						testjobs.setPlanproj(sectorproject.getProjectname());
						int jobrows = testJobsService.findRows(testjobs);
						if(jobrows>=1){
							status="fail";
							ms=sectorproject.getProjectname()+"有关联自动化调度任务的记录，不能删除!";
							break;
						}
						
						//检查测试计划项目关联
						ProjectPlan plan = new ProjectPlan();
						plan.setProjectid(id);
						int planrows = projectplanservice.findRows(plan);
						if(planrows>=1){
							status="fail";
							ms=sectorproject.getProjectname()+"有关联测试计划的记录，不能删除!";
							break;
						}
						
						//检查测试用例项目关联
						ProjectCase cases = new ProjectCase();
						cases.setProjectid(id);
						int caserows = projectcaseservice.findRows(cases);
						if(caserows>=1){
							status="fail";
							ms=sectorproject.getProjectname()+"有关联测试用例的记录，不能删除!";
							break;
						}
						
						//检查协议模板项目关联
						ProjectProtocolTemplate ppt = new ProjectProtocolTemplate();
						ppt.setProjectid(id);
						int templaterows = ptemplateservice.findRows(ppt);
						if(templaterows>=1){
							status="fail";
							ms=sectorproject.getProjectname()+"有关联协议模板的记录，不能删除!";
							break;
						}
						
						//检查用例公共参数项目关联
                        PublicCaseParams pcp = new PublicCaseParams();
                        pcp.setProjectid(id);
						int pcprows = pcpservice.findRows(pcp);
						if(pcprows>=1){
							status="fail";
							ms=sectorproject.getProjectname()+"有关联用例公共参数的记录，不能删除!";
							break;
						}
						
					}
					
					operationlogservice.delete(id);
					sectorprojectsservice.delete(sectorproject);
					
					if(sectorproject.getProjecttype()==0){
						List<SectorProjects> qaprolist=QueueListener.qa_projlist;
						for(int j=0;j<qaprolist.size();j++){
							SectorProjects sp=qaprolist.get(j);
							if(sp.getProjectid()==sectorproject.getProjectid()){
								qaprolist.remove(j);
								break;
							}
						}
					}else if(sectorproject.getProjecttype()==1){
						List<SectorProjects> prolist=QueueListener.projlist;
						for(int j=0;j<prolist.size();j++){
							SectorProjects sp=prolist.get(j);
							if(sp.getProjectid()==sectorproject.getProjectid()){
								prolist.remove(j);
								break;
							}
						}
					}
					
					operationlogservice.add(req, "SectorProjects", id, 
							99,0,"项目信息删除成功！项目名："+sectorproject.getProjectname());
					status="success";
					ms="删除项目成功!";
				}
				json.put("status", status);
				json.put("ms", ms);
			}
			pw.print(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
