package com.luckyframe.project.qualitymanagmt.qaVersion.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyframe.common.support.Convert;
import com.luckyframe.common.utils.DateUtils;
import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.project.qualitymanagmt.qaVersion.domain.QaVersion;
import com.luckyframe.project.qualitymanagmt.qaVersion.mapper.QaVersionMapper;

/**
 * 质量管理-版本管理 服务层实现
 * 
 * @author luckyframe
 * @date 2019-08-05
 */
@Service
public class QaVersionServiceImpl implements IQaVersionService 
{
	@Autowired
	private QaVersionMapper qaVersionMapper;

	/**
     * 查询质量管理-版本管理信息
     * 
     * @param versionId 质量管理-版本管理ID
     * @return 质量管理-版本管理信息
     */
    @Override
	public QaVersion selectQaVersionById(Integer versionId)
	{
	    return qaVersionMapper.selectQaVersionById(versionId);
	}
	
	/**
     * 查询质量管理-版本管理列表
     * 
     * @param qaVersion 质量管理-版本管理信息
     * @return 质量管理-版本管理集合
     */
	@Override
	public List<QaVersion> selectQaVersionList(QaVersion qaVersion)
	{
	    return qaVersionMapper.selectQaVersionList(qaVersion);
	}
	
    /**
     * 新增质量管理-版本管理
     * 
     * @param qaVersion 质量管理-版本管理信息
     * @return 结果
     */
	@Override
	public int insertQaVersion(QaVersion qaVersion)
	{
		qaVersion.setCreateBy(ShiroUtils.getLoginName());
		qaVersion.setCreateTime(new Date());
		qaVersion.setUpdateBy(ShiroUtils.getLoginName());
		qaVersion.setUpdateTime(new Date());
		
		if("已完成".equals(qaVersion.getVersionStatus())){
			/*计算工期偏移天数*/
			int projectDeviationDays = DateUtils.getDatePoorStr(qaVersion.getPlanFinishDate(),qaVersion.getActuallyFinishDate());
			qaVersion.setProjectDeviationDays(projectDeviationDays);
			
			/*计算工期偏移百分比，保留两位小数*/
			float deviationPercent = (float)projectDeviationDays/qaVersion.getTimeLimitVersion();
			BigDecimal bigDecimal = new BigDecimal(deviationPercent*100);  
			deviationPercent = bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue(); 
			qaVersion.setProjectDeviationPercent(deviationPercent);
			
			/*计算需求完成百分比，保留两位小数*/
			float demandPercent = (float)qaVersion.getDemandActuallyFinish()/qaVersion.getDemandPlanFinish();
			BigDecimal bigDecimal1 = new BigDecimal(demandPercent*100);  
			demandPercent = bigDecimal1.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue(); 
			qaVersion.setDemandPercent(demandPercent);
			
			/*计算缺陷DI值*/
			if(null!=qaVersion.getBugZm()&&null!=qaVersion.getBugYz()&&null!=qaVersion.getBugYb()&&null!=qaVersion.getBugTs()){
				float codeDi = (float)(qaVersion.getBugZm()*10+qaVersion.getBugYz()*3+qaVersion.getBugYb()+qaVersion.getBugTs()*0.1);
				qaVersion.setCodeDi(codeDi);
			}

		}
		
	    return qaVersionMapper.insertQaVersion(qaVersion);
	}
	
	/**
     * 修改质量管理-版本管理
     * 
     * @param qaVersion 质量管理-版本管理信息
     * @return 结果
     */
	@Override
	public int updateQaVersion(QaVersion qaVersion)
	{
		qaVersion.setUpdateBy(ShiroUtils.getLoginName());
		qaVersion.setUpdateTime(new Date());
		
		if("已完成".equals(qaVersion.getVersionStatus())){
			/*计算工期偏移天数*/
			int projectDeviationDays = DateUtils.getDatePoorStr(qaVersion.getPlanFinishDate(),qaVersion.getActuallyFinishDate());
			qaVersion.setProjectDeviationDays(projectDeviationDays);
			
			/*计算工期偏移百分比，保留两位小数*/
			float deviationPercent = (float)projectDeviationDays/qaVersion.getTimeLimitVersion();
			BigDecimal bigDecimal = new BigDecimal(deviationPercent*100);  
			deviationPercent = bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue(); 
			qaVersion.setProjectDeviationPercent(deviationPercent);
			
			/*计算需求完成百分比，保留两位小数*/
			float demandPercent = (float)qaVersion.getDemandActuallyFinish()/qaVersion.getDemandPlanFinish();
			BigDecimal bigDecimal1 = new BigDecimal(demandPercent*100);  
			demandPercent = bigDecimal1.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue(); 
			qaVersion.setDemandPercent(demandPercent);
			
			/*计算缺陷DI值*/
			if(null!=qaVersion.getBugZm()&&null!=qaVersion.getBugYz()&&null!=qaVersion.getBugYb()&&null!=qaVersion.getBugTs()){
				float codeDi = (float)(qaVersion.getBugZm()*10+qaVersion.getBugYz()*3+qaVersion.getBugYb()+qaVersion.getBugTs()*0.1);
				qaVersion.setCodeDi(codeDi);
			}
		}
		
	    return qaVersionMapper.updateQaVersion(qaVersion);
	}

	/**
     * 删除质量管理-版本管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteQaVersionByIds(String ids)
	{
		return qaVersionMapper.deleteQaVersionByIds(Convert.toStrArray(ids));
	}
	public static void main(String[] args) {
		int i=-3;
		int b=14;
		float a = (float) i/b;
		BigDecimal bb = new BigDecimal(a*100);  
		float   m_price   =  bb.setScale(2,  BigDecimal.ROUND_HALF_UP).floatValue(); 
		System.out.println(m_price);
	}
}
