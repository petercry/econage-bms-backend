package com.econage.extend.modular.bms.project.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;

@TableDef("bms_project_association")
public class BmsProjectAssociationEntity extends BaseEntity {
    private String id;
    @TableField(defaultUpdate = false)
    private Long orderSeq;
    private String projectId;
    private String modular;
    private String modularInnerId;

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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
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
}
