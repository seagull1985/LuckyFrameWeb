package com.luckyframe.rc;
import com.luckyframe.rc.entity.ElementAction;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ReadTxt {
    //存放控件集合
    ArrayList<ElementAction> elementActionArrayList;
    //剪贴板字符串
    String clipStr = "";

    public void getStrFromClip(){
            Clipboard clipct = Toolkit.getDefaultToolkit().getSystemClipboard();
            // 获取剪切板中的内容
            Transferable clipTf= clipct.getContents(null);
            if (clipTf!=null)
            {
                // 检查内容是否是文本类型
                if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    try {
                        clipStr = (String) clipTf.getTransferData(DataFlavor.stringFlavor);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(clipStr);
    }

    /**
     *
     * @param file 录制好的app自动化测试脚本文件
     * @return
     * @throws IOException
     */
    public List<ElementAction> Extract(MultipartFile file) throws IOException {
        //初始化
        elementActionArrayList=new ArrayList<>();
        //读取脚本
        if (file.isEmpty() || file.getSize() <= 0) {
            file = null;
        }
        BufferedReader reader=new BufferedReader(new InputStreamReader(file.getInputStream(),"utf-8"));
        String line;
        //按行读取，如果还没有读到尾，一直执行
        while ((line= reader.readLine())!=null){
            //如果读到的这行是获取控件
            if(line.contains("findElementBy")) {
                if (!line.contains("//")) {
                    ElementAction elementAction = new ElementAction();

                    //按格式截取相应的值，存入集合
                    int index = line.indexOf("findElement");

                    String way;
                    //判断获取控件的方式
                    if (line.substring(index, line.indexOf("\"") - 1).contains("Id"))
                        way = "id";
                    else
                        way = "xpath";

                    way = way + "=" + line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));


                    elementAction.setAccess(way);
                    //获取控件之后必为操作
                    line = reader.readLine();
                    elementAction.setAction(line.substring(line.indexOf(".") + 1, line.indexOf("(")));

                    if (line.contains("\""))
                        elementAction.setActionValue(line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\"")));
                    elementActionArrayList.add(elementAction);

                }
            }
            //如果读到这行是滑动操作
            else if(line.contains("(new TouchAction(driver))")) {
                line =reader.readLine();
                if(line.contains(".press"))
                {
                    String actionValue="";

                    actionValue=actionValue+line.substring(line.indexOf(":")+2,line.indexOf(",")+1)+line.substring(line.lastIndexOf(":")+2,line.lastIndexOf("}"))+"|";

                    line =reader.readLine();
                    if(line.contains(".moveto"))
                    {
                        line=line.substring(line.indexOf(":")+2);


                        actionValue=actionValue+line.substring(0,line.indexOf(":"))+","+line.substring(line.lastIndexOf(":")+2,line.lastIndexOf("}"));

                        ElementAction elementAction=new ElementAction();
                        elementAction.setAction("moveto");
                        elementAction.setActionValue(actionValue);
                        elementActionArrayList.add(elementAction);
                    }
                }
            }
        }
        for (ElementAction elementAction : elementActionArrayList) {
            System.out.println(elementAction);
        }

        reader.close();

        return elementActionArrayList;
    }

}
