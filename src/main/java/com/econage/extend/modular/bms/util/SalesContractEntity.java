package com.econage.extend.modular.bms.util;

import java.util.ArrayList;

public class SalesContractEntity {
    private String projectCode;//工程编号
    private String projectName;//工程名称
    private String partyA;//甲方
    private String partyB;//乙方
    private String projectLocation;//工程地点
    private String projectContent;//工程内容
    private String contractMode;//承包方式
    private String timeLimit;// 工期
    private String guaranteePeriod;//质保期
    private String qualityStandard;//质量标准
    private String contractPayMode;//合同价款方式
    private String breachLiability;//违约责任
    private String contractAmount;//合同价款
    private String contractAmountUpper;//合同价款大写

    private  Object apiFileVForAf_str;
    private ArrayList<ApiFileEntityForAf> apiFileVForAf;

    public Object getApiFileVForAf_str() {
        return apiFileVForAf_str;
    }

    public void setApiFileVForAf_str(Object apiFileVForAf_str) {
        this.apiFileVForAf_str = apiFileVForAf_str;
    }

    public ArrayList<ApiFileEntityForAf> getApiFileVForAf() {
        return apiFileVForAf;
    }

    public void setApiFileVForAf(ArrayList<ApiFileEntityForAf> apiFileVForAf) {
        this.apiFileVForAf = apiFileVForAf;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPartyA() {
        return partyA;
    }

    public void setPartyA(String partyA) {
        this.partyA = partyA;
    }

    public String getPartyB() {
        return partyB;
    }

    public void setPartyB(String partyB) {
        this.partyB = partyB;
    }

    public String getProjectLocation() {
        return projectLocation;
    }

    public void setProjectLocation(String projectLocation) {
        this.projectLocation = projectLocation;
    }

    public String getProjectContent() {
        return projectContent;
    }

    public void setProjectContent(String projectContent) {
        this.projectContent = projectContent;
    }

    public String getContractMode() {
        return contractMode;
    }

    public void setContractMode(String contractMode) {
        this.contractMode = contractMode;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getGuaranteePeriod() {
        return guaranteePeriod;
    }

    public void setGuaranteePeriod(String guaranteePeriod) {
        this.guaranteePeriod = guaranteePeriod;
    }

    public String getQualityStandard() {
        return qualityStandard;
    }

    public void setQualityStandard(String qualityStandard) {
        this.qualityStandard = qualityStandard;
    }

    public String getContractPayMode() {
        return contractPayMode;
    }

    public void setContractPayMode(String contractPayMode) {
        this.contractPayMode = contractPayMode;
    }

    public String getBreachLiability() {
        return breachLiability;
    }

    public void setBreachLiability(String breachLiability) {
        this.breachLiability = breachLiability;
    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getContractAmountUpper() {
        return contractAmountUpper;
    }

    public void setContractAmountUpper(String contractAmountUpper) {
        this.contractAmountUpper = contractAmountUpper;
    }
}
