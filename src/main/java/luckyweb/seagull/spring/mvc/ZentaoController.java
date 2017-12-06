package luckyweb.seagull.spring.mvc;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import luckyweb.seagull.spring.entity.ZtTask;
import luckyweb.seagull.spring.service.ZtTaskService;
import luckyweb.seagull.util.MysqlLib;
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
@RequestMapping("/zentao")
public class ZentaoController {
	
	private int allPage;
	private int pageSize = 20;
	private int allRows;
	private int page = 1;
	private int offset;
	
	@Resource(name = "zttaskService")
	private ZtTaskService zttaskService;

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
	public String list(HttpServletRequest req, ZtTask zt, Model model,boolean flag)
			throws Exception {
		model.addAttribute("zt", zt);
		String url = "/jsp/projectversion/zentaolist";
		if(zt.getVersionid()!=0){
			url = "/jsp/projectversion/zentaolistfortast";
		}
		
		if(flag){
			zt.setDelaystatus(2);
		}
		
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
			allRows = zttaskService.findRows(zt);
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
			
			List<ZtTask> sssMap = zttaskService.findByPage(zt, offset, pageSize);
			model.addAttribute("zttasklist", sssMap);

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/zentao/list.do");
			return "error";
		}
		return url;
	}
	
	/**
	 * 
	 * 禅道数据同步
	 * @param model
	 * @param req
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/synchronization_zt.do")
	public String synchronizationZt(HttpServletRequest req, Model model, HttpServletResponse rsp) throws Exception {

		String proid = req.getParameter("proid");
		MysqlLib mysqllib = new MysqlLib();
		String message = "数据同步完成！";

		PrintWriter pw = null;
		rsp.setContentType("text/html;charset=utf-8");

		try {
			pw = rsp.getWriter();
			String[][] data = mysqllib.querydata(proid);
			if (data != null) {
				zttaskService.delete(Integer.valueOf(data[0][1]));
				for (int i = 0; i < data.length; i++) {
					ZtTask zt = new ZtTask();
					zt.setId(Integer.valueOf(data[i][0]));
					zt.setVersionid(Integer.valueOf(data[i][1]));
					zt.setVersionname(data[i][2]);
					zt.setTaskname(data[i][3]);
					zt.setAssignedDate(data[i][4]);
					zt.setEstimate(Double.valueOf(data[i][5]));
					zt.setConsumed(Double.valueOf(data[i][6]));
					zt.setFinishedname(data[i][7]);
					zt.setFinishedby(data[i][8]);
					zt.setDeadline(data[i][9]);

					// 判断延期
					if (ProjectVersionController.checkdate(data[i][4].substring(0, 10), data[i][9])) {
						zt.setDelaystatus(1);
					} else {
						zt.setDelaystatus(2);
					}
					
					zttaskService.add(zt);
				}
			} else {
				message = "当前ID任务未结束或链接禅道数据库异常！";
			}
		} catch (Exception e) {
			pw.write(e.getMessage());
			return null;
		}
		pw.write(message);
		return null;
	}
	
	/**
	 * 
	 * 禅道报表
	 * @param model
	 * @param req
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/showreport.do")
	public String showReport(HttpServletRequest req,Model model) throws Exception{
		try {
			String p = req.getParameter("page");
			String startDate = req.getParameter("startDate");
			String endDate = req.getParameter("endDate");
			int reporttype = Integer.valueOf(req.getParameter("reporttype"));
			String title = "";
			String startDatetitle = "";
			String endDatetitle = "";
			
			if(StrLib.isEmpty(startDate)){
				startDate = "0000-00-00";
				startDatetitle = "初始日期";
			}else{
				startDatetitle = startDate;
			}
			if(StrLib.isEmpty(endDate)){
				endDate = "9999-99-99";
				endDatetitle = "最后日期";
			}else{
				endDatetitle = endDate;
			}
			
			if(reporttype==1){
				title = startDatetitle+"至"+endDatetitle+" 按完成人统计";
			}else{
				title = startDatetitle+"至"+endDatetitle+" 按版本统计";
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
			allRows = zttaskService.findRowsreport(startDate, endDate, reporttype);
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
			
			List<Object[]> sssMap = zttaskService.findByPagereport(offset, pageSize, startDate, endDate, reporttype);
			
			for(int i=0;i<sssMap.size();i++){
				if(!"0".equals(sssMap.get(i)[3].toString())){
					sssMap.get(i)[5] = Double.valueOf(new DecimalFormat("#.00").format(Double.valueOf(sssMap.get(i)[3].toString())*100/
							(Double.valueOf(sssMap.get(i)[3].toString())+Double.valueOf(sssMap.get(i)[4].toString()))));
				}else{
					sssMap.get(i)[5] = 0.00;
				}
				
				if(!"0".equals(sssMap.get(i)[1].toString())){
					sssMap.get(i)[6] = 0.00;
				}else if(!sssMap.get(i)[1].toString().equals(sssMap.get(i)[2].toString())){
					sssMap.get(i)[6] = 100.00;
				}else{
					sssMap.get(i)[6] = Double.valueOf(new DecimalFormat("#.00").format(Double.valueOf(sssMap.get(i)[1].toString())*100/
							(Double.valueOf(sssMap.get(i)[2].toString())-Double.valueOf(sssMap.get(i)[1].toString()))));
				}				
			}
			
			model.addAttribute("zttasklist", sssMap);
			model.addAttribute("reporttype", reporttype);
			model.addAttribute("title", title);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/zentao/list.do");
			return "error";
		}
		return "/jsp/projectversion/zentaoreport";
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
	}

}
