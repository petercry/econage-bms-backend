package com.econage.extend.modular.bms.kanban.trivial.wherelogic;

import com.flowyun.cornerstone.db.mybatis.wherelogic.WhereLogicContext;
import com.flowyun.cornerstone.db.mybatis.wherelogic.WhereLogicParser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

public class TaskWhereLogicParser {
    private static String userCollectSelectAuthParserSql(
            WhereLogicContext whereLogicContext,
            String expressReplaceKey,
            Collection<String> express
    ){

        TaskWhereLogic whereLogicObj = (TaskWhereLogic)whereLogicContext.getWhereLogicObj();

        if(CollectionUtils.isEmpty(whereLogicObj.getUserSelectAuthExpress())){
            return StringUtils.EMPTY;
        }

        StringBuilder wherePart = new StringBuilder();
        wherePart.append(" exists( " +
                "    select 1 from ecl_user_ " +
                "    where bms_dev_task.dealer_ = ecl_user_.id_  " +
                "    and ecl_user_.id_ in (" );

        wherePart.append(whereLogicContext.parseCollection(expressReplaceKey, express))
                .append(") )");

        return wherePart.toString();
    }
    private static String calendarDateArrangeSelectAuthParserSql(
            WhereLogicContext whereLogicContext,
            String expressReplaceKey,
            Collection<String> dateArrangeV
    ){

        TaskWhereLogic whereLogicObj = (TaskWhereLogic)whereLogicContext.getWhereLogicObj();
        if(CollectionUtils.isEmpty(whereLogicObj.getTaskCalendarArrangeSelectAuthExpress())){
            return StringUtils.EMPTY;
        }
        String dateFrom = (String)dateArrangeV.toArray()[0];
        String dateTo = (String)dateArrangeV.toArray()[1];
        StringBuilder wherePart = new StringBuilder();
        wherePart.append(" exists(select 1 from bms_task_calendar where bms_dev_task.id_ = bms_task_calendar.task_id_ and bms_task_calendar.date_ >= '"+dateFrom+"' and bms_task_calendar.date_<='"+dateTo+"')" );

        return wherePart.toString();
    }
    public static class TaskUserCollectSelectAuthParser implements WhereLogicParser {
        @Override
        public String parseWhereLogic(WhereLogicContext whereLogicContext) {
            TaskWhereLogic whereLogicObj = (TaskWhereLogic)whereLogicContext.getWhereLogicObj();

            if(CollectionUtils.isEmpty(whereLogicObj.getUserSelectAuthExpress())){
                return StringUtils.EMPTY;
            }

            return userCollectSelectAuthParserSql(whereLogicContext, "userSelectAuthExpress",whereLogicObj.getUserSelectAuthExpress());

        }
    }
    public static class TaskCalendarDateArrangeSelectAuthParser implements WhereLogicParser {
        @Override
        public String parseWhereLogic(WhereLogicContext whereLogicContext) {
            TaskWhereLogic whereLogicObj = (TaskWhereLogic)whereLogicContext.getWhereLogicObj();

            if(CollectionUtils.isEmpty(whereLogicObj.getTaskCalendarArrangeSelectAuthExpress())){
                return StringUtils.EMPTY;
            }

            return calendarDateArrangeSelectAuthParserSql(whereLogicContext, "taskCalendarArrangeSelectAuthExpress",whereLogicObj.getTaskCalendarArrangeSelectAuthExpress());

        }
    }
}
