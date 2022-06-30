package com.econage.extend.modular.bms.employeeManage.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;

import java.time.LocalDate;

@TableDef("bms_employee_promote_summary")
public class EmployeePromoteSummaryEntity extends BaseEntity {
    private String id;
    @TableField(defaultUpdate = false)
    private Long orderSeq;
    @TableField(defaultUpdate = false)
    private Boolean valid;

    private String promoteTitle; //晋升评估描述
    private LocalDate promoteTime; //晋升评估时间
    private String comments; //备注

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

    public String getPromoteTitle() {
        return promoteTitle;
    }

    public void setPromoteTitle(String promoteTitle) {
        this.promoteTitle = promoteTitle;
    }

    public LocalDate getPromoteTime() {
        return promoteTime;
    }

    public void setPromoteTime(LocalDate promoteTime) {
        this.promoteTime = promoteTime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
