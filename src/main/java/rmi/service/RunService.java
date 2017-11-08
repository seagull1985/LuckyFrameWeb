package rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rmi.model.RunBatchCaseEntity;
import rmi.model.RunCaseEntity;
import rmi.model.RunTaskEntity;

//此为远程对象调用的接口，必须继承Remote类
public interface RunService extends Remote {
    public String runtask(RunTaskEntity task,String loadpath) throws RemoteException;
    public String runcase(RunCaseEntity onecase,String loadpath) throws RemoteException;
    public String runbatchcase(RunBatchCaseEntity batchcase,String loadpath) throws RemoteException;
    public String getlogdetail(String storeName) throws RemoteException;
    public byte[] getlogimg(String imgName) throws RemoteException;
    public String uploadjar(byte[] fileContent,String name,String loadpath) throws RemoteException;
    public String webdebugcase(String sign,String executor,String loadpath) throws RemoteException;
    public String getClientStatus() throws RemoteException;
}
