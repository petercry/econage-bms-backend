package com.econage.extend.modular.bms.ba.component.auth.service;

import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.ba.component.auth.entity.BaAuthEntity;
import com.econage.extend.modular.bms.ba.component.auth.mapper.BaAuthMapper;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class BaAuthService extends ServiceImpl<BaAuthMapper, BaAuthEntity> {
    private UserUnionQuery userUnionQuery;
    @Autowired
    protected void setUserServiceUnionQuery(UserUnionQuery userUnionQuery) {
        this.userUnionQuery = userUnionQuery;
    }
    private final static List<String> validUpdateCols = ImmutableList.of("valid","modDate","modUser");
    @Transactional(rollbackFor = Throwable.class)
    public boolean changeBaTeamMemberValid(String memberId , boolean status){
        if(StringUtils.isEmpty(memberId)){
            return false;
        }
        BaAuthEntity baAuthEntity = new BaAuthEntity();
        baAuthEntity.setId(memberId);
        baAuthEntity.setValid(status);
        return updatePartialColumnById(baAuthEntity,validUpdateCols);
    }
    @Override
    protected void doRefreshSingleEntityBeforeInsert(BaAuthEntity entity){
        entity.setValid(true);
        if(entity.getOrderSeq() == null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
    @Override
    protected void doFillFkRelationship(Collection<BaAuthEntity> entities){
        for(BaAuthEntity entity : entities){
            if(entity.getType() == 1){
                entity.setMemberName(userUnionQuery.selectUserMi(entity.getLinkId()));
            }else{
                entity.setMemberName(entity.getLinkId());
            }
            entity.setCreateUserName(userUnionQuery.selectUserMi(entity.getCreateUser()));
            if(StringUtils.isEmpty(entity.getCreateUserName())) entity.setCreateUserName(entity.getCreateUser());
        }
    }
    public Boolean isAuthExist(String baId , String roleKey , String roleUser){
        int count_re = getMapper().countExistAuth(baId , roleKey , roleUser);
        return count_re != 0;
    }
}
