package com.econage.extend.modular.bms.ba.component.bizOppo.entity;

import java.time.LocalDateTime;

public class TmpLibBizOppoEntity {
    private String id;
    private String sourceId;
    private String sourcePt;
    private String phone;
    private String tenantName;
    private String contractName;
    private String clientIp;
    private String sourceCreateDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourcePt() {
        return sourcePt;
    }

    public void setSourcePt(String sourcePt) {
        this.sourcePt = sourcePt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getSourceCreateDate() {
        return sourceCreateDate;
    }

    public void setSourceCreateDate(String sourceCreateDate) {
        this.sourceCreateDate = sourceCreateDate;
    }
}
