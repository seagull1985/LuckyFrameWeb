package com.luckyframe.project.testmanagmt.projectCase.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyframe.common.constant.ProjectCaseConstants;
import com.luckyframe.common.support.Convert;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.project.system.project.mapper.ProjectMapper;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCase;
import com.luckyframe.project.testmanagmt.projectCase.mapper.ProjectCaseMapper;

/**
 * 项目测试用例管理 服务层实现
 * 
 * @author luckyframe
 * @date 2019-02-26
 */
@Service
public class ProjectCaseServiceImpl implements IProjectCaseService 
{
	@Autowired
	private ProjectCaseMapper projectCaseMapper;
	
	@Autowired
	private ProjectMapper projectMapper;

	/**
     * 查询项目测试用例管理信息
     * 
     * @param caseId 项目测试用例管理ID
     * @return 项目测试用例管理信息
     */
    @Override
	public ProjectCase selectProjectCaseById(Integer caseId)
	{
	    return projectCaseMapper.selectProjectCaseById(caseId);
	}
	
	/**
     * 查询项目测试用例管理列表
     * 
     * @param projectCase 项目测试用例管理信息
     * @return 项目测试用例管理集合
     */
	@Override
	public List<ProjectCase> selectProjectCaseList(ProjectCase projectCase)
	{
	    return projectCaseMapper.selectProjectCaseList(projectCase);
	}
	
    /**
     * 新增项目测试用例管理
     * 
     * @param projectCase 项目测试用例管理信息
     * @return 结果
     */
	@Override
	public int insertProjectCase(ProjectCase projectCase)
	{
		projectCase.setCreateBy(ShiroUtils.getLoginName());
		projectCase.setCreateTime(new Date());
		projectCase.setUpdateBy(ShiroUtils.getLoginName());
		projectCase.setUpdateTime(new Date());
		
		int caseSerialNumber=projectCaseMapper.selectMaxCaseSerialNumberByProjectId(projectCase.getProjectId())+1;
		projectCase.setCaseSerialNumber(caseSerialNumber);
		
		String caseSign=projectMapper.selectProjectById(projectCase.getProjectId()).getProjectSign()+"-"+caseSerialNumber;
		projectCase.setCaseSign(caseSign);
		
	    return projectCaseMapper.insertProjectCase(projectCase);
	}
	
	/**
     * 修改项目测试用例管理
     * 
     * @param projectCase 项目测试用例管理信息
     * @return 结果
     */
	@Override
	public int updateProjectCase(ProjectCase projectCase)
	{
		projectCase.setUpdateBy(ShiroUtils.getLoginName());
		projectCase.setUpdateTime(new Date());
	    return projectCaseMapper.updateProjectCase(projectCase);
	}

	/**
     * 删除项目测试用例管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProjectCaseByIds(String ids)
	{
		return projectCaseMapper.deleteProjectCaseByIds(Convert.toStrArray(ids));
	}
	
    /**
     * 校验测试用例名称是否唯一
     */
    @Override
    public String checkProjectCaseNameUnique(ProjectCase projectCase)
    {
        Long caseId = StringUtils.isNull(projectCase.getCaseId()) ? -1L : projectCase.getCaseId();
        ProjectCase info = projectCaseMapper.checkProjectCaseNameUnique(projectCase.getCaseName());
        if (StringUtils.isNotNull(info) && info.getCaseId().longValue() != caseId.longValue())
        {
            return ProjectCaseConstants.PROJECTCASE_NAME_NOT_UNIQUE;
        }
        return ProjectCaseConstants.PROJECTCASE_NAME_UNIQUE;
    }
}
