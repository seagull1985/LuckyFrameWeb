package luckyweb.seagull.spring.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.comm.QueueListener;
import luckyweb.seagull.spring.entity.Review;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.service.OperationLogService;
import luckyweb.seagull.spring.service.ReviewInfoService;
import luckyweb.seagull.spring.service.ReviewService;
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
@RequestMapping("/review")
public class ReviewController {
	
	@Resource(name = "reviewService")
	private ReviewService reviewservice;
	
	@Resource(name = "reviewInfoService")
	private ReviewInfoService reviewinfoservice;
	
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
			model.addAttribute("url", "/review/load.do");
			return "error";
		}
		return "/jsp/review/review";
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
		Review review = new Review();
		if (null == offset && null == limit) {
			offset = 0;
		}
		if (null == limit || limit == 0) {
			limit = 10;
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(search)) {
			review.setVersion(search);
			review.setReview_type(search);
			review.setReview_result(search);
		}
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(projectid)&&!PublicConst.STATUSSTR99.equals(projectid)) {
			review.setProjectid(Integer.valueOf(projectid));
		}
		
		if (!StrLib.isEmpty(startDate)) {
			review.setReview_startdate(startDate);
		}
			
		if (!StrLib.isEmpty(endDate)) {
			review.setReview_enddate(endDate);
		}
		
		List<Review> reviewlist = reviewservice.findByPage(review, offset, limit);

		// 转换成json字符串
		JSONArray recordJson = StrLib.listToJson(reviewlist);
		// 得到总记录数
		int total = reviewservice.findRows(review);
		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", recordJson);
		pw.print(json.toString());
	}
	
	
	/**
	 * 删除评审记录
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
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHREVIEWDEL)) {
				json.put("status", "fail");
				json.put("ms", "删除失败,权限不足,请联系管理员!");
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
				String ms="删除评审记录失败!";
				int suc=0;
				int fail=0;
				for (int i = 0; i < jsonarr.size(); i++) {
					int id = Integer.valueOf(jsonarr.get(i).toString());
					Review review = reviewservice.load(id);
					
					if(!UserLoginController.oppidboolean(req, review.getSectorProjects().getProjectid())){
						fail++;
						continue;
					}
					reviewinfoservice.deleteReviewid(id);
					reviewservice.delete(id);
					operationlogservice.add(req, "QA_REVIEW", id, 
							review.getSectorProjects().getProjectid(),0,"评审信息删除成功！");
					suc++;
				}
				
				if(suc>0){
					status="success";
					ms="删除评审记录成功!";
					if(fail>0){
						status="success";
						ms="删除评审记录"+suc+"条成功！"+fail+"条因为无项目权限删除失败！";
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

			Review reviewUpdate = reviewservice.load(Integer.valueOf(req.getParameter("id")));
			
			if(!UserLoginController.permissionboolean(req, PublicConst.AUTHREVIEWMOD)){
				model.addAttribute("review", new Review());
				model.addAttribute("url",  "/review/load.do");
				model.addAttribute("message", "当前用户无权限修改评审信息，请联系管理员！");
				return "success";
			}
			
			if(!UserLoginController.oppidboolean(req, reviewUpdate.getSectorProjects().getProjectid())){
				SectorProjects sp=sectorprojectsService.loadob(reviewUpdate.getSectorProjects().getProjectid());
				model.addAttribute("review", new Review());
				model.addAttribute("url",  "/review/load.do");
				model.addAttribute("message", "当前用户无权限修改项目【"+sp.getProjectname()+"】评审信息，请联系管理员！");
				return "error";
			}
			String retVal = "/jsp/review/review_update";
	
			if (PublicConst.REQPOSTTYPE.equals(req.getMethod()))
			{
				if (br.hasErrors()) {
					return retVal;
				}

				SectorProjects p = new SectorProjects();
				p.setProjectid(review.getProjectid());
				reviewUpdate.setSectorProjects(p);
				reviewUpdate.setVersion(review.getVersion());
				reviewUpdate.setReview_date(review.getReview_date());
				reviewUpdate.setReview_type(review.getReview_type());
				reviewUpdate.setReview_object(review.getReview_object());
				reviewUpdate.setConfirm_date(review.getConfirm_date());
				reviewUpdate.setResult_Confirmor(review.getResult_Confirmor());
				reviewUpdate.setReview_result(review.getReview_result());
				reviewUpdate.setRemark(review.getRemark());
				
				reviewservice.modify(reviewUpdate);
				
				operationlogservice.add(req, "QA_REVIEW", reviewUpdate.getId(), 
						reviewUpdate.getSectorProjects().getProjectid(),1,"评审记录修改成功！");
				
				model.addAttribute("message", "修改成功");
				model.addAttribute("url", "/reviewinfo/load.do");
				return retVal;

			}
			reviewUpdate.setProjectid(reviewUpdate.getSectorProjects().getProjectid());
			    model.addAttribute("url", "/review/load.do");
				model.addAttribute("review", reviewUpdate);
				model.addAttribute("projects", QueueListener.qa_projlist);
				return retVal;

		}
		catch (Exception e)
		{
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/review/load.do");
			return "error";
		}

	}
	
	
	public static void main(String[] args) throws Exception {

	}

}
