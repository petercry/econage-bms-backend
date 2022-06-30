package com.econage.extend.modular.bms.talentpool.trival.wherelogic.parser;

import com.econage.extend.modular.bms.talentpool.trival.wherelogic.TalentPoolInfoWhereLogic;
import com.flowyun.cornerstone.db.mybatis.wherelogic.WhereLogicContext;
import com.flowyun.cornerstone.db.mybatis.wherelogic.WhereLogicParser;
import org.apache.commons.collections4.CollectionUtils;

public class TalentPoolLabelParser implements WhereLogicParser {
    @Override
    public String parseWhereLogic(WhereLogicContext whereLogicContext){
        TalentPoolInfoWhereLogic whereLogic = (TalentPoolInfoWhereLogic)whereLogicContext.getWhereLogicObj();

        if(CollectionUtils.isEmpty(whereLogic.getLabel())){
            return " 1=1 ";
        }
        return " exists (select 1 from bms_talent_info_label_ where info_id_ = bms_talent_info_.id_ " +
                "        and label_ in ("+whereLogicContext.parseCollection("label", whereLogic.getLabel()) +") )  ";

    }
}
