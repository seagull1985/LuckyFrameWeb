package com.luckyframe.project.system.client.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyframe.common.support.Convert;
import com.luckyframe.project.system.client.domain.ClientProject;
import com.luckyframe.project.system.client.mapper.ClientProjectMapper;

/**
 * 客户端与项目关联 服务层实现
 * 
 * @author luckyframe
 * @date 2019-02-20
 */
@Service
public class ClientProjectServiceImpl implements IClientProjectService 
{
	@Autowired
	private ClientProjectMapper clientProjectMapper;

	/**
     * 查询客户端与项目关联信息
     * 
     * @param clientId 客户端与项目关联ID
     * @return 客户端与项目关联信息
     */
    @Override
	public List<ClientProject> selectClientProjectById(int clientId)
	{
	    return clientProjectMapper.selectClientProjectsById(clientId);
	}
	
	/**
     * 查询客户端与项目关联列表
     * 
     * @param clientProject 客户端与项目关联信息
     * @return 客户端与项目关联集合
     */
	@Override
	public List<ClientProject> selectClientProjectList(ClientProject clientProject)
	{
	    return clientProjectMapper.selectClientProjectList(clientProject);
	}
	
    /**
     * 新增客户端与项目关联
     * 
     * @param clientProject 客户端与项目关联信息
     * @return 结果
     */
	@Override
	public int insertClientProject(ClientProject clientProject)
	{
	    return clientProjectMapper.insertClientProject(clientProject);
	}
	
	/**
     * 修改客户端与项目关联
     * 
     * @param clientProject 客户端与项目关联信息
     * @return 结果
     */
	@Override
	public int updateClientProject(ClientProject clientProject)
	{
	    return clientProjectMapper.updateClientProject(clientProject);
	}

	/**
     * 删除客户端与项目关联对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteClientProjectByIds(String ids)
	{
		return clientProjectMapper.deleteClientProjectByIds(Convert.toStrArray(ids));
	}
	
}
