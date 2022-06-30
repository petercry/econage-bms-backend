package com.econage.extend.modular.bms.kanban.trivial.wherelogic;

import com.econage.core.service.BasicWhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogicField;

import java.util.Collection;

@WhereLogic
public class RequireWhereLogic extends BasicWhereLogic {
    @WhereLogicField(column = "valid_")
    private Boolean statusTarget;

    private String productId;
    private String projectId;

    @WhereLogicField(wherePart = "(title_ like concat('%',#{title},'%'))")
    private String title;

    @WhereLogicField(column = "priority_")
    private Collection<Integer> priorityList;

    @WhereLogicField(column = "status_")
    private Collection<Integer> statusList;

    @WhereLogicField(wherePart = "(expect_finish_date_ >= #{expectFinishDate_from} )")
    private String expectFinishDate_from;

    @WhereLogicField(wherePart = "(expect_finish_date_ <= #{expectFinishDate_to} )")
    private String expectFinishDate_to;

    @WhereLogicField(wherePart = "(create_date_ >= #{createDate_from} )")
    private String createDate_from;

    @WhereLogicField(wherePart = "(DATE_SUB(create_date_,INTERVAL 1 DAY) <= #{createDate_to} )")
    private String createDate_to;

    public String getExpectFinishDate_from() {
        return expectFinishDate_from;
    }

    public void setExpectFinishDate_from(String expectFinishDate_from) {
        this.expectFinishDate_from = expectFinishDate_from;
    }

    public String getExpectFinishDate_to() {
        return expectFinishDate_to;
    }

    public void setExpectFinishDate_to(String expectFinishDate_to) {
        this.expectFinishDate_to = expectFinishDate_to;
    }

    public String getCreateDate_from() {
        return createDate_from;
    }

    public void setCreateDate_from(String createDate_from) {
        this.createDate_from = createDate_from;
    }

    public String getCreateDate_to() {
        return createDate_to;
    }

    public void setCreateDate_to(String createDate_to) {
        this.createDate_to = createDate_to;
    }

    public Collection<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(Collection<Integer> statusList) {
        this.statusList = statusList;
    }

    public Collection<Integer> getPriorityList() {
        return priorityList;
    }

    public void setPriorityList(Collection<Integer> priorityList) {
        this.priorityList = priorityList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @WhereLogicField(column = "id_")
    private Collection<String> ids;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Boolean getStatusTarget() {
        return statusTarget;
    }

    public void setStatusTarget(Boolean statusTarget) {
        this.statusTarget = statusTarget;
    }



    public Collection<String> getIds() {
        return ids;
    }

    public void setIds(Collection<String> ids) {
        this.ids = ids;
    }
}
