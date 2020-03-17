package com.luckyframe.project.system.config.mapper;

import com.luckyframe.project.system.config.domain.Config;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 参数配置 数据层
 * 
 * @author ruoyi
 */
@Component
public interface ConfigMapper
{
    /**
     * 查询参数配置信息
     * 
     * @param config 参数配置信息
     * @return 参数配置信息
     */
    Config selectConfig(Config config);

    /**
     * 查询参数配置列表
     * 
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    List<Config> selectConfigList(Config config);

    /**
     * 根据键名查询参数配置信息
     * 
     * @param configKey 参数键名
     * @return 参数配置信息
     */
    Config checkConfigKeyUnique(String configKey);

    /**
     * 新增参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    int insertConfig(Config config);

    /**
     * 修改参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    int updateConfig(Config config);

    /**
     * 批量删除参数配置
     * 
     * @param configIds 需要删除的数据ID
     * @return 结果
     */
    int deleteConfigByIds(String[] configIds);
}