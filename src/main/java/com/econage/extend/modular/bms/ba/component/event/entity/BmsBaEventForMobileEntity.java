package com.econage.extend.modular.bms.ba.component.event.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BmsBaEventForMobileEntity {
    private LocalDateTime action_date;
    private String action_hour;
    private String action_user_id;
    private String ba_id;
    private String comments;
    private String contact_person;
    private LocalDateTime createDate;
    private String createUser;
    private String id;
    private LocalDateTime lmdate;
    private String lmuser;
    private LocalDate next_contact_time;
    private Long order_id;
    private String status;
    private String subject;
    private String type_id;

    public LocalDate getNext_contact_time() {
        return next_contact_time;
    }

    public void setNext_contact_time(LocalDate next_contact_time) {
        this.next_contact_time = next_contact_time;
    }

    public LocalDateTime getAction_date() {
        return action_date;
    }

    public void setAction_date(LocalDateTime action_date) {
        this.action_date = action_date;
    }

    public String getAction_hour() {
        return action_hour;
    }

    public void setAction_hour(String action_hour) {
        this.action_hour = action_hour;
    }

    public String getAction_user_id() {
        return action_user_id;
    }

    public void setAction_user_id(String action_user_id) {
        this.action_user_id = action_user_id;
    }

    public String getBa_id() {
        return ba_id;
    }

    public void setBa_id(String ba_id) {
        this.ba_id = ba_id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
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

    public LocalDateTime getLmdate() {
        return lmdate;
    }

    public void setLmdate(LocalDateTime lmdate) {
        this.lmdate = lmdate;
    }

    public String getLmuser() {
        return lmuser;
    }

    public void setLmuser(String lmuser) {
        this.lmuser = lmuser;
    }


    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }
}
