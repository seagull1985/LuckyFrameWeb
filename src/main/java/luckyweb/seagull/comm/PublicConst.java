package luckyweb.seagull.comm;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 项目常量/权限码定义
 * @author seagull
 * 
 */
public class PublicConst {
	/**
	 * 区分任务调度定时任务
	 */
	public static final String JOBTASKNAMETYPE="*JOB";
	/**
	 * 区分客户端心跳定时任务
	 */
	public static final String JOBCLIENTNAMETYPE="*CLIENT";
	/**
	 * 区别状态码 字符型 00
	 */
	public static final String STATUSSTR00="00";
	/**
	 * 区别状态码 字符型 0
	 */
	public static final String STATUSSTR0="0";
	/**
	 * 区别状态码 字符型 99
	 */
	public static final String STATUSSTR99="99";
	/**
	 * 区别状态码 整型 99
	 */
	public static final int STATUS99=99;
	/**
	 * HTTP请求类型 POST
	 */
	public static final String REQPOSTTYPE="POST";
	/**
	 * SESSION保存关键字账户名称  usercode
	 */
	public static final String SESSIONKEYUSERCODE="usercode";
	/**
	 * SESSION保存关键字用户名称  username
	 */
	public static final String SESSIONKEYUSERNAME="username";
	/**
	 * SESSION保存关键字用户权限  permission
	 */
	public static final String SESSIONKEYPERMISSION="permission";
	/**
	 * SESSION保存关键字项目权限  oppid
	 */
	public static final String SESSIONKEYPROJECTID="oppid";
	/**
	 * service层 where条件中去掉尾部字符个数
	 */
	public static final int WHERENUM=7;
	
	
	
	
	
	
	
	/**
	 * 生产故障权限码 添加生产事故
	 */
	public static final String AUTHACCADD="acc_1";
	/**
	 * 生产故障权限码 删除生产事故
	 */
	public static final String AUTHACCDEL="acc_2";
	/**
	 * 生产故障权限码 修改生产事故
	 */
	public static final String AUTHACCMOD="acc_3";
	/**
	 * 生产故障权限码 生产事故上传附件
	 */
	public static final String AUTHACCUPLOAD="acc_upload";
	
	/**
	 * 用例执行权限码 用例执行
	 */
	public static final String AUTHCASEEXC="case_ex";
	/**
	 * 用例执行权限码 全部失败用例判断
	 */
	public static final String AUTHCASEALLFAIL="ALLFAIL";
	/**
	 * 流程检查权限码 添加流程检查
	 */
	public static final String AUTHFCADD="fc_1";
	/**
	 * 流程检查权限码 删除流程检查
	 */
	public static final String AUTHFCDEL="fc_2";
	/**
	 * 流程检查权限码 修改流程检查
	 */
	public static final String AUTHFCMOD="fc_3";
	/**
	 * 流程检查计划权限码 添加流程检查计划
	 */
	public static final String AUTHPFCADD="pfc_1";
	/**
	 * 流程检查计划权限码 删除流程检查计划
	 */
	public static final String AUTHPFCDEL="pfc_2";
	/**
	 * 流程检查计划权限码 修改流程检查计划
	 */
	public static final String AUTHPFCMOD="pfc_3";
	/**
	 * 流程检查计划权限码 流程检查计划转检查结果
	 */
	public static final String AUTHPFCTOCHECK="fc_tocheck";
	/**
	 * 测试用例权限码 添加测试用例
	 */
	public static final String AUTHCASEADD="case_1";
	/**
	 * 测试用例权限码 删除测试用例
	 */
	public static final String AUTHCASEDEL="case_2";
	/**
	 * 测试用例权限码 修改测试用例
	 */
	public static final String AUTHCASEMOD="case_3";
	/**
	 * 测试用例步骤管理
	 */
	public static final String AUTHCASESTEPS="case_step";
	/**
	 * 测试计划权限码 添加测试计划
	 */
	public static final String AUTHTESTPLANADD="proplan_1";
	/**
	 * 测试计划权限码 删除测试计划
	 */
	public static final String AUTHTESTPLANDEL="proplan_2";
	/**
	 * 测试计划权限码 修改测试计划
	 */
	public static final String AUTHTESTPLANMOD="proplan_3";
	/**
	 * 协议模板权限码 添加协议模板
	 */
	public static final String AUTHPPTEMPLATEADD="ptct_1";
	/**
	 * 协议模板权限码 删除协议模板
	 */
	public static final String AUTHPPTEMPLATEDEL="ptct_2";
	/**
	 * 协议模板权限码 修改协议模板
	 */
	public static final String AUTHPPTEMPLATEMOD="ptct_3";
	/**
	 * 项目版本权限码 添加项目版本
	 */
	public static final String AUTHPVERSIONADD="pv_1";
	/**
	 * 项目版本权限码 删除项目版本
	 */
	public static final String AUTHPVERSIONDEL="pv_2";
	/**
	 * 项目版本权限码 修改项目版本
	 */
	public static final String AUTHPVERSIONMOD="pv_3";
	/**
	 * 项目版本权限码 添加项目版本计划
	 */
	public static final String AUTHPVERSIONPLANADD="pvp_1";
	/**
	 * 评审信息权限码 删除评审信息
	 */
	public static final String AUTHREVIEWDEL="rev_2";
	/**
	 * 评审信息权限码 修改评审信息
	 */
	public static final String AUTHREVIEWMOD="rev_3";
	/**
	 * 评审详细信息权限码 添加评审详细信息
	 */
	public static final String AUTHPVERSIONINFOADD="revinfo_1";
	/**
	 * 评审详细信息权限码 删除评审详细信息
	 */
	public static final String AUTHPVERSIONINFODEL="revinfo_2";
	/**
	 * 评审详细信息权限码 修改评审详细信息
	 */
	public static final String AUTHPVERSIONINFOMOD="revinfo_3";
	/**
	 * 部门管理权限码 添加部门管理
	 */
	public static final String AUTHSECTORADD="dpmt_1";
	/**
	 * 部门管理权限码 删除部门管理
	 */
	public static final String AUTHSECTORDEL="dpmt_2";
	/**
	 * 部门管理权限码 修改部门管理
	 */
	public static final String AUTHSECTORMOD="dpmt_3";
	/**
	 * 项目管理权限码 添加项目管理
	 */
	public static final String AUTHPROJECTADD="pro_1";
	/**
	 * 项目管理权限码 删除项目管理
	 */
	public static final String AUTHPROJECTDEL="pro_2";
	/**
	 * 项目管理权限码 修改项目管理
	 */
	public static final String AUTHPROJECTMOD="pro_3";
	/**
	 * 执行任务权限码 删除执行任务
	 */
	public static final String AUTHTASKEXDEL="tastex_2";
	/**
	 * 客户端管理权限码 添加客户端
	 */
	public static final String AUTHCLIENTADD="client_1";
	/**
	 * 客户端管理权限码 删除客户端
	 */
	public static final String AUTHCLIENTDEL="client_2";
	/**
	 * 客户端管理权限码 修改客户端
	 */
	public static final String AUTHCLIENTMOD="client_3";
	/**
	 * 任务调度管理权限码 添加任务调度
	 */
	public static final String AUTHJOBADD="tast_1";
	/**
	 * 任务调度管理权限码 删除任务调度
	 */
	public static final String AUTHJOBDEL="tast_2";
	/**
	 * 任务调度管理权限码 修改任务调度
	 */
	public static final String AUTHJOBMOD="tast_3";
	/**
	 * 任务调度管理权限码 任务调度中上传JAR包
	 */
	public static final String AUTHJOBUPLOAD="tast_upload";
	/**
	 * 用户信息管理权限码 添加用户信息
	 */
	public static final String AUTHUSERINFOADD="ui_1";
	/**
	 * 用户信息管理权限码 删除用户信息
	 */
	public static final String AUTHUSERINFODEL="ui_2";
	/**
	 * 用户信息管理权限码 修改用户信息
	 */
	public static final String AUTHUSERINFOMOD="ui_3";
	/**
	 * 用户角色管理权限码 添加用户角色
	 */
	public static final String AUTHUSERROLEADD="role_1";
	/**
	 * 用户角色管理权限码 删除用户角色
	 */
	public static final String AUTHUSERROLEDEL="role_2";
	/**
	 * 用户角色管理权限码 修改用户角色
	 */
	public static final String AUTHUSERROLEMOD="role_3";
}
