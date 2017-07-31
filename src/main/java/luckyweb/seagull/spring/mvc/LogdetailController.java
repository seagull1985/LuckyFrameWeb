package luckyweb.seagull.spring.mvc;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.Naming;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import luckyweb.seagull.spring.entity.TestLogdetail;
import luckyweb.seagull.spring.entity.TestTaskexcute;
import luckyweb.seagull.spring.service.LogDetailService;
import luckyweb.seagull.spring.service.TestJobsService;
import luckyweb.seagull.spring.service.TestTastExcuteService;
import rmi.service.RunService;

@Controller
@RequestMapping("/logDetail")
public class LogdetailController
{

	@Resource(name = "logdetailService")
	private LogDetailService	logdetailService;
	
	@Resource(name = "tastExcuteService")
	private TestTastExcuteService	tastExcuteService;

	@Resource(name = "testJobsService")
	private TestJobsService	      testJobsService;

	public LogDetailService getLogdetailService()
	{
		return logdetailService;
	}

	public void setLogdetailService(LogDetailService logdetailService)
	{
		this.logdetailService = logdetailService;
	}

	@RequestMapping(value = "/list/{caseId}.do")
	public String list(@PathVariable int caseId, TestLogdetail logDetail, Model model)
	{
		logDetail.getTestCasedetail().setId(caseId);
		List<TestLogdetail> list = this.logdetailService.list(logDetail);
		model.addAttribute("list", list);
		model.addAttribute("allRows", list.size());
		model.addAttribute("caseId", caseId);
		return "/jsp/task/logdetail_list";
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
}
