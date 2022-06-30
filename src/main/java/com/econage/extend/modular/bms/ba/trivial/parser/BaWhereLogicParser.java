package com.econage.extend.modular.bms.ba.trivial.parser;

import com.econage.extend.modular.bms.ba.trivial.wherelogic.BaWhereLogic;
import com.flowyun.cornerstone.db.mybatis.wherelogic.WhereLogicContext;
import com.flowyun.cornerstone.db.mybatis.wherelogic.WhereLogicParser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

public class BaWhereLogicParser {
    private static String baSelectAuthParserSql(
            WhereLogicContext whereLogicContext,
            String expressReplaceKey,
            Collection<String> express
    ){

        BaWhereLogic whereLogicObj = (BaWhereLogic)whereLogicContext.getWhereLogicObj();

        if(CollectionUtils.isEmpty(whereLogicObj.getBaSelectAuthExpress())){
            return StringUtils.EMPTY;
        }

        StringBuilder wherePart = new StringBuilder();
        wherePart.append(" exists( " +
                "    select 1 from bms_ba_auth " +
                "    where ba_id_ = bms_ba_master.id_ " +
                "    and bms_ba_auth.valid_ = true and express_ in (" );
        if(StringUtils.isEmpty(whereLogicObj.getAuthPart())){
            wherePart.append(whereLogicContext.parseCollection(expressReplaceKey, express))
                    .append(")  )");
        }else {
            wherePart.append(whereLogicContext.parseCollection(expressReplaceKey, express))
                    .append(") and key_ = '" + whereLogicObj.getAuthPart() + "' )");
        }

        return wherePart.toString();
    }
    public static class BaSelectAuthParser implements WhereLogicParser {
        @Override
        public String parseWhereLogic(WhereLogicContext whereLogicContext) {
            BaWhereLogic whereLogicObj = (BaWhereLogic)whereLogicContext.getWhereLogicObj();

            if(CollectionUtils.isEmpty(whereLogicObj.getBaSelectAuthExpress())){
                return StringUtils.EMPTY;
            }

            return baSelectAuthParserSql(whereLogicContext, "baSelectAuthExpress",whereLogicObj.getBaSelectAuthExpress());

        }
    }
}
