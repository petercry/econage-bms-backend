package com.econage.extend.modular.bms.project.component.event.service;

import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.project.component.event.entity.BmsProjectEventEntity;
import com.econage.extend.modular.bms.project.component.event.mapper.BmsProjectEventMapper;
import com.econage.extend.modular.bms.project.component.event.trivial.wherelogic.ProjectEventWhereLogic;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class BmsProjectEventService extends ServiceImpl<BmsProjectEventMapper, BmsProjectEventEntity> {
    private UserUnionQuery userUnionQuery;
    @Autowired
    protected void setUserServiceUnionQuery(UserUnionQuery userUnionQuery) {
        this.userUnionQuery = userUnionQuery;
    }
    private final static List<String> validUpdateCols = ImmutableList.of("valid","modDate","modUser");

    @Transactional(rollbackFor = Throwable.class)
    public boolean changeProjectEventStatus(String eventId , boolean status){
        if(StringUtils.isEmpty(eventId)){
            return false;
        }
        BmsProjectEventEntity bmsProjectEventEntity = new BmsProjectEventEntity();
        bmsProjectEventEntity.setId(eventId);
        bmsProjectEventEntity.setValid(status);
        return updatePartialColumnById(bmsProjectEventEntity,validUpdateCols);
    }
    @Override
    protected void doRefreshSingleEntityBeforeInsert(BmsProjectEventEntity entity){
        entity.setValid(true);
        if(entity.getOrderSeq() == null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
    @Override
    protected void doFillFkRelationship(Collection<BmsProjectEventEntity> entities) {
        for (BmsProjectEventEntity entity : entities) {
            if(!StringUtils.isEmpty(entity.getCreateUser())){
                String mi = userUnionQuery.selectUserMi(entity.getCreateUser());
                if(StringUtils.isEmpty(mi)){
                    mi = entity.getCreateUser();
                }
                entity.setCreateUserName(mi);
            }
        }
    }
    public Double getTaxSum( String projectId ){
        Double taxSum = 0d;
        ProjectEventWhereLogic projectEventWhereLogic = new ProjectEventWhereLogic();
        projectEventWhereLogic.setStatusTarget(true);
        projectEventWhereLogic.setProjectId(projectId);
        projectEventWhereLogic.setCategoryId(5);
        projectEventWhereLogic.setPaymtType(0);
        List<BmsProjectEventEntity> paymentList = selectListByWhereLogic(projectEventWhereLogic);
        for(BmsProjectEventEntity currPaymentNode : paymentList){
            taxSum += currPaymentNode.getValueAddedTaxAmt()==null?0:currPaymentNode.getValueAddedTaxAmt();
            taxSum += currPaymentNode.getSuperTaxAmt()==null?0:currPaymentNode.getSuperTaxAmt();
            taxSum += currPaymentNode.getStampTaxAmt()==null?0:currPaymentNode.getStampTaxAmt();
        }
        return taxSum;
    }

    public List<BmsProjectEventEntity> getPaymentListByDate(String projectId , String searchToDate){
        ProjectEventWhereLogic paymentWherelogic = new ProjectEventWhereLogic();
        paymentWherelogic.setStatusTarget(true);
        paymentWherelogic.setProjectId(projectId);
        paymentWherelogic.setCategoryId(5);
        paymentWherelogic.setPaymtType(0);
        paymentWherelogic.setSearchPaymentDateToStr(searchToDate);
        return selectListByWhereLogic(paymentWherelogic);
    }
}
