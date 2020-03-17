package com.luckyframe.project.qualitymanagmt.qaAccident.service;

import java.util.List;
import java.util.Map;

import com.luckyframe.project.qualitymanagmt.qaAccident.domain.QaAccident;

/**
 * 生产事故登记 服务层
 * 
 * @author luckyframe
 * @date 2019-07-12
 */
public interface IQaAccidentService 
{
	/**
     * 查询生产事故登记信息
     * 
     * @param accidentId 生产事故登记ID
     * @return 生产事故登记信息
     */
	QaAccident selectQaAccidentById(Integer accidentId);
	
	/**
     * 查询生产事故登记列表
     * 
     * @param qaAccident 生产事故登记信息
     * @return 生产事故登记集合
     */
	List<QaAccident> selectQaAccidentList(QaAccident qaAccident);
	
	/**
     * 新增生产事故登记
     * 
     * @param qaAccident 生产事故登记信息
     * @return 结果
     */
	int insertQaAccident(QaAccident qaAccident);
	
	/**
     * 修改生产事故登记
     * 
     * @param qaAccident 生产事故登记信息
     * @return 结果
     */
	int updateQaAccident(QaAccident qaAccident);
		
	/**
     * 删除生产事故登记信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	int deleteQaAccidentByIds(String ids);
	
	/**
     * 事故根据类型分组统计
     * 
     * @param qaAccident 生产事故登记信息
     * @return 生产事故登记集合
     */
	List<Map<Object, Object>>  selectGroupByAccidentType(QaAccident qaAccident);
	
	/**
     * 事故根据级别分组统计
     * 
     * @param qaAccident 生产事故登记信息
     * @return 生产事故登记集合
     */
	List<Map<Object, Object>>  selectGroupByAccidentLevel(QaAccident qaAccident);
}
