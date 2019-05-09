package com.luckyframe.project.system.client.service;

import java.util.List;

import com.luckyframe.project.system.client.domain.ClientProject;

/**
 * 客户端与项目关联 服务层
 * 
 * @author luckyframe
 * @date 2019-02-20
 */
public interface IClientProjectService 
{
	/**
     * 查询客户端与项目关联信息
     * 
     * @param clientId 客户端与项目关联ID
     * @return 客户端与项目关联信息
     */
	public List<ClientProject> selectClientProjectById(int clientId);
	
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
     * 删除客户端与项目关联信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteClientProjectByIds(String ids);
	
}
