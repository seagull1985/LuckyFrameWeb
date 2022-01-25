package com.luckyframe.project.testmanagmt.projectSuite.service;

import com.luckyframe.project.testmanagmt.projectSuite.domain.ProjectSuite;

import java.util.List;

public interface IprojectSuiteService {

    /**
     * 查询测试计划列表
     *
     * @param projectSuite 测试计划信息
     * @return 测试计划集合
     */
    List<ProjectSuite> selectProjectSuiteList(ProjectSuite projectSuite);

    /**
     * 根据项目ID查询聚合计划列表
     * @param projectId
     * @return
     */
    List<ProjectSuite> selectProjectSuiteListByProjectId(Integer projectId);

    /**
     * 新增测试计划
     *
     * @param projectSuite 测试计划信息
     * @return 结果
     */
    int insertProjectSuite(ProjectSuite projectSuite);

    /**
     * 查询测试计划信息
     *
     * @param suiteId 测试计划ID
     * @return 测试计划信息
     */
    ProjectSuite selectProjectSuiteById(Integer suiteId);

    /**
     * 修改测试计划
     *
     * @param projectSuite 测试计划信息
     * @return 结果
     */
    int updateProjectSuite(ProjectSuite projectSuite);

    /**
     * 删除测试计划信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteProjectSuiteByIds(String ids);

    /**
     * 校验测试计划名称是否唯一
     * @param projectSuite 测试计划对象
     * @author Seagull
     * @date 2019年3月18日
     */
    String checkProjectSuiteNameUnique(ProjectSuite projectSuite);
}
