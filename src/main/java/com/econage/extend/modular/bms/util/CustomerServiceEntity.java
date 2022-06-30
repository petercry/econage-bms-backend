package com.econage.extend.modular.bms.util;

import com.flowyun.cornerstone.db.mybatis.annotations.TableField;

public class CustomerServiceEntity {
    @TableField(exist = false)
    private String reqDataId;

    @TableField(exist = false)
    private String taskDataId;

    @TableField(exist = false)
    private String taskId;

    private String rpaCode ;
    private String customerName ;
    private String customerContact ;
    private String contactContent;

    public String getReqDataId() {
        return reqDataId;
    }

    public void setReqDataId(String reqDataId) {
        this.reqDataId = reqDataId;
    }

    public String getTaskDataId() {
        return taskDataId;
    }

    public void setTaskDataId(String taskDataId) {
        this.taskDataId = taskDataId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getRpaCode() {
        return rpaCode;
    }

    public void setRpaCode(String rpaCode) {
        this.rpaCode = rpaCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getContactContent() {
        return contactContent;
    }

    public void setContactContent(String contactContent) {
        this.contactContent = contactContent;
    }
}
