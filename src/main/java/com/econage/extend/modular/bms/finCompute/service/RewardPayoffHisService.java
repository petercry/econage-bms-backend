package com.econage.extend.modular.bms.finCompute.service;

import com.econage.base.plat.tokenstore.BasicTokenStoreEntity;
import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.finCompute.entity.FinSettleDetailEntity;
import com.econage.extend.modular.bms.finCompute.entity.RewardPayoffHisEntity;
import com.econage.extend.modular.bms.finCompute.mapper.RewardPayoffHisMapper;
import com.econage.extend.modular.bms.finCompute.trivial.wherelogic.RewardPayoffHisWherelogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardPayoffHisService extends ServiceImpl<RewardPayoffHisMapper, RewardPayoffHisEntity> {
    private FinSettleDetailService settleDetailService;
    @Autowired
    protected void setService(FinSettleDetailService settleDetailService) {
        this.settleDetailService = settleDetailService;
    }
    @Override
    protected void doRefreshSingleEntityBeforeInsert(RewardPayoffHisEntity entity){
        if(entity.getOrderSeq() == null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
    //校验计划发放的金额，是否超过了对应分配明细剩余可发放的金额
    public Boolean checkPayOffAmt(RewardPayoffHisEntity payoffHisEntity){
        FinSettleDetailEntity settleDetailEntity = settleDetailService.selectById(payoffHisEntity.getFinSettleDetId());
        Double settledAmt = settleDetailEntity.getDistributeAmt();
        RewardPayoffHisWherelogic payoffHisWherelogic = new RewardPayoffHisWherelogic();
        payoffHisWherelogic.setFinSettleDetId(payoffHisEntity.getFinSettleDetId());
        List<RewardPayoffHisEntity> existPayoffList = selectListByWhereLogic(payoffHisWherelogic);
        Double existsPayoffSum = 0d;
        for(RewardPayoffHisEntity existPayoffNode : existPayoffList){
            existsPayoffSum += existPayoffNode.getThisPayoffAmt();
        }
        return !((settledAmt - existsPayoffSum - payoffHisEntity.getThisPayoffAmt()) < -1);
    }
    public BasicTokenStoreEntity rewardPayOff(RewardPayoffHisEntity entity){
        BasicTokenStoreEntity re = new BasicTokenStoreEntity();
        //绩效发放前，先校验下计划发放的金额是否合理，是否未超过能够发放的金额
        if(!checkPayOffAmt(entity)){
            re.setId("1");
            re.setToken("计划发放金额超过结算分配剩余金额，绩效发放失败");
            return re;
        }
        insert(entity);

        re.setId("0");
        re.setToken("绩效发放完成");
        return re;
    }
    public Double getPaidoffAmtBySettleDet(String settleDetId){
        Double re = 0d;
        RewardPayoffHisWherelogic rewardPayoffHisWherelogic = new RewardPayoffHisWherelogic();
        rewardPayoffHisWherelogic.setFinSettleDetId(settleDetId);
        List<RewardPayoffHisEntity> paidoffList = selectListByWhereLogic(rewardPayoffHisWherelogic);
        for (RewardPayoffHisEntity payoffHisEntity : paidoffList){
            re += payoffHisEntity.getThisPayoffAmt();
        }
        return re;
    }
}
