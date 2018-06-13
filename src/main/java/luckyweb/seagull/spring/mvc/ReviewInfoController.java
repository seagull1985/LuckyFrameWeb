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
import luckyweb.seagull.spring.entity.ReviewInfo;
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
@RequestMapping("/reviewinfo")
public class ReviewInfoController {
	
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
			int reviewid = Integer.valueOf(req.getParameter("reviewid"));
			Review review = reviewservice.load(reviewid);

			model.addAttribute("review", review);
			model.addAttribute("reviewid", reviewid);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			model.addAttribute("url", "/review/load.do");
			return "error";
		}
		return "/jsp/review/reviewinfo";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list.do")
	private void ajaxGetSellRecord(Integer limit, Integer offset, HttpServletRequest request,
			HttpServletResponse response) throws NumberFormatException, Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		PrintWriter pw = response.getWriter();
		int reviewid = Integer.valueOf(request.getParameter("reviewid"));
		ReviewInfo reviewinfo = new ReviewInfo();
		reviewinfo.setReview_id(reviewid);
		reviewinfo.setId(0);
		if (null == offset && null == limit) {
			offset = 0;
		}
		if (null == limit || limit == 0) {
			limit = 10;
		}
		List<ReviewInfo> reviewlist = reviewinfoservice.findByPage(reviewinfo, offset, limit);

		// 转换成json字符串
		JSONArray recordJson = StrLib.listToJson(reviewlist);
		// 得到总记录数
		int total = reviewinfoservice.findRows(reviewinfo);
		// 需要返回的数据有总记录数和行数据
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", recordJson);
		pw.print(json.toString());
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

			if(!UserLoginController.permissionboolean(req, PublicConst.AUTHPVERSIONINFOADD)){
				model.addAttribute("reviewinfo", new ReviewInfo());
				model.addAttribute("url", "/review/load.do");
				model.addAttribute("message", "当前用户无权限添加评审信息，请联系管理员！");
				return "success";
			}
			
			String retVal = "/jsp/review/reviewinfo_add";
			Review review = null;
			String reviewidstr="reviewid";
			String reviewstatusover="已修复";
			if (PublicConst.REQPOSTTYPE.equals(req.getMethod()))
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
				}else{
					if(!UserLoginController.oppidboolean(req, reviewinfo.getProjectid())){
						SectorProjects sp=sectorprojectsService.loadob(reviewinfo.getProjectid());
						model.addAttribute("projects", QueueListener.qa_projlist);
						model.addAttribute("message", "当前用户无权限添加项目【"+sp.getProjectname()+"】评审信息，请联系管理员！");
						return retVal;
					}
				}
				
				int reviewid = 0;
				if(req.getParameter(reviewidstr)==null||Integer.valueOf(req.getParameter(reviewidstr))==0){
					review = new Review();
					SectorProjects p = new SectorProjects();
					p.setProjectid(reviewinfo.getProjectid());
					review.setSectorProjects(p);
					
					review.setVersion(reviewinfo.getVersion());
					review.setReview_type(reviewinfo.getReview_type());
					review.setReview_date(reviewinfo.getReview_date());
					review.setBug_num(1);
					if(reviewstatusover.equals(reviewinfo.getStatus())){
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
					if(reviewstatusover.equals(reviewinfo.getStatus())){
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
						reviewinfo.getProjectid(),2,"评审记录登记成功！");
				
				model.addAttribute("message", "添加成功");
				model.addAttribute("url", "/reviewinfo/load.do?reviewid="+reviewid);
				return retVal;

			}else{

				if(req.getParameter(reviewidstr)!=null&&Integer.valueOf(req.getParameter(reviewidstr))!=0){
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
					retVal = "/jsp/review/reviewinfo_add";
					model.addAttribute("url", "/reviewinfo/load.do?reviewid="+review.getId());
				}
			}
				model.addAttribute("reviewinfo", reviewinfo);
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
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHPVERSIONINFODEL)) {
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
				String ms="删除评审明细信息失败!";
				int suc=0;
				int fail=0;
				for (int i = 0; i < jsonarr.size(); i++) {
					int id = Integer.valueOf(jsonarr.get(i).toString());
					ReviewInfo reviewinfo = reviewinfoservice.load(id);
					Review review = reviewservice.load(reviewinfo.getReview_id());
					
					if(!UserLoginController.oppidboolean(req, review.getSectorProjects().getProjectid())){
						fail++;
						continue;
					}
					if ("已修复".equals(reviewinfo.getStatus())) {
						review.setRepair_num(review.getRepair_num() - 1);
					}
					
					review.setBug_num(review.getBug_num()-1);

					reviewservice.modify(review);
					
					reviewinfoservice.delete(id);
					
					operationlogservice.add(req, "QA_REVIEWINFO", id, 
							review.getSectorProjects().getProjectid(),0,"评审明细信息删除成功！");
					suc++;
				}
				
				if(suc>0){
					status="success";
					ms="删除评审明细信息成功!";
					if(fail>0){
						status="success";
						ms="删除评审明细信息"+suc+"条成功！"+fail+"条因为无项目权限删除失败！";
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
	public String update(@Valid @ModelAttribute("reviewinfo") ReviewInfo reviewinfo, BindingResult br, Model model,
	        HttpServletRequest req, HttpServletResponse rsp) throws Exception
	{
		try
		{
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");

			ReviewInfo reinfo = reviewinfoservice.load(Integer.valueOf(req.getParameter("id")));
			Review review = reviewservice.load(reinfo.getReview_id());
			
			if(!UserLoginController.permissionboolean(req, PublicConst.AUTHPVERSIONINFOMOD)){
				model.addAttribute("reviewinfo", new ReviewInfo());
				model.addAttribute("url",  "/reviewinfo/load.do?reviewid="+reinfo.getReview_id());
				model.addAttribute("message", "当前用户无权限修改评审信息，请联系管理员！");
				return "error";
			}
			if(!UserLoginController.oppidboolean(req, review.getSectorProjects().getProjectid())){
				SectorProjects sp=sectorprojectsService.loadob(review.getSectorProjects().getProjectid());
				model.addAttribute("reviewinfo", new ReviewInfo());
				model.addAttribute("url",  "/reviewinfo/load.do?reviewid="+reinfo.getReview_id());
				model.addAttribute("message", "当前用户无权限修改项目【"+sp.getProjectname()+"】评审信息，请联系管理员！");
				return "error";
			}
			String retVal = "/jsp/review/reviewinfo_update";
	
			if (PublicConst.REQPOSTTYPE.equals(req.getMethod()))
			{
				if (br.hasErrors()) {
					return retVal;
				}
				String reviewover = "已修复";
				if (reviewover.equals(reviewinfo.getStatus())) {
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
						reinfo.getProjectid(),1,"评审记录修改成功！");
				
				model.addAttribute("message", "修改成功");
				model.addAttribute("url", "/reviewinfo/load.do?reviewid="+reinfo.getReview_id());
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
			    model.addAttribute("url", "/reviewinfo/load.do?reviewid="+reinfo.getReview_id());
				model.addAttribute("reviewinfo", reinfo);
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
