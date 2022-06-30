package com.econage.extend.modular.bms.kanban.service;

import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.basic.service.BmsOpActionService;
import com.econage.extend.modular.bms.kanban.entity.TaskCalendarEntity;
import com.econage.extend.modular.bms.kanban.mapper.TaskCalendarMapper;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class TaskCalendarService  extends ServiceImpl<TaskCalendarMapper, TaskCalendarEntity> {
    private BmsOpActionService actionService;
    private UserUnionQuery userUnionQuery;
    @Autowired
    protected void setUserServiceUnionQuery(UserUnionQuery userUnionQuery) {
        this.userUnionQuery = userUnionQuery;
    }
    @Autowired
    protected void setActionService(BmsOpActionService actionService) {
        this.actionService = actionService;
    }
    private final static List<String> validUpdateCols = ImmutableList.of("valid","modDate","modUser");
    @Transactional(rollbackFor = Throwable.class)
    public boolean changeTaskCalendarValid(String id , boolean valid){
        if(StringUtils.isEmpty(id)){
            return false;
        }
        TaskCalendarEntity taskCalendarEntity = new TaskCalendarEntity();
        taskCalendarEntity.setId(id);
        taskCalendarEntity.setValid(valid);
        return updatePartialColumnById(taskCalendarEntity,validUpdateCols);
    }
    @Override
    protected void doRefreshSingleEntityBeforeInsert(TaskCalendarEntity entity){
        entity.setValid(true);
        if(entity.getOrderSeq()==null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
    public String getMyUserId(){
        return getRuntimeUserId();
    }
    @Override
    protected void doFillFkRelationship(Collection<TaskCalendarEntity> entities){
        for(TaskCalendarEntity entity : entities){
            entity.setDealerName(userUnionQuery.selectUserMi(entity.getDealer()));
            if(StringUtils.isEmpty(entity.getDealerName())) entity.setDealerName(entity.getDealer());
            entity.setPlanInputerName(userUnionQuery.selectUserMi(entity.getPlanInputer()));
            if(StringUtils.isEmpty(entity.getPlanInputerName())) entity.setPlanInputerName(entity.getPlanInputer());
            entity.setFeedbackInputerName(userUnionQuery.selectUserMi(entity.getFeedbackInputer()));
            if(StringUtils.isEmpty(entity.getFeedbackInputerName())) entity.setFeedbackInputerName(entity.getFeedbackInputer());
        }
    }
    @Override
    protected void doRefreshSingleEntityAfterInsert(TaskCalendarEntity taskCalendarEntityEntity){
        //actionService.addSingleAction(ObjTypeForOpActionEnum.TASKCALENDAR.getTypeFlag(),taskCalendarEntityEntity.getId(),taskCalendarEntityEntity.getTaskId(), OpActionEnum.CREATE.getActionFlag(),null);
    }
    @Override
    protected void doRefreshSingleEntityAfterUpdate(TaskCalendarEntity taskCalendarEntityEntity){
        /*
        if(taskCalendarEntityEntity.getFeedbackCreateTime()!=null){
            actionService.addSingleAction(ObjTypeForOpActionEnum.TASKCALENDAR.getTypeFlag(),taskCalendarEntityEntity.getId(),taskCalendarEntityEntity.getTaskId(), OpActionEnum.FEEDBACK.getActionFlag(),null);
        }*/
    }
}
