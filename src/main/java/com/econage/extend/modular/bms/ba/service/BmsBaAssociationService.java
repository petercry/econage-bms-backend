package com.econage.extend.modular.bms.ba.service;

import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.ba.entity.BmsBaAssociationEntity;
import com.econage.extend.modular.bms.ba.mapper.BmsBaAssociationMapper;
import com.econage.extend.modular.bms.ba.trivial.wherelogic.BaAssociationWhereLogic;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
@Service
public class BmsBaAssociationService  extends ServiceImpl<BmsBaAssociationMapper, BmsBaAssociationEntity> {
    @Override
    protected void doRefreshSingleEntityBeforeInsert(BmsBaAssociationEntity entity){
    }
    @Transactional(rollbackFor = Throwable.class)
    public void refresh(String modual, String baId, Collection<String> modularInnerIds){
        Assert.hasText(modual,"no modual");
        Assert.hasText(baId,"no baId");
        BaAssociationWhereLogic whereLogic = new BaAssociationWhereLogic();
        whereLogic.setModular(modual);
        whereLogic.setBaId(baId);
        deleteByWhereLogic(whereLogic);

        if(CollectionUtils.isNotEmpty(modularInnerIds)){
            ArrayList<BmsBaAssociationEntity> baAssociationEntities = Lists.newArrayList();
            for (String nodeModularInnerId:modularInnerIds) {
                BmsBaAssociationEntity bbae= new BmsBaAssociationEntity();
                bbae.setModular(modual);
                bbae.setBaId(baId);
                bbae.setModularInnerId(nodeModularInnerId);
                baAssociationEntities.add(bbae);
            }
            insertBatch(baAssociationEntities);
        }
    }

}
