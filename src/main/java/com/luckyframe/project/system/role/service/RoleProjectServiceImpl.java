package com.luckyframe.project.system.role.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyframe.common.support.Convert;
import com.luckyframe.project.system.role.domain.RoleProject;
import com.luckyframe.project.system.role.mapper.RoleProjectMapper;

/**
 * 角色和项目关联 服务层实现
 * 
 * @author luckyframe
 * @date 2019-02-13
 */
@Service
public class RoleProjectServiceImpl implements IRoleProjectService 
{
	@Autowired
	private RoleProjectMapper roleProjectMapper;

	/**
     * 查询角色和项目关联信息
     * 
     * @param roleId 角色和项目关联ID
     * @return 角色和项目关联信息
     */
    @Override
	public List<RoleProject> selectRoleProjectById(Integer roleId)
	{
	    return roleProjectMapper.selectRoleProjectsById(roleId);
	}
	
	/**
     * 查询角色和项目关联列表
     * 
     * @param roleProject 角色和项目关联信息
     * @return 角色和项目关联集合
     */
	@Override
	public List<RoleProject> selectRoleProjectList(RoleProject roleProject)
	{
	    return roleProjectMapper.selectRoleProjectList(roleProject);
	}
	
	/**
	 * 查询用户项目权限
	 * @param userId
	 * @return
	 * @author Seagull
	 * @date 2019年4月11日
	 */
    @Override
	public List<Integer> selectProjectPermsByUserId(Long userId)
	{    	
	    return roleProjectMapper.selectProjectPermsByUserId(userId);
	}
    
    /**
     * 新增角色和项目关联
     * 
     * @param roleProject 角色和项目关联信息
     * @return 结果
     */
	@Override
	public int insertRoleProject(RoleProject roleProject)
	{
	    return roleProjectMapper.insertRoleProject(roleProject);
	}
	
	/**
     * 修改角色和项目关联
     * 
     * @param roleProject 角色和项目关联信息
     * @return 结果
     */
	@Override
	public int updateRoleProject(RoleProject roleProject)
	{
	    return roleProjectMapper.updateRoleProject(roleProject);
	}

	/**
     * 删除角色和项目关联对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteRoleProjectByIds(String ids)
	{
		return roleProjectMapper.deleteRoleProjectByIds(Convert.toStrArray(ids));
	}
	
}
