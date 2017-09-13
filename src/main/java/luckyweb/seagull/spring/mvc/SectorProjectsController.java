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

import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.spring.entity.ProjectCase;
import luckyweb.seagull.spring.entity.ProjectPlan;
import luckyweb.seagull.spring.entity.SecondarySector;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.entity.TestCasedetail;
import luckyweb.seagull.spring.entity.TestJobs;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.ProjectCaseService;
import luckyweb.seagull.spring.service.ProjectPlanService;
import luckyweb.seagull.spring.service.SecondarySectorService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.spring.service.TestJobsService;
import luckyweb.seagull.util.StrLib;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
	
	@Resource(name = "operationlogService")
	private OperationLogService operationlogservice;
	
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
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
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
		String RecordJson = StrLib.listToJson(projects);
		// 得到总记录数
		int total = sectorprojectsservice.findRows(sectorprojects);
		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total-1);
		json.put("rows", RecordJson);
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
			if (!UserLoginController.permissionboolean(req, "pro_3")) {
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
						sectorprojects.getProjectid(),"项目信息修改成功！项目名"+sectorprojects.getProjectname());
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
			if (!UserLoginController.permissionboolean(req, "pro_1")) {
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
						proid,"项目添加成功！项目名："+sectorprojects.getProjectname());
				
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
			if (!UserLoginController.permissionboolean(req, "pro_2")) {
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
				JSONObject jsonObject = JSONObject.fromObject(sb.toString());
				JSONArray jsonarr = JSONArray.fromObject(jsonObject.getString("proids"));

				String status="fail";
				String ms="删除项目失败!";
				for (int i = 0; i < jsonarr.size(); i++) {
					int id = Integer.valueOf(jsonarr.get(i).toString());
					SectorProjects sectorproject = sectorprojectsservice.loadob(id);
					
					if(null!=sectorproject&&sectorproject.getProjecttype()==0){
						int rows = sectorprojectsservice.projectrow(id);
						if(rows>=1){
							status="fail";
							ms=sectorproject.getProjectname()+"有关联质量模块的记录，不能删除!";
							break;
						}
					}
					
					if(null!=sectorproject&&sectorproject.getProjecttype()==1){
						TestJobs testjobs = new TestJobs();
						testjobs.setPlanproj(sectorproject.getProjectname());
						int rows = testJobsService.findRows(testjobs);
						if(rows>=1){
							status="fail";
							ms=sectorproject.getProjectname()+"有关联自动化调度任务的记录，不能删除!";
							break;
						}
					}
					
					if(null!=sectorproject&&sectorproject.getProjecttype()==0){
						ProjectPlan plan = new ProjectPlan();
						plan.setProjectid(id);
						int rows = projectplanservice.findRows(plan);
						if(rows>=1){
							status="fail";
							ms=sectorproject.getProjectname()+"有关联测试计划的记录，不能删除!";
							break;
						}
					}
					
					if(null!=sectorproject&&sectorproject.getProjecttype()==0){
						ProjectCase cases = new ProjectCase();
						cases.setProjectid(id);
						int rows = projectcaseservice.findRows(cases);
						if(rows>=1){
							status="fail";
							ms=sectorproject.getProjectname()+"有关联测试用例的记录，不能删除!";
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
							99,"项目信息删除成功！项目名："+sectorproject.getProjectname());
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
