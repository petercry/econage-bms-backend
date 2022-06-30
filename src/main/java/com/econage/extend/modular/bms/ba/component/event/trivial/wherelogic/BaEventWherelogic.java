package com.econage.extend.modular.bms.ba.component.event.trivial.wherelogic;

import com.econage.core.service.BasicWhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogicField;

@WhereLogic
public class BaEventWherelogic extends BasicWhereLogic {
    @WhereLogicField(column = "valid_")
    private Boolean statusTarget;
    private String baId;

    @WhereLogicField(wherePart = "(action_date_ >= STR_TO_DATE(#{searchActionDate_from}, '%Y-%m-%d')  )")
    private String searchActionDate_from;

    @WhereLogicField(wherePart = "(action_date_ <= date_add(STR_TO_DATE(#{searchActionDate_to}, '%Y-%m-%d'),interval 1 day ) )")
    private String searchActionDate_to;

    public String getSearchActionDate_from() {
        return searchActionDate_from;
    }

    public void setSearchActionDate_from(String searchActionDate_from) {
        this.searchActionDate_from = searchActionDate_from;
    }

    public String getSearchActionDate_to() {
        return searchActionDate_to;
    }

    public void setSearchActionDate_to(String searchActionDate_to) {
        this.searchActionDate_to = searchActionDate_to;
    }

    public Boolean getStatusTarget() {
        return statusTarget;
    }

    public void setStatusTarget(Boolean statusTarget) {
        this.statusTarget = statusTarget;
    }

    public String getBaId() {
        return baId;
    }

    public void setBaId(String baId) {
        this.baId = baId;
    }
}
