package com.econage.extend.modular.bms.finCompute.service;

import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.finCompute.entity.FinSettlePaymentDetailEntity;
import com.econage.extend.modular.bms.finCompute.mapper.FinSettlePaymentDetailMapper;
import com.econage.extend.modular.bms.finCompute.trivial.wherelogic.FinSettlePaymentDetailWherelogic;
import com.econage.extend.modular.bms.project.component.event.entity.BmsProjectEventEntity;
import com.econage.extend.modular.bms.project.component.event.service.BmsProjectEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinSettlePaymentDetailService  extends ServiceImpl<FinSettlePaymentDetailMapper, FinSettlePaymentDetailEntity> {
    private FinSettleSummaryService finSettleSummaryService;
    private BmsProjectEventService projectEventService;
    @Autowired
    protected void setService(FinSettleSummaryService finSettleSummaryService , BmsProjectEventService projectEventService ) {
        this.finSettleSummaryService = finSettleSummaryService;
        this.projectEventService = projectEventService;
    }
    @Override
    protected void doRefreshSingleEntityBeforeInsert(FinSettlePaymentDetailEntity entity){
        if(entity.getOrderSeq() == null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
    public Double getPaymentSumBySingleFinSettle(String finSettleId){
        Double paymentSum = 0d;
        FinSettlePaymentDetailWherelogic settlePaymentDetailWherelogic = new FinSettlePaymentDetailWherelogic();
        settlePaymentDetailWherelogic.setFinSettleId(finSettleId);
        List<FinSettlePaymentDetailEntity> paymentList = selectListByWhereLogic(settlePaymentDetailWherelogic);
        for (FinSettlePaymentDetailEntity paymentNode : paymentList){
            String paymentId = paymentNode.getPaymentEventId();
            BmsProjectEventEntity paymentEventEntity = projectEventService.selectById(paymentId);
            paymentSum += paymentEventEntity.getPaymtAmt();
        }
        return paymentSum;
    }
//    public Double getPaymentSumByProject(String projectId){
//        Double paymentSum = 0d;
//        FinSettleSummaryWherelogic settleSummaryWherelogic = new FinSettleSummaryWherelogic();
//        settleSummaryWherelogic.setProjectId(projectId);
//        List<FinSettleSummaryEntity> settledSummarys = finSettleSummaryService.selectListByWhereLogic(settleSummaryWherelogic);
//        for(FinSettleSummaryEntity settledSummarayNode : settledSummarys){
//            FinSettlePaymentDetailWherelogic settlePaymentDetailWherelogic = new FinSettlePaymentDetailWherelogic();
//            settlePaymentDetailWherelogic.setFinSettleId(settledSummarayNode.getId());
//            List<FinSettlePaymentDetailEntity> paymentList = selectListByWhereLogic(settlePaymentDetailWherelogic);
//            for (FinSettlePaymentDetailEntity paymentNode : paymentList){
//                String paymentId = paymentNode.getPaymentEventId();
//                BmsProjectEventEntity paymentEventEntity = projectEventService.selectById(paymentId);
//                paymentSum += paymentEventEntity.getPaymtAmt();
//            }
//        }
//        return paymentSum;
//    }
}
