package luckyweb.seagull.spring.mvc;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import luckyweb.seagull.spring.entity.TestCasedetail;
import luckyweb.seagull.spring.service.TestTastExcuteService;

import net.sf.json.JSONArray;


@Controller
@RequestMapping("/autoTestChar")
public class AutoTestChartController {
	
	@Resource(name = "tastExcuteService")
	private TestTastExcuteService	tastExcuteService;
	
	public void setTastExcuteService(TestTastExcuteService tastExcuteService)
	{
		this.tastExcuteService = tastExcuteService;
	}

	/**
	 * 
	 * Job查询
	 * @param tj
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/chart.do")
	public String chart(HttpServletRequest req,Model model) {
		try {
			List<Object[]> taskinfo = tastExcuteService.listtastinfo();
			
			String jobname[] = new String[taskinfo.size()];
			String casetotal[] = new String[taskinfo.size()];
			String casesuc[] = new String[taskinfo.size()];
			String casefail[] = new String[taskinfo.size()];
			String caselock[] = new String[taskinfo.size()];
			String casenoex[] = new String[taskinfo.size()];
			String createtime[] = new String[taskinfo.size()];
			String taskid[] = new String[taskinfo.size()];
			int j=0;
			for(int i=0;i<taskinfo.size();i++){
				if(Integer.valueOf(taskinfo.get(i)[2].toString())==0){
					continue;
				}
				taskid[j] = taskinfo.get(i)[0].toString();
				jobname[j] = taskinfo.get(i)[1].toString();
				casetotal[j] = taskinfo.get(i)[2].toString();
				casesuc[j] = taskinfo.get(i)[3].toString();
				casefail[j] = taskinfo.get(i)[4].toString();
				caselock[j] = taskinfo.get(i)[5].toString();
				casenoex[j] = taskinfo.get(i)[6].toString();
				createtime[j] = taskinfo.get(i)[7].toString();
				j++;
			}
			
			JSONArray  jsontaskid=JSONArray.fromObject(taskid);
			JSONArray  jsonjobname=JSONArray.fromObject(jobname);
			JSONArray  jsoncasetotal=JSONArray.fromObject(casetotal);
			JSONArray  jsoncasesuc=JSONArray.fromObject(casesuc);
			JSONArray  jsoncasefail=JSONArray.fromObject(casefail);
			JSONArray  jsoncaselock=JSONArray.fromObject(caselock);
			JSONArray  jsoncasenoex=JSONArray.fromObject(casenoex);
			JSONArray  jsoncreatetime=JSONArray.fromObject(createtime);

			req.setAttribute("taskid", jsontaskid);
			req.setAttribute("jobname", jsonjobname);
			req.setAttribute("casetotal", jsoncasetotal);
			req.setAttribute("casesuc", jsoncasesuc);
			req.setAttribute("casefail", jsoncasefail);
			req.setAttribute("caselock", jsoncaselock);
			req.setAttribute("casenoex", jsoncasenoex);
			req.setAttribute("createtime", jsoncreatetime);

			TestCasedetail caseDetail = new TestCasedetail();
			model.addAttribute("testCasedetail", caseDetail);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/jsp/echart/echarts_pie5";
	}
	
	/**
	 * 
	 * Job查询
	 * @param tj
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/testchart.do")
	public String testchart(HttpServletRequest req,Model model) {
		return "/jsp/echart/echarts_pie6";
	}
}
