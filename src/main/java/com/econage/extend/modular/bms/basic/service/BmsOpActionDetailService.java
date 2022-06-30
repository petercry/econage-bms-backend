package com.econage.extend.modular.bms.basic.service;

import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.basic.entity.BmsOpActionDetailEntity;
import com.econage.extend.modular.bms.basic.mapper.BmsOpActionDetailMapper;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BmsOpActionDetailService  extends ServiceImpl<BmsOpActionDetailMapper, BmsOpActionDetailEntity> {
    private final static List<String> validUpdateCols = ImmutableList.of("valid","modDate","modUser");
    @Transactional(rollbackFor = Throwable.class)
    public boolean changeOpActionDetailStatus(String actionDetId , boolean status){
        if(StringUtils.isEmpty(actionDetId)){
            return false;
        }
        BmsOpActionDetailEntity bmsOpActionDetailEntity = new BmsOpActionDetailEntity();
        bmsOpActionDetailEntity.setId(actionDetId);
        bmsOpActionDetailEntity.setValid(status);
        return updatePartialColumnById(bmsOpActionDetailEntity,validUpdateCols);
    }
    @Override
    protected void doRefreshSingleEntityBeforeInsert(BmsOpActionDetailEntity entity){
        entity.setValid(true);
        if(entity.getOrderSeq() == null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
}
