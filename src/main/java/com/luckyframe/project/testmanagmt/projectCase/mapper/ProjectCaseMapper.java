package com.luckyframe.project.testmanagmt.projectCase.mapper;

import java.util.List;

import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCase;	

/**
 * 项目测试用例管理 数据层
 * 
 * @author luckyframe
 * @date 2019-02-26
 */
public interface ProjectCaseMapper 
{
	/**
     * 查询项目测试用例管理信息
     * 
     * @param caseId 项目测试用例管理ID
     * @return 项目测试用例管理信息
     */
	public ProjectCase selectProjectCaseById(Integer caseId);
	
	/**
     * 通过用例编号查询项目测试用例管理信息
     * 
     * @param caseId 项目测试用例管理ID
     * @return 项目测试用例管理信息
     */
	public ProjectCase selectProjectCaseByCaseSign(String caseSign);
	
	/**
     * 查询项目测试用例管理列表
     * 
     * @param projectCase 项目测试用例管理信息
     * @return 项目测试用例管理集合
     */
	public List<ProjectCase> selectProjectCaseList(ProjectCase projectCase);
	
	/**
	 * 查询计划中的用例列表
	 * @param projectCase
	 * @return
	 * @author Seagull
	 * @date 2019年4月10日
	 */
	public List<ProjectCase> selectProjectCaseListForPlan(ProjectCase projectCase);
	
	/**
     * 新增项目测试用例管理
     * 
     * @param projectCase 项目测试用例管理信息
     * @return 结果
     */
	public int insertProjectCase(ProjectCase projectCase);
	
	/**
     * 修改项目测试用例管理
     * 
     * @param projectCase 项目测试用例管理信息
     * @return 结果
     */
	public int updateProjectCase(ProjectCase projectCase);
	
	/**
     * 删除项目测试用例管理
     * 
     * @param caseId 项目测试用例管理ID
     * @return 结果
     */
	public int deleteProjectCaseById(Integer caseId);
	
	/**
     * 批量删除项目测试用例管理
     * 
     * @param caseIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteProjectCaseByIds(String[] caseIds);
	
	/**
	 * 检查测试用例名称唯一性
	 * @param caseName
	 * @return
	 * @author Seagull
	 * @date 2019年2月28日
	 */
	public ProjectCase checkProjectCaseNameUnique(String caseName);
	
	/**
	 * 查询测试用例项目序号
	 * @param projectId
	 * @return
	 * @author Seagull
	 * @date 2019年3月1日
	 */
	public int selectMaxCaseSerialNumberByProjectId(Integer projectId);
	
    /**
     * 查询项目下有没有测试用例
     * @param projectId
     * @return
     * @author Seagull
     * @date 2019年3月4日
     */
    public int selectProjectCaseCountByProjectId(Integer projectId);
    
    /**
     * 查询用例模块下有没有测试用例
     * @param projectId
     * @return
     * @author Seagull
     * @date 2019年3月4日
     */
    public int selectProjectCaseCountByModuleId(Integer moduleId);
    
    /**
     * 查询总用例数
     * @return
     * @author Seagull
     * @date 2019年4月28日
     */
    public int selectProjectCaseCount();
    
    /**
     * 查询指定天数内更新的用例
     * @param projectCase
     * @return
     * @author Seagull
     * @date 2019年4月28日
     */
    public int selectProjectCaseCountForThirtyDays(String updateTime);
}