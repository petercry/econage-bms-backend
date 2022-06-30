package com.econage.extend.modular.bms.talentpool.service;

import com.econage.base.organization.org.entity.UserEntity;
import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.core.basic.exception.EconageBaseException;
import com.econage.core.basic.exception.EconageBusinessException;
import com.econage.core.basic.util.SystemClock;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.talentpool.entity.BmsTalentFollowEntity;
import com.econage.extend.modular.bms.talentpool.entity.BmsTalentInfoEntity;
import com.econage.extend.modular.bms.talentpool.mapper.BmsTalentFollowMapper;
import lombok.NonNull;
import org.apache.commons.compress.utils.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author econage
 */
@Service
public class TalentPoolFollowService extends ServiceImpl<BmsTalentFollowMapper, BmsTalentFollowEntity> {

    private UserUnionQuery userUnionQuery;
    private TalentPoolInfoService talentPoolInfoService;


    @Autowired
    void setAutoWire(UserUnionQuery userUnionQuery, TalentPoolInfoService talentPoolInfoService) {
        this.userUnionQuery = userUnionQuery;
        this.talentPoolInfoService = talentPoolInfoService;
    }


    @Override
    protected void doFillFkRelationship(Collection<BmsTalentFollowEntity> entities) {

        Set<String> userIds = Sets.newHashSet();

        for (BmsTalentFollowEntity followEntity : entities) {
            userIds.add(followEntity.getRoundInterviewer());
            userIds.add(followEntity.getFollowPrincipal());
        }
        Map<String, String> userMap = userUnionQuery.selectList(userIds).stream()
                .collect(Collectors.toMap(UserEntity::getId, UserEntity::getMi));

        for (BmsTalentFollowEntity followEntity : entities) {
            followEntity.setUpdateEnable(ifUpdate(followEntity));
            followEntity.setRoundInterviewerName(userMap.get(followEntity.getRoundInterviewer()));
            followEntity.setFollowPrincipalName(userMap.get(followEntity.getFollowPrincipal()));
        }

    }

    /**
     * @param followEntity
     * @return 允许24小时内编辑删除
     */
    public boolean ifUpdate(@NonNull BmsTalentFollowEntity followEntity) {

        BmsTalentFollowEntity dbEntity = getMapper().selectById(followEntity.getId());
        if (dbEntity == null) {
            throw new EconageBusinessException(" cannot find,id: " + followEntity.getId());
        }

        return dbEntity.getCreateDate().plusHours(24).isAfter(SystemClock.nowDateTime());
    }


    @Override
    protected void doRefreshSingleEntityBeforeDelete(Serializable entityId) {
        BmsTalentFollowEntity bmsTalentFollowEntity = selectById(entityId);
        if (bmsTalentFollowEntity != null && !ifUpdate(bmsTalentFollowEntity)) {
            throw new EconageBaseException(" cannot update or delete ");
        }

    }

    @Override
    protected void doRefreshSingleEntityBeforeUpdate(BmsTalentFollowEntity entity) {
        if (entity != null && !ifUpdate(entity)) {
            throw new EconageBaseException(" cannot update or delete ");
        }
    }

    @Override
    protected void doRefreshSingleEntityAfterInsert(BmsTalentFollowEntity entity) {
        talentPoolInfoService.updateRedundancyInfo(entity.getInfoId(), entity);
    }

    @Override
    protected void doRefreshSingleEntityAfterUpdate(BmsTalentFollowEntity entity) {
        BmsTalentFollowEntity bmsTalentFollowEntity = selectById(entity.getId());
        if (bmsTalentFollowEntity != null) {
            //更新的时候可能没传infoId，获取
            talentPoolInfoService.updateRedundancyInfo(bmsTalentFollowEntity.getInfoId(), entity);
        }
    }

    public BmsTalentFollowEntity querySingle(String id) {
        BmsTalentFollowEntity bmsTalentFollowEntity = selectById(id);
        if (bmsTalentFollowEntity == null) {
            return null;
        }
        BmsTalentInfoEntity infoEntity = talentPoolInfoService.selectById(bmsTalentFollowEntity.getInfoId());
        if (infoEntity != null) {
            bmsTalentFollowEntity.setHrStatus(infoEntity.getHrStatus());
            bmsTalentFollowEntity.setBpStatus(infoEntity.getBpStatus());
            bmsTalentFollowEntity.setFollowNextDate(infoEntity.getFollowNextDate());
          //  bmsTalentFollowEntity.setFollowResultList(talentPoolInfoService.getParentFollowResult(bmsTalentFollowEntity.getFollowResult()));
        }
        return bmsTalentFollowEntity;
    }

}
