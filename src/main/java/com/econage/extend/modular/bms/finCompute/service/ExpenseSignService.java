package com.econage.extend.modular.bms.finCompute.service;

import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.basic.entity.BmsDataJournalEntity;
import com.econage.extend.modular.bms.basic.service.BmsDataJournalService;
import com.econage.extend.modular.bms.finCompute.entity.ExpenseSignEntity;
import com.econage.extend.modular.bms.finCompute.mapper.ExpenseSignMapper;
import com.econage.extend.modular.bms.finCompute.trivial.wherelogic.ExpenseSignWherelogic;
import com.econage.extend.modular.bms.project.component.event.entity.BmsProjectEventEntity;
import com.econage.extend.modular.bms.project.component.event.service.BmsProjectEventService;
import com.econage.extend.modular.bms.util.BmsConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseSignService extends ServiceImpl<ExpenseSignMapper, ExpenseSignEntity> {
    private FinSettleSummaryService finSettleSummaryService;
    private BmsDataJournalService dataJournalService;
    private BmsProjectEventService projectEventService;
    @Autowired
    protected void setService(FinSettleSummaryService finSettleSummaryService ,BmsDataJournalService dataJournalService , BmsProjectEventService projectEventService) {
        this.finSettleSummaryService = finSettleSummaryService;
        this.dataJournalService = dataJournalService;
        this.projectEventService = projectEventService;
    }
    @Override
    protected void doRefreshSingleEntityBeforeInsert(ExpenseSignEntity entity){
        if(entity.getOrderSeq() == null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
    public boolean checkExistExpenseSettled(int modular , String modularInnerId , int settleLevel){
        ExpenseSignWherelogic expenseSignWherelogic = new ExpenseSignWherelogic();
        expenseSignWherelogic.setModular(modular);
        expenseSignWherelogic.setModularInnerId(modularInnerId);
        expenseSignWherelogic.setSettleLevel(settleLevel);
        int existNum = selectCountByWhereLogic(expenseSignWherelogic);
        return existNum != 0;
    }
    public Double getExpenseSignSumIncludeTaxInSingleSettle(String finSettleId){
        Double expenseSum = 0d;
        ExpenseSignWherelogic expenseSignWherelogic = new ExpenseSignWherelogic();
        expenseSignWherelogic.setFinSettleId(finSettleId);
        List<ExpenseSignEntity> expenseSignEntities = selectListByWhereLogic(expenseSignWherelogic);
        for(ExpenseSignEntity expenseSignEntity : expenseSignEntities){
            if(expenseSignEntity.getModular() == BmsConst.BMS_EXPENSE_MODULAR_COST){
                BmsDataJournalEntity journalEntity = dataJournalService.selectById(expenseSignEntity.getModularInnerId());
                expenseSum += journalEntity.getDataNumber();
            }else if(expenseSignEntity.getModular() == BmsConst.BMS_EXPENSE_MODULAR_TAX){
                BmsProjectEventEntity paymentEntity = projectEventService.selectById(expenseSignEntity.getModularInnerId());
                expenseSum += paymentEntity.getValueAddedTaxAmt()==null?0:paymentEntity.getValueAddedTaxAmt();
                expenseSum += paymentEntity.getSuperTaxAmt()==null?0:paymentEntity.getSuperTaxAmt();
                expenseSum += paymentEntity.getStampTaxAmt()==null?0:paymentEntity.getStampTaxAmt();
            }
        }
        return expenseSum;
    }
    public List<ExpenseSignEntity> getExpenseListInSingleSettleByModular(String finSettleId , Integer modular){
        ExpenseSignWherelogic expenseSignWherelogic = new ExpenseSignWherelogic();
        expenseSignWherelogic.setFinSettleId(finSettleId);
        expenseSignWherelogic.setModular(BmsConst.BMS_EXPENSE_MODULAR_COST);
        return selectListByWhereLogic(expenseSignWherelogic);
    }
//    public Double getExpenseSignSumIncludeTaxByProject(String projectId){
//        Double expenseSum = 0d;
//        FinSettleSummaryWherelogic settleSummaryWherelogic = new FinSettleSummaryWherelogic();
//        settleSummaryWherelogic.setProjectId(projectId);
//        List<FinSettleSummaryEntity> settledSummarys = finSettleSummaryService.selectListByWhereLogic(settleSummaryWherelogic);
//        for(FinSettleSummaryEntity settledSummarayNode : settledSummarys){
//            ExpenseSignWherelogic expenseSignWherelogic = new ExpenseSignWherelogic();
//            expenseSignWherelogic.setFinSettleId(settledSummarayNode.getId());
//            List<ExpenseSignEntity> expenseSignEntities = selectListByWhereLogic(expenseSignWherelogic);
//            for(ExpenseSignEntity expenseSignEntity : expenseSignEntities){
//                if(expenseSignEntity.getModular() == BmsConst.BMS_EXPENSE_MODULAR_COST){
//                    BmsDataJournalEntity journalEntity = dataJournalService.selectById(expenseSignEntity.getModularInnerId());
//                    expenseSum += journalEntity.getDataNumber();
//                }else if(expenseSignEntity.getModular() == BmsConst.BMS_EXPENSE_MODULAR_TAX){
//                    BmsProjectEventEntity paymentEntity = projectEventService.selectById(expenseSignEntity.getModularInnerId());
//                    expenseSum += paymentEntity.getValueAddedTaxAmt()==null?0:paymentEntity.getValueAddedTaxAmt();
//                    expenseSum += paymentEntity.getSuperTaxAmt()==null?0:paymentEntity.getSuperTaxAmt();
//                    expenseSum += paymentEntity.getStampTaxAmt()==null?0:paymentEntity.getStampTaxAmt();
//                }
//            }
//        }
//        return expenseSum;
//    }
}
