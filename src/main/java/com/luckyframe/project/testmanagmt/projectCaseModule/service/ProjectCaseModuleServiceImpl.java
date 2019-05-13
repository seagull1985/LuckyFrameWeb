package com.luckyframe.project.testmanagmt.projectCaseModule.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyframe.common.exception.BusinessException;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.security.PermissionUtils;
import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.system.project.mapper.ProjectMapper;
import com.luckyframe.project.testmanagmt.projectCase.mapper.ProjectCaseMapper;
import com.luckyframe.project.testmanagmt.projectCaseModule.domain.ProjectCaseModule;
import com.luckyframe.project.testmanagmt.projectCaseModule.mapper.ProjectCaseModuleMapper;

/**
 * 测试用例模块管理 服务层实现
 * 
 * @author luckyframe
 * @date 2019-02-26
 */
@Service
public class ProjectCaseModuleServiceImpl implements IProjectCaseModuleService 
{
	@Autowired
	private ProjectCaseModuleMapper projectCaseModuleMapper;
	
	@Autowired
	private ProjectCaseMapper projectCaseMapper;
	
	@Autowired
	private ProjectMapper projectMapper;

	/**
     * 查询测试用例模块管理信息
     * 
     * @param moduleId 测试用例模块管理ID
     * @return 测试用例模块管理信息
     */
    @Override
	public ProjectCaseModule selectProjectCaseModuleById(Integer moduleId)
	{
	    return projectCaseModuleMapper.selectProjectCaseModuleById(moduleId);
	}
    
    /**
     * 通过项目ID获取用例模块的项目根节点实体
     */
    public ProjectCaseModule selectProjectCaseModuleParentZeroByProjectId(Integer projectId){
    	return projectCaseModuleMapper.selectProjectCaseModuleParentZeroByProjectId(projectId);
    }
	
	/**
	 * 通过父级ID查询子列表
	 * @param parentId
	 * @return
	 * @author Seagull
	 * @date 2019年2月26日
	 */
    @Override
	public List<ProjectCaseModule> selectProjectCaseModuleByParentId(Integer parentId)
	{
	    return projectCaseModuleMapper.selectProjectCaseModuleByParentId(parentId);
	}
    
	/**
     * 查询测试用例模块管理列表
     * 
     * @param projectCaseModule 测试用例模块管理信息
     * @return 测试用例模块管理集合
     */
	@Override
	public List<ProjectCaseModule> selectProjectCaseModuleList(ProjectCaseModule projectCaseModule)
	{
		List<ProjectCaseModule> pcmList=projectCaseModuleMapper.selectProjectCaseModuleList(projectCaseModule);
		List<Project> projectList = projectMapper.selectProjectList(new Project());
		for(ProjectCaseModule pcm:pcmList){
			pcm.setProjectName("未知项目ID:"+pcm.getProjectId());
			for(Project project:projectList){
				if(pcm.getProjectId().equals(project.getProjectId())){
					pcm.setProjectName(project.getProjectName());
					break;
				}				
			}
		}
	    return pcmList;
	}
	
    /**
     * 新增测试用例模块管理
     * 
     * @param projectCaseModule 测试用例模块管理信息
     * @return 结果
     */
	@Override
	public int insertProjectCaseModule(ProjectCaseModule projectCaseModule)
	{
		ProjectCaseModule parentProjectCaseModule=selectProjectCaseModuleById(projectCaseModule.getParentId());
		projectCaseModule.setProjectId(parentProjectCaseModule.getProjectId());
		projectCaseModule.setCreateBy(ShiroUtils.getLoginName());
		projectCaseModule.setCreateTime(new Date());
        projectCaseModule.setUpdateBy(ShiroUtils.getLoginName());
        projectCaseModule.setUpdateTime(new Date());
		projectCaseModule.setAncestors(parentProjectCaseModule.getAncestors() + "," + projectCaseModule.getParentId());
	    return projectCaseModuleMapper.insertProjectCaseModule(projectCaseModule);
	}
	
	/**
     * 修改测试用例模块管理
     * 
     * @param projectCaseModule 测试用例模块管理信息
     * @return 结果
     */
	@Override
	public int updateProjectCaseModule(ProjectCaseModule projectCaseModule)
	{
		ProjectCaseModule info = projectCaseModuleMapper.selectProjectCaseModuleById(projectCaseModule.getParentId());
        if (StringUtils.isNotNull(info))
        {
            String ancestors = info.getAncestors() + "," + info.getModuleId();
            projectCaseModule.setAncestors(ancestors);
            projectCaseModule.setProjectId(info.getProjectId());
            updateModuleChildren(projectCaseModule, ancestors);
        }
        projectCaseModule.setUpdateBy(ShiroUtils.getLoginName());
        projectCaseModule.setUpdateTime(new Date());
        return projectCaseModuleMapper.updateProjectCaseModule(projectCaseModule);
	}
	
    /**
     * 修改子模块关系
     * @param parentId
     * @param ancestors
     * @author Seagull
     * @date 2019年2月27日
     */
    private void updateModuleChildren(ProjectCaseModule parentProjectCaseModule, String ancestors)
    {
        List<ProjectCaseModule> childrens = projectCaseModuleMapper.selectProjectCaseModuleByParentId(parentProjectCaseModule.getModuleId());
        for (ProjectCaseModule children : childrens)
        {
            children.setAncestors(ancestors + "," + parentProjectCaseModule.getModuleId());
            children.setUpdateBy(ShiroUtils.getLoginName());
            children.setUpdateTime(new Date());
        }
        if (childrens.size() > 0)
        {
        	projectCaseModuleMapper.updateModuleChildren(childrens);
        }
    }

	/**
     * 删除测试用例模块管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProjectCaseModuleById(Integer moduleId) throws BusinessException
	{
		if(projectCaseMapper.selectProjectCaseCountByModuleId(moduleId)>0){
			throw new BusinessException(String.format("【%1$s】已绑定测试用例,不能删除", projectCaseModuleMapper.selectProjectCaseModuleById(moduleId).getModuleName()));
		}
		
		if(!PermissionUtils.isProjectPermsPassByProjectId(projectCaseModuleMapper.selectProjectCaseModuleById(moduleId).getProjectId())){	
			  throw new BusinessException("没有删除用例模块的项目权限");
		}
		
		return projectCaseModuleMapper.deleteProjectCaseModuleById(moduleId);
	}
	
    /**
     * 查询用例模块管理树
     */
    @Override
    public List<Map<String, Object>> selectProjectCaseModuleTree(ProjectCaseModule projectCaseModule)
    {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        List<ProjectCaseModule> pcmList = projectCaseModuleMapper.selectProjectCaseModuleList(projectCaseModule);
        trees = getTrees(pcmList);
        return trees;
    }
    
    /**
     * 对象转模块树
     * @param pcmList
     * @return
     * @author Seagull
     * @date 2019年2月26日
     */
    private List<Map<String, Object>> getTrees(List<ProjectCaseModule> pcmList)
    {

        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        for (ProjectCaseModule pcm : pcmList)
        {
                Map<String, Object> pcmMap = new HashMap<String, Object>();
                pcmMap.put("id", pcm.getModuleId());
                pcmMap.put("pId", pcm.getParentId());
                pcmMap.put("name", pcm.getModuleName());
                pcmMap.put("title", pcm.getModuleName());
                pcmMap.put("checked", false);
                trees.add(pcmMap);
        }
        return trees;
    }
    
	/**
	 * 获取当前父节点下排序号
	 */
	@Override
	public int selectProjectCaseModuleMaxOrderNumByParentId(Integer parentModuleId)
	{
		return projectCaseModuleMapper.selectProjectCaseModuleMaxOrderNumByParentId(parentModuleId)+1;
	}
}
