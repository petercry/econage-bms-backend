package com.econage.extend.modular.bms.finCompute.trivial.wherelogic;

import com.econage.core.service.BasicWhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogic;

@WhereLogic
public class FinSettleSummaryWherelogic extends BasicWhereLogic {
    private String projectId;
    private Integer settleLevel;

    public Integer getSettleLevel() {
        return settleLevel;
    }

    public void setSettleLevel(Integer settleLevel) {
        this.settleLevel = settleLevel;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
