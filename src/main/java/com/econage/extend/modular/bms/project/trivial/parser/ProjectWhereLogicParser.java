package com.econage.extend.modular.bms.project.trivial.parser;

import com.econage.extend.modular.bms.project.trivial.wherelogic.ProjectWhereLogic;
import com.flowyun.cornerstone.db.mybatis.wherelogic.WhereLogicContext;
import com.flowyun.cornerstone.db.mybatis.wherelogic.WhereLogicParser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

public class ProjectWhereLogicParser {
    private static String projectSelectAuthParserSql(
            WhereLogicContext whereLogicContext,
            String expressReplaceKey,
            Collection<String> express
    ){

        ProjectWhereLogic whereLogicObj = (ProjectWhereLogic)whereLogicContext.getWhereLogicObj();

        if(CollectionUtils.isEmpty(whereLogicObj.getProjectSelectAuthExpress())){
            return StringUtils.EMPTY;
        }

        StringBuilder wherePart = new StringBuilder();
        boolean isAdminRole = false;
        if(express!=null&&express.size()==1){
            String oneValue = (String)express.toArray()[0];
            if(oneValue.equals("ALL")){
                isAdminRole = true;
            }
        }
        if(isAdminRole){
            wherePart.append(" 1=1 " );
        }else {
            wherePart.append(" exists( " +
                    "    select 1 from bms_project_auth " +
                    "    where project_id_ = bms_project_master.id_ " +
                    "    and express_ in (");

            wherePart.append(whereLogicContext.parseCollection(expressReplaceKey, express))
                    .append(") )");
        }
        return wherePart.toString();
    }
    public static class ProjectSelectAuthParser implements WhereLogicParser {
        @Override
        public String parseWhereLogic(WhereLogicContext whereLogicContext) {
            ProjectWhereLogic whereLogicObj = (ProjectWhereLogic)whereLogicContext.getWhereLogicObj();

            if(CollectionUtils.isEmpty(whereLogicObj.getProjectSelectAuthExpress())){
                return StringUtils.EMPTY;
            }

            return projectSelectAuthParserSql(whereLogicContext, "projectSelectAuthExpress",whereLogicObj.getProjectSelectAuthExpress());

        }
    }
}

