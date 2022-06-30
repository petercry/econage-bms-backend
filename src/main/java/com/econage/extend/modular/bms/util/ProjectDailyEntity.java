package com.econage.extend.modular.bms.util;

public class ProjectDailyEntity {
    private String projectId;   //项目ID
    private String projectName; //项目名称
    private String dailyTitle; //标题
    private String fillUser; //填报人
    private String fillDate; //填报日期
    private String totalStatus; //总体状态
    private String currPhase; //当前阶段
    private String nextPayStep; //下一款项节点
    private String nextPayDate; //款项预计时间
    private String todayResults; //今日成果
    private String tomorrowPlans; //明日计划
    private String needSupportStuffs; //需支持事项
    private String problems; //问题或风险

    public String getFillUser() {
        return fillUser;
    }

    public void setFillUser(String fillUser) {
        this.fillUser = fillUser;
    }

    public String getTotalStatus() {
        return totalStatus;
    }

    public void setTotalStatus(String totalStatus) {
        this.totalStatus = totalStatus;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDailyTitle() {
        return dailyTitle;
    }

    public void setDailyTitle(String dailyTitle) {
        this.dailyTitle = dailyTitle;
    }

    public String getFillDate() {
        return fillDate;
    }

    public void setFillDate(String fillDate) {
        this.fillDate = fillDate;
    }

    public String getCurrPhase() {
        return currPhase;
    }

    public void setCurrPhase(String currPhase) {
        this.currPhase = currPhase;
    }

    public String getNextPayStep() {
        return nextPayStep;
    }

    public void setNextPayStep(String nextPayStep) {
        this.nextPayStep = nextPayStep;
    }

    public String getNextPayDate() {
        return nextPayDate;
    }

    public void setNextPayDate(String nextPayDate) {
        this.nextPayDate = nextPayDate;
    }

    public String getTodayResults() {
        return todayResults;
    }

    public void setTodayResults(String todayResults) {
        this.todayResults = todayResults;
    }

    public String getTomorrowPlans() {
        return tomorrowPlans;
    }

    public void setTomorrowPlans(String tomorrowPlans) {
        this.tomorrowPlans = tomorrowPlans;
    }

    public String getNeedSupportStuffs() {
        return needSupportStuffs;
    }

    public void setNeedSupportStuffs(String needSupportStuffs) {
        this.needSupportStuffs = needSupportStuffs;
    }

    public String getProblems() {
        return problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }
}
