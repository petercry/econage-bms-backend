package com.econage.extend.modular.bms.afhwc.entity.model;


import java.time.LocalDateTime;
import java.util.ArrayList;

public class AfhwcWorkflowInstModel {
    private String workflowName;
    private String workflowTemplateId;
    private Integer workflowStatus;
    private String initUserName;
    private LocalDateTime initDate;
    private ArrayList<AfhwcWorkflowInstTaskModel> taskList;
    private ArrayList<AfhwcWorkflowInstItemModel> itemList;

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public String getWorkflowTemplateId() {
        return workflowTemplateId;
    }

    public void setWorkflowTemplateId(String workflowTemplateId) {
        this.workflowTemplateId = workflowTemplateId;
    }

    public Integer getWorkflowStatus() {
        return workflowStatus;
    }

    public void setWorkflowStatus(Integer workflowStatus) {
        this.workflowStatus = workflowStatus;
    }

    public String getInitUserName() {
        return initUserName;
    }

    public void setInitUserName(String initUserName) {
        this.initUserName = initUserName;
    }

    public LocalDateTime getInitDate() {
        return initDate;
    }

    public void setInitDate(LocalDateTime initDate) {
        this.initDate = initDate;
    }

    public ArrayList<AfhwcWorkflowInstTaskModel> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<AfhwcWorkflowInstTaskModel> taskList) {
        this.taskList = taskList;
    }

    public ArrayList<AfhwcWorkflowInstItemModel> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<AfhwcWorkflowInstItemModel> itemList) {
        this.itemList = itemList;
    }
}
