package com.econage.extend.modular.bms.finCompute.entity;

import com.flowyun.cornerstone.db.mybatis.entity.BasicEntity;

import java.time.LocalDate;

public class ProjectFinComputeEntity implements BasicEntity {
    private String idx;
    private String projectName;//项目名称
    private String contractAmt;//合同金额
    private String receivedPaymtAmt;//已收款金额
    private String receivedPaymtPct;//已收款比例
    private String restPaymtAmt;//待回款金额
    private Double expenseSum;//已耗费成本

    private LocalDate currPaymentDate; //本次到款时间
    private String currPaymentType;//本次到款类型
    private String currPaymentTypeDesc;
    private Double currPaymentAmt;//本次到款金额
    private String currTaxType;//税费类别
    private String currTaxTypeDesc;
    private Double valueAddedTax;//增值税
    private Double superTax;//附加税
    private Double stampTax;//印花税

    private Double reimburseFeeExpense;//报销流程支出
    private Double payFeeExpense;//付款申请流程支出
    private Double advanceFeeExpense;//费用预支流程支出
    private Double bizFeeExpense;//商务费用申请流程支出
    private Double taxExpense;//税费支出

    private Boolean existPaymentBefore;//在结算时间点前是否有到款

    private String projectId;
    private String paymentEventId;

    private String paymentComments;

    public String getPaymentComments() {
        return paymentComments;
    }

    public void setPaymentComments(String paymentComments) {
        this.paymentComments = paymentComments;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPaymentEventId() {
        return paymentEventId;
    }

    public void setPaymentEventId(String paymentEventId) {
        this.paymentEventId = paymentEventId;
    }

    public Double getTaxExpense() {
        return taxExpense;
    }

    public void setTaxExpense(Double taxExpense) {
        this.taxExpense = taxExpense;
    }

    public Double getReimburseFeeExpense() {
        return reimburseFeeExpense;
    }

    public void setReimburseFeeExpense(Double reimburseFeeExpense) {
        this.reimburseFeeExpense = reimburseFeeExpense;
    }

    public Double getPayFeeExpense() {
        return payFeeExpense;
    }

    public void setPayFeeExpense(Double payFeeExpense) {
        this.payFeeExpense = payFeeExpense;
    }

    public Double getAdvanceFeeExpense() {
        return advanceFeeExpense;
    }

    public void setAdvanceFeeExpense(Double advanceFeeExpense) {
        this.advanceFeeExpense = advanceFeeExpense;
    }

    public Double getBizFeeExpense() {
        return bizFeeExpense;
    }

    public void setBizFeeExpense(Double bizFeeExpense) {
        this.bizFeeExpense = bizFeeExpense;
    }

    public String getCurrPaymentTypeDesc() {
        return currPaymentTypeDesc;
    }

    public void setCurrPaymentTypeDesc(String currPaymentTypeDesc) {
        this.currPaymentTypeDesc = currPaymentTypeDesc;
    }

    public String getCurrTaxTypeDesc() {
        return currTaxTypeDesc;
    }

    public void setCurrTaxTypeDesc(String currTaxTypeDesc) {
        this.currTaxTypeDesc = currTaxTypeDesc;
    }

    public LocalDate getCurrPaymentDate() {
        return currPaymentDate;
    }

    public void setCurrPaymentDate(LocalDate currPaymentDate) {
        this.currPaymentDate = currPaymentDate;
    }

    public String getCurrPaymentType() {
        return currPaymentType;
    }

    public void setCurrPaymentType(String currPaymentType) {
        this.currPaymentType = currPaymentType;
    }

    public Double getCurrPaymentAmt() {
        return currPaymentAmt;
    }

    public void setCurrPaymentAmt(Double currPaymentAmt) {
        this.currPaymentAmt = currPaymentAmt;
    }

    public String getCurrTaxType() {
        return currTaxType;
    }

    public void setCurrTaxType(String currTaxType) {
        this.currTaxType = currTaxType;
    }

    public Double getValueAddedTax() {
        return valueAddedTax;
    }

    public void setValueAddedTax(Double valueAddedTax) {
        this.valueAddedTax = valueAddedTax;
    }

    public Double getSuperTax() {
        return superTax;
    }

    public void setSuperTax(Double superTax) {
        this.superTax = superTax;
    }

    public Double getStampTax() {
        return stampTax;
    }

    public void setStampTax(Double stampTax) {
        this.stampTax = stampTax;
    }

    public Boolean getExistPaymentBefore() {
        return existPaymentBefore;
    }

    public void setExistPaymentBefore(Boolean existPaymentBefore) {
        this.existPaymentBefore = existPaymentBefore;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getContractAmt() {
        return contractAmt;
    }

    public void setContractAmt(String contractAmt) {
        this.contractAmt = contractAmt;
    }

    public String getReceivedPaymtAmt() {
        return receivedPaymtAmt;
    }

    public void setReceivedPaymtAmt(String receivedPaymtAmt) {
        this.receivedPaymtAmt = receivedPaymtAmt;
    }

    public String getReceivedPaymtPct() {
        return receivedPaymtPct;
    }

    public void setReceivedPaymtPct(String receivedPaymtPct) {
        this.receivedPaymtPct = receivedPaymtPct;
    }

    public String getRestPaymtAmt() {
        return restPaymtAmt;
    }

    public void setRestPaymtAmt(String restPaymtAmt) {
        this.restPaymtAmt = restPaymtAmt;
    }

    public Double getExpenseSum() {
        return expenseSum;
    }

    public void setExpenseSum(Double expenseSum) {
        this.expenseSum = expenseSum;
    }
}
