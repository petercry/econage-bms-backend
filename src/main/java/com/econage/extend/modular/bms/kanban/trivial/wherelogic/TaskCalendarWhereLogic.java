package com.econage.extend.modular.bms.kanban.trivial.wherelogic;

import com.econage.core.service.BasicWhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogicField;

import java.util.Collection;

@WhereLogic
public class TaskCalendarWhereLogic extends BasicWhereLogic {
    private String taskId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @WhereLogicField(column = "valid_")
    private Boolean statusTarget;

    @WhereLogicField(column = "dealer_")
    private Collection<String> dealers;

    @WhereLogicField(wherePart = " date_ >= #{viewDateFrom} ")
    private String viewDateFrom;

    @WhereLogicField(wherePart = " date_ <= #{viewDateTo} ")
    private String viewDateTo;

    public Boolean getStatusTarget() {
        return statusTarget;
    }

    public void setStatusTarget(Boolean statusTarget) {
        this.statusTarget = statusTarget;
    }

    public Collection<String> getDealers() {
        return dealers;
    }

    public void setDealers(Collection<String> dealers) {
        this.dealers = dealers;
    }

    public String getViewDateFrom() {
        return viewDateFrom;
    }

    public void setViewDateFrom(String viewDateFrom) {
        this.viewDateFrom = viewDateFrom;
    }

    public String getViewDateTo() {
        return viewDateTo;
    }

    public void setViewDateTo(String viewDateTo) {
        this.viewDateTo = viewDateTo;
    }
}
