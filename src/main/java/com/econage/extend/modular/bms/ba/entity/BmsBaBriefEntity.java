package com.econage.extend.modular.bms.ba.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BasicEntity;

import java.time.LocalDate;

@TableDef("bms_ba_master")
public class BmsBaBriefEntity implements BasicEntity {
    private String id;
    @TableField(defaultUpdate = false)
    private Boolean valid;

    @TableField(defaultUpdate = false)
    private Long orderSeq;
    private String baName;
    @TableField(exist = false)
    private String firstStatusDesc;
    @TableField(exist = false)
    private String valueCodeDesc;
    private String stateAreaDesc;
    private String lastActionUser;
    private LocalDate lastContactTime;
    private String state;
    private String city;
    private String firstStatus;
    private String valueCode;

    public String getFirstStatus() {
        return firstStatus;
    }

    public void setFirstStatus(String firstStatus) {
        this.firstStatus = firstStatus;
    }

    public String getValueCode() {
        return valueCode;
    }

    public void setValueCode(String valueCode) {
        this.valueCode = valueCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getBaName() {
        return baName;
    }

    public void setBaName(String baName) {
        this.baName = baName;
    }

    public String getFirstStatusDesc() {
        return firstStatusDesc;
    }

    public void setFirstStatusDesc(String firstStatusDesc) {
        this.firstStatusDesc = firstStatusDesc;
    }

    public String getValueCodeDesc() {
        return valueCodeDesc;
    }

    public void setValueCodeDesc(String valueCodeDesc) {
        this.valueCodeDesc = valueCodeDesc;
    }

    public String getStateAreaDesc() {
        return stateAreaDesc;
    }

    public void setStateAreaDesc(String stateAreaDesc) {
        this.stateAreaDesc = stateAreaDesc;
    }

    public String getLastActionUser() {
        return lastActionUser;
    }

    public void setLastActionUser(String lastActionUser) {
        this.lastActionUser = lastActionUser;
    }

    public LocalDate getLastContactTime() {
        return lastContactTime;
    }

    public void setLastContactTime(LocalDate lastContactTime) {
        this.lastContactTime = lastContactTime;
    }
}
