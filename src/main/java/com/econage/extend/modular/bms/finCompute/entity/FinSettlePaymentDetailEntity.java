package com.econage.extend.modular.bms.finCompute.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;

@TableDef("bms_financial_settle_payment_detail")
public class FinSettlePaymentDetailEntity extends BaseEntity {
    private String id;
    @TableField(defaultUpdate = false)
    private Long orderSeq;
    private String finSettleId;//结算ID
    private String paymentEventId;//到款事件ID

    @TableField(exist = false)
    private Integer settleLevel;

    public Integer getSettleLevel() {
        return settleLevel;
    }

    public void setSettleLevel(Integer settleLevel) {
        this.settleLevel = settleLevel;
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

    public String getPaymentEventId() {
        return paymentEventId;
    }

    public void setPaymentEventId(String paymentEventId) {
        this.paymentEventId = paymentEventId;
    }
}
