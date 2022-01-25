package com.luckyframe.project.testmanagmt.projectSuite.service;

import com.luckyframe.common.constant.ProjectSuiteConstants;
import com.luckyframe.common.exception.BusinessException;
import com.luckyframe.common.support.Convert;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.security.PermissionUtils;
import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.project.testexecution.taskScheduling.mapper.TaskSchedulingMapper;
import com.luckyframe.project.testmanagmt.projectSuite.domain.ProjectSuite;
import com.luckyframe.project.testmanagmt.projectSuite.mapper.ProjectSuiteMapper;
import com.luckyframe.project.testmanagmt.projectSuite.mapper.ProjectSuitePlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProjectSuiteServiceImpl implements IprojectSuiteService{

    @Autowired
    private ProjectSuiteMapper projectSuiteMapper;

    @Autowired
    private TaskSchedulingMapper taskSchedulingMapper;

    @Autowired
    private ProjectSuitePlanMapper projectSuitePlanMapper;

    @Override
    public List<ProjectSuite> selectProjectSuiteList(ProjectSuite projectSuite)
    {
        return projectSuiteMapper.selectProjectSuiteList(projectSuite);
    }


    @Override
    public List<ProjectSuite> selectProjectSuiteListByProjectId(Integer projectId) {
        ProjectSuite projectSuite = new ProjectSuite();
        projectSuite.setProjectId(projectId);
        return projectSuiteMapper.selectProjectSuiteList(projectSuite);
    }

    @Override
    public int insertProjectSuite(ProjectSuite projectSuite) {
        projectSuite.setCreateBy(ShiroUtils.getLoginName());
        projectSuite.setCreateTime(new Date());
        projectSuite.setUpdateBy(ShiroUtils.getLoginName());
        projectSuite.setUpdateTime(new Date());

        return projectSuiteMapper.insertProjectSuite(projectSuite);
    }

    /**
     * 查询测试计划信息
     *
     * @param suiteId 测试计划ID
     * @return 测试计划信息
     */
    @Override
    public ProjectSuite selectProjectSuiteById(Integer suiteId)
    {
        return projectSuiteMapper.selectProjectSuiteById(suiteId);
    }

    /**
     * 修改测试计划
     *
     * @param projectSuite 测试计划信息
     * @return 结果
     */
    @Override
    public int updateProjectSuite(ProjectSuite projectSuite)
    {
        projectSuite.setUpdateBy(ShiroUtils.getLoginName());
        projectSuite.setUpdateTime(new Date());

        return projectSuiteMapper.updateProjectSuite(projectSuite);
    }

    /**
     * 删除测试计划对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteProjectSuiteByIds(String ids)
    {
        String[] suiteIds= Convert.toStrArray(ids);

        int result=0;
        for(String suiteIdStr:suiteIds){
            int suiteId=Integer.parseInt(suiteIdStr);
            ProjectSuite ProjectSuite = projectSuiteMapper.selectProjectSuiteById(suiteId);

            if(taskSchedulingMapper.selectTaskSchedulingCountBySuiteId(suiteId)>0){
                throw new BusinessException(String.format("【%1$s】已绑定调度任务,不能删除", ProjectSuite.getSuiteName()));
            }
            if(!PermissionUtils.isProjectPermsPassByProjectId(ProjectSuite.getProjectId())){
                throw new BusinessException(String.format("测试计划【%1$s】没有项目删除权限", ProjectSuite.getSuiteName()));
            }

            projectSuitePlanMapper.deleteProjectSuitePlanBySuiteId(suiteId);
            projectSuiteMapper.deleteProjectSuiteById(suiteId);
            result++;
        }

        return result;
    }

    /**
     * 校验测试用例名称是否唯一
     */
    @Override
    public String checkProjectSuiteNameUnique(ProjectSuite projectSuite)
    {
        long suiteId = StringUtils.isNull(projectSuite.getSuiteId()) ? -1L : projectSuite.getSuiteId();
        ProjectSuite info = projectSuiteMapper.checkProjectSuiteNameUnique(projectSuite.getSuiteName());
        if (StringUtils.isNotNull(info) && info.getSuiteId().longValue() != suiteId)
        {
            return ProjectSuiteConstants.PROJECTSUITE_NAME_NOT_UNIQUE;
        }
        return ProjectSuiteConstants.PROJECTSUITE_NAME_UNIQUE;
    }
}
