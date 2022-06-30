package com.econage.extend.modular.bms.afhwc.entity.dto;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class AfhwcDriveTaskRequestDTO {
    private Integer flowTypeId;
    private String reqDataId;
    private String taskDataId;
    private String taskId;
    private String decisionId;
    private String decision;
    private Boolean wakeUp;
    private ObjectNode itemParams;
    private ObjectNode fileParams;

    public ObjectNode getFileParams() {
        return fileParams;
    }

    public void setFileParams(ObjectNode fileParams) {
        this.fileParams = fileParams;
    }

    public ObjectNode getItemParams() {
        return itemParams;
    }

    public void setItemParams(ObjectNode itemParams) {
        this.itemParams = itemParams;
    }

    public Integer getFlowTypeId() {
        return flowTypeId;
    }

    public void setFlowTypeId(Integer flowTypeId) {
        this.flowTypeId = flowTypeId;
    }

    public String getReqDataId() {
        return reqDataId;
    }

    public void setReqDataId(String reqDataId) {
        this.reqDataId = reqDataId;
    }

    public String getTaskDataId() {
        return taskDataId;
    }

    public void setTaskDataId(String taskDataId) {
        this.taskDataId = taskDataId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDecisionId() {
        return decisionId;
    }

    public void setDecisionId(String decisionId) {
        this.decisionId = decisionId;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public Boolean getWakeUp() {
        return wakeUp;
    }

    public void setWakeUp(Boolean wakeUp) {
        this.wakeUp = wakeUp;
    }

}
