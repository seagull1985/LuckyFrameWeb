package luckyweb.seagull.spring.mvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import luckyweb.seagull.spring.entity.OperationLog;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.util.DateLib;
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
@RequestMapping("/operationLog")
public class OperationLogController {
	
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
			String startdate=DateLib.beforNdFormat("yyyy-MM-dd HH:mm:ss", 7);
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
	
	@RequestMapping(value = "/list.do")
	private void ajaxGetSellRecord(Integer limit, Integer offset, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
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
			oplog.setStarttime(DateLib.beforNdFormat("yyyy-MM-dd HH:mm:ss", 7));
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
		JSONArray recordJson = StrLib.listToJson(loglist);
		// 得到总记录数
		int total = operationlogservice.findRows(oplog);
		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", recordJson);
		pw.print(json.toString());
	}
	
	@RequestMapping(value = "/getautostar.do")
	public void getAutoStar(HttpServletRequest req, HttpServletResponse rsp) throws Exception{
		List<Object[]> oplist = operationlogservice.getSumIntegral();
		

		String[] name;
		String[] integral;
		int maxvalue=0;
		
		if(null!=oplist&&0!=oplist.size()){
			name = new String[oplist.size()];
			integral = new String[oplist.size()];	
		}else{
			name = new String[1];
			integral = new String[1];

			
			name[0] = "暂无数据";
			integral[0] = "0";
		}

		for(int i=0;i<oplist.size();i++){
			name[i] = oplist.get(i)[0].toString();
			if(null!=oplist.get(i)[1]){
				integral[i] = oplist.get(i)[1].toString();
				if(i==oplist.size()-1){
					double maxdb=Double.valueOf(integral[i]);
					//取最高值的90%进行显示
					maxdb = maxdb*0.9;
					BigDecimal bd=new BigDecimal(maxdb).setScale(0, BigDecimal.ROUND_HALF_UP);
					maxvalue=Integer.valueOf(bd.toString());
				}
			}else{
				integral[i]="0";
			}
			
		}
		
		JSONArray  jsonname=(JSONArray) JSONArray.toJSON(name);
		JSONArray  jsonintegral=(JSONArray) JSONArray.toJSON(integral);
		
		JSONObject jsobjcet = new JSONObject();
		jsobjcet.put("name", jsonname);
		jsobjcet.put("integral", jsonintegral);
		jsobjcet.put("maxvalue", maxvalue);
		
		rsp.setContentType("application/json");
		rsp.setCharacterEncoding("utf-8");
		rsp.getWriter().write(jsobjcet.toString());
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

}
