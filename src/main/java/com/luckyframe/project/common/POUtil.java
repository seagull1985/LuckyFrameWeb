package com.luckyframe.project.common;

import com.luckyframe.common.constant.Constants;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseSteps;
import com.luckyframe.project.testmanagmt.projectPageDetail.domain.ProjectPageDetail;
import com.luckyframe.project.testmanagmt.projectPageDetail.service.IProjectPageDetailService;
import com.luckyframe.project.testmanagmt.projectPageObject.domain.ProjectPageObject;
import com.luckyframe.project.testmanagmt.projectPageObject.service.IProjectPageObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@Component
public class POUtil extends BaseController {
    private static IProjectPageObjectService projectPageObjectService;

    private static IProjectPageDetailService projectPageDetailService;

    @Autowired
    private IProjectPageObjectService projectPageObjectService1;

    @Autowired
    private IProjectPageDetailService projectPageDetailService1;

    @PostConstruct
    public void init() {
        this.projectPageObjectService = this.projectPageObjectService1;
        this.projectPageDetailService = this.projectPageDetailService1;

    }

    public static String transStepPathToPo(ProjectCaseSteps projectCaseSteps) {
        String stepPath = projectCaseSteps.getStepPath();
        String[] k = stepPath.split("\\.");
        //页面名称
        String pagename = k[0];
        //元素名称
        String element = k[1];
        ProjectPageObject projectPageObject = new ProjectPageObject();
        projectPageObject.setPageName(pagename);
        projectPageObject.setProjectId(projectCaseSteps.getProjectId());
        List<ProjectPageObject> ProjectPageObjectList = projectPageObjectService.selectProjectPageObjectList(projectPageObject);
        ProjectPageDetail projectPageDetail = new ProjectPageDetail();
        projectPageDetail.setElement(element);
        projectPageDetail.setPageId(ProjectPageObjectList.get(0).getPageId());
        List<ProjectPageDetail> projectPageDetailList = projectPageDetailService.selectProjectPageDetailList(projectPageDetail);
        String pageId = String.valueOf(ProjectPageObjectList.get(0).getPageId());
        String elementId = String.valueOf(projectPageDetailList.get(0).getId());
        stepPath = pageId + "." + elementId;
        return stepPath;
    }

    public static boolean isPO(ProjectCaseSteps projectCaseSteps) {
        if (projectCaseSteps.getStepPath() != null) {
            String stepPath = projectCaseSteps.getStepPath();
            if (stepPath.contains(".")) {
                String[] pas = stepPath.split("\\.");
                if (pas.length == 2) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void transPOToStepPath(ProjectCaseSteps step) {
        try {
            int elementId = Integer.parseInt(step.getStepPath().split("\\.")[1]);
            ProjectPageDetail projectPageDetail = projectPageDetailService.selectProjectPageDetailById(elementId);
            step.setStepPath(projectPageDetail.getType() + "=" + projectPageDetail.getValue());
        } catch (Exception e) {
        }
    }
}
