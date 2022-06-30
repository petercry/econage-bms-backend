package com.econage.extend.modular.bms.employeeManage.trivial.wherelogic;

import com.econage.core.service.BasicWhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogicField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@WhereLogic
@Data
public class EmployeeInfoWherelogic extends BasicWhereLogic {
    @WhereLogicField(column = "valid_")
    private Boolean statusTarget;

    @WhereLogicField(enable = false)
    private Boolean autoComputeFinalData;//取数时是否自动计算终值绩效分等数据

    @WhereLogicField(wherePart = "  exists(select 1 from bms_employee_examine_detail where bms_employee_examine_detail.valid_=true and bms_employee_examine_detail.examine_id_ = #{searchExamineId} and bms_employee_examine_detail.user_id_ = bms_employee_info.user_id_ and bms_employee_examine_detail.grade_model_ = #{searchExamineModel}) ")
    private String searchExamineModel;

    @WhereLogicField(enable = false)
    private String searchExamineId;

    @WhereLogicField(enable = false)
    private String searchPromoteId;

    public Boolean getAutoComputeFinalData() {
        return autoComputeFinalData;
    }

    public void setAutoComputeFinalData(Boolean autoComputeFinalData) {
        this.autoComputeFinalData = autoComputeFinalData;
    }

    public Boolean getStatusTarget() {
        return statusTarget;
    }

    public void setStatusTarget(Boolean statusTarget) {
        this.statusTarget = statusTarget;
    }

    public String getSearchExamineModel() {
        return searchExamineModel;
    }

    public void setSearchExamineModel(String searchExamineModel) {
        this.searchExamineModel = searchExamineModel;
    }

    public String getSearchExamineId() {
        return searchExamineId;
    }

    public void setSearchExamineId(String searchExamineId) {
        this.searchExamineId = searchExamineId;
    }

    public String getSearchPromoteId() {
        return searchPromoteId;
    }

    public void setSearchPromoteId(String searchPromoteId) {
        this.searchPromoteId = searchPromoteId;
    }
}
