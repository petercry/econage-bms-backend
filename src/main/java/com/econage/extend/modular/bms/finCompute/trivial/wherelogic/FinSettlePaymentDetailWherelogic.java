package com.econage.extend.modular.bms.finCompute.trivial.wherelogic;

import com.econage.core.service.BasicWhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogicField;

@WhereLogic
public class FinSettlePaymentDetailWherelogic  extends BasicWhereLogic {
    private String finSettleId;

    private String paymentEventId;

    @WhereLogicField(enable = false)
    private Integer settleLevel;

    @WhereLogicField(wherePart = "exists(select 1 from bms_financial_settle_summary where bms_financial_settle_summary.id_ = bms_financial_settle_payment_detail.fin_settle_id_ and bms_financial_settle_summary.settle_level_ = #{settleLevel} )")
    private Boolean checkExist;

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

    public Integer getSettleLevel() {
        return settleLevel;
    }

    public void setSettleLevel(Integer settleLevel) {
        this.settleLevel = settleLevel;
    }

    public Boolean getCheckExist() {
        return checkExist;
    }

    public void setCheckExist(Boolean checkExist) {
        this.checkExist = checkExist;
    }
}
