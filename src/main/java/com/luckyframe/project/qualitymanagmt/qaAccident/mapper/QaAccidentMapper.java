package com.luckyframe.project.qualitymanagmt.qaAccident.mapper;

import java.util.List;
import java.util.Map;

import com.luckyframe.project.qualitymanagmt.qaAccident.domain.QaAccident;	

/**
 * 生产事故登记 数据层
 * 
 * @author luckyframe
 * @date 2019-07-12
 */
public interface QaAccidentMapper 
{
	/**
     * 查询生产事故登记信息
     * 
     * @param accidentId 生产事故登记ID
     * @return 生产事故登记信息
     */
	public QaAccident selectQaAccidentById(Integer accidentId);
	
	/**
     * 查询生产事故登记列表
     * 
     * @param qaAccident 生产事故登记信息
     * @return 生产事故登记集合
     */
	public List<QaAccident> selectQaAccidentList(QaAccident qaAccident);
	
	/**
     * 新增生产事故登记
     * 
     * @param qaAccident 生产事故登记信息
     * @return 结果
     */
	public int insertQaAccident(QaAccident qaAccident);
	
	/**
     * 修改生产事故登记
     * 
     * @param qaAccident 生产事故登记信息
     * @return 结果
     */
	public int updateQaAccident(QaAccident qaAccident);
	
	/**
     * 删除生产事故登记
     * 
     * @param accidentId 生产事故登记ID
     * @return 结果
     */
	public int deleteQaAccidentById(Integer accidentId);
	
	/**
     * 批量删除生产事故登记
     * 
     * @param accidentIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteQaAccidentByIds(String[] accidentIds);
	
	/**
	 * 事故根据类型分组统计
	 * @param qaAccident
	 * @return
	 * @author Seagull
	 * @date 2019年8月1日
	 */
	public List<Map<Object,Object>> selectGroupByAccidentType(QaAccident qaAccident);
	
	/**
	 * 事故根据级别分组统计
	 * @param qaAccident
	 * @return
	 * @author Seagull
	 * @date 2019年8月1日
	 */
	public List<Map<Object,Object>> selectGroupByAccidentLevel(QaAccident qaAccident);
}