package com.luckyframe.project.system.project.service;

import com.luckyframe.project.system.project.domain.Project;
import java.util.List;

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
	public Project selectProjectById(Integer projectId);
	
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
	
}
