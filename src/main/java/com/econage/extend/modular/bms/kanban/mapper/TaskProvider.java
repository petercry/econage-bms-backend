package com.econage.extend.modular.bms.kanban.mapper;

import com.flowyun.cornerstone.db.mybatis.mapper.provider.MybatisProviderContext;
import org.apache.ibatis.annotations.Param;

public class TaskProvider {
    public static String countChildrenTaskByRequireSQL(MybatisProviderContext context, @Param("requireId") String requireId){
        return "select count(1) from  bms_dev_task where require_id_ = #{requireId} and valid_ = 1";
    }
    public static String sumUsedManHour(MybatisProviderContext context, @Param("taskId") String taskId , @Param("exceptCalendarId") String exceptCalendarId){
        return "select sum(feedback_man_hour_) from bms_task_calendar where task_id_ = #{taskId} and valid_ = 1 and id_ != #{exceptCalendarId}";
    }
    public static String countCalendarByTaskSQL(MybatisProviderContext context, @Param("taskId") String taskId ){
        return "select count(1) from bms_task_calendar where task_id_ = #{taskId} and valid_ = 1 ";
    }
}
