package com.econage.extend.modular.bms.basic.trivial.wherelogic;

import com.econage.extend.modular.bms.ba.trivial.wherelogic.BaWhereLogic;
import com.flowyun.cornerstone.db.mybatis.wherelogic.WhereLogicContext;
import com.flowyun.cornerstone.db.mybatis.wherelogic.WhereLogicParser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

public class BmsTagInfoWhereLogicParser {
    private static String tagKeyCollecParserSql(
            WhereLogicContext whereLogicContext,
            String expressReplaceKey,
            Collection<String> express
    ){

        BaWhereLogic whereLogicObj = (BaWhereLogic)whereLogicContext.getWhereLogicObj();

        if(CollectionUtils.isEmpty(whereLogicObj.getTagKeyCollecExpress())){
            return StringUtils.EMPTY;
        }

        StringBuilder wherePart = new StringBuilder();
        wherePart.append(" exists(select 1 from bms_tag_info where bms_tag_info.modular_='ba' and bms_ba_master.id_ = bms_tag_info.modular_inner_id_ and bms_tag_info.tag_key_id_ in( " );

        wherePart.append(whereLogicContext.parseCollection(expressReplaceKey, express))
                .append(") )");

        return wherePart.toString();
    }
    public static class TagKeyCollecParser implements WhereLogicParser {
        @Override
        public String parseWhereLogic(WhereLogicContext whereLogicContext) {
            BaWhereLogic whereLogicObj = (BaWhereLogic)whereLogicContext.getWhereLogicObj();

            if(CollectionUtils.isEmpty(whereLogicObj.getTagKeyCollecExpress())){
                return StringUtils.EMPTY;
            }

            return tagKeyCollecParserSql(whereLogicContext, "tagKeyCollecExpress",whereLogicObj.getTagKeyCollecExpress());

        }
    }
}
