package rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rmi.model.RunBatchCaseEntity;
import rmi.model.RunCaseEntity;
import rmi.model.RunTaskEntity;

//此为远程对象调用的接口，必须继承Remote类
public interface RunService extends Remote {
    public String runtask(RunTaskEntity task) throws RemoteException;
    public String runcase(RunCaseEntity onecase) throws RemoteException;
    public String runbatchcase(RunBatchCaseEntity batchcase) throws RemoteException;
    public String getlogdetail(String storeName) throws RemoteException;
    public byte[] getlogimg(String imgName) throws RemoteException;
    public String uploadjar(byte[] fileContent,String name) throws RemoteException;
    public String webdebugcase(String sign,String executor) throws RemoteException;
}
