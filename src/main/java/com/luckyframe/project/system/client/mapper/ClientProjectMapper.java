package com.luckyframe.project.system.client.mapper;

import java.util.List;

import com.luckyframe.project.system.client.domain.Client;
import com.luckyframe.project.system.client.domain.ClientProject;	

/**
 * 客户端与项目关联 数据层
 * 
 * @author luckyframe
 * @date 2019-02-20
 */
public interface ClientProjectMapper 
{
	/**
     * 查询客户端与项目关联信息
     * 
     * @param clientId 客户端与项目关联ID
     * @return 客户端与项目关联信息
     */
	public List<ClientProject> selectClientProjectsById(int clientId);
	
	/**
     * 查询客户端与项目关联列表
     * 
     * @param clientProject 客户端与项目关联信息
     * @return 客户端与项目关联集合
     */
	public List<ClientProject> selectClientProjectList(ClientProject clientProject);
	
	/**
     * 新增客户端与项目关联
     * 
     * @param clientProject 客户端与项目关联信息
     * @return 结果
     */
	public int insertClientProject(ClientProject clientProject);
	
	/**
     * 修改客户端与项目关联
     * 
     * @param clientProject 客户端与项目关联信息
     * @return 结果
     */
	public int updateClientProject(ClientProject clientProject);
	
	/**
     * 删除客户端与项目关联
     * 
     * @param clientId 客户端与项目关联ID
     * @return 结果
     */
	public int deleteClientProjectById(int clientId);
	
	/**
     * 批量删除客户端与项目关联
     * 
     * @param clientIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteClientProjectByIds(String[] clientIds);
	
    /**
     * 批量新增客户端与项目信息
     * @param ClientProjectList
     * @return
     * @author Seagull
     * @date 2019年2月20日
     */
    public int insertBatchClientProject(List<ClientProject> clientProjectList);
    
    /**
     * 查询项目是否有绑定的客户端
     * @param projectId
     * @return
     * @author Seagull
     * @date 2019年2月28日
     */
    public int selectClientProjectCountByProjectId(Integer projectId);
    
    /**
     * 根据projectId查询客户端列表
     * @param projectId
     * @return
     * @author Seagull
     * @date 2019年3月14日
     */
    public List<Client> selectClientsByProjectId(int projectId);
	
}