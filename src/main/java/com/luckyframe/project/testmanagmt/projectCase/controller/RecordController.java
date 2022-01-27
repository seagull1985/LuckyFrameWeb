package com.luckyframe.project.testmanagmt.projectCase.controller;

import com.luckyframe.common.utils.poi.ExcelUtil;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseSteps;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseService;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseStepsService;
import com.luckyframe.project.testmanagmt.projectCaseModule.domain.ProjectCaseModule;
import com.luckyframe.rc.ReadTxt;
import com.luckyframe.rc.Readfile;
import com.luckyframe.rc.entity.ElementAction;
import com.luckyframe.rc.entity.RcWebCaseSteps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author lifengyang
 * @Date 2021-11-05
 * @Version 2.0
 */
@Slf4j
@RestController
@RequestMapping("/testmanagmt/RecordController")
public class RecordController {

    @Autowired
    private IProjectCaseStepsService projectCaseStepsService;

    /**
     * 脚本录入插入数据
     * StepType默认类型 0 HTTP接口 1 Web UI 2 API驱动  3移动端
     */
    @PostMapping(value = "/insert",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity insertRecord(@RequestParam("file") MultipartFile file,@RequestParam("projectId") int ProjectId,@RequestParam("caseId") int CaseId,@RequestParam("stepType") int StepType) throws Exception {
        log.info("StepType:"+StepType);
        if (file.getOriginalFilename().toLowerCase().endsWith(".xlsx")){
            ExcelUtil<ProjectCaseSteps> util = new ExcelUtil<>(ProjectCaseSteps.class);
            List<ProjectCaseSteps> caseStepsList = util.importExcel(file.getInputStream());
            int stepSerialNumber=1;
            log.info("开始插入步骤");
            for(ProjectCaseSteps projectCaseSteps:caseStepsList){
                projectCaseSteps.setProjectId(ProjectId);
                projectCaseSteps.setCaseId(CaseId);
                projectCaseSteps.setStepSerialNumber(stepSerialNumber);
                projectCaseSteps.setCreateTime(new Date());
                log.info("第"+stepSerialNumber+"步骤为：\n"+projectCaseSteps);
                projectCaseStepsService.insertProjectCaseSteps(projectCaseSteps);
                stepSerialNumber++;
            }
        }else {
            if (StepType==3){
                //解析app自动化测试录制的脚本
                ReadTxt readTxt = new ReadTxt();
                List<ElementAction> stepList = readTxt.Extract(file);
                for (ElementAction elementAction : stepList) {
                    log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+ elementAction);
                }
                List<ProjectCaseSteps> listSteps = new ArrayList<>();
                for (ElementAction elementAction : stepList) {
                    ProjectCaseSteps projectCaseSteps = new ProjectCaseSteps();
                    projectCaseSteps.setCaseId(CaseId);
                    projectCaseSteps.setProjectId(ProjectId);
                    projectCaseSteps.setStepPath(elementAction.getAccess());
                    projectCaseSteps.setStepParameters(elementAction.getActionValue());
                    projectCaseSteps.setStepOperation(elementAction.getAction());
                    projectCaseSteps.setStepType(StepType);
                    projectCaseSteps.setCreateBy("admin");
                    projectCaseSteps.setCreateTime(new Date());
                    listSteps.add(projectCaseSteps);
                }
                int stepSerialNumber=1;
                for(ProjectCaseSteps projectCaseSteps:listSteps){
                    projectCaseSteps.setStepSerialNumber(stepSerialNumber);
                    log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~2"+projectCaseSteps);
                    projectCaseStepsService.insertProjectCaseSteps(projectCaseSteps);
                    stepSerialNumber++;
                }
            }else if(StepType==1){
                //解析web自动化测试录制的脚本
                Readfile Readfile = new Readfile();
                LinkedList<RcWebCaseSteps> stepList = Readfile.saveAction(file);
                for (RcWebCaseSteps rcWebCaseSteps : stepList) {
                    log.info("设置包|定位路径:"+ rcWebCaseSteps.getStepPath());
                }
                List<ProjectCaseSteps> listSteps = new ArrayList<>();
                for (RcWebCaseSteps rcWebCaseSteps : stepList) {
                    ProjectCaseSteps projectCaseSteps = new ProjectCaseSteps();
                    projectCaseSteps.setCaseId(CaseId);
                    projectCaseSteps.setProjectId(ProjectId);
                    projectCaseSteps.setStepPath(rcWebCaseSteps.getStepPath());
                    projectCaseSteps.setStepParameters(rcWebCaseSteps.getStepParameters());
                    projectCaseSteps.setStepOperation(rcWebCaseSteps.getStepOperation());
                    projectCaseSteps.setStepType(StepType);
                    projectCaseSteps.setCreateBy("admin");
                    projectCaseSteps.setCreateTime(new Date());
                    listSteps.add(projectCaseSteps);
                }
                int stepSerialNumber=1;
                log.info("开始插入步骤");
                for(ProjectCaseSteps projectCaseSteps:listSteps){
                    projectCaseSteps.setStepSerialNumber(stepSerialNumber);
                    log.info("第"+stepSerialNumber+"步骤为：\n"+projectCaseSteps);
                    projectCaseStepsService.insertProjectCaseSteps(projectCaseSteps);
                    stepSerialNumber++;
                }
            }
        }


        return ResponseEntity.ok().build();
    }

}
