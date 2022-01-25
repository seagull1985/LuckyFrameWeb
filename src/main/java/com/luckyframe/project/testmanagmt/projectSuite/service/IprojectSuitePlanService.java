package com.luckyframe.project.testmanagmt.projectSuite.service;

import com.luckyframe.project.testmanagmt.projectSuite.domain.ProjectSuitePlan;

/**
 * 聚合计划 服务层
 *
 * @author jerelli
 * @date 2021年1月5日
 */

public interface IprojectSuitePlanService {

    /**
     * 查询聚合计划用例信息
     *
     * @param suitePlanId 聚合计划用例ID
     * @return 聚合计划信息
     */
    ProjectSuitePlan selectProjectSuitePlanById(Integer suitePlanId);


    /**
     * 新增聚合计划
     *
     * @param projectSuitePlan 测试聚合计划信息
     * @return 结果
     */
    int insertProjectSuitePlan(ProjectSuitePlan projectSuitePlan);

    /**
     * 修改聚合计划用例
     *
     * @param projectSuitePlan 聚合计划用例信息
     * @return 结果
     */
    int updateProjectSuitePlan(ProjectSuitePlan projectSuitePlan);

    /**
     * 删除聚合计划用例
     *
     * @param suitePlanId 聚合计划ID
     * @return 结果
     */
    int deleteProjectSuitePlanById(Integer suitePlanId);

    /**
     * 批量删除聚合计划
     *
     * @param suitePlanIds 需要删除的数据ID
     * @return 结果
     */
    int deleteProjectSuitePlanByIds(String[] suitePlanIds);

    /**
     * 查询聚合计划下绑定计划总数
     * @param suiteId 测试计划ID
     */
    int selectProjectSuitePlanCountBySuiteId(Integer suiteId);
}
