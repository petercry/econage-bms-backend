package com.econage.extend.modular.bms.finCompute.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;

import java.time.LocalDate;
import java.util.List;

@TableDef("bms_financial_settle_summary")
public class FinSettleSummaryEntity extends BaseEntity {
    private String id;
    @TableField(defaultUpdate = false)
    private Long orderSeq;

    private String projectId;//项目ID
    private LocalDate settleDate;//结算时间
    private Integer settleLevel;//结算维度(1:销售;2:实施;3:技术支持;4:AF运营;5:维保服务)
    private Double settleAmt;//结算金额
    private Double settlePct;//结算点数

    private List<FinSettlePaymentDetailEntity> settlePaymentDetails;

    private List<FinSettleDetailEntity> settleDetails;

    public Double getSettlePct() {
        return settlePct;
    }

    public void setSettlePct(Double settlePct) {
        this.settlePct = settlePct;
    }

    public List<FinSettlePaymentDetailEntity> getSettlePaymentDetails() {
        return settlePaymentDetails;
    }

    public void setSettlePaymentDetails(List<FinSettlePaymentDetailEntity> settlePaymentDetails) {
        this.settlePaymentDetails = settlePaymentDetails;
    }

    public List<FinSettleDetailEntity> getSettleDetails() {
        return settleDetails;
    }

    public void setSettleDetails(List<FinSettleDetailEntity> settleDetails) {
        this.settleDetails = settleDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(Long orderSeq) {
        this.orderSeq = orderSeq;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public LocalDate getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(LocalDate settleDate) {
        this.settleDate = settleDate;
    }

    public Integer getSettleLevel() {
        return settleLevel;
    }

    public void setSettleLevel(Integer settleLevel) {
        this.settleLevel = settleLevel;
    }

    public Double getSettleAmt() {
        return settleAmt;
    }

    public void setSettleAmt(Double settleAmt) {
        this.settleAmt = settleAmt;
    }
}
