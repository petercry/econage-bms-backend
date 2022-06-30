package com.econage.extend.modular.bms.project.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.entity.BasicEntity;

import java.time.LocalDateTime;

@TableDef("bms_project_master")
public class BmsProjectForImportEntity implements BasicEntity {
    private String id;
    private String baId;
    private boolean valid;
    private long orderSeq;
    private String projectName;
    private String comments;
    private String priority;
    private String projectType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaId() {
        return baId;
    }

    public void setBaId(String baId) {
        this.baId = baId;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
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

    public String getInvoicedPct() {
        return invoicedPct;
    }

    public void setInvoicedPct(String invoicedPct) {
        this.invoicedPct = invoicedPct;
    }

    public String getNextPaymtPct() {
        return nextPaymtPct;
    }

    public void setNextPaymtPct(String nextPaymtPct) {
        this.nextPaymtPct = nextPaymtPct;
    }

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

    private String projectNature;
    private String projectPhase;
    private String productType;
    private String startDate;
    private String projectStatus;

    public LocalDateTime getNextPaymtDate() {
        return nextPaymtDate;
    }

    public void setNextPaymtDate(LocalDateTime nextPaymtDate) {
        this.nextPaymtDate = nextPaymtDate;
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

    private String location;
    private String contractAmt;
    private String invoicedPct;
    private String nextPaymtPct;

    private LocalDateTime nextPaymtDate;
    private String nextPaymtCond;
    private String receivedPaymtPct;
    private String receivedPaymtAmt;
    private String restPaymtAmt;


    private LocalDateTime createDate;
    private LocalDateTime modDate;
    private String createUser;
    private String modUser;
}
