package com.luckyframe.project.testmanagmt.projectPageObject.domain;

import com.luckyframe.framework.web.domain.BaseEntity;
import com.luckyframe.project.testmanagmt.projectCaseParams.mapper.ProjectCaseParamsMapper;
import com.luckyframe.project.testmanagmt.projectPageDetail.domain.ProjectPageDetail;
import com.luckyframe.project.testmanagmt.projectPageDetail.mapper.ProjectPageDetailMapper;
import com.luckyframe.project.testmanagmt.projectPageObject.mapper.ProjectPageObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 页面配置管理表 project_page_object
 *
 * @author YSS陈再兴
 * @date 2022-03-10
 */
public class ProjectPageObject extends BaseEntity {

    @Autowired
    private ProjectPageObjectMapper projectPageObjectMapper;
    @Autowired
    private ProjectPageDetailMapper projectPageDetailMapper;
    private static final long serialVersionUID = 1L;
    @Autowired
    private ProjectCaseParamsMapper projectCaseParamsMapper;

    /**
     * 关联项目ID
     */
    private Integer projectId;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 页面唯一编号
     */
    private Integer pageId;
    /**
     * 页面名称
     */
    private String pageName;
    /**
     * 页面父菜单
     */
    private String pageParentMenu;
    /**
     * 页面模块菜单
     */
    private String pageMenu;
    /**
     * pageiframe的定位路径
     */
    private String pageIframe;

    public List<ProjectPageDetail> getProjectPageDetailList() {
        return projectPageDetailList;
    }

    public void setProjectPageDetailList(List<ProjectPageDetail> projectPageDetailList) {
        this.projectPageDetailList = projectPageDetailList;
    }

    private List<ProjectPageDetail>projectPageDetailList;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    private String projectName;

    public ProjectPageObject() {

    }


    public ProjectPageObject(int pageId, String pageName) {
        this.pageId = pageId;

    }

    public ProjectPageObject(String pageName) {
        this.pageName = pageName;
    }

    @Override
    public String toString() {
        return "ProjectPageObject{" +
                "projectId=" + projectId +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", remark='" + remark + '\'' +
                ", pageId=" + pageId +
                ", pageName='" + pageName + '\'' +
                ", pageMainMenu='" + pageParentMenu + '\'' +
                ", pageModule='" + pageMenu + '\'' +
                ", pageIframe='" + pageIframe + '\'' +
                '}';
    }


    public String getPageParentMenu() {
        return pageParentMenu;
    }

    public void setPageParentMenu(String pageParentMenu) {
        this.pageParentMenu = pageParentMenu;
    }

    public String getPageMenu() {
        return pageMenu;
    }

    public void setPageMenu(String pageMenu) {
        this.pageMenu = pageMenu;
    }

    public String getPageIframe() {
        return pageIframe;
    }

    public void setPageIframe(String pageIframe) {
        this.pageIframe = pageIframe;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageName() {
        return pageName;
    }

}
