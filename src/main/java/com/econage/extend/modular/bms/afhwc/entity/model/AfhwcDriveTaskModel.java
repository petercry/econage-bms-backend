package com.econage.extend.modular.bms.afhwc.entity.model;

public class AfhwcDriveTaskModel {
    private String reqDataId;
    private String taskDataId;
    private String decisionId;
    private Integer reqDataVersion;
    private Object invalidMsg;
    private Object fileMap;

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

    public String getDecisionId() {
        return decisionId;
    }

    public void setDecisionId(String decisionId) {
        this.decisionId = decisionId;
    }

    public Integer getReqDataVersion() {
        return reqDataVersion;
    }

    public void setReqDataVersion(Integer reqDataVersion) {
        this.reqDataVersion = reqDataVersion;
    }

    public Object getInvalidMsg() {
        return invalidMsg;
    }

    public void setInvalidMsg(Object invalidMsg) {
        this.invalidMsg = invalidMsg;
    }

    public Object getFileMap() {
        return fileMap;
    }

    public void setFileMap(Object fileMap) {
        this.fileMap = fileMap;
    }
}
