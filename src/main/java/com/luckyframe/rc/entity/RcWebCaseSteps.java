package com.luckyframe.rc.entity;


import lombok.Getter;
import lombok.Setter;

/**
 * WEB自动化测试用例步骤
 * @author lifengyang
 */
@Getter
@Setter
public class RcWebCaseSteps {
    /** 包路径|定位路径 */
    private String stepPath;
    /** 方法名|操作 */
    private String stepOperation;
    /** 参数 */
    private String stepParameters;
    /** 步骤动作 */
    private String action;
    /** 预期结果 */
    private String expectedResult;
    /** 默认类型 0 HTTP接口 1 Web UI 2 API驱动  3移动端 */
    private Integer stepType;
    /** 扩展字段，可用于备注、存储HTTP模板等 */
    private String extend;
    /** 备注字段，用于接口类型的步骤的备注 */
    private String stepRemark;
}
