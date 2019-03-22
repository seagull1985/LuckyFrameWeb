package com.luckyframe.project.testmanagmt.projectCaseParams.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyframe.common.constant.ProjectCaseParamsConstants;
import com.luckyframe.common.support.Convert;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.project.testmanagmt.projectCaseParams.domain.ProjectCaseParams;
import com.luckyframe.project.testmanagmt.projectCaseParams.mapper.ProjectCaseParamsMapper;

/**
 * 用例公共参数 服务层实现
 * 
 * @author luckyframe
 * @date 2019-03-21
 */
@Service
public class ProjectCaseParamsServiceImpl implements IProjectCaseParamsService 
{
	@Autowired
	private ProjectCaseParamsMapper projectCaseParamsMapper;

	/**
     * 查询用例公共参数信息
     * 
     * @param paramsId 用例公共参数ID
     * @return 用例公共参数信息
     */
    @Override
	public ProjectCaseParams selectProjectCaseParamsById(Integer paramsId)
	{
	    return projectCaseParamsMapper.selectProjectCaseParamsById(paramsId);
	}
	
	/**
     * 查询用例公共参数列表
     * 
     * @param projectCaseParams 用例公共参数信息
     * @return 用例公共参数集合
     */
	@Override
	public List<ProjectCaseParams> selectProjectCaseParamsList(ProjectCaseParams projectCaseParams)
	{
	    return projectCaseParamsMapper.selectProjectCaseParamsList(projectCaseParams);
	}
	
    /**
     * 新增用例公共参数
     * 
     * @param projectCaseParams 用例公共参数信息
     * @return 结果
     */
	@Override
	public int insertProjectCaseParams(ProjectCaseParams projectCaseParams)
	{
		projectCaseParams.setCreateBy(ShiroUtils.getLoginName());
		projectCaseParams.setCreateTime(new Date());
		projectCaseParams.setUpdateBy(ShiroUtils.getLoginName());
		projectCaseParams.setUpdateTime(new Date());
		
	    return projectCaseParamsMapper.insertProjectCaseParams(projectCaseParams);
	}
	
	/**
     * 修改用例公共参数
     * 
     * @param projectCaseParams 用例公共参数信息
     * @return 结果
     */
	@Override
	public int updateProjectCaseParams(ProjectCaseParams projectCaseParams)
	{
		projectCaseParams.setUpdateBy(ShiroUtils.getLoginName());
		projectCaseParams.setUpdateTime(new Date());
		
	    return projectCaseParamsMapper.updateProjectCaseParams(projectCaseParams);
	}

	/**
     * 删除用例公共参数对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProjectCaseParamsByIds(String ids)
	{
		return projectCaseParamsMapper.deleteProjectCaseParamsByIds(Convert.toStrArray(ids));
	}
	
    /**
     * 校验用例参数名称是否唯一
     */
    @Override
    public String checkProjectCaseParamsNameUnique(ProjectCaseParams projectCaseParams)
    {
        Long paramsId = StringUtils.isNull(projectCaseParams.getParamsId()) ? -1L : projectCaseParams.getParamsId();
        ProjectCaseParams info = projectCaseParamsMapper.checkProjectCaseParamsNameUnique(projectCaseParams);
        if (StringUtils.isNotNull(info) && info.getParamsId().longValue() != paramsId.longValue())
        {
            return ProjectCaseParamsConstants.PROJECTCASEPARAMS_NAME_NOT_UNIQUE;
        }
        return ProjectCaseParamsConstants.PROJECTCASEPARAMS_NAME_UNIQUE;
    }
}
