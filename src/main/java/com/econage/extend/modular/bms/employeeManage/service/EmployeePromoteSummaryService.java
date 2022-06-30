package com.econage.extend.modular.bms.employeeManage.service;

import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.employeeManage.entity.EmployeePromoteSummaryEntity;
import com.econage.extend.modular.bms.employeeManage.mapper.EmployeePromoteSummaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmployeePromoteSummaryService extends ServiceImpl<EmployeePromoteSummaryMapper, EmployeePromoteSummaryEntity> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeePromoteSummaryService.class);
    @Override
    protected void doRefreshSingleEntityBeforeInsert(EmployeePromoteSummaryEntity entity){
        entity.setValid(true);
        if(entity.getOrderSeq() == null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
}
