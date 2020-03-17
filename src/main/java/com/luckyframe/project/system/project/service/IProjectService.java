package com.luckyframe.project.system.project.service;

import java.util.List;

import com.luckyframe.project.system.project.domain.Project;

/**
 * 测试项目管理 服务层
 * 
 * @author luckyframe
 * @date 2019-02-13
 */
public interface IProjectService 
{
	/**
     * 查询测试项目管理信息
     * 
     * @param projectId 测试项目管理ID
     * @return 测试项目管理信息
     */
	Project selectProjectById(int projectId);
	
	/**
	 * 查询所有项目管理列表
	 * @param projectId 默认先中标记
	 * @return 返回所有项目集合
	 * @author Seagull
	 * @date 2019年3月9日
	 */
	List<Project> selectProjectAll(Integer projectId);
	
	/**
     * 查询测试项目管理列表
     * 
     * @param project 测试项目管理信息
     * @return 测试项目管理集合
     */
	List<Project> selectProjectList(Project project);
	
	/**
     * 新增测试项目管理
     * 
     * @param project 测试项目管理信息
     * @return 结果
     */
	int insertProject(Project project);
	
	/**
     * 修改测试项目管理
     * 
     * @param project 测试项目管理信息
     * @return 结果
     */
	int updateProject(Project project);
		
	/**
     * 删除测试项目管理信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	int deleteProjectByIds(String ids);
	
	/**
	 * 根据客户端ID查询所有项目列表(打标记)
	 * @param clientId 客户端ID
	 * @return 返回客户端绑定的所有项目
	 * @author Seagull
	 * @date 2019年2月25日
	 */
	List<Project> selectProjectsByClientId(int clientId);
	
	/**
	 * 根据角色ID查询所有项目列表(打标记)
	 * @param roleId 角色ID
	 * @return 返回角色绑定的所有项目
	 * @author Seagull
	 * @date 2019年2月25日
	 */
	List<Project> selectProjectsByRoleId(int roleId);
	
	/**
	 * 校验项目名称唯一性
	 * @param project 项目对象
	 * @return 返回项目校验结果
	 * @author Seagull
	 * @date 2019年2月27日
	 */
	String checkProjectNameUnique(Project project);
	
    /**
     * 校验项目标识唯一性
     * @param project 项目对象
     * @return 返回项目校验结果
     * @author Seagull
     * @date 2019年2月27日
     */
    String checkProjectSignUnique(Project project);
}
