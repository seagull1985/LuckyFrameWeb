package com.luckyframe.project.qualitymanagmt.qaVersion.mapper;

import java.util.List;

import com.luckyframe.project.qualitymanagmt.qaVersion.domain.QaVersion;
import org.springframework.stereotype.Component;

/**
 * 质量管理-版本管理 数据层
 * 
 * @author luckyframe
 * @date 2019-08-05
 */
@Component
public interface QaVersionMapper 
{
	/**
     * 查询质量管理-版本管理信息
     * 
     * @param versionId 质量管理-版本管理ID
     * @return 质量管理-版本管理信息
     */
	QaVersion selectQaVersionById(Integer versionId);
	
	/**
     * 查询质量管理-版本管理列表
     * 
     * @param qaVersion 质量管理-版本管理信息
     * @return 质量管理-版本管理集合
     */
	List<QaVersion> selectQaVersionList(QaVersion qaVersion);
	
	/**
     * 新增质量管理-版本管理
     * 
     * @param qaVersion 质量管理-版本管理信息
     * @return 结果
     */
	int insertQaVersion(QaVersion qaVersion);
	
	/**
     * 修改质量管理-版本管理
     * 
     * @param qaVersion 质量管理-版本管理信息
     * @return 结果
     */
	int updateQaVersion(QaVersion qaVersion);
	
	/**
     * 删除质量管理-版本管理
     * 
     * @param versionId 质量管理-版本管理ID
     * @return 结果
     */
	int deleteQaVersionById(Integer versionId);
	
	/**
     * 批量删除质量管理-版本管理
     * 
     * @param versionIds 需要删除的数据ID
     * @return 结果
     */
	int deleteQaVersionByIds(String[] versionIds);
	
}