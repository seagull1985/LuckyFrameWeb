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
import java.rmi.Naming;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
import luckyweb.seagull.util.StrLib;
import net.sf.json.JSONObject;
import rmi.service.RunService;

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
	
	@SuppressWarnings({ "unused", "unchecked" })
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
		String RecordJson = StrLib.listToJson(loglist);
		pw.print(RecordJson);
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
	while ((count = fis.read(buffer)) != -1)
	os.write(buffer, 0, count);
	}
	catch (IOException e)
	{
	e.printStackTrace();
	}
	finally
	{
	if (os != null)
	os.flush();
	os.close();
	if (fis != null)
	fis.close();
    if (file.exists())
    file.delete();
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
			if (!UserLoginController.permissionboolean(req, "case_step")) {
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
				JSONObject jsonObject = JSONObject.fromObject(sb.toString());
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
	
}
