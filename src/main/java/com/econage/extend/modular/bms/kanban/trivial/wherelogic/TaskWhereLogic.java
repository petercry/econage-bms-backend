package com.econage.extend.modular.bms.kanban.trivial.wherelogic;

import com.econage.core.service.BasicWhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogicField;

import java.util.Collection;

@WhereLogic
public class TaskWhereLogic extends BasicWhereLogic {
    @WhereLogicField(column = "valid_")
    private Boolean statusTarget;
    private Integer status;

    @WhereLogicField(wherePart = "(title_ like concat('%',#{title},'%'))")
    private String title;

    private String projectId;
    private String requireId;
    private String dealer;
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

    @WhereLogicField(wherePart = "( exception_flag_ = 1 and leader_confirm_create_time_ is null )")
    private Boolean needLeaderConfirm;

    @WhereLogicField(enable = false)
    private String userIdStr;

    @WhereLogicField(enable = false)
    private String priorityStr;

    @WhereLogicField(enable = false)
    private String statusStr;

    public String getUserIdStr() {
        return userIdStr;
    }

    public void setUserIdStr(String userIdStr) {
        this.userIdStr = userIdStr;
    }

    public String getPriorityStr() {
        return priorityStr;
    }

    public void setPriorityStr(String priorityStr) {
        this.priorityStr = priorityStr;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getNeedLeaderConfirm() {
        return needLeaderConfirm;
    }

    public void setNeedLeaderConfirm(Boolean needLeaderConfirm) {
        this.needLeaderConfirm = needLeaderConfirm;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getRequireId() {
        return requireId;
    }

    public void setRequireId(String requireId) {
        this.requireId = requireId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @WhereLogicField(column = "id_")
    private Collection<String> ids;

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

    @WhereLogicField(parser = TaskWhereLogicParser.TaskUserCollectSelectAuthParser.class)
    private Collection<String> userSelectAuthExpress;
    @WhereLogicField(parser = TaskWhereLogicParser.TaskCalendarDateArrangeSelectAuthParser.class)
    private Collection<String> taskCalendarArrangeSelectAuthExpress;

    public Collection<String> getTaskCalendarArrangeSelectAuthExpress() {
        return taskCalendarArrangeSelectAuthExpress;
    }

    public void setTaskCalendarArrangeSelectAuthExpress(Collection<String> taskCalendarArrangeSelectAuthExpress) {
        this.taskCalendarArrangeSelectAuthExpress = taskCalendarArrangeSelectAuthExpress;
    }

    public Collection<String> getUserSelectAuthExpress() {
        return userSelectAuthExpress;
    }

    public void setUserSelectAuthExpress(Collection<String> userSelectAuthExpress) {
        this.userSelectAuthExpress = userSelectAuthExpress;
    }
}
