package com.luckyframe.project.system.client.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luckyframe.common.utils.poi.ExcelUtil;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.framework.web.page.TableDataInfo;
import com.luckyframe.project.system.client.domain.Client;
import com.luckyframe.project.system.client.service.IClientService;
import com.luckyframe.project.system.project.service.IProjectService;

/**
 * 客户端管理 信息操作处理
 * 
 * @author luckyframe
 * @date 2019-02-20
 */
@Controller
@RequestMapping("/system/client")
public class ClientController extends BaseController
{
    private String prefix = "system/client";
	
	@Autowired
	private IClientService clientService;
	
    @Autowired
    private IProjectService projectService;
	
	@RequiresPermissions("system:client:view")
	@GetMapping()
	public String client()
	{
	    return prefix + "/client";
	}
	
	/**
	 * 查询客户端管理列表
	 */
	@RequiresPermissions("system:client:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Client client)
	{
		startPage();
        List<Client> list = clientService.selectClientList(client);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出客户端管理列表
	 */
	@RequiresPermissions("system:client:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Client client)
    {
    	List<Client> list = clientService.selectClientList(client);
        ExcelUtil<Client> util = new ExcelUtil<Client>(Client.class);
        return util.exportExcel(list, "client");
    }
	
	/**
	 * 新增客户端管理
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
        mmap.put("projects", projectService.selectProjectAll(0));
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存客户端管理
	 */
	@RequiresPermissions("system:client:add")
	@Log(title = "客户端管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public AjaxResult addSave(Client client)
	{		
		return toAjax(clientService.insertClient(client));
	}

	/**
	 * 修改客户端管理
	 */
	@GetMapping("/edit/{clientId}")
	public String edit(@PathVariable("clientId") Integer clientId, ModelMap mmap)
	{
		Client client = clientService.selectClientById(clientId);
		mmap.put("projects", projectService.selectProjectsByClientId(clientId));
		mmap.put("client", client);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存客户端管理
	 */
	@RequiresPermissions("system:client:edit")
	@Log(title = "客户端管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Client client)
	{		
		return toAjax(clientService.updateClient(client));
	}
	
	/**
	 * 删除客户端管理
	 */
	@RequiresPermissions("system:client:remove")
	@Log(title = "客户端管理", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(clientService.deleteClientByIds(ids));
	}
	
    /**
     * 校验客户端名称唯一性
     */
    @PostMapping("/checkClientNameUnique")
    @ResponseBody
    public String checkClientNameUnique(Client client)
    {
        return clientService.checkClientNameUnique(client);
    }
    
    /**
     * 校验IP唯一性
     */
    @PostMapping("/checkIpUnique")
    @ResponseBody
    public String checkIpUnique(Client client)
    {
        return clientService.checkIpUnique(client);
    }
}
