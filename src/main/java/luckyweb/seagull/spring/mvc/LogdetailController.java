package luckyweb.seagull.spring.mvc;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.rmi.Naming;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.spring.entity.ProjectCase;
import luckyweb.seagull.spring.entity.ProjectCasesteps;
import luckyweb.seagull.spring.entity.TestCasedetail;
import luckyweb.seagull.spring.entity.TestLogdetail;
import luckyweb.seagull.spring.entity.TestTaskexcute;
import luckyweb.seagull.spring.service.CaseDetailService;
import luckyweb.seagull.spring.service.LogDetailService;
import luckyweb.seagull.spring.service.ProjectCaseService;
import luckyweb.seagull.spring.service.ProjectCasestepsService;
import luckyweb.seagull.spring.service.TestJobsService;
import luckyweb.seagull.spring.service.TestTastExcuteService;
import luckyweb.seagull.util.DateLib;
import luckyweb.seagull.util.StrLib;
import rmi.service.RunService;

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
@RequestMapping("/logDetail")
public class LogdetailController
{

	@Resource(name = "logdetailService")
	private LogDetailService	logdetailService;
	
	@Resource(name = "casedetailService")
	private CaseDetailService	casedetailService;
	
	@Resource(name = "tastExcuteService")
	private TestTastExcuteService	tastExcuteService;

	@Resource(name = "testJobsService")
	private TestJobsService	      testJobsService;
	
	@Resource(name = "projectCaseService")
	private ProjectCaseService projectcaseservice;

	@Resource(name = "projectCasestepsService")
	private ProjectCasestepsService casestepsservice;
	
	@RequestMapping(value = "/list.do")
	private void ajaxGetSellRecord(Integer limit, Integer offset, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		String caseId = request.getParameter("caseId");
		TestLogdetail logDetail = new TestLogdetail();
		// 得到客户端传递的查询参数
		if (!StrLib.isEmpty(caseId)) {
			logDetail.setCaseid(Integer.valueOf(caseId));
		}
		
		List<TestLogdetail> loglist = logdetailService.list(logDetail);
		for(int i=0;i<loglist.size();i++){
			TestLogdetail log = loglist.get(i);
			log.setTestCasedetail(null);
			loglist.set(i, log);
		}
		// 转换成json字符串
		JSONArray recordJson = StrLib.listToJson(loglist);
		pw.print(recordJson);
	}
	
	@RequestMapping(value = "/showImage.do")
	public void showImage(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	response.setContentType("text/html; charset=UTF-8");
	response.setContentType("image/jpeg");
	String fname = request.getParameter("filename");
	int logid = Integer.valueOf(request.getParameter("logid"));
	TestLogdetail tld=logdetailService.load(logid);
	TestTaskexcute tte=tastExcuteService.load(tld.getTaskid());
	
	String newname = new String(fname.getBytes("ISO-8859-1"), "UTF-8");
	
	 //调用远程对象，注意RMI路径与接口必须与服务器配置一致
	RunService service=(RunService)Naming.lookup("rmi://"+tte.getTestJob().getClientip()+":6633/RunService"); 
	byte[] bfis=service.getlogimg(newname);
	
	String path = System.getProperty("user.dir")+"\\";
	String pathName = path + newname;
	File file = new File(pathName);
    try {
        if (file.exists()){
        	file.delete();
        }
        file.createNewFile();
        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file));
        os.write(bfis);
        os.flush();
        os.close();
    } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    
    FileInputStream fis = new FileInputStream(pathName);
    System.out.println(pathName);
	OutputStream os = response.getOutputStream();
	try
	{
	int count = 0;
	byte[] buffer = new byte[1024 * 1024];
	while ((count = fis.read(buffer)) != -1){
		os.write(buffer, 0, count);
	}
	
	}
	catch (IOException e)
	{
	e.printStackTrace();
	}
	finally
	{
	if (os != null){
		os.flush();
	}
	os.close();
	if (fis != null){
		fis.close();
	}
    if (file.exists()){
        file.delete();
    }

	}
	}
	
	/**
	 * 更新用例的预期结果
	 * 
	 * @param tj
	 * @param br
	 * @param model
	 * @param req
	 * @param rsp
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	@RequestMapping(value = "/updateStepResult.do")
	public void updateStepResult(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		try {
			rsp.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			PrintWriter pw = rsp.getWriter();
			JSONObject json = new JSONObject();
			if (!UserLoginController.permissionboolean(req, PublicConst.AUTHCASESTEPS)) {
				json.put("status", "fail");
				json.put("ms", "更新用例预期结果失败,权限不足,请联系管理员!");
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
				String logid = jsonObject.getString("logid");
				String status="fail";
				String ms="更新用例预期结果失败！";
				TestLogdetail tld=logdetailService.load(Integer.valueOf(logid));
				TestCasedetail tcd=casedetailService.load(tld.getCaseid());
				ProjectCase pc=projectcaseservice.getCaseBySign(tcd.getCaseno());
				if(!UserLoginController.oppidboolean(req, pc.getProjectid())){
					status="fail";
					ms="更新用例预期结果失败,项目权限不足,请联系管理员!";					
				}else{
	                if(!StrLib.isEmpty(logid)){
	    				List<ProjectCasesteps> steps= casestepsservice.getSteps(pc.getId());
	    				
	    				String testresult=tld.getDetail().substring(tld.getDetail().lastIndexOf("测试结果：")+5);
	    				for(ProjectCasesteps step:steps){
	    					if(tld.getStep().equals(String.valueOf(step.getStepnum()))){
	    						if (null != req.getSession().getAttribute("usercode")
	    								&& null != req.getSession().getAttribute("username")) {
	    							String usercode = req.getSession().getAttribute("usercode").toString();
	    							step.setOperationer(usercode);
	    							pc.setOperationer(usercode);
	    						}
	    						Date currentTime = new Date();
	    						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    						String time = formatter.format(currentTime);
	    						
	    						step.setTime(time);   						
	    						step.setExpectedresult(testresult);
	    						
	    						pc.setTime(time);
	    						
	    						casestepsservice.modify(step);
	    						projectcaseservice.modify(pc);
	    						status="success";
	    						ms="更新用例【"+pc.getSign()+"】第【"+step.getStepnum()+"】步预期结果成功！";
	    						break;
	    					}
	    				}
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
	

	@RequestMapping(value = "/getindexdata.do")
	public void getIndexData(HttpServletRequest req, HttpServletResponse rsp) throws Exception{
		String[] taskdata = new String[2];
		String[] casedata = new String[2];
		String[] logdata = new String[2];
		String[] caseadddata = new String[2];
		
		TestTaskexcute task = new TestTaskexcute();
		String startdate=(String)tastExcuteService.getTopTaskDate().get(0);
		int days=DateLib.getDays(startdate);
		taskdata[0] = String.valueOf(days);
		int taskcount=tastExcuteService.findRows(task);
		taskdata[1] = String.valueOf(taskcount);
		
		TestCasedetail caseDetail = new TestCasedetail();
		int casecount=casedetailService.findRows(caseDetail);
		casedata[0] = String.valueOf(casecount);
		caseDetail.setCasestatus("0");
		int casesuccount=casedetailService.findRows(caseDetail);
		if(0==casecount&&0==casesuccount){
			casedata[1]="0";
		}else{
			double percase =(double)casesuccount/casecount;
			BigDecimal bcase =new BigDecimal(percase*100);    
			percase=bcase.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
			casedata[1] = String.valueOf(percase);
		}
		
		TestLogdetail logDetail = new TestLogdetail();
		if(0==casecount){
			logdata[0]="0";
		}else{
			double perrl =(double)casecount/45;
			perrl = perrl/days;
			BigDecimal bperrl =new BigDecimal(perrl);
			perrl=bperrl.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			logdata[0] = String.valueOf(perrl);
		}
		int logcount=logdetailService.findRows(logDetail);
		logdata[1] = String.valueOf(logcount);
		
		ProjectCase projectcase = new ProjectCase();
		int caseaddcount=projectcaseservice.findRows(projectcase);
		caseadddata[0] = String.valueOf(caseaddcount);
		String beforeccount=projectcaseservice.getBeforeDayRows(30);
		caseadddata[1] = String.valueOf(caseaddcount-Integer.valueOf(beforeccount));
		
		rsp.setContentType("application/json");
		rsp.setCharacterEncoding("utf-8");
		JSONArray taskArray = (JSONArray) JSONArray.toJSON(taskdata);
		JSONArray caseArray = (JSONArray) JSONArray.toJSON(casedata);
		JSONArray logArray = (JSONArray) JSONArray.toJSON(logdata);
		JSONArray caseaddArray = (JSONArray) JSONArray.toJSON(caseadddata);
		JSONObject jsobjcet = new JSONObject();
		jsobjcet.put("taskdata", taskArray);
		jsobjcet.put("casedata", caseArray); 
		jsobjcet.put("logdata", logArray); 
		jsobjcet.put("caseadddata", caseaddArray); 
		
		rsp.getWriter().write(jsobjcet.toString());
	}
	
	@RequestMapping(value = "/getindexreport.do")
	public void getIndexReport(HttpServletRequest req, HttpServletResponse rsp) throws Exception{
		List<Object[]> taskreport = tastExcuteService.listindexreport(30);
		

		String[] casetotal;
		String[] casesuc;
		String[] casefail;
		String[] caselock;
		String[] casenoex;
		String[] createdate;
		
		if(null!=taskreport&&0!=taskreport.size()){
		    casetotal = new String[taskreport.size()];
			casesuc = new String[taskreport.size()];
			casefail = new String[taskreport.size()];
			caselock = new String[taskreport.size()];
			casenoex = new String[taskreport.size()];
			createdate = new String[taskreport.size()];	
		}else{
		    casetotal = new String[1];
			casesuc = new String[1];
			casefail = new String[1];
			caselock = new String[1];
			casenoex = new String[1];
			createdate = new String[1];
			
		    casetotal[0] = "0";
			casesuc[0] = "0";
			casefail[0] = "0";
			caselock[0] = "0";
			casenoex[0] = "0";
			createdate[0] = DateLib.today("yyyy-MM-dd");
		}

		for(int i=0;i<taskreport.size();i++){
			casetotal[i] = taskreport.get(i)[0].toString();
			casesuc[i] = taskreport.get(i)[1].toString();
			casefail[i] = taskreport.get(i)[2].toString();
			caselock[i] = taskreport.get(i)[3].toString();
			casenoex[i] = taskreport.get(i)[4].toString();
			createdate[i] = taskreport.get(i)[5].toString();
		}
		
		JSONArray  jsoncasetotal=(JSONArray) JSONArray.toJSON(casetotal);
		JSONArray  jsoncasesuc=(JSONArray) JSONArray.toJSON(casesuc);
		JSONArray  jsoncasefail=(JSONArray) JSONArray.toJSON(casefail);
		JSONArray  jsoncaselock=(JSONArray) JSONArray.toJSON(caselock);
		JSONArray  jsoncasenoex=(JSONArray) JSONArray.toJSON(casenoex);
		JSONArray  jsoncreatedate=(JSONArray) JSONArray.toJSON(createdate);
		
		JSONObject jsobjcet = new JSONObject();
		jsobjcet.put("casetotal", jsoncasetotal);
		jsobjcet.put("casesuc", jsoncasesuc); 
		jsobjcet.put("casefail", jsoncasefail); 
		jsobjcet.put("caselock", jsoncaselock); 
		jsobjcet.put("casenoex", jsoncasenoex); 
		jsobjcet.put("casedate", jsoncreatedate);
		
		rsp.setContentType("application/json");
		rsp.setCharacterEncoding("utf-8");
		rsp.getWriter().write(jsobjcet.toString());
	}
}
