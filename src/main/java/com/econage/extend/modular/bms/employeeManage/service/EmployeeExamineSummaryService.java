package com.econage.extend.modular.bms.employeeManage.service;

import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.employeeManage.entity.EmployeeExamineSummaryEntity;
import com.econage.extend.modular.bms.employeeManage.mapper.EmployeeExamineSummaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmployeeExamineSummaryService extends ServiceImpl<EmployeeExamineSummaryMapper, EmployeeExamineSummaryEntity> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeExamineSummaryService.class);
    @Override
    protected void doRefreshSingleEntityBeforeInsert(EmployeeExamineSummaryEntity entity){
        entity.setValid(true);
        if(entity.getOrderSeq() == null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
}
