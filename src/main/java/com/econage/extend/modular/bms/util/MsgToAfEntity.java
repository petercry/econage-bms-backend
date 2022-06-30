package com.econage.extend.modular.bms.util;

import java.time.LocalDateTime;

public class MsgToAfEntity {
    private String modular;
    private String modularId;
    private String sourceId;
    private String action;
    private String msg;
    private String senderName;
    private String sendTime;
    private String comment;
    private String toDealerName;

    public String getToDealerName() {
        return toDealerName;
    }

    public void setToDealerName(String toDealerName) {
        this.toDealerName = toDealerName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getModularId() {
        return modularId;
    }

    public void setModularId(String modularId) {
        this.modularId = modularId;
    }

    public String getModular() {
        return modular;
    }

    public void setModular(String modular) {
        this.modular = modular;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
