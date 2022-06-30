package com.econage.extend.modular.bms.afhwc.entity.model;


import java.util.ArrayList;

public class AfhwcWorkflowInstTaskModel {
    private String taskName;
    private String taskId;
    private Integer taskStatus;
    private Integer approveType;
    private ArrayList<AfhwcWorkflowInstTaskAssigneeModel> assigneeList;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getApproveType() {
        return approveType;
    }

    public void setApproveType(Integer approveType) {
        this.approveType = approveType;
    }

    public ArrayList<AfhwcWorkflowInstTaskAssigneeModel> getAssigneeList() {
        return assigneeList;
    }

    public void setAssigneeList(ArrayList<AfhwcWorkflowInstTaskAssigneeModel> assigneeList) {
        this.assigneeList = assigneeList;
    }
}
