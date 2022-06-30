package com.econage.extend.modular.bms.employeeManage.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;

import java.time.LocalDate;

@TableDef("bms_employee_info")
public class EmployeeInfoEntity extends BaseEntity {
    private String id;
    @TableField(defaultUpdate = false)
    private Long orderSeq;
    @TableField(defaultUpdate = false)
    private Boolean valid;

    private String userId;//员工ID
    private String userName;
    private String positionGrade;//当前职等（D）
    private String postionLevel;//当前职级（L）

    @TableField(exist = false)
    private String positionGradeTotalDesc;

    private LocalDate regularTime;//转正时间
    private Double basicWage;//当前基础薪资
    private Double extraAllowance;//当前补贴
    private String comments;//备注

    @TableField(exist = false)
    private EmployeeExamineDetailEntity employeeExamineDetailEntity;

    @TableField(exist = false)
    private EmployeePromoteDetailEntity employeePromoteDetailEntity;

    public String getPositionGradeTotalDesc() {
        return positionGradeTotalDesc;
    }

    public void setPositionGradeTotalDesc(String positionGradeTotalDesc) {
        this.positionGradeTotalDesc = positionGradeTotalDesc;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public EmployeeExamineDetailEntity getEmployeeExamineDetailEntity() {
        return employeeExamineDetailEntity;
    }

    public void setEmployeeExamineDetailEntity(EmployeeExamineDetailEntity employeeExamineDetailEntity) {
        this.employeeExamineDetailEntity = employeeExamineDetailEntity;
    }

    public EmployeePromoteDetailEntity getEmployeePromoteDetailEntity() {
        return employeePromoteDetailEntity;
    }

    public void setEmployeePromoteDetailEntity(EmployeePromoteDetailEntity employeePromoteDetailEntity) {
        this.employeePromoteDetailEntity = employeePromoteDetailEntity;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPositionGrade() {
        return positionGrade;
    }

    public void setPositionGrade(String positionGrade) {
        this.positionGrade = positionGrade;
    }

    public String getPostionLevel() {
        return postionLevel;
    }

    public void setPostionLevel(String postionLevel) {
        this.postionLevel = postionLevel;
    }

    public LocalDate getRegularTime() {
        return regularTime;
    }

    public void setRegularTime(LocalDate regularTime) {
        this.regularTime = regularTime;
    }

    public Double getBasicWage() {
        return basicWage;
    }

    public void setBasicWage(Double basicWage) {
        this.basicWage = basicWage;
    }

    public Double getExtraAllowance() {
        return extraAllowance;
    }

    public void setExtraAllowance(Double extraAllowance) {
        this.extraAllowance = extraAllowance;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
