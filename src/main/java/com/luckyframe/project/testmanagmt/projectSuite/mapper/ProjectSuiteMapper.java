package com.luckyframe.project.testmanagmt.projectSuite.mapper;

import com.luckyframe.project.testmanagmt.projectSuite.domain.ProjectSuite;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProjectSuiteMapper {

    List<ProjectSuite> selectProjectSuiteList(ProjectSuite projectSuite);

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
     * 删除测试计划
     *
     * @param suiteId 测试计划ID
     * @return 结果
     */
    int deleteProjectSuiteById(Integer suiteId);

    /**
     * 检查测试计划名称唯一性
     * @param suiteName 测试计划名称
     */
    ProjectSuite checkProjectSuiteNameUnique(String suiteName);
}
