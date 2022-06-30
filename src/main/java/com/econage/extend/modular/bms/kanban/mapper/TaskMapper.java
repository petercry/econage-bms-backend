package com.econage.extend.modular.bms.kanban.mapper;

import com.econage.extend.modular.bms.kanban.entity.TaskEntity;
import com.flowyun.cornerstone.db.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

public interface TaskMapper extends BaseMapper<TaskEntity> {
    @SelectProvider(type = TaskProvider.class,method = "countChildrenTaskByRequireSQL")
    Integer countChildTaskByRequire(@Param("requireId") String requireId);

    @SelectProvider(type = TaskProvider.class,method = "countCalendarByTaskSQL")
    Integer countCalendarByTask(@Param("taskId") String taskId);

    @SelectProvider(type = TaskProvider.class,method = "sumUsedManHour")
    Double sumUsedManHour(@Param("taskId") String taskId , @Param("exceptCalendarId") String exceptCalendarId);
}
