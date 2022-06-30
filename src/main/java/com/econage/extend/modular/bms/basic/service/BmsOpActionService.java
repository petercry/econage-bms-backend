package com.econage.extend.modular.bms.basic.service;


import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.core.basic.util.IdWorker;
import com.econage.core.basic.util.SystemClock;
import com.econage.core.service.ServiceImpl;
import com.econage.core.web.extension.response.BasicDataGridRows;
import com.econage.extend.modular.bms.basic.entity.BmsOpActionDetailEntity;
import com.econage.extend.modular.bms.basic.entity.BmsOpActionEntity;
import com.econage.extend.modular.bms.basic.mapper.BmsOpActionMapper;
import com.econage.extend.modular.bms.basic.trivial.wherelogic.BmsOpActionDetailWhereLogic;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class BmsOpActionService  extends ServiceImpl<BmsOpActionMapper, BmsOpActionEntity> {
    private UserUnionQuery userUnionQuery;
    private BmsOpActionDetailService detService;
    @Autowired
    protected void setUserServiceUnionQuery(UserUnionQuery userUnionQuery) {
        this.userUnionQuery = userUnionQuery;
    }
    @Autowired
    protected void setDetService(BmsOpActionDetailService detService) {
        this.detService = detService;
    }
    private final static List<String> validUpdateCols = ImmutableList.of("valid","modDate","modUser");
    @Transactional(rollbackFor = Throwable.class)
    public boolean changeOpActionStatus(String actionId , boolean status){
        if(StringUtils.isEmpty(actionId)){
            return false;
        }
        BmsOpActionEntity bmsOpActionEntity = new BmsOpActionEntity();
        bmsOpActionEntity.setId(actionId);
        bmsOpActionEntity.setValid(status);
        return updatePartialColumnById(bmsOpActionEntity,validUpdateCols);
    }
    @Override
    protected void doRefreshSingleEntityBeforeInsert(BmsOpActionEntity entity){
        entity.setValid(true);
        if(entity.getOrderSeq() == null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
        entity.setDate(SystemClock.nowDateTime());
        entity.setActor(getRuntimeUserId());
    }
    /*
    public void addSingleAction(String objType ,String objId , String objParentId,String action,String comments){
        BmsOpActionEntity bmsOpActionEntity = new BmsOpActionEntity();
        bmsOpActionEntity.setObjectType(objType);
        bmsOpActionEntity.setObjectId(objId);
        bmsOpActionEntity.setObjectParentId(objParentId);
        bmsOpActionEntity.setAction(action);
        bmsOpActionEntity.setComments(comments);
        insert(bmsOpActionEntity);
    }*/
    public Collection<BmsOpActionEntity> extSearchOpAction(String objType , String objId ,String extObjType){
        Collection<BmsOpActionEntity> v = getMapper().extSearchOpAction(objType , objId , extObjType);
        for(BmsOpActionEntity entity : v){
            entity.setActorName(userUnionQuery.selectUserMi(entity.getActor()));
            BmsOpActionDetailWhereLogic detWhereLogic = new BmsOpActionDetailWhereLogic();
            detWhereLogic.setActionId(entity.getId());
            BasicDataGridRows detV = BasicDataGridRows.create().withRows(detService.selectListByWhereLogic(detWhereLogic));
            entity.setDetail((Collection<BmsOpActionDetailEntity>) detV.getRows());
        }
        return v;
    }
}
