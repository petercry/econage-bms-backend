package com.econage.extend.modular.bms.kanban.trivial.wherelogic;

import com.econage.core.service.BasicWhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogicField;
@WhereLogic
public class ProductWhereLogic extends BasicWhereLogic {
    @WhereLogicField(column = "valid_")
    private Boolean statusTarget;

    public Boolean getStatusTarget() {
        return statusTarget;
    }

    public void setStatusTarget(Boolean statusTarget) {
        this.statusTarget = statusTarget;
    }
}
