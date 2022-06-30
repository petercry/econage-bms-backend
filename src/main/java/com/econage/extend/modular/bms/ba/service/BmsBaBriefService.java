package com.econage.extend.modular.bms.ba.service;

import com.econage.base.support.kv.manage.BasicKvUnionQuery;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.ba.entity.BmsBaBriefEntity;
import com.econage.extend.modular.bms.ba.mapper.BmsBaBriefMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BmsBaBriefService extends ServiceImpl<BmsBaBriefMapper, BmsBaBriefEntity> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BmsBaBriefService.class);
    private BasicKvUnionQuery kvService;
    @Autowired
    protected void setKvService(BasicKvUnionQuery kvService) {
        this.kvService = kvService;
    }
    @Override
    protected void doFillFkRelationship(Collection<BmsBaBriefEntity> entities){
        for(BmsBaBriefEntity entity : entities){
            if(StringUtils.isEmpty(entity.getStateAreaDesc())){
                entity.setStateAreaDesc( StringUtils.defaultString(entity.getState() , "")  + StringUtils.defaultString(entity.getCity() , ""));
            }
            if(!StringUtils.isEmpty(entity.getFirstStatus())){
                entity.setFirstStatusDesc(kvService.getKvTextById(entity.getFirstStatus()));
            }
            if(!StringUtils.isEmpty(entity.getValueCode())){
                entity.setValueCodeDesc(kvService.getKvTextById(entity.getValueCode()));
            }
        }
    }
}
