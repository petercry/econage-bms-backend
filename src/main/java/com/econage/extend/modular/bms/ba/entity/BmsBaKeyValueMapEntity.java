package com.econage.extend.modular.bms.ba.entity;

import java.util.Map;

public class BmsBaKeyValueMapEntity {
    Map<String,String> contactValueCodeMap;
    Map<String,String> eventContactTypeMap;
    Map<String,String> scaleCodeMap;
    Map<String,String> valueCodeMap;
    Map<String,String> ownerShipMap;
    Map<String, String> contactPersonMap;

    public Map<String, String> getContactValueCodeMap() {
        return contactValueCodeMap;
    }

    public void setContactValueCodeMap(Map<String, String> contactValueCodeMap) {
        this.contactValueCodeMap = contactValueCodeMap;
    }

    public Map<String, String> getEventContactTypeMap() {
        return eventContactTypeMap;
    }

    public void setEventContactTypeMap(Map<String, String> eventContactTypeMap) {
        this.eventContactTypeMap = eventContactTypeMap;
    }

    public Map<String, String> getScaleCodeMap() {
        return scaleCodeMap;
    }

    public void setScaleCodeMap(Map<String, String> scaleCodeMap) {
        this.scaleCodeMap = scaleCodeMap;
    }

    public Map<String, String> getValueCodeMap() {
        return valueCodeMap;
    }

    public void setValueCodeMap(Map<String, String> valueCodeMap) {
        this.valueCodeMap = valueCodeMap;
    }

    public Map<String, String> getOwnerShipMap() {
        return ownerShipMap;
    }

    public void setOwnerShipMap(Map<String, String> ownerShipMap) {
        this.ownerShipMap = ownerShipMap;
    }

    public Map<String, String> getContactPersonMap() {
        return contactPersonMap;
    }

    public void setContactPersonMap(Map<String, String> contactPersonMap) {
        this.contactPersonMap = contactPersonMap;
    }
}
