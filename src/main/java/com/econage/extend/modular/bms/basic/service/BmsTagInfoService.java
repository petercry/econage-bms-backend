package com.econage.extend.modular.bms.basic.service;

import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.basic.entity.BmsTagInfoEntity;
import com.econage.extend.modular.bms.basic.mapper.BmsTagInfoMapper;
import com.econage.extend.modular.bms.basic.trivial.wherelogic.BmsTagInfoWhereLogic;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BmsTagInfoService extends ServiceImpl<BmsTagInfoMapper, BmsTagInfoEntity> {
    @Override
    protected void doRefreshSingleEntityBeforeInsert(BmsTagInfoEntity entity){
        if(entity.getOrderSeq() == null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
    @Transactional(rollbackFor = Throwable.class)
    public void refresh(String modual, String modualInnerId, Collection<String> tags){
        Assert.hasText(modual,"no modual");
        Assert.hasText(modualInnerId,"no modualInnerId");
        BmsTagInfoWhereLogic whereLogic = new BmsTagInfoWhereLogic();
        whereLogic.setModular(modual);
        whereLogic.setModularInnerId(modualInnerId);
        deleteByWhereLogic(whereLogic);

        if(CollectionUtils.isNotEmpty(tags)){
            ArrayList<BmsTagInfoEntity> tagEntities = Lists.newArrayList();
            for (String tag:tags) {
                BmsTagInfoEntity tagEntity= new BmsTagInfoEntity();
                tagEntity.setModular(modual);
                tagEntity.setModularInnerId(modualInnerId);
                tagEntity.setTagKeyId(tag);
                tagEntities.add(tagEntity);
            }
            insertBatch(tagEntities);
        }
    }
    private final String[] defaultSort = {"order_seq_"};
    private final String[] defaultOrder = {"desc"};
    public List<BmsTagInfoEntity> getSelectedTag(String modular, String modularInnerId){
        BmsTagInfoWhereLogic whereLogic = new BmsTagInfoWhereLogic();
        whereLogic.setModular(modular);
        whereLogic.setModularInnerId(modularInnerId);
        whereLogic.setSort(defaultSort);
        whereLogic.setOrder(defaultOrder);
        return selectListByWhereLogic(whereLogic);
    }
}
