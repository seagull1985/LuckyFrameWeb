package com.luckyframe.project.system.client.controller;

import java.util.HashMap;
import java.util.List;

import com.luckyframe.common.netty.NettyChannelMap;
import com.luckyframe.common.netty.NettyServer;
import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.luckyframe.common.constant.ClientConstants;
import com.luckyframe.common.constant.JobConstants;
import com.luckyframe.common.constant.ScheduleConstants;
import com.luckyframe.common.exception.BusinessException;
import com.luckyframe.common.utils.client.HttpRequest;
import com.luckyframe.common.utils.poi.ExcelUtil;
import com.luckyframe.common.utils.security.PermissionUtils;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.framework.web.page.TableDataInfo;
import com.luckyframe.project.monitor.job.domain.Job;
import com.luckyframe.project.monitor.job.service.IJobService;
import com.luckyframe.project.monitor.server.domain.set.SetServer;
import com.luckyframe.project.system.client.domain.Client;
import com.luckyframe.project.system.client.service.IClientService;
import com.luckyframe.project.system.project.service.IProjectService;

import javax.annotation.Resource;

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
	@Autowired
	private IClientService clientService;
	
    @Autowired
    private IProjectService projectService;
    
    @Autowired
    private IJobService jobService;

	@Resource
	private NettyChannelMap nettyChannelMap;
	
	@RequiresPermissions("system:client:view")
	@GetMapping()
	public String client()
	{
	    return "system/client/client";
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
	
    @RequiresPermissions("system:client:view")
    @GetMapping("/showMonitor/{clientId}")
    public String showMonitor(@PathVariable("clientId") Integer clientId, ModelMap mmap) throws Exception
    {
    	Client client = clientService.selectClientById(clientId);
		String result = HttpRequest.httpClientGet(
				"http://" + client.getClientIp() + ":" + ClientConstants.CLIENT_MONITOR_PORT + "/getClientMonitorData",client,
				new HashMap<>(0),15000);
		System.out.println(result);
		JSONObject jSONObject = JSONObject.parseObject(result);
		System.out.println(jSONObject.toJSONString());

		SetServer server = JSONObject.parseObject(jSONObject.toJSONString(),SetServer.class);
		System.out.println(JSONObject.toJSONString(server));
        mmap.put("server", server);
        return "system/client/showMonitor";
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
        ExcelUtil<Client> util = new ExcelUtil<>(Client.class);
        return util.exportExcel(list, "client");
    }
	
	/**
	 * 新增客户端管理
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
        mmap.put("projects", projectService.selectProjectAll(0));
	    return "system/client/add";
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
		for(Integer projectId:client.getProjectIds()){
			if(!PermissionUtils.isProjectPermsPassByProjectId(projectId)){				
				return error("没有项目【"+projectService.selectProjectById(projectId).getProjectName()+"】添加客户端权限");
			}		
		}
		
		int result;
		Job job=new Job();
    	job.setJobName(JobConstants.JOB_JOBNAME_FOR_CLIENTHEART);
    	job.setJobGroup(JobConstants.JOB_GROUPNAME_FOR_CLIENTHEART);
    	job.setMethodName(JobConstants.JOB_METHODNAME_FOR_CLIENTHEART);
    	job.setMethodParams(client.getClientIp());
    	job.setCronExpression("0/"+client.getCheckinterval().toString()+" * * * * ? ");
    	job.setMisfirePolicy(ScheduleConstants.MISFIRE_DO_NOTHING);
    	job.setStatus(JobConstants.JOB_STATUS_FOR_RUN);
    	job.setRemark("");
    	/*在公共调度表中插入数据*/
    	result = jobService.insertJobCron(job);
    	if(result<1){
    		return AjaxResult.error();
    	}
    	/*在调度预约表中插入数据*/
    	client.setJobId(job.getJobId().intValue());
    	
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
	    return "system/client/edit";
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
		/*不允许通过服务端修改netty方式的客户端IP
		* */
		Client oldClient=clientService.selectClientById(client.getClientId());
		if(oldClient!=null&&oldClient.getClientType().equals(1))
		{
			client.setStatus(1);
			if(!oldClient.getClientIp().equals(client.getClientIp()))
				return error("Netty客户端，请在客户端配置文件修改IP、名称");
		}
		for(Integer projectId:client.getProjectIds()){
			if(!PermissionUtils.isProjectPermsPassByProjectId(projectId)){				
				return error("没有项目【"+projectService.selectProjectById(projectId).getProjectName()+"】修改客户端权限");
			}		
		}

		if(oldClient!=null&&oldClient.getClientType().equals(0)){
			int result;
			Job job=jobService.selectJobById(client.getJobId().longValue());
			job.setMethodParams(client.getClientIp());
			job.setCronExpression("0/"+client.getCheckinterval().toString()+" * * * * ? ");
			/*在公共调度表中插入数据*/
			result = jobService.updateJob(job);
			if(result<1){
				return AjaxResult.error();
			}
		}else{
			//更新数据，删除心跳map中的数据
			NettyServer.clientMap.remove(client.getClientIp());
		}

    	client.setRemark("修改客户端信息，重新初始化");
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
        try
        {
			String[] idList=ids.split(",");
			for (String s : idList) {
				Client client = clientService.selectClientById(Integer.valueOf(s));
				NettyServer.clientMap.remove(client.getClientIp());
				Channel channel=nettyChannelMap.get(client.getClientIp());
				nettyChannelMap.remove((SocketChannel)channel);
			}
        	return toAjax(clientService.deleteClientByIds(ids));
        }
        catch (BusinessException e)
        {
            return error(e.getMessage());
        }		
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

	/**
	 * 根据客户端IP返回驱动路径
	 * @param clientId 客户端ID
	 * @return 返回驱动路径
	 */
    @GetMapping("/getDriverPathList/{clientId}")
	@ResponseBody
	public String getDriverPathList(@PathVariable("clientId") Integer clientId)
	{
		List<String> driverPathList = clientService.selectClientDriverListById(clientId);
		JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(driverPathList));
		return jsonArray.toJSONString();
	}
    
	/**
	 * 通过项目ID获取客户端列表
	 * @param projectId 项目ID
	 * @return 返回客户端列表
	 * @author Seagull
	 * @date 2019年3月26日
	 */
    @GetMapping("/getClientListByProjectId/{projectId}")
	@ResponseBody
	public String getClientListByProjectId(@PathVariable("projectId") Integer projectId)
	{
    	List<Client> clientList = clientService.selectClientsByProjectId(projectId);
		JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(clientList));
		return jsonArray.toJSONString();
	}
    
	/**
	 * 根据客户端ID获取状态
	 * @param clientId 客户端ID
	 * @return 返回客户端状态
	 * @author Seagull
	 * @date 2019年8月16日
	 */
    @GetMapping("/getClientStatusByClientId/{clientId}")
	@ResponseBody
	public String getClientStatusByClientId(@PathVariable("clientId") Integer clientId)
	{
    	Client client = clientService.selectClientById(clientId);
		return JSONObject.toJSONString(client);
	}

	/**
	 * 查询所有客户端管理列表
	 */
	@RequiresPermissions("system:client:view")
	@PostMapping("/all")
	@ResponseBody
	public List<Client> all(Client client)
	{
		List<Client> list = clientService.selectClientList(client);
		return list;
	}
}
