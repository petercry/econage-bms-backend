package com.econage.extend.modular.bms.employeeManage.service;

import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.employeeManage.entity.EmployeePromoteDetailEntity;
import com.econage.extend.modular.bms.employeeManage.mapper.EmployeePromoteDetailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmployeePromoteDetailService extends ServiceImpl<EmployeePromoteDetailMapper, EmployeePromoteDetailEntity> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeePromoteDetailService.class);
    @Override
    protected void doRefreshSingleEntityBeforeInsert(EmployeePromoteDetailEntity entity){
        entity.setValid(true);
        if(entity.getOrderSeq() == null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
}
