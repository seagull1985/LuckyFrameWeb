package com.luckyframe.project.testmanagmt.projectPageObject.service;

import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseSteps;
import com.luckyframe.project.testmanagmt.projectPageObject.domain.ProjectPageObject;

import java.util.List;
import java.util.Map;

/**
 * 页面配置管理 服务层
 *
 * @author YSS陈再兴
 * @date 2022-03-10
 */
public interface IProjectPageObjectService {
    /**
     * 查询页面配置管理信息
     *
     * @param projectId 页面配置管理ID 主键ID
     * @return 页面配置管理信息
     */
    public ProjectPageObject selectProjectPageObjectById(Integer projectId);

    /**
     * 查询页面配置管理列表
     *
     * @param projectPageObject 页面配置管理信息 基本信息
     * @return 页面配置管理集合
     */
    public List<ProjectPageObject> selectProjectPageObjectList(ProjectPageObject projectPageObject);

    /**
     * 新增页面配置管理
     *
     * @param projectPageObject 页面配置管理信息 基本信息
     * @return 结果
     */
    public int insertProjectPageObject(ProjectPageObject projectPageObject);

    /**
     * 修改页面配置管理
     *
     * @param projectPageObject 页面配置管理信息 基本信息
     * @return 结果
     */
    public int updateProjectPageObject(ProjectPageObject projectPageObject);

    /**
     * 删除页面配置管理信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteProjectPageObjectByIds(String ids);


    /**
     * 获取PO的缓存
     * @param projectId 项目ID
     * @return
     */
    public List<ProjectPageObject> getAllPageObject2(int projectId);


}
