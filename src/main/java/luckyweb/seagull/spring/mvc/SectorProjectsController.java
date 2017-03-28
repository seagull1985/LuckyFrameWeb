package luckyweb.seagull.spring.mvc;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import luckyweb.seagull.spring.entity.SecondarySector;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.entity.TestCasedetail;
import luckyweb.seagull.spring.service.SecondarySectorService;
import luckyweb.seagull.spring.service.SectorProjectsService;
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
