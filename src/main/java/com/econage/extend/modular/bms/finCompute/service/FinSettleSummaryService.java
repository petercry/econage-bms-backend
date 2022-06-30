package com.econage.extend.modular.bms.finCompute.service;

import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.finCompute.entity.FinSettleSummaryEntity;
import com.econage.extend.modular.bms.finCompute.mapper.FinSettleSummaryMapper;
import com.econage.extend.modular.bms.finCompute.trivial.wherelogic.FinSettleSummaryWherelogic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinSettleSummaryService  extends ServiceImpl<FinSettleSummaryMapper, FinSettleSummaryEntity> {
    @Override
    protected void doRefreshSingleEntityBeforeInsert(FinSettleSummaryEntity entity){
        if(entity.getOrderSeq() == null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
    public Double getSettledSumByProject(String projectId){
        FinSettleSummaryWherelogic settleSummaryWherelogic = new FinSettleSummaryWherelogic();
        settleSummaryWherelogic.setProjectId(projectId);
        List<FinSettleSummaryEntity> settledSummarys = selectListByWhereLogic(settleSummaryWherelogic);
        Double settledSum = 0d;
        for(FinSettleSummaryEntity settledSummarayNode : settledSummarys){
            settledSum += settledSummarayNode.getSettleAmt();
        }
        return settledSum;
    }
}
