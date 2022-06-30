package com.econage.extend.modular.bms.ba.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;

@TableDef("bms_ba_association")
public class BmsBaAssociationEntity extends BaseEntity {
    private String id;
    private String baId;
    private String modular;
    private String modularInnerId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaId() {
        return baId;
    }

    public void setBaId(String baId) {
        this.baId = baId;
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
