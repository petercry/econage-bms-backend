package com.econage.extend.modular.bms.finCompute.trivial.wherelogic;

import com.econage.core.service.BasicWhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogic;

@WhereLogic
public class RewardPayoffHisWherelogic extends BasicWhereLogic {
    private String finSettleDetId;
    private String projectId;
    private String distributeUserId;

    public String getFinSettleDetId() {
        return finSettleDetId;
    }

    public void setFinSettleDetId(String finSettleDetId) {
        this.finSettleDetId = finSettleDetId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getDistributeUserId() {
        return distributeUserId;
    }

    public void setDistributeUserId(String distributeUserId) {
        this.distributeUserId = distributeUserId;
    }
}
