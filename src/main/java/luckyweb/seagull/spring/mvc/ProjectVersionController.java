package luckyweb.seagull.spring.mvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
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

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.spring.entity.Barchart3;
import luckyweb.seagull.spring.entity.ProjectVersion;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.entity.TestCasedetail;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.ProjectsVersionService;
import luckyweb.seagull.spring.service.SectorProjectsService;
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
@RequestMapping("/projectVersion")
public class ProjectVersionController {
	
	private int allPage;
	private int pageSize = 20;
	private int allRows;
	private int page = 1;
	private int offset;
	
	private final String zentaoWeb_ipport="10.211.19.75";
	
	@Resource(name = "projectversionService")
	private ProjectsVersionService projectsversionservice;
	
	@Resource(name = "sectorprojectsService")
	private SectorProjectsService sectorprojectsService;

	public ProjectsVersionService getprojectsversionService() {
		return projectsversionservice;
	}

	@Resource(name = "operationlogService")
	private OperationLogService operationlogservice;

	public OperationLogService getOperationlogService() {
		return operationlogservice;
	}
	
	@RequestMapping(value = "/projectversion.do")
	public String qualityshow(TestCasedetail caseDetail, HttpServletRequest req,HttpServletResponse rsp,
			Model model) throws Exception {
		return "/jsp/projectversion/projectversion";
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

		try {
			int projectid = 99;

			List<SectorProjects> prolist = QueueListener.qa_projlist;
			model.addAttribute("projects", prolist);
			model.addAttribute("projectid", projectid);
			model.addAttribute("zentaoip", zentaoWeb_ipport);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/projectVersion/load.do");
			return "error";
		}
		return "/jsp/projectversion/projectversion";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list.do")
	private void ajaxGetSellRecord(Integer limit, Integer offset, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		PrintWriter pw = response.getWriter();
		String search = request.getParameter("search");
		String projectid = request.getParameter("projectid");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		ProjectVersion projectversion = new ProjectVersion();
		if (null == offset && null == limit) {
			offset = 0;
		}
		if (null == limit || limit == 0) {
			limit = 10;
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(search)) {
			projectversion.setVersionnumber(search);
			projectversion.setImprint(search);
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(projectid)) {
			projectversion.setProjectid(Integer.valueOf(projectid));
		}
		
		if (!StrLib.isEmpty(startDate)) {
			projectversion.setStartactually_launchdate(startDate);
		}
			
		if (!StrLib.isEmpty(endDate)) {
			projectversion.setEndactually_launchdate(endDate);
		}
		
		List<ProjectVersion> projectversions = projectsversionservice.findByPage(projectversion, offset, limit);

		// 转换成json字符串
		JSONArray recordJson = StrLib.listToJson(projectversions);
		// 得到总记录数
		int total = projectsversionservice.findRows(projectversion);
		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", recordJson);
		pw.print(json.toString());
	}
	
	/**
	 * 版本添加
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
	public String add(@Valid @ModelAttribute("projectversion") ProjectVersion projectversion, BindingResult br, Model model,
	        HttpServletRequest req, HttpServletResponse rsp) throws Exception
	{
		try
		{
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			
			if(!UserLoginController.permissionboolean(req, PublicConst.AUTHPVERSIONADD)){
				model.addAttribute("projectversion", new ProjectVersion());
				model.addAttribute("url", "/projectVersion/load.do");
				model.addAttribute("message", "当前用户无权限添加版本信息，请联系管理员！");
				return "success";
			}

			String retVal = "/jsp/projectversion/projectversion_add";
			if (PublicConst.REQPOSTTYPE.equals(req.getMethod()))
			{
				if (br.hasErrors())
				{
					return retVal;
				}
				String message = "";
				
				if(projectversion.getProjectid()==0){
					message = "请选择项目!";
					model.addAttribute("message", message);
					return retVal;
				}else{
					if(!UserLoginController.oppidboolean(req, projectversion.getProjectid())){
						SectorProjects sp=sectorprojectsService.loadob(projectversion.getProjectid());
						model.addAttribute("message", "当前用户无权限添加项目【"+sp.getProjectname()+"】版本信息，请联系管理员！");
						return retVal;
					}
				}
				
				if("".equals(projectversion.getActually_testend())||"".equals(projectversion.getPlan_testend())){
					message = "版本已完成,请选择测试计划结束日期以及测试实际结束日期!";
					model.addAttribute("message", message);
					return retVal;
				}		
				
				if(projectversion.getPlan_demand()==0){
					message = "请填写计划上线需求数!";
					model.addAttribute("message", message);
					return retVal;
				}
				if(projectversion.getActually_demand()==0){
					message = "请填写实际上线需求数!";
					model.addAttribute("message", message);
					return retVal;
				}
				if(!checkdate(projectversion.getPlan_devstart(),projectversion.getPlan_devend())){
					message = "计划开发开始日期大于计划开发结束日期!";
					model.addAttribute("message", message);
					return retVal;
				}
				if(!checkdate(projectversion.getActually_devstart(),projectversion.getActually_devend())){
					message = "实际开发开始日期大于实际开发结束日期!";
					model.addAttribute("message", message);
					return retVal;
				}
				if(!checkdate(projectversion.getPlan_teststart(),projectversion.getPlan_testend())){
					message = "计划测试开始日期大于计划测试结束日期!";
					model.addAttribute("message", message);
					return retVal;
				}
				if(!checkdate(projectversion.getActually_teststart(),projectversion.getActually_testend())){
					message = "实际测试开始日期大于实际测试结束日期!";
					model.addAttribute("message", message);
					return retVal;
				}
				
				int actuallydevinterval = interval(projectversion.getActually_devstart(),projectversion.getActually_devend(),1);
				int actuallytestinterval = interval(projectversion.getActually_teststart(),projectversion.getActually_testend(),1);
				int plandevinterval = interval(projectversion.getPlan_devstart(),projectversion.getPlan_devend(),1);
				int plantestinterval = interval(projectversion.getPlan_teststart(),projectversion.getPlan_testend(),1);
				//版本达成日期偏差，结实结束日期直接使用测试的实际完成日期
				//int launchinterval = interval(projectversion.getPlan_launchdate(),projectversion.getActually_launchdate(),0);
				int launchinterval = interval(projectversion.getPlan_testend(),projectversion.getActually_testend(),0);
				
				int membernum = member(projectversion.getDev_member()).length+member(projectversion.getTest_member()).length;
				int devhuman = member(projectversion.getDev_member()).length*actuallydevinterval;
				int testhuman = member(projectversion.getTest_member()).length*actuallytestinterval;
				projectversion.setHuman_costdev(String.valueOf(devhuman));
				projectversion.setHuman_costtest(String.valueOf(testhuman));
				//耗费人力资源
				if(membernum>0){    
					projectversion.setHuman_cost(String.valueOf(devhuman+testhuman));
				}else{
					projectversion.setHuman_cost(projectversion.getHuman_cost());
				}
				
				//开发效率
				if(member(projectversion.getDev_member()).length>0&&actuallydevinterval!=0){ 
					double perDev = Double.valueOf(new DecimalFormat("#.00").format((double)(projectversion.getCodeline())/devhuman));
					projectversion.setPer_dev(String.valueOf(perDev));
				}else{
					projectversion.setPer_dev(projectversion.getPer_dev());
				}
				
				//测试效率
				if(member(projectversion.getTest_member()).length>0&&actuallytestinterval!=0){
					double perTest = Double.valueOf(new DecimalFormat("#.00").format((double)projectversion.getTestcasenum()/testhuman));
					projectversion.setPer_test(String.valueOf(perTest));					
				}else{
					projectversion.setPer_test(projectversion.getPer_test());
				}
				
				//开发工期偏差+延期
				if(actuallydevinterval!=0&&plandevinterval!=0){
					double devTimeDeviation = Double.valueOf(new DecimalFormat("#.00").format(((double)(actuallydevinterval-plandevinterval)/plandevinterval)*100));
					projectversion.setDevtime_deviation(String.valueOf(devTimeDeviation));
					projectversion.setDevdelay_days(String.valueOf(interval(projectversion.getPlan_devend(),projectversion.getActually_devend(),0)));
					
				}else{
					projectversion.setDevtime_deviation(projectversion.getDevtime_deviation());
					projectversion.setDevdelay_days(projectversion.getDevdelay_days());
				}
				
				//测试工期偏差+延期
				if(actuallydevinterval!=0&&plandevinterval!=0){
					double testTimeDeviation = Double.valueOf(new DecimalFormat("#.00").format(((double)(actuallytestinterval-plantestinterval)/plantestinterval)*100));
					projectversion.setTesttime_deviation(String.valueOf(testTimeDeviation));
					projectversion.setTestdelay_days(String.valueOf(interval(projectversion.getPlan_testend(),projectversion.getActually_testend(),0)));
					
				}else{
					projectversion.setTesttime_deviation(projectversion.getTesttime_deviation());
					projectversion.setTestdelay_days(projectversion.getTestdelay_days());
				}
				
				//版本达成进度偏差+延期
				if(actuallydevinterval!=0&&plandevinterval!=0){
					double launchdeviation = Double.valueOf(new DecimalFormat("#.00").format(((double)launchinterval/(actuallydevinterval+actuallytestinterval))*100));
					projectversion.setProtime_deviation(String.valueOf(launchdeviation));
					projectversion.setProdelay_days(String.valueOf(launchinterval));
					
				}else{
					projectversion.setProtime_deviation(projectversion.getProtime_deviation());
					projectversion.setProdelay_days(projectversion.getProdelay_days());
				}
				
				
				projectversion.setVersionnumber(projectversion.getVersionnumber());
				
				//直接取测试完成日期
				projectversion.setPlan_launchdate(projectversion.getPlan_testend());
				projectversion.setActually_launchdate(projectversion.getActually_testend());
				
				projectversion.setImprint(projectversion.getImprint());
				projectversion.setRemark(projectversion.getRemark());
				projectversion.setPlan_demand(projectversion.getPlan_demand());
				projectversion.setActually_demand(projectversion.getActually_demand());
				projectversion.setCodestandard_yz(projectversion.getCodestandard_yz());
				projectversion.setCodestandard_zd(projectversion.getCodestandard_zd());
				projectversion.setCodestandard_zy(projectversion.getCodestandard_zy());
				projectversion.setTestcasenum(projectversion.getTestcasenum());
				projectversion.setCodeline(projectversion.getCodeline());
				projectversion.setChangetestingreturn(projectversion.getChangetestingreturn());
				projectversion.setDev_member(projectversion.getDev_member());
				projectversion.setTest_member(projectversion.getTest_member());
				projectversion.setZt_versionlink(projectversion.getZt_versionlink());
				projectversion.setVersiontype(1);
				
				double divalue = projectversion.getBug_zm()*10+projectversion.getBug_yz()*3+projectversion.getBug_yb()+projectversion.getBug_ts()*0.1;
				
				if(projectversion.getCodeline()!=0){
					projectversion.setCode_DI(Double.valueOf(new DecimalFormat("#.00").format(divalue/projectversion.getCodeline())).toString());
				}else{
					projectversion.setCode_DI("0");
				}
				
				if(projectversion.getActually_demand()!=0&&projectversion.getPlan_demand()!=0){
					projectversion.setPerdemand(Double.valueOf(new DecimalFormat("#.00").format(((double)projectversion.getActually_demand()/projectversion.getPlan_demand())*100)));
				}else{
					projectversion.setPerdemand(0.00);
				}
				
				projectversion.setPlan_devstart(projectversion.getPlan_devstart());
				projectversion.setPlan_devend(projectversion.getPlan_devend());
				projectversion.setActually_devstart(projectversion.getActually_devstart());
				projectversion.setActually_devend(projectversion.getActually_devend());				
				projectversion.setPlan_teststart(projectversion.getPlan_teststart());
				projectversion.setPlan_testend(projectversion.getPlan_testend());
				projectversion.setActually_teststart(projectversion.getActually_teststart());
				projectversion.setActually_testend(projectversion.getActually_testend());
				projectversion.setQualityreview(projectversion.getQualityreview());
				projectversion.setBug_zm(projectversion.getBug_zm());
				projectversion.setBug_yz(projectversion.getBug_yz());
				projectversion.setBug_yb(projectversion.getBug_yb());
				projectversion.setBug_ts(projectversion.getBug_ts());
				
				
				SectorProjects p = new SectorProjects();
				p.setProjectid(projectversion.getProjectid());
				projectversion.setSectorProjects(p);
				
				int versionid = projectsversionservice.add(projectversion);
				
				operationlogservice.add(req, "QA_PROJECTVERSION", versionid, 
						projectversion.getProjectid(),5,"版本信息添加成功！版本号："+projectversion.getVersionnumber());
				
				model.addAttribute("message", "添加成功");
				model.addAttribute("url", "/projectVersion/load.do");
				return retVal;

			}
				model.addAttribute("projectversion", projectversion);
				model.addAttribute("projects", QueueListener.qa_projlist);
				return retVal;

		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/projectVersion/load.do");
			return "error";
		}

	}

	/**
	 * 
	 * 根据versionid更新版本信息
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update.do")
	public String update(@Valid @ModelAttribute("projectversion") ProjectVersion projectversion, BindingResult br,
	        Model model, HttpServletRequest req) throws Exception
	{
		req.setCharacterEncoding("utf-8");		
		int versionid = Integer.valueOf(req.getParameter("versionid"));
		String retVal = "/jsp/projectversion/projectversion_add";
		
		if(!UserLoginController.permissionboolean(req, PublicConst.AUTHPVERSIONMOD)){
			model.addAttribute("projectversion", new ProjectVersion());
			model.addAttribute("url", "/projectVersion/load.do");
			model.addAttribute("message", "当前用户无权限操作修改版本信息，请联系管理员！");
			return "success";
		}
			
		model.addAttribute("projects", QueueListener.qa_projlist);
		if (PublicConst.REQPOSTTYPE.equals(req.getMethod()))
		{
			if (br.hasErrors())
			{
				return retVal;
			}
			String message = "";
			
			if(projectversion.getProjectid()==0){
				message = "请选择项目!";
				model.addAttribute("message", message);
				return retVal;
			}else{
				if(!UserLoginController.oppidboolean(req, projectversion.getProjectid())){
					SectorProjects sp=sectorprojectsService.loadob(projectversion.getProjectid());
					model.addAttribute("message", "当前用户无权限操作修改项目【"+sp.getProjectname()+"】版本信息，请联系管理员！");
					return retVal;
				}
			}
			
			if("".equals(projectversion.getPlan_testend())){
				message = "请选择测试计划结束日期!";
				model.addAttribute("message", message);
				return retVal;
			}				
			
			if("".equals(projectversion.getActually_testend())&&projectversion.getVersiontype()==1){
				message = "版本已完成,请选择测试实际结束日期!";
				model.addAttribute("message", message);
				return retVal;
			}

			if(projectversion.getPlan_demand()==0){
				message = "请填写计划上线需求数!";
				model.addAttribute("message", message);
				return retVal;
			}
			if(projectversion.getActually_demand()==0&&projectversion.getVersiontype()==1){
				message = "请填写实际上线需求数!";
				model.addAttribute("message", message);
				return retVal;
			}
			if(projectversion.getTestcasenum()==0&&projectversion.getVersiontype()==1){
				message = "请填写测试用例数!";
				model.addAttribute("message", message);
				return retVal;
			}
			if(!checkdate(projectversion.getPlan_devstart(),projectversion.getPlan_devend())){
				message = "计划开发开始日期大于计划开发结束日期!";
				model.addAttribute("message", message);
				return retVal;
			}
			if(!checkdate(projectversion.getActually_devstart(),projectversion.getActually_devend())&&projectversion.getVersiontype()==1){
				message = "实际开发开始日期大于实际开发结束日期!";
				model.addAttribute("message", message);
				return retVal;
			}
			if(!checkdate(projectversion.getPlan_teststart(),projectversion.getPlan_testend())){
				message = "计划测试开始日期大于计划测试结束日期!";
				model.addAttribute("message", message);
				return retVal;
			}
			if(!checkdate(projectversion.getActually_teststart(),projectversion.getActually_testend())&&projectversion.getVersiontype()==1){
				message = "实际测试开始日期大于实际测试结束日期!";
				model.addAttribute("message", message);
				return retVal;
			}
			
			int actuallydevinterval = interval(projectversion.getActually_devstart(),projectversion.getActually_devend(),1);
			int actuallytestinterval = interval(projectversion.getActually_teststart(),projectversion.getActually_testend(),1);
			int plandevinterval = interval(projectversion.getPlan_devstart(),projectversion.getPlan_devend(),1);
			int plantestinterval = interval(projectversion.getPlan_teststart(),projectversion.getPlan_testend(),1);
			//其际版本偏差的计算方式，实际结束日期直接使用测试的实际结束日期
			int launchinterval = interval(projectversion.getPlan_testend(),projectversion.getActually_testend(),0);
			
			int membernum = member(projectversion.getDev_member()).length+member(projectversion.getTest_member()).length;
			int devhuman = member(projectversion.getDev_member()).length*actuallydevinterval;
			int testhuman = member(projectversion.getTest_member()).length*actuallytestinterval;
			projectversion.setHuman_costdev(String.valueOf(devhuman));
			projectversion.setHuman_costtest(String.valueOf(testhuman));
			 //耗费人力资源
			if(membernum>0){   
				projectversion.setHuman_cost(String.valueOf(devhuman+testhuman));
			}else{
				projectversion.setHuman_cost(projectversion.getHuman_cost());
			}
			
			//开发效率
			if(member(projectversion.getDev_member()).length>0&&actuallydevinterval!=0){ 
				double perDev = Double.valueOf(new DecimalFormat("#.00").format((double)(projectversion.getCodeline())/devhuman));
				projectversion.setPer_dev(String.valueOf(perDev));
			}else{
				projectversion.setPer_dev(projectversion.getPer_dev());
			}
			
			//测试效率
			if(member(projectversion.getTest_member()).length>0&&actuallytestinterval!=0){
				double perTest = Double.valueOf(new DecimalFormat("#.00").format((double)projectversion.getTestcasenum()/testhuman));
				projectversion.setPer_test(String.valueOf(perTest));					
			}else{
				projectversion.setPer_test(projectversion.getPer_test());
			}
			
			//开发工期偏差+延期
			if(actuallydevinterval!=0&&plandevinterval!=0){
				double devTimeDeviation = Double.valueOf(new DecimalFormat("#.00").format(((double)(actuallydevinterval-plandevinterval)/plandevinterval)*100));
				projectversion.setDevtime_deviation(String.valueOf(devTimeDeviation));
				projectversion.setDevdelay_days(String.valueOf(interval(projectversion.getPlan_devend(),projectversion.getActually_devend(),0)));
				
			}else{
				projectversion.setDevtime_deviation(projectversion.getDevtime_deviation());
				projectversion.setDevdelay_days(projectversion.getDevdelay_days());
			}
			
			//测试工期偏差+延期
			if(actuallydevinterval!=0&&plandevinterval!=0){
				double testTimeDeviation = Double.valueOf(new DecimalFormat("#.00").format(((double)(actuallytestinterval-plantestinterval)/plantestinterval)*100));
				projectversion.setTesttime_deviation(String.valueOf(testTimeDeviation));
				projectversion.setTestdelay_days(String.valueOf(interval(projectversion.getPlan_testend(),projectversion.getActually_testend(),0)));
				
			}else{
				projectversion.setTesttime_deviation(projectversion.getTesttime_deviation());
				projectversion.setTestdelay_days(projectversion.getTestdelay_days());
			}
			
			//版本达成进度偏差+延期
			if(actuallydevinterval!=0&&plandevinterval!=0){
				double launchdeviation = Double.valueOf(new DecimalFormat("#.00").format(((double)launchinterval/(actuallydevinterval+actuallytestinterval))*100));
				projectversion.setProtime_deviation(String.valueOf(launchdeviation));
				projectversion.setProdelay_days(String.valueOf(launchinterval));
				
			}else{
				projectversion.setProtime_deviation(projectversion.getProtime_deviation());
				projectversion.setProdelay_days(projectversion.getProdelay_days());
			}
			
			projectversion.setVersionnumber(projectversion.getVersionnumber());
			
			//直接取测试完成日期
			projectversion.setPlan_launchdate(projectversion.getPlan_testend());
			projectversion.setActually_launchdate(projectversion.getActually_testend());
			
			projectversion.setImprint(projectversion.getImprint());
			projectversion.setRemark(projectversion.getRemark());
			projectversion.setPlan_demand(projectversion.getPlan_demand());
			projectversion.setActually_demand(projectversion.getActually_demand());
			projectversion.setCodestandard_yz(projectversion.getCodestandard_yz());
			projectversion.setCodestandard_zd(projectversion.getCodestandard_zd());
			projectversion.setCodestandard_zy(projectversion.getCodestandard_zy());
			projectversion.setTestcasenum(projectversion.getTestcasenum());
			projectversion.setCodeline(projectversion.getCodeline());
			projectversion.setChangetestingreturn(projectversion.getChangetestingreturn());
			projectversion.setDev_member(projectversion.getDev_member());
			projectversion.setTest_member(projectversion.getTest_member());
			projectversion.setZt_versionlink(projectversion.getZt_versionlink());
			projectversion.setVersiontype(projectversion.getVersiontype());
			
			double divalue = projectversion.getBug_zm()*10+projectversion.getBug_yz()*3+projectversion.getBug_yb()+projectversion.getBug_ts()*0.1;
			
			if(projectversion.getCodeline()!=0){
				projectversion.setCode_DI(Double.valueOf(new DecimalFormat("#.00").format(divalue/projectversion.getCodeline())).toString());
			}else{
				projectversion.setCode_DI("0");
			}
			
			if(projectversion.getActually_demand()!=0&&projectversion.getPlan_demand()!=0){
				projectversion.setPerdemand(Double.valueOf(new DecimalFormat("#.00").format(((double)projectversion.getActually_demand()/projectversion.getPlan_demand())*100)));
			}else{
				projectversion.setPerdemand(0.00);
			}
			
			projectversion.setPlan_devstart(projectversion.getPlan_devstart());
			projectversion.setPlan_devend(projectversion.getPlan_devend());
			projectversion.setActually_devstart(projectversion.getActually_devstart());
			projectversion.setActually_devend(projectversion.getActually_devend());				
			projectversion.setPlan_teststart(projectversion.getPlan_teststart());
			projectversion.setPlan_testend(projectversion.getPlan_testend());
			projectversion.setActually_teststart(projectversion.getActually_teststart());
			projectversion.setActually_testend(projectversion.getActually_testend());
			projectversion.setQualityreview(projectversion.getQualityreview());
			projectversion.setBug_zm(projectversion.getBug_zm());
			projectversion.setBug_yz(projectversion.getBug_yz());
			projectversion.setBug_yb(projectversion.getBug_yb());
			projectversion.setBug_ts(projectversion.getBug_ts());
			
			SectorProjects p = new SectorProjects();
			p.setProjectid(projectversion.getProjectid());
			projectversion.setSectorProjects(p);
			
			projectsversionservice.modify(projectversion);
			
			operationlogservice.add(req, "QA_PROJECTVERSION", versionid, 
					projectversion.getProjectid(),1,"版本信息修改成功！版本号："+projectversion.getVersionnumber());
			
			model.addAttribute("message", "修改成功");
			model.addAttribute("url", "/projectVersion/load.do");
			return retVal;
		}
			
		//projectversion.setVersionid(versionid);		
		projectversion = projectsversionservice.load(versionid);
		projectversion.setProjectid(projectversion.getSectorProjects().getProjectid());
		
		model.addAttribute("projectversion", projectversion);
		return "/jsp/projectversion/projectversion_update";
	}
	
	/**
	 * 计划添加
	 * @param tj
	 * @param br
	 * @param model
	 * @param req
	 * @param rsp
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/addplan.do")
	public String addplan(@Valid @ModelAttribute("projectversion") ProjectVersion projectversion, BindingResult br, Model model,
	        HttpServletRequest req, HttpServletResponse rsp) throws Exception
	{
		try
		{
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			
			if(!UserLoginController.permissionboolean(req, PublicConst.AUTHPVERSIONPLANADD)){
				model.addAttribute("projectversion", new ProjectVersion());
				model.addAttribute("url", "/projectVersion/load.do");
				model.addAttribute("message", "当前用户无权限添加版本计划信息，请联系管理员！");
				return "success";
			}

			String retVal = "/jsp/projectversion/projectversion_addplan";
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
					return retVal;
				}
				String message = "";
				
				if(projectversion.getProjectid()==0){
					message = "请选择项目!";
					model.addAttribute("message", message);
					return retVal;
				}else{
					if(!UserLoginController.oppidboolean(req, projectversion.getProjectid())){
						SectorProjects sp=sectorprojectsService.loadob(projectversion.getProjectid());
						model.addAttribute("message", "当前用户无权限添加项目【"+sp.getProjectname()+"】版本计划信息，请联系管理员！");
						return retVal;
					}
				}
				
				if(projectversion.getPlan_demand()==0){
					message = "请填写计划上线需求数!";
					model.addAttribute("message", message);
					return retVal;
				}
								
				if("".equals(projectversion.getPlan_testend())){
					message = "请选择测试计划结束日期!";
					model.addAttribute("message", message);
					return retVal;
				}	
				
				int actuallydevinterval = interval(projectversion.getActually_devstart(),projectversion.getActually_devend(),1);
				int actuallytestinterval = interval(projectversion.getActually_teststart(),projectversion.getActually_testend(),1);
				int plandevinterval = interval(projectversion.getPlan_devstart(),projectversion.getPlan_devend(),1);
				int plantestinterval = interval(projectversion.getPlan_teststart(),projectversion.getPlan_testend(),1);
				//版本达成日期偏差，结实结束日期直接使用测试的实际完成日期
				//int launchinterval = interval(projectversion.getPlan_launchdate(),projectversion.getActually_launchdate(),0);
				int launchinterval = interval(projectversion.getPlan_launchdate(),projectversion.getActually_testend(),0);
				
				int membernum = member(projectversion.getDev_member()).length+member(projectversion.getTest_member()).length;
				int devhuman = member(projectversion.getDev_member()).length*actuallydevinterval;
				int testhuman = member(projectversion.getTest_member()).length*actuallytestinterval;
				projectversion.setHuman_costdev(String.valueOf(devhuman));
				projectversion.setHuman_costtest(String.valueOf(testhuman));
				 //耗费人力资源
				if(membernum>0){   
					projectversion.setHuman_cost(String.valueOf(devhuman+testhuman));
				}else{
					projectversion.setHuman_cost(projectversion.getHuman_cost());
				}
				
				//开发效率
				if(member(projectversion.getDev_member()).length>0&&actuallydevinterval!=0){ 
					double perDev = Double.valueOf(new DecimalFormat("#.00").format((double)(projectversion.getCodeline())/devhuman));
					projectversion.setPer_dev(String.valueOf(perDev));
				}else{
					projectversion.setPer_dev(projectversion.getPer_dev());
				}
				
				//测试效率
				if(member(projectversion.getTest_member()).length>0&&actuallytestinterval!=0){
					double perTest = Double.valueOf(new DecimalFormat("#.00").format((double)projectversion.getTestcasenum()/testhuman));
					projectversion.setPer_test(String.valueOf(perTest));					
				}else{
					projectversion.setPer_test(projectversion.getPer_test());
				}
				
				//开发工期偏差+延期
				if(actuallydevinterval!=0&&plandevinterval!=0){
					double devTimeDeviation = Double.valueOf(new DecimalFormat("#.00").format(((double)(actuallydevinterval-plandevinterval)/plandevinterval)*100));
					projectversion.setDevtime_deviation(String.valueOf(devTimeDeviation));
					projectversion.setDevdelay_days(String.valueOf(interval(projectversion.getPlan_devend(),projectversion.getActually_devend(),0)));
					
				}else{
					projectversion.setDevtime_deviation(projectversion.getDevtime_deviation());
					projectversion.setDevdelay_days(projectversion.getDevdelay_days());
				}
				
				//测试工期偏差+延期
				if(actuallydevinterval!=0&&plandevinterval!=0){
					double testTimeDeviation = Double.valueOf(new DecimalFormat("#.00").format(((double)(actuallytestinterval-plantestinterval)/plantestinterval)*100));
					projectversion.setTesttime_deviation(String.valueOf(testTimeDeviation));
					projectversion.setTestdelay_days(String.valueOf(interval(projectversion.getPlan_testend(),projectversion.getActually_testend(),0)));
					
				}else{
					projectversion.setTesttime_deviation(projectversion.getTesttime_deviation());
					projectversion.setTestdelay_days(projectversion.getTestdelay_days());
				}
				
				//版本达成进度偏差+延期
				if(actuallydevinterval!=0&&plandevinterval!=0){
					double launchdeviation = Double.valueOf(new DecimalFormat("#.00").format(((double)launchinterval/(actuallydevinterval+actuallytestinterval))*100));
					projectversion.setProtime_deviation(String.valueOf(launchdeviation));
					projectversion.setProdelay_days(String.valueOf(launchinterval));
					
				}else{
					projectversion.setProtime_deviation(projectversion.getProtime_deviation());
					projectversion.setProdelay_days(projectversion.getProdelay_days());
				}
				
				
				projectversion.setVersionnumber(projectversion.getVersionnumber());
				projectversion.setPlan_launchdate(projectversion.getPlan_launchdate());
				//直接取测试完成日期
				projectversion.setActually_launchdate(projectversion.getActually_testend());
				
				projectversion.setImprint(projectversion.getImprint());
				projectversion.setRemark(projectversion.getRemark());
				projectversion.setPlan_demand(projectversion.getPlan_demand());
				projectversion.setActually_demand(projectversion.getActually_demand());
				projectversion.setCodestandard_yz(0);
				projectversion.setCodestandard_zd(0);
				projectversion.setCodestandard_zy(0);
				projectversion.setTestcasenum(projectversion.getTestcasenum());
				projectversion.setCodeline(projectversion.getCodeline());
				projectversion.setChangetestingreturn(projectversion.getChangetestingreturn());
				projectversion.setDev_member(projectversion.getDev_member());
				projectversion.setTest_member(projectversion.getTest_member());
				projectversion.setZt_versionlink(projectversion.getZt_versionlink());
				
				double divalue = projectversion.getBug_zm()*10+projectversion.getBug_yz()*3+projectversion.getBug_yb()+projectversion.getBug_ts()*0.1;
				
				if(projectversion.getCodeline()!=0){
					projectversion.setCode_DI(Double.valueOf(new DecimalFormat("#.00").format(divalue/projectversion.getCodeline())).toString());
				}else{
					projectversion.setCode_DI("0");
				}
				
				if(projectversion.getActually_demand()!=0&&projectversion.getPlan_demand()!=0){
					projectversion.setPerdemand(Double.valueOf(new DecimalFormat("#.00").format(((double)projectversion.getActually_demand()/projectversion.getPlan_demand())*100)));
				}else{
					projectversion.setPerdemand(0.00);
				}
				
				projectversion.setPlan_devstart(projectversion.getPlan_devstart());
				projectversion.setPlan_devend(projectversion.getPlan_devend());
				projectversion.setActually_devstart(projectversion.getActually_devstart());
				projectversion.setActually_devend(projectversion.getActually_devend());				
				projectversion.setPlan_teststart(projectversion.getPlan_teststart());
				projectversion.setPlan_testend(projectversion.getPlan_testend());
				projectversion.setActually_teststart(projectversion.getActually_teststart());
				projectversion.setActually_testend(projectversion.getActually_testend());
				projectversion.setQualityreview(projectversion.getQualityreview());
				projectversion.setBug_zm(projectversion.getBug_zm());
				projectversion.setBug_yz(projectversion.getBug_yz());
				projectversion.setBug_yb(projectversion.getBug_yb());
				projectversion.setBug_ts(projectversion.getBug_ts());
				projectversion.setVersiontype(0);
				
				
				SectorProjects p = new SectorProjects();
				p.setProjectid(projectversion.getProjectid());
				projectversion.setSectorProjects(p);
				
				int versionid = projectsversionservice.add(projectversion);
				
				operationlogservice.add(req, "QA_PROJECTVERSION", versionid, 
						projectversion.getProjectid(),2,"版本计划添加成功！版本号："+projectversion.getVersionnumber());
				
				model.addAttribute("message", "添加成功");
				model.addAttribute("url", "/projectVersion/load.do");
				return retVal;

			}
				model.addAttribute("projectversion", projectversion);
				model.addAttribute("projects", QueueListener.qa_projlist);
				return retVal;

		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/projectVersion/load.do");
			return "error";
		}

	}
	
	/**
	 * 删除版本记录
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
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHPVERSIONDEL)) {
				json.put("status", "fail");
				json.put("ms", "删除项目版本信息失败,权限不足,请联系管理员!");
			} else {
				int versionid = Integer.valueOf(req.getParameter("versionid"));
				ProjectVersion projectversion = projectsversionservice.load(versionid);
				try
				{
					if(!UserLoginController.oppidboolean(req, projectversion.getSectorProjects().getProjectid())){
						json.put("status", "fail");
						json.put("ms", "删除项目版本信息失败,项目权限不足,请联系管理员!");
					}else{
						projectsversionservice.delete(versionid);
						
						operationlogservice.add(req, "QA_PROJECTVERSION", versionid, 
								projectversion.getSectorProjects().getProjectid(),0,"版本信息删除成功！版本号："+projectversion.getVersionnumber());
						
						json.put("status", "success");
						json.put("ms", "删除项目版本信息成功!");
					}

				}
				catch (Exception e)
				{
					json.put("status", "fail");
					json.put("ms", "删除项目版本信息失败!");
				}
			}
			pw.print(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 版本详情
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/show.do")
	public String show(HttpServletRequest req,Model model) throws Exception
	{
		String versionid = req.getParameter("versionid");
		ProjectVersion pv = new ProjectVersion();
		pv.setVersionid(Integer.valueOf(versionid));
		ProjectVersion projectversion = projectsversionservice.load(pv.getVersionid());
		model.addAttribute("projectversion", projectversion);
		return "/jsp/projectversion/projectversion_show";
	}

	/**
	 * 
	 * 质量报表
	 * @param model
	 * @param req
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/showreport.do")
	public String showReport(HttpServletRequest req,Model model) throws Exception
	{
		try{
		String p = req.getParameter("page");
		String startdate = req.getParameter("startdate");
		String enddate = req.getParameter("enddate");
		String startdatestr = startdate+"至";
		String enddatestr = enddate;
		
		if(startdate==""||startdate==null){
			startdate = "0";
			startdatestr = "初始数据日期至";
		}
		if(enddate==""||enddate==null){
			enddate = "9999-99-99";
			enddatestr = "今天";
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
		allRows = projectsversionservice.findRowsreport(startdate, enddate);
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
	
		
		List<Object[]> reportlist = projectsversionservice.findByPagereport(offset, pageSize, startdate, enddate);
		
		for(int i=0;i<reportlist.size();i++){	
			Object[] project = (Object[])sectorprojectsService.load(Integer.valueOf(reportlist.get(i)[0].toString()));
			reportlist.get(i)[0] = project[1].toString();
			//计算需求达成率
			if(Integer.valueOf(reportlist.get(i)[1].toString())!=0&&Integer.valueOf(reportlist.get(i)[2].toString())!=0){
				reportlist.get(i)[12] = Double.valueOf(new DecimalFormat("#.00").format((Double.valueOf(reportlist.get(i)[2].toString())/Double.valueOf(reportlist.get(i)[1].toString()))*100));
			}else{
				reportlist.get(i)[12] = 0.00;
			}
			//计算版本达成率
			if((Integer.valueOf(reportlist.get(i)[3].toString())+Integer.valueOf(reportlist.get(i)[4].toString()))!=0){
				reportlist.get(i)[13] = Double.valueOf(new DecimalFormat("#.00").format((Double.valueOf(reportlist.get(i)[4].toString())/Double.valueOf((Integer.valueOf(reportlist.get(i)[3].toString())+Integer.valueOf(reportlist.get(i)[4].toString()))))*100));
			}else{
				reportlist.get(i)[13] = 0.00;
			}
		}
		
		model.addAttribute("titledate", startdatestr+enddatestr);
		model.addAttribute("reportlist", reportlist);
		
	} catch (Exception e) {
		e.printStackTrace();
		model.addAttribute("message", e.getMessage());
		model.addAttribute("url", "/projectVersion/load.do");
		return "error";
	}
	return "/jsp/projectversion/projectversionreport";
		
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
		String startdate = req.getParameter("start_date");
		String enddate = req.getParameter("end_date");
		
		List pv = projectsversionservice.listavgpro(startdate,enddate);
		
		String[] columnname = {"上线版本数","开发平均生产率(千行/天)","测试平均生产率(执行  个/天)","项目工期平均偏移率(百分比)","缺陷DI平均值(千行)","需求平均达成率(百分比)"};
		
		Barchart3[] data = new Barchart3[columnname.length];
				
        Map pointMap = new HashMap<String, Object>(0);
        
        Map innerptMap = new HashMap<String, Object>(0);
        innerptMap.put("type", "max");
        innerptMap.put("name", "最大值");
        
        Map innerptMap1 = new HashMap<String, Object>(0);
        innerptMap1.put("type", "min");
        innerptMap1.put("name", "最小值");
        List<Map> datamapList = new ArrayList<Map>();
        datamapList.add(innerptMap);
        datamapList.add(innerptMap1);
        pointMap.put("data", datamapList);
        
        
		Map lineMap = new HashMap<String, Object>(0);
        
        Map innerliMap = new HashMap<String, Object>(0);
        innerliMap.put("type", "average");
        innerliMap.put("name", "平均值");
        List<Map> datamapList1 = new ArrayList<Map>();
        datamapList1.add(innerliMap);
        lineMap.put("data", datamapList1);
		//获取数据
		for(int i=0;i<columnname.length;i++){			
			Barchart3 barchart3 = new Barchart3();
			barchart3.setName(columnname[i]);
			barchart3.setType("bar");
			barchart3.setMarkLine(lineMap);
			barchart3.setMarkPoint(pointMap);
			double[] columnvalue = new double[pv.size()];
			for(int j=0;j<pv.size();j++){
				Object[] a = (Object[]) pv.get(j);
				if(a[i+1]!=null){
					columnvalue[j] = Double.valueOf(a[i+1].toString());
				}else{
					columnvalue[j] = 0;
				}
				
			}
			barchart3.setData(columnvalue);			
			data[i] = barchart3;
		}
		
		//项目名
		String[] projectname = new String[pv.size()];
		for(int i=0;i<pv.size();i++){
			Object[] a = (Object[]) pv.get(i);
			if(a[0]!=null){
				Object[] aaa = (Object[])sectorprojectsService.load(Integer.valueOf(a[0].toString()));
				String projectname1 =  aaa[1].toString();
				projectname[i] = projectname1;
			}else{
				projectname[i] = "未知"+i+1;
			}
		}
		
		String title;
		if(pv.size()!=0){
			String startdatestr = startdate+"至";
			String enddatestr = enddate;
			if(startdate==""||startdate==null){
				startdatestr = "初始数据日期至";
			}
			if(enddate==""||enddate==null){
				enddatestr = "今天";
			}
			title = "所有项目上线版本质量数据平均值(统计日期    "+startdatestr+enddatestr+")";
		}else{
			title = "当前选择日期段内无数据";
		}
		
		JSONArray  jsondata= (JSONArray)JSONArray.toJSON(data);
		JSONArray  jsonprojectname= (JSONArray)JSONArray.toJSON(projectname);
		JSONArray  jsoncolumnname= (JSONArray)JSONArray.toJSON(columnname);
		
		req.setAttribute("gdata", jsondata.toString());
		req.setAttribute("labels", jsonprojectname.toString());
		req.setAttribute("column", jsoncolumnname.toString());
		model.addAttribute("title", title);
		
		return "/jsp/echart/echarts_bar1";
	}
	
	/**
	 * 
	 * HTML5  折线图
	 * @param model
	 * @param req
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/linechart_html5.do")
	public String showoneProject(HttpServletRequest req,Model model) throws Exception
	{
		int projectid = Integer.valueOf(req.getParameter("projectid"));
		String startdate = req.getParameter("startdate");
		String enddate = req.getParameter("enddate");
		
		List<ProjectVersion> pv = projectsversionservice.list(projectid,startdate,enddate);
		
		String[] columnname = {"代码规范DI评分","开发平均生产率(千行/天)","测试平均生产率(执行  个/天)","项目工期偏移率(百分比)","缺陷DI值(千行)","需求达成率(百分比)"};
		
		Barchart3[] data = new Barchart3[columnname.length];				
		
        Map pointMap = new HashMap<String, Object>(0);
        
        Map innerptMap = new HashMap<String, Object>(0);
        innerptMap.put("type", "max");
        innerptMap.put("name", "最大值");
        
        Map innerptMap1 = new HashMap<String, Object>(0);
        innerptMap1.put("type", "min");
        innerptMap1.put("name", "最小值");
        List<Map> datamapList = new ArrayList<Map>();
        datamapList.add(innerptMap);
        datamapList.add(innerptMap1);
        pointMap.put("data", datamapList);
        
        
		Map lineMap = new HashMap<String, Object>(0);
        
        Map innerliMap = new HashMap<String, Object>(0);
        innerliMap.put("type", "average");
        innerliMap.put("name", "平均值");
        List<Map> datamapList1 = new ArrayList<Map>();
        datamapList1.add(innerliMap);
        lineMap.put("data", datamapList1);
		//获取数据
		for(int i=0;i<columnname.length;i++){			
			Barchart3 linechart = new Barchart3();
			linechart.setName(columnname[i]);
			linechart.setType("line");
			linechart.setMarkLine(lineMap);
			linechart.setMarkPoint(pointMap);
			double[] columnvalue = new double[pv.size()];
			for(int j=0;j<pv.size();j++){
				if(i==0){
					//计算代码DI值评分
					columnvalue[j] = Double.valueOf(new DecimalFormat("#.00").format((double)(100.00-(pv.get(j).getCodestandard_zd()*5)-(pv.get(j).getCodestandard_yz()*2)
							-(pv.get(j).getCodestandard_zy()*0.3))));
				}				
				if(!StrLib.isEmpty(pv.get(j).getPer_dev())&&i==1){
					columnvalue[j] = Double.valueOf(pv.get(j).getPer_dev());
				}
				if(!StrLib.isEmpty(pv.get(j).getPer_test())&&i==2){
					columnvalue[j] = Double.valueOf(pv.get(j).getPer_test());
				}
				if(!StrLib.isEmpty(pv.get(j).getProtime_deviation())&&i==3){
					columnvalue[j] = Double.valueOf(pv.get(j).getProtime_deviation());
				}
				if(!StrLib.isEmpty(pv.get(j).getCode_DI())&&i==4){
					columnvalue[j] = Double.valueOf(pv.get(j).getCode_DI());
				}
				if(i==5){
					columnvalue[j] = Double.valueOf(new DecimalFormat("#.00").format(((double)(pv.get(j).getActually_demand())/pv.get(j).getPlan_demand())*100));
				}
				
			}
			linechart.setData(columnvalue);			
			data[i] = linechart;
		}
		
		//上线日期
		String[] launchdate = new String[pv.size()];
		for(int i=0;i<pv.size();i++){	
			if(pv.get(i).getActually_launchdate()!=null){
				launchdate[i] = pv.get(i).getActually_launchdate();
				launchdate[i] = launchdate[i].substring(5, launchdate[i].length());

			}else{
				launchdate[i] = "未知";
			}
		}
		
		String title;
		if(pv.size()!=0){
			String startdatestr = startdate+"至";
			String enddatestr = enddate;
			if(startdate==""||startdate==null){
				startdatestr = "初始数据日期至";
			}
			if(enddate==""||enddate==null){
				enddatestr = "今天";
			}
			title = pv.get(0).getSectorProjects().getProjectname()+"上线质量趋势("+startdatestr+enddatestr+")";
		}else{
			title = "当前项目无数据";
		}
		
		JSONArray  jsondata= (JSONArray)JSONArray.toJSON(data);
		JSONArray  jsonlaunchdate= (JSONArray)JSONArray.toJSON(launchdate);
		JSONArray  jsoncolumnname= (JSONArray)JSONArray.toJSON(columnname);
		
		req.setAttribute("gdata", jsondata.toString());
		req.setAttribute("launchdate", jsonlaunchdate.toString());
		req.setAttribute("column", jsoncolumnname.toString());
		model.addAttribute("title", title);
		
		return "/jsp/echart/echarts_line1";
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
	@RequestMapping(value = "/onepro_barcharthtml5.do")
	public String showoneProjectBarChart(HttpServletRequest req,Model model) throws Exception{
		int projectid = Integer.valueOf(req.getParameter("projectid"));
		String startdate = req.getParameter("startdate");
		String enddate = req.getParameter("enddate");
		
		List<ProjectVersion> pv = projectsversionservice.list(projectid,startdate,enddate);
		
		String[] columnname = {"代码规范DI评分","开发平均生产率(千行/天)","测试平均生产率(执行  个/天)","项目工期偏移率(百分比)","缺陷DI值(千行)","需求达成率(百分比)"};
		
		Barchart3[] data = new Barchart3[columnname.length];				
		
        Map pointMap = new HashMap<String, Object>(0);
        
        Map innerptMap = new HashMap<String, Object>(0);
        innerptMap.put("type", "max");
        innerptMap.put("name", "最大值");
        
        Map innerptMap1 = new HashMap<String, Object>(0);
        innerptMap1.put("type", "min");
        innerptMap1.put("name", "最小值");
        List<Map> datamapList = new ArrayList<Map>();
        datamapList.add(innerptMap);
        datamapList.add(innerptMap1);
        pointMap.put("data", datamapList);
        
        
		Map lineMap = new HashMap<String, Object>(0);
        
        Map innerliMap = new HashMap<String, Object>(0);
        innerliMap.put("type", "average");
        innerliMap.put("name", "平均值");
        List<Map> datamapList1 = new ArrayList<Map>();
        datamapList1.add(innerliMap);
        lineMap.put("data", datamapList1);
		//获取数据
		for(int i=0;i<columnname.length;i++){			
			Barchart3 barchart3 = new Barchart3();
			barchart3.setName(columnname[i]);
			barchart3.setType("bar");
			barchart3.setMarkLine(lineMap);
			barchart3.setMarkPoint(pointMap);	
			double[] columnvalue = new double[pv.size()];
			for(int j=0;j<pv.size();j++){
				if(i==0){
					//计算代码DI值评分
					columnvalue[j] = Double.valueOf(new DecimalFormat("#.00").format((double)(100.00-(pv.get(j).getCodestandard_zd()*5)-(pv.get(j).getCodestandard_yz()*2)
							-(pv.get(j).getCodestandard_zy()*0.3))));
				}				
				if(!StrLib.isEmpty(pv.get(j).getPer_dev())&&i==1){
					columnvalue[j] = Double.valueOf(pv.get(j).getPer_dev());
				}
				if(!StrLib.isEmpty(pv.get(j).getPer_test())&&i==2){
					columnvalue[j] = Double.valueOf(pv.get(j).getPer_test());
				}
				if(!StrLib.isEmpty(pv.get(j).getProtime_deviation())&&i==3){
					columnvalue[j] = Double.valueOf(pv.get(j).getProtime_deviation());
				}
				if(!StrLib.isEmpty(pv.get(j).getCode_DI())&&i==4){
					columnvalue[j] = Double.valueOf(pv.get(j).getCode_DI());
				}
				if(i==5){
					columnvalue[j] = Double.valueOf(new DecimalFormat("#.00").format(((double)(pv.get(j).getActually_demand())/pv.get(j).getPlan_demand())*100));
				}
				
			}
			barchart3.setData(columnvalue);			
			data[i] = barchart3;
		}
		
		//上线日期
		String[] launchdate = new String[pv.size()];
		for(int i=0;i<pv.size();i++){	
			if(pv.get(i).getActually_launchdate()!=null){
				launchdate[i] = pv.get(i).getActually_launchdate();
				launchdate[i] = launchdate[i].substring(5, launchdate[i].length());

			}else{
				launchdate[i] = "未知";
			}
		}
		
		String title;
		if(pv.size()!=0){
			String startdatestr = startdate+"至";
			String enddatestr = enddate;
			if(startdate==""||startdate==null){
				startdatestr = "初始数据日期至";
			}
			if(enddate==""||enddate==null){
				enddatestr = "今天";
			}
			title = pv.get(0).getSectorProjects().getProjectname()+"上线质量柱状图("+startdatestr+enddatestr+")";
		}else{
			title = "当前项目无数据";
		}
		
		JSONArray  jsondata= (JSONArray)JSONArray.toJSON(data);
		JSONArray  jsonlaunchdate= (JSONArray)JSONArray.toJSON(launchdate);
		JSONArray  jsoncolumnname= (JSONArray)JSONArray.toJSON(columnname);
		
		req.setAttribute("gdata", jsondata.toString());
		req.setAttribute("labels", jsonlaunchdate.toString());
		req.setAttribute("column", jsoncolumnname.toString());
		model.addAttribute("title", title);
		
		System.out.println(jsondata.toString());
		
		return "/jsp/echart/echarts_bar1";
		
	}
	
	public static boolean checkdate(String str1,String str2){
		if("".equals(str1)&&"".equals(str2)){
			return true;
		}
		if("".equals(str1)||"".equals(str2)){
			return false;
		}
		if(str2==null){
			return true;
		}
		str1 = str1.replaceAll("-", "");
		str2 = str2.replaceAll("-", "");
		int startdate = Integer.valueOf(str1);
		int enddate = Integer.valueOf(str2);
		if(startdate>enddate){
			return false;
		}else{
			return true;
		}
  
	}
	
	public static String[] member(String str){
		String splitFlag = ";";
		String[] temp = new String[0];
		if("".equals(str)){
		return temp;
		}
		String strfh=";";
		if(str.substring(str.length()-1,str.length()).indexOf(strfh)>-1){
			str = str.substring(0,str.length()-1);
		}
		temp=str.split(splitFlag,-1);
		return temp;
	}
	
	public static int interval(String str1,String str2,int type){
		try  
		{  
			if("".equals(str1)||"".equals(str2)){
				return 0;
			}
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		    Date date1 = sdf.parse(str1);  
		    Date date2 = sdf.parse(str2);
		    long sl=date1.getTime();
	        long el=date2.getTime();       
	        long ei=el-sl;
	        int interval = (int)(ei/(1000*60*60*24));
	        if(type==1){
		        if(interval>=0){
		        	interval = interval+1;
		        }else{
		        	interval = interval-1;
		        }
	        }

	        return interval;
		}  
		catch (ParseException e)  
		{  
		    System.out.println(e.getMessage());  
		    return 0;
		}  
	}
	



	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(Double.valueOf(100.00-(3*5)-(12*2)
				-(24*0.3)));
		
	}

}
