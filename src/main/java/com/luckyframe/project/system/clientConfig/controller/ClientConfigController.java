package com.luckyframe.project.system.clientConfig.controller;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.luckyframe.common.utils.EncryptionUtils;
import com.luckyframe.common.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.project.system.clientConfig.domain.ClientConfig;
import com.luckyframe.project.system.clientConfig.service.IClientConfigService;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.page.TableDataInfo;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.common.utils.poi.ExcelUtil;
/**
 * 客户端配置 信息操作处理
 * 
 * @author luckyframe
 * @date 2020-05-27
 */
@Controller
@RequestMapping("/system/clientConfig")
public class ClientConfigController extends BaseController
{
    private String prefix = "system/clientConfig";
	
	@Autowired
	private IClientConfigService clientConfigService;
	
	@RequiresPermissions("system:clientConfig:view")
	@GetMapping()
	public String clientConfig()
	{
	    return prefix + "/clientConfig";
	}
	
	/**
	 * 查询客户端配置列表
	 */
	@RequiresPermissions("system:clientConfig:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ClientConfig clientConfig)
	{
		startPage();
        List<ClientConfig> list = clientConfigService.selectClientConfigList(clientConfig);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出客户端配置列表
	 */
	@RequiresPermissions("system:clientConfig:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ClientConfig clientConfig)
    {
    	List<ClientConfig> list = clientConfigService.selectClientConfigList(clientConfig);
        ExcelUtil<ClientConfig> util = new ExcelUtil<ClientConfig>(ClientConfig.class);
        return util.exportExcel(list, "clientConfig");
    }
	
	/**
	 * 新增客户端配置
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存客户端配置
	 */
	@RequiresPermissions("system:clientConfig:add")
	@Log(title = "客户端配置", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ClientConfig clientConfig)
	{		
		return toAjax(clientConfigService.insertClientConfig(clientConfig));
	}

	/**
	 * 修改客户端配置
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		ClientConfig clientConfig = clientConfigService.selectClientConfigById(id);
		clientConfig.setCurrentClientId(clientConfig.getClientId());
		mmap.put("clientConfig", clientConfig);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存客户端配置
	 */
	@RequiresPermissions("system:clientConfig:edit")
	@Log(title = "客户端配置", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ClientConfig clientConfig)
	{		
		return toAjax(clientConfigService.updateClientConfig(clientConfig));
	}
	
	/**
	 * 删除客户端配置
	 */
	@RequiresPermissions("system:clientConfig:remove")
	@Log(title = "客户端配置", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(clientConfigService.deleteClientConfigByIds(ids));
	}

	/**
	 * 获得客户端配置
	 */
	@GetMapping("/config/{clientId}/{key}")
	@ResponseBody
	public String config(@PathVariable("clientId") String clientIdStr,@PathVariable("key") String key) throws Exception {
		Integer clientId = Integer.getInteger(clientIdStr);
		String value = clientConfigService.queryConfigValue(clientId, key);
		JSONObject res=new JSONObject();
		res.put("code",200);
		if(StringUtils.isEmpty(value))
		{
			res.put("code",404);
		}
		value= EncryptionUtils.encrypt(value);
		res.put("value", value);
		return res.toJSONString();
	}
	
}
