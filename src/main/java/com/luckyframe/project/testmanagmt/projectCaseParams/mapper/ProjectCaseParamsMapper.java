package com.luckyframe.project.testmanagmt.projectCaseParams.mapper;

import java.util.List;

import com.luckyframe.project.testmanagmt.projectCaseParams.domain.ProjectCaseParams;	

/**
 * 用例公共参数 数据层
 * 
 * @author luckyframe
 * @date 2019-03-21
 */
public interface ProjectCaseParamsMapper 
{
	/**
     * 查询用例公共参数信息
     * 
     * @param paramsId 用例公共参数ID
     * @return 用例公共参数信息
     */
	public ProjectCaseParams selectProjectCaseParamsById(Integer paramsId);
	
	/**
     * 查询用例公共参数列表
     * 
     * @param projectCaseParams 用例公共参数信息
     * @return 用例公共参数集合
     */
	public List<ProjectCaseParams> selectProjectCaseParamsList(ProjectCaseParams projectCaseParams);
	
	/**
	 * 根据项目ID查询项目下以及所有项目公共的参数
	 * @param projectId
	 * @return
	 * @author Seagull
	 * @date 2019年6月18日
	 */
	public List<ProjectCaseParams> selectProjectCaseParamsListByProjectId(Integer projectId);
	
	/**
     * 新增用例公共参数
     * 
     * @param projectCaseParams 用例公共参数信息
     * @return 结果
     */
	public int insertProjectCaseParams(ProjectCaseParams projectCaseParams);
	
	/**
     * 修改用例公共参数
     * 
     * @param projectCaseParams 用例公共参数信息
     * @return 结果
     */
	public int updateProjectCaseParams(ProjectCaseParams projectCaseParams);
	
	/**
     * 删除用例公共参数
     * 
     * @param paramsId 用例公共参数ID
     * @return 结果
     */
	public int deleteProjectCaseParamsById(Integer paramsId);
	
	/**
     * 批量删除用例公共参数
     * 
     * @param paramsIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteProjectCaseParamsByIds(String[] paramsIds);
	
	/**
	 * 检查项目中参数名称的唯一性
	 * @param projectCaseParams
	 * @return
	 * @author Seagull
	 * @date 2019年3月21日
	 */
	public ProjectCaseParams checkProjectCaseParamsNameUnique(ProjectCaseParams projectCaseParams);
}