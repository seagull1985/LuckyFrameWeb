package luckyweb.seagull.spring.mvc;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import luckyweb.seagull.spring.entity.SecondarySector;
import luckyweb.seagull.spring.entity.TestCasedetail;
import luckyweb.seagull.spring.entity.TestJobs;
import luckyweb.seagull.spring.service.SecondarySectorService;
import luckyweb.seagull.util.StrLib;

@Controller
@RequestMapping("/secondarySector")
public class SecondarySectorController {
	
	private int allPage;
	private int pageSize = 20;
	private int allRows;
	private int page = 1;
	private int offset;
	
	@Resource(name = "secondarysectorService")
	private SecondarySectorService secondarysectorservice;

	public SecondarySectorService getsecondarysectorService() {
		return secondarysectorservice;
	}

	public void setSecondarySectorService(SecondarySectorService secondarysectorservice) {
		this.secondarysectorservice = secondarysectorservice;
	}
	
	@RequestMapping(value = "/secondarysector.do")
	public String qualityshow(TestCasedetail caseDetail, HttpServletRequest req,HttpServletResponse rsp,
			Model model) throws Exception {
		return "/jsp/base/secondarysector";
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
	public String list(HttpServletRequest req, SecondarySector Sector, Model model)
			throws Exception {
		model.addAttribute("secondarysector", Sector);
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
			allRows = secondarysectorservice.findRows(Sector);
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

			List<TestJobs> sssMap = secondarysectorservice.findByPage(Sector, offset,
					pageSize);
			model.addAttribute("sslist", sssMap);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/secondarySector/list.do");
			return "error";
		}
		return "/jsp/base/secondarysector";
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
