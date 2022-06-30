package com.econage.extend.modular.bms.employeeManage.service;

import com.econage.base.support.kv.manage.BasicKvUnionQuery;
import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.employeeManage.entity.EmployeeExamineDetailEntity;
import com.econage.extend.modular.bms.employeeManage.entity.EmployeeExamineSummaryEntity;
import com.econage.extend.modular.bms.employeeManage.entity.EmployeeInfoEntity;
import com.econage.extend.modular.bms.employeeManage.entity.EmployeePromoteDetailEntity;
import com.econage.extend.modular.bms.employeeManage.mapper.EmployeeInfoMapper;
import com.econage.extend.modular.bms.employeeManage.trivial.wherelogic.EmployeeExamineDetailWherelogic;
import com.econage.extend.modular.bms.employeeManage.trivial.wherelogic.EmployeeInfoWherelogic;
import com.econage.extend.modular.bms.employeeManage.trivial.wherelogic.EmployeePromoteDetailWherelogic;
import com.econage.extend.modular.bms.util.BmsConst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;


@Service
public class EmployeeInfoService extends ServiceImpl<EmployeeInfoMapper, EmployeeInfoEntity> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeInfoService.class);
    private BasicKvUnionQuery kvService;
    private EmployeeExamineDetailService employeeExamineDetailService;
    private EmployeePromoteDetailService employeePromoteDetailService;
    private EmployeeExamineSummaryService employeeExamineSummaryService;
    @Autowired
    protected void setService(BasicKvUnionQuery kvService , EmployeeExamineDetailService employeeExamineDetailService , EmployeePromoteDetailService employeePromoteDetailService , EmployeeExamineSummaryService employeeExamineSummaryService) {
        this.kvService = kvService;
        this.employeeExamineDetailService = employeeExamineDetailService;
        this.employeePromoteDetailService = employeePromoteDetailService;
        this.employeeExamineSummaryService = employeeExamineSummaryService;
    }
    @Override
    protected void doRefreshSingleEntityBeforeInsert(EmployeeInfoEntity entity){
        entity.setValid(true);
        if(entity.getOrderSeq() == null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
    @Override
    protected void doFillFkRelationship(Collection<EmployeeInfoEntity> entities ){
        for(EmployeeInfoEntity entity : entities) {
            String posGradeTotalDesc = "";
            if (!StringUtils.isEmpty(entity.getPositionGrade())) {
                posGradeTotalDesc += kvService.getKvTextById(entity.getPositionGrade());
            }
            if (!StringUtils.isEmpty(entity.getPostionLevel())) {
                posGradeTotalDesc += kvService.getKvTextById(entity.getPostionLevel());
            }
            entity.setPositionGradeTotalDesc(posGradeTotalDesc);
        }
    }
    public List<EmployeeInfoEntity> setExamineDataIntoEntities(EmployeeInfoWherelogic employeeInfoWherelogic){
        employeeInfoWherelogic.setStatusTarget(true);
        List<EmployeeInfoEntity> employeeInfoEntities = selectListByWhereLogic(employeeInfoWherelogic);
        double maxFinalGrade = 0; //最高分
        double minFinalGrade = 100; //最低分
        for (EmployeeInfoEntity employeeInfoEntity : employeeInfoEntities){
            EmployeeExamineDetailWherelogic employeeExamineDetailWherelogic = new EmployeeExamineDetailWherelogic();
            employeeExamineDetailWherelogic.setStatusTarget(true);
            employeeExamineDetailWherelogic.setExamineId(employeeInfoWherelogic.getSearchExamineId());
            employeeExamineDetailWherelogic.setUserId(employeeInfoEntity.getUserId());
            List<EmployeeExamineDetailEntity> examineDetailEntities = employeeExamineDetailService.selectListByWhereLogic(employeeExamineDetailWherelogic);
            if(examineDetailEntities!=null&&examineDetailEntities.size()>0){
                EmployeeExamineDetailEntity examineDetailEntity = examineDetailEntities.get(0);
                if(employeeInfoWherelogic.getAutoComputeFinalData()){ //需要自动计算终值绩效分
                    if(examineDetailEntity.getGradeModel()!=null) {
                        if (examineDetailEntity.getGradeModel().equals(BmsConst.EMPLOYEE_GRADE_MODEL_FOR_AF_DEV)) {//AF研发线算法
                            Double finalGrade = examineDetailEntity.getLeaderGrade() * 0.34 + examineDetailEntity.getProductLeaderGrade() * 0.34 + examineDetailEntity.getProductAssistantGrade() * 0.05 + examineDetailEntity.getCtoGrade() * 0.24 + examineDetailEntity.getPosLevelGrade() * 0.03;
                            examineDetailEntity.setFinalGrade(finalGrade);
                        }else if (examineDetailEntity.getGradeModel().equals(BmsConst.EMPLOYEE_GRADE_MODEL_FOR_IBPM_DEV)) {//iBpm研发线算法
                            Double finalGrade = examineDetailEntity.getCoworkerGrade() * 0.32 + examineDetailEntity.getLeaderGrade() * 0.4 + examineDetailEntity.getPmoGrade() * 0.1 + examineDetailEntity.getManhourGrade() * 0.04 + examineDetailEntity.getPosLevelGrade() * 0.04 + examineDetailEntity.getCtoGrade() * 0.1;
                            examineDetailEntity.setFinalGrade(finalGrade);
                        }else if (examineDetailEntity.getGradeModel().equals(BmsConst.EMPLOYEE_GRADE_MODEL_FOR_IT_SUPPORT)) {//技术支撑线算法
                            Double finalGrade = examineDetailEntity.getLeaderGrade() * 0.6 + examineDetailEntity.getCtoGrade() * 0.35 + examineDetailEntity.getPosLevelGrade() * 0.05 ;
                            examineDetailEntity.setFinalGrade(finalGrade);
                        }else if (examineDetailEntity.getGradeModel().equals(BmsConst.EMPLOYEE_GRADE_MODEL_FOR_ROOKIE)) {//新人算法
                            Double examineReward = examineDetailEntity.getWageBase() * examineDetailEntity.getFinalRewardScore();
                            Double dayNum = (double)( (int) (LocalDate.parse("2021-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd")).toEpochDay() - employeeInfoEntity.getRegularTime().toEpochDay())+1) / 365;
                            examineReward = dayNum * examineReward ;
                            examineDetailEntity.setExamineReward(examineReward);
                        }
                    }
                }
                if(examineDetailEntity.getFinalGrade()!=null && examineDetailEntity.getFinalGrade() > maxFinalGrade){
                    maxFinalGrade = examineDetailEntity.getFinalGrade();
                }
                if(examineDetailEntity.getFinalGrade()!=null && examineDetailEntity.getFinalGrade() < minFinalGrade){
                    minFinalGrade = examineDetailEntity.getFinalGrade();
                }
                employeeInfoEntity.setEmployeeExamineDetailEntity(examineDetailEntity);
            }
            EmployeePromoteDetailWherelogic employeePromoteDetailWherelogic = new EmployeePromoteDetailWherelogic();
            employeePromoteDetailWherelogic.setStatusTarget(true);
            employeePromoteDetailWherelogic.setPromoteId(employeeInfoWherelogic.getSearchPromoteId());
            employeePromoteDetailWherelogic.setUserId(employeeInfoEntity.getUserId());
            List<EmployeePromoteDetailEntity> promoteDetailEntities = employeePromoteDetailService.selectListByWhereLogic(employeePromoteDetailWherelogic);
            if(promoteDetailEntities!=null&& promoteDetailEntities.size()>0){
                employeeInfoEntity.setEmployeePromoteDetailEntity(promoteDetailEntities.get(0));
            }
        }
        //maxFinalGrade = 100;
        //自动计算最终的奖金基数（X个月奖金）
        if(!StringUtils.isEmpty(employeeInfoWherelogic.getSearchExamineId()) && employeeInfoWherelogic.getAutoComputeFinalData() && !employeeInfoWherelogic.getSearchExamineModel().equals(BmsConst.EMPLOYEE_GRADE_MODEL_FOR_ROOKIE)) {
            EmployeeExamineSummaryEntity examineSummaryEntity = employeeExamineSummaryService.selectById(employeeInfoWherelogic.getSearchExamineId());
            double rewardBase = examineSummaryEntity.getRewardBase();  //保底奖金基数，比如2.5个月
            double rewardTop = examineSummaryEntity.getRewardTop();    //最高奖金基数，比如4个月

            if(employeeInfoWherelogic.getSearchExamineModel().equals(BmsConst.EMPLOYEE_GRADE_MODEL_FOR_IT_SUPPORT)){ //如果是技术支撑线，则最高分按100算，最低分按60算
                maxFinalGrade = 100;
                minFinalGrade = 60;
            }

            for (EmployeeInfoEntity employeeInfoEntity : employeeInfoEntities) {
                EmployeeExamineDetailEntity examineDetailEntity = employeeInfoEntity.getEmployeeExamineDetailEntity();
                if(examineDetailEntity.getFinalGrade()!=null) {
                    double checkFinalGrade = examineDetailEntity.getFinalGrade();
                    double finalRewardScore = ((checkFinalGrade - minFinalGrade) * rewardTop + (maxFinalGrade - checkFinalGrade) * rewardBase) / (maxFinalGrade - minFinalGrade);
                    examineDetailEntity.setFinalRewardScore(finalRewardScore);
                    if (examineDetailEntity.getWageBase() != null) {
                        Double examineReward = finalRewardScore * examineDetailEntity.getWageBase();
                        if( (LocalDate.parse("2021-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd")).toEpochDay() - employeeInfoEntity.getRegularTime().toEpochDay()) < 364 ){
                            Double dayNum = (double)( (int) (LocalDate.parse("2021-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd")).toEpochDay() - employeeInfoEntity.getRegularTime().toEpochDay())+1) / 365;
                            examineReward = dayNum * examineReward ;
                        }
                        examineDetailEntity.setExamineReward(examineReward);
                    }
                }
            }
        }
        return employeeInfoEntities;
    }
}
