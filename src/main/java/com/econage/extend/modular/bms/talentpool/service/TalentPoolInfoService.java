package com.econage.extend.modular.bms.talentpool.service;

import com.econage.base.organization.org.entity.UserEntity;
import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.base.support.kv.manage.TreeKvUnionQuery;
import com.econage.base.support.kv.service.TreeKvService;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.talentpool.entity.BmsTalentFollowEntity;
import com.econage.extend.modular.bms.talentpool.entity.BmsTalentInfoEntity;
import com.econage.extend.modular.bms.talentpool.entity.BmsTalentInfoLabelEntity;
import com.econage.extend.modular.bms.talentpool.entity.BmsTalentInfoRecycleEntity;
import com.econage.extend.modular.bms.talentpool.mapper.BmsTalentInfoMapper;
import com.econage.extend.modular.bms.talentpool.trival.meta.FollowTypeEnum;
import com.econage.extend.modular.bms.talentpool.trival.wherelogic.TalentPoolFollowWhereLogic;
import com.econage.extend.modular.bms.talentpool.trival.wherelogic.TalentPoolInfoWhereLogic;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.apache.commons.compress.utils.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author econage
 */
@Service
public class TalentPoolInfoService extends ServiceImpl<BmsTalentInfoMapper, BmsTalentInfoEntity> {

    private TalentPoolInfoRecycleService talentPoolInfoRecycleService;
    private UserUnionQuery userUnionQuery;
    private TalentPoolInfoLabelService talentPoolInfoLabelService;
    private TreeKvService treeKvService;
    private TreeKvUnionQuery treeKvUnionQuery;
    private TalentPoolFollowService talentPoolFollowService;

    @Autowired
    void setAutoWire(TalentPoolInfoRecycleService talentPoolInfoRecycleService,
                     TalentPoolInfoLabelService talentPoolInfoLabelService,
                     TreeKvService treeKvService,
                     UserUnionQuery userUnionQuery,
                     TreeKvUnionQuery treeKvUnionQuery,
                     TalentPoolFollowService talentPoolFollowService) {
        this.talentPoolInfoRecycleService = talentPoolInfoRecycleService;
        this.userUnionQuery = userUnionQuery;
        this.treeKvService = treeKvService;
        this.talentPoolInfoLabelService= talentPoolInfoLabelService;
        this.treeKvUnionQuery = treeKvUnionQuery;
        this.talentPoolFollowService = talentPoolFollowService;
    }

    private final List<String> updateFolderCol = Lists.newArrayList("folderId", "modDate", "modUser");
    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final String[] defaultSort = {"create_date_"};
    private final String[] defaultOrder = {"asc"};

    @Transactional(rollbackFor = Throwable.class)
    public boolean changeTaFolder(String taId , Integer folderId){
        if(StringUtils.isEmpty(taId)){
            return false;
        }
        BmsTalentInfoEntity btie = new BmsTalentInfoEntity();
        btie.setId(taId);
        btie.setFolderId(folderId);
        return updatePartialColumnById(btie,updateFolderCol);
    }

    private final List<String> updateFollowCols = Lists.newArrayList("hrStatus","bpStatus", "followNextDate", "modDate", "modUser");

    @Override
    protected void doFillFkRelationship(Collection<BmsTalentInfoEntity> entities) {

        Set<String> userIds = Sets.newHashSet();
        Set<String> ids=Sets.newHashSet();

        for (BmsTalentInfoEntity infoEntity : entities) {
            userIds.add(infoEntity.getRoundInterviewer());
            userIds.add(infoEntity.getModUser());
            ids.add(infoEntity.getId());
        }
        Map<String, String> userMap = userUnionQuery.selectList(userIds).stream()
                .collect(Collectors.toMap(UserEntity::getId, UserEntity::getMi));

        Map<? extends Serializable, List<BmsTalentInfoLabelEntity>> labelMap = talentPoolInfoLabelService.selectMapByFk(ids);
        for (BmsTalentInfoEntity infoEntity : entities) {
            infoEntity.setRoundInterviewerName(userMap.get(infoEntity.getRoundInterviewer()));
            infoEntity.setModUserName(userMap.get(infoEntity.getModUser()));
            infoEntity.setLabels(labelMap.get(infoEntity.getId()));
            if(!StringUtils.isEmpty(infoEntity.getHrStatus())){
                infoEntity.setHrStatusDesc(treeKvUnionQuery.getTreeFullPathText(infoEntity.getHrStatus() ,  "/" , 1));
            }
            if(StringUtils.isNotEmpty(infoEntity.getBpStatus())){
                infoEntity.setBpStatusDesc(treeKvUnionQuery.getTreeFullPathText(infoEntity.getBpStatus() ,  "/" , 1));
            }

            TalentPoolFollowWhereLogic talentPoolFollowWhereLogic = new TalentPoolFollowWhereLogic();
            talentPoolFollowWhereLogic.setInfoId(infoEntity.getId());
            talentPoolFollowWhereLogic.setType(2);
            talentPoolFollowWhereLogic.setIsSearchLaterThanNow(true);
            talentPoolFollowWhereLogic.setPage(1);
            talentPoolFollowWhereLogic.setRows(1000);
            talentPoolFollowWhereLogic.setOrder(defaultOrder);
            talentPoolFollowWhereLogic.setSort(defaultSort);
            List<BmsTalentFollowEntity> bmsTalentFollowEntities = talentPoolFollowService.selectListByWhereLogic(talentPoolFollowWhereLogic);
            String interviewDescStr = "";
            for(BmsTalentFollowEntity btfe : bmsTalentFollowEntities){
                String roundDateStr = df.format(btfe.getRoundDate());
                if(interviewDescStr.equals("")){
                    interviewDescStr = roundDateStr;
                }else{
                    interviewDescStr += "\r\n" + roundDateStr;
                }
            }
            infoEntity.setInterviewArrangeDesc(interviewDescStr);
        }
    }

    @Override
    protected void doRefreshSingleEntityAfterInsert(BmsTalentInfoEntity entity) {
        refreshLabels(entity);
    }


    @Override
    protected void doRefreshSingleEntityAfterUpdate(BmsTalentInfoEntity entity) {
        refreshLabels(entity);
    }

    @Override
    protected void doRefreshSingleEntityBeforeDelete(Serializable entityId) {
        BmsTalentInfoEntity bmsTalentInfoEntity = selectById(entityId);
        if (bmsTalentInfoEntity == null) {
            return;
        }
        BmsTalentInfoRecycleEntity recycleEntity = new BmsTalentInfoRecycleEntity();
        BeanUtils.copyProperties(bmsTalentInfoEntity, recycleEntity);
        talentPoolInfoRecycleService.insert(recycleEntity);
    }

    private void refreshLabels(BmsTalentInfoEntity entity){
        if(entity==null){
            return;
        }
        if(entity.getLabels()!=null){
            Set<String> labels = entity.getLabels().stream()
                    .map(BmsTalentInfoLabelEntity::getLabel).collect(Collectors.toSet());
            talentPoolInfoLabelService.refresh(entity.getId(),labels);
        }
    }

    public void updateRedundancyInfo(String infoId, @NonNull BmsTalentFollowEntity entity) {

        Assert.hasText(infoId, "no infoId");

        BmsTalentInfoEntity infoEntity = selectById(infoId);
        if (infoEntity == null) {
            return;
        }

        if (FollowTypeEnum.INTERVIEW.equals(entity.getType())) {
            updateFollowCols.addAll(Arrays.asList("roundDate", "roundInterviewer", "roundMethod"));
        } else if (FollowTypeEnum.RESULT.equals(entity.getType())) {
            updateFollowCols.addAll(Arrays.asList("roundDate", "roundInterviewer", "roundMethod", "join", "joinDate"));
        }
        infoEntity.setHrStatus(entity.getHrStatus());
        infoEntity.setBpStatus(entity.getBpStatus());
        infoEntity.setFollowNextDate(entity.getFollowNextDate());
        infoEntity.setJoin(entity.getJoin());
        infoEntity.setJoinDate(entity.getJoinDate());
        infoEntity.setRoundDate(entity.getRoundDate());
        infoEntity.setRoundInterviewer(entity.getRoundInterviewer());
        infoEntity.setRoundMethod(entity.getRoundMethod());
        updatePartialColumnById(infoEntity, updateFollowCols);
    }

    /*public void fillFollowResultList(BmsTalentInfoEntity entity) {
        if (entity == null) {
            return;
        }
        entity.setFollowResultList(getParentFollowResult(entity.getFollowResult()));
    }*/


/*    *//**
     * @param
     * @return 查询该跟进状态的父节点，包含自身
     *//*
    public List<String> getParentFollowResult(String followResult) {
        if (StringUtils.isEmpty(followResult)) {
            return Collections.emptyList();
        }
        List<String> arrayList = org.apache.commons.compress.utils.Lists.newArrayList();
        arrayList.add(followResult);

        Set<String> ids = (Set<String>) treeKvService.selectParentIds(followResult);
        ids.add(followResult);
        ids.remove(BmsConst.BMS_TALENT_POOL_FOLLOW_RESULT_ROOT_ID);
        List<TreeKvEntity> treeKvEntities = treeKvService.selectListByIds(ids);
        if (CollectionUtils.isNotEmpty(treeKvEntities)) {
            Map<String, String> idParentIdMap = treeKvEntities.stream()
                    .collect(Collectors.toMap(TreeKvEntity::getId, TreeKvEntity::getParentId));

            String parentId = idParentIdMap.get(followResult);
            while (idParentIdMap.containsKey(parentId)) {
                arrayList.add(parentId);
                parentId = idParentIdMap.get(parentId);
            }
        }
        arrayList.remove(BmsConst.BMS_TALENT_POOL_FOLLOW_RESULT_ROOT_ID);
        return arrayList;
    }*/

    public List<BmsTalentInfoEntity> queryByNameOrPhone(String phone, String name) {
        if (StringUtils.isEmpty(phone) && StringUtils.isEmpty(name)) {
            return Collections.emptyList();
        }
        List<BmsTalentInfoEntity> list = Lists.newArrayList();
        if (StringUtils.isNotEmpty(phone)) {
            TalentPoolInfoWhereLogic whereLogic = new TalentPoolInfoWhereLogic();
            whereLogic.setAccuratePhone(phone);
            list.addAll(selectListByWhereLogic(whereLogic));
        }

        if (StringUtils.isNotEmpty(name)) {
            TalentPoolInfoWhereLogic whereLogic = new TalentPoolInfoWhereLogic();
            whereLogic.setAccurateName(name);
            list.addAll(selectListByWhereLogic(whereLogic));
        }
        List<BmsTalentInfoEntity> collect = list.stream()
                .collect(Collectors.collectingAndThen(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(BmsTalentInfoEntity::getId))), ArrayList::new));

        collect.sort(Comparator.comparing(BmsTalentInfoEntity::getModDate).reversed());
        return collect;

    }


}
