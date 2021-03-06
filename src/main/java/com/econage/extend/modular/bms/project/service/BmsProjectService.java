package com.econage.extend.modular.bms.project.service;

import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.base.plat.tokenstore.BasicTokenStoreEntity;
import com.econage.base.support.filemanager.manage.FileManager;
import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.ba.entity.BmsBaEntity;
import com.econage.extend.modular.bms.ba.service.BmsBaService;
import com.econage.extend.modular.bms.basic.entity.BmsDataJournalEntity;
import com.econage.extend.modular.bms.basic.service.BmsDataJournalService;
import com.econage.extend.modular.bms.finCompute.entity.ExpenseSignEntity;
import com.econage.extend.modular.bms.finCompute.entity.FinSettleDetailEntity;
import com.econage.extend.modular.bms.finCompute.entity.FinSettlePaymentDetailEntity;
import com.econage.extend.modular.bms.finCompute.entity.FinSettleSummaryEntity;
import com.econage.extend.modular.bms.finCompute.service.*;
import com.econage.extend.modular.bms.finCompute.trivial.wherelogic.FinSettleDetailWherelogic;
import com.econage.extend.modular.bms.finCompute.trivial.wherelogic.FinSettlePaymentDetailWherelogic;
import com.econage.extend.modular.bms.finCompute.trivial.wherelogic.FinSettleSummaryWherelogic;
import com.econage.extend.modular.bms.project.component.event.entity.BmsProjectEventEntity;
import com.econage.extend.modular.bms.project.component.event.service.BmsProjectEventService;
import com.econage.extend.modular.bms.project.component.event.trivial.wherelogic.ProjectEventWhereLogic;
import com.econage.extend.modular.bms.project.entity.BmsProjectAssociationEntity;
import com.econage.extend.modular.bms.project.entity.BmsProjectEntity;
import com.econage.extend.modular.bms.project.mapper.BmsProjectMapper;
import com.econage.extend.modular.bms.project.trivial.wherelogic.ProjectAssociationWhereLogic;
import com.econage.extend.modular.bms.util.BmsConst;
import com.econage.extend.modular.bms.util.BmsHelper;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BmsProjectService extends ServiceImpl<BmsProjectMapper, BmsProjectEntity> {
    private UserUnionQuery userUnionQuery;
    private BmsProjectAssociationService bmsProjectAssociationService;
    private BmsBaService bmsBaService;
    private FinSettleSummaryService finSettleSummaryService;
    private FinSettleDetailService finSettleDetailService;
    private FinSettlePaymentDetailService finSettlePaymentDetailService;
    private ExpenseSignService expenseSignService;
    private BmsDataJournalService dataJournalService;
    private BmsProjectEventService projectEventService;
    private RewardPayoffHisService rewardPayoffHisService;

    private final DateTimeFormatter datef = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Autowired
    protected void setService(UserUnionQuery userUnionQuery , BmsProjectAssociationService bmsProjectAssociationService , BmsBaService bmsBaService ,
                              FinSettleSummaryService finSettleSummaryService , FinSettleDetailService finSettleDetailService , FinSettlePaymentDetailService finSettlePaymentDetailService ,
                              ExpenseSignService expenseSignService , BmsDataJournalService dataJournalService , BmsProjectEventService projectEventService , RewardPayoffHisService rewardPayoffHisService
                              ) {
        this.userUnionQuery = userUnionQuery;
        this.bmsProjectAssociationService = bmsProjectAssociationService;
        this.bmsBaService = bmsBaService;
        this.finSettleSummaryService = finSettleSummaryService;
        this.finSettleDetailService = finSettleDetailService;
        this.finSettlePaymentDetailService = finSettlePaymentDetailService;
        this.expenseSignService = expenseSignService;
        this.dataJournalService = dataJournalService;
        this.projectEventService = projectEventService;
        this.rewardPayoffHisService = rewardPayoffHisService;
    }
    private final static List<String> validUpdateCols = ImmutableList.of("valid","modDate","modUser");

    @Transactional(rollbackFor = Throwable.class)
    public boolean changeProjectStatus(String projectId , boolean status){
        if(StringUtils.isEmpty(projectId)){
            return false;
        }
        BmsProjectEntity bmsProjectEntity = new BmsProjectEntity();
        bmsProjectEntity.setId(projectId);
        bmsProjectEntity.setValid(status);
        return updatePartialColumnById(bmsProjectEntity,validUpdateCols);
    }
    @Override
    protected void doRefreshSingleEntityBeforeInsert(BmsProjectEntity entity){
        entity.setValid(true);
        if(entity.getOrderSeq() == null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
    @Override
    protected void doFillFkRelationship(Collection<BmsProjectEntity> entities) {
        for (BmsProjectEntity entity : entities) {
            if(!StringUtils.isEmpty(entity.getCreateUser())){
                String mi = userUnionQuery.selectUserMi(entity.getCreateUser());
                if(StringUtils.isEmpty(mi)){
                    mi = entity.getCreateUser();
                }
                entity.setCreateUserName(mi);
            }
            if(!StringUtils.isEmpty(entity.getSalesManager())){
                String mi = userUnionQuery.selectUserMi(entity.getSalesManager());
                if(StringUtils.isEmpty(mi)){
                    mi = entity.getSalesManager();
                }
                entity.setSalesManagerName(mi);
            }
            if(entity.getProductFocusFlag()){ //??????Alpha???????????????/???????????????????????????
                entity.setProductFocusIdForAf("6cb8790a");
            }else{
                entity.setProductFocusIdForAf("bdaa3acf");
            }
            if(!StringUtils.isEmpty(entity.getProjectPhase())&&StringUtils.isNumeric(entity.getProjectPhase())){
                entity.setProjectPhaseName(BmsHelper.getProjectPhaseName(Integer.parseInt(entity.getProjectPhase())));
            }
        }
    }
    @Override
    protected void doRefreshSingleEntityAfterInsert(BmsProjectEntity entity) {
        refreshBaRelated(entity);
    }
    @Override
    protected void doRefreshSingleEntityAfterUpdate(BmsProjectEntity entity) {
        refreshBaRelated(entity);
    }
    private void refreshBaRelated(BmsProjectEntity entity){
        if(entity==null){
            return;
        }
        Set<String> baIds = null;
        if(entity.getRelatedBaIds()!=null){
            baIds = entity.getRelatedBaIds().stream().collect(Collectors.toSet());

        }
        bmsProjectAssociationService.refresh("ba" , entity.getId(),baIds);
    }
    private final String[] defaultSort = {"order_seq_"};
    private final String[] defaultOrder = {"desc"};

    public List<BmsBaEntity> getRelatedBaList( String projectId){
        ProjectAssociationWhereLogic whereLogic = new ProjectAssociationWhereLogic();
        whereLogic.setModular("ba");
        whereLogic.setProjectId(projectId);
        whereLogic.setSort(defaultSort);
        whereLogic.setOrder(defaultOrder);
        List<BmsProjectAssociationEntity> al = bmsProjectAssociationService.selectListByWhereLogic(whereLogic);
        List<BmsBaEntity> relatedBaList = new ArrayList<>();
        for (BmsProjectAssociationEntity bpae:al){
            BmsBaEntity bbe = bmsBaService.selectById(bpae.getModularInnerId());
            relatedBaList.add(bbe);
        }
        return relatedBaList;
    }
    //???????????????????????????????????????????????????
    public BasicTokenStoreEntity verifyProjectFinSettle(String projectId){
        BasicTokenStoreEntity reEntity = new BasicTokenStoreEntity();
        //???????????????????????????????????? settledSummaryAmt
        Double settledSummaryAmt = finSettleSummaryService.getSettledSumByProject(projectId);
        //???????????????????????????????????????????????????????????? settledDetailAmt
        Double settledDetailAmt = finSettleDetailService.getSettledDetailSumByProject(projectId);
        if(Math.abs(settledSummaryAmt - settledDetailAmt)>1) {
            reEntity.setId("1");
            reEntity.setToken("??????????????????????????????????????????????????????");
            return reEntity;
        }
        FinSettleSummaryWherelogic settleSummaryWherelogic = new FinSettleSummaryWherelogic();
        settleSummaryWherelogic.setProjectId(projectId);
        List<FinSettleSummaryEntity> settledSummarys = finSettleSummaryService.selectListByWhereLogic(settleSummaryWherelogic);
        HashMap<Integer , LocalDate> settleLevelLatestSettleDateHash = new HashMap<>();//??????????????????????????????????????????
        List<ExpenseSignEntity> expenseSignedList = new ArrayList<>();
        for(FinSettleSummaryEntity settledSummarayNode : settledSummarys){
            //?????????????????????????????????
            Double paymentSumInSingleSettle = finSettlePaymentDetailService.getPaymentSumBySingleFinSettle(settledSummarayNode.getId());
            //?????????????????????????????????
            Double expenseSumInSingleSettle = expenseSignService.getExpenseSignSumIncludeTaxInSingleSettle(settledSummarayNode.getId());
            if(Math.abs((paymentSumInSingleSettle - expenseSumInSingleSettle )*(settledSummarayNode.getSettlePct()/100)-settledSummarayNode.getSettleAmt() )>1){
                reEntity.setId("2");
                reEntity.setToken("??????#"+settledSummarayNode.getId()+"#??????????????????????????????????????????????????????????????????");
                return reEntity;
            }
            if(!settleLevelLatestSettleDateHash.containsKey(settledSummarayNode.getSettleLevel())){
                settleLevelLatestSettleDateHash.put(settledSummarayNode.getSettleLevel() , settledSummarayNode.getSettleDate());
            }else{
                LocalDate existSettleDate = settleLevelLatestSettleDateHash.get(settledSummarayNode.getSettleLevel());
                if(existSettleDate.isBefore(settledSummarayNode.getSettleDate())){
                    settleLevelLatestSettleDateHash.remove(settledSummarayNode.getSettleLevel());
                    settleLevelLatestSettleDateHash.put(settledSummarayNode.getSettleLevel() , settledSummarayNode.getSettleDate());
                }
            }
            expenseSignedList.addAll(expenseSignService.getExpenseListInSingleSettleByModular(settledSummarayNode.getId() , BmsConst.BMS_EXPENSE_MODULAR_COST));
        }
        if(settleLevelLatestSettleDateHash.size() > 0){
            for(Map.Entry<Integer , LocalDate> settleLevelDateNode : settleLevelLatestSettleDateHash.entrySet()){
                int checkSettleLevel = settleLevelDateNode.getKey();
                LocalDate checkLatestSettleDate = settleLevelDateNode.getValue();
                List<BmsDataJournalEntity> expenseJournalList = dataJournalService.getProjectExpenseListByDate(projectId , datef.format(checkLatestSettleDate));
                int expenseSignedNumForSpecSettleLevel = 0;//??????????????????????????????????????????
                for (ExpenseSignEntity expenseSignEntity : expenseSignedList){
                    if(expenseSignEntity.getSettleLevel() == checkSettleLevel){
                        expenseSignedNumForSpecSettleLevel ++;
                    }
                }
                if(expenseJournalList.size() != expenseSignedNumForSpecSettleLevel){
                    reEntity.setId("3");
                    reEntity.setToken("???????????????????????????????????????????????????????????????????????????????????????(?????????"+checkSettleLevel+")??????");
                    return reEntity;
                }
            }
        }
        reEntity.setId("0");
        reEntity.setToken("????????????");
        return reEntity;
    }
    //??????????????????????????????????????????????????????????????????
    public BasicTokenStoreEntity checkExistProjectBeforeSettle(String projectId , FinSettleSummaryEntity finSettleSummaryEntity){
        BasicTokenStoreEntity re = new BasicTokenStoreEntity();
        //?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        HashMap<String , String> paymentEventIdHash = new HashMap<>();
        for(FinSettlePaymentDetailEntity paymentDetailEntity : finSettleSummaryEntity.getSettlePaymentDetails()){
            paymentEventIdHash.put(paymentDetailEntity.getPaymentEventId() , paymentDetailEntity.getPaymentEventId());
        }
        FinSettleSummaryWherelogic settleSummaryWherelogic = new FinSettleSummaryWherelogic();
        settleSummaryWherelogic.setProjectId(projectId);
        settleSummaryWherelogic.setSettleLevel(finSettleSummaryEntity.getSettleLevel());
        List<FinSettleSummaryEntity> settledSummarys = finSettleSummaryService.selectListByWhereLogic(settleSummaryWherelogic);
        for(FinSettleSummaryEntity settledSummarayNode : settledSummarys){
            FinSettlePaymentDetailWherelogic settledPaymentDetailWherelogic = new FinSettlePaymentDetailWherelogic();
            settledPaymentDetailWherelogic.setFinSettleId(settledSummarayNode.getId());
            List<FinSettlePaymentDetailEntity> existPaymentList = finSettlePaymentDetailService.selectListByWhereLogic(settledPaymentDetailWherelogic);
            for (FinSettlePaymentDetailEntity existPaymentNode : existPaymentList){
                if(paymentEventIdHash.containsKey(existPaymentNode.getPaymentEventId())){
                    re.setId("1");
                    re.setToken("??????????????????????????????????????????("+existPaymentNode.getPaymentEventId()+")???????????????????????????");
                    return re;
                }
            }
        }
        re.setId("0");
        re.setToken("????????????");
        return re;
    }
    //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
    public List<FinSettlePaymentDetailEntity> checkRestSettlePaymentByProject(String projectId , List<Integer> focusSettleLevelList){
        List<FinSettlePaymentDetailEntity> reList = new ArrayList<>();
        FinSettleSummaryWherelogic settleSummaryWherelogic = new FinSettleSummaryWherelogic();
        settleSummaryWherelogic.setProjectId(projectId);
        List<FinSettleSummaryEntity> settledSummarys = finSettleSummaryService.selectListByWhereLogic(settleSummaryWherelogic);
        HashMap<String , String > checkPaymentIdWithSettleLevelHash = new HashMap<>();//????????????key?????????2_1480149318591500300 , ??????????????????????????????id??????
        for(FinSettleSummaryEntity settledSummarayNode : settledSummarys){
            FinSettlePaymentDetailWherelogic paymentDetailWherelogic = new FinSettlePaymentDetailWherelogic();
            paymentDetailWherelogic.setFinSettleId(settledSummarayNode.getId());
            List<FinSettlePaymentDetailEntity> paymentDetailEntities = finSettlePaymentDetailService.selectListByWhereLogic(paymentDetailWherelogic);
            for(FinSettlePaymentDetailEntity paymentDetailEntity : paymentDetailEntities){
                checkPaymentIdWithSettleLevelHash.put(settledSummarayNode.getSettleLevel()+"_"+paymentDetailEntity.getPaymentEventId() , settledSummarayNode.getSettleLevel()+"_"+paymentDetailEntity.getPaymentEventId());
            }
        }
        ProjectEventWhereLogic paymentWherelogic = new ProjectEventWhereLogic();
        paymentWherelogic.setStatusTarget(true);
        paymentWherelogic.setProjectId(projectId);
        paymentWherelogic.setCategoryId(5);
        paymentWherelogic.setPaymtType(0);
        List<BmsProjectEventEntity> paymentList = projectEventService.selectListByWhereLogic(paymentWherelogic);
        for(BmsProjectEventEntity paymentNode : paymentList){
            for(Integer settleLevelNode : focusSettleLevelList){
                if(!checkPaymentIdWithSettleLevelHash.containsKey(settleLevelNode+"_"+paymentNode.getId())){
                    FinSettlePaymentDetailEntity rePaymentEntity = new FinSettlePaymentDetailEntity();
                    rePaymentEntity.setSettleLevel(settleLevelNode);
                    rePaymentEntity.setPaymentEventId(paymentNode.getId());
                    reList.add(rePaymentEntity);
                }
            }
        }
        return reList;
    }
    //?????????????????????????????????????????????????????????
    public List<FinSettleDetailEntity> checkRestPayoffByProject(String projectId){
        List<FinSettleDetailEntity> reList = new ArrayList<>();
        FinSettleSummaryWherelogic settleSummaryWherelogic = new FinSettleSummaryWherelogic();
        settleSummaryWherelogic.setProjectId(projectId);
        List<FinSettleSummaryEntity> settledSummarys = finSettleSummaryService.selectListByWhereLogic(settleSummaryWherelogic);
        for(FinSettleSummaryEntity settledSummarayNode : settledSummarys) {
            FinSettleDetailWherelogic settleDetailWherelogic = new FinSettleDetailWherelogic();
            settleDetailWherelogic.setFinSettleId(settledSummarayNode.getId());
            List<FinSettleDetailEntity> settleDetailEntities = finSettleDetailService.selectListByWhereLogic(settleDetailWherelogic);
            for (FinSettleDetailEntity settleDetailEntity : settleDetailEntities) {
                Double selltedAmt = settleDetailEntity.getDistributeAmt();
                Double paidoffAmt = rewardPayoffHisService.getPaidoffAmtBySettleDet(settleDetailEntity.getId());
                if(Math.abs(selltedAmt-paidoffAmt)>1){//??????????????????
                    FinSettleDetailEntity restPayoffSettleDetEntity = new FinSettleDetailEntity();
                    restPayoffSettleDetEntity.setFinSettleId(settleDetailEntity.getFinSettleId());
                    restPayoffSettleDetEntity.setId(settleDetailEntity.getId());
                    restPayoffSettleDetEntity.setDistributeUserId(settleDetailEntity.getDistributeUserId());
                    restPayoffSettleDetEntity.setDistributeAmt(settleDetailEntity.getDistributeAmt());
                    restPayoffSettleDetEntity.setAlreadyPayoffAmt(paidoffAmt);
                    restPayoffSettleDetEntity.setRestPayoffAmt(selltedAmt-paidoffAmt);
                    reList.add(restPayoffSettleDetEntity);
                }
            }
        }
        return reList;
    }
}
