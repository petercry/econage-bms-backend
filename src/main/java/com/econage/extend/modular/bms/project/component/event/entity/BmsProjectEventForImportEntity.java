package com.econage.extend.modular.bms.project.component.event.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.entity.BasicEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@TableDef("bms_project_event")
public class BmsProjectEventForImportEntity implements BasicEntity {
    private String id;
    private String projectId;
    private Integer categoryId;//1:项目阶段;2:项目里程碑;3:进度检查;4:财务概要;5:收付款历史记录;6:项目事件;7:费用报销
    private LocalDateTime createDate;
    private String createUser;
    private LocalDateTime modDate;
    private String  modUser;
    private String orderSeq;
    private boolean valid;
    private LocalDate startDate;//在"项目阶段"中，是"开始时间"
    private LocalDate endDate;//在"项目阶段"中，是"结束时间"
    private String stage;//在"收付款历史记录"中，是"种类"
    private String recordPerson;//在"项目阶段"中，是"记录人员"
    private LocalDateTime recordDate;//在"项目阶段"中，是"记录时间"
    private String milestone;//在“里程碑”中，是“里程碑”
    private LocalDateTime checkDate;//在"进度检查"中，是"检查时间"
    private String statusId;//在"进度检查"中，是"进度状况"
    private String delayType;//在"进度检查"中，是"滞后原因"
    private String bottleneck;//在"进度检查"中，是"主要瓶颈"
    private String comments;
    private String execEval;//在"进度检查"中，是"上次工作评价"
    private String execScore;//在"进度检查"中，是"上次工作评分"
    private String nextPlan;//在"进度检查"中，是"工作计划"
    private String nextAssignee;//在"进度检查"中，是"负责人"
    private LocalDate targetDate;//在"进度检查"中，是"要求完成时间"
    private LocalDate paymtDate;
    private String paymtType;
    private Double paymtAmt;
    private String typeId;//在"项目事件"中，是"联系方式"
    private String contactPerson;//在"项目事件"中，是"客户联系人"
    private LocalDateTime actionDate;//在"项目事件"中，是"时间"
    private String actionUser;//在"项目事件"中，是"经办方"
    private String subject;//在"项目事件"中，是"内容"

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public LocalDateTime getModDate() {
        return modDate;
    }

    public void setModDate(LocalDateTime modDate) {
        this.modDate = modDate;
    }

    public String getModUser() {
        return modUser;
    }

    public void setModUser(String modUser) {
        this.modUser = modUser;
    }

    public String getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getRecordPerson() {
        return recordPerson;
    }

    public void setRecordPerson(String recordPerson) {
        this.recordPerson = recordPerson;
    }

    public LocalDateTime getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDateTime recordDate) {
        this.recordDate = recordDate;
    }

    public String getMilestone() {
        return milestone;
    }

    public void setMilestone(String milestone) {
        this.milestone = milestone;
    }

    public LocalDateTime getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(LocalDateTime checkDate) {
        this.checkDate = checkDate;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getDelayType() {
        return delayType;
    }

    public void setDelayType(String delayType) {
        this.delayType = delayType;
    }

    public String getBottleneck() {
        return bottleneck;
    }

    public void setBottleneck(String bottleneck) {
        this.bottleneck = bottleneck;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getExecEval() {
        return execEval;
    }

    public void setExecEval(String execEval) {
        this.execEval = execEval;
    }

    public String getExecScore() {
        return execScore;
    }

    public void setExecScore(String execScore) {
        this.execScore = execScore;
    }

    public String getNextPlan() {
        return nextPlan;
    }

    public void setNextPlan(String nextPlan) {
        this.nextPlan = nextPlan;
    }

    public String getNextAssignee() {
        return nextAssignee;
    }

    public void setNextAssignee(String nextAssignee) {
        this.nextAssignee = nextAssignee;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public LocalDate getPaymtDate() {
        return paymtDate;
    }

    public void setPaymtDate(LocalDate paymtDate) {
        this.paymtDate = paymtDate;
    }

    public String getPaymtType() {
        return paymtType;
    }

    public void setPaymtType(String paymtType) {
        this.paymtType = paymtType;
    }

    public Double getPaymtAmt() {
        return paymtAmt;
    }

    public void setPaymtAmt(Double paymtAmt) {
        this.paymtAmt = paymtAmt;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public LocalDateTime getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDateTime actionDate) {
        this.actionDate = actionDate;
    }

    public String getActionUser() {
        return actionUser;
    }

    public void setActionUser(String actionUser) {
        this.actionUser = actionUser;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
