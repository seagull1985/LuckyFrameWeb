package com.luckyframe.project.testmanagmt.projectPageDetail.service;

import com.luckyframe.common.exception.BusinessException;
import com.luckyframe.common.support.Convert;
import com.luckyframe.common.utils.security.PermissionUtils;
import com.luckyframe.project.common.POUtil;
import com.luckyframe.project.testexecution.taskExecute.domain.TaskExecute;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseSteps;
import com.luckyframe.project.testmanagmt.projectCase.mapper.ProjectCaseStepsMapper;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseService;
import com.luckyframe.project.testmanagmt.projectPageDetail.domain.ProjectPageDetail;
import com.luckyframe.project.testmanagmt.projectPageDetail.mapper.ProjectPageDetailMapper;
import com.luckyframe.project.testmanagmt.projectPageObject.domain.ProjectPageObject;
import com.luckyframe.project.testmanagmt.projectPageObject.mapper.ProjectPageObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 页面详情 服务层实现
 * 
 * @author YSS陈再兴
 * @date 2022-03-10
 */
@Service
public class ProjectPageDetailServiceImpl implements IProjectPageDetailService 
{
	@Autowired(required = false)
	private ProjectPageDetailMapper projectPageDetailMapper;

	@Autowired
	private ProjectCaseStepsMapper projectCaseStepsMapper;

	@Autowired
	private IProjectCaseService projectCaseService;

	@Autowired(required = false)
	private ProjectPageObjectMapper projectPageObjectMapper;

	/**
     * 查询页面详情信息
     * 
     * @param id 页面详情ID 主键ID
     * @return 页面详情信息 基础信息
     */
    @Override
	public ProjectPageDetail selectProjectPageDetailById(Integer id)
	{
	    return projectPageDetailMapper.selectProjectPageDetailById(id);
	}
	
	/**
     * 查询页面详情列表
     * 
     * @param projectPageDetail 页面详情信息 基础信息
     * @return 页面详情集合
     */
	@Override
	public List<ProjectPageDetail> selectProjectPageDetailList(ProjectPageDetail projectPageDetail)
	{
	    return projectPageDetailMapper.selectProjectPageDetailList(projectPageDetail);
	}
	
    /**
     * 新增页面详情
     * 
     * @param projectPageDetail 页面详情信息 基础信息
     * @return 结果
     */
	@Override
	public int insertProjectPageDetail(ProjectPageDetail projectPageDetail)
	{
	    return projectPageDetailMapper.insertProjectPageDetail(projectPageDetail);
	}
	
	/**
     * 修改页面详情
     * 
     * @param projectPageDetail 页面详情信息 基础信息
     * @return 结果
     */
	@Override
	public int updateProjectPageDetail(ProjectPageDetail projectPageDetail)
	{
	    return projectPageDetailMapper.updateProjectPageDetail(projectPageDetail);
	}

	/**
     * 删除页面详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProjectPageDetailByIds(String ids)
	{
		String[] pdIds=Convert.toStrArray(ids);
		int result=0;
		ProjectPageObject projectPageObject = new ProjectPageObject();
		ProjectCaseSteps projectCaseSteps = new ProjectCaseSteps();

		for(String pdIdstr:pdIds){
			Integer pdId=Integer.valueOf(pdIdstr);
			ProjectPageDetail projectPageDetail = projectPageDetailMapper.selectProjectPageDetailById(pdId);

			if(null==projectPageDetail){
				result=1;
				continue;
			}

			if(null==projectPageObject.getProjectId() || 0==projectPageObject.getProjectId()){
				projectPageObject = projectPageObjectMapper.selectProjectPageObjectById(projectPageDetail.getPageId());
			}

			projectCaseSteps.setProjectId(projectPageObject.getProjectId());
			projectCaseSteps.setStepType(1);
			List<ProjectCaseSteps> pcsList = projectCaseStepsMapper.selectProjectCaseStepsList(projectCaseSteps);

			if(pcsList.size()>0){
				for(ProjectCaseSteps pcs:pcsList){
					if(POUtil.isPO(pcs)){
						Integer elementId = Integer.parseInt(pcs.getStepPath().split("\\.")[1]);
						if(pdId.equals(elementId)){
							String caseSign = projectCaseService.selectProjectCaseById(pcs.getCaseId()).getCaseSign();
							throw new BusinessException(String.format("当前元素【%1$s】有关联用例步骤【%2$s】使用，请先删除或修改用例步骤！",
									projectPageDetail.getElement(),caseSign));
						}
					}

				}
			}

			projectPageDetailMapper.deleteProjectPageDetailById(pdId);
			result++;
		}

		return result;
	}
	
}
