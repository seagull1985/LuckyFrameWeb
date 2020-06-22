package com.luckyframe.project.system.clientConfig.service;

import java.util.List;

import com.luckyframe.common.exception.base.BaseException;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.project.system.client.domain.Client;
import com.luckyframe.project.system.client.mapper.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.luckyframe.project.system.clientConfig.mapper.ClientConfigMapper;
import com.luckyframe.project.system.clientConfig.domain.ClientConfig;
import com.luckyframe.common.support.Convert;

/**
 * 客户端配置 服务层实现
 * 
 * @author luckyframe
 * @date 2020-05-27
 */
@Service
public class ClientConfigServiceImpl implements IClientConfigService 
{
	@Autowired
	private ClientConfigMapper clientConfigMapper;

	@Autowired
	private ClientMapper clientMapper;
	/**
     * 查询客户端配置信息
     * 
     * @param id 客户端配置ID 主键ID
     * @return 客户端配置信息 基础信息
     */
    @Override
	public ClientConfig selectClientConfigById(Integer id)
	{
	    return clientConfigMapper.selectClientConfigById(id);
	}
	
	/**
     * 查询客户端配置列表
     * 
     * @param clientConfig 客户端配置信息 基础信息
     * @return 客户端配置集合
     */
	@Override
	public List<ClientConfig> selectClientConfigList(ClientConfig clientConfig)
	{
	    return clientConfigMapper.selectClientConfig(clientConfig);
	}
	
    /**
     * 新增客户端配置
     * 
     * @param clientConfig 客户端配置信息 基础信息
     * @return 结果
     */
	@Override
	public int insertClientConfig(ClientConfig clientConfig)
	{
	    return clientConfigMapper.insertClientConfig(clientConfig);
	}
	
	/**
     * 修改客户端配置
     * 
     * @param clientConfig 客户端配置信息 基础信息
     * @return 结果
     */
	@Override
	public int updateClientConfig(ClientConfig clientConfig)
	{
	    return clientConfigMapper.updateClientConfig(clientConfig);
	}

	/**
     * 删除客户端配置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteClientConfigByIds(String ids)
	{
		return clientConfigMapper.deleteClientConfigByIds(Convert.toStrArray(ids));
	}


	@Override
	public String queryConfigValue(Integer clientId, String key) {
		if(StringUtils.isEmpty(key))
		{
			return null;
		}
		ClientConfig sysClientConfig = clientConfigMapper.selectClientConfigValue(clientId, key);
		if(sysClientConfig==null)
		{
			return "";
		}
		return sysClientConfig.getConfigValue();
	}
}
