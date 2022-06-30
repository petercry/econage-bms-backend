package com.econage.extend.modular.projectwh88.entity;


import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.entity.BasicEntity;

import java.time.LocalDateTime;

@TableDef("bms_project_info")
public class ProjectWh88Entity implements BasicEntity {
    private String id;
    //private String baId;
    //private boolean valid;
    private long orderSeq;
    private String projectName;
    private String comments;
    private String priority;
    private String projectType;
    private String projectNature;
    private String projectPhase;
    private String productType;

    public LocalDateTime getNextPaymtDate() {
        return nextPaymtDate;
    }

    public void setNextPaymtDate(LocalDateTime nextPaymtDate) {
        this.nextPaymtDate = nextPaymtDate;
    }

    private String startDate;
    private String projectStatus;
    private String location;
    private String contractAmt;

    private LocalDateTime nextPaymtDate;
    private String nextPaymtCond;
    private String receivedPaymtPct;
    private String receivedPaymtAmt;
    private String restPaymtAmt;

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getModDate() {
        return modDate;
    }

    public void setModDate(LocalDateTime modDate) {
        this.modDate = modDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModUser() {
        return modUser;
    }

    public void setModUser(String modUser) {
        this.modUser = modUser;
    }

    private String invoicedPct;
    private String nextPaymtPct;
    private LocalDateTime createDate;
    private LocalDateTime modDate;
    private String createUser;
    private String modUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(long orderSeq) {
        this.orderSeq = orderSeq;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectNature() {
        return projectNature;
    }

    public void setProjectNature(String projectNature) {
        this.projectNature = projectNature;
    }

    public String getProjectPhase() {
        return projectPhase;
    }

    public void setProjectPhase(String projectPhase) {
        this.projectPhase = projectPhase;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public String getInvoicedPct() {
        return invoicedPct;
    }

    public void setInvoicedPct(String invoicedPct) {
        this.invoicedPct = invoicedPct;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContractAmt() {
        return contractAmt;
    }

    public void setContractAmt(String contractAmt) {
        this.contractAmt = contractAmt;
    }

    public String getNextPaymtPct() {
        return nextPaymtPct;
    }

    public void setNextPaymtPct(String nextPaymtPct) {
        this.nextPaymtPct = nextPaymtPct;
    }

    public String getNextPaymtCond() {
        return nextPaymtCond;
    }

    public void setNextPaymtCond(String nextPaymtCond) {
        this.nextPaymtCond = nextPaymtCond;
    }

    public String getReceivedPaymtPct() {
        return receivedPaymtPct;
    }

    public void setReceivedPaymtPct(String receivedPaymtPct) {
        this.receivedPaymtPct = receivedPaymtPct;
    }

    public String getReceivedPaymtAmt() {
        return receivedPaymtAmt;
    }

    public void setReceivedPaymtAmt(String receivedPaymtAmt) {
        this.receivedPaymtAmt = receivedPaymtAmt;
    }

    public String getRestPaymtAmt() {
        return restPaymtAmt;
    }

    public void setRestPaymtAmt(String restPaymtAmt) {
        this.restPaymtAmt = restPaymtAmt;
    }



}
