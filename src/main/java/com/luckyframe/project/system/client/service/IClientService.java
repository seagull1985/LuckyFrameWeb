package com.luckyframe.project.system.client.service;

import java.util.List;

import com.luckyframe.project.system.client.domain.Client;

/**
 * 客户端管理 服务层
 * 
 * @author luckyframe
 * @date 2019-02-20
 */
public interface IClientService 
{
	/**
     * 查询客户端管理信息
     * 
     * @param clientId 客户端管理ID
     * @return 客户端管理信息
     */
	Client selectClientById(Integer clientId);
	
	/**
	 * 根据客户端ID获取驱动路径列表
	 * @author Seagull
	 * @date 2019年3月14日
	 */
	List<String> selectClientDriverListById(Integer clientId);
	
	/**
     * 查询客户端管理列表
     * 
     * @param client 客户端管理信息
     * @return 客户端管理集合
     */
	List<Client> selectClientList(Client client);
	
	/**
     * 新增客户端管理
     * 
     * @param client 客户端管理信息
     * @return 结果
     */
	int insertClient(Client client);

	/**
	 * NETTY方便新增客户端管理
	 *
	 * @param client 客户端管理信息
	 * @return 结果
	 */
	int insertClientForNetty(Client client);
	
	/**
     * 修改客户端管理
     * 
     * @param client 客户端管理信息
     * @return 结果
     */
	int updateClient(Client client);

	/**
	 * NETTY方式修改客户端管理
	 *
	 * @param client 客户端管理信息
	 * @return 结果
	 */
	int updateClientForNetty(Client client);
	
	/**
	 * 根据IP修改客户端状态以及备注
	 * @author Seagull
	 * @date 2019年4月13日
	 */
	int updateClientStatusByIp(Client client);
		
	/**
     * 删除客户端管理信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	int deleteClientByIds(String ids);
	
	/**
	 * 检查客户端名称唯一性
	 * @author Seagull
	 * @date 2019年2月25日
	 */
	String checkClientNameUnique(Client client);
	
	/**
	 * 检查客户端IP唯一性
	 * @author Seagull
	 * @date 2019年2月25日
	 */
	String checkIpUnique(Client client);
	
    /**
     * 根据项目ID查询所有客户端列表(打标记)
     * @param projectId 项目ID
     * @author Seagull
     * @date 2019年3月14日
     */
    List<Client> selectClientsByProjectId(int projectId);


	/**
	 * 根据客户端IP查找客户端(netty方式)
	 */
	Client selectClientByClientIP(String clientIp);
}
