package luckyweb.seagull.spring.mvc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.spring.entity.Accident;
import luckyweb.seagull.spring.entity.PieLasagna;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.service.AccidentService;
import luckyweb.seagull.spring.service.OperationLogService;
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
@RequestMapping("/accident")
public class AccidentController {
	
	@Resource(name = "accidentService")
	private AccidentService accidentservice;
	
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

		try {
			int projectid = 99;

			List<SectorProjects> prolist = QueueListener.qa_projlist;
			model.addAttribute("projects", prolist);
			model.addAttribute("projectid", projectid);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/accident/load.do");
			return "error";
		}
		return "/jsp/accident/accident";
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
		String accstatus = request.getParameter("accstatus");
		Accident accident = new Accident();
		if (null == offset && null == limit) {
			offset = 0;
		}
		if (null == limit || limit == 0) {
			limit = 10;
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(search)) {
			accident.setAccdescription(search);
			accident.setCausaltype(search);
		}
		if (accstatus!=null&&!"".equals(accstatus)&&!PublicConst.STATUSSTR00.equals(accstatus))
		{
			accident.setAccstatus(accstatus);
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(projectid)&&!PublicConst.STATUSSTR99.equals(projectid)) {
			accident.setProjectid(Integer.valueOf(projectid));
		}

		if (!StrLib.isEmpty(startDate)) {
			accident.setAccstarttime(startDate+" 00:00:00");
		}
			
		if (!StrLib.isEmpty(endDate)) {
			accident.setAccendtime(endDate+" 23:59:59");
		}
		List<Accident> acclist = accidentservice.findByPage(accident, offset, limit);

		// 转换成json字符串
		JSONArray recordJson = StrLib.listToJson(acclist);
		// 得到总记录数
		int total = accidentservice.findRows(accident);
		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", recordJson);
		pw.print(json.toString());
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

			if(!UserLoginController.permissionboolean(req, PublicConst.AUTHACCADD)){
				model.addAttribute("accident", new Accident());
				model.addAttribute("url", "/accident/load.do");
				model.addAttribute("message", "当前用户无权限添加生产事故信息，请联系管理员！");
				return "error";
			}
			
			String retVal = "/jsp/accident/accident_add";
			if (PublicConst.REQPOSTTYPE.equals(req.getMethod()))
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
				}else{
					if(!UserLoginController.oppidboolean(req, accident.getProjectid())){
						SectorProjects sp=sectorprojectsService.loadob(accident.getProjectid());
						model.addAttribute("accident", new Accident());
						model.addAttribute("url", "/accident/load.do");
						model.addAttribute("message", "当前用户无权限添加项目【"+sp.getProjectname()+"】生产事故信息，请联系管理员！");
						return "error";
					}
				}
				
				if("".equals(accident.getEventtime())){
					message = "请选择事故出现时间!";
					model.addAttribute("message", message);
					model.addAttribute("projects", QueueListener.qa_projlist);
					return retVal;
				}
				
				if("".equals(accident.getReporter())){
					message = "请填写事故报告人!";
					model.addAttribute("message", message);
					model.addAttribute("projects", QueueListener.qa_projlist);
					return retVal;
				}
				String accStatusOver="跟踪处理完成";
				String causalTypeChoose="暂未选择";
				if(accStatusOver.equals(accident.getAccstatus())){
					if(causalTypeChoose.equals(accident.getCausaltype())||"".equals(accident.getCausalanalysis())){
						message = "跟踪处理完成状态，请选择原因类型或填写原因分析!";
						model.addAttribute("message", message);
						model.addAttribute("projects", QueueListener.qa_projlist);
						return retVal;
					}
					if("".equals(accident.getResolutiontime())){
						message = "跟踪处理完成状态，请填写解决时间!";
						model.addAttribute("message", message);
						model.addAttribute("projects", QueueListener.qa_projlist);
						return retVal;
					}

				}
				
				if(!"".equals(accident.getEventtime())&&!"".equals(accident.getResolutiontime())){
					accident.setTrouble_duration(interval(accident.getEventtime(),accident.getResolutiontime()));
				}		
				
				SectorProjects p = new SectorProjects();
				p.setProjectid(accident.getProjectid());
				accident.setSectorProjects(p);
				
				int accid = accidentservice.add(accident);
				
				operationlogservice.add(req, "QA_ACCIDENT", accid, 
						accident.getProjectid(),10,"生产事故登记成功！事故等级："+accident.getAcclevel()+" 事故发生时间："+accident.getEventtime());
				
				model.addAttribute("message", "添加成功");
				model.addAttribute("url", "/accident/load.do");
				return retVal;

			}
				model.addAttribute("accident", accident);
				model.addAttribute("projects", QueueListener.qa_projlist);
				return retVal;

		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/accident/load.do");
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
		
		if(!UserLoginController.permissionboolean(req, PublicConst.AUTHACCMOD)){
			model.addAttribute("accident", new Accident());
			model.addAttribute("url", "/accident/load.do");
			model.addAttribute("message", "当前用户无权限修改生产事故信息，请联系管理员！");
			return "error";
		}
		
		try{
		model.addAttribute("projects", QueueListener.qa_projlist);
		String retVal = "/jsp/accident/accident_update";
		if (PublicConst.REQPOSTTYPE.equals(req.getMethod()))
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
			String accStatusOver="跟踪处理完成";
			String causalTypeChoose="暂未选择";
			if(accStatusOver.equals(accident.getAccstatus())){
				if(causalTypeChoose.equals(accident.getCausaltype())||"".equals(accident.getCausalanalysis())){
					message = "跟踪处理完成状态，请选择原因类型或填写原因分析!";
					model.addAttribute("message", message);
					return retVal;
				}
				if("".equals(accident.getResolutiontime())){
					message = "跟踪处理完成状态，请填写解决时间!";
					model.addAttribute("message", message);
					return retVal;
				}
			}
			
			if(!"".equals(accident.getEventtime())&&!"".equals(accident.getResolutiontime())){
				accident.setTrouble_duration(interval(accident.getEventtime(),accident.getResolutiontime()));
			}		
			
			SectorProjects p = new SectorProjects();
			p.setProjectid(accident.getProjectid());
			accident.setSectorProjects(p);
			
			accidentservice.modify(accident);
			
			operationlogservice.add(req, "QA_ACCIDENT", id, 
					accident.getProjectid(),1,"生产事故信息修改成功！事故等级："+accident.getAcclevel()+" 事故发生时间："+accident.getEventtime());

			
			model.addAttribute("message", "修改成功");
			model.addAttribute("url", "/accident/load.do");
			return retVal;

		}	
		accident = accidentservice.load(id);
		accident.setProjectid(accident.getSectorProjects().getProjectid());
		
		if(!UserLoginController.oppidboolean(req, accident.getProjectid())){
			SectorProjects sp=sectorprojectsService.loadob(accident.getProjectid());
			model.addAttribute("accident", new Accident());
			model.addAttribute("url", "/accident/load.do");
			model.addAttribute("message", "当前用户无权限修改项目【"+sp.getProjectname()+"】生产事故信息，请联系管理员！");
			return "error";
		}
		
		model.addAttribute("accident", accident);
		return "/jsp/accident/accident_update";

	}
	catch (Exception e)
	{
		model.addAttribute("message", e.getMessage());
		model.addAttribute("url", "/accident/load.do");
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
	@RequestMapping(value = "/delete.do")
	public void delete(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHACCDEL)) {
				json.put("status", "fail");
				json.put("ms", "删除故障记录失败,权限不足,请联系管理员!");
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
				String ms="删除故障记录失败!";
				int suc=0;
				int fail=0;
				for (int i = 0; i < jsonarr.size(); i++) {
					int id = Integer.valueOf(jsonarr.get(i).toString());
					Accident accident = accidentservice.load(id);
					
					if(!UserLoginController.oppidboolean(req, accident.getSectorProjects().getProjectid())){
						fail++;
						continue;
					}					
					accidentservice.delete(id);

					operationlogservice.add(req, "QA_ACCIDENT", id, 
							accident.getSectorProjects().getProjectid(),0,"生产事故信息删除成功！事故等级："+accident.getAcclevel()+" 事故发生时间："+accident.getEventtime());
					suc++;
				}
				
				if(suc>0){
					status="success";
					ms="删除故障记录成功!";
					if(fail>0){
						status="success";
						ms="删除故障记录"+suc+"条成功！"+fail+"条因为无项目权限删除失败！";
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
		//+" 00:00:00";
		String startdate = req.getParameter("pie_startdate");
		//+" 23:59:59";
		String enddate = req.getParameter("pie_enddate");
		String type = req.getParameter("type");
		String unit = ""; 
		String title1 = "";
		int projectid = Integer.valueOf(req.getParameter("projectid"));
		
/*		String[] columncolor = {"#7B7B7B","#FFE6D9","#796400","#CE0000","#921AFF","#E800E8","#00EC00","#00E3E3",
				"#F9F900","#FFA042","#AFAF61","#6FB7B7","#B87070","#9999CC",
				"#B766AD","#FF359A","#CA8EFF","#A6A600","#CECEFF","#C4E1E1","#E2C2DE","#FFD2D2","#743A3A","#7E3D76"};*/
		
		@SuppressWarnings("unchecked")
		List<Object[]> ls = accidentservice.listcausaltype(startdate, enddate, projectid,type);
		
		PieLasagna[] data = new PieLasagna[ls.size()];
		
		//初始化显示数据单位
		String count="count";
		if(count.equals(type)){
			unit = " 次";
			title1 = "生产事故原因类型图(按累计次数)";
		}
		String sumimpact="sumimpact";
		if(sumimpact.equals(type)){
			unit = " 小时";
			title1 = "生产事故原因类型图(按累计影响时间)";
		}
		
		//获取数据
				for(int i=0;i<ls.size();i++){	
					PieLasagna lasagna = new PieLasagna();
					lasagna.setName(ls.get(i)[0].toString());
					if(ls.get(i)[1]!=null){
						if("count".equals(type)){
							lasagna.setValue(Double.valueOf(ls.get(i)[1].toString()));
						}else if("sumimpact".equals(type)){
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
		
				
		JSONArray jsondata = (JSONArray)JSONArray.toJSON(data);

		req.setAttribute("gdata", jsondata.toString());
		model.addAttribute("title1", title1);
		model.addAttribute("title2", title);
		model.addAttribute("title3", projectname);
		model.addAttribute("unit", unit);

		return "/jsp/echart/echarts_lasagna";
		
	}
	
	public static long interval(String str1,String str2) throws ParseException{
		if("".equals(str1)||"".equals(str2)){
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
	public String toUpload(Model model,HttpServletRequest req, HttpServletResponse response) throws Exception
	{
		req.setCharacterEncoding("utf-8");
		if(!UserLoginController.permissionboolean(req, PublicConst.AUTHACCUPLOAD)){
			model.addAttribute("message", "当前用户无权限上传事故附件！");
			model.addAttribute("url", "/accident/load.do");
			return "error";
		}

		String id = req.getParameter("id");
		Accident accident=accidentservice.load(Integer.valueOf(id));
		if(!UserLoginController.oppidboolean(req, accident.getProjectid())){
			SectorProjects sp=sectorprojectsService.loadob(accident.getProjectid());
			model.addAttribute("accident", new Accident());
			model.addAttribute("url", "/accident/load.do");
			model.addAttribute("message", "当前用户无权限添加项目【"+sp.getProjectname()+"】生产事故信息，请联系管理员！");
			return "error";
		}
		
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
		String filetypedoc=".doc";
		String filetypedocx=".docx";
		String filetypepdf=".pdf";
		if (!filetypedocx.equals(filetype)&&!filetypedoc.equals(filetype)&&!filetypepdf.equals(filetype))
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
			model.addAttribute("url", "/accident/load.do");
			return "error";
		}
		Accident accident = accidentservice.load(id);
		accident.setFilename(id+filetype);
		accidentservice.modify(accident);		
		operationlogservice.add(request, "QA_ACCIDENT", id, 
				accident.getProjectid(),2,"生产事故附件上传成功！附件名称【"+id+filetype+"】");

		model.addAttribute("url", "/accident/load.do");
		model.addAttribute("message", "【" + file.getOriginalFilename() + "】文件自动更名为【"+id+filetype+"】上传成功！");
		return "success";
	}

	public static void main(String[] args) throws Exception {

	}

}
