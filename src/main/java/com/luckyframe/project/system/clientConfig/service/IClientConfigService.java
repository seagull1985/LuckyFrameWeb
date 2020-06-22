package com.luckyframe.project.system.clientConfig.service;

import com.luckyframe.project.system.clientConfig.domain.ClientConfig;
import java.util.List;

/**
 * 客户端配置 服务层
 * 
 * @author luckyframe
 * @date 2020-05-27
 */
public interface IClientConfigService 
{
	/**
     * 查询客户端配置信息
     * 
     * @param id 客户端配置ID 主键ID
     * @return 客户端配置信息
     */
	public ClientConfig selectClientConfigById(Integer id);
	
	/**
     * 查询客户端配置列表
     * 
     * @param clientConfig 客户端配置信息 基本信息
     * @return 客户端配置集合
     */
	public List<ClientConfig> selectClientConfigList(ClientConfig clientConfig);
	
	/**
     * 新增客户端配置
     * 
     * @param clientConfig 客户端配置信息 基本信息
     * @return 结果
     */
	public int insertClientConfig(ClientConfig clientConfig);
	
	/**
     * 修改客户端配置
     * 
     * @param clientConfig 客户端配置信息 基本信息
     * @return 结果
     */
	public int updateClientConfig(ClientConfig clientConfig);
		
	/**
     * 删除客户端配置信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteClientConfigByIds(String ids);


	String queryConfigValue(Integer clientId,String key);
}
