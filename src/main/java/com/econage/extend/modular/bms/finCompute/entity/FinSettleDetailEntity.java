package com.econage.extend.modular.bms.finCompute.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;

@TableDef("bms_financial_settle_detail")
public class FinSettleDetailEntity extends BaseEntity {
    private String id;
    @TableField(defaultUpdate = false)
    private Long orderSeq;

    private String finSettleId;//结算ID
    private String distributeUserId;//分配对应人员ID
    private Double distributeAmt;//分配对应金额
    private Double distributePct;//提成点数
    private Double workContributionPct;//工作贡献度
    private String workContributionType;//工作贡献类型
    private String comments;//备注

    @TableField(exist = false)
    private Double alreadyPayoffAmt;

    @TableField(exist = false)
    private Double restPayoffAmt;

    public Double getAlreadyPayoffAmt() {
        return alreadyPayoffAmt;
    }

    public void setAlreadyPayoffAmt(Double alreadyPayoffAmt) {
        this.alreadyPayoffAmt = alreadyPayoffAmt;
    }

    public Double getRestPayoffAmt() {
        return restPayoffAmt;
    }

    public void setRestPayoffAmt(Double restPayoffAmt) {
        this.restPayoffAmt = restPayoffAmt;
    }

    public String getWorkContributionType() {
        return workContributionType;
    }

    public void setWorkContributionType(String workContributionType) {
        this.workContributionType = workContributionType;
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

    public String getFinSettleId() {
        return finSettleId;
    }

    public void setFinSettleId(String finSettleId) {
        this.finSettleId = finSettleId;
    }

    public String getDistributeUserId() {
        return distributeUserId;
    }

    public void setDistributeUserId(String distributeUserId) {
        this.distributeUserId = distributeUserId;
    }

    public Double getDistributeAmt() {
        return distributeAmt;
    }

    public void setDistributeAmt(Double distributeAmt) {
        this.distributeAmt = distributeAmt;
    }

    public Double getDistributePct() {
        return distributePct;
    }

    public void setDistributePct(Double distributePct) {
        this.distributePct = distributePct;
    }

    public Double getWorkContributionPct() {
        return workContributionPct;
    }

    public void setWorkContributionPct(Double workContributionPct) {
        this.workContributionPct = workContributionPct;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
