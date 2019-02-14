package com.luckyframe.project.system.roleProject.service;

import com.luckyframe.project.system.roleProject.domain.RoleProject;
import java.util.List;

/**
 * 角色和项目关联 服务层
 * 
 * @author luckyframe
 * @date 2019-02-13
 */
public interface IRoleProjectService 
{
	/**
     * 查询角色和项目关联信息
     * 
     * @param roleId 角色和项目关联ID
     * @return 角色和项目关联信息
     */
	public RoleProject selectRoleProjectById(Integer roleId);
	
	/**
     * 查询角色和项目关联列表
     * 
     * @param roleProject 角色和项目关联信息
     * @return 角色和项目关联集合
     */
	public List<RoleProject> selectRoleProjectList(RoleProject roleProject);
	
	/**
     * 新增角色和项目关联
     * 
     * @param roleProject 角色和项目关联信息
     * @return 结果
     */
	public int insertRoleProject(RoleProject roleProject);
	
	/**
     * 修改角色和项目关联
     * 
     * @param roleProject 角色和项目关联信息
     * @return 结果
     */
	public int updateRoleProject(RoleProject roleProject);
		
	/**
     * 删除角色和项目关联信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteRoleProjectByIds(String ids);
	
}
