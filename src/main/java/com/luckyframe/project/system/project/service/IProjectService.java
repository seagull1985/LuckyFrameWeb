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
	public Project selectProjectById(int projectId);
	
	/**
	 * 查询所有项目管理列表
	 * @param projectId 默认先中标记
	 * @return
	 * @author Seagull
	 * @date 2019年3月9日
	 */
	public List<Project> selectProjectAll(Integer projectId);
	
	/**
     * 查询测试项目管理列表
     * 
     * @param project 测试项目管理信息
     * @return 测试项目管理集合
     */
	public List<Project> selectProjectList(Project project);
	
	/**
     * 新增测试项目管理
     * 
     * @param project 测试项目管理信息
     * @return 结果
     */
	public int insertProject(Project project);
	
	/**
     * 修改测试项目管理
     * 
     * @param project 测试项目管理信息
     * @return 结果
     */
	public int updateProject(Project project);
		
	/**
     * 删除测试项目管理信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProjectByIds(String ids);
	
	/**
	 * 根据客户端ID查询所有项目列表(打标记)
	 * @param clientId
	 * @return
	 * @author Seagull
	 * @date 2019年2月25日
	 */
	public List<Project> selectProjectsByClientId(int clientId);
	
	/**
	 * 根据角色ID查询所有项目列表(打标记)
	 * @param roleId
	 * @return
	 * @author Seagull
	 * @date 2019年2月25日
	 */
	public List<Project> selectProjectsByRoleId(int roleId);
	
	/**
	 * 校验项目名称唯一性
	 * @param project
	 * @return
	 * @author Seagull
	 * @date 2019年2月27日
	 */
	public String checkProjectNameUnique(Project project);
	
    /**
     * 校验项目标识唯一性
     * @param project
     * @return
     * @author Seagull
     * @date 2019年2月27日
     */
    public String checkProjectSignUnique(Project project);
}
