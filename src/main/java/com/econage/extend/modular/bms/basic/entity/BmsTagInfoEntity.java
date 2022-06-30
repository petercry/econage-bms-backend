package com.econage.extend.modular.bms.basic.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;
@TableDef("bms_tag_info")
public class BmsTagInfoEntity extends BaseEntity {
    private String id;

    @TableField(defaultUpdate = false)
    private Long orderSeq;

    private String modular;
    private String modularInnerId;
    private String tagKeyId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(Long orderSeq) {
        this.orderSeq = orderSeq;
    }

    public String getModular() {
        return modular;
    }

    public void setModular(String modular) {
        this.modular = modular;
    }

    public String getModularInnerId() {
        return modularInnerId;
    }

    public void setModularInnerId(String modularInnerId) {
        this.modularInnerId = modularInnerId;
    }

    public String getTagKeyId() {
        return tagKeyId;
    }

    public void setTagKeyId(String tagKeyId) {
        this.tagKeyId = tagKeyId;
    }
}
