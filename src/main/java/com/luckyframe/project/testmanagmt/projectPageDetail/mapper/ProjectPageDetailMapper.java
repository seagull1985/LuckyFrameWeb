package com.luckyframe.project.testmanagmt.projectPageDetail.mapper;

import com.luckyframe.project.testmanagmt.projectPageDetail.domain.ProjectPageDetail;

import java.util.List;

/**
 * 页面详情 数据层
 * 
 * @author YSS陈再兴
 * @date 2022-03-10
 */
public interface ProjectPageDetailMapper 
{
	/**
     * 查询页面详情信息
     * 
     * @param id 页面详情ID 主键ID
     * @return 页面详情信息
     */
	public ProjectPageDetail selectProjectPageDetailById(Integer id);
	
	/**
     * 查询页面详情列表
     * 
     * @param projectPageDetail 页面详情信息 基本信息
     * @return 页面详情集合
     */
	public List<ProjectPageDetail> selectProjectPageDetailList(ProjectPageDetail projectPageDetail);
	
	/**
     * 新增页面详情
     * 
     * @param projectPageDetail 页面详情信息 基本信息
     * @return 结果
     */
	public int insertProjectPageDetail(ProjectPageDetail projectPageDetail);
	
	/**
     * 修改页面详情
     * 
     * @param projectPageDetail 页面详情信息 基本信息
     * @return 结果
     */
	public int updateProjectPageDetail(ProjectPageDetail projectPageDetail);
	
	/**
     * 删除页面详情
     * 
     * @param id 页面详情ID 主键ID
     * @return 结果
     */
	public int deleteProjectPageDetailById(Integer id);
	
	/**
     * 批量删除页面详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProjectPageDetailByIds(String[] ids);
	
}