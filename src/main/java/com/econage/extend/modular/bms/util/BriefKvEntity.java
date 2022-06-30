package com.econage.extend.modular.bms.util;

public class BriefKvEntity {
    public BriefKvEntity(Integer keyId, String value) {
        this.keyId = keyId;
        this.value = value;
    }

    private Integer keyId;
    private String value;

    public Integer getKeyId() {
        return keyId;
    }

    public void setKeyId(Integer keyId) {
        this.keyId = keyId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
