package com.luckyframe.project.testmanagmt.projectPageDetail.service;

import com.luckyframe.common.support.Convert;
import com.luckyframe.project.testmanagmt.projectPageDetail.domain.ProjectPageDetail;
import com.luckyframe.project.testmanagmt.projectPageDetail.mapper.ProjectPageDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 页面详情 服务层实现
 * 
 * @author YSS陈再兴
 * @date 2022-03-10
 */
@Service
public class ProjectPageDetailServiceImpl implements IProjectPageDetailService 
{
	@Autowired
	private ProjectPageDetailMapper projectPageDetailMapper;

	/**
     * 查询页面详情信息
     * 
     * @param id 页面详情ID 主键ID
     * @return 页面详情信息 基础信息
     */
    @Override
	public ProjectPageDetail selectProjectPageDetailById(Integer id)
	{
	    return projectPageDetailMapper.selectProjectPageDetailById(id);
	}
	
	/**
     * 查询页面详情列表
     * 
     * @param projectPageDetail 页面详情信息 基础信息
     * @return 页面详情集合
     */
	@Override
	public List<ProjectPageDetail> selectProjectPageDetailList(ProjectPageDetail projectPageDetail)
	{
	    return projectPageDetailMapper.selectProjectPageDetailList(projectPageDetail);
	}
	
    /**
     * 新增页面详情
     * 
     * @param projectPageDetail 页面详情信息 基础信息
     * @return 结果
     */
	@Override
	public int insertProjectPageDetail(ProjectPageDetail projectPageDetail)
	{
	    return projectPageDetailMapper.insertProjectPageDetail(projectPageDetail);
	}
	
	/**
     * 修改页面详情
     * 
     * @param projectPageDetail 页面详情信息 基础信息
     * @return 结果
     */
	@Override
	public int updateProjectPageDetail(ProjectPageDetail projectPageDetail)
	{
	    return projectPageDetailMapper.updateProjectPageDetail(projectPageDetail);
	}

	/**
     * 删除页面详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProjectPageDetailByIds(String ids)
	{
		return projectPageDetailMapper.deleteProjectPageDetailByIds(Convert.toStrArray(ids));
	}
	
}
