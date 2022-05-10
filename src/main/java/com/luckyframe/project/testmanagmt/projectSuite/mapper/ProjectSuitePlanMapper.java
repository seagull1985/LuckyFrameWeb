package com.luckyframe.project.testmanagmt.projectSuite.mapper;

import com.luckyframe.project.testmanagmt.projectSuite.domain.ProjectSuitePlan;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 聚合计划 数据层
 *
 * @author jerelli
 * @date 2021年1月5日
 */

@Component
public interface ProjectSuitePlanMapper {

    /**
     * 查询聚合计划用例信息
     *
     * @param suitePlanId 聚合计划用例ID
     * @return 聚合计划信息
     */
    ProjectSuitePlan selectProjectSuitePlanById(Integer suitePlanId);

    /**
     * 查询聚合计划用例列表
     *
     * @param projectSuitePlan 聚合计划用例信息
     * @return 聚合计划集合
     */
    List<ProjectSuitePlan> selectProjectSuitePlanList(ProjectSuitePlan projectSuitePlan);

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
     * 根据suiteId删除聚合计划中所有计划
     * @param suiteId 测试计划ID
     * @author Seagull
     * @date 2019年4月12日
     */
    int deleteProjectSuitePlanBySuiteId(Integer suiteId);

    /**
     * 查询该测试计划有没有绑定聚合计划
     * @param planId 用例ID
     * @author Seagull
     * @date 2019年4月12日
     */
    int selectProjectSuitePlanCountByPlanId(Integer planId);

    /**
     * 查询聚合计划下绑定计划总数
     * @param suiteId 测试计划ID
     * @author Seagull
     * @date 2019年5月13日
     */
    int selectProjectSuitePlanCountBySuiteId(Integer suiteId);

    /**
     * 查询同一聚合计划下同一个计划的数量
     * @param suiteId 聚合测试计划ID  planId 测试计划ID
     * @author Seagull
     * @date 2022年5月10日
     */
    int selectProjectSuitePlanCountBySuiteIdAndPlanId(ProjectSuitePlan projectSuitePlan);


}
