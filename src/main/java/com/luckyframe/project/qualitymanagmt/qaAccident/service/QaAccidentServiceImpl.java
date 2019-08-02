package com.luckyframe.project.qualitymanagmt.qaAccident.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyframe.common.support.Convert;
import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.project.qualitymanagmt.qaAccident.domain.QaAccident;
import com.luckyframe.project.qualitymanagmt.qaAccident.mapper.QaAccidentMapper;

/**
 * 生产事故登记 服务层实现
 * 
 * @author luckyframe
 * @date 2019-07-12
 */
@Service
public class QaAccidentServiceImpl implements IQaAccidentService 
{
	@Autowired
	private QaAccidentMapper qaAccidentMapper;

	/**
     * 查询生产事故登记信息
     * 
     * @param accidentId 生产事故登记ID
     * @return 生产事故登记信息
     */
    @Override
	public QaAccident selectQaAccidentById(Integer accidentId)
	{
	    return qaAccidentMapper.selectQaAccidentById(accidentId);
	}
	
	/**
     * 查询生产事故登记列表
     * 
     * @param qaAccident 生产事故登记信息
     * @return 生产事故登记集合
     */
	@Override
	public List<QaAccident> selectQaAccidentList(QaAccident qaAccident)
	{
	    return qaAccidentMapper.selectQaAccidentList(qaAccident);
	}
	
	/**
	 * 事故根据类型分组统计
	 */
	@Override
	public List<Map<Object, Object>>  selectGroupByAccidentType(QaAccident qaAccident)
	{
	    return qaAccidentMapper.selectGroupByAccidentType(qaAccident);
	}
	
	/**
     * 事故根据级别分组统计
     * 
     * @param qaAccident 生产事故登记信息
     * @return 生产事故登记集合
     */
	@Override
	public List<Map<Object, Object>>  selectGroupByAccidentLevel(QaAccident qaAccident)
	{
	    return qaAccidentMapper.selectGroupByAccidentLevel(qaAccident);
	}
	
    /**
     * 新增生产事故登记
     * 
     * @param qaAccident 生产事故登记信息
     * @return 结果
     */
	@Override
	public int insertQaAccident(QaAccident qaAccident)
	{
		qaAccident.setCreateBy(ShiroUtils.getLoginName());
		qaAccident.setCreateTime(new Date());
		qaAccident.setUpdateBy(ShiroUtils.getLoginName());
		qaAccident.setUpdateTime(new Date());
	    return qaAccidentMapper.insertQaAccident(qaAccident);
	}
	
	/**
     * 修改生产事故登记
     * 
     * @param qaAccident 生产事故登记信息
     * @return 结果
     */
	@Override
	public int updateQaAccident(QaAccident qaAccident)
	{
		qaAccident.setUpdateBy(ShiroUtils.getLoginName());
		qaAccident.setUpdateTime(new Date());
	    return qaAccidentMapper.updateQaAccident(qaAccident);
	}

	/**
     * 删除生产事故登记对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteQaAccidentByIds(String ids)
	{
		return qaAccidentMapper.deleteQaAccidentByIds(Convert.toStrArray(ids));
	}
	
}
