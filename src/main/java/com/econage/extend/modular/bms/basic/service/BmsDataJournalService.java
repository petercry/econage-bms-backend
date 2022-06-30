package com.econage.extend.modular.bms.basic.service;

import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.ba.entity.BmsBaEntity;
import com.econage.extend.modular.bms.ba.service.BmsBaService;
import com.econage.extend.modular.bms.basic.entity.BmsDataJournalEntity;
import com.econage.extend.modular.bms.basic.mapper.BmsDataJournalMapper;
import com.econage.extend.modular.bms.basic.trivial.wherelogic.BmsDataJournalWhereLogic;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class BmsDataJournalService extends ServiceImpl<BmsDataJournalMapper, BmsDataJournalEntity> {
    private UserUnionQuery userService;
    private BmsBaService bmsBaService;
    @Autowired
    protected void setService(UserUnionQuery userService , BmsBaService bmsBaService) {
        this.userService = userService;
        this.bmsBaService = bmsBaService;
    }

    private final static List<String> updateExtContentCols = ImmutableList.of("extContent","modDate","modUser");

    @Transactional(rollbackFor = Throwable.class)
    public boolean changeExtContent(String id , String extContent){
        if(StringUtils.isEmpty(id)){
            return false;
        }
        BmsDataJournalEntity entity = new BmsDataJournalEntity();
        entity.setId(id);
        entity.setExtContent(extContent);
        return updatePartialColumnById(entity,updateExtContentCols);
    }

    @Override
    protected void doRefreshSingleEntityBeforeInsert(BmsDataJournalEntity entity){
        entity.setValid(true);
        if(entity.getOrderSeq() == null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
    public Double sumWithDataJournalByFinalRelatedProjectId(String final_related_project_id , String func_flag_str){
        return getMapper().sumWithDataJournalByFinalRelatedProjectId(final_related_project_id , func_flag_str);
    }
    public Double sumWithDataJournalByNoFinalRelatedProjectIdJustBa(String ba_id , String func_flag_str){
        return getMapper().sumWithDataJournalByNoFinalRelatedProjectIdJustBa(ba_id , func_flag_str);
    }
    @Override
    protected void doFillFkRelationship(Collection<BmsDataJournalEntity> entities) {
        for (BmsDataJournalEntity entity : entities) {
            if(!StringUtils.isEmpty(entity.getCreateUser()) && (entity.getCreateUser()).matches("[0-9]+") ){
                entity.setCreateUserDesc(userService.selectUserMi(entity.getCreateUser()));
            }else {
                entity.setCreateUserDesc(entity.getCreateUser());
            }
            if(!StringUtils.isEmpty(entity.getModular()) && entity.getModular().equals("ba")){
                BmsBaEntity bbe = bmsBaService.selectById(entity.getModularInnerId());
                entity.setRelatedBaName(bbe.getBaName());
            }
        }
    }
    //获取某个项目在某个日期前的费用支出列表
    public List<BmsDataJournalEntity> getProjectExpenseListByDate(String projectId , String searchToDateStr){
        BmsDataJournalWhereLogic journalWhereLogic = new BmsDataJournalWhereLogic();
        journalWhereLogic.setStatusTarget(true);
        journalWhereLogic.setModular("project");
        journalWhereLogic.setModular_inner_id(projectId);
        journalWhereLogic.setAboutFin(true);
        journalWhereLogic.setSearchCreateDate_to(searchToDateStr);
        return selectListByWhereLogic(journalWhereLogic);
    }
}

