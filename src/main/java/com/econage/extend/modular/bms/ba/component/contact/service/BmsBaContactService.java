package com.econage.extend.modular.bms.ba.component.contact.service;

import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.ba.component.contact.entity.BmsBaContactEntity;
import com.econage.extend.modular.bms.ba.component.contact.mapper.BmsBaContactMapper;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class BmsBaContactService extends ServiceImpl<BmsBaContactMapper, BmsBaContactEntity> {
    private UserUnionQuery userUnionQuery;
    @Autowired
    protected void setService(UserUnionQuery userUnionQuery) {
        this.userUnionQuery = userUnionQuery;
    }
    private final static List<String> validUpdateCols = ImmutableList.of("valid","modDate","modUser");
    @Transactional(rollbackFor = Throwable.class)
    public boolean changeBaContactValid(String contactId , boolean status){
        if(StringUtils.isEmpty(contactId)){
            return false;
        }
        BmsBaContactEntity bmsBaContactEntity = new BmsBaContactEntity();
        bmsBaContactEntity.setId(contactId);
        bmsBaContactEntity.setValid(status);
        return updatePartialColumnById(bmsBaContactEntity,validUpdateCols);
    }
    @Override
    protected void doRefreshSingleEntityBeforeInsert(BmsBaContactEntity entity){
        entity.setValid(true);
        if(StringUtils.isEmpty(entity.getId())||"0".equals(entity.getId())){
            entity.setId(IdWorker.getIdStr());
        }
        if(entity.getOrderSeq() == null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
    @Override
    protected void doFillFkRelationship(Collection<BmsBaContactEntity> entities){
        for(BmsBaContactEntity entity : entities){
            entity.setCreateUserName(userUnionQuery.selectUserMi(entity.getCreateUser()));
            if(StringUtils.isEmpty(entity.getCreateUserName())) entity.setCreateUserName(entity.getCreateUser());
        }
    }
}
