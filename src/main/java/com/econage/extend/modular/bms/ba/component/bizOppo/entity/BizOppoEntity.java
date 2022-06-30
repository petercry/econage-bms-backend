package com.econage.extend.modular.bms.ba.component.bizOppo.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;

import java.time.LocalDateTime;

@TableDef("bms_biz_oppo_master")
public class BizOppoEntity extends BaseEntity {
    private String id;
    @TableField(defaultUpdate = false)
    private Boolean valid;
    @TableField(defaultUpdate = false)
    private Long orderSeq;
    private String sourceId;
    private Integer sourceFlag;
    private String applyPhone;
    private String applyContactorName;
    private String applyBaName;
    private LocalDateTime sourceApplyDate;

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

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getSourceFlag() {
        return sourceFlag;
    }

    public void setSourceFlag(Integer sourceFlag) {
        this.sourceFlag = sourceFlag;
    }

    public String getApplyPhone() {
        return applyPhone;
    }

    public void setApplyPhone(String applyPhone) {
        this.applyPhone = applyPhone;
    }

    public String getApplyContactorName() {
        return applyContactorName;
    }

    public void setApplyContactorName(String applyContactorName) {
        this.applyContactorName = applyContactorName;
    }

    public String getApplyBaName() {
        return applyBaName;
    }

    public void setApplyBaName(String applyBaName) {
        this.applyBaName = applyBaName;
    }

    public LocalDateTime getSourceApplyDate() {
        return sourceApplyDate;
    }

    public void setSourceApplyDate(LocalDateTime sourceApplyDate) {
        this.sourceApplyDate = sourceApplyDate;
    }
}
