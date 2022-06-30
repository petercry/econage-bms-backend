package com.econage.extend.modular.bms.finCompute.trivial.wherelogic;

import com.econage.core.service.BasicWhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogic;

@WhereLogic
public class ExpenseSignWherelogic extends BasicWhereLogic {
    private Integer modular;
    private String modularInnerId;
    private Integer settleLevel;
    private Integer opFlag = 1;
    private String finSettleId;

    public String getFinSettleId() {
        return finSettleId;
    }

    public void setFinSettleId(String finSettleId) {
        this.finSettleId = finSettleId;
    }

    public Integer getModular() {
        return modular;
    }

    public void setModular(Integer modular) {
        this.modular = modular;
    }

    public String getModularInnerId() {
        return modularInnerId;
    }

    public void setModularInnerId(String modularInnerId) {
        this.modularInnerId = modularInnerId;
    }

    public Integer getSettleLevel() {
        return settleLevel;
    }

    public void setSettleLevel(Integer settleLevel) {
        this.settleLevel = settleLevel;
    }

    public Integer getOpFlag() {
        return opFlag;
    }

    public void setOpFlag(Integer opFlag) {
        this.opFlag = opFlag;
    }
}
