package rmi.remotingclient;

import java.rmi.Naming;

import rmi.model.RunTaskEntity;
import rmi.service.RunService;


public class Program {
    public static void main(String[] args){
    	try{
    		//调用远程对象，注意RMI路径与接口必须与服务器配置一致
    		RunService service=(RunService)Naming.lookup("rmi://10.211.19.55:6633/RunService");
    		RunTaskEntity task = new RunTaskEntity();
    		task.setProjectname("支付平台");
    		task.setTaskid("5172");
    		String personList=service.runtask(task);
    		System.out.println(personList);
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
}
