package com.luckyframe.project.testmanagmt.projectPageObject.domain;

public interface IbasePageObject {
    //系统index主页面名称
    String indexPage = "index";
    //快速查询输入框
    String input_quicksearch="input_quicksearch";
    //快速查询子输入框，点击后出现
    String input_inputforquicksearch="input_inputforquicksearch";
    //span_parentmenu
    String span_parentmenu="span_parentmenu";
    //具体的菜单栏
    String span_menu="span_menu";
    String iframe="iframe";
    /**
     获取进入当前页面方法的步骤
     */
}
