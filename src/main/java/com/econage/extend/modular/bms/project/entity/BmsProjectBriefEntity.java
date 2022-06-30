package com.econage.extend.modular.bms.project.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BasicEntity;

@TableDef("bms_project_master")
public class BmsProjectBriefEntity implements BasicEntity {
    private String id;
    @TableField(defaultUpdate = false)
    private Boolean valid;

    @TableField(defaultUpdate = false)
    private Long orderSeq;

    private String projectName;
    @TableField(exist = false)
    private String productFocusIdForAf;
    private String devPlatformUrl;
    private String devPlatformUserid;
    private String devPlatformPwd;
    private String devPlatformDbIp;
    private String devPlatformDbSid;
    private String devVersionLib;
    private String salesManager;
    @TableField(exist = false)
    private String salesManagerName;
    private Boolean productFocusFlag;

    public Boolean getProductFocusFlag() {
        return productFocusFlag;
    }

    public void setProductFocusFlag(Boolean productFocusFlag) {
        this.productFocusFlag = productFocusFlag;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProductFocusIdForAf() {
        return productFocusIdForAf;
    }

    public void setProductFocusIdForAf(String productFocusIdForAf) {
        this.productFocusIdForAf = productFocusIdForAf;
    }

    public String getDevPlatformUrl() {
        return devPlatformUrl;
    }

    public void setDevPlatformUrl(String devPlatformUrl) {
        this.devPlatformUrl = devPlatformUrl;
    }

    public String getDevPlatformUserid() {
        return devPlatformUserid;
    }

    public void setDevPlatformUserid(String devPlatformUserid) {
        this.devPlatformUserid = devPlatformUserid;
    }

    public String getDevPlatformPwd() {
        return devPlatformPwd;
    }

    public void setDevPlatformPwd(String devPlatformPwd) {
        this.devPlatformPwd = devPlatformPwd;
    }

    public String getDevPlatformDbIp() {
        return devPlatformDbIp;
    }

    public void setDevPlatformDbIp(String devPlatformDbIp) {
        this.devPlatformDbIp = devPlatformDbIp;
    }

    public String getDevPlatformDbSid() {
        return devPlatformDbSid;
    }

    public void setDevPlatformDbSid(String devPlatformDbSid) {
        this.devPlatformDbSid = devPlatformDbSid;
    }

    public String getDevVersionLib() {
        return devVersionLib;
    }

    public void setDevVersionLib(String devVersionLib) {
        this.devVersionLib = devVersionLib;
    }

    public String getSalesManager() {
        return salesManager;
    }

    public void setSalesManager(String salesManager) {
        this.salesManager = salesManager;
    }

    public String getSalesManagerName() {
        return salesManagerName;
    }

    public void setSalesManagerName(String salesManagerName) {
        this.salesManagerName = salesManagerName;
    }
}
