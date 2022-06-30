package com.econage.extend.modular.bms.ba.entity;

import com.econage.extend.modular.bms.basic.entity.BmsTagInfoEntity;
import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@TableDef("bms_ba_master")
public class BmsBaEntity extends BaseEntity {
    private String id;
    @TableField(exist = false)
    private String baTagStr;

    @TableField(exist = false)
    private List<BmsTagInfoEntity> baTag;

    @TableField(exist = false)
    private List<BmsBaAssociationEntity> productDirectionOptions;

    @TableField(exist = false)
    private String productDirectionStr;

    public String getProductDirectionStr() {
        return productDirectionStr;
    }

    public void setProductDirectionStr(String productDirectionStr) {
        this.productDirectionStr = productDirectionStr;
    }

    public List<BmsBaAssociationEntity> getProductDirectionOptions() {
        return productDirectionOptions;
    }

    public void setProductDirectionOptions(List<BmsBaAssociationEntity> productDirectionOptions) {
        this.productDirectionOptions = productDirectionOptions;
    }

    public String getBaTagStr() {
        return baTagStr;
    }

    public void setBaTagStr(String baTagStr) {
        this.baTagStr = baTagStr;
    }

    public List<BmsTagInfoEntity> getBaTag() {
        return baTag;
    }

    public void setBaTag(List<BmsTagInfoEntity> baTag) {
        this.baTag = baTag;
    }

    public LocalDate getLastContactTime() {
        return lastContactTime;
    }

    public void setLastContactTime(LocalDate lastContactTime) {
        this.lastContactTime = lastContactTime;
    }

    public LocalDate getNextContactTime() {
        return nextContactTime;
    }

    public void setNextContactTime(LocalDate nextContactTime) {
        this.nextContactTime = nextContactTime;
    }

   @TableField(defaultUpdate = false)
    private Boolean valid;

    @TableField(defaultUpdate = false)
    private Long orderSeq;
    private String baName;
    private String shortName;
    private String code;
    private String[] stateAreaArray;
    private String stateArea;
    private String stateAreaDesc;

    private String productDirection;
    private Double projectBudget;
    private LocalDate expectTenderTime;
    private String currentPhase;
    private String competitiveSituation;

    public String getProductDirection() {
        return productDirection;
    }

    public void setProductDirection(String productDirection) {
        this.productDirection = productDirection;
    }

    public Double getProjectBudget() {
        return projectBudget;
    }

    public void setProjectBudget(Double projectBudget) {
        this.projectBudget = projectBudget;
    }

    public LocalDate getExpectTenderTime() {
        return expectTenderTime;
    }

    public void setExpectTenderTime(LocalDate expectTenderTime) {
        this.expectTenderTime = expectTenderTime;
    }

    public String getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(String currentPhase) {
        this.currentPhase = currentPhase;
    }

    public String getCompetitiveSituation() {
        return competitiveSituation;
    }

    public void setCompetitiveSituation(String competitiveSituation) {
        this.competitiveSituation = competitiveSituation;
    }

    public String[] getStateAreaArray() {
        return stateAreaArray;
    }

    public void setStateAreaArray(String[] stateAreaArray) {
        this.stateAreaArray = stateAreaArray;
    }

    public String getStateArea() {
        return stateArea;
    }

    public void setStateArea(String stateArea) {
        this.stateArea = stateArea;
    }

    public String getStateAreaDesc() {
        return stateAreaDesc;
    }

    public void setStateAreaDesc(String stateAreaDesc) {
        this.stateAreaDesc = stateAreaDesc;
    }

    public Long getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(Long orderSeq) {
        this.orderSeq = orderSeq;
    }

    private String relationCode;
    private String industryCode;
    private String firstStatus;
    private String secondStatus;
    private LocalDate lastContactTime;
    private String lastActionUser;
    private String valueCode;

    @TableField(exist = false)
    private String valueCodeDesc;

    @TableField(exist = false)
    private String firstStatusDesc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getValueCodeDesc() {
        return valueCodeDesc;
    }

    public void setValueCodeDesc(String valueCodeDesc) {
        this.valueCodeDesc = valueCodeDesc;
    }

    public String getFirstStatusDesc() {
        return firstStatusDesc;
    }

    public void setFirstStatusDesc(String firstStatusDesc) {
        this.firstStatusDesc = firstStatusDesc;
    }

    public String getBaName() {
        return baName;
    }

    public void setBaName(String baName) {
        this.baName = baName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRelationCode() {
        return relationCode;
    }

    public void setRelationCode(String relationCode) {
        this.relationCode = relationCode;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    public String getFirstStatus() {
        return firstStatus;
    }

    public void setFirstStatus(String firstStatus) {
        this.firstStatus = firstStatus;
    }

    public String getSecondStatus() {
        return secondStatus;
    }

    public void setSecondStatus(String secondStatus) {
        this.secondStatus = secondStatus;
    }


    public String getLastActionUser() {
        return lastActionUser;
    }

    public void setLastActionUser(String lastActionUser) {
        this.lastActionUser = lastActionUser;
    }

    public String getValueCode() {
        return valueCode;
    }

    public void setValueCode(String valueCode) {
        this.valueCode = valueCode;
    }

    public String getClientContactPerson() {
        return clientContactPerson;
    }

    public void setClientContactPerson(String clientContactPerson) {
        this.clientContactPerson = clientContactPerson;
    }

    public String getCurrentSoftware() {
        return currentSoftware;
    }

    public void setCurrentSoftware(String currentSoftware) {
        this.currentSoftware = currentSoftware;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getOwnershipCode() {
        return ownershipCode;
    }

    public void setOwnershipCode(String ownershipCode) {
        this.ownershipCode = ownershipCode;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getScaleCode() {
        return scaleCode;
    }

    public void setScaleCode(String scaleCode) {
        this.scaleCode = scaleCode;
    }

    public String getContactsStatus() {
        return contactsStatus;
    }

    public void setContactsStatus(String contactsStatus) {
        this.contactsStatus = contactsStatus;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getNumOfEmp() {
        return numOfEmp;
    }

    public void setNumOfEmp(String numOfEmp) {
        this.numOfEmp = numOfEmp;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    private String clientContactPerson;
    private String currentSoftware;
    private LocalDate nextContactTime;
    private String country;
    private String state;
    private String city;
    private String ownershipCode;
    private String posCode;
    private String customerType;
    private String scaleCode;
    private String contactsStatus;
    private String sourceCode;
    private String revenue;
    private String numOfEmp;
    private String phoneNo;
    private String faxNo;
    private String address;
    private String webUrl;
    private String emailAddr;
    private String bankName;
    private String bankAccount;
    private String taxId;
    private String comments;
    private LocalDateTime bizOppoTime;

    public LocalDateTime getBizOppoTime() {
        return bizOppoTime;
    }

    public void setBizOppoTime(LocalDateTime bizOppoTime) {
        this.bizOppoTime = bizOppoTime;
    }
}
