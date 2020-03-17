package com.luckyframe.project.tool.gen.mapper;

import java.util.List;
import com.luckyframe.project.tool.gen.domain.ColumnInfo;
import com.luckyframe.project.tool.gen.domain.TableInfo;
import org.springframework.stereotype.Component;

/**
 * 代码生成 数据层
 * 
 * @author ruoyi
 */
@Component
public interface GenMapper
{
    /**
     * 查询ry数据库表信息
     * 
     * @param tableInfo 表信息
     * @return 数据库表列表
     */
    List<TableInfo> selectTableList(TableInfo tableInfo);

    /**
     * 根据表名称查询信息
     * 
     * @param tableName 表名称
     * @return 表信息
     */
    TableInfo selectTableByName(String tableName);

    /**
     * 根据表名称查询列信息
     * 
     * @param tableName 表名称
     * @return 列信息
     */
    List<ColumnInfo> selectTableColumnsByName(String tableName);
}
