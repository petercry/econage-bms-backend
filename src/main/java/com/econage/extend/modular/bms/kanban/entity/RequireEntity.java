package com.econage.extend.modular.bms.kanban.entity;

import com.econage.extend.modular.bms.util.ApiFileEntity;
import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;

import java.time.LocalDate;

@TableDef("bms_dev_require")
public class RequireEntity extends BaseEntity {
    private String id;
    @TableField(defaultUpdate = false)
    private Boolean valid;

    @TableField(defaultUpdate = false)
    private Long orderSeq;
    private Integer priority;
    private String desc;
    private Integer status;
    @TableField(exist = false)
    private String statusDesc;
    private LocalDate expectFinishDate;
    private LocalDate actualFinishDate;
    private String preSysFlag;
    private String preSysId;
    private String code;
    private String productId;
    private String projectId;
    private String sourceFlag;
    private String sourceDesc;
    private String comments;
    private String title;
    private Double manHour;

    @TableField(exist = false)
    private Integer childrenCount;

    @TableField(exist = false)
    private ApiFileEntity[] apiFileV;

    @TableField(exist = false)
    private String createUserName;

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Double getManHour() {
        return manHour;
    }

    public void setManHour(Double manHour) {
        this.manHour = manHour;
    }

    public ApiFileEntity[] getApiFileV() {
        return apiFileV;
    }

    public void setApiFileV(ApiFileEntity[] apiFileV) {
        this.apiFileV = apiFileV;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Integer getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(Integer childrenCount) {
        this.childrenCount = childrenCount;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getExpectFinishDate() {
        return expectFinishDate;
    }

    public void setExpectFinishDate(LocalDate expectFinishDate) {
        this.expectFinishDate = expectFinishDate;
    }

    public LocalDate getActualFinishDate() {
        return actualFinishDate;
    }

    public void setActualFinishDate(LocalDate actualFinishDate) {
        this.actualFinishDate = actualFinishDate;
    }

    public String getPreSysFlag() {
        return preSysFlag;
    }

    public void setPreSysFlag(String preSysFlag) {
        this.preSysFlag = preSysFlag;
    }

    public String getPreSysId() {
        return preSysId;
    }

    public void setPreSysId(String preSysId) {
        this.preSysId = preSysId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getSourceFlag() {
        return sourceFlag;
    }

    public void setSourceFlag(String sourceFlag) {
        this.sourceFlag = sourceFlag;
    }

    public String getSourceDesc() {
        return sourceDesc;
    }

    public void setSourceDesc(String sourceDesc) {
        this.sourceDesc = sourceDesc;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
