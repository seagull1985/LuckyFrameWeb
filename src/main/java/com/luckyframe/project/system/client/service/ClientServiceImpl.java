package com.luckyframe.project.system.client.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyframe.common.constant.ClientConstants;
import com.luckyframe.common.exception.BusinessException;
import com.luckyframe.common.support.Convert;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.security.PermissionUtils;
import com.luckyframe.project.monitor.job.domain.Job;
import com.luckyframe.project.monitor.job.service.IJobService;
import com.luckyframe.project.monitor.job.task.ClientHeart;
import com.luckyframe.project.system.client.domain.Client;
import com.luckyframe.project.system.client.domain.ClientProject;
import com.luckyframe.project.system.client.mapper.ClientMapper;
import com.luckyframe.project.system.client.mapper.ClientProjectMapper;
import com.luckyframe.project.system.project.mapper.ProjectMapper;
import com.luckyframe.project.testexecution.taskScheduling.mapper.TaskSchedulingMapper;

/**
 * 客户端管理 服务层实现
 * 
 * @author luckyframe
 * @date 2019-02-20
 */
@Service
public class ClientServiceImpl implements IClientService 
{
	@Autowired
	private ClientMapper clientMapper;
	
	@Autowired
	private ClientProjectMapper clientProjectMapper;
	
	@Autowired
	private TaskSchedulingMapper taskSchedulingMapper;
	
    @Autowired
    private IJobService jobService;
    
	@Autowired
	private ProjectMapper projectMapper;

	/**
     * 查询客户端管理信息
     * 
     * @param clientId 客户端管理ID
     * @return 客户端管理信息
     */
    @Override
	public Client selectClientById(Integer clientId)
	{
	    return clientMapper.selectClientById(clientId);
	}
	
	/**
	 * 根据客户端ID获取驱动路径列表
	 * @param clientId
	 * @return
	 * @author Seagull
	 * @date 2019年3月14日
	 */
    @Override
	public List<String> selectClientDriverListById(Integer clientId)
	{
    	Client client = clientMapper.selectClientById(clientId);
    	String[] clientPath=Convert.toStrArrayBySymbol(client.getClientPath(), ";");
    	List<String> clientPathList = Arrays.asList(clientPath);
	    return clientPathList;
	}
    
	/**
     * 查询客户端管理列表
     * 
     * @param client 客户端管理信息
     * @return 客户端管理集合
     */
	@Override
	public List<Client> selectClientList(Client client)
	{
	    return clientMapper.selectClientList(client);
	}
	
    /**
     * 新增客户端管理
     * 
     * @param client 客户端管理信息
     * @return 结果
     */
	@Override
	public int insertClient(Client client)
	{		
        int rows = clientMapper.insertClient(client);
        // 新增客户端与项目关联
		insertClientProject(client);
		ClientHeart.iniClientListen(client.getClientIp());
	    return rows;
	}
	
	/**
     * 修改客户端管理
     * 
     * @param client 客户端管理信息
     * @return 结果
     */
	@Override
	public int updateClient(Client client)
	{
		//先删除客户端与项目关联关系
		clientProjectMapper.deleteClientProjectById(client.getClientId());
        // 新增客户端与项目关联
		insertClientProject(client);
		ClientHeart.iniClientListen(client.getClientIp());
	    return clientMapper.updateClient(client);
	}
	
	/**
	 * 根据IP修改客户端状态以及备注
	 * @param clientIp
	 * @param status
	 * @return
	 * @author Seagull
	 * @date 2019年4月13日
	 */
	@Override
	public int updateClientStatusByIp(Client client)
	{
		ClientHeart.setClientStatus(client.getClientIp(), client.getStatus());
	    return clientMapper.updateClientStatusByIp(client);
	}

	/**
     * 删除客户端管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteClientByIds(String ids)
	{
		String[] clientIds=Convert.toStrArray(ids);
		for(String clientIdStr:clientIds){
			int clientId=Integer.valueOf(clientIdStr);
			if(taskSchedulingMapper.selectTaskSchedulingCountByClientId(clientId)>0){
				throw new BusinessException(String.format("【%1$s】已绑定任务调度,不能删除", clientMapper.selectClientById(clientId).getClientName()));
			}
		}
		
		Integer result = 0;
		for(String clientIdStr:clientIds){
			int clientId=Integer.valueOf(clientIdStr);
			Client client = clientMapper.selectClientById(clientId);
			List<ClientProject> clientProjectList= clientProjectMapper.selectClientProjectsById(clientId);
			for(ClientProject clientProject:clientProjectList){
			  Integer projectId = clientProject.getProjectId();
			  if(!PermissionUtils.isProjectPermsPassByProjectId(projectId)){	
				  throw new BusinessException(String.format("没有项目【%1$s】删除客户端权限", projectMapper.selectProjectById(projectId).getProjectName()));
			  }		
		    }
			
			//删除心跳任务
			Job job = new Job();
			job.setJobId(client.getJobId().longValue());
			jobService.deleteJob(job);
			//删除客户端与项目关联关系
			clientProjectMapper.deleteClientProjectById(clientId);
			//删除客户端表
			clientMapper.deleteClientById(clientId);
			ClientHeart.removeClientListen(client.getClientIp());
			result++;
		}

		return result;
	}
	
	/**
	 * 批量增加客户端与项目的对应关系
	 * @param client
	 * @author Seagull
	 * @date 2019年2月20日
	 */
    private void insertClientProject(Client client)
    {
    	Integer[] projects = client.getProjectIds();
        if (StringUtils.isNotNull(projects))
        {
            // 新增客户端与项目的映射表
            List<ClientProject> clientProjectList = new ArrayList<ClientProject>();
            for (Integer projectId : projects)
            {
            	ClientProject cp = new ClientProject();
            	cp.setClientId(client.getClientId());
            	cp.setProjectId(projectId);
            	clientProjectList.add(cp);
            }
            if (clientProjectList.size() > 0)
            {
            	clientProjectMapper.insertBatchClientProject(clientProjectList);
            }
        }
    }
    
    /**
     * 校验客户端名称是否唯一
     */
    @Override
    public String checkClientNameUnique(Client client)
    {
        Long clientId = StringUtils.isNull(client.getClientId()) ? -1L : client.getClientId();
        Client info = clientMapper.checkClientNameUnique(client.getClientName());
        if (StringUtils.isNotNull(info) && info.getClientId().longValue() != clientId.longValue())
        {
            return ClientConstants.CLIENT_NAME_NOT_UNIQUE;
        }
        return ClientConstants.CLIENT_NAME_UNIQUE;
    }
    
    /**
     * 校验客户端IP是否唯一
     */
    @Override
    public String checkIpUnique(Client client)
    {
        Long clientId = StringUtils.isNull(client.getClientId()) ? -1L : client.getClientId();
        Client info = clientMapper.checkIpUnique(client.getClientIp());
        if (StringUtils.isNotNull(info) && info.getClientId().longValue() != clientId.longValue())
        {
            return ClientConstants.CLIENT_IP_NOT_UNIQUE;
        }
        return ClientConstants.CLIENT_IP_UNIQUE;
    }
    
    /**
     * 根据项目ID查询所有客户端列表(打标记)
     * @param projectId
     * @return
     * @author Seagull
     * @date 2019年3月14日
     */
    @Override
    public List<Client> selectClientsByProjectId(int projectId)
    {
    	ClientProject clientProject = new ClientProject();
    	clientProject.setProjectId(projectId);;
        List<ClientProject> clientProjects = clientProjectMapper.selectClientProjectList(clientProject);
        List<Client> clients = clientMapper.selectClientList(new Client());
        List<Client> returnClientProjects = new ArrayList<Client>();
        for (ClientProject cp : clientProjects)
        {
            for (Client client : clients)
            {
                if (cp.getClientId() == client.getClientId())
                {
                	int status=client.getStatus();
                	if(status==0){
                		client.setStatusStr(" 状态正常");
                	}else if(status==1){
                		client.setStatusStr(" 状态异常");
                	}else{
                		client.setStatusStr(" 状态未知");
                	}
                	returnClientProjects.add(client);
                    break;
                }
            }
        }
        return returnClientProjects;
    }
}
