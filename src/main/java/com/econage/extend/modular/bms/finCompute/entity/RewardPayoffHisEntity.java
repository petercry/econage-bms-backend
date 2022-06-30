package com.econage.extend.modular.bms.finCompute.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BasicEntity;

import java.time.LocalDate;

@TableDef("bms_reward_payoff_history")
public class RewardPayoffHisEntity implements BasicEntity {
    private String id;
    @TableField(defaultUpdate = false)
    private Long orderSeq;
    private String projectId;
    private String finSettleDetId;//结算明细ID
    private String distributeUserId;//分配人员ID
    private LocalDate payoffDate;//本次发放时间
    private Double thisPayoffAmt;//本次发放金额
    private Double thisPayoffPct;//本次发放金额占比
    private String comments;//备注

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
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

    public String getFinSettleDetId() {
        return finSettleDetId;
    }

    public void setFinSettleDetId(String finSettleDetId) {
        this.finSettleDetId = finSettleDetId;
    }

    public String getDistributeUserId() {
        return distributeUserId;
    }

    public void setDistributeUserId(String distributeUserId) {
        this.distributeUserId = distributeUserId;
    }

    public LocalDate getPayoffDate() {
        return payoffDate;
    }

    public void setPayoffDate(LocalDate payoffDate) {
        this.payoffDate = payoffDate;
    }

    public Double getThisPayoffAmt() {
        return thisPayoffAmt;
    }

    public void setThisPayoffAmt(Double thisPayoffAmt) {
        this.thisPayoffAmt = thisPayoffAmt;
    }

    public Double getThisPayoffPct() {
        return thisPayoffPct;
    }

    public void setThisPayoffPct(Double thisPayoffPct) {
        this.thisPayoffPct = thisPayoffPct;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
