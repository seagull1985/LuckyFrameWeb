package com.luckyframe.project.monitor.logininfor.mapper;

import java.util.List;
import com.luckyframe.project.monitor.logininfor.domain.Logininfor;
import org.springframework.stereotype.Component;

/**
 * 系统访问日志情况信息 数据层
 * 
 * @author ruoyi
 */
@Component
public interface LogininforMapper
{
    /**
     * 新增系统登录日志
     * 
     * @param logininfor 访问日志对象
     */
    void insertLogininfor(Logininfor logininfor);

    /**
     * 查询系统登录日志集合
     * 
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    List<Logininfor> selectLogininforList(Logininfor logininfor);

    /**
     * 批量删除系统登录日志
     * 
     * @param ids 需要删除的数据
     * @return 结果
     */
    int deleteLogininforByIds(String[] ids);

    /**
     * 清空系统登录日志
     * 
     * @return 结果
     */
    int cleanLogininfor();
}
