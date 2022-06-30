package com.econage.extend.modular.bms.finCompute.web;

import com.econage.base.plat.tokenstore.BasicTokenStoreEntity;
import com.econage.core.basic.util.IdWorker;
import com.econage.core.web.extension.controller.BasicControllerImpl;
import com.econage.extend.modular.bms.basic.entity.BmsDataJournalEntity;
import com.econage.extend.modular.bms.basic.service.BmsDataJournalService;
import com.econage.extend.modular.bms.finCompute.entity.*;
import com.econage.extend.modular.bms.finCompute.service.*;
import com.econage.extend.modular.bms.finCompute.trivial.wherelogic.FinComputeWhereLogic;
import com.econage.extend.modular.bms.finCompute.trivial.wherelogic.FinSettlePaymentDetailWherelogic;
import com.econage.extend.modular.bms.finCompute.util.ProjectFinComputeDownloadRender;
import com.econage.extend.modular.bms.project.component.event.entity.BmsProjectEventEntity;
import com.econage.extend.modular.bms.project.component.event.service.BmsProjectEventService;
import com.econage.extend.modular.bms.project.service.BmsProjectService;
import com.econage.extend.modular.bms.util.BmsConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/bms/finCompute")
public class BmsFinComputeController extends BasicControllerImpl {
    private BmsProjectService bmsProjectService;
    private BmsDataJournalService dataJournalService;
    private FinComputeService finComputeService;
    private BmsProjectEventService projectEventService;
    private ExpenseSignService expenseSignService;
    private FinSettleDetailService finSettleDetailService;
    private FinSettlePaymentDetailService finSettlePaymentDetailService;
    private FinSettleSummaryService finSettleSummaryService;
    private RewardPayoffHisService rewardPayoffHisService;
    private final DateTimeFormatter datef = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    protected void setService(BmsProjectService bmsProjectService , BmsDataJournalService dataJournalService , FinComputeService finComputeService , BmsProjectEventService projectEventService , ExpenseSignService expenseSignService ,
        FinSettleDetailService finSettleDetailService , FinSettlePaymentDetailService finSettlePaymentDetailService , FinSettleSummaryService finSettleSummaryService , RewardPayoffHisService rewardPayoffHisService
    ) {
        this.bmsProjectService = bmsProjectService;
        this.dataJournalService = dataJournalService;
        this.finComputeService = finComputeService;
        this.projectEventService = projectEventService;
        this.expenseSignService = expenseSignService;
        this.finSettleDetailService = finSettleDetailService;
        this.finSettlePaymentDetailService = finSettlePaymentDetailService;
        this.finSettleSummaryService = finSettleSummaryService;
        this.rewardPayoffHisService = rewardPayoffHisService;
    }
    @PostMapping("/projectFinXlsExp")
    public View downloadProjectFinXlsExp(@RequestParam("fromDate") String fromDate,
                                 @RequestParam("toDate") String toDate){
        return new ProjectFinComputeDownloadRender(fromDate, toDate , finComputeService);
    }
    /*
     * 查询-get
     * */
    @GetMapping("/search")
    protected List<ProjectFinComputeEntity> search(FinComputeWhereLogic whereLogic ){
        return finComputeService.getFinComputeInfoList(whereLogic.getSearchFromDate() ,whereLogic.getSearchToDate());
    }

    /*
     * 计算某次财务结算的可分配金额-post
     * */
    @PostMapping("/computeFinSettleAmt")
    public BasicTokenStoreEntity computeFinSettleAmt(@RequestBody FinSettleSummaryEntity finSettleSummaryEntity) throws Exception{
        BasicTokenStoreEntity re = new BasicTokenStoreEntity();
        //结算前，先将当前项目的结算情况做一次校验
        BasicTokenStoreEntity preVerifyRe = bmsProjectService.verifyProjectFinSettle(finSettleSummaryEntity.getProjectId());
        if(!preVerifyRe.getId().equals("0")){
            re.setId("pre_"+preVerifyRe.getId());
            re.setToken("结算前的项目财务校验未通过，"+preVerifyRe.getToken());
            return re;
        }
        BasicTokenStoreEntity preCheckRe = bmsProjectService.checkExistProjectBeforeSettle(finSettleSummaryEntity.getProjectId() , finSettleSummaryEntity);
        if(!preCheckRe.getId().equals("0")){
            re.setId("pre_check_"+preCheckRe.getId());
            re.setToken("结算前的检查未通过，"+preCheckRe.getToken());
            return re;
        }
        List<FinSettlePaymentDetailEntity> finSettlePaymentDetailEntityList = finSettleSummaryEntity.getSettlePaymentDetails();
        Double paymentSum = 0d;//到款总计金额
        for(FinSettlePaymentDetailEntity finSettlePaymentDetailEntity : finSettlePaymentDetailEntityList){
            BmsProjectEventEntity paymentEntity = projectEventService.selectById(finSettlePaymentDetailEntity.getPaymentEventId());
            FinSettlePaymentDetailWherelogic settlePaymentDetailWherelogic = new FinSettlePaymentDetailWherelogic();
            settlePaymentDetailWherelogic.setPaymentEventId(paymentEntity.getId());
            settlePaymentDetailWherelogic.setSettleLevel(finSettleSummaryEntity.getSettleLevel());
            settlePaymentDetailWherelogic.setCheckExist(true);
            if(finSettlePaymentDetailService.selectCountByWhereLogic(settlePaymentDetailWherelogic)>0) {
                //如果该笔到款的相应维度已结算过，则计算无效，直接报错
                throw new Exception("到款" + paymentEntity.getId() + "已结算过");
            }
            paymentSum += paymentEntity.getPaymtAmt();
        }
        //计算某个时间点之前，某个维度，尚未结算过的成本支出
        Double costSum = 0d;//成本开支（含税）总计
        //以下统计费用支出
        List<BmsDataJournalEntity> expenseList = dataJournalService.getProjectExpenseListByDate(finSettleSummaryEntity.getProjectId(),datef.format(finSettleSummaryEntity.getSettleDate()));
        for(BmsDataJournalEntity expenseJournal : expenseList){
            //如果该笔费用，在该维度已经被结算过，则略过
            if(expenseSignService.checkExistExpenseSettled(BmsConst.BMS_EXPENSE_MODULAR_COST , expenseJournal.getId() , finSettleSummaryEntity.getSettleLevel())){
                continue;
            }
            costSum += expenseJournal.getDataNumber();
        }
        //以下统计税费
        List<BmsProjectEventEntity> paymentList = projectEventService.getPaymentListByDate(finSettleSummaryEntity.getProjectId() , datef.format(finSettleSummaryEntity.getSettleDate()) + " 23:59:59");
        for(BmsProjectEventEntity paymentNode : paymentList){
            //如果该笔到款的税费，在该维度上已经被结算过，则略过
            if(expenseSignService.checkExistExpenseSettled(BmsConst.BMS_EXPENSE_MODULAR_TAX , paymentNode.getId() , finSettleSummaryEntity.getSettleLevel())){
                continue;
            }
            costSum += (paymentNode.getValueAddedTaxAmt()==null?0d:paymentNode.getValueAddedTaxAmt()) + (paymentNode.getSuperTaxAmt()==null?0d:paymentNode.getSuperTaxAmt()) + (paymentNode.getStampTaxAmt()==null?0d:paymentNode.getStampTaxAmt());
        }
        re.setId("0");
        re.setToken(String.valueOf((paymentSum - costSum)*(finSettleSummaryEntity.getSettlePct()/100)));
        return re;
    }
    /*
     * 校验某项目中目前的结算是否全部正常-post
     * */
    @PostMapping("/verifySettledAmtByProject")
    public BasicTokenStoreEntity verifySettledAmtByProject(@RequestParam String projectId) throws Exception{
        return bmsProjectService.verifyProjectFinSettle(projectId);
    }
    /*
     * 检查某个项目中，目前的到款是否对所有关注的维度都以结算过，返回未结算的到款列表及所对应的维度-post
     * */
    @PostMapping("/checkRestSettlePaymentByProject")
    public List<FinSettlePaymentDetailEntity> checkRestSettlePaymentByProject(@RequestParam String projectId , @RequestParam List<Integer> focusSettleLevelList) throws Exception{
        return bmsProjectService.checkRestSettlePaymentByProject(projectId , focusSettleLevelList);
    }
    /*
     * 检查某个项目中，已结算过，但尚未发放的-post
     * */
    @PostMapping("/checkRestPayoffByProject")
    public List<FinSettleDetailEntity> checkRestPayoffByProject(@RequestParam String projectId ) throws Exception{
        return bmsProjectService.checkRestPayoffByProject(projectId );
    }
    /*
     * 触发一次财务结算-post
     * */
    @PostMapping("/triggerFinSettle")
    @Transactional(rollbackFor = Throwable.class)
    public BasicTokenStoreEntity triggerFinSettle(@RequestBody FinSettleSummaryEntity finSettleSummaryEntity) throws Exception{
        BasicTokenStoreEntity re = new BasicTokenStoreEntity();
        //结算前，先将当前项目的结算情况做一次校验
        BasicTokenStoreEntity preVerifyRe = bmsProjectService.verifyProjectFinSettle(finSettleSummaryEntity.getProjectId());
        if(!preVerifyRe.getId().equals("0")){
            re.setId("pre_"+preVerifyRe.getId());
            re.setToken("结算前的项目财务校验未通过，"+preVerifyRe.getToken());
            return re;
        }
        BasicTokenStoreEntity preCheckRe = bmsProjectService.checkExistProjectBeforeSettle(finSettleSummaryEntity.getProjectId() , finSettleSummaryEntity);
        if(!preCheckRe.getId().equals("0")){
            re.setId("pre_check_"+preCheckRe.getId());
            re.setToken("结算前的检查未通过，"+preCheckRe.getToken());
            return re;
        }
        String finSettleSummaryId = IdWorker.getIdStr();
        finSettleSummaryEntity.setId(finSettleSummaryId);
        //插入bms_financial_settle_detail，本次结算的分配明细
        List<FinSettleDetailEntity> settleDetailEntityList = finSettleSummaryEntity.getSettleDetails();
        for(FinSettleDetailEntity settleDetailEntity : settleDetailEntityList){
            settleDetailEntity.setFinSettleId(finSettleSummaryId);
            finSettleDetailService.insert(settleDetailEntity);
        }
        //插入bms_financial_settle_payment_detail，本次结算中对应的到款明细表
        List<FinSettlePaymentDetailEntity> settlePaymentDetailEntityList = finSettleSummaryEntity.getSettlePaymentDetails();
        for(FinSettlePaymentDetailEntity settlePaymentDetailEntity : settlePaymentDetailEntityList){
            settlePaymentDetailEntity.setFinSettleId(finSettleSummaryId);
            finSettlePaymentDetailService.insert(settlePaymentDetailEntity);
        }
        //插入bms_financial_settle_summary，结算主表
        finSettleSummaryService.insert(finSettleSummaryEntity);
        //标记结算日期前，在本次结算中被该维度结算了的费用支出
        List<BmsDataJournalEntity> expenseList = dataJournalService.getProjectExpenseListByDate(finSettleSummaryEntity.getProjectId(),datef.format(finSettleSummaryEntity.getSettleDate()));
        for(BmsDataJournalEntity expenseJournal : expenseList){
            //如果该笔费用，在该维度已经被结算过，则略过
            if(expenseSignService.checkExistExpenseSettled(BmsConst.BMS_EXPENSE_MODULAR_COST , expenseJournal.getId() , finSettleSummaryEntity.getSettleLevel())){
                continue;
            }
            ExpenseSignEntity expenseSignEntity = new ExpenseSignEntity();
            expenseSignEntity.setModular(BmsConst.BMS_EXPENSE_MODULAR_COST);
            expenseSignEntity.setModularInnerId(expenseJournal.getId());
            expenseSignEntity.setOpFlag(1);
            expenseSignEntity.setSettleLevel(finSettleSummaryEntity.getSettleLevel());
            expenseSignEntity.setFinSettleId(finSettleSummaryId);
            expenseSignService.insert(expenseSignEntity);
        }
        //标记结算日期前，在本次结算中被该维度结算了的税费
        List<BmsProjectEventEntity> paymentList = projectEventService.getPaymentListByDate(finSettleSummaryEntity.getProjectId() , datef.format(finSettleSummaryEntity.getSettleDate()) + " 23:59:59");
        for(BmsProjectEventEntity paymentNode : paymentList){
            //如果该笔到款的税费，在该维度上已经被结算过，则略过
            if(expenseSignService.checkExistExpenseSettled(BmsConst.BMS_EXPENSE_MODULAR_TAX , paymentNode.getId() , finSettleSummaryEntity.getSettleLevel())){
                continue;
            }
            ExpenseSignEntity expenseSignEntity = new ExpenseSignEntity();
            expenseSignEntity.setModular(BmsConst.BMS_EXPENSE_MODULAR_TAX);
            expenseSignEntity.setModularInnerId(paymentNode.getId());
            expenseSignEntity.setOpFlag(1);
            expenseSignEntity.setSettleLevel(finSettleSummaryEntity.getSettleLevel());
            expenseSignEntity.setFinSettleId(finSettleSummaryId);
            expenseSignService.insert(expenseSignEntity);
        }
        //结算完成后，将当前项目的结算情况再做一次校验
        BasicTokenStoreEntity afterVerifyRe = bmsProjectService.verifyProjectFinSettle(finSettleSummaryEntity.getProjectId());
        if(!afterVerifyRe.getId().equals("0")){
            re.setId("after_"+afterVerifyRe.getId());
            re.setToken("结算操作已完成，但结算后的项目财务校验未通过，"+afterVerifyRe.getToken());
            return re;
        }
        re.setId("0");
        re.setToken("结算完成，且校验通过");
        return re;
    }
    /*
     * 按照单笔结算分配明细，进行财务绩效发放-post
     * */
    @PostMapping("/rewardPayoffBySingleSettleDet")
    @Transactional(rollbackFor = Throwable.class)
    public BasicTokenStoreEntity rewardPayoffBySingleSettleDet(@RequestBody RewardPayoffHisEntity payoffHisEntity) throws Exception {
        BasicTokenStoreEntity re = new BasicTokenStoreEntity();
        //结交发放前，先将当前项目的结算情况做一次校验
        BasicTokenStoreEntity preVerifyRe = bmsProjectService.verifyProjectFinSettle(payoffHisEntity.getProjectId());
        if (!preVerifyRe.getId().equals("0")) {
            re.setId("pre_" + preVerifyRe.getId());
            re.setToken("结交发放前的项目财务校验未通过，" + preVerifyRe.getToken());
            return re;
        }
        //发放前前校验下本次发
        return rewardPayoffHisService.rewardPayOff(payoffHisEntity);
    }
}
