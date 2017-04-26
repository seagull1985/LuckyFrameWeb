package luckyweb.seagull.spring.mvc;

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

import luckyweb.seagull.spring.entity.Accident;
import luckyweb.seagull.spring.entity.ProjectVersion;
import luckyweb.seagull.spring.entity.SecondarySector;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.entity.TestCasedetail;
import luckyweb.seagull.spring.entity.TestJobs;
import luckyweb.seagull.spring.entity.UserInfo;
import luckyweb.seagull.spring.service.AccidentService;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.ProjectsVersionService;
import luckyweb.seagull.spring.service.SecondarySectorService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.spring.service.TestJobsService;
import luckyweb.seagull.util.Endecrypt;
import luckyweb.seagull.util.StrLib;

@Controller
@RequestMapping("/sectorProjects")
public class SectorProjectsController {
	
	private int allPage;
	private int pageSize = 20;
	private int allRows;
	private int page = 1;
	private int offset;
	
	@Resource(name = "sectorprojectsService")
	private SectorProjectsService sectorprojectsservice;
	
	@Resource(name = "secondarysectorService")
	private SecondarySectorService secondarysectorservice;

	public SectorProjectsService getsectorprojectsService() {
		return sectorprojectsservice;
	}

	public void setSectorProjectsService(SectorProjectsService sectorprojectsservice) {
		this.sectorprojectsservice = sectorprojectsservice;
	}

	@Resource(name = "testJobsService")
	private TestJobsService	 testJobsService;
	
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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list.do")
	public String list(HttpServletRequest req, SectorProjects sectorprojects, Model model)
			throws Exception {
		model.addAttribute("sectorprojects", sectorprojects);
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
			allRows = sectorprojectsservice.findRows(sectorprojects);
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

			// 调度列表
			List<SecondarySector> secondarySector = secondarysectorservice.findSecotorList();
			model.addAttribute("secondarySector", secondarySector);
			
			List<SectorProjects> sssMap = sectorprojectsservice.findByPage(sectorprojects, offset,
					pageSize);
			model.addAttribute("splist", sssMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/sectorProjects/list.do");
			return "error";
		}
		return "/jsp/base/sectorprojects";
	}
	
	/**
	 * 项目添加
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
	public String add(@Valid @ModelAttribute("sectorprojects") SectorProjects sectorprojects, BindingResult br, Model model,
	        HttpServletRequest req, HttpServletResponse rsp) throws Exception
	{
		try
		{
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			
			if(!UserLoginController.permissionboolean(req, "pro_1")){
				model.addAttribute("sectorprojects", new SectorProjects());
				model.addAttribute("url", "/sectorProjects/list.do");
				model.addAttribute("message", "当前用户无权限添加项目，请联系管理员！");
				return "success";
			}

			String retVal = "/jsp/base/projects_add";
			if (req.getMethod().equals("POST"))
			{
				if (br.hasErrors())
				{
					return retVal;
				}
				String message = "";
				
				if(StrLib.isEmpty(sectorprojects.getProjectname())){
					message = "项目名称不能为空！";
				    model.addAttribute("secondarysector", secondarysectorservice.listall());
					model.addAttribute("message", message);
					return retVal;
				}
				if(StrLib.isEmpty(sectorprojects.getProjectmanager())){
					message = "项目经理不能为空！";
				    model.addAttribute("secondarysector", secondarysectorservice.listall());
					model.addAttribute("message", message);
					return retVal;
				}
				if(sectorprojects.getSecondarySector().getSectorid()==0){
					message = "请至少选择一个所属部门！";
				    model.addAttribute("secondarysector", secondarysectorservice.listall());
					model.addAttribute("message", message);
					return retVal;
				}
				SecondarySector ss = secondarysectorservice.load(sectorprojects.getSecondarySector().getSectorid());
				sectorprojects.setSecondarySector(ss);
				
				int proid = sectorprojectsservice.add(sectorprojects);
				
				operationlogservice.add(req, "SectorProjects", proid, 
						proid,"项目添加成功！项目名："+sectorprojects.getProjectname());
				
				model.addAttribute("message", "添加项目成功");
				model.addAttribute("url", "/sectorProjects/list.do");
				return "success";

			}
			    model.addAttribute("secondarysector", secondarysectorservice.listall());
				return retVal;

		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/sectorProjects/list.do");
			return "error";
		}

	}

	/**
	 * 删除项目
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete.do", method = RequestMethod.GET)
	public String delete(Model model,HttpServletRequest req) throws Exception
	{
		if(!UserLoginController.permissionboolean(req, "pro_2")){
			model.addAttribute("sectorprojects", new SectorProjects());
			model.addAttribute("url", "/sectorProjects/list.do");
			model.addAttribute("message", "当前用户无权限删除项目，请联系管理员！");
			return "success";
		}
		
		int projectid = Integer.valueOf(req.getParameter("projectid"));
		SectorProjects sectorprojects = sectorprojectsservice.loadob(projectid);
		sectorprojects.setSectorid(sectorprojects.getSecondarySector().getSectorid());

		if(null!=sectorprojects&&sectorprojects.getProjecttype()==0){
			int rows = sectorprojectsservice.projectrow(projectid);
			if(rows>=1){
				String message = "此项目关联了质量管理模块信息，不能删除！";
				model.addAttribute("sectorprojects", new SectorProjects());
				model.addAttribute("message", message);
				model.addAttribute("url", "/sectorProjects/list.do");
				return "success";
			}
		}
		
		if(null!=sectorprojects&&sectorprojects.getProjecttype()==1){
			TestJobs testjobs = new TestJobs();
			testjobs.setPlanproj(sectorprojects.getProjectname());
			int rows = testJobsService.findRows(testjobs);
			if(rows>=1){
				String message = "此项目有关联自动化调度任务，不能删除！";
				model.addAttribute("sectorprojects", new SectorProjects());
				model.addAttribute("message", message);
				model.addAttribute("url", "/sectorProjects/list.do");
				return "success";
			}
		}
		
		try
		{		
			operationlogservice.delete(projectid);
			sectorprojectsservice.delete(sectorprojects);
		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/sectorProjects/list.do");
			return "error";
		}
		
		operationlogservice.add(req, "SectorProjects", projectid, 
				99,"项目信息删除成功！项目名："+sectorprojects.getProjectname());

		String message = "删除项目成功！";
		model.addAttribute("sectorprojects", new SectorProjects());
		model.addAttribute("message", message);
		model.addAttribute("url", "/sectorProjects/list.do");
		return "success";
	}
	
	/**
	 * 
	 * 根据id更新项目信息
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update.do")
	public String update(@Valid @ModelAttribute("sectorprojects") SectorProjects sectorprojects, BindingResult br,
	        Model model, HttpServletRequest req) throws Exception
	{
		req.setCharacterEncoding("utf-8");
		int projectid = Integer.valueOf(req.getParameter("projectid"));
		
		if(!UserLoginController.permissionboolean(req, "pro_3")){
			model.addAttribute("sectorprojects", new SectorProjects());
			model.addAttribute("url", "/sectorProjects/list.do");
			model.addAttribute("message", "当前用户无权限修改项目信息，请联系管理员！");
			return "success";
		}
		try{
		String retVal = "/jsp/base/projects_add";
		if (req.getMethod().equals("POST"))
		{
			if (br.hasErrors())
			{
				return retVal;
			}
			String message = "";
			if(StrLib.isEmpty(sectorprojects.getProjectname())){
				message = "项目名称不能为空！";
			    model.addAttribute("secondarysector", secondarysectorservice.listall());
				model.addAttribute("message", message);
				return retVal;
			}
			if(StrLib.isEmpty(sectorprojects.getProjectmanager())){
				message = "项目经理不能为空！";
			    model.addAttribute("secondarysector", secondarysectorservice.listall());
				model.addAttribute("message", message);
				return retVal;
			}
			if(sectorprojects.getSecondarySector().getSectorid()==0){
				message = "请至少选择一个所属部门！";
			    model.addAttribute("secondarysector", secondarysectorservice.listall());
				model.addAttribute("message", message);
				return retVal;
			}
			
			SecondarySector ss = secondarysectorservice.load(sectorprojects.getSecondarySector().getSectorid());
			sectorprojects.setSecondarySector(ss);
			
			sectorprojectsservice.modify(sectorprojects);
			
			operationlogservice.add(req, "SectorProjects", sectorprojects.getProjectid(), 
					sectorprojects.getProjectid(),"项目信息修改成功！项目名"+sectorprojects.getProjectname());

			
			model.addAttribute("message", "修改项目信息成功");
			model.addAttribute("url", "/sectorProjects/list.do");
			return "success";

		}	
		SectorProjects sss = sectorprojectsservice.loadob(projectid);
		sss.setSectorid(sss.getSecondarySector().getSectorid());
		
	    model.addAttribute("secondarysector", secondarysectorservice.listall());
		model.addAttribute("sectorprojects", sss);
		return "/jsp/base/projects_update";

	}
	catch (Exception e){
		model.addAttribute("message", e.getMessage());
		model.addAttribute("url", "/sectorProjects/list.do");
		return "error";
	 }
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
