package com.luckyframe.project.system.user.mapper;

import java.util.List;
import com.luckyframe.project.system.user.domain.UserRole;
import org.springframework.stereotype.Component;

/**
 * 用户表 数据层
 * 
 * @author ruoyi
 */
@Component
public interface UserRoleMapper
{
    /**
     * 通过用户ID删除用户和角色关联
     * 
     * @param userId 用户ID
     * @return 结果
     */
    int deleteUserRoleByUserId(Long userId);

    /**
     * 批量删除用户和角色关联
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteUserRole(Long[] ids);

    /**
     * 通过角色ID查询角色使用数量
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    int countUserRoleByRoleId(Long roleId);

    /**
     * 批量新增用户角色信息
     * 
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    int batchUserRole(List<UserRole> userRoleList);
}
