package com.luckyframe.project.system.project.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.luckyframe.project.system.project.mapper.ProjectMapper;
import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.system.project.service.IProjectService;
import com.luckyframe.common.support.Convert;

/**
 * 测试项目管理 服务层实现
 * 
 * @author luckyframe
 * @date 2019-02-13
 */
@Service
public class ProjectServiceImpl implements IProjectService 
{
	@Autowired
	private ProjectMapper projectMapper;

	/**
     * 查询测试项目管理信息
     * 
     * @param projectId 测试项目管理ID
     * @return 测试项目管理信息
     */
    @Override
	public Project selectProjectById(Integer projectId)
	{
	    return projectMapper.selectProjectById(projectId);
	}
	
	/**
     * 查询测试项目管理列表
     * 
     * @param project 测试项目管理信息
     * @return 测试项目管理集合
     */
	@Override
	public List<Project> selectProjectList(Project project)
	{
	    return projectMapper.selectProjectList(project);
	}
	
    /**
     * 新增测试项目管理
     * 
     * @param project 测试项目管理信息
     * @return 结果
     */
	@Override
	public int insertProject(Project project)
	{
	    return projectMapper.insertProject(project);
	}
	
	/**
     * 修改测试项目管理
     * 
     * @param project 测试项目管理信息
     * @return 结果
     */
	@Override
	public int updateProject(Project project)
	{
	    return projectMapper.updateProject(project);
	}

	/**
     * 删除测试项目管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProjectByIds(String ids)
	{
		return projectMapper.deleteProjectByIds(Convert.toStrArray(ids));
	}
	
}
