package com.econage.extend.modular.bms.afhwc.entity.dto;

import java.util.Set;

public class AfhwcInstanceFormRequestDTO {
    private String account;
    private long workflowId;
    private Set<String> itemApiKeys;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(long workflowId) {
        this.workflowId = workflowId;
    }

    public Set<String> getItemApiKeys() {
        return itemApiKeys;
    }

    public void setItemApiKeys(Set<String> itemApiKeys) {
        this.itemApiKeys = itemApiKeys;
    }
}
