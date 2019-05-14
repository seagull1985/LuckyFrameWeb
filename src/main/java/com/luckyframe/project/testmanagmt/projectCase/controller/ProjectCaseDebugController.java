package com.luckyframe.project.testmanagmt.projectCase.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.luckyframe.common.constant.ClientConstants;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.client.HttpRequest;
import com.luckyframe.common.utils.client.WebDebugCaseEntity;
import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.project.system.client.domain.Client;
import com.luckyframe.project.system.client.service.IClientService;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCase;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseDebug;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseDebugService;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseService;

/**
 * 用例调试日志记录 信息操作处理
 * 
 * @author luckyframe
 * @date 2019-03-14
 */
@Controller
@RequestMapping("/testmanagmt/projectCaseDebug")
public class ProjectCaseDebugController extends BaseController
{
    private String prefix = "testmanagmt/projectCase";
	
	@Autowired
	private IProjectCaseDebugService projectCaseDebugService;
	
	@Autowired
	private IProjectCaseService projectCaseService;
	
	@Autowired
	private IClientService clientService;
	
	/**
	 * 测试用例Debug
	 * @param caseId
	 * @param mmap
	 * @return
	 * @author Seagull
	 * @date 2019年3月14日
	 */
	@GetMapping("/projectCaseDebug/{caseId}")
	public String projectCaseDebug(@PathVariable("caseId") Integer caseId, ModelMap mmap)
	{
		ProjectCase projectCase = projectCaseService.selectProjectCaseById(caseId);
		ProjectCaseDebug projectCaseDebug=new ProjectCaseDebug();
		projectCaseDebug.setCaseId(caseId);
		projectCaseDebug.setUserId(ShiroUtils.getUserId().intValue());
		List<Client> clients=clientService.selectClientsByProjectId(projectCase.getProjectId());
		if(clients.size()>0){
			List<String> driverPathList=clientService.selectClientDriverListById(clients.get(0).getClientId());
			mmap.put("driverPathList", driverPathList);
		}		
		mmap.put("projectCaseDebug", projectCaseDebug);
		mmap.put("clients", clients);
	    return prefix + "/debugCase";
	}
	
	/**
	 * 测试用例Debug运行
	 * @param listSteps
	 * @return
	 * @author Seagull
	 * @date 2019年3月14日
	 */
	@RequiresPermissions("testmanagmt:projectCase:edit")
	@Log(title = "测试用例调试(Debug)", businessType = BusinessType.OTHER)
	@PostMapping("/debugCaseRun")
	@ResponseBody
	public String debugCaseRun(ProjectCaseDebug projectCaseDebug)
	{
		JSONObject json = new JSONObject();
		try {
			projectCaseDebugService.deleteProjectCaseDebugById(projectCaseDebug);
			WebDebugCaseEntity webDebugCaseEntity = new WebDebugCaseEntity();
	    	if(StringUtils.isEmpty(projectCaseDebug.getDriverPath())){
	    		webDebugCaseEntity.setLoadpath("/TestDriven");
	        }else{
	        	webDebugCaseEntity.setLoadpath(projectCaseDebug.getDriverPath());
	        }
	    	webDebugCaseEntity.setCaseId(projectCaseDebug.getCaseId());
	    	webDebugCaseEntity.setUserId(ShiroUtils.getUserId().intValue());
			Client client = clientService.selectClientById(projectCaseDebug.getClientId());
			String url= "http://"+client.getClientIp()+":"+ClientConstants.CLIENT_MONITOR_PORT+"/webDebugCase";
			String result=HttpRequest.httpClientPost(url, JSONObject.toJSONString(webDebugCaseEntity),3000);
			
			if(result.contains("正常")){
				json.put("status", "info");
			}else{
				json.put("status", "warning");
			}
			
			json.put("ms", result);
			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
			json.put("status", "error");
			json.put("ms", "调试用例启动出现异常！");
			return json.toString();
		}
		
	}
	
	/**
	 * 获取用例调试日志
	 * @param listSteps
	 * @return
	 * @author Seagull
	 * @date 2019年3月14日
	 */
	@RequiresPermissions("testmanagmt:projectCase:edit")
	@PostMapping("/refreshDebugLog")
	@ResponseBody
	public String refreshDebugLog(ProjectCaseDebug projectCaseDebug)
	{
		JSONObject json = new JSONObject();
		/*调试结束标识 0 进行中 1结束 2异常*/
		String status="0";
		String ms="获取调试日志失败！";
		try {
			List<ProjectCaseDebug> logList=projectCaseDebugService.selectProjectCaseDebugList(projectCaseDebug);
			StringBuilder stringBuilder = new StringBuilder();
			for(ProjectCaseDebug log:logList){
				stringBuilder.append(log.getLogLevel()+": "+log.getLogDetail()+"</br>");
				status=log.getDebugIsend().toString();
			}
			ms=stringBuilder.toString();
			
			json.put("status", status);
			json.put("ms", ms);
			return json.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			json.put("status", "2");
			json.put("ms", "获取调试日志出现异常！");
			return json.toString();
		}
		
	}
	
	/**
	 * 新增保存用例调试日志记录
	 */
	@Log(title = "用例调试日志记录", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProjectCaseDebug projectCaseDebug)
	{		
		return toAjax(projectCaseDebugService.insertProjectCaseDebug(projectCaseDebug));
	}
	
	/**
	 * 删除用例调试日志记录
	 */
	@Log(title = "用例调试日志记录", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(projectCaseDebugService.deleteProjectCaseDebugByIds(ids));
	}
	
}
