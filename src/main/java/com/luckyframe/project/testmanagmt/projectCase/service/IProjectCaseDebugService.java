package com.luckyframe.project.testmanagmt.projectCase.service;

import java.util.List;

import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseDebug;

/**
 * 用例调试日志记录 服务层
 * 
 * @author luckyframe
 * @date 2019-03-14
 */
public interface IProjectCaseDebugService 
{
	/**
     * 查询用例调试日志记录信息
     * 
     * @param debugId 用例调试日志记录ID
     * @return 用例调试日志记录信息
     */
	public ProjectCaseDebug selectProjectCaseDebugById(Integer debugId);
	
	/**
     * 查询用例调试日志记录列表
     * 
     * @param projectCaseDebug 用例调试日志记录信息
     * @return 用例调试日志记录集合
     */
	public List<ProjectCaseDebug> selectProjectCaseDebugList(ProjectCaseDebug projectCaseDebug);
	
	/**
     * 新增用例调试日志记录
     * 
     * @param projectCaseDebug 用例调试日志记录信息
     * @return 结果
     */
	public int insertProjectCaseDebug(ProjectCaseDebug projectCaseDebug);
	
	/**
     * 修改用例调试日志记录
     * 
     * @param projectCaseDebug 用例调试日志记录信息
     * @return 结果
     */
	public int updateProjectCaseDebug(ProjectCaseDebug projectCaseDebug);
		
	/**
     * 删除用例调试日志记录信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProjectCaseDebugByIds(String ids);
	
	/**
	 * 根据id,useid,caseid删除调试日志
	 * @param projectCaseDebug
	 * @return
	 * @author Seagull
	 * @date 2019年3月15日
	 */
	public int deleteProjectCaseDebugById(ProjectCaseDebug projectCaseDebug);
	
}
