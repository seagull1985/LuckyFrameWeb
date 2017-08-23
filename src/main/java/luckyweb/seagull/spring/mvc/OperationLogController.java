package luckyweb.seagull.spring.mvc;

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
import luckyweb.seagull.spring.entity.OperationLog;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.util.DateLib;
import luckyweb.seagull.util.StrLib;
import net.sf.json.JSONObject;


@Controller
@RequestMapping("/operationLog")
public class OperationLogController {
	
	private int allPage;
	private int pageSize = 20;
	private int allRows;
	private int page = 1;
	private int offset;
	
	@Resource(name = "operationlogService")
	private OperationLogService operationlogservice;
	
	@Resource(name = "sectorprojectsService")
	private SectorProjectsService sectorprojectsService;
	
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
			List<SectorProjects> prolist=sectorprojectsService.getAllProject();
			for(int i=0;i<prolist.size();i++){
				if(prolist.get(i).getProjecttype()==1){
					prolist.get(i).setProjectname(prolist.get(i).getProjectname()+"(TestLink项目)");
				}
			}
			String startdate=DateLib.befor_Nd_format("yyyy-MM-dd HH:mm:ss", 7);
			String enddate=DateLib.today("yyyy-MM-dd HH:mm:ss");
			model.addAttribute("projects", prolist);
			model.addAttribute("projectid", 0);
			model.addAttribute("startdate", startdate);
			model.addAttribute("enddate", enddate);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/operationLog/load.do");
			return "error";
		}
		return "/jsp/base/operationlog";
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/list.do")
	private void ajaxGetSellRecord(Integer limit, Integer offset, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		String search = request.getParameter("search");
		String projectid = request.getParameter("projectid");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		OperationLog oplog = new OperationLog();
		if (null == offset && null == limit) {
			offset = 0;
		}
		if (null == limit || limit == 0) {
			limit = 10;
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(search)) {
			oplog.setOperationer(search);
			oplog.setOperation_description(search);
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(projectid)) {
			oplog.setProjectid(Integer.valueOf(projectid));
		}

		if (StrLib.isEmpty(startTime)) {
			oplog.setStarttime(DateLib.befor_Nd_format("yyyy-MM-dd HH:mm:ss", 7));
		} else {
			oplog.setStarttime(startTime);
		}
		if (StrLib.isEmpty(endTime)) {
			oplog.setEndtime(DateLib.today("yyyy-MM-dd HH:mm:ss"));
		} else {
			oplog.setEndtime(endTime);
		}
		
		List<OperationLog> loglist = operationlogservice.findByPage(oplog, offset, limit);
		// 转换成json字符串
		String RecordJson = StrLib.listToJson(loglist);
		// 得到总记录数
		int total = operationlogservice.findRows(oplog);
		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", RecordJson);
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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/listbak.do")
	public String list(HttpServletRequest req, OperationLog oplog, Model model)
			throws Exception {
		model.addAttribute("oplog", oplog);

		try {
			String p = req.getParameter("page");
			
			if(oplog.getProjectid()!=0){
				oplog.setProjectid(oplog.getProjectid());
			}
			if(oplog.getOperationer()!=null&&!oplog.getOperationer().equals("")){
				oplog.setOperationer(oplog.getOperationer());
			}
			
			if(oplog.getStarttime()!=null&&!oplog.getStarttime().equals("")){
				oplog.setStarttime(oplog.getStarttime());
			}
			
			if(oplog.getEndtime()!=null&&!oplog.getEndtime().equals("")){
				oplog.setEndtime(oplog.getEndtime());
			}
			
			if(oplog.getOperation_description()!=null&&!oplog.getOperation_description().equals("")){
				oplog.setOperation_description(oplog.getOperation_description());
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
			allRows = operationlogservice.findRows(oplog);
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
			
			List<OperationLog> sssMap = operationlogservice.findByPage(oplog, offset,
					pageSize);
			model.addAttribute("splist", sssMap);
			model.addAttribute("projects", QueueListener.qa_projlist);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/operationlog/list.do");
			return "error";
		}
		return "/jsp/base/operationlog";
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
	}

}
