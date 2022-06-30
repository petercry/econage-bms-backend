package com.econage.extend.modular.bms.kanban.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@TableDef("bms_task_calendar")
public class TaskCalendarEntity extends BaseEntity {
    private String id;
    @TableField(defaultUpdate = false)
    private Boolean valid;

    @TableField(defaultUpdate = false)
    private Long orderSeq;
    private String taskId;

    @TableField(exist = false)
    private String taskName;

    private LocalDate date;
    private Integer priority;
    private String planInputer;

    @TableField(exist = false)
    private String planInputerName;

    private LocalDateTime planCreateTime;
    private Integer planRate;
    private String planComments;
    private String feedbackInputer;

    @TableField(exist = false)
    private String feedbackInputerName;

    private LocalDateTime feedbackCreateTime;
    private Integer feedbackRate;
    private Double feedbackManHour;
    private String feedbackComments;
    private String dealer;

    @TableField(exist = false)
    private String dealerName;

    @TableField(exist = false)
    private Double correctTotalTaskManHour;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Double getCorrectTotalTaskManHour() {
        return correctTotalTaskManHour;
    }

    public void setCorrectTotalTaskManHour(Double correctTotalTaskManHour) {
        this.correctTotalTaskManHour = correctTotalTaskManHour;
    }

    public Double getFeedbackManHour() {
        return feedbackManHour;
    }

    public void setFeedbackManHour(Double feedbackManHour) {
        this.feedbackManHour = feedbackManHour;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getPlanInputerName() {
        return planInputerName;
    }

    public void setPlanInputerName(String planInputerName) {
        this.planInputerName = planInputerName;
    }

    public String getFeedbackInputerName() {
        return feedbackInputerName;
    }

    public void setFeedbackInputerName(String feedbackInputerName) {
        this.feedbackInputerName = feedbackInputerName;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Long getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(Long orderSeq) {
        this.orderSeq = orderSeq;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPlanInputer() {
        return planInputer;
    }

    public void setPlanInputer(String planInputer) {
        this.planInputer = planInputer;
    }

    public LocalDateTime getPlanCreateTime() {
        return planCreateTime;
    }

    public void setPlanCreateTime(LocalDateTime planCreateTime) {
        this.planCreateTime = planCreateTime;
    }

    public Integer getPlanRate() {
        return planRate;
    }

    public void setPlanRate(Integer planRate) {
        this.planRate = planRate;
    }

    public String getPlanComments() {
        return planComments;
    }

    public void setPlanComments(String planComments) {
        this.planComments = planComments;
    }

    public String getFeedbackInputer() {
        return feedbackInputer;
    }

    public void setFeedbackInputer(String feedbackInputer) {
        this.feedbackInputer = feedbackInputer;
    }

    public LocalDateTime getFeedbackCreateTime() {
        return feedbackCreateTime;
    }

    public void setFeedbackCreateTime(LocalDateTime feedbackCreateTime) {
        this.feedbackCreateTime = feedbackCreateTime;
    }

    public Integer getFeedbackRate() {
        return feedbackRate;
    }

    public void setFeedbackRate(Integer feedbackRate) {
        this.feedbackRate = feedbackRate;
    }

    public String getFeedbackComments() {
        return feedbackComments;
    }

    public void setFeedbackComments(String feedbackComments) {
        this.feedbackComments = feedbackComments;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }
}
