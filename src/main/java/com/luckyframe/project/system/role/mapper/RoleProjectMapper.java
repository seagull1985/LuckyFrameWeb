package com.luckyframe.project.system.role.mapper;

import java.util.List;

import com.luckyframe.project.system.role.domain.RoleProject;
import org.springframework.stereotype.Component;

/**
 * 角色和项目关联 数据层
 * 
 * @author luckyframe
 * @date 2019-02-13
 */
@Component
public interface RoleProjectMapper 
{
	/**
     * 查询角色和项目关联信息
     * 
     * @param roleId 角色和项目关联ID
     * @return 角色和项目关联信息
     */
	List<RoleProject> selectRoleProjectsById(Integer roleId);
	
	/**
     * 查询角色和项目关联列表
     * 
     * @param roleProject 角色和项目关联信息
     * @return 角色和项目关联集合
     */
	List<RoleProject> selectRoleProjectList(RoleProject roleProject);
	
    /**
     * 根据用户ID查询项目权限
     * @param userId 用户ID
     * @return 项目ID集合
     * @author Seagull
     * @date 2019年4月11日
     */
    List<Integer> selectProjectPermsByUserId(Long userId);
	
	/**
     * 新增角色和项目关联
     * 
     * @param roleProject 角色和项目关联信息
     * @return 结果
     */
	int insertRoleProject(RoleProject roleProject);
	
	/**
     * 修改角色和项目关联
     * 
     * @param roleProject 角色和项目关联信息
     * @return 结果
     */
	int updateRoleProject(RoleProject roleProject);
	
	/**
     * 删除角色和项目关联
     * 
     * @param roleId 角色和项目关联ID
     * @return 结果
     */
	int deleteRoleProjectById(Integer roleId);
	
	/**
     * 批量删除角色和项目关联
     * 
     * @param roleIds 需要删除的数据ID
     * @return 结果
     */
	int deleteRoleProjectByIds(String[] roleIds);
	
    /**
     * 批量新增角色与项目信息
     * @param roleProjectList 项目角色权限集合
     * @return 新增结果
     * @author Seagull
     * @date 2019年2月25日
     */
    int insertBatchRoleProject(List<RoleProject> roleProjectList);
}