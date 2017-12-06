package rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rmi.model.RunBatchCaseEntity;
import rmi.model.RunCaseEntity;
import rmi.model.RunTaskEntity;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 此为远程对象调用的接口，必须继承Remote类
 * @author seagull
 */
public interface RunService extends Remote {
	/**
	 * 运行任务
	 * @param task
	 * @param loadpath
	 * @return
	 * @throws RemoteException
	 */
    public String runtask(RunTaskEntity task,String loadpath) throws RemoteException;
    /**
     * 运行单条用例
     * @param onecase
     * @param loadpath
     * @return
     * @throws RemoteException
     */
    public String runcase(RunCaseEntity onecase,String loadpath) throws RemoteException;
    /**
     * 运行批量用例
     * @param batchcase
     * @param loadpath
     * @return
     * @throws RemoteException
     */
    public String runbatchcase(RunBatchCaseEntity batchcase,String loadpath) throws RemoteException;
    /**
     * 取客户端日志
     * @param storeName
     * @return
     * @throws RemoteException
     */
    public String getlogdetail(String storeName) throws RemoteException;
    /**
     * 取客户端错误图片
     * @param imgName
     * @return
     * @throws RemoteException
     */
    public byte[] getlogimg(String imgName) throws RemoteException;
    /**
     * 上传JAR包到客户端
     * @param fileContent
     * @param name
     * @param loadpath
     * @return
     * @throws RemoteException
     */
    public String uploadjar(byte[] fileContent,String name,String loadpath) throws RemoteException;
    /**
     * WEB端远程调试用例
     * @param sign
     * @param executor
     * @param loadpath
     * @return
     * @throws RemoteException
     */
    public String webdebugcase(String sign,String executor,String loadpath) throws RemoteException;
    /**
     * 检测客户端心跳
     * @return
     * @throws RemoteException
     */
    public String getClientStatus() throws RemoteException;
}
