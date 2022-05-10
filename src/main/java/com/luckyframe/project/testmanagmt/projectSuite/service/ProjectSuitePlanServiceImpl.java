package com.luckyframe.project.testmanagmt.projectSuite.service;


import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.project.testmanagmt.projectSuite.domain.ProjectSuite;
import com.luckyframe.project.testmanagmt.projectSuite.domain.ProjectSuitePlan;
import com.luckyframe.project.testmanagmt.projectSuite.mapper.ProjectSuiteMapper;
import com.luckyframe.project.testmanagmt.projectSuite.mapper.ProjectSuitePlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 聚合计划 服务层实现
 *
 * @author jerelli
 * @date 2021年1月5日
 */
@Service
public class ProjectSuitePlanServiceImpl implements IprojectSuitePlanService {

    @Autowired
    private ProjectSuitePlanMapper projectSuitePlanMapper;

    @Autowired
    private ProjectSuiteMapper projectSuiteMapper;


    /**
     * 查询测试计划用例信息
     *
     * @param suitePlanId 测试计划用例ID
     * @return 测试计划用例信息
     */
    @Override
    public ProjectSuitePlan selectProjectSuitePlanById(Integer suitePlanId)
    {
        return projectSuitePlanMapper.selectProjectSuitePlanById(suitePlanId);
    }

    /**
     * 查询聚合计划用例列表
     *
     * @param projectSuitePlan 聚合计划用例列表信息
     * @return 聚合计划集合
     */
    @Override
    public List<ProjectSuitePlan> selectProjectSuitePlanList(ProjectSuitePlan projectSuitePlan)
    {
        return projectSuitePlanMapper.selectProjectSuitePlanList(projectSuitePlan);
    }

    /**
     * 新增测试计划用例
     *
     * @param projectSuitePlan 测试计划用例信息
     * @return 结果
     */
    @Override
    public int insertProjectSuitePlan(ProjectSuitePlan projectSuitePlan)
    {
        return projectSuitePlanMapper.insertProjectSuitePlan(projectSuitePlan);
    }

    /**
     * 修改测试计划用例
     *
     * @param projectSuitePlan 测试计划用例信息
     * @return 结果
     */
    @Override
    public int updateProjectSuitePlan(ProjectSuitePlan projectSuitePlan)
    {
        ProjectSuite projectSuite = projectSuiteMapper.selectProjectSuiteById(projectSuitePlan.getSuiteId());
        projectSuite.setUpdateBy(ShiroUtils.getLoginName());
        projectSuite.setUpdateTime(new Date());
        projectSuiteMapper.updateProjectSuite(projectSuite);

        return projectSuitePlanMapper.updateProjectSuitePlan(projectSuitePlan);
    }

    /**
     * 删除测试计划用例对象
     *
     * @param suitePlanIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteProjectSuitePlanByIds(String[] suitePlanIds)
    {
        return projectSuitePlanMapper.deleteProjectSuitePlanByIds(suitePlanIds);
    }

    /**
     * 删除单个测试计划用例对象
     * @param suitePlanId 计划用例ID
     * @author Seagull
     * @date 2019年3月20日
     */
    @Override
    public int deleteProjectSuitePlanById(Integer suitePlanId)
    {
        return projectSuitePlanMapper.deleteProjectSuitePlanById(suitePlanId);
    }

    /**
     * 查询指定测试计划下绑定用例总数
     * @param suiteId 测试计划ID
     * @author Seagull
     * @date 2019年5月13日
     */
    @Override
    public int selectProjectSuitePlanCountBySuiteId(Integer suiteId)
    {
        return projectSuitePlanMapper.selectProjectSuitePlanCountBySuiteId(suiteId);
    }

    /**
     * 查询同一聚合计划下同一个计划的数量
     * @param suiteId 聚合测试计划ID  planId 测试计划ID
     * @author Seagull
     * @date 2022年5月10日
     */
    @Override
    public int selectProjectSuitePlanCountBySuiteIdAndPlanId(Integer suiteId,Integer planId)
    {
        ProjectSuitePlan projectSuitePlan = new ProjectSuitePlan();
        projectSuitePlan.setSuiteId(suiteId);
        projectSuitePlan.setPlanId(planId);
        return projectSuitePlanMapper.selectProjectSuitePlanCountBySuiteIdAndPlanId(projectSuitePlan);
    }

}
