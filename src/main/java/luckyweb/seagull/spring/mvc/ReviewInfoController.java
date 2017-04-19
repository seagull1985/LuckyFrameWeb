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
import luckyweb.seagull.spring.entity.ReviewInfo;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.ReviewInfoService;
import luckyweb.seagull.spring.service.ReviewService;
import luckyweb.seagull.spring.service.SectorProjectsService;
import luckyweb.seagull.util.StrLib;

@Controller
@RequestMapping("/reviewinfo")
public class ReviewInfoController {
	
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
	public String list(HttpServletRequest req, ReviewInfo reviewinfo, Model model)
			throws Exception {
		model.addAttribute("reviewinfo", reviewinfo);

		try {
			String p = req.getParameter("page");
			int reviewid = 0;
			if(req.getParameter("reviewid")!=null){
				reviewid = Integer.valueOf(req.getParameter("reviewid"));
				reviewinfo.setReview_id(reviewid);
				reviewinfo.setId(0);
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
			allRows = reviewinfoservice.findRows(reviewinfo);
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
			
			List<Review> sssMap = reviewinfoservice.findByPage(reviewinfo, offset, pageSize);
			Review review = reviewservice.load(reviewid);
			
			model.addAttribute("splist", sssMap);
			model.addAttribute("reviewid", reviewid);
			model.addAttribute("review", review);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/reviewinfo/list.do");
			return "error";
		}
		return "/jsp/review/reviewinfo";
	}
	

	/**
	 * 添加评审信息
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
	public String add(@Valid @ModelAttribute("reviewinfo") ReviewInfo reviewinfo, BindingResult br, Model model,
	        HttpServletRequest req, HttpServletResponse rsp) throws Exception
	{
		try
		{
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");

			if(!UserLoginController.permissionboolean(req, "revinfo_1")){
				model.addAttribute("reviewinfo", new ReviewInfo());
				model.addAttribute("url", "/review/list.do");
				model.addAttribute("message", "当前用户无权限添加评审信息，请联系管理员！");
				return "success";
			}
			
			String retVal = "/jsp/review/reviewinfo_add";
			Review review = null;
			if (req.getMethod().equals("POST"))
			{
				if (br.hasErrors())
				{
					return retVal;
				}
				String message = "";
				
				if(reviewinfo.getProjectid()==0){
					message = "请选择项目!";
					model.addAttribute("message", message);
					model.addAttribute("projects", QueueListener.qa_projlist);
					return retVal;
				}
				
				int reviewid = 0;
				if(req.getParameter("reviewid")==null||Integer.valueOf(req.getParameter("reviewid"))==0){
					review = new Review();
					SectorProjects p = new SectorProjects();
					p.setProjectid(reviewinfo.getProjectid());
					review.setSectorProjects(p);
					
					review.setVersion(reviewinfo.getVersion());
					review.setReview_type(reviewinfo.getReview_type());
					review.setReview_date(reviewinfo.getReview_date());
					review.setBug_num(1);
					if("已修复".equals(reviewinfo.getStatus())){
						review.setRepair_num(review.getRepair_num()+1);
					}else{
						review.setRepair_num(0);
					}
					review.setConfirm_date(reviewinfo.getReview_date());
					review.setReview_object(reviewinfo.getReview_object());
					review.setReview_result(reviewinfo.getReview_result());
					review.setResult_Confirmor(reviewinfo.getResult_Confirmor());
					review.setRemark(reviewinfo.getRemark());
					
					reviewid = reviewservice.add(review);
				}else{
					review = reviewservice.load(Integer.valueOf(req.getParameter("reviewid")));
					reviewid = review.getId();

					review.setBug_num(review.getBug_num()+1);
					if("已修复".equals(reviewinfo.getStatus())){
						review.setRepair_num(review.getRepair_num()+1);
					}
					review.setConfirm_date(reviewinfo.getConfirm_date());
					review.setResult_Confirmor(reviewinfo.getDuty_officer());
					
					reviewservice.modify(review);
				}

				reviewinfo.setBug_description(reviewinfo.getBug_description());
				reviewinfo.setConfirm_date(reviewinfo.getConfirm_date());
				reviewinfo.setDuty_officer(reviewinfo.getDuty_officer());
				reviewinfo.setReview_id(reviewid);
				reviewinfo.setStatus(reviewinfo.getStatus());
				reviewinfo.setCorrective(reviewinfo.getCorrective());
			
				int infoid = reviewinfoservice.add(reviewinfo);
				
				operationlogservice.add(req, "QA_REVIEWINFO", infoid, 
						reviewinfo.getProjectid(),"评审记录登记成功！");
				
				model.addAttribute("message", "添加成功");
				model.addAttribute("url", "/reviewinfo/list.do?reviewid="+reviewid);
				return retVal;

			}else{

				if(req.getParameter("reviewid")!=null&&Integer.valueOf(req.getParameter("reviewid"))!=0){
					review = reviewservice.load(Integer.valueOf(req.getParameter("reviewid")));
					
					reviewinfo.setProjectid(review.getSectorProjects().getProjectid());
					reviewinfo.setVersion(review.getVersion());
					reviewinfo.setReview_date(review.getReview_date());
					reviewinfo.setReview_type(review.getReview_type());
					reviewinfo.setReview_object(review.getReview_object());
					reviewinfo.setReview_result(review.getReview_result());
					reviewinfo.setRemark(review.getRemark());
					reviewinfo.setProjectname(review.getSectorProjects().getProjectname());
					reviewinfo.setResult_Confirmor(review.getResult_Confirmor());
					retVal = "/jsp/review/reviewinfo_add2";
					model.addAttribute("url", "/reviewinfo/list.do?reviewid="+review.getId());
				}
			}
				model.addAttribute("reviewinfo", reviewinfo);
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
		if(!UserLoginController.permissionboolean(req, "revinfo_2")){
			model.addAttribute("reviewinfo", new ReviewInfo());
			model.addAttribute("url", "/review/list.do");
			model.addAttribute("message", "当前用户无权限删除评审信息，请联系管理员！");
			return "success";
		}
		
		int id = Integer.valueOf(req.getParameter("id"));
		ReviewInfo reviewinfo = reviewinfoservice.load(id);
		Review review = reviewservice.load(reviewinfo.getReview_id());
		if ("已修复".equals(reviewinfo.getStatus())) {
			review.setRepair_num(review.getRepair_num() - 1);
		}
		
		review.setBug_num(review.getBug_num()-1);

		reviewservice.modify(review);
		
		try
		{		
			reviewinfoservice.delete(id);
		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/reviewinfo/list.do?reviewid="+review.getId());
			return "error";
		}
		
		operationlogservice.add(req, "QA_REVIEWINFO", id, 
				review.getSectorProjects().getProjectid(),"评审明细信息删除成功！");

		String message = "删除成功！";
		model.addAttribute("reviewinfo", new ReviewInfo());
		model.addAttribute("message", message);
		model.addAttribute("url", "/reviewinfo/list.do?reviewid="+review.getId());
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
	public String update(@Valid @ModelAttribute("reviewinfo") ReviewInfo reviewinfo, BindingResult br, Model model,
	        HttpServletRequest req, HttpServletResponse rsp) throws Exception
	{
		try
		{
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");

			ReviewInfo reinfo = reviewinfoservice.load(Integer.valueOf(req.getParameter("id")));
			Review review = reviewservice.load(reinfo.getReview_id());
			
			if(!UserLoginController.permissionboolean(req, "revinfo_3")){
				model.addAttribute("reviewinfo", new ReviewInfo());
				model.addAttribute("url",  "/reviewinfo/list.do?reviewid="+reinfo.getReview_id());
				model.addAttribute("message", "当前用户无权限修改评审信息，请联系管理员！");
				return "success";
			}
			
			String retVal = "/jsp/review/reviewinfo_update";
	
			if (req.getMethod().equals("POST"))
			{
				if (br.hasErrors()) {
					return retVal;
				}
				String message = "";

				if ("已修复".equals(reviewinfo.getStatus())) {
					review.setRepair_num(review.getRepair_num() + 1);
				}
				review.setConfirm_date(reviewinfo.getConfirm_date());
				reviewservice.modify(review);

				reinfo.setBug_description(reviewinfo.getBug_description());
				reinfo.setConfirm_date(reviewinfo.getConfirm_date());
				reinfo.setDuty_officer(reviewinfo.getDuty_officer());
				reinfo.setStatus(reviewinfo.getStatus());
				reinfo.setCorrective(reviewinfo.getCorrective());
			
				reviewinfoservice.modify(reinfo);
				
				operationlogservice.add(req, "QA_REVIEWINFO", reinfo.getId(), 
						reinfo.getProjectid(),"评审记录修改成功！");
				
				model.addAttribute("message", "修改成功");
				model.addAttribute("url", "/reviewinfo/list.do?reviewid="+reinfo.getReview_id());
				return retVal;

			}else{
				reinfo.setProjectid(review.getSectorProjects().getProjectid());
				reinfo.setVersion(review.getVersion());
				reinfo.setReview_date(review.getReview_date());
				reinfo.setReview_type(review.getReview_type());
				reinfo.setReview_object(review.getReview_object());
				reinfo.setReview_result(review.getReview_result());
				reinfo.setRemark(review.getRemark());
				reinfo.setProjectname(review.getSectorProjects().getProjectname());
				reinfo.setResult_Confirmor(review.getResult_Confirmor());
			}
			    model.addAttribute("url", "/reviewinfo/list.do?reviewid="+reinfo.getReview_id());
				model.addAttribute("reviewinfo", reinfo);
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
