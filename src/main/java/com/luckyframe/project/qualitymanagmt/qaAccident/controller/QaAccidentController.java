package com.luckyframe.project.qualitymanagmt.qaAccident.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.luckyframe.common.utils.DateUtils;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.poi.ExcelUtil;
import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.framework.web.page.TableDataInfo;
import com.luckyframe.project.qualitymanagmt.qaAccident.domain.PieCharts;
import com.luckyframe.project.qualitymanagmt.qaAccident.domain.QaAccident;
import com.luckyframe.project.qualitymanagmt.qaAccident.service.IQaAccidentService;
import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.system.project.service.IProjectService;

/**
 * 生产事故登记 信息操作处理
 * 
 * @author luckyframe
 * @date 2019-07-12
 */
@Controller
@RequestMapping("/qualitymanagmt/qaAccident")
public class QaAccidentController extends BaseController
{
    private String prefix = "qualitymanagmt/qaAccident";
	
	@Autowired
	private IQaAccidentService qaAccidentService;
	
	@Autowired
	private IProjectService projectService;
	
	@RequiresPermissions("qualitymanagmt:qaAccident:view")
	@GetMapping()
	public String qaAccident(ModelMap mmap)
	{
        List<Project> projects=projectService.selectProjectAll(0);
        mmap.put("projects", projects);
        if(projects.size()>0){
            if(StringUtils.isNotEmpty(ShiroUtils.getProjectId())){
            	mmap.put("defaultProjectId", ShiroUtils.getProjectId());
            }
        }
        
	    return prefix + "/qaAccident";
	}
	
	/**
	 * 查询生产事故登记列表
	 */
	@RequiresPermissions("qualitymanagmt:qaAccident:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(QaAccident qaAccident)
	{
		startPage();
        List<QaAccident> list = qaAccidentService.selectQaAccidentList(qaAccident);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出生产事故登记列表
	 */
	@RequiresPermissions("qualitymanagmt:qaAccident:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(QaAccident qaAccident)
    {
    	List<QaAccident> list = qaAccidentService.selectQaAccidentList(qaAccident);
        ExcelUtil<QaAccident> util = new ExcelUtil<QaAccident>(QaAccident.class);
        return util.exportExcel(list, "qaAccident");
    }
	
	/**
	 * 新增生产事故登记
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
		List<Project> projects = projectService.selectProjectAll(0);
		mmap.put("projects", projects);
		if (projects.size() > 0) {
			if (StringUtils.isNotEmpty(ShiroUtils.getProjectId())) {
				mmap.put("defaultProjectId", ShiroUtils.getProjectId());
			}
		}
        
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存生产事故登记
	 */
	@RequiresPermissions("qualitymanagmt:qaAccident:add")
	@Log(title = "生产事故登记", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(QaAccident qaAccident)
	{		
		return toAjax(qaAccidentService.insertQaAccident(qaAccident));
	}

	/**
	 * 修改生产事故登记
	 */
	@GetMapping("/edit/{accidentId}")
	public String edit(@PathVariable("accidentId") Integer accidentId, ModelMap mmap)
	{
        List<Project> projects=projectService.selectProjectAll(0);
        mmap.put("projects", projects);
		QaAccident qaAccident = qaAccidentService.selectQaAccidentById(accidentId);
		mmap.put("qaAccident", qaAccident);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存生产事故登记
	 */
	@RequiresPermissions("qualitymanagmt:qaAccident:edit")
	@Log(title = "生产事故登记", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(QaAccident qaAccident)
	{		
		return toAjax(qaAccidentService.updateQaAccident(qaAccident));
	}
	
	/**
	 * 展示生产事故详细信息
	 */
	@GetMapping("/showAccidentDetail/{accidentId}")
	public String showAccidentDetail(@PathVariable("accidentId") Integer accidentId, ModelMap mmap)
	{
		QaAccident qaAccident = qaAccidentService.selectQaAccidentById(accidentId);
		mmap.put("qaAccident", qaAccident);
	    return prefix + "/detail";
	}
	
	/**
	 * 删除生产事故登记
	 */
	@RequiresPermissions("qualitymanagmt:qaAccident:remove")
	@Log(title = "生产事故登记", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(qaAccidentService.deleteQaAccidentByIds(ids));
	}
	
	/**
	 * 图形展示查询条件
	 * @param mmap
	 * @return
	 * @author Seagull
	 * @date 2019年4月25日
	 */
	@GetMapping("/queryGraph")
	public String queryGraph(ModelMap mmap)
	{
        List<Project> projects=projectService.selectProjectAll(0);
        mmap.put("projects", projects);
        mmap.put("defaultStartDate", DateUtils.getDateByNum(-7));
        mmap.put("defaultEndDate", DateUtils.getDate());
	    return prefix + "/queryGraph";
	}
	
	/**
	 * 图形展示生产事故
	 * @param mmap
	 * @return
	 * @author Seagull
	 * @date 2019年7月30日
	 */
	@GetMapping("/showGraph")
	public String showGraph(HttpServletRequest request,ModelMap mmap)
	{
		String projectId = request.getParameter("projectId");
		String queryStartDate = request.getParameter("queryStartDate");
		String queryEndDate = request.getParameter("queryEndDate");
		String Statistical = request.getParameter("Statistical");
		String dataLatitude = request.getParameter("dataLatitude");
		String projectName="生产事故分析图";
		if(StringUtils.isNotEmpty(projectId)){
	        Project project=projectService.selectProjectById(Integer.valueOf(projectId));
	        projectName = project.getProjectName()+" "+projectName;
		}
		
		mmap.put("projectName", projectName);
		mmap.put("projectId", projectId);
        mmap.put("queryStartDate", queryStartDate);
        mmap.put("queryEndDate", queryEndDate);
        mmap.put("Statistical", Statistical);
        mmap.put("dataLatitude", dataLatitude);
	    return prefix + "/showGraph";
	}
	
	/**
	 * 图形展示生产事故异步加载数据
	 * @param mmap
	 * @return
	 * @author Seagull
	 * @date 2019年7月30日
	 */
	@ResponseBody
	@GetMapping("/getGraphData")
	public String getGraphData(HttpServletRequest request)
	{
		String projectId = request.getParameter("projectId");
		String queryStartDate = request.getParameter("queryStartDate");
		String queryEndDate = request.getParameter("queryEndDate");
		String statistical = request.getParameter("Statistical");
		String dataLatitude = request.getParameter("dataLatitude");
		
		QaAccident qaAccident = new QaAccident();
		if(StringUtils.isNotEmpty(projectId)){
			qaAccident.setProjectId(Integer.valueOf(projectId));
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beginTime", queryStartDate);
		params.put("endTime", queryEndDate);
		qaAccident.setParams(params);
		
		/*数据统计纬度*/
		if("1".equals(dataLatitude)){
			qaAccident.setImpactTime(0);
		}else if("2".equals(dataLatitude)){
			qaAccident.setDurationTime(0);
		}else{
			qaAccident.setAccidentType("true");
		}		
		
		List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
		
		/*事故类型或是等级纬度*/
		if("0".equals(statistical)){
			list=qaAccidentService.selectGroupByAccidentType(qaAccident);
		}else{
			list=qaAccidentService.selectGroupByAccidentLevel(qaAccident);
		}

		PieCharts[] arraydata = new PieCharts[list.size()];
		String[] legendData=new String[list.size()];
		for(int i=0;i<list.size();i++){
			Map<Object, Object> map=list.get(i);
			PieCharts pieData=new PieCharts();
			pieData.setName(map.get("selectName").toString());
			pieData.setValue(Double.valueOf(map.get("selectValue").toString()));
			arraydata[i] = pieData;
			legendData[i] = map.get("selectName").toString();
		}
		
		String titleText="事故分析饼图";
		String titleSubText="所有项目";

		JSONArray jsonArrayData = (JSONArray)JSONArray.toJSON(arraydata);
		JSONArray jsonArraylegendData = (JSONArray)JSONArray.toJSON(legendData);
		String result="{\"seriesData\":"+jsonArrayData.toString()+",\"legendData\":"+jsonArraylegendData.toString()+",\"titleText\":\""+titleText+"\",\"titleSubText\":\""+titleSubText+"\"}";
	    return result;
	}
}
