package com.econage.extend.modular.bms.project.entity;

import com.econage.extend.modular.bms.ba.entity.BmsBaEntity;
import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;

import java.time.LocalDate;
import java.util.List;

@TableDef("bms_project_master")
public class BmsProjectEntity extends BaseEntity {
    private String id;
    private String baId;
    @TableField(defaultUpdate = false)
    private Boolean valid;
    @TableField(defaultUpdate = false)
    private Long orderSeq;
    private String projectName;
    private String comments;
    private String priority;
    private String projectType;
    private String projectNature;
    private String projectPhase;
    private String productType;
    private String startDate;
    private String projectStatus;
    private String location;
    private String contractAmt;
    private String invoicedPct;
    private String nextPaymtPct;

    private LocalDate nextPaymtDate;
    private String nextPaymtCond;
    private String receivedPaymtPct;
    private String receivedPaymtAmt;
    private String restPaymtAmt;

    private Boolean productFocusFlag;

    @TableField(exist = false)
    private String productFocusIdForAf;

    private String devPlatformUrl;
    private String devPlatformUserid;
    private String devPlatformPwd;
    private String devPlatformDbIp;
    private String devPlatformDbSid;
    private String devVersionLib;
    private String devComments;
    private String salesManager;
    @TableField(exist = false)
    private String salesManagerName;

    @TableField(exist = false)
    private String createUserName;
    @TableField(exist = false)
    private String projectPhaseName;

    @TableField(exist = false)
    private String customerName;

    private Boolean activeFlag;
    private String dingGroupAccessToken;
    private String dingGroupSecret;

    private LocalDate finCloseDate;

    @TableField(exist = false)
    private Double taxSum;

    private String customerDesc; //客户全称
    private String contractNo; //合同编号
    private LocalDate contractSignDate; //签约时间
    private String contractStatus; //合同状态
    private String contractNature; //合同性质(新购、增购、年度服务费、单次服务、AF新购、AF复购)
    private LocalDate contractValidDateFrom; //合同有效期起始日
    private LocalDate contractValidDateTo; //合同有效期终止日
    private String totalStatus; //项目整体状态(进行中、已验收、已完结)

    private String bidHitDocFileId;//中标通知书文件
    private String contractDocFileId;//合同文件
    private String checkAcceptDocFileId;//验收单文件

    public String getBidHitDocFileId() {
        return bidHitDocFileId;
    }

    public void setBidHitDocFileId(String bidHitDocFileId) {
        this.bidHitDocFileId = bidHitDocFileId;
    }

    public String getContractDocFileId() {
        return contractDocFileId;
    }

    public void setContractDocFileId(String contractDocFileId) {
        this.contractDocFileId = contractDocFileId;
    }

    public String getCheckAcceptDocFileId() {
        return checkAcceptDocFileId;
    }

    public void setCheckAcceptDocFileId(String checkAcceptDocFileId) {
        this.checkAcceptDocFileId = checkAcceptDocFileId;
    }

    public String getCustomerDesc() {
        return customerDesc;
    }

    public void setCustomerDesc(String customerDesc) {
        this.customerDesc = customerDesc;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public LocalDate getContractSignDate() {
        return contractSignDate;
    }

    public void setContractSignDate(LocalDate contractSignDate) {
        this.contractSignDate = contractSignDate;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getContractNature() {
        return contractNature;
    }

    public void setContractNature(String contractNature) {
        this.contractNature = contractNature;
    }

    public LocalDate getContractValidDateFrom() {
        return contractValidDateFrom;
    }

    public void setContractValidDateFrom(LocalDate contractValidDateFrom) {
        this.contractValidDateFrom = contractValidDateFrom;
    }

    public LocalDate getContractValidDateTo() {
        return contractValidDateTo;
    }

    public void setContractValidDateTo(LocalDate contractValidDateTo) {
        this.contractValidDateTo = contractValidDateTo;
    }

    public String getTotalStatus() {
        return totalStatus;
    }

    public void setTotalStatus(String totalStatus) {
        this.totalStatus = totalStatus;
    }

    public Double getTaxSum() {
        return taxSum;
    }

    public void setTaxSum(Double taxSum) {
        this.taxSum = taxSum;
    }

    public LocalDate getFinCloseDate() {
        return finCloseDate;
    }

    public void setFinCloseDate(LocalDate finCloseDate) {
        this.finCloseDate = finCloseDate;
    }

    @TableField(exist = false)
    private List<String> relatedBaIds;

    public List<String> getRelatedBaIds() {
        return relatedBaIds;
    }

    public void setRelatedBaIds(List<String> relatedBaIds) {
        this.relatedBaIds = relatedBaIds;
    }

    @TableField(exist = false)
    private List<BmsBaEntity> relatedBaList;

    public List<BmsBaEntity> getRelatedBaList() {
        return relatedBaList;
    }

    public void setRelatedBaList(List<BmsBaEntity> relatedBaList) {
        this.relatedBaList = relatedBaList;
    }

    public String getDingGroupAccessToken() {
        return dingGroupAccessToken;
    }

    public void setDingGroupAccessToken(String dingGroupAccessToken) {
        this.dingGroupAccessToken = dingGroupAccessToken;
    }

    public String getDingGroupSecret() {
        return dingGroupSecret;
    }

    public void setDingGroupSecret(String dingGroupSecret) {
        this.dingGroupSecret = dingGroupSecret;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductFocusIdForAf() {
        return productFocusIdForAf;
    }

    public void setProductFocusIdForAf(String productFocusIdForAf) {
        this.productFocusIdForAf = productFocusIdForAf;
    }

    public String getSalesManager() {
        return salesManager;
    }

    public void setSalesManager(String salesManager) {
        this.salesManager = salesManager;
    }

    public String getSalesManagerName() {
        return salesManagerName;
    }

    public void setSalesManagerName(String salesManagerName) {
        this.salesManagerName = salesManagerName;
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

    public LocalDate getNextPaymtDate() {
        return nextPaymtDate;
    }

    public void setNextPaymtDate(LocalDate nextPaymtDate) {
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

    public Boolean getProductFocusFlag() {
        return productFocusFlag;
    }

    public void setProductFocusFlag(Boolean productFocusFlag) {
        this.productFocusFlag = productFocusFlag;
    }

    public String getDevPlatformUrl() {
        return devPlatformUrl;
    }

    public void setDevPlatformUrl(String devPlatformUrl) {
        this.devPlatformUrl = devPlatformUrl;
    }

    public String getDevPlatformUserid() {
        return devPlatformUserid;
    }

    public void setDevPlatformUserid(String devPlatformUserid) {
        this.devPlatformUserid = devPlatformUserid;
    }

    public String getDevPlatformPwd() {
        return devPlatformPwd;
    }

    public void setDevPlatformPwd(String devPlatformPwd) {
        this.devPlatformPwd = devPlatformPwd;
    }

    public String getDevPlatformDbIp() {
        return devPlatformDbIp;
    }

    public void setDevPlatformDbIp(String devPlatformDbIp) {
        this.devPlatformDbIp = devPlatformDbIp;
    }

    public String getDevPlatformDbSid() {
        return devPlatformDbSid;
    }

    public void setDevPlatformDbSid(String devPlatformDbSid) {
        this.devPlatformDbSid = devPlatformDbSid;
    }

    public String getDevVersionLib() {
        return devVersionLib;
    }

    public void setDevVersionLib(String devVersionLib) {
        this.devVersionLib = devVersionLib;
    }

    public String getDevComments() {
        return devComments;
    }

    public void setDevComments(String devComments) {
        this.devComments = devComments;
    }

    public String getProjectPhase() {
        return projectPhase;
    }

    public void setProjectPhase(String projectPhase) {
        this.projectPhase = projectPhase;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getProjectPhaseName() {
        return projectPhaseName;
    }

    public void setProjectPhaseName(String projectPhaseName) {
        this.projectPhaseName = projectPhaseName;
    }

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

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Long getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(Long orderSeq) {
        this.orderSeq = orderSeq;
    }
}
