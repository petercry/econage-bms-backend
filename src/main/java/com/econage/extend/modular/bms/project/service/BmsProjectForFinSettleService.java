package com.econage.extend.modular.bms.project.service;

import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.basic.service.BmsDataJournalService;
import com.econage.extend.modular.bms.project.component.event.service.BmsProjectEventService;
import com.econage.extend.modular.bms.project.entity.BmsProjectForFinSettleEntity;
import com.econage.extend.modular.bms.project.mapper.BmsProjectForFinSettleMapper;
import com.econage.extend.modular.bms.util.BmsConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

@Service
public class BmsProjectForFinSettleService extends ServiceImpl<BmsProjectForFinSettleMapper, BmsProjectForFinSettleEntity> {
    private BmsDataJournalService dataJournalService;
    private BmsProjectEventService projectEventService;
    @Autowired
    protected void setService(BmsDataJournalService dataJournalService , BmsProjectEventService projectEventService
    ) {
        this.dataJournalService = dataJournalService;
        this.projectEventService = projectEventService;
    }
    @Override
    protected void doFillFkRelationship(Collection<BmsProjectForFinSettleEntity> entities) {
        for (BmsProjectForFinSettleEntity entity : entities) {
            Double expendSum = dataJournalService.sumWithDataJournalByFinalRelatedProjectId(entity.getId(), BmsConst.BMS_DATA_JOURNAL_FOR_FIN);
            Double taxSum = projectEventService.getTaxSum(entity.getId());
            BigDecimal costSum = new BigDecimal(expendSum + taxSum).setScale(2, RoundingMode.HALF_UP);
            entity.setCostSum(costSum.doubleValue());
        }
    }
}
