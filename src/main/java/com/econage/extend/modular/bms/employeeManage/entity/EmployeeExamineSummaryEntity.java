package com.econage.extend.modular.bms.employeeManage.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;

import java.time.LocalDate;

@TableDef("bms_employee_examine_summary")
public class EmployeeExamineSummaryEntity extends BaseEntity {
    private String id;
    @TableField(defaultUpdate = false)
    private Long orderSeq;
    @TableField(defaultUpdate = false)
    private Boolean valid;

    private String examineTitle;//考核描述
    private LocalDate examineTime;//考核时间
    private String comments;//备注

    private Double rewardBase;//考核保底奖金基数
    private Double rewardTop;//考核最高奖金基数

    public Double getRewardBase() {
        return rewardBase;
    }

    public void setRewardBase(Double rewardBase) {
        this.rewardBase = rewardBase;
    }

    public Double getRewardTop() {
        return rewardTop;
    }

    public void setRewardTop(Double rewardTop) {
        this.rewardTop = rewardTop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(Long orderSeq) {
        this.orderSeq = orderSeq;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getExamineTitle() {
        return examineTitle;
    }

    public void setExamineTitle(String examineTitle) {
        this.examineTitle = examineTitle;
    }

    public LocalDate getExamineTime() {
        return examineTime;
    }

    public void setExamineTime(LocalDate examineTime) {
        this.examineTime = examineTime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
