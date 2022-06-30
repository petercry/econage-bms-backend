package com.econage.extend.modular.bms.afhwc.entity.dto;


import com.econage.extend.modular.bms.afhwc.entity.model.AfhwcWorkflowInstModel;

public class AfhwcWorkflowInstResponseDTO extends AfhwcBasicDTO {
    private AfhwcWorkflowInstModel data;

    public AfhwcWorkflowInstModel getData() {
        return data;
    }

    public void setData(AfhwcWorkflowInstModel data) {
        this.data = data;
    }
}
