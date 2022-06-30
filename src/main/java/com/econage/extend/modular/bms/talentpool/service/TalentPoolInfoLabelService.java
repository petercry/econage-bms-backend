package com.econage.extend.modular.bms.talentpool.service;

import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.talentpool.entity.BmsTalentInfoLabelEntity;
import com.econage.extend.modular.bms.talentpool.mapper.BmsTalentInfoLabelMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;


/**
 * @author econage
 */
@Service
public class TalentPoolInfoLabelService extends ServiceImpl<BmsTalentInfoLabelMapper, BmsTalentInfoLabelEntity> {

    @Transactional(rollbackFor = Throwable.class)
    public void refresh(String infoId, Collection<String> labels){
        Assert.hasText(infoId,"no infoId");

        deleteByFk(infoId);
        if(CollectionUtils.isNotEmpty(labels)){
            ArrayList<BmsTalentInfoLabelEntity> labelEntities = Lists.newArrayList();
            for (String label:labels) {
                BmsTalentInfoLabelEntity labelEntity= new BmsTalentInfoLabelEntity();
                labelEntity.setInfoId(infoId);
                labelEntity.setLabel(label);
                labelEntities.add(labelEntity);
            }
            insertBatch(labelEntities);
        }
    }

}
