package com.econage.extend.modular.bms.finCompute.trivial.wherelogic;

import com.econage.core.service.BasicWhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogic;

@WhereLogic
public class FinSettleDetailWherelogic extends BasicWhereLogic {
    private String finSettleId;

    public String getFinSettleId() {
        return finSettleId;
    }

    public void setFinSettleId(String finSettleId) {
        this.finSettleId = finSettleId;
    }
}
