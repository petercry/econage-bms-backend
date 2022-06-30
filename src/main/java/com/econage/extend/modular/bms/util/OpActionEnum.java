package com.econage.extend.modular.bms.util;

public enum OpActionEnum  {
    CREATE( 1,"create","创建"),
    EDIT(2,"edit","编辑"),
    DELETE(3,"delete","删除"),
    FEEDBACK(4,"feedback","反馈")
    ;

    private final Integer idx;
    private final String actionFlag;
    private final String desc;

    OpActionEnum(Integer idx, String actionFlag , String desc) {
        this.idx = idx;
        this.actionFlag = actionFlag;
        this.desc = desc;
    }

    public Integer getIdx() {
        return idx;
    }

    public String getActionFlag() {
        return actionFlag;
    }

    public String getDesc() {
        return desc;
    }
}
