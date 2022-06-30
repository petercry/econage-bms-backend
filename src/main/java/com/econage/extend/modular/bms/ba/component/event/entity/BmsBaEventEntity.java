package com.econage.extend.modular.bms.ba.component.event.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@TableDef("bms_ba_event")
public class BmsBaEventEntity extends BaseEntity {
    private String id;
    private String baId;
    private boolean valid;
    private Long orderSeq;
    private String typeId;
    private String contactPerson;
    private String actionUser;
    private LocalDateTime actionDate;
    private String subject;
    private String comments;
    private LocalDate nextContactDate;
    private String dataJournalId;
    private String nextPlan;

    @TableField(exist = false)
    private String extContent;

    @TableField(exist = false)
    private String senderName;

    public String getNextPlan() {
        return nextPlan;
    }

    public void setNextPlan(String nextPlan) {
        this.nextPlan = nextPlan;
    }

    public LocalDate getNextContactDate() {
        return nextContactDate;
    }

    public void setNextContactDate(LocalDate nextContactDate) {
        this.nextContactDate = nextContactDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaId() {
        return baId;
    }

    public void setBaId(String baId) {
        this.baId = baId;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Long getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(Long orderSeq) {
        this.orderSeq = orderSeq;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getActionUser() {
        return actionUser;
    }

    public void setActionUser(String actionUser) {
        this.actionUser = actionUser;
    }

    public LocalDateTime getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDateTime actionDate) {
        this.actionDate = actionDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getDataJournalId() {
        return dataJournalId;
    }

    public void setDataJournalId(String dataJournalId) {
        this.dataJournalId = dataJournalId;
    }

    public String getExtContent() {
        return extContent;
    }

    public void setExtContent(String extContent) {
        this.extContent = extContent;
    }
}
