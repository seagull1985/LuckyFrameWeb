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

import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.spring.entity.Review;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.ReviewInfoService;
import luckyweb.seagull.spring.service.ReviewService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.util.StrLib;

@Controller
@RequestMapping("/review")
public class ReviewController {
	
	private int allPage;
	private int pageSize = 20;
	private int allRows;
	private int page = 1;
	private int offset; 
	
	@Resource(name = "reviewService")
	private ReviewService reviewservice;
	
	@Resource(name = "reviewInfoService")
	private ReviewInfoService reviewinfoservice;
	
	@Resource(name = "sectorprojectsService")
	private SectorProjectsService sectorprojectsService;
	

	public ReviewService getreviewService() {
		return reviewservice;
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
	public String list(HttpServletRequest req, Review review, Model model)
			throws Exception {
		model.addAttribute("review", review);

		try {
			String p = req.getParameter("page");
			String projectid = req.getParameter("projectid");
			String restarttime = req.getParameter("review_startdate");
			String reendtime = req.getParameter("review_enddate");

			if (!StrLib.isEmpty(projectid))
			{
				review.setProjectid(Integer.valueOf(projectid));
			}
			
			if (restarttime!=null&&!"".equals(restarttime))
			{
				review.setReview_startdate(restarttime);
			}
			
			if (reendtime!=null&&!"".equals(reendtime))
			{
				review.setReview_enddate(reendtime);
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
			allRows = reviewservice.findRows(review);
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
			
			List<Review> sssMap = reviewservice.findByPage(review, offset, pageSize);

			model.addAttribute("splist", sssMap);
			model.addAttribute("projects", QueueListener.qa_projlist);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/review/list.do");
			return "error";
		}
		return "/jsp/review/review";
	}
	
	/**
	 * 删除评审记录
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete.do", method = RequestMethod.GET)
	public String delete(Model model,HttpServletRequest req) throws Exception
	{
		if(!UserLoginController.permissionboolean(req, "rev_2")){
			model.addAttribute("review", new Review());
			model.addAttribute("url", "/review/list.do");
			model.addAttribute("message", "当前用户无权限删除评审信息，请联系软件质量管理室！");
			return "success";
		}
		
		int id = Integer.valueOf(req.getParameter("id"));
		Review review = reviewservice.load(id);
		try
		{		
			reviewinfoservice.delete_reviewid(id);
			reviewservice.delete(id);
		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/review/list.do");
			return "error";
		}
		
		operationlogservice.add(req, "QA_REVIEW", id, 
				review.getSectorProjects().getProjectid(),"评审信息删除成功！");

		String message = "删除成功！";
		model.addAttribute("review", new Review());
		model.addAttribute("message", message);
		model.addAttribute("url", "/review/list.do");
		return "success";
	}
	
	/**
	 * 修改评审信息
	 * @param tj
	 * @param br
	 * @param model
	 * @param req
	 * @param rsp
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/update.do")
	public String update(@Valid @ModelAttribute("review") Review review, BindingResult br, Model model,
	        HttpServletRequest req, HttpServletResponse rsp) throws Exception
	{
		try
		{
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");

			Review review_update = reviewservice.load(Integer.valueOf(req.getParameter("id")));
			
			if(!UserLoginController.permissionboolean(req, "rev_3")){
				model.addAttribute("review", new Review());
				model.addAttribute("url",  "/review/list.do");
				model.addAttribute("message", "当前用户无权限修改评审信息，请联系软件质量管理室！");
				return "success";
			}
			
			String retVal = "/jsp/review/review_update";
	
			if (req.getMethod().equals("POST"))
			{
				if (br.hasErrors()) {
					return retVal;
				}
				String message = "";

				SectorProjects p = new SectorProjects();
				p.setProjectid(review.getProjectid());
				review_update.setSectorProjects(p);
				review_update.setVersion(review.getVersion());
				review_update.setReview_date(review.getReview_date());
				review_update.setReview_type(review.getReview_type());
				review_update.setReview_object(review.getReview_object());
				review_update.setConfirm_date(review.getConfirm_date());
				review_update.setResult_Confirmor(review.getResult_Confirmor());
				review_update.setReview_result(review.getReview_result());
				review_update.setRemark(review.getRemark());
				
				reviewservice.modify(review_update);
				
				operationlogservice.add(req, "QA_REVIEW", review_update.getId(), 
						review_update.getSectorProjects().getProjectid(),"评审记录修改成功！");
				
				model.addAttribute("message", "修改成功");
				model.addAttribute("url", "/reviewinfo/list.do");
				return retVal;

			}
			    review_update.setProjectid(review_update.getSectorProjects().getProjectid());
			    model.addAttribute("url", "/review/list.do");
				model.addAttribute("review", review_update);
				model.addAttribute("projects", QueueListener.qa_projlist);
				return retVal;

		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/review/list.do");
			return "error";
		}

	}
	
	
	public static void main(String[] args) throws Exception {

	}

}
