package com.econage.extend.modular.bms.project.service;

import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.project.entity.BmsProjectAssociationEntity;
import com.econage.extend.modular.bms.project.mapper.BmsProjectAssociationMapper;
import com.econage.extend.modular.bms.project.trivial.wherelogic.ProjectAssociationWhereLogic;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class BmsProjectAssociationService extends ServiceImpl<BmsProjectAssociationMapper, BmsProjectAssociationEntity> {
    @Override
    protected void doRefreshSingleEntityBeforeInsert(BmsProjectAssociationEntity entity){
        if(entity.getOrderSeq() == null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
    @Transactional(rollbackFor = Throwable.class)
    public void refresh(String modual, String projectId, Collection<String> baIds){
        Assert.hasText(modual,"no modual");
        Assert.hasText(projectId,"no projectId");
        ProjectAssociationWhereLogic whereLogic = new ProjectAssociationWhereLogic();
        whereLogic.setModular(modual);
        whereLogic.setProjectId(projectId);
        deleteByWhereLogic(whereLogic);

        if(CollectionUtils.isNotEmpty(baIds)){
            ArrayList<BmsProjectAssociationEntity> paEntities = Lists.newArrayList();
            for (String nodeBaId:baIds) {
                BmsProjectAssociationEntity bpae= new BmsProjectAssociationEntity();
                bpae.setModular(modual);
                bpae.setProjectId(projectId);
                bpae.setModularInnerId(nodeBaId);
                paEntities.add(bpae);
            }
            insertBatch(paEntities);
        }
    }

}
