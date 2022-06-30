package com.econage.extend.modular.bms.afhwc.entity.model;


import java.util.ArrayList;

public class AfhwcWorkflowInstTaskAssigneeModel {
    private String assigneeName;
    private ArrayList<AfhwcWorkflowInstTaskDecisionModel> decisionList;

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public ArrayList<AfhwcWorkflowInstTaskDecisionModel> getDecisionList() {
        return decisionList;
    }

    public void setDecisionList(ArrayList<AfhwcWorkflowInstTaskDecisionModel> decisionList) {
        this.decisionList = decisionList;
    }
}
