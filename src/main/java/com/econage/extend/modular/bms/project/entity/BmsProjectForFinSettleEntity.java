package com.econage.extend.modular.bms.project.entity;

import com.econage.extend.modular.bms.ba.entity.BmsBaEntity;
import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BasicEntity;

import java.time.LocalDate;
import java.util.List;

@TableDef("bms_project_master")
public class BmsProjectForFinSettleEntity implements BasicEntity {
    private String id;
    @TableField(defaultUpdate = false)
    private Boolean valid;
    @TableField(defaultUpdate = false)
    private Long orderSeq;
    private String projectName;
    private Double contractAmt;
    private String receivedPaymtPct;
    private Double receivedPaymtAmt;
    @TableField(exist = false)
    private Double costSum;

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


    public String getReceivedPaymtPct() {
        return receivedPaymtPct;
    }

    public void setReceivedPaymtPct(String receivedPaymtPct) {
        this.receivedPaymtPct = receivedPaymtPct;
    }

    public Double getContractAmt() {
        return contractAmt;
    }

    public void setContractAmt(Double contractAmt) {
        this.contractAmt = contractAmt;
    }

    public Double getReceivedPaymtAmt() {
        return receivedPaymtAmt;
    }

    public void setReceivedPaymtAmt(Double receivedPaymtAmt) {
        this.receivedPaymtAmt = receivedPaymtAmt;
    }

    public Double getCostSum() {
        return costSum;
    }

    public void setCostSum(Double costSum) {
        this.costSum = costSum;
    }
}
