package com.econage.extend.modular.bms.ba.component.event.service;

import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.ba.component.event.entity.BmsBaEventEntity;
import com.econage.extend.modular.bms.ba.component.event.mapper.BmsBaEventMapper;
import com.econage.extend.modular.bms.ba.service.BmsBaService;
import com.econage.extend.modular.bms.basic.entity.BmsDataJournalEntity;
import com.econage.extend.modular.bms.basic.service.BmsDataJournalService;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class BmsBaEventService extends ServiceImpl<BmsBaEventMapper, BmsBaEventEntity> {
    private UserUnionQuery userUnionQuery;
    private BmsBaService bmsBaService;
    private BmsDataJournalService dataJournalService;
    @Autowired
    protected void setUserServiceUnionQuery(UserUnionQuery userUnionQuery) {
        this.userUnionQuery = userUnionQuery;
    }
    @Autowired
    protected void setBaService(BmsBaService bmsBaService) {
        this.bmsBaService = bmsBaService;
    }
    @Autowired
    protected void setBmsDataJournalService(BmsDataJournalService dataJournalService) {
        this.dataJournalService = dataJournalService;
    }
    @Override
    protected void doFillFkRelationship(Collection<BmsBaEventEntity> entities){
        for(BmsBaEventEntity entity : entities){
            entity.setSenderName(userUnionQuery.selectUserMi(entity.getCreateUser()));
            if(StringUtils.isEmpty(entity.getSenderName())) entity.setSenderName(entity.getCreateUser());
            if(!StringUtils.isEmpty(entity.getDataJournalId())){
                BmsDataJournalEntity bmsDataJournalEntity = dataJournalService.selectById(entity.getDataJournalId());
                if(bmsDataJournalEntity!=null){
                    entity.setExtContent(bmsDataJournalEntity.getExtContent());
                }
            }
        }
    }
    private final static List<String> validUpdateCols = ImmutableList.of("valid","modDate","modUser");
    @Transactional(rollbackFor = Throwable.class)
    public boolean changeBaEventValid(String eventId , boolean status){
        if(StringUtils.isEmpty(eventId)){
            return false;
        }
        BmsBaEventEntity bmsBaEventEntity = new BmsBaEventEntity();
        bmsBaEventEntity.setId(eventId);
        bmsBaEventEntity.setValid(status);
        return updatePartialColumnById(bmsBaEventEntity,validUpdateCols);
    }
    @Override
    protected void doRefreshSingleEntityBeforeInsert(BmsBaEventEntity entity){
        entity.setValid(true);
        if(StringUtils.isEmpty(entity.getId())||"0".equals(entity.getId())){
            entity.setId(IdWorker.getIdStr());
        }
        if(entity.getOrderSeq()==null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
    @Override
    protected void doRefreshSingleEntityAfterInsert(BmsBaEventEntity entity){
        bmsBaService.updateBaEntityWhenUpdateEvent(entity.getBaId() , entity);
    }
    @Override
    protected void doRefreshSingleEntityAfterUpdate(BmsBaEventEntity entity){
        if(StringUtils.isEmpty(entity.getBaId())) return;
        bmsBaService.updateBaEntityWhenUpdateEvent(entity.getBaId() , entity);
    }

}
