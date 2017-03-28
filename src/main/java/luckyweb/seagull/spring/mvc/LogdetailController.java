package luckyweb.seagull.spring.mvc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import luckyweb.seagull.spring.entity.TestLogdetail;
import luckyweb.seagull.spring.service.LogDetailService;

@Controller
@RequestMapping("/logDetail")
public class LogdetailController
{
	private static final Logger	log	= Logger.getLogger(LogdetailController.class);

	@Resource(name = "logdetailService")
	private LogDetailService	logdetailService;

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
	String newpath = new String(fname.getBytes("ISO-8859-1"), "UTF-8");
	String imagePath =  "D:\\log\\ScreenShot\\";
	String absolutePath = imagePath + newpath;
	FileInputStream fis = new FileInputStream(absolutePath);
	OutputStream os = response.getOutputStream();
	try
	{
	int count = 0;
	byte[] buffer = new byte[1024 * 1024];
	while ((count = fis.read(buffer)) != -1)
	os.write(buffer, 0, count);
	os.flush();
	}
	catch (IOException e)
	{
	e.printStackTrace();
	}
	finally
	{
	if (os != null)
	os.close();
	if (fis != null)
	fis.close();
	}
	}
}
