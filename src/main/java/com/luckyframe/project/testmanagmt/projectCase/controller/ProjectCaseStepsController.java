package com.luckyframe.project.testmanagmt.projectCase.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.security.PermissionUtils;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCase;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseSteps;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseService;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseStepsService;

/**
 * 测试用例步骤管理 信息操作处理
 * 
 * @author luckyframe
 * @date 2019-02-26
 */
@Controller
@RequestMapping("/testmanagmt/projectCaseSteps")
public class ProjectCaseStepsController extends BaseController
{
    private String prefix = "testmanagmt/projectCase";
	
	@Autowired
	private IProjectCaseStepsService projectCaseStepsService;

	@Autowired
	private IProjectCaseService projectCaseService;
	
	/**
	 * 修改测试用例步骤管理
	 */
	@GetMapping("/edit/{caseId}")
	public String edit(@PathVariable("caseId") Integer caseId, ModelMap mmap)
	{
		ProjectCase projectCase=projectCaseService.selectProjectCaseById(caseId);
		ProjectCaseSteps projectCaseSteps = new ProjectCaseSteps();
		projectCaseSteps.setCaseId(caseId);
		List<ProjectCaseSteps> stepsList=projectCaseStepsService.selectProjectCaseStepsList(projectCaseSteps);
		
		if(stepsList.size()==0){
			projectCaseSteps.setAction("");
			projectCaseSteps.setExpectedResult("");
			projectCaseSteps.setExtend("");
			projectCaseSteps.setProjectId(projectCase.getProjectId());
			projectCaseSteps.setStepId(0);
			projectCaseSteps.setStepOperation("");
			projectCaseSteps.setStepParameters("");
			projectCaseSteps.setStepPath("");
			projectCaseSteps.setStepSerialNumber(1);
			projectCaseSteps.setStepType(projectCase.getCaseType());
			stepsList.add(projectCaseSteps);
		}
		
		mmap.put("stepsList", stepsList);
		mmap.put("projectCase", projectCase);
	    return prefix + "/projectCaseSteps";
	}
	
	/**
	 * 修改保存测试用例步骤管理
	 */
	@RequiresPermissions("testmanagmt:projectCase:edit")
	@Log(title = "测试用例步骤管理", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/editSave",method=RequestMethod.POST,consumes="application/json")
	@ResponseBody
	public AjaxResult editSave(@RequestBody List<ProjectCaseSteps> listSteps)
	{
		if(!PermissionUtils.isProjectPermsPassByProjectId(listSteps.get(0).getProjectId())){
			return error("没有此项目编辑用例步骤权限");
		}
		
		int result=0;
		projectCaseStepsService.deleteProjectCaseStepsByIds(listSteps);
		int stepSerialNumber=1;
		for(ProjectCaseSteps projectCaseSteps:listSteps){
			projectCaseSteps.setStepSerialNumber(stepSerialNumber);
			result=result+projectCaseStepsService.insertProjectCaseSteps(projectCaseSteps);
			stepSerialNumber++;
		}
		return toAjax(result);
	}

	/**
	 * 行内子查询步骤
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author Seagull
	 * @date 2019年5月9日
	 */
	@RequiresPermissions("testmanagmt:projectCase:list")
	@RequestMapping(value = "/list")
	public void list(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		String caseIdStr = request.getParameter("caseId");
		Integer caseId = 0;
		// 得到客户端传递的查询参数
		if (StringUtils.isNotEmpty(caseIdStr)) {
			caseId = Integer.valueOf(caseIdStr);
		}
				
		ProjectCaseSteps projectCaseSteps = new ProjectCaseSteps();
		projectCaseSteps.setCaseId(caseId);
		List<ProjectCaseSteps> stepsList=projectCaseStepsService.selectProjectCaseStepsList(projectCaseSteps);
		
		// 转换成json字符串
		JSONArray recordJson= JSONArray.parseArray(JSON.toJSONString(stepsList,SerializerFeature.WriteNullStringAsEmpty));
		pw.print(recordJson);
	}
	
	/**
	 * 修改保存项目测试用例管理
	 */
	@RequiresPermissions("testmanagmt:projectCase:edit")
	@Log(title = "测试用例步骤管理", businessType = BusinessType.UPDATE)
	@PostMapping("/stepEditSave")
	@ResponseBody
	public AjaxResult stepEditSave(ProjectCaseSteps projectCaseSteps)
	{		
		if(!PermissionUtils.isProjectPermsPassByProjectId(projectCaseSteps.getProjectId())){
			return error("没有此项目修改用例步骤权限");
		}
		
		return toAjax(projectCaseStepsService.updateProjectCaseSteps(projectCaseSteps));
	}
}
