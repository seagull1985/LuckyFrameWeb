package luckyweb.seagull.spring.mvc;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.spring.entity.OperationLog;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.util.StrLib;


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

	public OperationLogService getOperationlogService() {
		return operationlogservice;
	}
	
	
	public void setOperationlogservice(OperationLogService operationlogservice) {
		this.operationlogservice = operationlogservice;
	}

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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list.do")
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
