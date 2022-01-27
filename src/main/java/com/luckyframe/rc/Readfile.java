package com.luckyframe.rc;

import com.luckyframe.rc.entity.RcWebCaseSteps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;


@Slf4j
public class Readfile {

    /**
     * 保存活动
     * @param file 读取录制好的web自动化测试脚本文件内容
     * @throws IOException
     */
    public LinkedList<RcWebCaseSteps> saveAction(MultipartFile file) throws IOException {
        LinkedList<RcWebCaseSteps> rcWebCaseStepsLinkedList=new LinkedList<>();
        if (file.isEmpty() || file.getSize() <= 0) {
            file = null;
        }
        BufferedReader reader=new BufferedReader(new InputStreamReader(file.getInputStream(),"utf-8"));
        StringBuilder StringBuilder=new StringBuilder();//用来显示内容用的StringBuilder
        String line;
        //按行读取内容，如果还没有读到尾，一直执行
        StringBuilder.append("\n");
        int n=0;
        int start = 99999999;
        int end;
        LinkedList<String> var=new LinkedList<>();
        while ((line= reader.readLine())!=null){
            StringBuilder.append(line).append("\n");
            var.add(line);
            n++;
            line=line.trim();
            if(line.startsWith("driver.get(")) {
                //打开网页
                log.info("打开网页");
                RcWebCaseSteps rcWebCaseSteps=new RcWebCaseSteps();
                rcWebCaseSteps.setStepOperation("open");
                //解析网页路径
                String url;
                url=line.substring(line.indexOf("driver.get(")+"driver.get(".length(),line.indexOf(")")).replace("\"","");
                rcWebCaseSteps.setStepParameters(url);
                rcWebCaseStepsLinkedList.add(rcWebCaseSteps);
                rcWebCaseSteps=null;
            }else if (line.startsWith("driver.findElement(")){
                //查找元素
                if (line.contains("By.id(")){
                    //通过id查找
                    log.info("通过id查找");
                    String id;
                    id=line.substring(line.indexOf("By.id(")+"By.id(".length(),line.indexOf(")")).replace("\"","");
                    log.info("id="+id);

                    RcWebCaseSteps rcWebCaseSteps=new RcWebCaseSteps();
                    rcWebCaseSteps.setStepPath("id="+id);
                    //解析操作
                    if(line.endsWith(".click();")) {
                        rcWebCaseSteps.setStepOperation("click");
                    }else if(line.contains(".sendKeys(")){
                        rcWebCaseSteps.setStepOperation("sendkeys");
                        //解析SendKeys的value
                        String param=line.substring(line.indexOf(".sendKeys(")+".sendKeys(".length(),line.indexOf(";")-1).replace("\"","");
                        rcWebCaseSteps.setStepParameters(param);
                    }
                    rcWebCaseStepsLinkedList.add(rcWebCaseSteps);
                    rcWebCaseSteps=null;
                }else if (line.contains("By.cssSelector")){
                    //通过css选择器查找
                    log.info("通过css查找");
                    String id;
                    id=line.substring(line.indexOf("By.cssSelector(")+"By.cssSelector(".length(),line.indexOf("\"",("driver.findElement(By.cssSelector(\"").length())).replace("\"","");
                    log.info("cssselector="+id);

                    RcWebCaseSteps rcWebCaseSteps=new RcWebCaseSteps();
                    rcWebCaseSteps.setStepPath("cssselector="+id);
                    //解析操作
                    if(line.endsWith(".click();")) {
                        rcWebCaseSteps.setStepOperation("click");
                    }else if(line.contains(".sendKeys(")){
                        rcWebCaseSteps.setStepOperation("sendkeys");
                        //解析SendKeys的value
                        String param=line.substring(line.indexOf(".sendKeys(")+".sendKeys(".length(),line.indexOf(";")-1).replace("\"","");
                        rcWebCaseSteps.setStepParameters(param);
                    }
                    rcWebCaseStepsLinkedList.add(rcWebCaseSteps);
                    rcWebCaseSteps=null;
                }
            }else if(line.startsWith("driver.manage().window().setSize(")){
                //设置浏览器大小
                log.info("设置浏览器大小");
                RcWebCaseSteps rcWebCaseSteps=new RcWebCaseSteps();
                rcWebCaseSteps.setStepOperation("windowsetsize");
                //解析窗口大小
                String operationValue;
                operationValue=line.substring("driver.manage().window().setSize(new Dimension(".length(),line.indexOf(")","driver.manage().window().setSize(new Dimension(".length()));
                rcWebCaseSteps.setStepParameters(operationValue);
                rcWebCaseStepsLinkedList.add(rcWebCaseSteps);
                rcWebCaseSteps=null;
            }else if(line.startsWith("driver.switchTo().window")){
                //切换窗口句柄
                log.info("切换窗口句柄");
                RcWebCaseSteps rcWebCaseSteps=new RcWebCaseSteps();
                rcWebCaseSteps.setStepOperation("switchtowindow");
                rcWebCaseStepsLinkedList.add(rcWebCaseSteps);
                rcWebCaseSteps=null;
            }else if (line.startsWith("{")){
                start=n;
            }else if(line.startsWith("}")){
                end=n;
                if (end>start){
                    for (int i=0;i<end-start;i++){
                        var.get((start-1)+i);
                    }
                    start=99999999;//初始化
                }
            }
        }
        log.info(StringBuilder.toString());
        return rcWebCaseStepsLinkedList;
    }
}
