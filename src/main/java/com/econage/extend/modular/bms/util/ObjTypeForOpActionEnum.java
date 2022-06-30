package com.econage.extend.modular.bms.util;

public enum ObjTypeForOpActionEnum {
    TASKCALENDAR( 1,"taskCalendar","任务日程"),
    TASK(2,"task","任务"),
    ;
    private final Integer idx;
    private final String typeFlag;
    private final String typeDesc;
    ObjTypeForOpActionEnum(Integer idx, String typeFlag , String typeDesc) {
        this.idx = idx;
        this.typeFlag = typeFlag;
        this.typeDesc = typeDesc;
    }
    public Integer getIdx() {
        return idx;
    }

    public String getTypeFlag() {
        return typeFlag;
    }

    public String getTypeDesc() {
        return typeDesc;
    }
}
