package com.econage.extend.modular.bms.ba.component.auth.trival.tempBean;

import com.flowyun.cornerstone.db.mybatis.entity.BasicEntity;

public class OrgByMiBean implements BasicEntity {
    private String id;
    private String mi;
    private String org;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMi() {
        return mi;
    }

    public void setMi(String mi) {
        this.mi = mi;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }
}
