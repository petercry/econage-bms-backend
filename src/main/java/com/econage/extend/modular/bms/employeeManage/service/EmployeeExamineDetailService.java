package com.econage.extend.modular.bms.employeeManage.service;

import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.employeeManage.entity.EmployeeExamineDetailEntity;
import com.econage.extend.modular.bms.employeeManage.mapper.EmployeeExamineDetailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmployeeExamineDetailService extends ServiceImpl<EmployeeExamineDetailMapper, EmployeeExamineDetailEntity> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeExamineDetailService.class);
    @Override
    protected void doRefreshSingleEntityBeforeInsert(EmployeeExamineDetailEntity entity){
        entity.setValid(true);
        if(entity.getOrderSeq() == null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
}
