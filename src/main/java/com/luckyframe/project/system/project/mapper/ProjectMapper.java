package com.luckyframe.project.system.project.mapper;

import com.luckyframe.project.system.project.domain.Project;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 测试项目管理 数据层
 * 
 * @author luckyframe
 * @date 2019-02-13
 */
@Component
public interface ProjectMapper 
{
	/**
     * 查询测试项目管理信息
     * 
     * @param projectId 测试项目管理ID
     * @return 测试项目管理信息
     */
	Project selectProjectById(Integer projectId);
	
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
     * 删除测试项目管理
     * 
     * @param projectId 测试项目管理ID
     * @return 结果
     */
	int deleteProjectById(Integer projectId);
	
	/**
     * 批量删除测试项目管理
     * 
     * @param projectIds 需要删除的数据ID
     * @return 结果
     */
	int deleteProjectByIds(String[] projectIds);
	
	/**
	 * 校验项目名称唯一性
	 * @param projectName 项目名称
	 * @return 返回项目对象
	 * @author Seagull
	 * @date 2019年2月27日
	 */
	Project checkProjectNameUnique(String projectName);
	
    /**
     * 校验项目标识唯一性
     * @param projectSign 项目标识
     * @return 返回项目对象
     * @author Seagull
     * @date 2019年2月27日
     */
    Project checkProjectSignUnique(String projectSign);
	
}