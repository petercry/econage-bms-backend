package com.econage.extend.modular.bms.ba.component.contact.trivial.wherelogic;

import com.econage.core.service.BasicWhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogicField;

@WhereLogic
public class BaContactWherelogic  extends BasicWhereLogic {
    @WhereLogicField(column = "valid_")
    private Boolean statusTarget;
    private String baId;

    public String getBaId() {
        return baId;
    }

    public void setBaId(String baId) {
        this.baId = baId;
    }

    public Boolean getStatusTarget() {
        return statusTarget;
    }

    public void setStatusTarget(Boolean statusTarget) {
        this.statusTarget = statusTarget;
    }
}
