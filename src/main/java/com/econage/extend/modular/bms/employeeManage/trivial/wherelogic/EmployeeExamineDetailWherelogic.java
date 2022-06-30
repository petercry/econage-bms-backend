package com.econage.extend.modular.bms.employeeManage.trivial.wherelogic;

import com.econage.core.service.BasicWhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogicField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@WhereLogic
@Data
public class EmployeeExamineDetailWherelogic extends BasicWhereLogic {
    @WhereLogicField(column = "valid_")
    private Boolean statusTarget;
    private String examineId; //考核主表ID
    private String userId;

    public Boolean getStatusTarget() {
        return statusTarget;
    }

    public void setStatusTarget(Boolean statusTarget) {
        this.statusTarget = statusTarget;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExamineId() {
        return examineId;
    }

    public void setExamineId(String examineId) {
        this.examineId = examineId;
    }
}
