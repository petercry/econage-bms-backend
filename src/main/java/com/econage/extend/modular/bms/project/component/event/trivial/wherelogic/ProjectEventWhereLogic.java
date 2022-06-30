package com.econage.extend.modular.bms.project.component.event.trivial.wherelogic;

import com.econage.core.service.BasicWhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogicField;

@WhereLogic
public class ProjectEventWhereLogic extends BasicWhereLogic {
    @WhereLogicField(column = "valid_")
    private Boolean statusTarget;

    private Integer categoryId;
    private String projectId;
    private Integer paymtType;

    @WhereLogicField(wherePart = " paymt_date_ >= STR_TO_DATE(#{searchPaymentDateFromStr}, '%Y-%m-%d %H:%i:%s')" )
    private String searchPaymentDateFromStr;

    @WhereLogicField(wherePart = " paymt_date_ <= STR_TO_DATE(#{searchPaymentDateToStr}, '%Y-%m-%d %H:%i:%s')" )
    private String searchPaymentDateToStr;

    public String getSearchPaymentDateFromStr() {
        return searchPaymentDateFromStr;
    }

    public void setSearchPaymentDateFromStr(String searchPaymentDateFromStr) {
        this.searchPaymentDateFromStr = searchPaymentDateFromStr;
    }

    public String getSearchPaymentDateToStr() {
        return searchPaymentDateToStr;
    }

    public void setSearchPaymentDateToStr(String searchPaymentDateToStr) {
        this.searchPaymentDateToStr = searchPaymentDateToStr;
    }

    public Integer getPaymtType() {
        return paymtType;
    }

    public void setPaymtType(Integer paymtType) {
        this.paymtType = paymtType;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Boolean getStatusTarget() {
        return statusTarget;
    }

    public void setStatusTarget(Boolean statusTarget) {
        this.statusTarget = statusTarget;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
