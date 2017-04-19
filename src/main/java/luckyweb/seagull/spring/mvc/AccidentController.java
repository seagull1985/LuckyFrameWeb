package luckyweb.seagull.spring.mvc;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.spring.entity.Accident;
import luckyweb.seagull.spring.entity.PieLasagna;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.service.AccidentService;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.util.StrLib;

@Controller
@RequestMapping("/accident")
public class AccidentController {
	
	private int allPage;
	private int pageSize = 20;
	private int allRows;
	private int page = 1;
	private int offset; 
	
	@Resource(name = "accidentService")
	private AccidentService accidentservice;
	
	@Resource(name = "sectorprojectsService")
	private SectorProjectsService sectorprojectsService;
	

	public AccidentService getaccidentService() {
		return accidentservice;
	}

	public void setSectorProjectsService(AccidentService accidentservice) {
		this.accidentservice = accidentservice;
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
	public String list(HttpServletRequest req, Accident accident, Model model)
			throws Exception {
		model.addAttribute("accident", accident);

		try {
			String p = req.getParameter("page");
			String projectid = req.getParameter("projectid");
			String accstarttime = req.getParameter("accstarttime");
			String accendtime = req.getParameter("accendtime");
			String accstatus = req.getParameter("accstatus");

			if (!StrLib.isEmpty(projectid))
			{
				accident.setProjectid(Integer.valueOf(projectid));
			}
			
			if (accstarttime!=null&&!"".equals(accstarttime))
			{
				accident.setAccstarttime(accstarttime);
			}
			
			if (accendtime!=null&&!"".equals(accendtime))
			{
				accident.setAccendtime(accendtime);
			}
			
			if (accstatus!=null&&!"".equals(accstatus)&&!"00".equals(accstatus))
			{
				accident.setAccstatus(accstatus);
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
			allRows = accidentservice.findRows(accident);
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
			
			List<Accident> sssMap = accidentservice.findByPage(accident, offset,
					pageSize);
			model.addAttribute("projects", QueueListener.qa_projlist);
			model.addAttribute("splist", sssMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/accident/list.do");
			return "error";
		}
		return "/jsp/accident/accident";
	}
	
	/**
	 * 添加生产事故信息
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
	public String add(@Valid @ModelAttribute("accident") Accident accident,BindingResult br, Model model,
	        HttpServletRequest req, HttpServletResponse rsp) throws Exception
	{
		try
		{
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");

			if(!UserLoginController.permissionboolean(req, "acc_1")){
				model.addAttribute("accident", new Accident());
				model.addAttribute("url", "/accident/list.do");
				model.addAttribute("message", "当前用户无权限添加生产事故信息，请联系管理员！");
				return "success";
			}
			
			String retVal = "/jsp/accident/accident_add";
			if (req.getMethod().equals("POST"))
			{
				if (br.hasErrors())
				{
					return retVal;
				}
				String message = "";
				
				if(accident.getProjectid()==0){
					message = "请选择项目!";
					model.addAttribute("message", message);
					model.addAttribute("projects", QueueListener.qa_projlist);
					return retVal;
				}
				
				if(accident.getEventtime().equals("")){
					message = "请选择事故出现时间!";
					model.addAttribute("message", message);
					model.addAttribute("projects", QueueListener.qa_projlist);
					return retVal;
				}
				
				if(accident.getReporttime().equals("")){
					message = "请选择事故报告时间!";
					model.addAttribute("message", message);
					model.addAttribute("projects", QueueListener.qa_projlist);
					return retVal;
				}
				
				if(accident.getReporter().equals("")){
					message = "请填写事故报告人!";
					model.addAttribute("message", message);
					model.addAttribute("projects", QueueListener.qa_projlist);
					return retVal;
				}
				
				if(accident.getAccstatus().equals("跟踪处理完成")){
					if(accident.getCausaltype().equals("暂未选择")||accident.getCausalanalysis().equals("")){
						message = "跟踪处理完成状态，请选择原因类型或填写原因分析!";
						model.addAttribute("message", message);
						model.addAttribute("projects", QueueListener.qa_projlist);
						return retVal;
					}
					if(accident.getResolutiontime().equals("")||accident.getResolutioner().equals("")){
						message = "跟踪处理完成状态，请填写解决时间以及解决人员!";
						model.addAttribute("message", message);
						model.addAttribute("projects", QueueListener.qa_projlist);
						return retVal;
					}
					if(accident.getImpact_time()==0){
						message = "跟踪处理完成状态，请填写故障影响时间!";
						model.addAttribute("message", message);
						model.addAttribute("projects", QueueListener.qa_projlist);
						return retVal;
					}

				}
				
				if(!accident.getEventtime().equals("")&&!accident.getResolutiontime().equals("")){
					accident.setTrouble_duration(interval(accident.getEventtime(),accident.getResolutiontime()));
				}
				accident.setImpact_time(accident.getImpact_time());
				accident.setAccstatus(accident.getAccstatus());
				accident.setEventtime(accident.getEventtime());
				accident.setReporter(accident.getReporter());
				accident.setReporttime(accident.getReporttime());
				accident.setAccdescription(accident.getAccdescription());
				accident.setAcclevel(accident.getAcclevel());
				accident.setCausalanalysis(accident.getCausalanalysis());
				accident.setCausaltype(accident.getCausaltype());
				accident.setConsequenceanalysis(accident.getConsequenceanalysis());
				accident.setCorrectiveaction(accident.getCorrectiveaction());
				accident.setResolutiontime(accident.getResolutiontime());
				accident.setResolutioner(accident.getResolutioner());
				accident.setPreventiveaction(accident.getPreventiveaction());
				accident.setPreventiver(accident.getPreventiver());
				accident.setPreventiveplandate(accident.getPreventiveplandate());
				accident.setPreventiveaccdate(accident.getPreventiveaccdate());				
				
				SectorProjects p = new SectorProjects();
				p.setProjectid(accident.getProjectid());
				accident.setSectorProjects(p);
				
				int accid = accidentservice.add(accident);
				
				operationlogservice.add(req, "QA_ACCIDENT", accid, 
						accident.getProjectid(),"生产事故登记成功！事故等级："+accident.getAcclevel()+" 事故发生时间："+accident.getEventtime());
				
				model.addAttribute("message", "添加成功");
				model.addAttribute("url", "/accident/list.do");
				return retVal;

			}
				model.addAttribute("accident", accident);
				model.addAttribute("projects", QueueListener.qa_projlist);
				return retVal;

		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/accident/list.do");
			return "error";
		}

	}
	
	/**
	 * 
	 * 根据id更新记录
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update.do")
	public String update(@Valid @ModelAttribute("accident") Accident accident, BindingResult br,
	        Model model, HttpServletRequest req) throws Exception
	{
		req.setCharacterEncoding("utf-8");
		int id = Integer.valueOf(req.getParameter("id"));
		
		if(!UserLoginController.permissionboolean(req, "acc_3")){
			model.addAttribute("accident", new Accident());
			model.addAttribute("url", "/accident/list.do");
			model.addAttribute("message", "当前用户无权限修改生产事故信息，请联系管理员！");
			return "success";
		}
		
		try{
		model.addAttribute("projects", QueueListener.qa_projlist);
		String retVal = "/jsp/accident/accident_update";
		if (req.getMethod().equals("POST"))
		{
			if (br.hasErrors())
			{
				return retVal;
			}
			String message = "";
			
			if(accident.getProjectid()==0){
				message = "请选择项目!";
				model.addAttribute("message", message);
				return retVal;
			}
			if(accident.getAccstatus().equals("跟踪处理完成")){
				if(accident.getCausaltype().equals("暂未选择")||accident.getCausalanalysis().equals("")){
					message = "跟踪处理完成状态，请选择原因类型或填写原因分析!";
					model.addAttribute("message", message);
					return retVal;
				}
				if(accident.getResolutiontime().equals("")||accident.getResolutioner().equals("")){
					message = "跟踪处理完成状态，请填写解决时间以及解决人员!";
					model.addAttribute("message", message);
					return retVal;
				}
				if(accident.getImpact_time()==null){
					message = "跟踪处理完成状态，请填写故障影响时间!";
					model.addAttribute("message", message);
					return retVal;
				}
			}
			
			if(!accident.getEventtime().equals("")&&!accident.getResolutiontime().equals("")){
				accident.setTrouble_duration(interval(accident.getEventtime(),accident.getResolutiontime()));
			}
			accident.setImpact_time(accident.getImpact_time());
			accident.setAccstatus(accident.getAccstatus());
			accident.setEventtime(accident.getEventtime());
			accident.setReporter(accident.getReporter());
			accident.setReporttime(accident.getReporttime());
			accident.setAccdescription(accident.getAccdescription());
			accident.setAcclevel(accident.getAcclevel());
			accident.setCausalanalysis(accident.getCausalanalysis());
			accident.setCausaltype(accident.getCausaltype());
			accident.setConsequenceanalysis(accident.getConsequenceanalysis());
			accident.setCorrectiveaction(accident.getCorrectiveaction());
			accident.setResolutiontime(accident.getResolutiontime());
			accident.setResolutioner(accident.getResolutioner());
			accident.setPreventiveaction(accident.getPreventiveaction());
			accident.setPreventiver(accident.getPreventiver());
			accident.setPreventiveplandate(accident.getPreventiveplandate());
			accident.setPreventiveaccdate(accident.getPreventiveaccdate());				
			
			SectorProjects p = new SectorProjects();
			p.setProjectid(accident.getProjectid());
			accident.setSectorProjects(p);
			
			accidentservice.modify(accident);
			
			operationlogservice.add(req, "QA_ACCIDENT", id, 
					accident.getProjectid(),"生产事故信息修改成功！事故等级："+accident.getAcclevel()+" 事故发生时间："+accident.getEventtime());

			
			model.addAttribute("message", "修改成功");
			model.addAttribute("url", "/accident/list.do");
			return retVal;

		}	
		accident = accidentservice.load(id);
		accident.setProjectid(accident.getSectorProjects().getProjectid());
		
		model.addAttribute("accident", accident);
		return "/jsp/accident/accident_update";

	}
	catch (Exception e)
	{
		model.addAttribute("message", e.getMessage());
		model.addAttribute("url", "/accident/list.do");
		return "error";
	 }
	}
	
	/**
	 * 删除事故记录
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete.do", method = RequestMethod.GET)
	public String delete(Model model,HttpServletRequest req) throws Exception
	{
		if(!UserLoginController.permissionboolean(req, "acc_2")){
			model.addAttribute("accident", new Accident());
			model.addAttribute("url", "/accident/list.do");
			model.addAttribute("message", "当前用户无权限删除生产事故信息，请联系管理员！");
			return "success";
		}
		
		int id = Integer.valueOf(req.getParameter("id"));
		Accident accident = accidentservice.load(id);
		try
		{		
			accidentservice.delete(id);
		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/accident/list.do");
			return "error";
		}
		
		operationlogservice.add(req, "QA_ACCIDENT", id, 
				accident.getSectorProjects().getProjectid(),"生产事故信息删除成功！事故等级："+accident.getAcclevel()+" 事故发生时间："+accident.getEventtime());

		String message = "删除成功！";
		model.addAttribute("accident", new Accident());
		model.addAttribute("message", message);
		model.addAttribute("url", "/accident/list.do");
		return "success";
	}
	
	/**
	 * 事故详情
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/show.do", method = RequestMethod.GET)
	public String show(Model model,HttpServletRequest req) throws Exception
	{
		int id = Integer.valueOf(req.getParameter("id"));
		Accident accident = accidentservice.load(id);
		if(accident.getTrouble_duration()!=null){
			accident.setStrtrouble_duration(timecast(accident.getTrouble_duration()));
		}
		if(accident.getImpact_time()!=null){
			accident.setStrimpact_time(timecast(accident.getImpact_time()*60));
		}
		
		model.addAttribute("accident", accident);
		return "/jsp/accident/accident_show";
	}
	
	/**
	 * 
	 * HTML5  饼图
	 * @param model
	 * @param req
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/piechart_html5.do")
	public String pieGroupCausaltype(HttpServletRequest req,Model model) throws Exception
	{
		String startdate = req.getParameter("pie_startdate");
		String enddate = req.getParameter("pie_enddate");
		String type = req.getParameter("type");
		String unit = ""; 
		String title1 = "";
		int projectid = Integer.valueOf(req.getParameter("projectid"));
		
/*		String[] columncolor = {"#7B7B7B","#FFE6D9","#796400","#CE0000","#921AFF","#E800E8","#00EC00","#00E3E3",
				"#F9F900","#FFA042","#AFAF61","#6FB7B7","#B87070","#9999CC",
				"#B766AD","#FF359A","#CA8EFF","#A6A600","#CECEFF","#C4E1E1","#E2C2DE","#FFD2D2","#743A3A","#7E3D76"};*/
		
		List<Object[]> ls = accidentservice.listcausaltype(startdate, enddate, projectid,type);
		
		PieLasagna[] data = new PieLasagna[ls.size()];
		
		//初始化显示数据单位
		if(type.equals("count")){
			unit = " 次";
			title1 = "生产事故原因类型图(按累计次数)";
		}
		if(type.equals("sumimpact")){
			unit = " 小时";
			title1 = "生产事故原因类型图(按累计影响时间)";
		}
		
		//获取数据
				for(int i=0;i<ls.size();i++){	
					PieLasagna lasagna = new PieLasagna();
					lasagna.setName(ls.get(i)[0].toString());
					if(ls.get(i)[1]!=null){
						if(type.equals("count")){
							lasagna.setValue(Double.valueOf(ls.get(i)[1].toString()));
						}else if(type.equals("sumimpact")){
							lasagna.setValue(Double.valueOf(new DecimalFormat("#.00").format(Double.valueOf(ls.get(i)[1].toString())/60)));
						}
						
					}else{
						lasagna.setValue(0.00);
					}
					
/*					if(i>=20){
						piechart3D.setColor(columncolor[19]);
					}else{
						piechart3D.setColor(columncolor[i]);
					}*/
					data[i] = lasagna;
				}
				
		String title;
		if (ls.size() != 0) {
			String startdatestr = startdate+"至";
			String enddatestr = enddate;
			if(startdate==""||startdate==null){
				startdatestr = "初始数据日期至";
			}
			if(enddate==""||enddate==null){
				enddatestr = "今天";
			}
			title = startdatestr + enddatestr;
		} else {
			title = "本次查询无可用数据";
		}
		
		String projectname = "全部项目";
				
		if(projectid!=0){
			Object[] aaa = (Object[])sectorprojectsService.load(projectid);
			projectname =  aaa[1].toString();
		}		
		
				
		JSONArray jsondata = JSONArray.fromObject(data);

		req.setAttribute("gdata", jsondata.toString());
		model.addAttribute("title1", title1);
		model.addAttribute("title2", title);
		model.addAttribute("title3", projectname);
		model.addAttribute("unit", unit);

		return "/jsp/echart/echarts_lasagna";
		
	}
	
	public static long interval(String str1,String str2) throws ParseException{
		if(str1.equals("")||str2.equals("")){
			return 0;
		}
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
	    Date date1 = sdf.parse(str1);  
	    Date date2 = sdf.parse(str2);
	    long sl=date1.getTime();
        long el=date2.getTime();     
        return (el-sl)/1000;
	}
	
	public static String timecast(long second){
        long days = second / (60 * 60 * 24);
        long hours = (second % (60 * 60 * 24)) / (60 * 60);
        long minutes = (second % (60 * 60)) / (60);
        long seconds = (second % (60));
        String str =days + "天" + hours + "小时" + minutes + "分" + seconds + "秒";
        return str;
	}

	/**
	 * 上传
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/to_upload.do")
	public String to_upload(Model model,HttpServletRequest req, HttpServletResponse response) throws Exception
	{
		req.setCharacterEncoding("utf-8");
		if(!UserLoginController.permissionboolean(req, "acc_upload")){
			model.addAttribute("message", "当前用户无权限上传事故附件！");
			model.addAttribute("url", "/accident/list.do");
			return "error";
		}
		String id = req.getParameter("id");
		model.addAttribute("id", id);
		return "/jsp/accident/acc_upload";
	}

	/**
	 * 上传
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload.do")
	public String upload(@RequestParam(value = "file", required = false) MultipartFile file, Model model,
	        HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int id = Integer.valueOf(request.getParameter("id"));
		String filetype = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),
		        file.getOriginalFilename().length()).toLowerCase();
		if (!filetype.equals(".docx")&&!filetype.equals(".doc")&&!filetype.equals(".pdf"))
		{
			String message = "只能上传word或是pdf的文件！";
			model.addAttribute("message", message);
			return "/jsp/accident/acc_upload";
		}
		// 文件目录
		String path = "D:\\website\\TestWeb\\upload\\";
		String pathName = path + id+filetype;
		File targetFile = new File(pathName);
		if (targetFile.exists()){
			targetFile.deleteOnExit();
		}else{
			targetFile.mkdir();
		}
		// 保存
		try
		{
			file.transferTo(targetFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/accident/list.do");
			return "error";
		}
		Accident accident = accidentservice.load(id);
		accident.setFilename(id+filetype);
		accidentservice.modify(accident);		
		operationlogservice.add(request, "QA_ACCIDENT", id, 
				accident.getProjectid(),"生产事故附件上传成功！附件名称【"+id+filetype+"】");

		model.addAttribute("url", "/accident/list.do");
		model.addAttribute("message", "【" + file.getOriginalFilename() + "】文件自动更名为【"+id+filetype+"】上传成功！");
		return "success";
	}

	public static void main(String[] args) throws Exception {

	}

}
