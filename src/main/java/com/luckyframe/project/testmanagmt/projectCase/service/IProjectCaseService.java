package com.luckyframe.project.testmanagmt.projectCase.service;

import java.util.List;

import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCase;

/**
 * 项目测试用例管理 服务层
 * 
 * @author luckyframe
 * @date 2019-02-26
 */
public interface IProjectCaseService 
{
	/**
     * 查询项目测试用例管理信息
     * 
     * @param caseId 项目测试用例管理ID
     * @return 项目测试用例管理信息
     */
	public ProjectCase selectProjectCaseById(Integer caseId);
	
	/**
	 * 根据用例编号查询实体
	 * @param caseSign
	 * @return
	 * @author Seagull
	 * @date 2019年4月16日
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
	 * 根据项目ID查询用例集合
	 * @param projectId
	 * @return
	 * @author Seagull
	 * @date 2019年3月18日
	 */
	public List<ProjectCase> selectProjectCaseListByProjectId(Integer projectId);
	
	/**
	 * 根据计划ID查询用例列表
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
     * 删除项目测试用例管理信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProjectCaseByIds(String ids);
	
    /**
     * 校验测试用例名称是否唯一
     * @param projectCase
     * @return
     * @author Seagull
     * @date 2019年2月28日
     */
    public String checkProjectCaseNameUnique(ProjectCase projectCase);
    
	/**
	 * 查询总用例数
	 * @return
	 * @author Seagull
	 * @date 2019年4月28日
	 */
	public int selectProjectCaseCount();
	
	/**
	 * 查询30天内更新的用例数
	 * @return
	 * @author Seagull
	 * @date 2019年4月28日
	 */
	public int selectProjectCaseCountForThirtyDays();
}
