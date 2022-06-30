package com.econage.extend.modular.bms.finCompute.service;

import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.finCompute.entity.FinSettleDetailEntity;
import com.econage.extend.modular.bms.finCompute.entity.FinSettleSummaryEntity;
import com.econage.extend.modular.bms.finCompute.mapper.FinSettleDetailMapper;
import com.econage.extend.modular.bms.finCompute.trivial.wherelogic.FinSettleDetailWherelogic;
import com.econage.extend.modular.bms.finCompute.trivial.wherelogic.FinSettleSummaryWherelogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinSettleDetailService  extends ServiceImpl<FinSettleDetailMapper, FinSettleDetailEntity> {
    private FinSettleSummaryService finSettleSummaryService;
    @Autowired
    protected void setService(FinSettleSummaryService finSettleSummaryService ) {
        this.finSettleSummaryService = finSettleSummaryService;
    }
    @Override
    protected void doRefreshSingleEntityBeforeInsert(FinSettleDetailEntity entity){
        if(entity.getOrderSeq() == null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
    public Double getSettledDetailSumByProject(String projectId){
        Double settledDetailSum = 0d;
        FinSettleSummaryWherelogic settleSummaryWherelogic = new FinSettleSummaryWherelogic();
        settleSummaryWherelogic.setProjectId(projectId);
        List<FinSettleSummaryEntity> settledSummarys = finSettleSummaryService.selectListByWhereLogic(settleSummaryWherelogic);
        for(FinSettleSummaryEntity settledSummarayNode : settledSummarys){
            FinSettleDetailWherelogic settleDetailWherelogic = new FinSettleDetailWherelogic();
            settleDetailWherelogic.setFinSettleId(settledSummarayNode.getId());
            List<FinSettleDetailEntity> settleDetailEntities = selectListByWhereLogic(settleDetailWherelogic);
            for(FinSettleDetailEntity settleDetailEntity : settleDetailEntities){
                settledDetailSum += settleDetailEntity.getDistributeAmt();
            }
        }
        return settledDetailSum;
    }
}
