package com.econage.extend.modular.bms.finCompute.service;

import com.econage.base.support.kv.manage.BasicKvUnionQuery;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.basic.service.BmsDataJournalService;
import com.econage.extend.modular.bms.finCompute.entity.ProjectFinComputeEntity;
import com.econage.extend.modular.bms.finCompute.mapper.FinComputeMapper;
import com.econage.extend.modular.bms.project.component.event.entity.BmsProjectEventEntity;
import com.econage.extend.modular.bms.project.component.event.service.BmsProjectEventService;
import com.econage.extend.modular.bms.project.component.event.trivial.wherelogic.ProjectEventWhereLogic;
import com.econage.extend.modular.bms.project.entity.BmsProjectEntity;
import com.econage.extend.modular.bms.project.service.BmsProjectService;
import com.econage.extend.modular.bms.project.trivial.wherelogic.ProjectWhereLogic;
import com.econage.extend.modular.bms.util.BmsConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FinComputeService  extends ServiceImpl<FinComputeMapper, ProjectFinComputeEntity> {
    private BmsProjectService projectService;
    private BmsDataJournalService dataJournalService;
    private BmsProjectEventService projectEventService;
    private BasicKvUnionQuery kvService;
    private final String[] defaultSort = {"create_date_"};
    private final String[] defaultOrder = {"desc"};
    private final String[] paymentDefaultSort = {"paymt_date_"};
    private final String[] paymentDefaultOrder = {"desc"};
    @Autowired
    protected void setService(BmsProjectService projectService , BmsDataJournalService dataJournalService , BmsProjectEventService projectEventService , BasicKvUnionQuery kvService) {
        this.projectService = projectService;
        this.dataJournalService = dataJournalService;
        this.projectEventService = projectEventService;
        this.kvService = kvService;
    }
    public ArrayList<ProjectFinComputeEntity> getFinComputeInfoList(String searchFromDate , String searchToDate ){
        ProjectWhereLogic projectWhereLogicObj = new ProjectWhereLogic();
        projectWhereLogicObj.setStatusTarget(true);
        projectWhereLogicObj.setSearchFinCompute(true);
        projectWhereLogicObj.setFinComputeFromDateStr(searchFromDate + " 00:00:00");
        projectWhereLogicObj.setFinComputeToDateStr(searchToDate + " 23:59:59");
        projectWhereLogicObj.setSort(defaultSort);
        projectWhereLogicObj.setOrder(defaultOrder);
        projectWhereLogicObj.setPage(1);
        projectWhereLogicObj.setRows(10000);
        List<BmsProjectEntity> bpeList = projectService.selectListByWhereLogic(projectWhereLogicObj);

        LocalDate searchFromD = LocalDate.parse(searchFromDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        ArrayList<ProjectFinComputeEntity> pfceList = new ArrayList<>();
        for(BmsProjectEntity bpe : bpeList){
            ProjectEventWhereLogic projectEventWhereLogic = new ProjectEventWhereLogic();
            projectEventWhereLogic.setStatusTarget(true);
            projectEventWhereLogic.setProjectId(bpe.getId());
            projectEventWhereLogic.setCategoryId(5);
            projectEventWhereLogic.setPaymtType(0);
            projectEventWhereLogic.setSearchPaymentDateFromStr(searchFromDate + " 00:00:00");
            projectEventWhereLogic.setSearchPaymentDateToStr(searchToDate + " 23:59:59");
            projectEventWhereLogic.setSort(paymentDefaultSort);
            projectEventWhereLogic.setOrder(paymentDefaultOrder);
            projectEventWhereLogic.setPage(1);
            projectEventWhereLogic.setPage(100);

            List<BmsProjectEventEntity> currPaymentList = projectEventService.selectListByWhereLogic(projectEventWhereLogic);
            for(BmsProjectEventEntity currPaymentNode : currPaymentList){
                ProjectFinComputeEntity pfce = new ProjectFinComputeEntity();
                pfce.setIdx(""+(pfceList.size()+1));
                pfce.setProjectName(bpe.getProjectName());
                pfce.setContractAmt(bpe.getContractAmt());
                pfce.setReceivedPaymtAmt(bpe.getReceivedPaymtAmt());
                pfce.setReceivedPaymtPct(bpe.getReceivedPaymtPct());
                pfce.setRestPaymtAmt(bpe.getRestPaymtAmt());
                Double expendSum = dataJournalService.sumWithDataJournalByFinalRelatedProjectId(bpe.getId(), BmsConst.BMS_DATA_JOURNAL_FOR_FIN);
                Double taxSum = projectEventService.getTaxSum(bpe.getId());
                pfce.setExpenseSum(expendSum+taxSum);

                Double reimburseFeeExpenseSum = dataJournalService.sumWithDataJournalByFinalRelatedProjectId(bpe.getId(), BmsConst.BMS_DATA_JOURNAL_FOR_FIN_ABOUT_REIMBURSEFEE);
                Double payFeeExpenseSum = dataJournalService.sumWithDataJournalByFinalRelatedProjectId(bpe.getId(), BmsConst.BMS_DATA_JOURNAL_FOR_FIN_ABOUT_PAYFEE);
                Double advanceFeeExpenseSum = dataJournalService.sumWithDataJournalByFinalRelatedProjectId(bpe.getId(), BmsConst.BMS_DATA_JOURNAL_FOR_FIN_ABOUT_ADVANCEFEE);
                Double bizFeeExpenseSum = dataJournalService.sumWithDataJournalByFinalRelatedProjectId(bpe.getId(), BmsConst.BMS_DATA_JOURNAL_FOR_FIN_ABOUT_BIZFEE);

                pfce.setReimburseFeeExpense(reimburseFeeExpenseSum);
                pfce.setPayFeeExpense(payFeeExpenseSum);
                pfce.setAdvanceFeeExpense(advanceFeeExpenseSum);
                pfce.setBizFeeExpense(bizFeeExpenseSum);
                pfce.setTaxExpense(taxSum);

                ProjectEventWhereLogic hisEventWhereLogic = new ProjectEventWhereLogic();
                hisEventWhereLogic.setStatusTarget(true);
                hisEventWhereLogic.setProjectId(bpe.getId());
                hisEventWhereLogic.setCategoryId(5);
                hisEventWhereLogic.setPaymtType(0);

                List<BmsProjectEventEntity> hisPaymentList = projectEventService.selectListByWhereLogic(hisEventWhereLogic);
                boolean hasPaymentBefore = false;
                for(BmsProjectEventEntity payment_node : hisPaymentList){
                    if(payment_node.getPaymtDate().isBefore(searchFromD)){
                        hasPaymentBefore = true;break;
                    }
                }
                pfce.setExistPaymentBefore(hasPaymentBefore);

                pfce.setCurrPaymentDate(currPaymentNode.getPaymtDate()); //本次到款时间
                pfce.setCurrPaymentType(currPaymentNode.getStage());//本次到款类型
                pfce.setCurrPaymentTypeDesc(kvService.getKvTextById(pfce.getCurrPaymentType()));
                pfce.setCurrPaymentAmt(currPaymentNode.getPaymtAmt());//本次到款金额
                pfce.setPaymentComments(currPaymentNode.getSubject());
                pfce.setCurrTaxType(currPaymentNode.getTaxType());//税费类别
                pfce.setCurrTaxTypeDesc(kvService.getKvTextById(pfce.getCurrTaxType()));
                pfce.setValueAddedTax(currPaymentNode.getValueAddedTaxAmt());//增值税
                pfce.setSuperTax(currPaymentNode.getSuperTaxAmt());//附加税
                pfce.setStampTax(currPaymentNode.getStampTaxAmt());//印花税

                pfce.setProjectId(bpe.getId());
                pfce.setPaymentEventId(currPaymentNode.getId());

                pfceList.add(pfce);
            }





        }
        return pfceList;
    }
}
