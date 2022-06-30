package com.econage.extend.modular.bms.afhwc.entity.model;

import java.time.LocalDateTime;

public class AfhwcWorkflowInstTaskDecisionModel {
    private String decisionId ;
    private String decisionDesc;
    private String decisionValue;
    private LocalDateTime decisionDate;
    private Integer roundNum;

    public String getDecisionId() {
        return decisionId;
    }

    public void setDecisionId(String decisionId) {
        this.decisionId = decisionId;
    }

    public String getDecisionDesc() {
        return decisionDesc;
    }

    public void setDecisionDesc(String decisionDesc) {
        this.decisionDesc = decisionDesc;
    }

    public String getDecisionValue() {
        return decisionValue;
    }

    public void setDecisionValue(String decisionValue) {
        this.decisionValue = decisionValue;
    }

    public LocalDateTime getDecisionDate() {
        return decisionDate;
    }

    public void setDecisionDate(LocalDateTime decisionDate) {
        this.decisionDate = decisionDate;
    }

    public Integer getRoundNum() {
        return roundNum;
    }

    public void setRoundNum(Integer roundNum) {
        this.roundNum = roundNum;
    }
}
