package com.luckyframe.project.system.clientConfig.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.luckyframe.framework.web.domain.BaseEntity;

/**
 * 客户端配置表 sys_client_config
 * 
 * @author luckyframe
 * @date 2020-05-27
 */
public class ClientConfig extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private Integer id;
	/** 客户端id */
	private Integer clientId;
	/** 原始客户端id */
	private Integer currentClientId;
	/** 客户端配置名称 */
	private String configKey;
	/** 客户端配置值 */
	private String configValue;
	//客户端名称
	private String clientName;
	//客户端ip
	private String clientIp;

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setClientId(Integer clientId) 
	{
		this.clientId = clientId;
	}

	public Integer getClientId() 
	{
		return clientId;
	}
	public void setConfigKey(String configKey) 
	{
		this.configKey = configKey;
	}

	public String getConfigKey() 
	{
		return configKey;
	}
	public void setConfigValue(String configValue) 
	{
		this.configValue = configValue;
	}

	public String getConfigValue() 
	{
		return configValue;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public Integer getCurrentClientId() {
		return currentClientId;
	}

	public void setCurrentClientId(Integer currentClientId) {
		this.currentClientId = currentClientId;
	}

	@Override
	public String toString() {
		return "ClientConfig{" +
				"id=" + id +
				", clientId=" + clientId +
				", configKey='" + configKey + '\'' +
				", configValue='" + configValue + '\'' +
				", clientName='" + clientName + '\'' +
				", clientIp='" + clientIp + '\'' +
				'}';
	}
}
