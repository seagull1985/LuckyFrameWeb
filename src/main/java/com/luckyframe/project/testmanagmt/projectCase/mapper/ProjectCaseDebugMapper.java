package com.luckyframe.project.testmanagmt.projectCase.mapper;

import java.util.List;

import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseDebug;
import org.springframework.stereotype.Component;

/**
 * 用例调试日志记录 数据层
 * 
 * @author luckyframe
 * @date 2019-03-14
 */
@Component
public interface ProjectCaseDebugMapper 
{
	/**
     * 查询用例调试日志记录信息
     * 
     * @param debugId 用例调试日志记录ID
     * @return 用例调试日志记录信息
     */
	ProjectCaseDebug selectProjectCaseDebugById(Integer debugId);
	
	/**
     * 查询用例调试日志记录列表
     * 
     * @param projectCaseDebug 用例调试日志记录信息
     * @return 用例调试日志记录集合
     */
	List<ProjectCaseDebug> selectProjectCaseDebugList(ProjectCaseDebug projectCaseDebug);
	
	/**
     * 新增用例调试日志记录
     * 
     * @param projectCaseDebug 用例调试日志记录信息
     * @return 结果
     */
	int insertProjectCaseDebug(ProjectCaseDebug projectCaseDebug);
	
	/**
     * 修改用例调试日志记录
     * 
     * @param projectCaseDebug 用例调试日志记录信息
     * @return 结果
     */
	int updateProjectCaseDebug(ProjectCaseDebug projectCaseDebug);
	
	/**
     * 删除用例调试日志记录
     * 
     * @param projectCaseDebug 取用例调试日志对象中的记录ID
     * @return 结果
     */
	int deleteProjectCaseDebugById(ProjectCaseDebug projectCaseDebug);
	
	/**
     * 批量删除用例调试日志记录
     * 
     * @param debugIds 需要删除的数据ID
     * @return 结果
     */
	int deleteProjectCaseDebugByIds(String[] debugIds);
	
}