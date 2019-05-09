package com.luckyframe.project.system.project.mapper;

import com.luckyframe.project.system.project.domain.Project;
import java.util.List;	

/**
 * 测试项目管理 数据层
 * 
 * @author luckyframe
 * @date 2019-02-13
 */
public interface ProjectMapper 
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
     * 删除测试项目管理
     * 
     * @param projectId 测试项目管理ID
     * @return 结果
     */
	public int deleteProjectById(Integer projectId);
	
	/**
     * 批量删除测试项目管理
     * 
     * @param projectIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteProjectByIds(String[] projectIds);
	
	/**
	 * 校验项目名称唯一性
	 * @param project
	 * @return
	 * @author Seagull
	 * @date 2019年2月27日
	 */
	public Project checkProjectNameUnique(String projectName);
	
    /**
     * 校验项目标识唯一性
     * @param project
     * @return
     * @author Seagull
     * @date 2019年2月27日
     */
    public Project checkProjectSignUnique(String projectSign);
	
}