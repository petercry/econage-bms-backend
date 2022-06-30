package com.econage.extend.modular.bms.finCompute.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;

@TableDef("bms_expense_sign")
public class ExpenseSignEntity extends BaseEntity {
    private String id;
    @TableField(defaultUpdate = false)
    private Long orderSeq;

    private Integer Modular;//1:费用(journal里取);2:税费(payment event里取);
    private String modularInnerId;
    private Integer opFlag;//一般就是插入1
    private Integer settleLevel;//结算维度(1:销售;2:实施;3:技术支持;4:AF运营;5:维保服务)
    private String finSettleId;//结算id

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

    public Integer getModular() {
        return Modular;
    }

    public void setModular(Integer modular) {
        Modular = modular;
    }

    public String getModularInnerId() {
        return modularInnerId;
    }

    public void setModularInnerId(String modularInnerId) {
        this.modularInnerId = modularInnerId;
    }

    public Integer getOpFlag() {
        return opFlag;
    }

    public void setOpFlag(Integer opFlag) {
        this.opFlag = opFlag;
    }

    public Integer getSettleLevel() {
        return settleLevel;
    }

    public void setSettleLevel(Integer settleLevel) {
        this.settleLevel = settleLevel;
    }

    public String getFinSettleId() {
        return finSettleId;
    }

    public void setFinSettleId(String finSettleId) {
        this.finSettleId = finSettleId;
    }
}
