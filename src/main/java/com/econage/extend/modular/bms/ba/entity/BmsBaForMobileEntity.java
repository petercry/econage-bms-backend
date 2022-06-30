package com.econage.extend.modular.bms.ba.entity;

import com.econage.extend.modular.bms.ba.component.auth.entity.BmsBaAuthForMobileEntity;
import com.econage.extend.modular.bms.ba.component.contact.entity.BmsBaContactForMobileEntity;
import com.econage.extend.modular.bms.ba.component.event.entity.BmsBaEventForMobileEntity;

import java.time.LocalDateTime;
import java.util.Collection;

public class BmsBaForMobileEntity {
    private String address;
    private String cityCode;
    private String clientContactPerson;
    private String code;
    private String comments;
    private String contactsStatus;
    private LocalDateTime createDate;
    private String createUser;
    private String id;
    private Boolean inTime;
    private LocalDateTime modifiedDate;
    private String modifiedUser;
    private String name;
    private String ownerShipCode;
    private String phoneNo;
    private String posCode;
    private String relationCode;
    private String scaleCode;
    private String shortName;
    private String sourceCode;
    private String stateCode;
    private String typeCode;
    private String valueCode;

    private Collection<BmsBaEventForMobileEntity> requestBAEventBeans;

    private Collection<BmsBaAuthForMobileEntity> componentRoleUserBeans;

    private Collection<BmsBaContactForMobileEntity> requestBAContactBeans;

    public Collection<BmsBaContactForMobileEntity> getRequestBAContactBeans() {
        return requestBAContactBeans;
    }

    public void setRequestBAContactBeans(Collection<BmsBaContactForMobileEntity> requestBAContactBeans) {
        this.requestBAContactBeans = requestBAContactBeans;
    }

    public Collection<BmsBaAuthForMobileEntity> getComponentRoleUserBeans() {
        return componentRoleUserBeans;
    }

    public void setComponentRoleUserBeans(Collection<BmsBaAuthForMobileEntity> componentRoleUserBeans) {
        this.componentRoleUserBeans = componentRoleUserBeans;
    }

    public Collection<BmsBaEventForMobileEntity> getRequestBAEventBeans() {
        return requestBAEventBeans;
    }

    public void setRequestBAEventBeans(Collection<BmsBaEventForMobileEntity> requestBAEventBeans) {
        this.requestBAEventBeans = requestBAEventBeans;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getClientContactPerson() {
        return clientContactPerson;
    }

    public void setClientContactPerson(String clientContactPerson) {
        this.clientContactPerson = clientContactPerson;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getContactsStatus() {
        return contactsStatus;
    }

    public void setContactsStatus(String contactsStatus) {
        this.contactsStatus = contactsStatus;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getInTime() {
        return inTime;
    }

    public void setInTime(Boolean inTime) {
        this.inTime = inTime;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerShipCode() {
        return ownerShipCode;
    }

    public void setOwnerShipCode(String ownerShipCode) {
        this.ownerShipCode = ownerShipCode;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    public String getRelationCode() {
        return relationCode;
    }

    public void setRelationCode(String relationCode) {
        this.relationCode = relationCode;
    }

    public String getScaleCode() {
        return scaleCode;
    }

    public void setScaleCode(String scaleCode) {
        this.scaleCode = scaleCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getValueCode() {
        return valueCode;
    }

    public void setValueCode(String valueCode) {
        this.valueCode = valueCode;
    }
}
