package com.econage.extend.modular.bms.talentpool.trival.wherelogic;

import com.econage.core.service.BasicWhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogicField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@WhereLogic
public class TalentPoolFollowWhereLogic extends BasicWhereLogic {
    private String infoId;
    private Integer type;
    @WhereLogicField(wherePart = " round_date_ > now() ")
    private Boolean isSearchLaterThanNow;

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getSearchLaterThanNow() {
        return isSearchLaterThanNow;
    }

    public void setSearchLaterThanNow(Boolean searchLaterThanNow) {
        isSearchLaterThanNow = searchLaterThanNow;
    }
}
