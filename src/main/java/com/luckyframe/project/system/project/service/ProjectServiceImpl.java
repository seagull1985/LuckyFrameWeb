package com.luckyframe.project.system.project.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyframe.common.constant.ProjectConstants;
import com.luckyframe.common.exception.BusinessException;
import com.luckyframe.common.support.Convert;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.security.PermissionUtils;
import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.project.system.client.domain.ClientProject;
import com.luckyframe.project.system.client.mapper.ClientProjectMapper;
import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.system.project.mapper.ProjectMapper;
import com.luckyframe.project.system.role.domain.RoleProject;
import com.luckyframe.project.system.role.mapper.RoleProjectMapper;
import com.luckyframe.project.testexecution.taskCaseExecute.mapper.TaskCaseExecuteMapper;
import com.luckyframe.project.testexecution.taskExecute.mapper.TaskExecuteMapper;
import com.luckyframe.project.testexecution.taskScheduling.mapper.TaskSchedulingMapper;
import com.luckyframe.project.testmanagmt.projectCase.mapper.ProjectCaseMapper;
import com.luckyframe.project.testmanagmt.projectCaseModule.domain.ProjectCaseModule;
import com.luckyframe.project.testmanagmt.projectCaseModule.mapper.ProjectCaseModuleMapper;

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
	
	@Autowired
	private ProjectCaseModuleMapper projectCaseModuleMapper;
	
	@Autowired
	private ProjectCaseMapper projectCaseMapper;
	
	@Autowired
	private ClientProjectMapper clientProjectMapper;
	
	@Autowired
	private RoleProjectMapper roleProjectMapper;
	
	@Autowired
	private TaskSchedulingMapper taskSchedulingMapper;
	
	@Autowired
	private TaskExecuteMapper taskExecuteMapper;
	
	@Autowired
	private TaskCaseExecuteMapper taskCaseExecuteMapper;

	/**
     * 查询测试项目管理信息
     * 
     * @param projectId 测试项目管理ID
     * @return 测试项目管理信息
     */
    @Override
	public Project selectProjectById(int projectId)
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
     * 查询所有项目管理列表
     */
    @Override
    public List<Project> selectProjectAll(Integer projectId)
    {
    	List<Project> projectList= selectProjectList(new Project());
    	if(projectId!=0){
    		for(Project p:projectList){
    			if(p.getProjectId()==projectId){
    				p.setFlag(true);
    			}   			
    		}   		
    	}
        return projectList;
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
		projectMapper.insertProject(project);
		ProjectCaseModule projectCaseModule=new ProjectCaseModule();
		projectCaseModule.setModuleName(project.getProjectName());
		projectCaseModule.setProjectId(project.getProjectId());
		projectCaseModule.setCreateBy(ShiroUtils.getLoginName());
		projectCaseModule.setCreateTime(new Date());
        projectCaseModule.setUpdateBy(ShiroUtils.getLoginName());
        projectCaseModule.setUpdateTime(new Date());
		projectCaseModule.setAncestors("0");
		projectCaseModule.setOrderNum(projectCaseModuleMapper.selectProjectCaseModuleMaxOrderNumByParentId(0)+1);
		projectCaseModule.setRemark("项目初始化模块");
	    projectCaseModuleMapper.insertProjectCaseModule(projectCaseModule);
	    return project.getProjectId();
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
	public int deleteProjectByIds(String ids) throws BusinessException
	{
		String[] projectIds=Convert.toStrArray(ids);
		for(String projectIdStr:projectIds){
			int projectId=Integer.valueOf(projectIdStr);
			if(projectCaseModuleMapper.selectProjectCaseModuleCountByProjectId(projectId)>0){
				throw new BusinessException(String.format("【%1$s】已绑定子用例模块,不能删除", projectMapper.selectProjectById(projectId).getProjectName()));
			}
			if(clientProjectMapper.selectClientProjectCountByProjectId(projectId)>0){
				throw new BusinessException(String.format("【%1$s】已绑定客户端,不能删除", projectMapper.selectProjectById(projectId).getProjectName()));
			}
			if(projectCaseMapper.selectProjectCaseCountByProjectId(projectId)>0){
				throw new BusinessException(String.format("【%1$s】已绑定测试用例,不能删除", projectMapper.selectProjectById(projectId).getProjectName()));
			}
			if(taskSchedulingMapper.selectTaskSchedulingCountByProjectId(projectId)>0){
				throw new BusinessException(String.format("【%1$s】已绑定调度,不能删除", projectMapper.selectProjectById(projectId).getProjectName()));
			}
			if(taskExecuteMapper.selectTaskExecuteCountByProjectId(projectId)>0){
				throw new BusinessException(String.format("【%1$s】已生成执行任务,不能删除", projectMapper.selectProjectById(projectId).getProjectName()));
			}
			if(taskCaseExecuteMapper.selectTaskCaseExecuteCountByProjectId(projectId)>0){
				throw new BusinessException(String.format("【%1$s】已生成执行用例明细,不能删除", projectMapper.selectProjectById(projectId).getProjectName()));
			}
			if(!PermissionUtils.isProjectPermsPassByProjectId(projectId)){	
				  throw new BusinessException(String.format("没有项目【%1$s】删除权限", projectMapper.selectProjectById(projectId).getProjectName()));
			}		
		}
		projectCaseModuleMapper.deleteProjectCaseModuleByProjectIds(projectIds);
		return projectMapper.deleteProjectByIds(projectIds);
	}
	
    /**
     * 根据客户端ID查询所有项目列表(打标记)
     * @param userId
     * @return
     * @author Seagull
     * @date 2019年2月25日
     */
    @Override
    public List<Project> selectProjectsByClientId(int clientId)
    {
        List<ClientProject> clientProjects = clientProjectMapper.selectClientProjectsById(clientId);
        List<Project> projects = selectProjectAll(0);
        for (Project project : projects)
        {
            for (ClientProject cp : clientProjects)
            {
                if (cp.getProjectId() == project.getProjectId().longValue())
                {
                	project.setFlag(true);
                    break;
                }
            }
        }
        return projects;
    }
    
    /**
     * 根据角色ID查询所有项目列表(打标记)
     * @param roleId
     * @return
     * @author Seagull
     * @date 2019年2月25日
     */
    @Override
    public List<Project> selectProjectsByRoleId(int roleId)
    {
        List<RoleProject> roleProjects = roleProjectMapper.selectRoleProjectsById(roleId);
        List<Project> projects = selectProjectAll(0);
        for (Project project : projects)
        {
            for (RoleProject rp : roleProjects)
            {
                if (rp.getProjectId() == project.getProjectId().longValue())
                {
                	project.setFlag(true);
                    break;
                }
            }
        }
        return projects;
    }
    
    /**
     * 校验项目名称唯一性
     */
    @Override
    public String checkProjectNameUnique(Project project)
    {
        Long projectId = StringUtils.isNull(project.getProjectId()) ? -1L : project.getProjectId();
        Project info = projectMapper.checkProjectNameUnique(project.getProjectName());
        if (StringUtils.isNotNull(info) && info.getProjectId().longValue() != projectId.longValue())
        {
            return ProjectConstants.PROJECT_NAME_NOT_UNIQUE;
        }
        return ProjectConstants.PROJECT_NAME_UNIQUE;
    }
    
    /**
     * 校验项目标识唯一性
     */
    @Override
    public String checkProjectSignUnique(Project project)
    {
        Long projectId = StringUtils.isNull(project.getProjectId()) ? -1L : project.getProjectId();
        Project info = projectMapper.checkProjectSignUnique(project.getProjectSign());
        if (StringUtils.isNotNull(info) && info.getProjectId().longValue() != projectId.longValue())
        {
            return ProjectConstants.PROJECT_SIGN_NOT_UNIQUE;
        }
        return ProjectConstants.PROJECT_SIGN_UNIQUE;
    }
}
