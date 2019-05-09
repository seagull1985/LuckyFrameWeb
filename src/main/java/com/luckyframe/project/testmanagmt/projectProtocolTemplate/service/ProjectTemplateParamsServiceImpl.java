package com.luckyframe.project.testmanagmt.projectProtocolTemplate.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.project.testmanagmt.projectProtocolTemplate.domain.ProjectTemplateParams;
import com.luckyframe.project.testmanagmt.projectProtocolTemplate.mapper.ProjectTemplateParamsMapper;

/**
 * 模板参数管理 服务层实现
 * 
 * @author luckyframe
 * @date 2019-03-05
 */
@Service
public class ProjectTemplateParamsServiceImpl implements IProjectTemplateParamsService 
{
	@Autowired
	private ProjectTemplateParamsMapper projectTemplateParamsMapper;

	/**
     * 查询模板参数管理信息
     * 
     * @param paramsId 模板参数管理ID
     * @return 模板参数管理信息
     */
    @Override
	public ProjectTemplateParams selectProjectTemplateParamsById(Integer paramsId)
	{
	    return projectTemplateParamsMapper.selectProjectTemplateParamsById(paramsId);
	}
	
	/**
     * 查询模板参数管理列表
     * 
     * @param projectTemplateParams 模板参数管理信息
     * @return 模板参数管理集合
     */
	@Override
	public List<ProjectTemplateParams> selectProjectTemplateParamsList(ProjectTemplateParams projectTemplateParams)
	{
	    return projectTemplateParamsMapper.selectProjectTemplateParamsList(projectTemplateParams);
	}
	
    /**
     * 新增模板参数管理
     * 
     * @param projectTemplateParams 模板参数管理信息
     * @return 结果
     */
	@Override
	public int insertProjectTemplateParams(ProjectTemplateParams projectTemplateParams)
	{
	    return projectTemplateParamsMapper.insertProjectTemplateParams(projectTemplateParams);
	}
	
	/**
     * 修改模板参数管理
     * 
     * @param projectTemplateParams 模板参数管理信息
     * @return 结果
     */
	@Override
	public int updateProjectTemplateParams(ProjectTemplateParams projectTemplateParams)
	{
		int result=0;
		projectTemplateParams.setUpdateBy(ShiroUtils.getLoginName());
		projectTemplateParams.setUpdateTime(new Date());
		if(projectTemplateParams.getParamsId().equals(0)){
			projectTemplateParams.setCreateBy(ShiroUtils.getLoginName());
			projectTemplateParams.setCreateTime(new Date());
			result=projectTemplateParamsMapper.insertProjectTemplateParams(projectTemplateParams);
		}else{
			result=projectTemplateParamsMapper.updateProjectTemplateParams(projectTemplateParams);
		}
	    return result;
	}

	/**
     * 删除模板参数管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProjectTemplateParamsByIds(List<ProjectTemplateParams> templateParams)
	{
		Integer templateId=0;
		if(null!=templateParams&&templateParams.size()>0){
			if(null!=templateParams.get(0).getTemplateId()){
				templateId=templateParams.get(0).getTemplateId();
			}
		}
		return projectTemplateParamsMapper.deleteProjectTemplateParamsByTemplateId(templateId);
	}
}
