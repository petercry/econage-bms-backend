package com.econage.extend.modular.bms.afhwc.entity.dto;

import com.econage.extend.modular.bms.afhwc.entity.model.AfhwcDriveTaskModel;
import com.fasterxml.jackson.databind.JsonNode;

public class AfhwcDriveTaskResponseDTO extends AfhwcBasicDTO {
    private JsonNode data;
    private AfhwcDriveTaskModel model;

    public AfhwcDriveTaskModel getModel() {
        return model;
    }

    public void setModel(AfhwcDriveTaskModel model) {
        this.model = model;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }
}
