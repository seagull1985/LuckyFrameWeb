package com.luckyframe.common.utils.security;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.luckyframe.common.constant.PermissionConstants;
import com.luckyframe.common.utils.MessageUtils;

/**
 * permission 工具类
 * 
 * @author ruoyi
 */
public class PermissionUtils
{
	 private static final Logger logger = LoggerFactory.getLogger("sys-user");
    /**
     * 权限错误消息提醒
     * 
     * @param permissionsStr 错误信息
     */
    public static String getMsg(String permissionsStr)
    {
        String permission = StringUtils.substringBetween(permissionsStr, "[", "]");
        String msg = MessageUtils.message("no.view.permission", permission);
        if (StringUtils.endsWithIgnoreCase(permission, PermissionConstants.ADD_PERMISSION))
        {
            msg = MessageUtils.message("no.create.permission", permission);
        }
        else if (StringUtils.endsWithIgnoreCase(permission, PermissionConstants.EDIT_PERMISSION))
        {
            msg = MessageUtils.message("no.update.permission", permission);
        }
        else if (StringUtils.endsWithIgnoreCase(permission, PermissionConstants.REMOVE_PERMISSION))
        {
            msg = MessageUtils.message("no.delete.permission", permission);
        }
        else if (StringUtils.endsWithIgnoreCase(permission, PermissionConstants.EXPORT_PERMISSION))
        {
            msg = MessageUtils.message("no.export.permission", permission);
        }
        else if (StringUtils.endsWithAny(permission, new String[] { PermissionConstants.VIEW_PERMISSION, PermissionConstants.LIST_PERMISSION }))
        {
            msg = MessageUtils.message("no.view.permission", permission);
        }
        return msg;
    }
    
	/**
	 * 查询用户项目权限
	 * @param projectId 项目ID
	 * @author Seagull
	 * @date 2019年4月12日
	 */
	public static Boolean isProjectPermsPassByProjectId(Integer projectId)
	{
		List<Integer> projectIDList = ShiroUtils.getProjectIdForRoles();
    	
    	boolean result = false;
    	
    	if(null==ShiroUtils.getLoginName()){
    		logger.warn("项目访问权限不通过，用户登录名在ShiroUtils中为空...");
    		return false;
    	}
    	
    	/*超级管理员权限*/
    	if("admin".equals(ShiroUtils.getLoginName())){
    		return true;
    	}
    	
    	for(Integer pId:projectIDList){
    		if(projectId.equals(pId)){
    			result = true;
    			break;
    		}   		
    	}
    	
    	if(!result){
    		logger.warn("项目访问权限不通过，被访项目ID:{},用户项目权限列表：{}",projectId,JSON.toJSONString(projectIDList));
    	}
    	
	    return result;
	}
	
	
}
