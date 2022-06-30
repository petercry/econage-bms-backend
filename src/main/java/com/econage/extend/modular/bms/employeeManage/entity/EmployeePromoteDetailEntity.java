package com.econage.extend.modular.bms.employeeManage.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;

@TableDef("bms_employee_promote_detail")
public class EmployeePromoteDetailEntity extends BaseEntity {
    private String id;
    @TableField(defaultUpdate = false)
    private Long orderSeq;
    @TableField(defaultUpdate = false)
    private Boolean valid;

    private String promoteId; //晋升主表ID
    private String userId; //员工ID
    private String oriPositionGrade; //原职等D
    private String oriPositionLevel; //原职级L
    private Double oriBasicWage; //原基础薪资
    private Double oriExtraAllowance; //原补贴
    private String newPositionGrade; //晋升后职等D
    private String newPositionLevel; //晋升后职级L
    private Double newBasicWage; //晋升后基础薪资
    private Double newExtraAllowance; //晋升后补贴
    private String comments; //备注

    public Double getOriBasicWage() {
        return oriBasicWage;
    }

    public void setOriBasicWage(Double oriBasicWage) {
        this.oriBasicWage = oriBasicWage;
    }

    public Double getOriExtraAllowance() {
        return oriExtraAllowance;
    }

    public void setOriExtraAllowance(Double oriExtraAllowance) {
        this.oriExtraAllowance = oriExtraAllowance;
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

    public String getPromoteId() {
        return promoteId;
    }

    public void setPromoteId(String promoteId) {
        this.promoteId = promoteId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOriPositionGrade() {
        return oriPositionGrade;
    }

    public void setOriPositionGrade(String oriPositionGrade) {
        this.oriPositionGrade = oriPositionGrade;
    }

    public String getOriPositionLevel() {
        return oriPositionLevel;
    }

    public void setOriPositionLevel(String oriPositionLevel) {
        this.oriPositionLevel = oriPositionLevel;
    }

    public String getNewPositionGrade() {
        return newPositionGrade;
    }

    public void setNewPositionGrade(String newPositionGrade) {
        this.newPositionGrade = newPositionGrade;
    }

    public String getNewPositionLevel() {
        return newPositionLevel;
    }

    public void setNewPositionLevel(String newPositionLevel) {
        this.newPositionLevel = newPositionLevel;
    }

    public Double getNewBasicWage() {
        return newBasicWage;
    }

    public void setNewBasicWage(Double newBasicWage) {
        this.newBasicWage = newBasicWage;
    }

    public Double getNewExtraAllowance() {
        return newExtraAllowance;
    }

    public void setNewExtraAllowance(Double newExtraAllowance) {
        this.newExtraAllowance = newExtraAllowance;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
