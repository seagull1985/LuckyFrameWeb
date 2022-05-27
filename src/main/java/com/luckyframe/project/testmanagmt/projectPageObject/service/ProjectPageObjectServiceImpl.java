package com.luckyframe.project.testmanagmt.projectPageObject.service;

import com.luckyframe.common.constant.Constants;
import com.luckyframe.common.support.Convert;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseSteps;
import com.luckyframe.project.testmanagmt.projectPageDetail.domain.ProjectPageDetail;
import com.luckyframe.project.testmanagmt.projectPageDetail.mapper.ProjectPageDetailMapper;
import com.luckyframe.project.testmanagmt.projectPageObject.domain.IbasePageObject;
import com.luckyframe.project.testmanagmt.projectPageObject.domain.ProjectPageObject;
import com.luckyframe.project.testmanagmt.projectPageObject.mapper.ProjectPageObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 页面配置管理 服务层实现
 * @author YSS陈再兴
 * @date 2022-03-10
 */
@Service
public class ProjectPageObjectServiceImpl implements IProjectPageObjectService, IbasePageObject {
    @Autowired(required = false)
    private ProjectPageObjectMapper projectPageObjectMapper;

    @Autowired(required = false)
    private ProjectPageDetailMapper projectPageDetailMapper;

    /**
     * 查询页面配置管理信息
     *
     * @param pageId 页面配置管理ID 主键ID
     * @return 页面配置管理信息 基础信息
     */
    @Override
    public ProjectPageObject selectProjectPageObjectById(Integer pageId) {
        return projectPageObjectMapper.selectProjectPageObjectById(pageId);
    }

    /**
     * 查询页面配置管理列表
     *
     * @param projectPageObject 页面配置管理信息 基础信息
     * @return 页面配置管理集合
     */
    @Override
    public List<ProjectPageObject> selectProjectPageObjectList(ProjectPageObject projectPageObject) {
        return projectPageObjectMapper.selectProjectPageObjectList(projectPageObject);
    }

    /**
     * 新增页面配置管理
     *
     * @param projectPageObject 页面配置管理信息 基础信息
     * @return 结果
     */
    @Override
    public int insertProjectPageObject(ProjectPageObject projectPageObject) {
        return projectPageObjectMapper.insertProjectPageObject(projectPageObject);
    }

    /**
     * 修改页面配置管理
     *
     * @param projectPageObject 页面配置管理信息 基础信息
     * @return 结果
     */
    @Override
    public int updateProjectPageObject(ProjectPageObject projectPageObject) {
        return projectPageObjectMapper.updateProjectPageObject(projectPageObject);
    }

    /**
     * 删除页面配置管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteProjectPageObjectByIds(String ids) {
        return projectPageObjectMapper.deleteProjectPageObjectByIds(Convert.toStrArray(ids));
    }


    @Override
    public List<ProjectPageObject> getAllPageObject2(int projectId) {
        ProjectPageObject temp = new ProjectPageObject();
        temp.setProjectId(projectId);
        List<ProjectPageObject> projectPageObjectsList = projectPageObjectMapper.selectProjectPageObjectList(temp);
        for (ProjectPageObject projectPageObject : projectPageObjectsList) {
            ProjectPageDetail projectPageDetail = new ProjectPageDetail();
            projectPageDetail.setPageId(projectPageObject.getPageId());
            List<ProjectPageDetail> projectPageDetailList = projectPageDetailMapper.selectProjectPageDetailList(projectPageDetail);
            projectPageObject.setProjectPageDetailList(projectPageDetailList);
        }
        return projectPageObjectsList;
    }
}
