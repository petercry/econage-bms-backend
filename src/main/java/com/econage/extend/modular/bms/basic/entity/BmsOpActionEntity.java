package com.econage.extend.modular.bms.basic.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.Collection;

@TableDef("bms_op_action")
public class BmsOpActionEntity extends BaseEntity {
    private String id;
    @TableField(defaultUpdate = false)
    private boolean valid;

    @TableField(defaultUpdate = false)
    private Long orderSeq;
    private String objectType;
    private String objectId;
    private String objectParentId;
    private LocalDateTime date;
    private String action;
    private String actor;
    private String comments;
    private String extra;
    @TableField(exist = false)
    private String actorName;
    @TableField(exist = false)
    private Collection<BmsOpActionDetailEntity> detail;

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public Collection<BmsOpActionDetailEntity> getDetail() {
        return detail;
    }

    public void setDetail(Collection<BmsOpActionDetailEntity> detail) {
        this.detail = detail;
    }

    public String getObjectParentId() {
        return objectParentId;
    }

    public void setObjectParentId(String objectParentId) {
        this.objectParentId = objectParentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Long getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(Long orderSeq) {
        this.orderSeq = orderSeq;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
