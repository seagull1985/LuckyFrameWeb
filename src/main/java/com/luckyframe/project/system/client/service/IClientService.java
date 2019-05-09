package com.luckyframe.project.system.client.service;

import com.luckyframe.project.system.client.domain.Client;
import java.util.List;

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
	public Client selectClientById(Integer clientId);
	
	/**
	 * 根据客户端ID获取驱动路径列表
	 * @param clientId
	 * @return
	 * @author Seagull
	 * @date 2019年3月14日
	 */
	public List<String> selectClientDriverListById(Integer clientId);
	
	/**
     * 查询客户端管理列表
     * 
     * @param client 客户端管理信息
     * @return 客户端管理集合
     */
	public List<Client> selectClientList(Client client);
	
	/**
     * 新增客户端管理
     * 
     * @param client 客户端管理信息
     * @return 结果
     */
	public int insertClient(Client client);
	
	/**
     * 修改客户端管理
     * 
     * @param client 客户端管理信息
     * @return 结果
     */
	public int updateClient(Client client);
	
	/**
	 * 根据IP修改客户端状态以及备注
	 * @param clientIp
	 * @param status
	 * @return
	 * @author Seagull
	 * @date 2019年4月13日
	 */
	public int updateClientStatusByIp(Client client);
		
	/**
     * 删除客户端管理信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteClientByIds(String ids);
	
	/**
	 * 检查客户端名称唯一性
	 * @param clientName
	 * @return
	 * @author Seagull
	 * @date 2019年2月25日
	 */
	public String checkClientNameUnique(Client client);
	
	/**
	 * 检查客户端IP唯一性
	 * @param clientIp
	 * @return
	 * @author Seagull
	 * @date 2019年2月25日
	 */
	public String checkIpUnique(Client client);
	
    /**
     * 根据项目ID查询所有客户端列表(打标记)
     * @param projectId
     * @return
     * @author Seagull
     * @date 2019年3月14日
     */
    public List<Client> selectClientsByProjectId(int projectId);
}
